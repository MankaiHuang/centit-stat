package com.centit.stat.dao;

import com.centit.framework.jdbc.dao.BaseDaoImpl;
import com.centit.stat.po.ChartModel;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public class ChartModelDao extends BaseDaoImpl<ChartModel, String> {


    @Override
    public Map<String, String> getFilterField() {
        return null;
    }
}
