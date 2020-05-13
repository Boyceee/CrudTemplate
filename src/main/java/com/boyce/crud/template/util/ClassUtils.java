package com.boyce.crud.template.util;

import com.boyce.crud.template.annotation.Column;
import com.boyce.crud.template.annotation.Table;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Boyce
 * @version V1.0
 * @date 2020/5/9 18:05
 */
public class ClassUtils {
    /**
     * get <field's name,sql column> as <key,value> from object
     *
     * @param object
     * @return map of <field's name,sql column>
     */
    public static Map<String, String> getFields(Object object) {
        Field[] fields = object.getClass().getDeclaredFields();
        int maxLength = fields.length;
        Map<String, String> map = new HashMap<>(maxLength);
        String sqlField = "";
        for (Field field : fields) {
            if (field.isAnnotationPresent(Column.class) && !StringUtils.isEmpty(sqlField = field.getAnnotation(Column.class).value())) {
                map.put(field.getName(), sqlField);
            } else {
                map.put(field.getName(), field.getName());
            }
        }
        return map;
    }

    /**
     * get <object's name,sql table> as <key,value> from object
     *
     * @param object
     * @return map of <object's name,sql table>
     */
    public static Map<String, String> getTable(Object object) {
        String objectName = object.getClass().getName();
        String[] splits = objectName.split("\\.");
        objectName = splits[splits.length - 1];
        Map<String, String> tableMap = new HashMap<>(1, 1);
        String table = "";
        if (object.getClass().isAnnotationPresent(Table.class) && !StringUtils.isEmpty(table = object.getClass().getAnnotation(Table.class).value())) {
            tableMap.put(object.getClass().getName(), table);
        } else {
            tableMap.put(object.getClass().getName(), objectName);
        }
        return tableMap;
    }
}
