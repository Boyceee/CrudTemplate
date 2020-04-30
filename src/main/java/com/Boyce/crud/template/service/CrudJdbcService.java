package com.Boyce.CrudTemplate.service;

import java.util.List;
import java.util.Map;

/**
 * @Description
 * @Author Boyce
 * @Date 2020/4/9 11:56
 * @Version V1.0
 */
public interface CrudJdbcService {
    public List<Map<String,Object>> query(Object object);
}
