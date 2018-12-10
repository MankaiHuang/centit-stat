package com.centit.stat.query.service;

import com.centit.support.database.utils.PageDesc;

public interface FormDataManager {
    public FormDataModel getDataModel(String modelName);

    /**
     * 数据查询模块
     *
     * @param formData 根据这个结构中的设置查询数据
     * @param page     分页信息
     * @return Integer
     */
    public Integer queryFormData(FormDataModel formData, PageDesc page);

    /**
     * 查询对比分析数据
     *
     * @param formData 根据这个结构中的设置查询数据
     * @return Integer
     */
    public Integer queryCompareData(FormDataModel formData);

    public Integer queryCompareData(FormDataModel formData, boolean needSum);

    /**
     * 查询交叉制表数据
     *
     * @param formData 根据这个结构中的设置查询数据
     * @return Integer
     */
    public Integer queryCrossData(FormDataModel formData);

    public Integer queryCrossData(FormDataModel formData, boolean needSum);

}
