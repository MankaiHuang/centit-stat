package com.centit.stat.report.po;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

/**
 * Name	Code	Data Type	Length	Precision	Primary	Foreign Key	Mandatory
 * 模块名称	Model_Name	VARCHAR2(64)	64		FALSE	TRUE	TRUE
 * 查询语句ID	SQL_ID	VARCHAR2(32)	32		TRUE	FALSE	TRUE
 * 数据属性	property_NAME	VARCHAR2(64)	64		FALSE	FALSE	FALSE
 * 数据库ID	Database_Code	VARCHAR2(32)	32		FALSE	TRUE	FALSE
 * 查询语句	QUERY_SQL	VARCHAR2(4000)	4,000		FALSE	FALSE	FALSE
 * 查询描述	Query_DESC	VARCHAR2(512)	512		FALSE	FALSE	FALSE
 * 查询类别	query_type	CHAR(1)	1		FALSE	FALSE	FALSE
 * 创建人	creator	VARCHAR2(32)	32		FALSE	FALSE	FALSE
 * 创建时间	create_time	DATE			FALSE	FALSE	FALSE
 */
@Getter
@Setter
@Entity
@Table(name = "Q_REPORT_SQL")
public class ReportSql {
    public String modelName;

    public String sqlId;

    public String propertyName;

    public  String databaseCode;

    public String querySql;

    public String queryType;

    public String creator;

    public Date createTime;

}
