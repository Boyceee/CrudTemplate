package com.boyce.crud.template.adapter;

import com.boyce.crud.template.controller.CrudController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerAdapter;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * @description
 * @author Boyce
 * @date 2020/4/7 20:52
 * @version V1.0
 */
@Component
public class CrudHandlerAdapter implements HandlerAdapter {
    @Autowired
    private CrudController crudController;
    private final static String DISPATCH_METHOD = "dispatch";

    /**
     * @param o
     * @return boolean
     * @description override the supports method to match CrudController's dispatch,
     * so it will handle the Handler later
     **/
    @Override
    public boolean supports(Object o) {
        try {
            if (o.equals(CrudController.class.getMethod(DISPATCH_METHOD, HttpServletRequest.class, HttpServletResponse.class))) {
                return true;
            }
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * @param httpServletRequest
     * @param httpServletResponse
     * @param o
     * @return org.springframework.web.servlet.ModelAndView
     * @description handle the Handler,by invoking the method
     **/
    @Override
    public ModelAndView handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        Method method = (Method) o;
        return (ModelAndView) method.invoke(crudController, httpServletRequest, httpServletResponse);
    }

    @Override
    public long getLastModified(HttpServletRequest httpServletRequest, Object o) {
        return 0;
    }
}
