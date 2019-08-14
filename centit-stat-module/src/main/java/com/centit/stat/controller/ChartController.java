package com.centit.stat.controller;

import com.centit.framework.common.ObjectException;
import com.centit.framework.common.WebOptUtils;
import com.centit.framework.core.controller.BaseController;
import com.centit.framework.core.controller.WrapUpResponseBody;
import com.centit.framework.core.dao.PageQueryResult;
import com.centit.product.dataopt.core.BizModel;
import com.centit.product.datapacket.service.DataPacketService;
import com.centit.stat.po.ChartModel;
import com.centit.stat.service.ChartService;
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
import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "chart")
@Api(value = "统计图表", tags = "统计图表")
public class ChartController extends BaseController {

    @Autowired
    private ChartService chartService;

    @Autowired
    private DataPacketService dataPacketService;

    @ApiOperation(value = "新增图表模块")
    @PostMapping
    @WrapUpResponseBody
    public void createChart(ChartModel chartModel, HttpServletRequest request){
        String userCode = WebOptUtils.getCurrentUserCode(request);
        if(StringUtils.isBlank(userCode)){
            throw new ObjectException("未登录");
        }
        chartModel.setRecorder(userCode);
        chartModel.setChartDesignJson(StringEscapeUtils.unescapeHtml4(chartModel.getChartDesignJson()));
        chartService.createChartModel(chartModel);
    }

    @ApiOperation(value = "修改图表模块")
    @ApiImplicitParam(name = "chartId", value = "图表ID")
    @PutMapping(value = "/{chartId}")
    @WrapUpResponseBody
    public void updateChart(@PathVariable String chartId, @RequestBody ChartModel chartModel){
        chartModel.setChartId(chartId);
        chartModel.setChartDesignJson(StringEscapeUtils.unescapeHtml4(chartModel.getChartDesignJson()));
        chartService.updateChart(chartModel);
    }

    @ApiOperation(value = "删除图表模块")
    @ApiImplicitParam(name = "chartId", value = "图表ID")
    @DeleteMapping(value = "/{chartId}")
    @WrapUpResponseBody
    public void deleteChart(@PathVariable String chartId){
        chartService.deleteChart(chartId);
    }

    @ApiOperation(value = "查询图表模块")
    @GetMapping
    @WrapUpResponseBody
    public PageQueryResult<ChartModel> listChart(HttpServletRequest request,PageDesc pageDesc){
        Map<String, Object> searchColumn = collectRequestParameters(request);
        List<ChartModel> list = chartService.listChart(searchColumn, pageDesc);
        return PageQueryResult.createResult(list, pageDesc);
    }

    @ApiOperation(value = "查询单个图表模块")
    @GetMapping(value = "/{chartId}")
    @WrapUpResponseBody
    public ChartModel getChart(@PathVariable String chartId){
        ChartModel chart = chartService.getChart(chartId);
        return chart;
    }

    @ApiOperation(value = "报表文书数据")
    @GetMapping(value = "/data/{chartId}")
    @WrapUpResponseBody()
    public BizModel reportData(@PathVariable String chartId, HttpServletRequest request){
        Map<String, Object> params = BaseController.collectRequestParameters(request);
        ChartModel chart = chartService.getChart(chartId);
        return dataPacketService.fetchDataPacketData(chart.getPacketId(), params);
    }

    @ApiOperation(value = "下载报表文书")
    @GetMapping(value = "/download/{chartId}")
    public void downLoadReport(@PathVariable String chartId, HttpServletRequest request, HttpServletResponse response){
        Map<String, Object> params = BaseController.collectRequestParameters(request);
        ChartModel chart = chartService.getChart(chartId);
        BizModel dataModel = dataPacketService.fetchDataPacketData(chart.getPacketId(), params);

        try (InputStream in = new FileInputStream(new File(this.getClass().getClassLoader().getResource("report/report.docx").getPath()))) {
            // 1) Load ODT file and set Velocity template engine and cache it to the registry
            IXDocReport report = XDocReportRegistry.getRegistry().loadReport(in, TemplateEngineKind.Freemarker, false);
            // 2) Create Java model context
            IContext context = new JsonDocxContext(dataModel);
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
