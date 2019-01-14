package com.centit.stat.chart.service.impl;

import com.centit.stat.chart.dao.ChartModelDao;
import com.centit.stat.chart.po.ChartModel;
import com.centit.stat.chart.service.ChartService;
import com.centit.support.database.utils.PageDesc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional
public class ChartServiceImpl implements ChartService {

    @Autowired
    private ChartModelDao chartModelDao;

    @Override
    public void createChartModel(ChartModel chartModel) {
        chartModelDao.saveNewObject(chartModel);
    }

    @Override
    public List<ChartModel> listChart(Map<String, Object> param, PageDesc pageDesc) {
        return chartModelDao.listObjectsByProperties(param, pageDesc);
    }

    @Override
    public ChartModel getChart(String chartId) {
        return chartModelDao.getObjectById(chartId);
    }

    @Override
    public void deleteChart(String chartId) {
        chartModelDao.deleteObjectById(chartId);
    }

    @Override
    public void updateChart(ChartModel chartModel) {
        chartModelDao.updateObject(chartModel);
    }
}
