package com.centit.stat.po;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Digits;

import org.hibernate.validator.constraints.Length;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;
import com.centit.support.json.JSONOpt;

@Entity
@Table(name = "Q_QUERYCOLUMN")
public class QueryColumn implements java.io.Serializable {
	private static final long serialVersionUID =  1L;
	@EmbeddedId
	private QueryColumnId cid;
	
	/**
	 * 数据操作 0：无操作  1：合计  2：平均  3：平均 合计
	 */
	@Column(name = "OPTTYPE")
	private String  optType;
	@Column(name = "DRAWCHART")
	private String  drawChart;
	@Column(name = "COLTYPE")
	private String  colType;
	@Column(name = "COLFORMAT")
    @Length(min = 0, max = 120, message = "字段长度不能小于{min}大于{max}")
	private String colFormat;
	@Column(name = "COLLOGIC")
    @Length(min = 0, max = 120, message = "字段长度不能小于{min}大于{max}")
	private String  colLogic;
	@Column(name = "COLORDER")
    @Digits(integer = 2, fraction = 0, message = "字段范围整数{integer}位小数{fraction}位")
	private Integer colOrder;
	@Column(name = "ISSHOW")
    private String isShow;
	
	@Column(name = "CATALOGCODE")
	private String catalogCode;
    /**
     * R 行头  C 列头  D 数值
     */
	@Column(name = "SHOWTYPE")
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
    @Column(name = "LINKTYPE")
    @Length(min = 0, max = 64, message = "字段长度不能小于{min}大于{max}")
    private String linkType;
    @ManyToOne
	@JSONField(serialize=false)
    @JoinColumn(name = "MODELNAME", insertable = false, updatable = false)
    private QueryModel queryModel;
    
    /**
     * 用于指定单元格样式
     */
    @Column(name = "CSSSTYLE")
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

	public QueryColumn(QueryColumnId id 
				
		) {
		this.cid = id; 
			
			
	}
	
	public QueryColumn(Integer colOrder, String colName,String colType,String colFormat, String optType,String drawChart,String colLogic, String isShow){
	    this.cid = new QueryColumnId();
        this.cid.setColName(colName);
        this.optType = optType;
        this.colType = colType;
        this.drawChart = drawChart;
        this.averageValue = 0.0;
        this.sumValue = 0.0;
        this.colLogic = colLogic;
        this.isShow = isShow;
        this.colFormat = colFormat;
        this.colOrder = colOrder;
        this.cssStyle="";
    }
    
    public QueryColumn(String colName,String colType,String optType,String drawChart){
        this(0, colName,colType,optType,"",drawChart,null,"T");
    }
    
    public QueryColumn(String colName,String colType,String optType){
        this(0, colName,colType,optType,"","F",null,"T");
    } 
    
    public QueryColumn(String colName,String colType){
        this(0, colName,colType,"","0","F",null,"T");
    }
    

	public QueryColumn(QueryColumnId id
			
	,String  optType,String  drawChart,String  colType,String  colLogic,Integer colorder,String isShow,String showType) {
		this.cid = id; 
			
		this.optType= optType;
		this.drawChart= drawChart;
		this.colType= colType;
		this.colLogic= colLogic;	
		this.colOrder=colorder;
		this.isShow = isShow;
        this.showType= showType;

	}

	public QueryColumnId getCid() {
		return this.cid;
	}
	
	public void setCid(QueryColumnId id) {
		this.cid = id;
	}
	public String getIsShow() {
        return isShow;
    }
    public void setIsShow(String isShow) {
        this.isShow = isShow;
    }
	public String getModelName() {
		if(this.cid==null)
			this.cid = new QueryColumnId();
		return this.cid.getModelName();
	}
	
	public void setModelName(String modelName) {
		if(this.cid==null)
			this.cid = new QueryColumnId();
		this.cid.setModelName(modelName);
	}
  
	public String getColName() {
		if(this.cid==null)
			this.cid = new QueryColumnId();
		return this.cid.getColName();
	}
	
	public void setColName(String colName) {
		if(this.cid==null)
			this.cid = new QueryColumnId();
		this.cid.setColName(colName);
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

	public QueryColumn copy(QueryColumn other){
  
		this.setModelName(other.getModelName());  
		this.setColName(other.getColName());
  
		this.optType= other.getOptType();  
		this.drawChart= other.getDrawChart();  
		this.colType= other.getColType();  
		this.colLogic= other.getColLogic();
        this.colOrder=other.getColOrder();
        this.isShow = other.getIsShow();
        this.showType= other.getShowType();
        this.colFormat = other.getColFormat();
        this.linkType = other.getLinkType();
        this.cssStyle=other.getCssStyle();
        this.catalogCode=other.getCatalogCode();
        return this;
	}
	
	public QueryColumn copyNotNullProperty(QueryColumn other){
  
    	if( other.getModelName() != null)
    		this.setModelName(other.getModelName());  
    	if( other.getColName() != null)
    		this.setColName(other.getColName());
    	if(other.getCssStyle()!=null)
    		this.setCssStyle(other.getCssStyle());
		if( other.getOptType() != null)
			this.optType= other.getOptType();  
		if( other.getDrawChart() != null)
			this.drawChart= other.getDrawChart();  
		if( other.getColType() != null)
			this.colType= other.getColType();  
		if( other.getColLogic() != null)
			this.colLogic= other.getColLogic();
		if( other.getColOrder() != null)
            this.colOrder= other.getColOrder();
		if( other.getIsShow() != null)
            this.isShow= other.getIsShow();
        if( other.getShowType() != null)
            this.showType= other.getShowType();
        if(other.getLinkType()!=null) 
            this.linkType = other.getLinkType();
		if (other.getColFormat() != null) {
		    this.colFormat = other.getColFormat();
		}
		if(other.getCatalogCode()!=null)
			{this.catalogCode=other.getCatalogCode();}
		return this;
	}
	
	public QueryColumn clearProperties(){
  
		this.optType= null;  
		this.drawChart= null;  
		this.colType= null;  
		this.colLogic= null;
		this.colOrder=null;
		this.isShow=null;
		this.showType="D";
		this.colFormat = null;
		this.linkType = null;
		this.cssStyle=null;
		this.catalogCode=null;
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
     * @return 显示类型
     */
    public String getShowType() {
        return showType;
    }
    /**
     * R 行头  C 列头  D 数值
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
	
    public JSONObject toJsonData()
    {
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
