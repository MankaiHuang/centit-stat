package com.centit.pagedesign.service.impl;

import com.centit.pagedesign.dao.PageModelDao;
import com.centit.pagedesign.po.PageModel;
import com.centit.pagedesign.service.PageModeService;
import com.centit.support.database.utils.PageDesc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional
public class PageModeServiceImpl implements PageModeService {

    @Autowired
    private PageModelDao pageModelDao;

    @Override
    public void createPageMode(PageModel pageModel) {
        pageModelDao.saveNewObject(pageModel);
    }

    @Override
    public List<PageModel> listPageMode(Map<String, Object> param, PageDesc pageDesc) {
        return pageModelDao.listObjectsByProperties(param, pageDesc);
    }

    @Override
    public PageModel getPageMode(String pageCode) {
        return pageModelDao.getObjectWithReferences(pageCode);
    }

    @Override
    public void deletePageMode(String pageCode) {
        pageModelDao.deleteObjectById(pageCode);
    }

    @Override
    public void updatePageMode(PageModel pageModel) {
        pageModelDao.updateObject(pageModel);
    }
}
