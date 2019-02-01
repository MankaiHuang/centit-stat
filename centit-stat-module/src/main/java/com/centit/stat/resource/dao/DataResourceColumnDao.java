package com.centit.stat.resource.dao;

import com.centit.framework.jdbc.dao.BaseDaoImpl;
import com.centit.stat.resource.po.DataResourceColumn;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.Map;

@Repository
public class DataResourceColumnDao extends BaseDaoImpl<DataResourceColumn, Serializable> {
    @Override
    public Map<String, String> getFilterField() {
        return null;
    }
}
