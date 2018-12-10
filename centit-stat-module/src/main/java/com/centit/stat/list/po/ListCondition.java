package com.centit.stat.list.po;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 *    Model_Name           varchar(64) not null,
 *    cond_Name            varchar(64) not null,
 *    cond_Label           varchar(200) not null,
 *    cond_Display_Style   char(1) not null comment 'N:普通 nomal H 隐藏 hide R 只读 readonly',
 *    param_Type           varchar(64) comment 'S:文本 N数字  D：日期 T：时间戳（datetime)  ',
 *    param_Reference_Type char(1) comment '0：没有：1： 数据字典 2：JSON表达式 3：sql语句  Y：年份 M：月份',
 *    param_Reference_Data varchar(1000) comment '根据paramReferenceType类型（1,2,3）填写对应值',
 *    param_Validate_Regex varchar(200) comment 'regex表达式',
 *    param_Validate_Info  varchar(200) comment '约束不通过提示信息',
 *    param_Default_Value  varchar(200) comment '参数默认值',
 *    cond_Order           numeric(2,0),
 */
@Getter
@Setter
@Entity
@Table(name = "Q_LIST_CONDITION")
public class ListCondition implements Serializable {

    @Id
    @Column(name = "MODEL_NAME")
    private String modelName;

    @Id
    @Column(name = "COND_NAME")
    private String condName;

    @Column(name = "COND_LABEL")
    private String condLabel;

    @Column(name = "COND_DISPLAY_STYLE")
    private String condDisplayStyle;

    @Column(name = "PARAM_TYPE")
    private String paramType;

    @Column(name = "PARAM_REFERENCE_DATA")
    private String paramReferenceData;

    @Column(name = "PARAM_VALIDATE_REGEX")
    private String paramValidateRegex;

    @Column(name = "PARAM_VALIDATE_INFO")
    private String paramValidateInfo;

    @Column(name = "PARAM_DEFAULT_VALUE")
    private String paramDefaultValue;

    @Column(name = "COND_ORDER")
    private int condOrder;
}
