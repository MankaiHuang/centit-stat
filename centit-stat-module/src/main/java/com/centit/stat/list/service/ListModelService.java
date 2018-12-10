package com.centit.stat.list.service;

import com.centit.stat.list.po.ListModel;

public interface ListModelService {

    void creteListModel(ListModel listModel);

    void updateListModel(ListModel listModel);

    void deleteListModel(String modelName);

    ListModel getListModel(String modelName);
}
