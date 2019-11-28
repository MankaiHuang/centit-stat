package com.centit.stat.service;

import com.centit.stat.po.ReportModel;

public interface ReportService {
    void createReportModel(ReportModel model);

    ReportModel getReportModel(String reportId);
}
