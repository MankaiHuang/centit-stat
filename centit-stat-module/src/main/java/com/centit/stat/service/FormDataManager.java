package com.centit.stat.service;

import com.centit.framework.core.dao.PageDesc;

public interface FormDataManager {
    public FormDataModel getDataModel(String modelName);
    
    /**
     * 
     * @param formData 数据查询模块，根据这个结构中的设置查询数据
     * @param page 分页信息
     * @return
     * @throws Exception 
     */
    public Integer queryFormData(FormDataModel formData,PageDesc page);
    
    /**
     * 查询对比分析数据
     * @param formData
     * @return
     */
    public Integer queryCompareData(FormDataModel formData);
    public Integer queryCompareData(FormDataModel formData,boolean needSum);
    
    /**
     * 查询交叉制表数据
     * @param formData
     * @return
     */
    public Integer queryCrossData(FormDataModel formData);
    public Integer queryCrossData(FormDataModel formData,boolean needSum);
    
}
