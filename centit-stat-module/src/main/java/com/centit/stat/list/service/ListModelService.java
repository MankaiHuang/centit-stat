package com.centit.stat.list.service;

import com.centit.stat.list.po.ListData;
import com.centit.stat.list.po.ListModel;
import com.centit.support.database.utils.PageDesc;

import java.util.Map;

public interface ListModelService {

    void creteListModel(ListModel listModel);

    void updateListModel(ListModel listModel);

    void deleteListModel(String modelName);

    ListModel getListModel(String modelName);

    ListData initList(String modelName);

    void loadData(ListData data, Map<String, Object> params, PageDesc pageDesc);
}
