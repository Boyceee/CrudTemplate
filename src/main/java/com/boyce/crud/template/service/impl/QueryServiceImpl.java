package com.boyce.crud.template.service.impl;

import com.boyce.crud.template.service.QueryService;
import com.boyce.crud.template.util.ClassUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Boyce
 * @version V1.0
 * @date 2020/4/9 13:42
 */
@Service
public class QueryServiceImpl implements QueryService {
    private static final String CLASS_STRING = "java.lang.String";
    private static final String CLASS_CHAR = "java.lang.Character";
    private static final String CLASS_DATE = "java.util.Date";
    private static final String CLASS_TIMESTAMP = "java.sql.Timestamp";
    private static final Logger logger = LoggerFactory.getLogger(QueryServiceImpl.class);
    @Autowired
    private JdbcOperations jdbcOperations;

    /**
     * do select
     *
     * @param object
     * @return the query result of json according to the given object
     * @throws Exception
     */
    @Override
    public String query(Object object) throws Exception {
        String objectName = object.getClass().getName();
        List<Map<String, Object>> list = new ArrayList<>();
        StringBuilder columns = new StringBuilder();
        StringBuilder sql = new StringBuilder();
        Map<String, String> fieldsMap = ClassUtils.getFields(object);
        for (Map.Entry<String, String> entry : fieldsMap.entrySet()) {
            columns.append(",").append(entry.getValue());
        }
        Map<String, String> tableMap = ClassUtils.getTable(object);
        sql.append("select ").append(columns.substring(1)).append(" from ").append(tableMap.get(objectName));
        logger.info("execute sql:" + sql);
        try {
            list = jdbcOperations.queryForList(sql.toString());
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw e;
        }
        return parseListToJson(list, objectName, reverseFieldsMap(fieldsMap));
    }

    /**
     * reverse target map's key and value
     *
     * @param map
     * @return reversed map
     */
    private Map<String, String> reverseFieldsMap(Map<String, String> map) {
        Map<String, String> reversedMap = new HashMap<>(map.size());
        for (Map.Entry<String, String> entry : map.entrySet()) {
            reversedMap.put(entry.getValue(), entry.getKey());
        }
        return reversedMap;
    }

    /**
     * transform the result list to json
     *
     * @param list
     * @param objectName
     * @param fieldsMap
     * @return json of String
     */
    private String parseListToJson(List<Map<String, Object>> list, String objectName, Map<String, String> fieldsMap) {
        String[] splits = objectName.split("\\.");
        objectName = splits[splits.length - 1];
        objectName = Character.toString(objectName.charAt(0)).toLowerCase() + objectName.substring(1);
        String json = "{\"" + objectName + "\" : [";
        String listJson = "";
        for (Map<String, Object> map : list) {
            listJson += ",{";
            String mapJson = "";
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                mapJson += ",\"" + fieldsMap.get(entry.getKey()) + "\" : " + transformObjectToString(entry.getValue());
            }
            listJson += mapJson.substring(1);
            listJson += "}";
        }
        json += listJson.substring(1);
        json += "]}";
        return json;
    }

    /**
     * transform an object to String
     * if the object is String or char,then add \"\" to the result
     *
     * @param object
     * @return String result
     */
    private String transformObjectToString(Object object) {
        if (object == null) {
            return "\"\"";
        } else if (CLASS_STRING.equals(object.getClass().getName()) || CLASS_CHAR.equals(object.getClass().getName()) || CLASS_TIMESTAMP.equals(object.getClass().getName()) || CLASS_DATE.equals(object.getClass().getName())) {
            return "\"" + object.toString() + "\"";
        } else {
            return object.toString();
        }
    }

}
