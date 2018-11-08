package com.centit.stat.list.controller;

import com.centit.framework.core.controller.BaseController;
import com.centit.stat.list.po.ListModel;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 列表模块
 * Q_LIST_Condition;
 * Q_LIST_Model;
 * Q_List_Column;
 * Q_List_OPERATOR;
 */
@RestController
@RequestMapping("list_model")
public class ListModelController extends BaseController {

    /**
     * 新增模块
     */
    public void createListModel(ListModel listModel){

    }

    /**
     * 编辑模块
     */
    public void updateListModel(ListModel listModel){

    }

    /**
     * 删除模块
     */
    public void deleteListModel(String modelName){

    }

    /**
     * 生成列表
     */
    public void generateList(String moduleName){

    }
}
