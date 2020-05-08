package com.boyce.crud.template.service.impl;

import com.boyce.crud.template.annotation.Column;
import com.boyce.crud.template.annotation.Table;
import com.boyce.crud.template.service.CrudJdbcService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @description
 * @author Boyce
 * @date 2020/4/9 13:42
 * @version V1.0
 */
@Component
public class CrudJdbcServiceImpl implements CrudJdbcService {
    private static final String CLASS_STRING = "java.lang.String";
    private static final Logger logger = LoggerFactory.getLogger(CrudJdbcServiceImpl.class);
    @Autowired
    private JdbcOperations jdbcOperations;

    /**
     * @param object
     * @return java.lang.String
     * @description return the query result of json according to the given object
     **/
    @Override
    public String query(Object object) {
        String objectName = object.getClass().getName();
        List<Map<String, Object>> list = new ArrayList<>();
        String sql = "";
        Map<String, String> fieldsMap = getFields(object);
        for (Map.Entry<String, String> entry : fieldsMap.entrySet()) {
            sql += "," + entry.getValue();
        }
        Map<String, String> tableMap = getTable(object);
        try {
            list = jdbcOperations.queryForList("select " + sql.substring(1) + " from " + tableMap.get(objectName));
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw e;
        }
        return parseListToJson(list, objectName, reverseFieldsMap(fieldsMap));
    }

    /**
     * @param object
     * @return java.util.Map<java.lang.String,java.lang.String>
     * @description get fields from object
     **/
    private Map<String, String> getFields(Object object) {
        Field[] fields = object.getClass().getDeclaredFields();
        int maxLength = fields.length;
        Map<String, String> map = new HashMap<>(maxLength);
        String sqlField = "";
        for (Field field : fields) {
            if (field.isAnnotationPresent(Column.class) && !StringUtils.isEmpty(sqlField = field.getAnnotation(Column.class).name())) {
                map.put(field.getName(), sqlField);
            } else {
                map.put(field.getName(), field.getName());
            }
        }
        return map;
    }

    /**
     * @param object
     * @return java.util.Map<java.lang.String,java.lang.String>
     * @description get the target table from object
     **/
    private Map<String, String> getTable(Object object) {
        Map<String, String> tableMap = new HashMap<>(1, 1);
        String table = "";
        if (object.getClass().isAnnotationPresent(Table.class) && !StringUtils.isEmpty(table = object.getClass().getAnnotation(Table.class).name())) {
            tableMap.put(object.getClass().getName(), table);
        } else {
            tableMap.put(object.getClass().getName(), object.getClass().getName());
        }
        return tableMap;
    }

    /**
     * @param map
     * @return java.util.Map<java.lang.String,java.lang.String>
     * @description reverse target map's key and value
     **/
    private Map<String, String> reverseFieldsMap(Map<String, String> map) {
        Map<String, String> reversedMap = new HashMap<>(map.size());
        for (Map.Entry<String, String> entry : map.entrySet()) {
            reversedMap.put(entry.getValue(), entry.getKey());
        }
        return reversedMap;
    }

    /**
     * @param list
     * @param objectName
     * @param fieldsMap
     * @return java.lang.String
     * @description parse the result list to json
     **/
    private String parseListToJson(List<Map<String, Object>> list, String objectName, Map<String, String> fieldsMap) {
        String[] splits= objectName.split("\\.");
        objectName = splits[splits.length-1];
        objectName = Character.toString(objectName.charAt(0)).toLowerCase() + objectName.substring(1);
        String json = "{\"" + objectName + "\" : [";
        String listJson = "";
        for(Map<String,Object> map : list){
            listJson += ",{";
            String mapJson = "";
            for(Map.Entry<String,Object> entry : map.entrySet()){
                mapJson += ",\"" + fieldsMap.get(entry.getKey()) + "\" : " + transformObjectToJson(entry.getValue());
            }
            listJson += mapJson.substring(1);
            listJson += "}";
        }
        json += listJson.substring(1);
        json += "]}";
        return json;
    }

    /**
     * @param object
     * @return java.lang.String
     * @description transfer an object to json
     **/
    private String transformObjectToJson(Object object){
        if(CLASS_STRING.equals(object.getClass().getName())){
            return "\"" + object.toString() + "\"";
        }else {
            return object.toString();
        }
    }

}
