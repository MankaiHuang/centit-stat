package com.centit.support.report;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by codefan on 17-9-23.
 */
public class TestDocReport {

    public static void main(String[] args) throws Exception {
//       Map<String, Object> reportData = new HashMap<>();
//        reportData.put("name", "赵四");
//        reportData.put("unitName", "江苏南大先腾信息产业股份有限公司");
//        WordReportUtil.reportDocxWithFreemarker(reportData, "/D/Projects/RunData/demo_home/temp/XDocReport.docx",
//                "/D/Projects/RunData/demo_home/temp/XDocReport2.docx");

//        InputStream in = new FileInputStream(new File("C:\\Users\\zhang_gd\\Desktop\\sola\\testTypeFile\\read\\testE2003.xls"));
//        List<String[]> list = ExcelImportUtil.loadDataFromExcel(in,null,1,5);

        OutputStream out = new FileOutputStream("C:\\\\Users\\\\zhang_gd\\\\Desktop\\\\sola\\\\testTypeFile\\\\read\\\\5555.xls");
        List<Object> list = new ArrayList<>();
        list.add("aa");
        list.add("bb");
        list.add("cc");
        list.add("dd");
       // ExcelExportUtil.get(out,list, new String[]{"显示","第二行"}, new String[]{});

    }

}
