package com.centit.stat.report.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.centit.framework.common.ResponseData;
import com.centit.stat.report.po.ReportModel;
import com.centit.stat.report.service.ReportService;
import com.centit.support.algorithm.GeneralAlgorithm;
import com.centit.support.report.JsonDocxContext;
import com.centit.support.report.SmartDocxContext;
import com.centit.support.report.WordReportUtil;
import fr.opensagres.xdocreport.core.XDocReportException;
import fr.opensagres.xdocreport.document.IXDocReport;
import fr.opensagres.xdocreport.document.registry.XDocReportRegistry;
import fr.opensagres.xdocreport.template.IContext;
import fr.opensagres.xdocreport.template.TemplateEngineKind;
import fr.opensagres.xdocreport.template.formatter.FieldsMetadata;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

@Api(value = "报表文书")
@RestController
@RequestMapping("report")
public class ReportController {

    @Autowired
    private ReportService reportService;

    @ApiOperation(value = "创建报表文书配置")
    @PostMapping
    public ResponseData createReportModel(ReportModel model){
        reportService.createReportModel(model);
        return ResponseData.makeSuccessResponse();
    }

    @ApiOperation(value = "下载报表文书")
    @GetMapping(value = "/download/{modelName}")
    public void downLoadReport(@PathVariable String modelName, HttpServletResponse response){

        JSONObject params = reportService.queryData(modelName);
        params.put("name", "报表");
        params.put("year", 2018);
        try (InputStream in = new FileInputStream(new File(this.getClass().getClassLoader().getResource("report/report.docx").getPath()))) {
            // 1) Load ODT file and set Velocity template engine and cache it to the registry

            IXDocReport report = XDocReportRegistry.getRegistry().loadReport(in, TemplateEngineKind.Freemarker, false);

            // 2) Create Java model context
            IContext context = new JsonDocxContext(params);
            // 生成新的文件 到响应流
            response.reset();
            response.setContentType("application/x-msdownload");
            response.setHeader("Content-Disposition", "attachment; filename=report.docx");
            report.process(context, response.getOutputStream());
            //XDocReportRegistry.getRegistry().unregisterReport(report);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (XDocReportException e) {
            e.printStackTrace();
        }
    }



}
