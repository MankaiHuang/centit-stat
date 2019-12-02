package com.centit.stat.service.impl;

import com.centit.stat.dao.ReportModelDao;
import com.centit.stat.po.ReportModel;
import com.centit.stat.service.ReportService;
import com.centit.support.database.utils.PageDesc;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ReportServiceImpl implements ReportService {

    private Logger logger = LoggerFactory.getLogger("报表文书");

    @Autowired
    private ReportModelDao reportDao;

    @Override
    public void createReportModel(ReportModel model) {
        reportDao.saveNewObject(model);
    }

    @Override
    public ReportModel getReportModel(String reportId) {
        return reportDao.getObjectById(reportId);
    }

    @Override
    public List<ReportModel> listReportModel(Map<String, Object> param, PageDesc pageDesc) {
        return reportDao.listObjectsByProperties(param,pageDesc);
    }

    @Override
    public void deleteReportModel(String reportId) {
        reportDao.deleteObjectById(reportId);
    }

    @Override
    public void updateReportModel(ReportModel reportModel) {
        reportDao.updateObject(reportModel);
    }
}
