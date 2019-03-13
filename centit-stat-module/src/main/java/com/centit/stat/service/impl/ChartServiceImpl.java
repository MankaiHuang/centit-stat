package com.centit.stat.service.impl;

import com.centit.stat.dao.ChartModelDao;
import com.centit.stat.po.ChartModel;
import com.centit.stat.service.ChartService;
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
        chartModelDao.saveObjectReferences(chartModel);
    }

    @Override
    public List<ChartModel> listChart(Map<String, Object> param, PageDesc pageDesc) {
        return chartModelDao.listObjectsByProperties(param, pageDesc);
    }

    @Override
    public ChartModel getChart(String chartId) {
        return chartModelDao.getObjectWithReferences(chartId);
    }

    @Override
    public void deleteChart(String chartId) {
        chartModelDao.deleteObjectById(chartId);
    }

    @Override
    public void updateChart(ChartModel chartModel) {
        chartModelDao.updateObject(chartModel);
        chartModelDao.saveObjectReferences(chartModel);
    }
}
