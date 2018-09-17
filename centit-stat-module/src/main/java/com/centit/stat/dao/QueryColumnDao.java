package com.centit.stat.dao;

import com.centit.framework.jdbc.dao.BaseDaoImpl;
import com.centit.stat.po.QueryColumn;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.Map;

@Repository
public class QueryColumnDao extends BaseDaoImpl<QueryColumn, Serializable> {
    @Override
    public Map<String, String> getFilterField() {
        return null;
    }
}
