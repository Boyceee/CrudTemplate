package com.boyce.crud.template.mapping;

import com.boyce.crud.template.controller.CrudController;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.AbstractHandlerMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @Description
 * @Author Boyce
 * @Date 2020/4/7 8:50 下午
 */
@Component
public class CrudHandlerMapping extends AbstractHandlerMapping {

    private final static String DISPATCH_METHOD = "dispatch";

    public CrudHandlerMapping() {
        this.setOrder(0);
    }

    @Override
    protected Object getHandlerInternal(HttpServletRequest httpServletRequest) throws Exception {
        for (Map.Entry<String, Object> entry : CrudController.getCrudInterfaces().entrySet()) {
            if (httpServletRequest.getRequestURI().startsWith(entry.getKey())) {
                return CrudController.class.getMethod(DISPATCH_METHOD, HttpServletRequest.class, HttpServletResponse.class);
            }
        }
        return null;
    }
}
