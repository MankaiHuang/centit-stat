package com.centit.stat.report.po;

import com.centit.stat.report.service.ReportService;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

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
@ApiModel
@Getter
@Setter
@Entity
@Table(name = "Q_REPORT_SQL")
public class ReportSql implements Serializable {

    @Column(name = "MODEL_NAME")
    @ApiModelProperty(value = "模块名称")
    public String modelName;

    @Id
    @Column(name = "SQL_ID")
    @ApiModelProperty(value = "查询语句ID")
    public String sqlId;

    @Column(name = "PARENT_SQL_ID")
    @ApiModelProperty(value = "父查询语句ID")
    public String parentSqlId;

    @Column(name = "PROPERTY_NAME")
    @ApiModelProperty(value = "数据属性")
    public String propertyName;

    @Column(name = "DATABASE_CODE")
    @ApiModelProperty(value = "数据库ID")
    public  String databaseCode;

    @Column(name = "QUERY_SQL")
    @ApiModelProperty(value = "查询语句")
    public String querySql;

    @Column(name = "QUERY_TYPE")
    @ApiModelProperty(value = "查询类别（S: 只有一个值 V：向量只有一行 T 表格）")
    @Length(max = 1)
    public String queryType;

    @Column(name = "CREATOR")
    @ApiModelProperty(value = "创建人")
    public String creator;

    @Column(name = "CREATE_TIME")
    @ApiModelProperty(value = "创建时间")
    public Date createTime;

    @OneToMany(targetEntity = ReportSql.class)
    @JoinColumn(name = "sqlId", referencedColumnName = "parentSqlId")
    List<ReportSql> children;

    @OneToMany(targetEntity = ReportSqlColumn.class)
    @JoinColumn(name = "sqlId", referencedColumnName = "sqlId")
    List<ReportSqlColumn> columns;

}
