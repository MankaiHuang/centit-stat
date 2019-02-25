package com.centit.stat.resource.dao;

import com.centit.framework.jdbc.dao.BaseDaoImpl;
import com.centit.stat.resource.po.DataResource;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public class DataResourceDao extends BaseDaoImpl<DataResource, String> {
    @Override
    public Map<String, String> getFilterField() {
        return null;
    }
}
