package com.boyce.crud.template.service;


/**
 * @author Boyce
 * @version V1.0
 * @date 2020/4/9 11:56
 */
public interface QueryService {
    /**
     * do select
     *
     * @param object
     * @return the query result of json according to the given object
     * @throws Exception
     */
    public String query(Object object) throws Exception;
}
