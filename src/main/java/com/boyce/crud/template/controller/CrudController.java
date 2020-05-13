package com.boyce.crud.template.controller;

import com.boyce.crud.template.service.CrudJdbcService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Boyce
 * @version V1.0
 * @date 2020/4/7 21:18
 */
@Component
public class CrudController {

    private static final Logger logger = LoggerFactory.getLogger(CrudController.class);
    private static Map<String, Object> dispatchedInterfaces = new HashMap<>();
    @Autowired
    private CrudJdbcService crudJdbcService;

    /**
     * add interfaces
     *
     * @param string
     * @param object
     * @return void
     */
    public static void addCrudInterface(String string, Object object) {
        dispatchedInterfaces.put(string, object);
    }

    /**
     * remove interfaces
     *
     * @param string
     * @param object
     * @return void
     */
    public static void removeCrudInterface(String string, Object object) {
        if (!dispatchedInterfaces.remove(string, object)) {
            logger.error("removeCRUDInterface error : There is no such key-value in the Map.");
        }
    }

    /**
     * get interfaces
     *
     * @param
     * @return java.util.Map<java.lang.String, java.lang.Object>
     */
    public static Map<String, Object> getCrudInterfaces() {
        return dispatchedInterfaces;
    }

    /**
     * the entrance of the controller
     *
     * @param request
     * @param response
     * @return org.springframework.web.servlet.ModelAndView
     */
    public ModelAndView dispatch(HttpServletRequest request, HttpServletResponse response) {
        try {
            PrintWriter writer = response.getWriter();
            String json = "";
            for (Map.Entry<String, Object> entry : dispatchedInterfaces.entrySet()) {
                if (request.getRequestURI().startsWith(entry.getKey())) {
                    json = crudJdbcService.query(dispatchedInterfaces.get(entry.getKey()));
                    break;
                }
            }
            writer.print(json);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
        return null;
    }
}
