package com.centit.stat.resource.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.centit.framework.common.ObjectException;
import com.centit.framework.ip.po.DatabaseInfo;
import com.centit.framework.ip.service.IntegrationEnvironment;
import com.centit.stat.resource.dao.DataResourceDao;
import com.centit.stat.resource.po.DataResource;
import com.centit.stat.resource.po.DataResourceColumn;
import com.centit.stat.resource.service.DataResourceService;
import com.centit.support.database.utils.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
@Transactional
public class DataResourceServiceImpl implements DataResourceService {

    private final Logger logger = LoggerFactory.getLogger(DataResourceServiceImpl.class);

    @Autowired
    private DataResourceDao dataResourceDao;

    @Autowired
    private IntegrationEnvironment integrationEnvironment;

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
        return dataResourceDao.getObjectWithReferences(resourceId);
    }

    public List<DataResourceColumn> mapSql2Column(String sql) {
        List<String> fields = QueryUtils.getSqlFiledNames(sql);
        return null;
    }

    @Override
    public List<DataResourceColumn> generateColumn(String databaseCode, String sql) {
        List<DataResourceColumn> columns = new ArrayList<>();
        List<String> fields = QueryUtils.getSqlFiledNames(sql);
        for(String field : fields){
            columns.add(new DataResourceColumn(field, ""));
        }
        return columns;
    }

    @Override
    public JSONArray queryData(String databaseCode, String sql, Map<String, Object> params, PageDesc pageDesc) {
        DatabaseInfo databaseInfo = integrationEnvironment.getDatabaseInfo(databaseCode);
        QueryAndParams qap = QueryAndParams.createFromQueryAndNamedParams(new QueryAndNamedParams(sql, params));
//        if(qap.getParams() == null || qap.getParams().length == 0){
//            sql = sql.substring(0, sql.toLowerCase().indexOf("where"));
//        }
        try (Connection connection = DbcpConnectPools.getDbcpConnect(
            new DataSourceDescription(databaseInfo.getDatabaseUrl(), databaseInfo.getUsername(), databaseInfo.getClearPassword()))){

//            return DatabaseAccess.findObjectsByNamedSqlAsJSON(connection, sql, params, null, pageDesc.getPageNo(), pageDesc.getPageSize());
            return DatabaseAccess.findObjectsAsJSON(connection, qap.getQuery(), qap.getParams(), pageDesc.getPageNo(), pageDesc.getPageSize());

        }catch (SQLException | IOException e){
            throw new ObjectException("查询数据出错！");
        }
    }

    @Override
    public Set<String> generateParam(String sql) {
        return QueryUtils.getSqlTemplateParameters(sql);
    }
}
