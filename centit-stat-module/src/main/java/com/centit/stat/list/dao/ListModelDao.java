package com.centit.stat.list.dao;

import com.centit.framework.jdbc.dao.BaseDaoImpl;
import com.centit.stat.list.po.ListModel;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public class ListModelDao extends BaseDaoImpl<ListModel, String> {


    @Override
    public Map<String, String> getFilterField() {
        return null;
    }
}
