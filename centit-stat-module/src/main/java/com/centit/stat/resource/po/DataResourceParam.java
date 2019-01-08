package com.centit.stat.resource.po;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/*Name	Code	Comment	Data Type	Length	Precision	Primary	Foreign Key	Mandatory
包ID	RESOURCE_ID		VARCHAR2(64)	64		TRUE	TRUE	TRUE
参数名称	PARAM_Name		VARCHAR2(64)	64		TRUE	FALSE	TRUE
参数提示（中文名）	PARAM_Label		VARCHAR2(200)	200		FALSE	FALSE	TRUE
参数显示样式	PARAM_Display_Style	N:普通 nomal H 隐藏 hide R 只读 readonly	CHAR			FALSE	FALSE	TRUE
参数类型	param_Type	S:文本 N数字  D：日期 T：时间戳（datetime)  	VARCHAR2(64)	64		FALSE	FALSE	FALSE
参数引用类型	param_Reference_Type	0：没有：1： 数据字典 2：JSON表达式 3：sql语句  Y：年份 M：月份	CHAR			FALSE	FALSE	FALSE
参数引用数据	param_Reference_Data	根据paramReferenceType类型（1,2,3）填写对应值	VARCHAR2(1000)	1,000		FALSE	FALSE	FALSE
参数约束表达式	param_Validate_Regex	regex表达式	VARCHAR2(200)	200		FALSE	FALSE	FALSE
参数约束提示	param_Validate_Info	约束不通过提示信息	VARCHAR2(200)	200		FALSE	FALSE	FALSE
参数默认值	param_Default_Value	参数默认值	VARCHAR2(200)	200		FALSE	FALSE	FALSE
条件序号	PARAM_Order		number(2)	2		FALSE	FALSE	FALSE*/
@Data
@Entity
@Table(name = "Q_DATA_RESOURCE_PARAM")
@ApiModel(value = "数据包参数")
public class DataResourceParam implements Serializable {

    private static final long serialVersionUID = 7093824507690734538L;

    @Id
    @Column(name = "RESOURCE_ID")
    private String resourceId;

    @Id
    @Column(name = "PARAM_NAME")
    private String paramName;

    @Column(name = "PARAM_LABEL")
    private String paramLabel;

    @Column(name = "PARAM_Display_Style")
    private String paramDisplayStyle;

    @Column(name = "PARAM_TYPE")
    private String paramType;

    @Column(name = "PARAM_REFERENCE_TYPE")
    private String paramReferenceType;

    @Column(name = "PARAM_REFERENCE_DATA")
    private String paramReferenceData;

    @Column(name = "PARAM_VALIDATE_REGEX")
    private String paramValidateRegex;

    @Column(name = "PARAM_VALIDATE_INFO")
    private String paramValidateInfo;

    @Column(name = "PARAM_DEFAULT_VALUE")
    private String paramDefaultValue;

    @Column(name = "PARAM_ORDER")
    private int paramOrder;
}
