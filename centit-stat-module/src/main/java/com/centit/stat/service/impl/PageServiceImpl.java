package com.centit.stat.service.impl;

import com.centit.stat.dao.PageModelDao;
import com.centit.stat.po.PageModel;
import com.centit.stat.service.PageService;
import com.centit.support.database.utils.PageDesc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional
public class PageServiceImpl implements PageService {

    @Autowired
    private PageModelDao pageModelDao;

    @Override
    public void createPage(PageModel pageModel) {
        pageModelDao.saveNewObject(pageModel);
    }

    @Override
    public List<PageModel> listPage(Map<String, Object> param, PageDesc pageDesc) {
        return pageModelDao.listObjectsByProperties(param, pageDesc);
    }

    @Override
    public PageModel getPage(String pageCode) {
        return pageModelDao.getObjectWithReferences(pageCode);
    }

    @Override
    public void deletePage(String pageCode) {
        pageModelDao.deleteObjectById(pageCode);
    }

    @Override
    public void updatePage(PageModel pageModel) {
        pageModelDao.updateObject(pageModel);
    }
}
