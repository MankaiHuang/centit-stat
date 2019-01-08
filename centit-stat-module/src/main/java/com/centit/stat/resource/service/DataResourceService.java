package com.centit.stat.resource.service;

import com.centit.stat.resource.po.DataResource;
import com.centit.support.database.utils.PageDesc;

import java.util.List;
import java.util.Map;

public interface DataResourceService {

    /**
     * 新增数据包
     */
    void createDataResource(DataResource dataResource);

    void updateDataResource(DataResource dataResource);

    void deleteDataResource(String resourceId);

    List<DataResource> listDataResource(Map<String, Object> params, PageDesc pageDesc);

    DataResource getDataResource(String resourceId);
}
