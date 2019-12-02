package com.centit.stat.controller;

import com.alibaba.fastjson.JSONObject;
import com.centit.framework.common.ResponseData;
import com.centit.framework.core.controller.BaseController;
import com.centit.framework.core.controller.WrapUpResponseBody;
import com.centit.framework.core.dao.PageQueryResult;
import com.centit.product.dataopt.core.BizModel;
import com.centit.product.datapacket.service.DataPacketService;
import com.centit.stat.po.ChartModel;
import com.centit.stat.po.ReportModel;
import com.centit.stat.service.ReportService;
import com.centit.support.common.ObjectException;
import com.centit.support.compiler.Pretreatment;
import com.centit.support.database.utils.PageDesc;
import com.centit.support.file.FileType;
import com.centit.support.report.JsonDocxContext;
import fr.opensagres.xdocreport.core.XDocReportException;
import fr.opensagres.xdocreport.document.IXDocReport;
import fr.opensagres.xdocreport.document.registry.XDocReportRegistry;
import fr.opensagres.xdocreport.template.IContext;
import fr.opensagres.xdocreport.template.TemplateEngineKind;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.text.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

@Api(value = "报表文书", tags = "报表文书")
@RestController
@RequestMapping("report")
public class ReportController {

    @Autowired
    private ReportService reportService;

    @Autowired
    private DataPacketService dataPacketService;

    @ApiOperation(value = "创建报表文书配置")
    @PostMapping
    public ResponseData createReportModel(ReportModel model){
        reportService.createReportModel(model);
        return ResponseData.makeSuccessResponse();
    }
    @ApiOperation(value = "修改报表文书配置")
    @ApiImplicitParam(name = "reportId", value = "文书ID")
    @PutMapping(value = "/{reportId}")
    @WrapUpResponseBody
    public void updateReport(@PathVariable String reportId, @RequestBody ReportModel reportModel){
        reportModel.setReportId(reportId);
        reportService.updateReportModel(reportModel);
    }

    @ApiOperation(value = "删除报表文书配置")
    @ApiImplicitParam(name = "reportId", value = "文书ID")
    @DeleteMapping(value = "/{reportId}")
    @WrapUpResponseBody
    public void deleteReport(@PathVariable String reportId){
        reportService.deleteReportModel(reportId);
    }

    @ApiOperation(value = "查询报表文书配置")
    @GetMapping
    @WrapUpResponseBody
    public PageQueryResult<ReportModel> listChart(HttpServletRequest request, PageDesc pageDesc){
        Map<String, Object> searchColumn = BaseController.collectRequestParameters(request);
        List<ReportModel> list = reportService.listReportModel(searchColumn, pageDesc);
        return PageQueryResult.createResult(list, pageDesc);
    }
    @ApiOperation(value = "报表文书数据")
    @GetMapping(value = "/data/{reportId}")
    @WrapUpResponseBody()
    public BizModel reportData(@PathVariable String reportId, HttpServletRequest request){
        Map<String, Object> params = BaseController.collectRequestParameters(request);
        ReportModel reportModel = reportService.getReportModel(reportId);
        return dataPacketService.fetchDataPacketData(reportModel.getPacketId(), params);
    }
    @ApiOperation(value = "下载报表文书")
    @GetMapping(value = "/download/{reportId}")
    public void downLoadReport(@PathVariable String reportId, HttpServletRequest request, HttpServletResponse response){
        Map<String, Object> params = BaseController.collectRequestParameters(request);
        ReportModel reportModel = reportService.getReportModel(reportId);
        BizModel dataModel = dataPacketService.fetchDataPacketData(reportModel.getPacketId(), params);
        JSONObject docData = dataModel.toJSONObject(true);
        docData.put("queryParams", params);
        try (InputStream in = new FileInputStream(new File(this.getClass().getClassLoader().getResource("report/report.docx").getPath()))) {
            // 1) Load ODT file and set Velocity template engine and cache it to the registry
            IXDocReport report = XDocReportRegistry.getRegistry().loadReport(in, TemplateEngineKind.Freemarker, false);
            // 2) Create Java model context
            IContext context = new JsonDocxContext(docData);
            // 生成新的文件 到响应流
            String fileName = URLEncoder.encode(
                Pretreatment.mapTemplateString(reportModel.getReportNameFormat(),params), "UTF-8") + ".docx";
            response.reset();
            response.setContentType(FileType.mapExtNameToMimeType("docx"));
            response.setHeader("Content-disposition", "attachment; filename=" + fileName);
            report.process(context, response.getOutputStream());
            //XDocReportRegistry.getRegistry().unregisterReport(report);
        } catch (IOException | XDocReportException e) {
            throw new ObjectException(ResponseData.ERROR_PROCESS_FAILED, e.getMessage());
        }
    }


}
