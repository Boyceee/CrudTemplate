package com.boyce.crud.template.service;


/**
 * @Description
 * @Author Boyce
 * @Date 2020/4/9 11:56
 * @Version V1.0
 */
public interface CrudJdbcService {
    /**
     * @param object
     * @return java.lang.String
     * @description return the query result of json according to the given object
     **/
    public String query(Object object);
}
