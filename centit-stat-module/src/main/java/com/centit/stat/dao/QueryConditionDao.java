package com.centit.stat.dao;

import com.centit.framework.jdbc.dao.BaseDaoImpl;
import com.centit.stat.po.QueryCondition;
import com.centit.stat.po.QueryConditionId;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public class QueryConditionDao extends BaseDaoImpl<QueryCondition, QueryConditionId> {
    @Override
    public Map<String, String> getFilterField() {
        return null;
    }
}
