package com.centit.stat.client;

import com.centit.framework.appclient.AppSession;
import com.centit.support.network.HttpExecutor;
import org.apache.http.impl.client.CloseableHttpClient;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by codefan on 16-11-17.
 */
public class StatClientImpl implements StatClient{

    private AppSession appSession;
    private String fileServerExportUrl;

    public void setAppSession(AppSession appSession) {
        this.appSession = appSession;
    }

    public void setFileServerExportUrl(String fileServerUrl) {
        this.fileServerExportUrl = fileServerUrl;
    }

    public CloseableHttpClient  getHttpClient() throws Exception{
        return  appSession
    }

    public void releaseHttpClient(CloseableHttpClient httpClient){
        appSession.releaseHttpClient(httpClient);
    }
    /**
     * 获得统计数据
     * @param httpClient    http客户段链接
     * @param modelName     统计模块代码
     * @param params    查询参数
     * @param pageNo 分页信息
     * @param pageSize 分页信息
     * @return String json
     * @throws Exception Exception
     */
    public String getStatData(CloseableHttpClient httpClient, String modelName,
                              Map<String,Object> params, int pageNo, int pageSize)throws Exception{
        Map<String,Object> paramsWithPage = params;
        if(pageSize > 0 ){
            paramsWithPage = new HashMap<>();
            paramsWithPage.putAll(params);
            paramsWithPage.put("pageSize",pageSize);
            paramsWithPage.put("pageNo",pageNo);
            paramsWithPage.put("totalRows",-1);
        }

        appSession.checkAccessToken(httpClient);
        String jsonStr = HttpExecutor.simpleGet(httpClient,
                appSession.completeQueryUrl("/service/stat/twodimenform/"+modelName), paramsWithPage);
        return jsonStr;
    }

    /**
     * 获得统计数据
     * @param httpclient    http客户段链接
     * @param modelName     统计模块代码
     * @param params    查询参数
     * @return String
     * @throws Exception Exception
     */
    public String getStatData(CloseableHttpClient httpclient, String modelName,
                              Map<String,Object> params) throws Exception{
        return getStatData(httpclient,modelName,params,-1,-1);
    }

    /**
     * 获得统计数据
     * @param modelName     统计模块代码
     * @param params    查询参数
     * @param pageNo 分页信息
     * @param pageSize 分页信息
     * @return String json
     * @throws Exception Exception
     */
    public String getStatData(String modelName,
                              Map<String,Object> params, int pageNo, int pageSize)throws Exception{
        CloseableHttpClient httpclient = getHttpClient();
        String  jsonStr = getStatData(httpclient,modelName,params,pageNo, pageSize);
        releaseHttpClient(httpclient);
        return jsonStr;

    }
    /**
     * 获得统计数据
     * @param modelName     统计模块代码
     * @param params    查询参数
     * @return json
     * @throws Exception Exception
     */
    @Override
    public String getStatData(String modelName,Map<String, Object> params) throws Exception{
        CloseableHttpClient httpclient = getHttpClient();
        String  jsonStr = getStatData(httpclient,modelName,params, -1,-1);
        releaseHttpClient(httpclient);
        return jsonStr;
    }
}
