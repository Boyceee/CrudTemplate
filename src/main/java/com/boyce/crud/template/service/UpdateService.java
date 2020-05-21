package com.boyce.crud.template.service;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Boyce
 * @version V1.0
 * @date 2020/5/13 15:01
 */
public interface UpdateService {
    /**
     * do update
     *
     * @param httpServletRequest
     * @param object
     * @throws Exception
     */
    public void update(HttpServletRequest httpServletRequest, Object object) throws Exception;
}
