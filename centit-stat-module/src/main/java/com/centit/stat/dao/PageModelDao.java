package com.centit.stat.dao;

import com.centit.framework.jdbc.dao.BaseDaoImpl;
import com.centit.stat.po.PageModel;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public class PageModelDao extends BaseDaoImpl<PageModel, String> {


    @Override
    public Map<String, String> getFilterField() {
        return null;
    }
}
