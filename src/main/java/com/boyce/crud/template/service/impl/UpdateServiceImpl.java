package com.boyce.crud.template.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.boyce.crud.template.service.UpdateService;
import com.boyce.crud.template.util.ClassUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedInputStream;
import java.util.Map;

/**
 * @author Boyce
 * @version V1.0
 * @date 2020/5/13 17:37
 */
@Service
public class UpdateServiceImpl implements UpdateService {
    static final String CONTENT_TYPE_FORM = "application/x-www-form-urlencoded";
    static final String CONTENT_TYPE_JSON = "application/json";
    static final String FIELD_NAME = "fieldName";
    static final String SQL_COLUMN = "sqlColumn";
    @Autowired
    private JdbcOperations jdbcOperations;

    /**
     * do update
     *
     * @param httpServletRequest
     * @param object
     * @throws Exception
     */
    @Override
    public void update(HttpServletRequest httpServletRequest, Object object) throws Exception {
        Map<String, String> idColumn = ClassUtils.getIdColumn(object);
        if (idColumn.size() == 0) {
            throw new Exception("can not get a field annotated by @Id or named id from the POJO");
        }
        /**
         * hasParameter : when the column and value is null,throw a exception before execute wrong sql.
         */
        boolean hasParameter = false;
        String objectName = object.getClass().getName();
        StringBuilder sql = new StringBuilder();
        StringBuilder set = new StringBuilder();
        StringBuilder where = new StringBuilder();
        sql.append("update ").append(ClassUtils.getTable(object).get(objectName)).append(" set ");
        where.append(" where ").append(idColumn.get(SQL_COLUMN)).append(" = ");
        Map<String, String> fieldsMap = ClassUtils.getFields(object);
        String contentType = httpServletRequest.getContentType();
        if (CONTENT_TYPE_FORM.equals(contentType)) {
            if (StringUtils.isEmpty(httpServletRequest.getParameter(idColumn.get(FIELD_NAME)))) {
                throw new Exception("can not get the primary key,annotated by @id or named id in POJO, from request");
            }
            where.append(httpServletRequest.getParameter(idColumn.get(FIELD_NAME)));
            for (Map.Entry<String, String> entry : fieldsMap.entrySet()) {
                if (!StringUtils.isEmpty(httpServletRequest.getParameter(entry.getKey()))) {
                    hasParameter = true;
                    set.append(",").append(entry.getValue()).append("=").append("\"").append(httpServletRequest.getParameter(entry.getKey())).append("\"");
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
            if (StringUtils.isEmpty(jsonObject.getString(idColumn.get(FIELD_NAME)))) {
                throw new Exception("can not get the primary key,annotated by @id or named id in POJO, from request");
            }
            where.append(jsonObject.getString(idColumn.get(FIELD_NAME)));
            for (Map.Entry<String, String> entry : fieldsMap.entrySet()) {
                if (!StringUtils.isEmpty(jsonObject.getString(entry.getKey()))) {
                    hasParameter = true;
                    set.append(",").append(entry.getValue()).append("=").append("\"").append(jsonObject.getString(entry.getKey())).append("\"");
                }
            }
        } else {
            throw new Exception("the contentType of :" + contentType + " have not been supported");
        }
        if (hasParameter) {
            sql.append(set.substring(1)).append(where);
            System.out.println("UpdateServiceImpl execute sql:" + sql);
            jdbcOperations.execute(sql.toString());
        } else {
            throw new Exception("parameters have not been accepted");
        }
    }
}
