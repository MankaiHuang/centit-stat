package com.centit.stat.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.centit.framework.common.ObjectException;
import com.centit.framework.ip.po.DatabaseInfo;
import com.centit.framework.ip.service.IntegrationEnvironment;
import com.centit.stat.utils.DBCPDao;
import com.centit.stat.dao.ReportDao;
import com.centit.stat.dao.ReportSqlDao;
import com.centit.stat.po.ReportModel;
import com.centit.stat.po.ReportSql;
import com.centit.stat.service.ReportService;
import com.centit.support.database.utils.DatabaseAccess;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ReportServiceImpl implements ReportService {

    private Logger logger = LoggerFactory.getLogger("报表文书");

    @Autowired
    private ReportDao reportDao;

    @Autowired
    private ReportSqlDao reportSqlDao;

    @Autowired
    private IntegrationEnvironment integrationEnvironment;

    @Override
    public void createReportModel(ReportModel model) {
        reportDao.saveNewObject(model);
        reportDao.saveObjectReferences(model);
    }

    @Override
    public JSONObject queryData(String modelName) {
        ReportModel model = reportDao.getObjectWithReferences(modelName);
        List<ReportSql> reportSqls = model.getReportSqls();
        JSONObject result = new JSONObject();
        if(reportSqls == null || reportSqls.size() == 0){
            return null;
        }
        for(ReportSql reportSql : reportSqls){
            reportSql = reportSqlDao.fetchObjectReference(reportSql, "children");
            String databaseCode = reportSql.getDatabaseCode();
            DatabaseInfo databaseInfo = integrationEnvironment.getDatabaseInfo(databaseCode);
            Connection connection = null;
            try{
                connection = DBCPDao.getConn(databaseInfo);
                String sql = reportSql.getQuerySql();
                String propertyName = reportSql.getPropertyName();
                String type = reportSql.getQueryType();//S: 只有一个值 V：向量只有一行 T 表格
                JSONObject data = queryData(reportSql, connection, new HashMap<>());
                result.putAll(data);
            }catch (SQLException e){
                //连接数据库出错
                logger.error("连接数据库【{}】出错", databaseInfo.getDatabaseName());
            }finally {
                if(connection != null){
                    try{
                        connection.close();
                    }catch (SQLException e){
                        logger.error("关闭数据库连接出错", e);
                    }
                }
            }
        }
        return result;
    }

    private JSONObject queryData(ReportSql reportSql, Connection connection, Map<String, Object> params){
        JSONObject result = new JSONObject();
        String sql = reportSql.getQuerySql();
        String propertyName = reportSql.getPropertyName();
        String type = reportSql.getQueryType();//S: 只有一个值 V：向量只有一行 T 表格
        try {
            switch (type) {
                case "S":
                    Object sData = DatabaseAccess.getScalarObjectQuery(connection, sql, params);
                    result.put(propertyName, sData);
                    break;
                case "V"://可能有子查询
                    JSONObject vData = DatabaseAccess.getObjectAsJSON(connection, sql, params);
                    List<ReportSql> children = reportSql.getChildren();
                    if(children != null && children.size() > 0){
                        for(ReportSql child : children) {
                            vData.putAll(queryData(child, connection, vData));
                        }
                    }
                    result.put(propertyName, vData);
                    break;
                case "T"://可能有子查询
                    JSONArray tData = DatabaseAccess.findObjectsByNamedSqlAsJSON(connection, sql, params);
                    List<ReportSql> childrenv = reportSql.getChildren();
                    if(childrenv != null && childrenv.size() > 0) {
                        for (Object data : tData) {
                            for (ReportSql child : childrenv) {
                                JSONObject jsonData = (JSONObject) data;
                                jsonData.putAll(queryData(child, connection, jsonData));
                            }
                        }
                    }
                    result.put(propertyName, tData);

                    break;
                default:
                    throw new ObjectException("查询类型不明确!");
            }
        }catch (SQLException | IOException e){
            logger.error("查询数据出错", e);
        }
        return result;
    }
}
