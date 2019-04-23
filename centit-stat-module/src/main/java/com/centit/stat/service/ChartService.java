package com.centit.stat.service;

import com.centit.stat.po.ChartModel;
import com.centit.support.database.utils.PageDesc;

import java.util.List;
import java.util.Map;

public interface ChartService {

    void createChartModel(ChartModel chartModel);

    List<ChartModel> listChart(Map<String, Object> param, PageDesc pageDesc);

    ChartModel getChart(String chartId);

    void deleteChart(String chartId);

    void updateChart(ChartModel chartModel);
}
