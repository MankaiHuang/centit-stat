package com.centit.stat.po;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.*;
import javax.validation.constraints.Digits;

import org.hibernate.validator.constraints.Length;

import com.alibaba.fastjson.annotation.JSONField;
import org.hibernate.validator.constraints.NotBlank;

@Entity
@Table(name = "Q_QUERY_CONDITION")

public class QueryCondition implements java.io.Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "MODEL_NAME")
    @NotBlank(message = "字段不能为空")
    private String modelName;

    @Id
    @Column(name = "COND_NAME")
    @NotBlank(message = "字段不能为空")
    private String condName;

    @Column(name = "COND_LABEL")
    @Length(max = 120, message = "字段长度不能大于{max}")
    private String condLabel;//参数提示

    /**
     * 参数显示样式 N:普通 nomal H 隐藏 hide R 只读 readonly
     */
    @Column(name = "COND_DISPLAY_STYLE")
    @Length(max = 1, message = "字段长度不能大于{max}")
    private String condDisplayStyle;

    /**
     * 参数类型 S:文本 N数字  D：日期 T：时间戳（datetime)
     */
    @Column(name = "PARAM_TYPE")
    @Length(max = 1, message = "字段长度不能大于{max}")
    private String paramType;

    @Column(name = "COMPARE_TYPE")
    @Length(max = 1, message = "字段长度不能大于{max}")
    private String compareType;//对比时间字段类 必需是时间字段， 3 同比分析  4 环比分析 0 其他

    @Column(name = "PARAM_REFERENCE_TYPE")
    @Length(max = 1, message = "字段长度不能大于{max}")
    private String paramReferenceType;//参数应用类型 0：没有：1： 数据字典 2：JSON表达式 3：sql语句  Y：年份 M：月份

    @Column(name = "PARAM_REFERENCE_DATA")
    @Length(max = 1000, message = "字段长度不能大于{max}")
    private String paramReferenceData;//参数应用数据 根据paramReferenceType类型（1,2,3）填写对应值


    @Column(name = "PARAM_VALIDATE_REGEX")
    @Length(max = 200, message = "字段长度不能大于{max}")
    private String paramValidateRegex;//参数约束表达式 regex表达式

    @Column(name = "PARAM_VALIDATE_INFO")
    @Length(max = 200, message = "字段长度不能大于{max}")
    private String paramValidateInfo;//参数约束提示 约束不通过提示信息

    @Column(name = "PARAM_DEFAULT_VALUE")
    @Length(max = 200, message = "字段长度不能大于{max}")
    private String paramDefaultValue;//查询变量默认值

    @Column(name = "COND_ORDER")
    @Digits(integer = 2, fraction = 0, message = "字段范围整数{integer}位小数{fraction}位")
    private Integer condOrder;

    @Transient
    private Object condValue;

    //    @ManyToOne
    @JSONField(serialize = false)
//    @JoinColumn(name = "MODELNAME", insertable = false, updatable = false)
    private QueryModel queryModel;

    @Transient
    private List<Map<String, Object>> comboValues;

    // Constructors
    public QueryCondition() {
        clearProperties();
    }

    public QueryCondition(String modelName, String condName) {
        this.modelName = modelName;
        this.condName = condName;
    }

    public QueryCondition(String modelName, String condName, String condLabel, String condDisplayStyle, String paramType, String compareType, String paramReferenceType, String paramReferenceData, String paramValidateRegex, String paramValidateInfo, String paramDefaultValue, @Digits(integer = 2, fraction = 0, message = "字段范围整数{integer}位小数{fraction}位") Integer condOrder, Object condValue, QueryModel queryModel, List<Map<String, Object>> comboValues) {
        this.modelName = modelName;
        this.condName = condName;
        this.condLabel = condLabel;
        this.condDisplayStyle = condDisplayStyle;
        this.paramType = paramType;
        this.compareType = compareType;
        this.paramReferenceType = paramReferenceType;
        this.paramReferenceData = paramReferenceData;
        this.paramValidateRegex = paramValidateRegex;
        this.paramValidateInfo = paramValidateInfo;
        this.paramDefaultValue = paramDefaultValue;
        this.condOrder = condOrder;
        this.condValue = condValue;
        this.queryModel = queryModel;
        this.comboValues = comboValues;
    }
