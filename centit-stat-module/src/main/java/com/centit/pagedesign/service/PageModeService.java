package com.centit.pagedesign.service;

import com.centit.pagedesign.po.PageModel;
import com.centit.support.database.utils.PageDesc;

import java.util.List;
import java.util.Map;

public interface PageModeService {

    void createPageMode(PageModel pageModel);

    List<PageModel> listPageMode(Map<String, Object> param, PageDesc pageDesc);

    PageModel getPageMode(String pageCode);

    void deletePageMode(String pageCode);

    void updatePageMode(PageModel pageModel);
}
