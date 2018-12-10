package com.centit.stat.list.po;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Model_Name           varchar(64) not null,
 * model_title_Format   varchar(256),
 * Database_Code        varchar(32),
 * Owner_Type           char(1) comment '机构或者个人
 * Owner_Code           varchar(64),
 * QUERY_SQL            varchar(4000),
 * Query_DESC           varchar(512),
 * page_type            char(1) comment 'T/F',
 * page_size            numeric(4,0),
 * creator              varchar(32),
 * create_time          datetime,
 */
@Getter
@Setter
@ApiModel(value = "列表模块")
@Entity
@Table(name = "Q_LIST_MODEL")
public class ListModel implements Serializable {

    /**
     * 模块名称
     */
    @ApiModelProperty(name = "modelName", value = "模块名称")
    @Id
    @Column(name = "MODEL_NAME")
    private String modelName;

    /**
     * 名称模板
     */
    @ApiModelProperty(name = "modelTitleFormat", value = "名称模板")
    @Column(name = "MODEL_TITLE_FORMAT")
    private String modelTitleFormat;

    /**
     * 数据库
     */
    @ApiModelProperty(name = "databaseCode", value = "数据库ID")
    @Column(name = "DATABASE_CODE")
    private String databaseCode;

    /**
     * 属主类别
     */
    @ApiModelProperty(name = "ownerType", value = "属主类别")
    @Column(name = "OWNER_TYPE")
    private String ownerType;

    /**
     * 属主代码
     */
    @ApiModelProperty(name = "ownerCode", value = "属主代码")
    @Column(name = "OWNER_CODE")
    private String ownerCode;

    /**
     * 查询sql
     */
    @ApiModelProperty(name = "querySql", value = "查询Sql")
    @Column(name = "QUERY_SQL")
    private String querySql;

    /**
     * 查询描述
     */
    @ApiModelProperty(name = "queryDesc", value = "查询描述")
    @Column(name = "QUERY_DESC")
    private String queryDesc;

    @ApiModelProperty(name = "pageType", value = "分页类别")
    @Column(name = "PAGE_TYPE")
    private String pageType;

    @ApiModelProperty(name = "pageSize", value = "分页大小")
    @Column(name = "PAGE_SIZE")
    private int pageSize;

    @ApiModelProperty(name = "creator", value = "创建人")
    @Column(name = "CREATOR")
    private String creator;

    @ApiModelProperty(name = "createTime", value = "创建时间")
    @Column(name = "CREATE_TIME")
    private Date createTime;

    @OneToMany(targetEntity = ListColumn.class)
    @JoinColumn(name = "modelName", referencedColumnName = "modelName")
    private List<ListColumn> columns;

    @OneToMany(targetEntity = ListCondition.class)
    @JoinColumn(name = "modelName", referencedColumnName = "modelName")
    private List<ListCondition> conditions;

    @OneToMany(targetEntity = ListOperator.class)
    @JoinColumn(name = "modelName", referencedColumnName = "modelName")
    private List<ListOperator> operators;
}
