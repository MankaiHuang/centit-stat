package com.centit.stat.service;

import com.alibaba.fastjson.JSONObject;
import com.centit.stat.po.ReportModel;

public interface ReportService {
    void createReportModel(ReportModel model);

    JSONObject queryData(String modelName);
}
