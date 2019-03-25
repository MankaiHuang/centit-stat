package com.centit.stat.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.centit.framework.core.controller.BaseController;
import com.centit.framework.core.controller.WrapUpResponseBody;
import com.centit.framework.core.dao.PageQueryResult;
import com.centit.framework.ip.service.IntegrationEnvironment;
import com.centit.product.datapacket.po.DataPacket;
import com.centit.product.datapacket.po.RmdbQuery;
import com.centit.product.datapacket.service.DBPacketBizSupplier;
import com.centit.product.datapacket.service.DataPacketService;
import com.centit.product.datapacket.service.RmdbQueryService;
import com.centit.product.metadata.utils.JdbcConnect;
import com.centit.support.dataopt.bizopt.BuiltInOperation;
import com.centit.support.dataopt.core.BizModel;
import com.centit.support.dataopt.core.SimpleDataSet;
import com.centit.support.dataopt.dataset.SQLDataSetReader;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping(value = "chart")
@Api(value = "数据包数据获取接口", tags = "获取数据包中的数据")
public class DataPacketOptCtroller extends BaseController {

    @Autowired
    private DataPacketService dataPacketService;

    @Autowired
    private RmdbQueryService rmdbQueryService;

    @Autowired
    private IntegrationEnvironment integrationEnvironment;

    private BizModel innerFetchDataPacketData(String packetId, String params){
        DataPacket dataPacket = dataPacketService.getDataPacket(packetId);
        DBPacketBizSupplier bizSupplier = new DBPacketBizSupplier(dataPacket);
        bizSupplier.setIntegrationEnvironment(integrationEnvironment);
        if(StringUtils.isNotBlank(params)){
            JSONObject obj = JSON.parseObject(params);
            if(obj!=null){
                bizSupplier.setQueryParams(obj);
            }
        }
        return bizSupplier.get();
    }

    @ApiOperation(value = "获取数据包数据")
    @ApiImplicitParams({@ApiImplicitParam(
        name = "packetId", value="数据包ID",
        required=true, paramType = "path", dataType ="String"
    ), @ApiImplicitParam(
        name = "params", value="查询参数，map的json格式字符串"
    )})
    @GetMapping(value = "/packet/{packetId}")
    @WrapUpResponseBody
    public BizModel fetchDataPacketData(@PathVariable String packetId, String params){
        return innerFetchDataPacketData(packetId, params);
    }

    @ApiOperation(value = "获取数据库查询数据")
    @ApiImplicitParams({@ApiImplicitParam(
        name = "queryId", value="数据查询ID",
        required=true, paramType = "path", dataType ="String"
    ), @ApiImplicitParam(
        name = "params", value="查询参数，map的json格式字符串"
    )})
    @GetMapping(value = "/dbquery/{queryId}")
    @WrapUpResponseBody
    public SimpleDataSet fetchDBQueryData(@PathVariable String queryId, String params){
        RmdbQuery query = rmdbQueryService.getDbQuery(queryId);
        DataPacket dataPacket = dataPacketService.getDataPacket(query.getPacketId());
        Map<String, Object> modelTag = dataPacket.getPacketParamsValue();

        SQLDataSetReader sqlDSR = new SQLDataSetReader();
        sqlDSR.setDataSource(JdbcConnect.mapDataSource(
            integrationEnvironment.getDatabaseInfo(query.getDatabaseCode())));
        sqlDSR.setSqlSen(query.getQuerySQL());

        if(StringUtils.isNotBlank(params)){
            JSONObject obj = JSON.parseObject(params);
            if(obj!=null){
                modelTag.putAll(obj);
            }
        }

        return sqlDSR.load(modelTag);
    }

    @ApiOperation(value = "获取数据库查询数据")
    @ApiImplicitParams({@ApiImplicitParam(
        name = "queryId", value="数据查询ID",
        required=true, paramType = "path", dataType ="String"
    ), @ApiImplicitParam(
        name = "optsteps", value="数据操作，steps的json格式字符串，参见js代码中的说明"
    ), @ApiImplicitParam(
        name = "params", value="查询参数，map的json格式字符串"
    )})
    @GetMapping(value = "/dataopts/{packetId}")
    @WrapUpResponseBody
    public BizModel fetchDataPacketDataWithOpt(@PathVariable String packetId, String optsteps, String params){
        BizModel bizModel = innerFetchDataPacketData(packetId, params);
        if(StringUtils.isNotBlank(optsteps)){
            JSONObject obj = JSON.parseObject(optsteps);
            if(obj!=null){
                BuiltInOperation builtInOperation = new BuiltInOperation(obj);
                return builtInOperation.apply(bizModel);
            }
        }
        return bizModel;
    }
}