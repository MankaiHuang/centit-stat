package com.centit.stat.controller;

import com.alibaba.fastjson.JSONObject;
import com.centit.framework.common.ResponseData;
import com.centit.framework.core.controller.WrapUpResponseBody;
import com.centit.framework.core.dao.PageQueryResult;
import com.centit.stat.po.ReportModel;
import com.centit.stat.service.ReportService;
import com.centit.support.database.utils.PageDesc;
import com.centit.support.report.JsonDocxContext;
import fr.opensagres.xdocreport.core.XDocReportException;
import fr.opensagres.xdocreport.document.IXDocReport;
import fr.opensagres.xdocreport.document.registry.XDocReportRegistry;
import fr.opensagres.xdocreport.template.IContext;
import fr.opensagres.xdocreport.template.TemplateEngineKind;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;

@Api(value = "报表文书", tags = "报表文书")
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

    @ApiOperation(value = "编辑报表模块")
    @ApiImplicitParam(name = "modelName", value = "图表名称")
    @PutMapping(value = "/{modelName}")
    @WrapUpResponseBody
    public void updateReport(@PathVariable String modelName, @RequestBody ReportModel reportModel){
        reportModel.setModelName(modelName);
        reportService.updateReport(reportModel);
    }

    @ApiOperation(value = "删除报表模块")
    @ApiImplicitParam(name = "modelName", value = "图表名称")
    @DeleteMapping(value = "/{modelName}")
    @WrapUpResponseBody
    public void deleteReport(@PathVariable String modelName){
        reportService.deleteReport(modelName);
    }

    @ApiOperation(value = "查询报表模块")
    @GetMapping
    @WrapUpResponseBody
    public PageQueryResult<ReportModel> listReport(PageDesc pageDesc){
        List<ReportModel> list = reportService.listReport(new HashMap<String, Object>(), pageDesc);
        return PageQueryResult.createResult(list, pageDesc);
    }

    @ApiOperation(value = "查询单个报表模块")
    @GetMapping(value = "/{modelName}")
    @WrapUpResponseBody
    public ReportModel getReport(@PathVariable String modelName){
        ReportModel model = reportService.getReport(modelName);
        return model;
    }

    @ApiOperation(value = "报表文书数据")
    @GetMapping(value = "/data/{modelName}")
    @WrapUpResponseBody()
    public JSONObject reportData(@PathVariable String modelName){
        return reportService.queryData(modelName);
    }

    @ApiOperation(value = "下载报表文书")
    @GetMapping(value = "/download/{modelName}")
    public void downLoadReport(@PathVariable String modelName, HttpServletResponse response){

        JSONObject params = reportService.queryData(modelName);
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
            //
        }
    }

}
