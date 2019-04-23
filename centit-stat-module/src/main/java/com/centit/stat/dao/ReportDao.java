package com.centit.stat.dao;

import com.centit.framework.jdbc.dao.BaseDaoImpl;
import com.centit.stat.po.ReportModel;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public class ReportDao extends BaseDaoImpl<ReportModel, String> {
    @Override
    public Map<String, String> getFilterField() {
        return null;
    }

}
