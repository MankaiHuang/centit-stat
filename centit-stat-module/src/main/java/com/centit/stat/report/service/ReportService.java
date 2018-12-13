package com.centit.stat.report.service;

import com.alibaba.fastjson.JSONObject;
import com.centit.stat.report.po.ReportModel;

public interface ReportService {
    void createReportModel(ReportModel model);

    JSONObject queryData(String modelName);
}
