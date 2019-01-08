package com.centit.stat.form.po;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/*
* Name	Code	Comment	Data Type	Length	Precision	Primary	Foreign Key	Mandatory
报表ID	form_ID		VARCHAR2(64)	64		TRUE	FALSE	TRUE
包ID	RESOURCE_ID		VARCHAR2(64)	64		FALSE	TRUE	FALSE
报表名称模板	from_Name_Format		VARCHAR2(256)	256		FALSE	FALSE	FALSE
报表类型	fromt_type	2 二维表 3 同比分析 4 环比分析 5 交叉制表	VARCHAR2(16)	16		FALSE	FALSE	FALSE
更改人员	Recorder		VARCHAR2(32)	32		FALSE	FALSE	FALSE
更改时间	RecordDate		DATE			FALSE	FALSE	FALSE
*/
@Data
@Entity
@Table(name = "Q_FORM_MODEL")
public class FormModel {

    @Id
    @Column(name = "FORM_ID")
    private String formId;

    @Column(name = "RESOURCE_ID")
    private String resourceId;

    @Column(name = "FROM_NAME_FORMAT")
    private String formNameFormat;

    @Column(name = "FORM_TYPE")
    private String formType;

    @Column(name = "RECORDER")
    private String recorder;

    @Column(name = "RECORDER_DATE")
    private Date recorderDate;
}
