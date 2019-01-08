package com.centit.stat.resource.po;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Data
@Entity
@Table(name = "Q_DATA_RESOURCE_COLUMN")
@ApiModel(value = "数据包字段")
public class DataResourceColumn implements Serializable {

    private static final long serialVersionUID = -4897866009902238572L;

    @Id
    @Column(name = "RESOURCE_ID")
    @ApiModelProperty(hidden = true)
    private String resourceId;

    @Id
    @Column(name = "COLUMN_CODE")
    @ApiModelProperty(value = "字段代码")
    private String columnCode;

    @Column(name = "COLUMN_NAME")
    @ApiModelProperty(value = "字段名称")
    private String columnName;

    @Column(name = "IS_STAT_DATA")
    @ApiModelProperty(value = "是否是统计数据")
    private char isStatData;

    @Column(name = "DATA_TYPE")
    @ApiModelProperty(value = "数据类型")
    private String dataType;

    @Column(name = "CATALOG_CODE")
    @ApiModelProperty(value = "对应数据字典代码")
    private String catalogCode;
}
