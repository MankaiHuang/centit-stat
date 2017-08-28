package com.centit.stat.service.impl;

import java.util.*;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.centit.framework.hibernate.service.BaseEntityManagerImpl;
import com.centit.stat.dao.QueryModelDao;
import com.centit.stat.po.QueryColumn;
import com.centit.stat.po.QueryColumnId;
import com.centit.stat.po.QueryModel;
import com.centit.stat.po.QueryCondition;

import com.centit.stat.service.QueryModelManager;
import com.centit.support.database.utils.QueryUtils;

@Service
@Transactional
public class QueryModelManagerImpl extends BaseEntityManagerImpl<QueryModel,String,QueryModelDao>
	implements QueryModelManager{
	
	private QueryModelDao queryModelDao ;
	@Resource
	public void setQueryModelDao(QueryModelDao baseDao)
	{
		this.queryModelDao = baseDao;
		setBaseDao(this.queryModelDao);
	}
	
	public String  getWizardNo(){
	    return this.queryModelDao.getWizardNo();
	}

	@Override
	public Map<String,List<Object>> getColAndCond(String sql) {
		Map<String,List<Object>> ColAndCond=new HashMap<String,List<Object>>();
		List<Object> colList=new ArrayList<Object>();

		List<String> columnNames=QueryUtils.getSqlTemplateFiledNames(sql);

		for(String name:columnNames){
			QueryColumn col=new QueryColumn();
			col.setCid(new QueryColumnId("",name));
			colList.add(col);
		}
		ColAndCond.put("columns", colList);
		List<Object> condList=new ArrayList<Object>();
		Set<String> paramNames=QueryUtils.getSqlTemplateParameters(sql);
		for(String param : paramNames){
			QueryCondition cond=new QueryCondition();
			cond.setCondName(param);
			condList.add(cond);
		}
		ColAndCond.put("conditions", condList);
		return ColAndCond;
	}
	
}

