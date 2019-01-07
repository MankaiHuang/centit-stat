package com.centit.stat.resource.po;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Data
@Entity
@Table(name = "Q_DATA_RESOURCE")
@ApiModel(value = "数据包")
public class DataResource {

    @Id
    @Column(name = "RESOURCE_ID")
    private String resourceId;

    @Column(name = "DATABASE_CODE")
    private String databaseCode;

    @Column(name = "OWNER_TYPE")
    private char ownerType;

    @Column(name = "OWNER_CODE")
    private String ownerCode;

    @Column(name = "QUERY_SQL")
    private String querySql;

    @Column(name = "QUERY_DESC")
    private String queryDesc;

    @Column(name = "RESOURCE_NAME_FORMAT")
    private String resourceNameFormat;

    @Column(name = "RECORDER")
    private String recorder;

    @Column(name = "RECORDER_DATE")
    private Date recorderDate;
}
