package com.centit.stat.chart.dao;

import com.centit.framework.jdbc.dao.BaseDaoImpl;
import com.centit.stat.chart.po.ChartModel;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public class ChartModelDao extends BaseDaoImpl<ChartModel, String> {


    @Override
    public Map<String, String> getFilterField() {
        return null;
    }
}
