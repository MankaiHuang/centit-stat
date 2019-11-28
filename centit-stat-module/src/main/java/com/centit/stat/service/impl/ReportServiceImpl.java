package com.centit.stat.service.impl;

import com.centit.stat.dao.ReportModelDao;
import com.centit.stat.po.ReportModel;
import com.centit.stat.service.ReportService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReportServiceImpl implements ReportService {

    private Logger logger = LoggerFactory.getLogger("报表文书");

    @Autowired
    private ReportModelDao reportDao;

    @Override
    public void createReportModel(ReportModel model) {

    }

    @Override
    public ReportModel getReportModel(String reportId) {
        return null;
    }
}
