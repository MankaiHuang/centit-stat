package com.centit.stat.report.po;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Name	Code	Data Type	Length	Precision	Primary	Foreign Key	Mandatory
 * 模块名称	Model_Name	VARCHAR2(64)	64		TRUE	FALSE	TRUE
 * 格式报表名称模板	model_title_Format	VARCHAR2(256)	256		FALSE	FALSE	FALSE
 * 属主类别	Owner_Type	CHAR(1)	1		FALSE	FALSE	FALSE
 * 属主代码	Owner_Code	VARCHAR2(64)	64		FALSE	FALSE	FALSE
 * 报表描述	MODEL_DESC	VARCHAR2(512)	512		FALSE	FALSE	FALSE
 * 报表模板	report_doc_fileid	VARCHAR2(64)	64		FALSE	FALSE	FALSE
 */
@Getter
@Setter
@Entity
@Table(name = "Q_REPORT_MODEL")
public class ReportModel {
    public String modelName;

    public String modelTitleFormat;

    public String ownerType;

    public String ownerCode;

    public String modelDesc;

    public String reportDocFileId;
}
