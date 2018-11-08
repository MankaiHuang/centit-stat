package com.centit.stat.list.po;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 *  Model_Name           varchar(64) not null,
 * model_title_Format varchar(256),
 * Database_Code        varchar(32),
 * Owner_Type           char(1) comment '机构或者个人
 * Owner_Code           varchar(64),
 * QUERY_SQL            varchar(4000),
 * Query_DESC           varchar(512),
 * page_type            char(1) comment 'T/F',
 * page_size            numeric(4,0),
 * creator              varchar(32),
 * create_time           datetime,
 */
@Entity
@Table(name = "Q_LIST_MODEL")
public class ListModel implements Serializable {

    @Id
    @Column(name = "MODEL_NAME")
    private String modelName;

    @Column(name = "MODEL_TITLE_FORMAT")
    private String modelTitleFormat;

    @Column(name = "DATABASE_CODE")
    private String databaseCode;

    @Column(name = "OWNER_TYPE")
    private String ownerType;

    @Column(name = "OWNER_CODE")
    private String ownerCode;

    @Column(name = "QUERY_SQL")
    private String querySql;

    @Column(name = "QUERY_DESC")
    private String queryDesc;

    @Column(name = "PAGE_TYPE")
    private String pageType;

    @Column(name = "PAGE_SIZE")
    private int pageSize;

    @Column(name = "CREATOR")
    private String creator;

    @Column(name = "CREATE_TIME")
    private Date createTime;
}
