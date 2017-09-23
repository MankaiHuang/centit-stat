package com.centit.support.report;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by codefan on 17-9-23.
 */
public class TestDocReport {

    public static void main(String[] args) throws Exception {
        Map<String, Object> reportData = new HashMap<>();
        reportData.put("name", "张三");
        reportData.put("unitName", "江苏南大先腾信息产业股份有限公司");
        WordReportUtil.reportDocxWithFreemarker(reportData, "/D/Projects/RunData/demo_home/temp/XDocReport.docx",
                "/D/Projects/RunData/demo_home/temp/XDocReport2.docx");
    }

}
