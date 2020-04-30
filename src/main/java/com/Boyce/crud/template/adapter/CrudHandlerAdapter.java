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
 * @Description
 * @Author Boyce
 * @Date 2020/4/7 8:52 下午
 */
@Component
public class CrudHandlerAdapter implements HandlerAdapter {
    @Autowired
    private CrudController crudController;
    private final static String DISPATCH_METHOD = "dispatch";

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
