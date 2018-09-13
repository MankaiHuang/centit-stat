package com.centit.stat.po;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Digits;

import org.hibernate.validator.constraints.Length;

import com.alibaba.fastjson.annotation.JSONField;

@Entity
@Table(name = "Q_QUERYCONDITION")

public class QueryCondition implements java.io.Serializable {
    private static final long serialVersionUID =  1L;
    @EmbeddedId
    private QueryConditionId cid;

    @Column(name = "CONDLABEL")
    @Length(max = 120, message = "字段长度不能大于{max}")
    private String condLabel;//参数提示

    @Column(name = "CONDDISPLAYSTYLE")
    @Length(max = 1, message = "字段长度不能大于{max}")
    private String condDisplayStyle;//参数显示样式 N:普通 nomal H 隐藏 hide R 只读 readonly

    @Column(name = "PARAMTYPE")
    @Length(max = 1, message = "字段长度不能大于{max}")
    private String paramType;//参数类型 S:文本 N数字  D：日期 T：时间戳（datetime)

    @Column(name = "COMPARETYPE")
    @Length(max = 1, message = "字段长度不能大于{max}")
     private String  compareType;//对比时间字段类 必需是时间字段， 3 同比分析  4 环比分析 0 其他

    @Column(name = "PARAMREFERENCETYPE")
    @Length(max = 1, message = "字段长度不能大于{max}")
    private String paramReferenceType;//参数应用类型 0：没有：1： 数据字典 2：JSON表达式 3：sql语句  Y：年份 M：月份

    @Column(name = "PARAMREFERENCEDATA")
    @Length(max = 1000, message = "字段长度不能大于{max}")
    private String paramReferenceData;//参数应用数据 根据paramReferenceType类型（1,2,3）填写对应值


    @Column(name = "PARAMVALIDATEREGEX")
    @Length(max = 200, message = "字段长度不能大于{max}")
    private String paramValidateRegex;//参数约束表达式 regex表达式

    @Column(name = "PARAMVALIDATEINFO")
    @Length(max = 200, message = "字段长度不能大于{max}")
    private String paramValidateInfo;//参数约束提示 约束不通过提示信息

    @Column(name = "PARAMDEFAULTVALUE")
    @Length(max = 200, message = "字段长度不能大于{max}")
    private String paramDefaultValue;//查询变量默认值

    @Column(name = "CONDORDER")
    @Digits(integer = 2, fraction = 0, message = "字段范围整数{integer}位小数{fraction}位")
    private Integer condOrder;

    @Transient
    private Object condValue;

//    @ManyToOne
    @JSONField(serialize=false)
//    @JoinColumn(name = "MODELNAME", insertable = false, updatable = false)
    private QueryModel queryModel;

    @Transient
    private List<Map<String,Object>> comboValues;
    // Constructors
    public QueryCondition() {
        clearProperties();
    }

    // 正常变量的构造函数
    public QueryCondition(String modelName, String condName,String condLabel,String paramReferenceData,String condValue){
        this.cid = new QueryConditionId(modelName,condName);
        this.condLabel= condLabel;
        this.condDisplayStyle= "N";
        this.paramReferenceData=paramReferenceData;
        this.condOrder = null;
        this.compareType= "0";
    }

     //异常变量的构造函数
     // condDisplayStyle N:普通 nomal H 隐藏 hide R 只读 readonly
    public QueryCondition(String modelName, String condName,String condDisplayStyle, String condValue){
        this(condName,condName,condDisplayStyle,null,condValue);
    }


    public QueryCondition(QueryConditionId id,String  condLabel,String  condDisplayStyle) {
        this.cid = id;


        this.condLabel= condLabel;
        this.condDisplayStyle= condDisplayStyle;
    }

