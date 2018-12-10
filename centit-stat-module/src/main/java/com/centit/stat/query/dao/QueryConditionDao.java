package com.centit.stat.query.dao;

import com.centit.framework.jdbc.dao.BaseDaoImpl;
import com.centit.stat.query.po.QueryCondition;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.Map;

@Repository
public class QueryConditionDao extends BaseDaoImpl<QueryCondition, Serializable> {
    @Override
    public Map<String, String> getFilterField() {
        return null;
    }
}
