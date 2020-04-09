package com.Boyce.CrudTemplate.service.serviceImpl;

import com.Boyce.CrudTemplate.service.CrudJdbcService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * @Description
 * @Author Boyce
 * @Date 2020/4/9 13:42
 * @Version V1.0
 */
@Component
public class CrudJdbcServiceImpl implements CrudJdbcService {

    @Autowired
    private JdbcOperations jdbcOperations;

    @Override
    public List<Map<String, Object>> query(Object object) {
        return jdbcOperations.queryForList("select * from test1");
    }
}
