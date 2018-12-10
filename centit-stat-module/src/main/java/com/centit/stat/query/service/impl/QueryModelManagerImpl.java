package com.centit.stat.query.service.impl;

import com.centit.framework.jdbc.service.BaseEntityManagerImpl;
import com.centit.stat.query.dao.QueryColumnDao;
import com.centit.stat.query.dao.QueryConditionDao;
import com.centit.stat.query.dao.QueryModelDao;
import com.centit.stat.query.po.QueryColumn;
import com.centit.stat.query.po.QueryCondition;
import com.centit.stat.query.po.QueryModel;
import com.centit.stat.query.service.QueryModelManager;
import com.centit.support.database.utils.QueryUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

@Service
@Transactional
public class QueryModelManagerImpl extends BaseEntityManagerImpl<QueryModel, String, QueryModelDao>
    implements QueryModelManager {

    private QueryModelDao queryModelDao;

    @Resource
    private QueryColumnDao queryColumnDao;
    @Resource
    private QueryConditionDao queryConditionDao;

    @Resource
    public void setQueryModelDao(QueryModelDao baseDao) {
        this.queryModelDao = baseDao;
        setBaseDao(this.queryModelDao);
    }

    public String getWizardNo() {
        return this.queryModelDao.getWizardNo();
    }

    @Override
    public Map<String, List<Object>> getColAndCond(String sql) {
        Map<String, List<Object>> ColAndCond = new HashMap<String, List<Object>>();
        List<Object> colList = new ArrayList<Object>();

        List<String> columnNames = QueryUtils.getSqlTemplateFiledNames(sql);

        for (String name : columnNames) {
            QueryColumn col = new QueryColumn();
            col.setModelName("");
            col.setColName(name);
            colList.add(col);
        }
        ColAndCond.put("columns", colList);
        List<Object> condList = new ArrayList<Object>();
        Set<String> paramNames = QueryUtils.getSqlTemplateParameters(sql);
        for (String param : paramNames) {
            QueryCondition cond = new QueryCondition();
            cond.setCondName(param);
            condList.add(cond);
        }
        ColAndCond.put("conditions", condList);
        return ColAndCond;
    }

    @Override
    @Transactional
    public void saveQueryAndReference(QueryModel queryModel){
        super.saveNewObject(queryModel);
        queryModelDao.saveObjectReferences(queryModel);
    }

    @Override
    public void updateObjectAndReference(QueryModel queryModel){
        super.updateObject(queryModel);
        queryModelDao.saveObjectReferences(queryModel);
    }

    @Override
    public QueryModel getObjectWithReference(String modelName){
        return queryModelDao.getObjectWithReferences(modelName);
    }

}

