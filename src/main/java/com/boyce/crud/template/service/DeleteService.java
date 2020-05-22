package com.boyce.crud.template.service;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Boyce
 * @version V1.0
 * @date 2020/5/22 13:23
 */
public interface DeleteService {
    /**
     * do delete
     *
     * @param httpServletRequest
     * @param object
     * @throws Exception
     */
    public void delete(HttpServletRequest httpServletRequest, Object object) throws Exception;
}
