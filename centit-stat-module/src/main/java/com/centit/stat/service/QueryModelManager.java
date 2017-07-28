package com.centit.stat.service;

import com.centit.framework.core.service.BaseEntityManager;
import com.centit.stat.po.QueryModel;

import java.util.List;
import java.util.Map;

public interface QueryModelManager extends BaseEntityManager<QueryModel, String>{
	public String  getWizardNo();

	public Map<String, List<Object>> getColAndCond(String sql);
}
