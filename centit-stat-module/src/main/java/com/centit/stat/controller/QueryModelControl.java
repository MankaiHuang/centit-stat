package com.centit.stat.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.commons.lang3.ArrayUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.util.HtmlUtils;

import com.alibaba.fastjson.serializer.SimplePropertyPreFilter;
import com.centit.framework.core.common.JsonResultUtils;
import com.centit.framework.core.common.ResponseData;
import com.centit.framework.core.controller.BaseController;
import com.centit.framework.core.dao.PageDesc;
import com.centit.stat.po.QueryModel;
import com.centit.stat.service.QueryModelManager;
@Controller
@RequestMapping("/stat/querymodel")
public class QueryModelControl extends BaseController{
	@Resource
	private QueryModelManager queryModelMag;
	
	
	@RequestMapping(value="",method={RequestMethod.GET})
	public void list(PageDesc pageDesc,String[] field,  String _search,HttpServletRequest request, HttpServletResponse response){
		Map<String, Object> searchColumn = convertSearchColumn(request);
        List<QueryModel> listObjects = new ArrayList<QueryModel>();
        if (null == _search) {
            listObjects = queryModelMag.listObjects(searchColumn);
        } else {
            listObjects = queryModelMag.listObjects(searchColumn, pageDesc);
        }
        SimplePropertyPreFilter simplePropertyPreFilter = null;
        if (!ArrayUtils.isEmpty(field)) {
            simplePropertyPreFilter = new SimplePropertyPreFilter(QueryModel.class, field);
        }
        if (null == pageDesc) {
            JsonResultUtils.writeSingleDataJson(listObjects, response, simplePropertyPreFilter);
            return;
        }
        ResponseData resData = new ResponseData();
        resData.addResponseData(OBJLIST, listObjects);
        resData.addResponseData(PAGE_DESC, pageDesc);
        JsonResultUtils.writeResponseDataAsJson(resData, response, simplePropertyPreFilter);
	}
	
	
	@RequestMapping(value="/{modelName}",method={RequestMethod.GET})
	public void get(@PathVariable String modelName,HttpServletRequest request, HttpServletResponse response){
		QueryModel qm=queryModelMag.getObjectById(modelName);
		JsonResultUtils.writeSingleDataJson(qm, response);
	}
	
	
	@RequestMapping(value="",method={RequestMethod.POST})
	public void create(@Valid QueryModel qm,HttpServletRequest request, HttpServletResponse response){
		qm.setQuerySql(dealPlusAndAnd(qm.getQuerySql()));
		queryModelMag.saveNewObject(qm);
		JsonResultUtils.writeSingleDataJson(qm, response);
	}
	
	
	@RequestMapping(value="/{modelName}",method={RequestMethod.PUT})
	public void update(@Valid QueryModel qm,@PathVariable String modelName,HttpServletRequest request, HttpServletResponse response){
		QueryModel dbqm=queryModelMag.getObjectById(modelName);
		qm.setQuerySql(dealPlusAndAnd(qm.getQuerySql()));
		if(null!=dbqm)
			dbqm.copyNotNullProperty(qm);
		queryModelMag.saveObject(dbqm);
		JsonResultUtils.writeSingleDataJson(dbqm, response);
	}

	/**
	 * 前端暂时将+号和&替换为<plussign>和<andsign>，然后进行了特殊字符转义，这个方法用来先反转义，再反替换
	 */
	public String dealPlusAndAnd(String str){
		str=HtmlUtils.htmlUnescape(str);
		return str.replaceAll("<plussign>", "+").replaceAll("<andsign>", "&");
	}
	
	/**
	 * 根据sql获取列名和查询参数名
	 * @param sql
	 * @param response
	 */
	@RequestMapping(value="/colandcond",method={RequestMethod.POST})
	public void generateColAndConByQM(@RequestParam String sql, HttpServletResponse response){
		try {
			Map<String,List<Object>> map=queryModelMag.getColAndCond(dealPlusAndAnd(sql));
			JsonResultUtils.writeSingleDataJson(map, response);
		}
		catch(Exception e) {

			JsonResultUtils.writeErrorMessageJson(501, "SQL解析失败，请检查是否", response);

		}
	}
	
	@RequestMapping(value="/{queryName}",method={RequestMethod.DELETE})
	public void delete(HttpServletRequest request, @PathVariable String queryName,HttpServletResponse response){
		QueryModel dbqm=queryModelMag.getObjectById(queryName);
		if(null!=dbqm)
			queryModelMag.deleteObjectById(queryName);
		JsonResultUtils.writeSuccessJson(response);
	}
	
}
