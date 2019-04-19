package com.centit.stat.po;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;
import com.centit.support.database.orm.GeneratorCondition;
import com.centit.support.database.orm.GeneratorTime;
import com.centit.support.database.orm.GeneratorType;
import com.centit.support.database.orm.ValueGenerator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Date;

/*
* Name	Code	Comment	Data Type	Length	Precision	Primary	Foreign Key	Mandatory
报表ID	form_ID		VARCHAR2(64)	64		TRUE	FALSE	TRUE
包ID	RESOURCE_ID		VARCHAR2(64)	64		FALSE	TRUE	FALSE
报表名称模板	from_Name_Format		VARCHAR2(256)	256		FALSE	FALSE	FALSE
报表类型	fromt_type	2 二维表 3 同比分析 4 环比分析 5 交叉制表	VARCHAR2(16)	16		FALSE	FALSE	FALSE
更改人员	Recorder		VARCHAR2(32)	32		FALSE	FALSE	FALSE
更改时间	RecordDate		DATE			FALSE	FALSE	FALSE
*/
@Data
@Entity
@ApiModel
@Table(name = "Q_FORM_MODEL")
public class FormModel implements Serializable {

    private static final long serialVersionUID = 6075169482385433049L;

    @Id
    @Column(name = "FORM_ID")
    @ApiModelProperty(value = "报表ID", hidden = true)
    @ValueGenerator(strategy = GeneratorType.UUID)
    private String formId;

    @Column(name = "PACKET_ID")
    @ApiModelProperty(value = "包ID", required = true)
    @NotBlank
    private String packetId;

    @ApiModelProperty(value = "数据查询ID", hidden = true)
    @Column(name = "QUERY_ID")
    private String queryId;

    @Column(name = "DATA_SET_NAME")
    @ApiModelProperty(value = "数据集名", required = true)
    @NotBlank
    private String dataSetName;

    @Column(name = "FROM_NAME_FORMAT")
    @ApiModelProperty(value = "报表名称模板", required = true)
    @NotBlank
    private String formNameFormat;

    @Column(name = "FORM_TYPE")
    @ApiModelProperty(value = "报表类型", required = true)
    @NotBlank
    private String formType;

    @Column(name = "RECORDER")
    @ApiModelProperty(value = "更改人员", hidden = true)
    private String recorder;

    @Column(name = "RECORD_DATE")
    @ApiModelProperty(value = "更改时间", hidden = true)
    @ValueGenerator(strategy = GeneratorType.FUNCTION, occasion = GeneratorTime.NEW_UPDATE, condition = GeneratorCondition.ALWAYS, value = "today()")
    private Date recordDate;

    @JSONField(serialize=false)
    @Column(name = "FORM_DESIGN_JSON")
    @ApiModelProperty(value = "图表自定义属性 json格式的图表自定义说明", required = true)
    private String formDesignJson;


    public JSONObject getFormDesign() {
        if(StringUtils.isBlank(formDesignJson)) {
            return null;
        }
        return JSONObject.parseObject(formDesignJson);
    }
}
