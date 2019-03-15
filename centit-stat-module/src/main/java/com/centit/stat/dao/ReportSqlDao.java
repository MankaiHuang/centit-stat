package com.centit.stat.dao;

import com.centit.framework.jdbc.dao.BaseDaoImpl;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public class ReportSqlDao extends BaseDaoImpl<ReportSql, String> {
    @Override
    public Map<String, String> getFilterField() {
        return null;
    }
}
