package com.boyce.crud.template.mapping;

import com.boyce.crud.template.controller.CrudController;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.AbstractHandlerMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @description
 * @author Boyce
 * @date 2020/4/7 20:50
 * @version V1.0
 */
@Component
public class CrudHandlerMapping extends AbstractHandlerMapping {

    private final static String DISPATCH_METHOD = "dispatch";

    /**
     * @description put this HandlerMapping at the top of the priority list when initializing
     **/
    public CrudHandlerMapping() {
        this.setOrder(0);
    }

    /**
     * @param httpServletRequest
     * @return java.lang.Object
     * @description override the get Handler method to match CrudController's dispatch
     **/
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
