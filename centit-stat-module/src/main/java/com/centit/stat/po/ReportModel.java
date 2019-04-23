package com.centit.stat.po;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * Name	Code	Data Type	Length	Precision	Primary	Foreign Key	Mandatory
 * 模块名称	Model_Name	VARCHAR2(64)	64		TRUE	FALSE	TRUE
 * 格式报表名称模板	model_title_Format	VARCHAR2(256)	256		FALSE	FALSE	FALSE
 * 属主类别	Owner_Type	CHAR(1)	1		FALSE	FALSE	FALSE
 * 属主代码	Owner_Code	VARCHAR2(64)	64		FALSE	FALSE	FALSE
 * 报表描述	MODEL_DESC	VARCHAR2(512)	512		FALSE	FALSE	FALSE
 * 报表模板	report_doc_fileId	VARCHAR2(64)	64		FALSE	FALSE	FALSE
 */
@ApiModel
@Getter
@Setter
@Entity
@Table(name = "Q_REPORT_MODEL")
public class ReportModel implements Serializable {
    @ApiModelProperty(value = "模板名称")
    @Id
    @Column(name = "MODEL_NAME")
    public String modelName;

    @Column(name = "PACKET_ID")
    @ApiModelProperty(value = "包ID", required = true)
    @NotBlank
    private String packetId;

    @ApiModelProperty(value = "格式报表名称模板")
    @Column(name = "REPORT_TITLE_FORMAT")
    public String reportTitleFormat;

    @Column(name = "OWNER_TYPE")
    @ApiModelProperty(value = "属主类别")
    @Length(max = 1)
    public String ownerType;

    @Column(name = "OWNER_CODE")
    @ApiModelProperty(value = "属主代码")
    public String ownerCode;

    @Column(name = "REPORT_DESC")
    @ApiModelProperty(value = "报表文书描述")
    public String reportDesc;

    @Column(name = "REPORT_DOC_FILEID")
    @ApiModelProperty(value = "报表文书ID")
    public String reportDocFileId;

}
