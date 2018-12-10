package com.centit.stat.query.service;

import com.centit.framework.jdbc.service.BaseEntityManager;
import com.centit.stat.query.po.QueryModel;

import java.util.List;
import java.util.Map;

public interface QueryModelManager extends BaseEntityManager<QueryModel, String> {
    String getWizardNo();

    Map<String, List<Object>> getColAndCond(String sql);

    void saveQueryAndReference(QueryModel queryModel);

    void updateObjectAndReference(QueryModel queryModel);

    QueryModel getObjectWithReference(String modelName);

}
