package com.centit.stat.service;

import com.alibaba.fastjson.JSONObject;
import com.centit.stat.po.ChartModel;
import com.centit.stat.po.ReportModel;
import com.centit.support.database.utils.PageDesc;

import java.util.List;
import java.util.Map;

public interface ReportService {
    JSONObject queryData(String modelName);
    
    void createReportModel(ReportModel model);

    List<ReportModel> listReport(Map<String, Object> param, PageDesc pageDesc);

    ReportModel getReport(String modelName);

    void updateReport(ReportModel model);

    void deleteReport(String modelName);
}
