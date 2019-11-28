package com.centit.stat.controller;

import com.centit.framework.common.WebOptUtils;
import com.centit.framework.core.controller.BaseController;
import com.centit.framework.core.controller.WrapUpResponseBody;
import com.centit.framework.core.dao.PageQueryResult;
import com.centit.stat.po.ChartModel;
import com.centit.stat.service.ChartService;
import com.centit.support.database.utils.PageDesc;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.text.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "chart")
@Api(value = "统计图表", tags = "统计图表")
public class ChartController extends BaseController {

    @Autowired
    private ChartService chartService;

    @ApiOperation(value = "新增图表模块")
    @PostMapping
    @WrapUpResponseBody
    public void createChart(ChartModel chartModel, HttpServletRequest request){
        String userCode = WebOptUtils.getCurrentUserCode(request);
        if(StringUtils.isBlank(userCode)){
            userCode="";
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

}
