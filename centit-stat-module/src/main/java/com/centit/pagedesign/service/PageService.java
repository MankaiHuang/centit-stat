package com.centit.pagedesign.service;

import com.centit.pagedesign.po.PageModel;
import com.centit.support.database.utils.PageDesc;

import java.util.List;
import java.util.Map;

public interface PageService {

    void createPage(PageModel pageModel);

    List<PageModel> listPage(Map<String, Object> param, PageDesc pageDesc);

    PageModel getPage(String pageCode);

    void deletePage(String pageCode);

    void updatePage(PageModel pageModel);
}
