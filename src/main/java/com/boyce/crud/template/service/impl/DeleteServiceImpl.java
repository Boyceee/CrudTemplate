package com.boyce.crud.template.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.boyce.crud.template.service.DeleteService;
import com.boyce.crud.template.util.ClassUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
 * @date 2020/5/22 13:25
 */
@Service
public class DeleteServiceImpl implements DeleteService {
    private static final Logger logger = LoggerFactory.getLogger(DeleteServiceImpl.class);
    static final String CONTENT_TYPE_FORM = "application/x-www-form-urlencoded";
    static final String CONTENT_TYPE_JSON = "application/json";
    static final String FIELD_NAME = "fieldName";
    static final String SQL_COLUMN = "sqlColumn";
    @Autowired
    private JdbcOperations jdbcOperations;

    /**
     * do delete
     *
     * @param httpServletRequest
     * @param object
     * @throws Exception
     */
    @Override
    public void delete(HttpServletRequest httpServletRequest, Object object) throws Exception {
        Map<String, String> idColumn = ClassUtils.getIdColumn(object);
        if (idColumn.size() == 0) {
            logger.error("can not get a field annotated by @Id or named id from the POJO");
            throw new Exception("can not get a field annotated by @Id or named id from the POJO");
        }
        String objectName = object.getClass().getName();
        StringBuilder sql = new StringBuilder();
        StringBuilder where = new StringBuilder();
        sql.append("delete from ").append(ClassUtils.getTable(object).get(objectName));
        where.append(" where ").append(idColumn.get(SQL_COLUMN)).append(" = ");
        Map<String, String> fieldsMap = ClassUtils.getFields(object);
        String contentType = httpServletRequest.getContentType();
        if (CONTENT_TYPE_FORM.equals(contentType)) {
            if (StringUtils.isEmpty(httpServletRequest.getParameter(idColumn.get(FIELD_NAME)))) {
                logger.error("can not get the primary key,annotated by @id or named id in POJO, from request");
                throw new Exception("can not get the primary key,annotated by @id or named id in POJO, from request");
            }
            where.append(httpServletRequest.getParameter(idColumn.get(FIELD_NAME)));
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
                logger.error("can not get the primary key,annotated by @id or named id in POJO, from request");
                throw new Exception("can not get the primary key,annotated by @id or named id in POJO, from request");
            }
            where.append(jsonObject.getString(idColumn.get(FIELD_NAME)));
        } else {
            logger.error("the contentType of :" + contentType + " have not been supported");
            throw new Exception("the contentType of :" + contentType + " have not been supported");
        }
        sql.append(where);
        logger.info("execute sql:" + sql);
        jdbcOperations.execute(sql.toString());
    }
}
