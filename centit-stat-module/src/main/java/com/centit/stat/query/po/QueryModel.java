package com.centit.stat.query.po;

import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 查询统计模型
 */
@Entity
@Table(name = "Q_QUERY_MODEL")
public class QueryModel implements java.io.Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 模块名称/模块代码
     */
    @Id
    @Column(name = "MODEL_NAME")
    private String modelName;
    /**
     * 数据库外键
     */
    @Column(name = "Database_Code")
    private String databaseCode;

    @Transient
    private String databaseName;
    /**
     * 2 ： 二维表  3 ：同比分析 4：环比分析 5：交叉制表
     */
    @Column(name = "MODEL_TYPE")
    private String modelType;

    /**
     * 属主类别
     */
    @Column(name = "OWNER_TYPE")
    private String ownerType;

    /**
     * 属主代码
     */
    @Column(name = "OWNER_CODE")
    @Length(min = 0, max = 8, message = "字段长度不能小于{min}大于{max}")
    private String ownerCode;

    /**
     * 查询语句
     */
    @Column(name = "QUERY_SQL")
//    @Length(min = 0, max = 4000, message = "字段长度不能小于{min}大于{max}")
    private String querySql;

    /**
     * 查询描述
     */
    @Column(name = "QUERY_DESC")
    @Length(min = 0, max = 512, message = "字段长度不能小于{min}大于{max}")
    private String queryDesc;

    /**
     * 模块中文名
     */
    @Column(name = "FORM_NAME_FORMAT")
    @Length(min = 0, max = 256, message = "字段长度不能小于{min}大于{max}")
    private String formNameFormat;// 关于&{year}的统计

    /**
     * 返回页面
     */
    @Column(name = "RESULT_NAME")
    @Length(min = 0, max = 64, message = "字段长度不能小于{min}大于{max}")
    private String resultName;

    /**
     * 是否按行画图
     */
    @Column(name = "ROW_DRAW_CHART")
    private String rowDrawChart;

    /**
     * 画图数据起始列
     */
    @Column(name = "DRAW_CHART_BEGIN_COL")
    @Digits(integer = 4, fraction = 0, message = "字段范围整数{integer}位小数{fraction}位")
    private Integer drawChartBeginCol;
    @Column(name = "DRAW_CHART_END_COL")
    @Digits(integer = 4, fraction = 0, message = "字段范围整数{integer}位小数{fraction}位")
    private Integer drawChartEndCol;
    @Column(name = "ADDITION_ROW")
    private String additionRow;
    @Column(name = "ROW_LOGIC")
    @Length(min = 0, max = 64, message = "字段长度不能小于{min}大于{max}")
    private String rowLogic;
    @Column(name = "ROW_LOGIC_VALUE")
    @Digits(integer = 4, fraction = 0, message = "字段范围整数{integer}位小数{fraction}位")
    private Long rowLogicValue;
    @Column(name = "LOGIC_URL")
    @Length(min = 0, max = 512, message = "字段长度不能小于{min}大于{max}")
    private String logicUrl;
    @Column(name = "IS_TREE")
    @Length(min = 0, max = 8, message = "字段长度不能小于{min}大于{max}")
    private String isTree;
    @Column(name = "WIZARD_NO")
    @Length(min = 0, max = 32, message = "字段长度不能小于{min}大于{max}")
    private String wizardNo;
    @Column(name = "COLUMN_SQL")
    @Length(min = 0, max = 2048, message = "字段长度不能小于{min}大于{max}")
    private String columnSql;

    @OneToMany(targetEntity = QueryColumn.class, mappedBy = "queryModel", orphanRemoval = true, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @OrderBy("colOrder")
    @JoinColumn(name = "MODEL_NAME", referencedColumnName = "MODEL_NAME")
    private List<QueryColumn> queryColumns = null;// new ArrayList<QueryColumn>();

    @OneToMany(targetEntity = QueryCondition.class, mappedBy = "queryModel", orphanRemoval = true, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @OrderBy("condOrder")
    @JoinColumn(name = "MODEL_NAME", referencedColumnName = "MODEL_NAME")
    private List<QueryCondition> queryConditions = null;// new ArrayList<QueryConditon>();

    // Constructors
    public QueryModel() {
        this.drawChartBeginCol = 0;
        this.drawChartEndCol = 0;
    }

    public QueryModel(
        String modelName
    ) {
        this.modelName = modelName;
        this.drawChartBeginCol = 0;
        this.drawChartEndCol = 0;
    }


    public String getDatabaseCode() {
        return databaseCode;
    }

    public void setDatabaseCode(String database) {
        this.databaseCode = database;
    }

    public void setDataBaseName(String dataBaseName) {
        this.databaseName = dataBaseName;
    }

    public String getIsTree() {
        return isTree;
    }

    public void setIsTree(String isTree) {
        this.isTree = isTree;
    }

    public QueryModel(
        String modelName
        , String modelType, String ownerType, String ownerCode, String querySql
        , String queryDesc, String formNameFormat, String resultName
        , String rowDrawChart, Integer drawChartBeginCol, Integer drawChartEndCol
        , String additionRow, String rowLogic, Long rowLogicValue, String logicUrl, String wizardNo) {


        this.modelName = modelName;

        this.modelType = modelType;
        this.ownerType = ownerType;
        this.ownerCode = ownerCode;
        this.querySql = querySql;
        this.queryDesc = queryDesc;
        this.formNameFormat = formNameFormat;
        this.resultName = resultName;
        this.rowDrawChart = rowDrawChart;
        this.drawChartBeginCol = drawChartBeginCol;
        this.drawChartEndCol = drawChartEndCol;
        this.additionRow = additionRow;
        this.rowLogic = rowLogic;
        this.rowLogicValue = rowLogicValue;
        this.logicUrl = logicUrl;
        this.wizardNo = wizardNo;
    }


    public String getWizardNo() {
        return wizardNo;
    }

    public void setWizardNo(String wizardNo) {
        this.wizardNo = wizardNo;
    }

    public String getLogicUrl() {
        return logicUrl;
    }

    public void setLogicUrl(String logicUrl) {
        this.logicUrl = logicUrl;
    }

    public String getModelName() {
        return this.modelName;
    }

    public String getColumnSql() {
        return columnSql;
    }

    public void setColumnSql(String columnSql) {
        this.columnSql = columnSql;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }
    // Property accessors

    /**
     * 2 ： 二维表  3 ：同比分析 4：环比分析 5：交叉制表
     *
     * @return 模型类型
     */
    public String getModelType() {
        return this.modelType;
    }

    /**
     * 2 ： 二维表  3 ：同比分析 4：环比分析 5：交叉制表
     *
     * @param modelType 模型类型
     */
    public void setModelType(String modelType) {
        this.modelType = modelType;
    }

    public String getOwnerType() {
        return this.ownerType;
    }

    public void setOwnerType(String ownerType) {
        this.ownerType = ownerType;
    }

    public String getOwnerCode() {
        return this.ownerCode;
    }

    public void setOwnerCode(String ownerCode) {
        this.ownerCode = ownerCode;
    }

    public String getQuerySql() {
        return this.querySql;
    }

    public void setQuerySql(String querySql) {
        this.querySql = querySql;
    }

    public String getQueryDesc() {
        return this.queryDesc;
    }

    public void setQueryDesc(String queryDesc) {
        this.queryDesc = queryDesc;
    }

    public String getFormNameFormat() {
        return this.formNameFormat;
    }

    public void setFormNameFormat(String formNameFormat) {
        this.formNameFormat = formNameFormat;
    }

    public String getResultName() {
        return this.resultName;
    }

    public void setResultName(String resultName) {
        this.resultName = resultName;
    }

    public String getRowDrawChart() {
        return this.rowDrawChart;
    }

    public void setRowDrawChart(String rowDrawChart) {
        this.rowDrawChart = rowDrawChart;
    }

    public Integer getDrawChartBeginCol() {
        if (this.drawChartBeginCol != null)
            return this.drawChartBeginCol;
        return 0;
    }

    public void setDrawChartBeginCol(Integer drawChartBeginCol) {
        this.drawChartBeginCol = drawChartBeginCol;
    }

    public Integer getDrawChartEndCol() {
        if (this.drawChartEndCol != null)
            return this.drawChartEndCol;
        return 0;
    }

    public void setDrawChartEndCol(Integer drawChartEndCol) {
        this.drawChartEndCol = drawChartEndCol;
    }

    public String getAdditionRow() {
        return this.additionRow;
    }

    public void setAdditionRow(String additionRow) {
        this.additionRow = additionRow;
    }

    public String getRowLogic() {
        return this.rowLogic;
    }

    public void setRowLogic(String rowLogic) {
        this.rowLogic = rowLogic;
    }

    public Long getRowLogicValue() {
        return this.rowLogicValue;
    }

    public void setRowLogicValue(Long rowLogicValue) {
        this.rowLogicValue = rowLogicValue;
    }


    public List<QueryColumn> getQueryColumns() {
        if (this.queryColumns == null)
            this.queryColumns = new ArrayList<QueryColumn>();
        return this.queryColumns;
    }

    public void setQueryColumns(List<QueryColumn> queryColumns) {
        if (null == this.queryColumns)
            this.queryColumns = new ArrayList<QueryColumn>();
        this.queryColumns = queryColumns;
    }

    public void addQueryColumn(QueryColumn queryColumn) {
        if (this.queryColumns == null)
            this.queryColumns = new ArrayList<QueryColumn>();
        this.queryColumns.add(queryColumn);
    }

    public void removeQueryColumn(QueryColumn queryColumn) {
        if (this.queryColumns == null)
            return;
        this.queryColumns.remove(queryColumn);
    }

    public QueryColumn newQueryColumn() {
        QueryColumn res = new QueryColumn();

        res.setModelName(this.getModelName());

        return res;
    }

    //替换子类对象数组，这个函数主要是考虑hibernate中的对象的状态，以避免对象状态不一致的问题

    public void replaceQueryColumns(List<QueryColumn> queryColumns) {
        List<QueryColumn> newObjs = new ArrayList<QueryColumn>();
        for (QueryColumn p : queryColumns) {
            if (p == null)
                continue;
            QueryColumn newdt = newQueryColumn();
            newdt.copyNotNullProperty(p);
            newObjs.add(newdt);
        }
        //delete
        boolean found = false;
        List<QueryColumn> oldObjs = new ArrayList<QueryColumn>();
        oldObjs.addAll(getQueryColumns());

        for (Iterator<QueryColumn> it = oldObjs.iterator(); it.hasNext(); ) {
            QueryColumn odt = it.next();
            found = false;
            for (QueryColumn newdt : newObjs) {
                if (odt.getModelName().equals(newdt.getModelName()) && odt.getColName().equals(newdt.getColName())) {
                    found = true;
                    break;
                }
            }
            if (!found)
                removeQueryColumn(odt);
        }
        oldObjs.clear();
        //insert or update
        for (QueryColumn newdt : newObjs) {
            found = false;
            for (Iterator<QueryColumn> it = getQueryColumns().iterator();
                 it.hasNext(); ) {
                QueryColumn odt = it.next();
                if (odt.getModelName().equals(newdt.getModelName()) && odt.getColName().equals(newdt.getColName())) {
                    odt.copy(newdt);
                    found = true;
                    break;
                }
            }
            if (!found)
                addQueryColumn(newdt);
        }
    }

    public List<QueryCondition> getQueryConditions() {
        if (this.queryConditions == null)
            this.queryConditions = new ArrayList<QueryCondition>();
        return this.queryConditions;
    }

    public void setQueryConditions(List<QueryCondition> queryConditons) {
        if (null == this.queryConditions)
            this.queryConditions = new ArrayList<QueryCondition>();
        this.queryConditions = queryConditons;
    }

    public void addQueryConditon(QueryCondition queryConditon) {
        if (this.queryConditions == null)
            this.queryConditions = new ArrayList<QueryCondition>();
        this.queryConditions.add(queryConditon);
    }

    public void removeQueryConditon(QueryCondition queryConditon) {
        if (this.queryConditions == null)
            return;
        this.queryConditions.remove(queryConditon);
    }

    public QueryCondition newQueryConditon() {
        QueryCondition res = new QueryCondition();

        res.setModelName(this.getModelName());

        return res;
    }

    /**
     * 替换子类对象数组，这个函数主要是考虑hibernate中的对象的状态，以避免对象状态不一致的问题
     *
     * @param queryConditons 查询条件
     */
    public void replaceQueryConditions(List<QueryCondition> queryConditons) {
        List<QueryCondition> newObjs = new ArrayList<QueryCondition>();
        for (QueryCondition p : queryConditons) {
            if (p == null)
                continue;
            QueryCondition newdt = newQueryConditon();
            newdt.copyNotNullProperty(p);
            newObjs.add(newdt);
        }
        //delete
        boolean found = false;
        List<QueryCondition> oldObjs = new ArrayList<QueryCondition>();
        oldObjs.addAll(getQueryConditions());

        for (Iterator<QueryCondition> it = oldObjs.iterator(); it.hasNext(); ) {
            QueryCondition odt = it.next();
            found = false;
            for (QueryCondition newdt : newObjs) {
                if (odt.getModelName().equals(newdt.getModelName())&&odt.getCondName().equals(newdt.getCondName())) {
                    found = true;
                    break;
                }
            }
            if (!found)
                removeQueryConditon(odt);
        }
        oldObjs.clear();
        //insert or update
        for (QueryCondition newdt : newObjs) {
            found = false;
            for (Iterator<QueryCondition> it = getQueryConditions().iterator();
                 it.hasNext(); ) {
                QueryCondition odt = it.next();
                if (odt.getModelName().equals(newdt.getModelName())&&odt.getCondName().equals(newdt.getCondName())) {
                    odt.copy(newdt);
                    found = true;
                    break;
                }
            }
            if (!found)
                addQueryConditon(newdt);
        }
    }


    public void copy(QueryModel other) {

        this.setModelName(other.getModelName());
        this.setDatabaseCode(other.getDatabaseCode());
        this.modelType = other.getModelType();
        this.ownerType = other.getOwnerType();
        this.ownerCode = other.getOwnerCode();
        this.querySql = other.getQuerySql();
        this.queryDesc = other.getQueryDesc();
        this.formNameFormat = other.getFormNameFormat();
        this.resultName = other.getResultName();
        this.rowDrawChart = other.getRowDrawChart();
        this.drawChartBeginCol = other.getDrawChartBeginCol();
        this.drawChartEndCol = other.getDrawChartEndCol();
        this.additionRow = other.getAdditionRow();
        this.rowLogic = other.getRowLogic();
        this.rowLogicValue = other.getRowLogicValue();
        this.logicUrl = other.getLogicUrl();
        this.queryColumns = other.getQueryColumns();
        this.queryConditions = other.getQueryConditions();
        this.columnSql = other.getColumnSql();
        this.isTree = other.getIsTree();
        this.wizardNo = other.getWizardNo();
    }

    public void copyNotNullProperty(QueryModel other) {

        if (other.getModelName() != null)
            this.setModelName(other.getModelName());
        if (other.getDatabaseCode() != null)
            this.setDatabaseCode(other.getDatabaseCode());
        if (other.getModelType() != null)
            this.modelType = other.getModelType();
        if (other.getOwnerType() != null)
            this.ownerType = other.getOwnerType();
        if (other.getOwnerCode() != null)
            this.ownerCode = other.getOwnerCode();
        if (other.getQuerySql() != null)
            this.querySql = other.getQuerySql();
        if (other.getQueryDesc() != null)
            this.queryDesc = other.getQueryDesc();
        if (other.getFormNameFormat() != null)
            this.formNameFormat = other.getFormNameFormat();
        if (other.getResultName() != null)
            this.resultName = other.getResultName();
        if (other.getRowDrawChart() != null)
            this.rowDrawChart = other.getRowDrawChart();
        if (other.getDrawChartBeginCol() != null)
            this.drawChartBeginCol = other.getDrawChartBeginCol();
        if (other.getDrawChartEndCol() != null)
            this.drawChartEndCol = other.getDrawChartEndCol();
        if (other.getAdditionRow() != null)
            this.additionRow = other.getAdditionRow();
        if (other.getRowLogic() != null)
            this.rowLogic = other.getRowLogic();
        if (other.getRowLogicValue() != null)
            this.rowLogicValue = other.getRowLogicValue();
        if (other.getLogicUrl() != null)
            this.logicUrl = other.getLogicUrl();
        if (other.getColumnSql() != null)
            this.columnSql = other.getColumnSql();
        this.isTree = other.isTree;
        if (other.getWizardNo() != null)
            this.wizardNo = other.getWizardNo();
        if (null != other.getQueryColumns())
            replaceQueryColumns(other.getQueryColumns());
        if (null != other.getQueryConditions())
            replaceQueryConditions(other.getQueryConditions());
    }

    public void clearProperties() {
        this.modelType = null;
        this.columnSql = null;
        this.ownerType = null;
        this.ownerCode = null;
        this.setDatabaseCode(null);
        this.querySql = null;
        this.queryDesc = null;
        this.formNameFormat = null;
        this.resultName = null;
        this.rowDrawChart = null;
        this.drawChartBeginCol = 0;
        this.drawChartEndCol = 0;
        this.additionRow = null;
        this.rowLogic = null;
        this.rowLogicValue = null;
        this.logicUrl = null;
        this.isTree = null;
        this.wizardNo = null;
        this.queryColumns = new ArrayList<QueryColumn>();
        this.queryConditions = new ArrayList<QueryCondition>();
    }
}
