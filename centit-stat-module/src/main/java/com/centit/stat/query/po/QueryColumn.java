package com.centit.stat.query.po;

import javax.persistence.*;
import javax.validation.constraints.Digits;

import org.hibernate.validator.constraints.Length;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;
import com.centit.support.json.JSONOpt;
import org.hibernate.validator.constraints.NotBlank;

@Entity
@Table(name = "Q_QUERY_COLUMN")
public class QueryColumn implements java.io.Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "MODEL_NAME")
    @NotBlank(message = "字段不能为空")
    private String modelName;

    @Id
    @Column(name = "COL_NAME")
    @NotBlank(message = "字段不能为空")
    private String colName;

    /**
     * 数据操作 0：无操作  1：合计  2：平均  3：平均 合计
     */
    @Column(name = "OPT_TYPE")
    private String optType;
    @Column(name = "DRAW_CHART")
    private String drawChart;
    @Column(name = "COL_TYPE")
    private String colType;
    @Column(name = "COL_FORMAT")
    @Length(min = 0, max = 120, message = "字段长度不能小于{min}大于{max}")
    private String colFormat;
    @Column(name = "COL_LOGIC")
    @Length(min = 0, max = 120, message = "字段长度不能小于{min}大于{max}")
    private String colLogic;
    @Column(name = "COL_ORDER")
    @Digits(integer = 2, fraction = 0, message = "字段范围整数{integer}位小数{fraction}位")
    private Integer colOrder;
    @Column(name = "IS_SHOW")
    private String isShow;

    @Column(name = "CATALOG_CODE")
    private String catalogCode;
    /**
     * R 行头  C 列头  D 数值
     */
    @Column(name = "SHOW_TYPE")
    private String showType;
    @Transient
    private Double averageValue;
    @Transient
    private Double sumValue;
    @Transient
    private String colProperty;

    /**
     * 链接类型 navTab dialog blank等
     */
    @Column(name = "LINK_TYPE")
    @Length(min = 0, max = 64, message = "字段长度不能小于{min}大于{max}")
    private String linkType;
    //    @ManyToOne
    @JSONField(serialize = false)
