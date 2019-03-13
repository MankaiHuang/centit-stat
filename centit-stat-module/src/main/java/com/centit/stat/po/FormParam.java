package com.centit.stat.po;

/*Name	Code	Comment	Data Type	Length	Precision	Primary	Foreign Key	Mandatory
报表ID	form_ID		VARCHAR2(64)	64		TRUE	TRUE	TRUE
参数名称	PARAM_Name		VARCHAR2(64)	64		TRUE	FALSE	TRUE
参数报表对比属性	compare_TYPE	1 同比 2 环比	char			FALSE	FALSE	FALSE
参数默认值	param_Default_Value	参数默认值	VARCHAR2(200)	200		FALSE	FALSE	FALSE*/

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "Q_FORM_PARAM")
public class FormParam {

    @Id
    @Column(name = "FORM_ID")
    private String formId;
    @Id
    @Column(name = "PARAM_NAME")
    private String paramName;
    @Column(name = "COMPARE_TYPE")
    private char compareType;
    @Column(name = "PARAM_DEFAULT_VALUE")
    private String paramDefaultValue;
}
