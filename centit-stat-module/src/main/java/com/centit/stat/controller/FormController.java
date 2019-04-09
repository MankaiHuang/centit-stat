package com.centit.stat.controller;

import com.centit.framework.common.ObjectException;
import com.centit.framework.common.WebOptUtils;
import com.centit.framework.core.controller.BaseController;
import com.centit.framework.core.controller.WrapUpResponseBody;
import com.centit.framework.security.model.CentitUserDetails;
import com.centit.stat.po.FormModel;
import com.centit.stat.service.FormService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(value = "form")
@Api(value = "报表模块", tags = "报表模块")
public class FormController extends BaseController {

    @Autowired
    private FormService formService;

    @PostMapping
    @ApiOperation(value = "新建报表模块")
    @WrapUpResponseBody
    public void createForm(FormModel formModel, HttpServletRequest request){
        CentitUserDetails userDetails = WebOptUtils.getLoginUser(request);
        if(userDetails == null){
            throw new ObjectException("未登录");
        }
        formModel.setRecorder(userDetails.getUserCode());
        formService.createForm(formModel);
    }

    @PutMapping(value = "/{formId}")
    @ApiOperation(value = "编辑报表模块")
    @ApiImplicitParam(name = "formId", value = "报表模块ID")
    @WrapUpResponseBody
    public void updateForm(@PathVariable String formId, @RequestBody FormModel formModel){
        formModel.setFormId(formId);
        formService.updateForm(formModel);
    }

    @DeleteMapping(value = "/{formId}")
    @ApiOperation(value = "删除报表模块")
    @ApiImplicitParam(name = "formId", value = "报表模块ID")
    @WrapUpResponseBody
    public void deleteForm(@PathVariable String formId){
        formService.deleteForm(formId);
    }

    @GetMapping(value = "/{formId}")
    @ApiOperation(value = "查询单个报表模块")
    @ApiImplicitParam(name = "formId", value = "报表模块ID")
    @WrapUpResponseBody
    public FormModel getFormModel(@PathVariable String formId){
        return formService.getForm(formId);
    }

}
