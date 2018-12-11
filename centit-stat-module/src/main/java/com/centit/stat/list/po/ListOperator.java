package com.centit.stat.list.po;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Model_Name           varchar(64) not null,
 *    opt_Name             varchar(64) not null,
 *    opt_Type             char(1) comment '更改、查看、警告 用来控制样式',
 *    opt_hint             varchar(200),
 *    opt_ORDER            numeric(2,0),
 *    OPT_URL_FORMAT       varchar(500) comment '连接url模板，需要根据值转换',
 */
@ApiModel
@Getter
@Setter
@Entity
@Table(name = "Q_LIST_OPERATOR")
public class ListOperator {

    @ApiModelProperty(value = "模板名称")
    @Id
    @Column(name = "MODEL_NAME")
    private String modelName;

    @ApiModelProperty(value = "操作按钮名称")
    @Id
    @Column(name = "OPT_NAME")
    private String optName;

    @ApiModelProperty(value = "操作类型（更改、查看、警告 用来控制样式）")
    @Column(name = "OPT_TYPE")
    private String optType;

    @ApiModelProperty(value = "操作按钮提示")
    @Column(name = "OPT_HINT")
    private String optHint;

    @ApiModelProperty(value = "操作按钮顺序")
    @Column(name = "OPT_ORDER")
    private int optOrder;

    @ApiModelProperty(value = "操作URL链接格式")
    @Column(name = "OPT_URL_FORMAT")
    private String optUrlFormat;
}
