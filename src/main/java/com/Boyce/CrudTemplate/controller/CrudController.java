package com.Boyce.CrudTemplate.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description
 * @Author Boyce
 * @Date 2020/4/7 9:18 下午
 */
public class CrudController {

    private static Logger logger = LoggerFactory.getLogger(CrudController.class);
    private static Map<String, Object> dispatchedInterfaces = new HashMap<>();

    public static void addCRUDInterface(String string, Object object) {
        dispatchedInterfaces.put(string, object);
    }

    public static void removeCRUDInterface(String string, Object object) {
        if (!dispatchedInterfaces.remove(string, object)) {
            logger.error("removeCRUDInterface error : There is no such key-value in the Map.");
        }
    }

    public static Map<String, Object> getCRUDInterfaces() {
        return dispatchedInterfaces;
    }

    public ModelAndView dispatch( HttpServletRequest request, HttpServletResponse response) throws IOException {
        PrintWriter writer = response.getWriter();
        writer.print("{\"data\" : \"test\"}");
        writer.flush();
        writer.close();
        return null;
    }
}
