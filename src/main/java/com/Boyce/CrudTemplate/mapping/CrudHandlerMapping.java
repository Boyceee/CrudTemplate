package com.Boyce.CrudTemplate.mapping;

import com.Boyce.CrudTemplate.controller.CrudController;
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

    public CrudHandlerMapping() {
        this.setOrder(0);
    }

    @Override
    protected Object getHandlerInternal(HttpServletRequest httpServletRequest) throws Exception {
        for (Map.Entry<String, Object> entry : CrudController.getCRUDInterfaces().entrySet()) {
            if (httpServletRequest.getRequestURI().startsWith(entry.getKey())) {
                return CrudController.class.getMethod("dispatch", HttpServletRequest.class, HttpServletResponse.class);
            }
        }
        return null;
    }
}
