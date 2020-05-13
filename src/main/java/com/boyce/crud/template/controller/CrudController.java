package com.boyce.crud.template.controller;

import com.boyce.crud.template.service.AddService;
import com.boyce.crud.template.service.QueryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
    private QueryService queryService;
    @Autowired
    private AddService addService;

    /**
     * add interfaces
     *
     * @param string
     * @param object
     */
    public static void addCrudInterface(String string, Object object) {
        dispatchedInterfaces.put(string, object);
    }

    /**
     * remove interfaces
     *
     * @param string
     * @param object
     */
    public static void removeCrudInterface(String string, Object object) {
        if (!dispatchedInterfaces.remove(string, object)) {
            logger.error("removeCRUDInterface error : There is no such key-value in the Map.");
        }
    }

    /**
     * get interfaces
     *
     * @return all interfaces
     */
    public static Map<String, Object> getCrudInterfaces() {
        return dispatchedInterfaces;
    }

    /**
     * the entrance of the controller
     *
     * @param request
     * @param response
     * @return null
     */
    public ModelAndView dispatch(HttpServletRequest request, HttpServletResponse response) {
        for (Map.Entry<String, Object> entry : dispatchedInterfaces.entrySet()) {
            if (request.getRequestURI().startsWith(entry.getKey() + "/query")) {
                query(entry.getKey(), response);
                break;
            }
            if (request.getRequestURI().startsWith(entry.getKey() + "/add")) {
                add(entry.getKey(), request, response);
                break;
            }
        }
        return null;
    }

    /**
     * do query
     * "/query" interface basically support all of the https's methods.
     *
     * @param key
     * @param response
     */
    public void query(String key, HttpServletResponse response) {
        PrintWriter writer = null;
        try {
            response.setHeader("Content-type", "text/html;charset=UTF-8");
            response.setCharacterEncoding("UTF-8");
            writer = response.getWriter();
            String json = queryService.query(dispatchedInterfaces.get(key));
            writer.print(json);
        } catch (Exception e) {
            writer.println("error:" + e.getMessage());
        } finally {
            writer.flush();
            writer.close();
        }
    }

    /**
     * do insert
     * Up to now,"/add" interface only support the content type of "application/x-www-form-urlencoded" and "application/json".
     * As for http's methods,as long as they supported these two content type,it will be OK.
     *
     * @param key
     * @param request
     * @param response
     */
    public void add(String key, HttpServletRequest request, HttpServletResponse response) {
        PrintWriter writer = null;
        try {
            response.setHeader("Content-type", "text/html;charset=UTF-8");
            response.setCharacterEncoding("UTF-8");
            writer = response.getWriter();
            addService.add(request, dispatchedInterfaces.get(key));
            writer.println("success");
        } catch (Exception e) {
            writer.println("error:" + e.getMessage());
        } finally {
            writer.flush();
            writer.close();
        }
    }
}
