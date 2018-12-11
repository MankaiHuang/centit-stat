package com.centit.stat.list.po;

import lombok.Data;

import java.util.List;

@Data
public class ListData {

    ListModel model;

    String modelTitle;

    int pageSize;

    int pageNo;

    int totalRows;

    List<ListColumn> columns;

    List<ListCondition> conditions;

    List<ListOperator> operators;

    List<Object[]> data;

    /**
     * 初始化配置信息
     */
    public void initList(ListModel model){
        setModel(model);
        setModelTitle(model.getModelTitleFormat());//名称转换 参数值用属性值填充
        setPageSize(model.getPageSize());
        if(model.getColumns() != null && model.getColumns().size() > 0){
            setColumns(model.getColumns());
        }
        if(model.getConditions() != null && model.getConditions().size() > 0){
            setConditions(model.getConditions());
        }
        if(model.getOperators() != null && model.getOperators().size() > 0){
            setOperators(model.getOperators());
        }
    }

    /**
     * 加在数据
     */
    public void loadData(){

    }


}
