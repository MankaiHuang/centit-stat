package com.centit.stat.resource.service.impl;

import com.centit.stat.resource.dao.DataResourceDao;
import com.centit.stat.resource.po.DataResource;
import com.centit.stat.resource.po.DataResourceColumn;
import com.centit.stat.resource.service.DataResourceService;
import com.centit.support.database.utils.PageDesc;
import com.centit.support.database.utils.QueryUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional
public class DataResourceServiceImpl implements DataResourceService {

    @Autowired
    private DataResourceDao dataResourceDao;

    @Override
    public void createDataResource(DataResource dataResource) {
        dataResourceDao.saveNewObject(dataResource);
        dataResourceDao.saveObjectReferences(dataResource);
    }

    @Override
    public void updateDataResource(DataResource dataResource) {
        dataResourceDao.updateObject(dataResource);
        dataResourceDao.saveObjectReferences(dataResource);
    }

    @Override
    public void deleteDataResource(String resourceId) {
        DataResource dataResource = dataResourceDao.getObjectById(resourceId);
        dataResourceDao.deleteObjectById(resourceId);
        dataResourceDao.deleteObjectReferences(dataResource);
    }

    @Override
    public List<DataResource> listDataResource(Map<String, Object> params, PageDesc pageDesc) {
        return dataResourceDao.listObjectsByProperties(params, pageDesc);
    }

    @Override
    public DataResource getDataResource(String resourceId) {
        return dataResourceDao.getObjectById(resourceId);
    }

    public List<DataResourceColumn> mapSql2Column(String sql){
        List<String> fields = QueryUtils.getSqlFiledNames(sql);
        return null;
    }
}