// Property accessors


    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public String getCondName() {
        return condName;
    }

    public void setCondName(String condName) {
        this.condName = condName;
    }

    public String getCondLabel() {
        return this.condLabel;
    }

    public void setCondLabel(String condLabel) {
        this.condLabel = condLabel;
    }

    public Object getCondValue() {
        return condValue;
    }

    public void setCondValue(Object condValue) {
        this.condValue = condValue;
    }

    public String getParamType() {
        return paramType;
    }

    public void setParamType(String paramType) {
        this.paramType = paramType;
    }

    public String getParamReferenceType() {
        return paramReferenceType;
    }

    public void setParamReferenceType(String paramReferenceType) {
        this.paramReferenceType = paramReferenceType;
    }

    public String getParamReferenceData() {
        return paramReferenceData;
    }

    public void setParamReferenceData(String paramReferenceData) {
        this.paramReferenceData = paramReferenceData;
    }

    public String getParamValidateRegex() {
        return paramValidateRegex;
    }

    public void setParamValidateRegex(String paramValidateRegex) {
        this.paramValidateRegex = paramValidateRegex;
    }

    public String getParamValidateInfo() {
        return paramValidateInfo;
    }

    public void setParamValidateInfo(String paramValidateInfo) {
        this.paramValidateInfo = paramValidateInfo;
    }

    public String getParamDefaultValue() {
        return paramDefaultValue;
    }

    public void setParamDefaultValue(String paramDefaultValue) {
        this.paramDefaultValue = paramDefaultValue;
    }

    /**
     * N:普通 nomal H 隐藏 hide R 只读 readonly
     *
     * @return String
     */
    public String getCondDisplayStyle() {
        return this.condDisplayStyle;
    }


    public void setCondDisplayStyle(String condDisplayStyle) {
        this.condDisplayStyle = condDisplayStyle;
    }


    public Integer getCondOrder() {
        return condOrder;
    }

    public void setCondOrder(Integer condOrder) {
        this.condOrder = condOrder;
    }


    public QueryCondition copy(QueryCondition other) {
        this.setModelName(other.getModelName());
        this.setCondName(other.getCondName());
        this.setParamReferenceData(other.getParamReferenceData());
        this.setParamReferenceType(other.getParamReferenceType());
        this.setParamType(other.getParamType());
        this.setParamValidateInfo(other.getParamValidateInfo());
        this.setParamValidateRegex(other.getParamValidateRegex());
        this.paramDefaultValue = other.getParamDefaultValue();
        this.condValue = other.getCondValue();
        this.condLabel = other.getCondLabel();
        this.condDisplayStyle = other.getCondDisplayStyle();
        this.condOrder = other.getCondOrder();
        this.compareType = other.getCompareType();
        return this;
    }

    public QueryCondition copyNotNullProperty(QueryCondition other) {

        if (other.getModelName() != null)
            this.setModelName(other.getModelName());
        if (other.getCondName() != null)
            this.setCondName(other.getCondName());
        if (other.getParamReferenceData() != null)
            this.setParamReferenceData(other.getParamReferenceData());
        if (other.getParamReferenceType() != null)
            this.setParamReferenceType(other.getParamReferenceType());
        if (other.getParamType() != null)
            this.setParamType(other.getParamType());
        if (other.getParamValidateInfo() != null)
            this.setParamValidateInfo(other.getParamValidateInfo());
        if (other.getParamValidateRegex() != null)
            this.setParamValidateRegex(other.getParamValidateRegex());

        if (other.getParamDefaultValue() != null)
            this.paramDefaultValue = other.getParamDefaultValue();

        if (other.getCondValue() != null)
            this.condValue = other.getCondValue();

        if (other.getCondLabel() != null)
            this.condLabel = other.getCondLabel();
        if (other.getCompareType() != null)
            this.compareType = other.getCompareType();
        if (other.getCondDisplayStyle() != null)
            this.condDisplayStyle = other.getCondDisplayStyle();
        if (other.getCondOrder() != null)
            this.condOrder = other.getCondOrder();
        return this;
    }

    public QueryCondition clearProperties() {
        this.paramReferenceData = null;
        this.paramReferenceType = null;
        this.paramType = null;
        this.paramValidateRegex = null;
        this.paramValidateInfo = null;
        this.condLabel = null;
        this.condDisplayStyle = "N";
        this.condOrder = null;
        this.compareType = "0";
        return this;
    }

    /**
     * 必需是时间字段， 3 同比分析  4 环比分析 0 其他
     *
     * @return String
     */
    public String getCompareType() {
        return compareType;
    }


    public void setCompareType(String compareType) {
        this.compareType = compareType;
    }

    public QueryModel getQueryModel() {
        return queryModel;
    }

    public void setQueryModel(QueryModel queryModel) {
        this.queryModel = queryModel;
    }

    public List<Map<String, Object>> getComboValues() {
        if (comboValues == null)
            comboValues = new ArrayList<Map<String, Object>>();
        return comboValues;
    }

    public void setComboValues(List<Map<String, Object>> comboValues) {
        this.comboValues = comboValues;
    }
}
