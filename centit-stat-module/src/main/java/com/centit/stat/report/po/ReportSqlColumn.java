package com.centit.stat.report.po;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Name	Code	Data Type	Length	Precision	Primary	Foreign Key	Mandatory
 * 查询语句ID	SQL_ID	VARCHAR2(32)	32		TRUE	TRUE	TRUE
 * 字段名称	col_Name	VARCHAR2(64)	64		TRUE	FALSE	TRUE
 * 数据类型	col_Type	CHAR(1)	1		FALSE	FALSE	FALSE
 */
@Getter
@Setter
@Entity
@Table(name = "Q_REPORT_SQL_Column")
public class ReportSqlColumn {
    public String sqlId;

    public String colName;

    public String colType;
}