    public QueryCondition(QueryConditionId id
            ,String condLabel, String paramReferenceData, String paramType, String paramValidateRegex, String paramReferenceType, String condDisplayStyle, String compareType, Integer condOrder, String paramValidateInfo) {
        this.cid = id;
        this.condLabel=condLabel;
        this.paramType=paramType;
        this.paramReferenceData=paramReferenceData;
        this.paramReferenceType=paramReferenceType;
        this.paramValidateInfo=paramValidateInfo;
        this.paramValidateRegex=paramValidateRegex;
        this.condLabel= condLabel;
        this.condDisplayStyle= condDisplayStyle;
        this.compareType = compareType;
        this.condOrder = condOrder;
    }

    public QueryConditionId getCid() {
        return this.cid;
    }

    public void setCid(QueryConditionId id) {
        this.cid = id;
    }

    public String getModelName() {
        if(this.cid==null)
            this.cid = new QueryConditionId();
        return this.cid.getModelName();
    }

    public void setModelName(String modelName) {
        if(this.cid==null)
            this.cid = new QueryConditionId();
        this.cid.setModelName(modelName);
    }

    public String getCondName() {
        if(this.cid==null)
            this.cid = new QueryConditionId();
        return this.cid.getCondName();
    }

    public void setCondName(String condName) {
        if(this.cid==null)
            this.cid = new QueryConditionId();
        this.cid.setCondName(condName);
    }

    // Property accessors


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


    public QueryCondition copy(QueryCondition other){
        this.setModelName(other.getModelName());
        this.setCondName(other.getCondName());
        this.setParamReferenceData(other.getParamReferenceData());
        this.setParamReferenceType(other.getParamReferenceType());
        this.setParamType(other.getParamType());
        this.setParamValidateInfo(other.getParamValidateInfo());
        this.setParamValidateRegex(other.getParamValidateRegex());
        this.paramDefaultValue = other.getParamDefaultValue();
           this.condValue = other.getCondValue();
        this.condLabel= other.getCondLabel();
        this.condDisplayStyle= other.getCondDisplayStyle();
        this.condOrder = other.getCondOrder();
        this.compareType= other.getCompareType();
        return this;
    }

    public QueryCondition copyNotNullProperty(QueryCondition other){

        if( other.getModelName() != null)
            this.setModelName(other.getModelName());
        if( other.getCondName() != null)
            this.setCondName(other.getCondName());
        if(other.getParamReferenceData()!=null)
            this.setParamReferenceData(other.getParamReferenceData());
        if(other.getParamReferenceType()!=null)
            this.setParamReferenceType(other.getParamReferenceType());
        if(other.getParamType()!=null)
            this.setParamType(other.getParamType());
        if(other.getParamValidateInfo()!=null)
            this.setParamValidateInfo(other.getParamValidateInfo());
        if(other.getParamValidateRegex()!=null)
            this.setParamValidateRegex(other.getParamValidateRegex());

        if(other.getParamDefaultValue()!=null)
            this.paramDefaultValue = other.getParamDefaultValue();

           if(other.getCondValue()!=null)
            this.condValue = other.getCondValue();

        if( other.getCondLabel() != null)
            this.condLabel= other.getCondLabel();
        if( other.getCompareType() != null)
            this.compareType= other.getCompareType();
        if( other.getCondDisplayStyle() != null)
            this.condDisplayStyle= other.getCondDisplayStyle();
        if (other.getCondOrder()!=null)
            this.condOrder = other.getCondOrder();
        return this;
    }

    public QueryCondition clearProperties(){
        this.paramReferenceData=null;
        this.paramReferenceType=null;
        this.paramType=null;
        this.paramValidateRegex=null;
        this.paramValidateInfo=null;
        this.condLabel= null;
        this.condDisplayStyle= "N";
        this.condOrder = null;
        this.compareType= "0";
        return this;
    }
    /**
     * 必需是时间字段， 3 同比分析  4 环比分析 0 其他
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

    public List<Map<String,Object>> getComboValues() {
        if(comboValues==null)
            comboValues = new ArrayList<Map<String,Object>>();
        return comboValues;
    }

    public void setComboValues(List<Map<String,Object>> comboValues) {
        this.comboValues = comboValues;
    }
}
