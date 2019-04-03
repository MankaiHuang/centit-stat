package com.centit.stat.controller;

import com.centit.framework.core.controller.WrapUpResponseBody;
import com.centit.framework.core.dao.PageQueryResult;
import com.centit.stat.po.PageModel;
import com.centit.stat.service.PageService;
import com.centit.support.database.utils.PageDesc;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping(value = "page")
@Api(value = "自定义页面", tags = "自定义页面")
public class PageController {

    @Autowired
    private PageService pageService;

    @ApiOperation(value = "新增页面")
    @PostMapping
    @WrapUpResponseBody
    public void createPage(PageModel pageModel){
        pageService.createPage(pageModel);
    }

    @ApiOperation(value = "修改页面")
    @ApiImplicitParam(name = "pageCode", value = "页面代码")
    @PutMapping(value = "/{pageCode}")
    @WrapUpResponseBody
    public void updatePage(@PathVariable String pageCode, @RequestBody PageModel pageModel){
        pageModel.setPageCode(pageCode);
        pageService.updatePage(pageModel);
    }

    @ApiOperation(value = "删除页面")
    @ApiImplicitParam(name = "pageCode", value = "页面代码")
    @DeleteMapping(value = "/{pageCode}")
    @WrapUpResponseBody
    public void deletePage(@PathVariable String pageCode){
        pageService.deletePage(pageCode);
    }

    @ApiOperation(value = "查询页面")
    @GetMapping
    @WrapUpResponseBody
    public PageQueryResult<PageModel> listPage(PageDesc pageDesc){
        List<PageModel> list = pageService.listPage(new HashMap<String, Object>(), pageDesc);
        return PageQueryResult.createResult(list, pageDesc);
    }

    @ApiOperation(value = "查询单个页面")
    @GetMapping(value = "/{pageCode}")
    @WrapUpResponseBody
    public PageModel getPage(@PathVariable String pageCode){
        PageModel page = pageService.getPage(pageCode);
        return page;
    }
}
