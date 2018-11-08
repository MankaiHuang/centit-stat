package com.centit.stat.list.po;

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
@Entity
@Table(name = "Q_LIST_OPERATOR")
public class ListOperator {

    @Id
    @Column(name = "MODEL_NAME")
    private String modelName;

    @Id
    @Column(name = "OPT_NAME")
    private String optName;

    @Column(name = "OPT_TYPE")
    private String optType;

    @Column(name = "OPT_HINT")
    private String optHint;

    @Column(name = "OPT_ORDER")
    private int optOrder;

    @Column(name = "OPT_URL_FORMAT")
    private String optUrlFormat;
}
