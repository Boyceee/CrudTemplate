package com.boyce.crud.template.controller;

import com.boyce.crud.template.service.AddService;
import com.boyce.crud.template.service.DeleteService;
import com.boyce.crud.template.service.QueryService;
import com.boyce.crud.template.service.UpdateService;
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
    private static Map<String, Object> dispatchedInterfaces = new HashMap<>();
    @Autowired
    private QueryService queryService;
    @Autowired
    private AddService addService;
    @Autowired
    private UpdateService updateService;
    @Autowired
    private DeleteService deleteService;

    /**
     * add a interface
     *
     * @param string
     * @param object
     */
    public static void addCrudInterface(String string, Object object) {
        dispatchedInterfaces.put(string, object);
    }

    /**
     * remove a interface
     *
     * @param string
     * @param object
     */
    public static void removeCrudInterface(String string, Object object) throws Exception{
        if (!dispatchedInterfaces.remove(string, object)) {
            throw new Exception("removeCRUDInterface error : There is no such key-value in the Map.");
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
            if (request.getRequestURI().startsWith(entry.getKey() + "/update")) {
                update(entry.getKey(), request, response);
                break;
            }
            if (request.getRequestURI().startsWith(entry.getKey() + "/delete")) {
                delete(entry.getKey(), request, response);
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

    /**
     * do update
     * Up to now,"/update" interface only support the content type of "application/x-www-form-urlencoded" and "application/json".
     * As for http's methods,as long as they supported these two content type,it will be OK.
     *
     * @param key
     * @param request
     * @param response
     */
    public void update(String key, HttpServletRequest request, HttpServletResponse response) {
        PrintWriter writer = null;
        try {
            response.setHeader("Content-type", "text/html;charset=UTF-8");
            response.setCharacterEncoding("UTF-8");
            writer = response.getWriter();
            updateService.update(request, dispatchedInterfaces.get(key));
            writer.println("success");
        } catch (Exception e) {
            writer.println("error:" + e.getMessage());
        } finally {
            writer.flush();
            writer.close();
        }
    }

    /**
     * do delete
     * Up to now,"/delete" interface only support the content type of "application/x-www-form-urlencoded" and "application/json".
     * As for http's methods,as long as they supported these two content type,it will be OK.
     *
     * @param key
     * @param request
     * @param response
     */
    public void delete(String key, HttpServletRequest request, HttpServletResponse response) {
        PrintWriter writer = null;
        try {
            response.setHeader("Content-type", "text/html;charset=UTF-8");
            response.setCharacterEncoding("UTF-8");
            writer = response.getWriter();
            deleteService.delete(request, dispatchedInterfaces.get(key));
            writer.println("success");
        } catch (Exception e) {
            writer.println("error:" + e.getMessage());
        } finally {
            writer.flush();
            writer.close();
        }
    }
}
