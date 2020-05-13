package com.boyce.crud.template.mapping;

import com.boyce.crud.template.controller.CrudController;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.AbstractHandlerMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @author Boyce
 * @version V1.0
 * @date 2020/4/7 20:50
 */
@Component
public class CrudHandlerMapping extends AbstractHandlerMapping {

    private final static String DISPATCH_METHOD = "dispatch";

    /**
     * put this HandlerMapping at the top of the priority list when initializing
     */
    public CrudHandlerMapping() {
        this.setOrder(0);
    }

    /**
     * override the get Handler method to match CrudController's dispatch
     *
     * @param httpServletRequest
     * @return java.lang.Object
     */
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