//    @JoinColumn(name = "MODELNAME", insertable = false, updatable = false)
    private QueryModel queryModel;

    /**
     * 用于指定单元格样式
     */
    @Column(name = "CSS_STYLE")
    @Length(min = 0, max = 120, message = "字段长度不能小于{min}大于{max}")
    private String cssStyle;

    public String getCssStyle() {
        // TODO Auto-generated method stub
        return this.cssStyle;
    }

    public void setCssStyle(String cssStyle) {
        this.cssStyle = cssStyle;
    }

    public String getLinkType() {
        return linkType;
    }

    public void setLinkType(String linkType) {
        this.linkType = linkType;
    }

    public String getCatalogCode() {
        return catalogCode;
    }

    public void setCatalogCode(String catalogCode) {
        this.catalogCode = catalogCode;
    }

    // Constructors
    public QueryColumn() {
    }

    public QueryColumn(String modelName, String colName) {
        this.modelName = modelName;
        this.colName = colName;
    }

    public QueryColumn(String modelName, String colName, String optType, String drawChart, String colType, String colFormat, String colLogic, @Digits(integer = 2, fraction = 0, message = "字段范围整数{integer}位小数{fraction}位") Integer colOrder, String isShow, String catalogCode, String showType, Double averageValue, Double sumValue, String colProperty, String linkType, QueryModel queryModel, String cssStyle) {
        this.modelName = modelName;
        this.colName = colName;
        this.optType = optType;
        this.drawChart = drawChart;
        this.colType = colType;
        this.colFormat = colFormat;
        this.colLogic = colLogic;
        this.colOrder = colOrder;
        this.isShow = isShow;
        this.catalogCode = catalogCode;
        this.showType = showType;
        this.averageValue = averageValue;
        this.sumValue = sumValue;
        this.colProperty = colProperty;
        this.linkType = linkType;
        this.queryModel = queryModel;
        this.cssStyle = cssStyle;
    }

    public String getIsShow() {
        return isShow;
    }

    public void setIsShow(String isShow) {
        this.isShow = isShow;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public String getColName() {
        return colName;
    }

    public void setColName(String colName) {
        this.colName = colName;
    }

// Property accessors

    public String getOptType() {
        return this.optType;
    }

    public void setOptType(String optType) {
        this.optType = optType;
    }

    public String getDrawChart() {
        return this.drawChart;
    }

    public void setDrawChart(String drawChart) {
        this.drawChart = drawChart;
    }

    public String getColType() {
        return this.colType;
    }

    public void setColType(String colType) {
        this.colType = colType;
    }

    public String getColLogic() {
        return this.colLogic;
    }

    public void setColLogic(String colLogic) {
        this.colLogic = colLogic;
    }

    public Integer getColOrder() {
        return colOrder;
    }

    public void setColOrder(Integer colorder) {
        this.colOrder = colorder;
    }

    public QueryColumn copy(QueryColumn other) {

        this.setModelName(other.getModelName());
        this.setColName(other.getColName());

        this.optType = other.getOptType();
        this.drawChart = other.getDrawChart();
        this.colType = other.getColType();
        this.colLogic = other.getColLogic();
        this.colOrder = other.getColOrder();
        this.isShow = other.getIsShow();
        this.showType = other.getShowType();
        this.colFormat = other.getColFormat();
        this.linkType = other.getLinkType();
        this.cssStyle = other.getCssStyle();
        this.catalogCode = other.getCatalogCode();
        return this;
    }

    public QueryColumn copyNotNullProperty(QueryColumn other) {

        if (other.getModelName() != null)
            this.setModelName(other.getModelName());
        if (other.getColName() != null)
            this.setColName(other.getColName());
        if (other.getCssStyle() != null)
            this.setCssStyle(other.getCssStyle());
        if (other.getOptType() != null)
            this.optType = other.getOptType();
        if (other.getDrawChart() != null)
            this.drawChart = other.getDrawChart();
        if (other.getColType() != null)
            this.colType = other.getColType();
        if (other.getColLogic() != null)
            this.colLogic = other.getColLogic();
        if (other.getColOrder() != null)
            this.colOrder = other.getColOrder();
        if (other.getIsShow() != null)
            this.isShow = other.getIsShow();
        if (other.getShowType() != null)
            this.showType = other.getShowType();
        if (other.getLinkType() != null)
            this.linkType = other.getLinkType();
        if (other.getColFormat() != null) {
            this.colFormat = other.getColFormat();
        }
        if (other.getCatalogCode() != null) {
            this.catalogCode = other.getCatalogCode();
        }
        return this;
    }

    public QueryColumn clearProperties() {

        this.optType = null;
        this.drawChart = null;
        this.colType = null;
        this.colLogic = null;
        this.colOrder = null;
        this.isShow = null;
        this.showType = "D";
        this.colFormat = null;
        this.linkType = null;
        this.cssStyle = null;
        this.catalogCode = null;
        return this;
    }

    public String getColFormat() {
        return colFormat;
    }

    public void setColFormat(String colFormat) {
        this.colFormat = colFormat;
    }

    /**
     * R 行头  C 列头  D 数值
     *
     * @return 显示类型
     */
    public String getShowType() {
        return showType;
    }

    /**
     * R 行头  C 列头  D 数值
     *
     * @param showType 显示类型
     */
    public void setShowType(String showType) {
        this.showType = showType;
    }

    public Double getAverageValue() {
        return averageValue;
    }

    public void setAverageValue(Double averageValue) {
        this.averageValue = averageValue;
    }

    public Double getSumValue() {
        return sumValue;
    }

    public void setSumValue(Double sumValue) {
        this.sumValue = sumValue;
    }

    public String getColProperty() {
        return colProperty;
    }

    public void setColProperty(String colProperty) {
        this.colProperty = colProperty;
    }

    public JSONObject toJsonData() {
        JSONObject objJson = new JSONObject();
        JSONOpt.setAttribute(objJson, "colName", this.getColName());
        JSONOpt.setAttribute(objJson, "averageValue", averageValue);
        JSONOpt.setAttribute(objJson, "sumValue", sumValue);
        JSONOpt.setAttribute(objJson, "optType", optType);
        JSONOpt.setAttribute(objJson, "drawChart", drawChart);
        JSONOpt.setAttribute(objJson, "colType", colType);
        JSONOpt.setAttribute(objJson, "colOrder", colOrder);
        JSONOpt.setAttribute(objJson, "colFormat", colFormat);
        JSONOpt.setAttribute(objJson, "colLogic", colLogic);
        JSONOpt.setAttribute(objJson, "isShow", isShow);
        JSONOpt.setAttribute(objJson, "showType", showType);
        JSONOpt.setAttribute(objJson, "cssStyle", cssStyle);

        return objJson;
    }

    public QueryModel getQueryModel() {
        return queryModel;
    }

    public void setQueryModel(QueryModel queryModel) {
        this.queryModel = queryModel;
    }
}
