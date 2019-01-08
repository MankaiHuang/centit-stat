package com.centit.stat.resource.controller;

import com.centit.framework.core.controller.WrapUpResponseBody;
import com.centit.framework.core.dao.PageQueryResult;
import com.centit.stat.resource.po.DataResource;
import com.centit.stat.resource.service.DataResourceService;
import com.centit.support.database.utils.PageDesc;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(value = "数据包")
@RestController
@RequestMapping(value = "data_resource")
public class ResourceController {

    @Autowired
    private DataResourceService dataResourceService;

    @ApiOperation(value = "新增数据包")
    @PostMapping
    @WrapUpResponseBody
    public void createDataResource(DataResource dataResource){
        dataResourceService.createDataResource(dataResource);
    }

    @ApiOperation(value = "编辑数据包")
    @PutMapping(value = "/{resourceId}")
    @WrapUpResponseBody
    public void updateDataResource(@PathVariable String resourceId, DataResource dataResource){
        dataResource.setResourceId(resourceId);
        dataResourceService.updateDataResource(dataResource);
    }

    @ApiOperation(value = "删除数据包")
    @DeleteMapping(value = "/{resourceId}")
    @WrapUpResponseBody
    public void deleteDataResource(@PathVariable String resourceId){
        dataResourceService.deleteDataResource(resourceId);
    }

    @ApiOperation(value = "查询数据包")
    @GetMapping
    @WrapUpResponseBody
    public PageQueryResult<DataResource> listDataResource(PageDesc pageDesc){
        List<DataResource> list = dataResourceService.listDataResource(null, pageDesc);
        return PageQueryResult.createResult(list, pageDesc);
    }

    @ApiOperation(value = "查询单个数据包")
    @GetMapping(value = "/{resourceId}")
    @WrapUpResponseBody
    public DataResource getDataResource(@PathVariable String resourceId){
        return dataResourceService.getDataResource(resourceId);
    }


}
