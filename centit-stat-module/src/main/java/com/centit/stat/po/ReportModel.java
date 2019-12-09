package com.centit.stat.po;

import com.centit.support.database.orm.GeneratorCondition;
import com.centit.support.database.orm.GeneratorTime;
import com.centit.support.database.orm.GeneratorType;
import com.centit.support.database.orm.ValueGenerator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Date;

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

    @Id
    @Column(name = "REPORT_ID")
    @ApiModelProperty(value = "图表ID", hidden = true)
    @ValueGenerator(strategy = GeneratorType.UUID)
    private String reportId;

    @Column(name = "PACKET_ID")
    @ApiModelProperty(value = "包ID", required = true)
    @NotBlank
    private String packetId;

    @Column(name = "REPORT_NAME_FORMAT")
    @ApiModelProperty(value = "图表名称模板", required = true)
    @NotBlank
    private String reportNameFormat;

    /**
     * report 模板文件id
     */
    @Column(name = "REPORT_DOC_FILEID")
    @ApiModelProperty(value = "报表文书ID")
    public String reportDocFileId;

    @Column(name = "RECORDER")
    @ApiModelProperty(value = "更改人员", hidden = true)
    private String recorder;

    @Column(name = "RECORD_DATE")
    @ApiModelProperty(value = "更改时间", hidden = true)
    @ValueGenerator(strategy = GeneratorType.FUNCTION, occasion = GeneratorTime.NEW_UPDATE, condition = GeneratorCondition.ALWAYS, value = "today()")
    private Date recordDate;

    @ApiModelProperty(value = "业务模块代码")
    @Column(name = "APPLICATION_ID")
    private String  applicationId;
    @ApiModelProperty(value = "图片json")
    @Column(name = "PHOTO_JS")
    private String  photoJs;
}
