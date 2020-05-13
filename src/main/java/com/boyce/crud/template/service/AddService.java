package com.boyce.crud.template.service;


import javax.servlet.http.HttpServletRequest;

/**
 * @author Boyce
 * @version V1.0
 * @date 2020/5/9 17:19
 */
public interface AddService {
    /**
     * do insert
     *
     * @param httpServletRequest
     * @param object
     * @return void
     */
    public void add(HttpServletRequest httpServletRequest, Object object) throws Exception;
}
