package com.boyce.crud.template.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.boyce.crud.template.service.AddService;
import com.boyce.crud.template.util.ClassUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedInputStream;
import java.util.Map;

/**
 * @author Boyce
 * @version V1.0
 * @date 2020/5/9 17:20
 */
@Service
public class AddServiceImpl implements AddService {
    private static final Logger logger = LoggerFactory.getLogger(AddServiceImpl.class);
    static final String CONTENT_TYPE_FORM = "application/x-www-form-urlencoded";
    static final String CONTENT_TYPE_JSON = "application/json";
    @Autowired
    private JdbcOperations jdbcOperations;

    /**
     * do insert
     *
     * @param httpServletRequest
     * @param object
     * @throws Exception
     */
    @Override
    public void add(HttpServletRequest httpServletRequest, Object object) throws Exception {
        //hasParameter:when the column and value is null,throw a exception before execute wrong sql.
        boolean hasParameter = false;
        String objectName = object.getClass().getName();
        StringBuilder sql = new StringBuilder();
        StringBuilder column = new StringBuilder();
        StringBuilder value = new StringBuilder();
        Map<String, String> fieldsMap = ClassUtils.getFields(object);
        String contentType = httpServletRequest.getContentType();
        String method = httpServletRequest.getMethod();
        sql.append("insert into ").append(ClassUtils.getTable(object).get(objectName));
        if (CONTENT_TYPE_FORM.equals(contentType)) {
            for (Map.Entry<String, String> entry : fieldsMap.entrySet()) {
                if (null != httpServletRequest.getParameter(entry.getKey())) {
                    hasParameter = true;
                    column.append(",").append(entry.getValue());
                    value.append(",").append("\"").append(httpServletRequest.getParameter(entry.getKey())).append("\"");
                }
            }
        } else if (CONTENT_TYPE_JSON.equals(contentType)) {
            BufferedInputStream inputStream = new BufferedInputStream(httpServletRequest.getInputStream());
            StringBuilder json = new StringBuilder();
            byte[] buffer = new byte[1024];
            int length;
            while ((length = inputStream.read(buffer)) != -1) {
                json.append(new String(buffer, 0, length));
            }
            JSONObject jsonObject = (JSONObject) JSONObject.parse(json.toString());
            for (Map.Entry<String, String> entry : fieldsMap.entrySet()) {
                if (null != jsonObject.getString(entry.getKey())) {
                    hasParameter = true;
                    column.append(",").append(entry.getValue());
                    value.append(",").append("\"").append(jsonObject.getString(entry.getKey())).append("\"");
                }
            }
        } else {
            logger.error("the contentType of :" + contentType + " have not been supported");
            throw new Exception("the contentType of :" + contentType + " have not been supported");
        }
        if (hasParameter) {
            column = new StringBuilder(column.substring(1));
            column.insert(0, "(").append(")");
            value = new StringBuilder(value.substring(1));
            value.insert(0, "(").append(")");
            sql.append(" ").append(column).append(" values ").append(value);
            logger.info("execute sql:" + sql);
            jdbcOperations.execute(sql.toString());
        } else {
            logger.error("parameters have not been accepted");
            throw new Exception("parameters have not been accepted");
        }
    }
}
