package com.centit.stat.client;


import org.apache.http.impl.client.CloseableHttpClient;

import java.util.Map;

/**
 * Created by codefan on 16-11-17.
 */
public interface StatClient {
    CloseableHttpClient getHttpClient() throws Exception;

    void releaseHttpClient(CloseableHttpClient httpClient);


    /**
     * 获得统计数据
     * @param httpclient    http客户段链接
     * @param modelName     统计模块代码
     * @param params    查询参数
     * @param pageNo 分页信息
     * @param pageSize 分页信息
     * @return String json
     */
    String getStatData(CloseableHttpClient httpclient, String modelName,
                              Map<String,Object> params, int pageNo, int pageSize) throws Exception;

    /**
     * 获得统计数据
     * @param httpclient    http客户段链接
     * @param modelName     统计模块代码
     * @param params    查询参数
     * @return String json
     */
    String getStatData(CloseableHttpClient httpclient, String modelName,
                              Map<String,Object> params) throws Exception;

    /**
     * 获得统计数据
     * @param modelName     统计模块代码
     * @param params    查询参数
     * @param pageNo 分页信息
     * @param pageSize 分页信息
     * @return String json
     */
    String getStatData(String modelName,
                              Map<String,Object> params,  int pageNo, int pageSize)throws Exception;
    /**
     * 获得统计数据
     * @param modelName     统计模块代码
     * @param params    查询参数
     * @return String json
     */
    String getStatData(String modelName,Map<String,Object> params)throws Exception;
}
