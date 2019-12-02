package com.centit.stat.service;

import com.centit.stat.po.ChartModel;
import com.centit.stat.po.ReportModel;
import com.centit.support.database.utils.PageDesc;

import java.util.List;
import java.util.Map;

public interface ReportService {
    void createReportModel(ReportModel model);

    ReportModel getReportModel(String reportId);

    List<ReportModel> listReportModel(Map<String, Object> param, PageDesc pageDesc);

    void deleteReportModel(String reportId);

    void updateReportModel(ReportModel reportModel);
}
