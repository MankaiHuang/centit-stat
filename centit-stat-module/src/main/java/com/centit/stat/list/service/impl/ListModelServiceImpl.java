package com.centit.stat.list.service.impl;

import com.centit.stat.list.dao.ListModelDao;
import com.centit.stat.list.po.ListModel;
import com.centit.stat.list.service.ListModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ListModelServiceImpl implements ListModelService {

    @Autowired
    private ListModelDao listModelDao;

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
        return listModelDao.getObjectById(modelName);
    }
}
