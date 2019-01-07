package com.centit.stat.resource.po;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "Q_DATA_RESOURCE_COLUMN")
@ApiModel(value = "数据包字段")
public class DataResourceColumn {

    @Id
    @Column(name = "RESOURCE_ID")
    private String resourceId;

    @Id
    @Column(name = "COLUMN_CODE")
    private String columnCode;

    @Column(name = "COLUMN_NAME")
    private String columnName;

    @Column(name = "IS_STAT_DATA")
    private char isStatData;

    @Column(name = "DATA_TYPE")
    private String dataType;

    @Column(name = "CATALOG_CODE")
    private String catalogCode;
}
