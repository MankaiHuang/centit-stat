package com.centit.stat.list.service.impl;

import com.centit.framework.ip.po.DatabaseInfo;
import com.centit.framework.ip.service.IntegrationEnvironment;
import com.centit.stat.list.dao.ListModelDao;
import com.centit.stat.list.po.ListData;
import com.centit.stat.list.po.ListModel;
import com.centit.stat.list.service.ListModelService;
import com.centit.stat.query.dao.DBCPDao;
import com.centit.support.database.utils.PageDesc;
import com.centit.support.database.utils.QueryAndNamedParams;
import com.centit.support.database.utils.QueryUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional
public class ListModelServiceImpl implements ListModelService {

    @Autowired
    private ListModelDao listModelDao;

    @Autowired
    private IntegrationEnvironment integrationEnvironment;

    @Override
    public void creteListModel(ListModel listModel) {
        listModelDao.saveNewObject(listModel);
        listModelDao.saveObjectReferences(listModel);
    }

    @Override
    public void updateListModel(ListModel listModel) {
        listModelDao.updateObject(listModel);
        listModelDao.saveObjectReferences(listModel);
    }

    @Override
    public void deleteListModel(String modelName) {
        ListModel model = listModelDao.getObjectById(modelName);
        listModelDao.deleteObjectById(modelName);
        listModelDao.deleteObjectReferences(model);
    }

    @Transactional(readOnly = true)
    @Override
    public ListModel getListModel(String modelName) {
        return listModelDao.getObjectWithReferences(modelName);
    }

    @Transactional(readOnly = true)
    @Override
    public ListData initList(String modelName) {
        ListModel model = getListModel(modelName);
        ListData data = new ListData();
        data.initList(model);
        return data;
    }

    @Override
    public void loadData(ListData listData, Map<String, Object> params, PageDesc pageDesc) {
        DatabaseInfo databaseInfo = integrationEnvironment.getDatabaseInfo(listData.getModel().getDatabaseCode());
        QueryAndNamedParams qap = QueryUtils.translateQuery(listData.getModel().getQuerySql(), params);
        List<Object[]> data = DBCPDao.findObjectsNamedSql(databaseInfo, qap, pageDesc);
        listData.setData(data);
    }
}
