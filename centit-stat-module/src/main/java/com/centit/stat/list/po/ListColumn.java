package com.centit.stat.list.po;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 *    Model_Name           varchar(64) not null,
 *    col_Name             varchar(64) not null,
 *    col_Type             char(1),
 *    col_title            varchar(120),
 *    COL_ORDER            numeric(2,0),
 *    SHOW_TYPE            char(1) comment 'H:不显示，S：显示； V：显示数据字段对应值；D：和数据字典对应值一起显示',
 *    COL_FORMAT           varchar(64),
 *    LINK_TYPE            char(1) comment 'T/F',
 *    LINK_UTL_FORMAT      varchar(500) comment '连接url模板，需要根据值转换',
 *    catalog_code         varchar(64),
 *    catalog_value_code   varchar(64) comment '数据字典值对应的 代码',
 *    catalog_value_title  varchar(120) comment '数据字典值对应的 表头',
 */
@Getter
@Setter
@Entity
@Table(name = "Q_LIST_COLUMN")
public class ListColumn implements Serializable {

    @Id
    @Column(name = "MODEL_NAME")
    private String modelName;

    @Id
    @Column(name = "COL_NAME")
    private String colName;

    @Column(name = "COL_TYPE")
    private String colType;

    @Column(name = "COL_TITLE")
    private String colTitle;

    @Column(name = "COL_ORDER")
    private int colOrder;

    @Column(name = "SHOW_TYPE")
    private String showType;

    @Column(name = "COL_FORMAT")
    private String colFormat;

    @Column(name = "LINK_TYPE")
    private String linkType;

    @Column(name = "LINK_URL_FORMAT")
    private String linkUrlFormat;

    @Column(name = "CATALOG_CODE")
    private String catalogCode;

    @Column(name = "CATALOG_VALUE_CODE")
    private String catalogValueCode;

    @Column(name = "CATALOG_VALUE_TILE")
    private String catalogVAlueTitle;
}
