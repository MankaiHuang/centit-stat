package com.centit.stat.list.controller;

import com.centit.framework.common.JsonResultUtils;
import com.centit.framework.core.controller.BaseController;
import com.centit.stat.list.po.ListModel;
import com.centit.stat.list.service.ListModelService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

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
    public void createListModel(ListModel listModel, HttpServletResponse response){
        listModelService.creteListModel(listModel);
        JsonResultUtils.writeSuccessJson(response);
    }

    /**
     * 编辑模块
     */
    @ApiOperation(value = "编辑模块")
    @PutMapping
    public void updateListModel(ListModel listModel){

    }

    /**
     * 删除模块
     */
    @ApiOperation(value = "删除模块")
    @DeleteMapping(value = "/{modelName}")
    public void deleteListModel(@PathVariable String modelName){

    }

    /**
     * 查询单个模块
     */
    @ApiOperation(value = "查询单个模块")
    @GetMapping(value = "/{modelName}")
    public void getListModel(@PathVariable String modelName){

    }

    /**
     * 生成列表
     */
    @ApiOperation(value = "生成列表")
    @GetMapping(value = "/list_data/{modelName}")
    public void generateList(@PathVariable String modelName){

    }
}
