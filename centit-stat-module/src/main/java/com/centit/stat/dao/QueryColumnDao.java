package com.centit.stat.dao;

import com.centit.framework.jdbc.dao.BaseDaoImpl;
import com.centit.stat.po.QueryColumn;
import com.centit.stat.po.QueryColumnId;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public class QueryColumnDao extends BaseDaoImpl<QueryColumn, QueryColumnId> {
    @Override
    public Map<String, String> getFilterField() {
        return null;
    }
}
