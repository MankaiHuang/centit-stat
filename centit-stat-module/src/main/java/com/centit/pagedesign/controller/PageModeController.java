package com.centit.pagedesign.controller;

import com.centit.framework.common.ObjectException;
import com.centit.framework.common.WebOptUtils;
import com.centit.framework.core.controller.BaseController;
import com.centit.framework.core.controller.WrapUpResponseBody;
import com.centit.framework.core.dao.PageQueryResult;
import com.centit.pagedesign.po.PageModel;
import com.centit.pagedesign.service.PageModeService;
import com.centit.support.database.utils.PageDesc;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping(value = "page")
@Api(value = "自定义页面", tags = "自定义页面")
public class PageModeController extends BaseController {

    @Autowired
    private PageModeService pageService;

    @ApiOperation(value = "新增页面")
    @PostMapping
    @WrapUpResponseBody
    public void createPage(PageModel pageModel, HttpServletRequest request){
        String userCode = WebOptUtils.getCurrentUserCode(request);
        if(StringUtils.isBlank(userCode)){
            throw new ObjectException("未登录");
        }
        pageModel.setRecorder(userCode);
        pageModel.setPageDesignJson(StringEscapeUtils.unescapeHtml4(pageModel.getPageDesignJson()));
        pageService.createPageMode(pageModel);
    }

    @ApiOperation(value = "修改页面")
    @ApiImplicitParam(name = "pageCode", value = "页面代码")
    @PutMapping(value = "/{pageCode}")
    @WrapUpResponseBody
    public void updatePage(@PathVariable String pageCode, @RequestBody PageModel pageModel){
        pageModel.setPageCode(pageCode);
        pageModel.setPageDesignJson(StringEscapeUtils.unescapeHtml4(pageModel.getPageDesignJson()));
        pageService.updatePageMode(pageModel);
    }

    @ApiOperation(value = "删除页面")
    @ApiImplicitParam(name = "pageCode", value = "页面代码")
    @DeleteMapping(value = "/{pageCode}")
    @WrapUpResponseBody
    public void deletePage(@PathVariable String pageCode){
        pageService.deletePageMode(pageCode);
    }

    @ApiOperation(value = "查询页面")
    @GetMapping
    @WrapUpResponseBody
    public PageQueryResult<PageModel> listPage(PageDesc pageDesc){
        List<PageModel> list = pageService.listPageMode(new HashMap<>(), pageDesc);
        return PageQueryResult.createResult(list, pageDesc);
    }

    @ApiOperation(value = "查询单个页面")
    @GetMapping(value = "/{pageCode}")
    @WrapUpResponseBody
    public PageModel getPage(@PathVariable String pageCode){
        PageModel page = pageService.getPageMode(pageCode);
        return page;
    }
}
