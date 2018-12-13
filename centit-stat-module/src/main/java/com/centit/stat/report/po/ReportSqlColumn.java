package com.centit.stat.report.po;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 这个表不需要维护，它 通过sql 语句自动生成。作用是作为 维护模板时做参考
 * Name	Code	Data Type	Length	Precision	Primary	Foreign Key	Mandatory
 * 查询语句ID	SQL_ID	VARCHAR2(32)	32		TRUE	TRUE	TRUE
 * 字段名称	col_Name	VARCHAR2(64)	64		TRUE	FALSE	TRUE
 * 数据类型	col_Type	CHAR(1)	1		FALSE	FALSE	FALSE
 */
//@ApiModel
@Getter
@Setter
@Entity
@Table(name = "Q_REPORT_SQL_Column")
public class ReportSqlColumn {
    @Id
    @Column(name = "SQL_ID")
    @ApiModelProperty(value = "查询语句ID")
    public String sqlId;

    @Column(name = "COL_NAME")
    @ApiModelProperty(value = "列名称")
    public String colName;

    @Column(name = "COL_TYPE")
    @ApiModelProperty(value = "列类型")
    public String colType;
}
