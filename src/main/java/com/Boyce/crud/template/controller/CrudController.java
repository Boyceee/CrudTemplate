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
 * @Description
 * @Author Boyce
 * @Date 2020/4/7 9:18 下午
 */
@Component
public class CrudController {

    private static final Logger logger = LoggerFactory.getLogger(CrudController.class);
    private static Map<String, Object> dispatchedInterfaces = new HashMap<>();
    @Autowired
    private CrudJdbcService crudJdbcService;

    public static void addCrudInterface(String string, Object object) {
        dispatchedInterfaces.put(string, object);
    }

    public static void removeCrudInterface(String string, Object object) {
        if (!dispatchedInterfaces.remove(string, object)) {
            logger.error("removeCRUDInterface error : There is no such key-value in the Map.");
        }
    }

    public static Map<String, Object> getCrudInterfaces() {
        return dispatchedInterfaces;
    }

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
