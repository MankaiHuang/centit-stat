package com.centit.stat.report.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.centit.framework.common.ObjectException;
import com.centit.framework.ip.po.DatabaseInfo;
import com.centit.framework.ip.service.IntegrationEnvironment;
import com.centit.stat.query.dao.DBCPDao;
import com.centit.stat.report.dao.ReportDao;
import com.centit.stat.report.po.ReportModel;
import com.centit.stat.report.po.ReportSql;
import com.centit.stat.report.service.ReportService;
import com.centit.support.database.utils.DatabaseAccess;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

@Service
public class ReportServiceImpl implements ReportService {

    @Autowired
    private ReportDao reportDao;

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
        for(ReportSql reportSql : reportSqls){
            String databaseCode = reportSql.getDatabaseCode();
            DatabaseInfo databaseInfo = integrationEnvironment.getDatabaseInfo(databaseCode);
            String sql = reportSql.getQuerySql();
            String propertyName = reportSql.getPropertyName();
            String type = reportSql.getQueryType();//S: 只有一个值 V：向量只有一行 T 表格
            switch (type){
                case "S":
                    try {
                        Connection connection = DBCPDao.getConn(databaseInfo);
                        Object data = DatabaseAccess.getScalarObjectQuery(connection, sql);
                        result.put(propertyName, data);
                    }catch (SQLException | IOException e){
                        //连接数据库失败
                    }
                    break;
                case "V":
                    try {
                        Connection connection = DBCPDao.getConn(databaseInfo);
                        JSONObject data = DatabaseAccess.getObjectAsJSON(connection, sql);
                        result.put(propertyName, data);
                    }catch (SQLException | IOException e){
                        //连接数据库失败
                    }
                    break;
                case "T":
                    try {
                        Connection connection = DBCPDao.getConn(databaseInfo);
                        JSONArray data = DatabaseAccess.findObjectsAsJSON(connection, sql);
                        result.put(propertyName, data);
                    }catch (SQLException | IOException e){
                        //连接数据库失败
                    }
                    break;
                default:
                    throw new ObjectException("查询类型不明确!");
            }
        }
        return result;
    }
}
