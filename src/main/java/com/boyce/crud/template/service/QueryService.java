package com.boyce.crud.template.service;


/**
 * @author Boyce
 * @version V1.0
 * @date 2020/4/9 11:56
 */
public interface QueryService {
    /**
     * return the query result of json according to the given object
     *
     * @param object
     * @return java.lang.String
     */
    public String query(Object object) throws Exception;
}
