package com.centit.stat.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.centit.support.database.utils.PageDesc;
import com.centit.framework.ip.po.DatabaseInfo;
import com.centit.support.database.utils.DataSourceDescription;
import com.centit.support.database.utils.DatabaseAccess;
import com.centit.support.database.utils.DbcpConnectPools;
import com.centit.support.database.utils.QueryAndNamedParams;

public class DBCPDao {

	/**
	 * 这里的params必须和queryString里面的？一一对应。
	 * @param dbinfo 数据库连接信息
	 * @param queryString 查询sql
	 * @return 查询结果集
	 */
	public static List<Object[]> findObjectsBySql(DatabaseInfo dbinfo, String queryString){
		List<Object[]> currDatas=new ArrayList<Object[]>();
		Connection conn =null;
		try {
    		conn = getConn(dbinfo);
    		currDatas = DatabaseAccess.findObjectsBySql(conn, queryString);
		}catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return currDatas;
	}

	/**
	 * 这里的params必须和queryString里面的？一一对应。
	 * @param dbinfo 数据库连接信息
	 * @param queryString 查询sql
	 * @param params 查询参数
	 * @return 返回结果集
	 */
	public static List<Object[]> findObjectsBySql(DatabaseInfo dbinfo,String queryString,List<Object> params){
		List<Object[]> currDatas=new ArrayList<Object[]>();
		try {
    		Connection conn = getConn(dbinfo);
    		currDatas = DatabaseAccess.findObjectsBySql(conn, queryString, params);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return currDatas;
	}

	public static List<Object[]> findObjectsBySql(DatabaseInfo dbinfo,String queryString,Object[] params){
		List<Object[]> currDatas=new ArrayList<Object[]>();
		try {
    		Connection conn = getConn(dbinfo);
    		currDatas = DatabaseAccess.findObjectsBySql(conn, queryString, params);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return currDatas;
	}

	public static List<Object[]> findObjectsBySql(DatabaseInfo dbinfo,String queryString,Object oneParam){
		List<Object[]> currDatas=new ArrayList<Object[]>();
		try {
    		Connection conn = getConn(dbinfo);
    		currDatas = DatabaseAccess.findObjectsBySql(conn, queryString, oneParam);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return currDatas;
	}

	public static List<Object[]> findObjectsNamedSql(DatabaseInfo dbinfo,String sSql,Map<String,Object> params){
		List<Object[]> currDatas=new ArrayList<Object[]>();
		try {
    		Connection conn = getConn(dbinfo);
    		currDatas = DatabaseAccess.findObjectsByNamedSql(conn, sSql, params);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return currDatas;
	}

	public static List<Object[]> findObjectsNamedSql(DatabaseInfo dbinfo,QueryAndNamedParams queryAndParams){
		List<Object[]> currDatas=new ArrayList<Object[]>();
		Connection conn=null;
		try {
    		conn = getConn(dbinfo);
    		currDatas = DatabaseAccess.findObjectsByNamedSql(conn, queryAndParams.getQuery(), queryAndParams.getParams());
		}catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return currDatas;
	}

	// 从连接池中获取链接
	private static Connection getConn(DatabaseInfo dbinfo) throws Exception {
		   DataSourceDescription desc=new DataSourceDescription();
		   desc.setConnUrl(dbinfo.getDatabaseUrl());
		   desc.setUsername(dbinfo.getUsername());
		   desc.setPassword(dbinfo.getClearPassword());
		   desc.setMaxIdle(10);
		   desc.setMaxTotal(20);
		   desc.setMaxWaitMillis(20000);
		   return DbcpConnectPools.getDbcpConnect(desc);//.getConn();
	}

	//代替数据字典
	public static String dbTypeText(String type){
		switch(type){
		case "1":return "sqlserver";
		case "2":return "oracle";
		case "3":return "db2";
		case "4":return "access";
		case "5":return "mysql";
		default:
			return "Unknown";
		}
	}

	public static List<Object[]> findObjectsNamedSql(DatabaseInfo dbinfo,
			QueryAndNamedParams qap, PageDesc page) {
      List<Object[]> currDatas=new ArrayList<Object[]>();

      if(null==dbinfo)
        throw new RuntimeException("未配置数据源！");
      try(Connection conn= getConn(dbinfo)) {
        if (DatabaseAccess.findObjectsByNamedSql(conn, qap.getQuery(), qap.getParams(),page.getPageNo(),page.getPageSize()) == null){
          page.setTotalRows(0);
        }else {
          currDatas = DatabaseAccess.findObjectsByNamedSql(conn, qap.getQuery(), qap.getParams(),page.getPageNo(),page.getPageSize());
          //long totalRows=DatabaseAccess.queryTotalRows(conn, qap.getQuery(), qap.getParams());
          long totalRows=DatabaseAccess.findObjectsByNamedSql(conn, qap.getQuery(), qap.getParams()).size();
          //分页数超过int范围会报错
          page.setTotalRows((int) totalRows);
        }
      }catch (Exception e) {
        e.printStackTrace();
      }
      return currDatas;
	}
}
