package com.centit.stat.list.controller;

import com.centit.framework.common.ResponseData;
import com.centit.framework.core.controller.BaseController;
import com.centit.stat.list.po.ListData;
import com.centit.stat.list.po.ListModel;
import com.centit.stat.list.service.ListModelService;
import com.centit.support.database.utils.PageDesc;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * 列表模块
 * Q_LIST_Condition;
 * Q_LIST_Model;
 * Q_List_Column;
 * Q_List_OPERATOR;
 */
@Api(value = "列表模块配置", tags = "列表模块配置")
@RestController
@RequestMapping("list_model")
public class ListModelController extends BaseController {

    @Autowired
    private ListModelService listModelService;

    /**
     * 新增模块
     */
    @ApiOperation(value = "新增模块")
    @PostMapping
    public ResponseData createListModel(ListModel listModel, HttpServletResponse response){
        listModelService.creteListModel(listModel);
        return ResponseData.makeSuccessResponse();
    }

    /**
     * 编辑模块
     */
    @ApiOperation(value = "编辑模块")
    @PutMapping
    public ResponseData updateListModel(ListModel listModel, HttpServletResponse response){
        listModelService.updateListModel(listModel);
        return ResponseData.makeSuccessResponse();
    }

    /**
     * 删除模块
     */
    @ApiOperation(value = "删除模块")
    @DeleteMapping(value = "/{modelName}")
    public ResponseData deleteListModel(@PathVariable String modelName, HttpServletResponse response){
        listModelService.deleteListModel(modelName);
        return ResponseData.makeSuccessResponse();
    }

    /**
     * 查询单个模块
     */
    @ApiOperation(value = "查询单个模块")
    @GetMapping(value = "/{modelName}")
    public ResponseData getListModel(@PathVariable String modelName, HttpServletResponse response){
        ListModel model = listModelService.getListModel(modelName);
        return ResponseData.makeResponseData(model);
    }

    /**
     * 生成列表
     */
    @ApiOperation(value = "生成列表")
    @GetMapping(value = "/list_data/{modelName}")
    public ListData generateList(@PathVariable String modelName, PageDesc pageDesc,
                                 HttpServletRequest request){
        Map<String, Object> params = convertSearchColumn(request);
        ListData data = listModelService.initList(modelName);
        listModelService.loadData(data, params, pageDesc);
        return data;
    }
}
