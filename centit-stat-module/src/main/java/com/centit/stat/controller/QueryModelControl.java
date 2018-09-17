package com.centit.stat.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.serializer.SimplePropertyPreFilter;
import com.centit.framework.common.JsonResultUtils;
import com.centit.framework.common.ResponseMapData;
import com.centit.framework.core.controller.BaseController;
import com.centit.stat.po.QueryModel;
import com.centit.stat.service.QueryModelManager;
import com.centit.support.database.utils.PageDesc;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.HtmlUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 * 报表模块配置控制器
 */
@Controller
@RequestMapping("/stat/querymodel")
public class QueryModelControl extends BaseController {
    @Resource
    private QueryModelManager queryModelMag;

    /**
     * 模块列表
     * @param pageDesc
     * @param field
     * @param _search
     * @param request
     * @param response
     */
    @GetMapping
    public void list(PageDesc pageDesc, String[] field, String _search, HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> searchColumn = convertSearchColumn(request);
        JSONArray listObjects = queryModelMag.listObjectsAsJson(searchColumn, pageDesc);
        SimplePropertyPreFilter simplePropertyPreFilter = null;
        if (!ArrayUtils.isEmpty(field)) {
            simplePropertyPreFilter = new SimplePropertyPreFilter(QueryModel.class, field);
        }
        if (null == pageDesc) {
            JsonResultUtils.writeSingleDataJson(listObjects, response, simplePropertyPreFilter);
            return;
        }
        ResponseMapData resData = new ResponseMapData();
        resData.addResponseData(OBJLIST, listObjects);
        resData.addResponseData(PAGE_DESC, pageDesc);
        JsonResultUtils.writeResponseDataAsJson(resData, response, simplePropertyPreFilter);
    }

    /**
     * 查询单个模块
     * @param modelName 模块代码/名称
     * @param request
     * @param response
     */
    @RequestMapping(value = "/{modelName}", method = {RequestMethod.GET})
    public void get(@PathVariable String modelName, HttpServletRequest request, HttpServletResponse response) {
        QueryModel qm = queryModelMag.getObjectWithReference(modelName);
        JsonResultUtils.writeSingleDataJson(qm, response);
    }

    /**
     * 新增模块
     * @param qm
     * @param request
     * @param response
     */
    @RequestMapping(method = {RequestMethod.POST})
    public void create(@Valid QueryModel qm, HttpServletRequest request, HttpServletResponse response) {
        qm.setQuerySql(dealPlusAndAnd(qm.getQuerySql()));
        queryModelMag.saveNewObject(qm);
        JsonResultUtils.writeSingleDataJson(qm, response);
    }

    /**
     * 修改模块
     * @param qm
     * @param modelName
     * @param request
     * @param response
     */
    @RequestMapping(value = "/{modelName}", method = {RequestMethod.PUT})
    public void update(@Valid QueryModel qm, @PathVariable String modelName, HttpServletRequest request, HttpServletResponse response) {
        QueryModel dbqm = queryModelMag.getObjectById(modelName);
        qm.setQuerySql(dealPlusAndAnd(qm.getQuerySql()));
        if (null != dbqm)
            dbqm.copyNotNullProperty(qm);
        queryModelMag.updateObjectAndReference(dbqm);
        JsonResultUtils.writeSingleDataJson(dbqm, response);
    }


    //前端暂时将+号和&替换为<plussign>和<andsign>，然后进行了特殊字符转义，这个方法用来先反转义，再反替换

    public String dealPlusAndAnd(String str) {
        str = HtmlUtils.htmlUnescape(str);
        return str.replaceAll("<plussign>", "+").replaceAll("<andsign>", "&");
    }

    /**
     * 根据sql获取列名和查询参数名
     *
     * @param sql      查询sql
     * @param response HttpServletResponse
     */
    @RequestMapping(value = "/colandcond", method = {RequestMethod.POST})
    public void generateColAndConByQM(@RequestParam String sql, HttpServletResponse response) {
        try {
            Map<String, List<Object>> map = queryModelMag.getColAndCond(dealPlusAndAnd(sql));
            JsonResultUtils.writeSingleDataJson(map, response);
        } catch (Exception e) {
            JsonResultUtils.writeErrorMessageJson(501, "SQL解析失败，请检查是否", response);
        }
    }

    /**
     * 删除模块
     * @param request
     * @param modelName
     * @param response
     */
    @RequestMapping(value = "/{modelName}", method = {RequestMethod.DELETE})
    public void delete(HttpServletRequest request, @PathVariable String modelName, HttpServletResponse response) {
        QueryModel dbqm = queryModelMag.getObjectById(modelName);
        if (null != dbqm)
            queryModelMag.deleteObjectById(modelName);
        JsonResultUtils.writeSuccessJson(response);
    }

}
