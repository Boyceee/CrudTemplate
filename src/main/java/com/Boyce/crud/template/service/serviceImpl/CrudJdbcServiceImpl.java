package com.Boyce.CrudTemplate.service.serviceImpl;

import com.Boyce.CrudTemplate.annotation.Column;
import com.Boyce.CrudTemplate.annotation.Table;
import com.Boyce.CrudTemplate.service.CrudJdbcService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description
 * @Author Boyce
 * @Date 2020/4/9 13:42
 * @Version V1.0
 */
@Component
public class CrudJdbcServiceImpl implements CrudJdbcService {
    private static final Logger logger = LoggerFactory.getLogger(CrudJdbcServiceImpl.class);
    @Autowired
    private JdbcOperations jdbcOperations;

    @Override
    public List<Map<String, Object>> query(Object object) {
        List<Map<String,Object>> list = new ArrayList<>();
        String sql = "";
        for(Map.Entry<String,Object> entry : getFields(object).entrySet()){
            sql += "," + entry.getKey();
        }
        String table = getTable(object);
        try{
            list = jdbcOperations.queryForList("select " + sql.substring(1) + " from " + table);
        }catch (Exception e){
            logger.error(e.getMessage());
            throw e;
        }
        return list;
    }

    private Map<String, Object> getFields(Object object) {
        Field[] fields = object.getClass().getDeclaredFields();
        int maxLength = fields.length;
        Map<String, Object> map = new HashMap<>(maxLength);
        for(Field field : fields){
            if(field.isAnnotationPresent(Column.class)){
                String column = field.getAnnotation(Column.class).name();
            }
        }
        return map;
    }

    private String getTable(Object object){
        for(Annotation annotation : object.getClass().getAnnotations()){
            if(annotation instanceof Table){
                return ((Table) annotation).name();
            }
        }
        return null;
    }
}
