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

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Date;

/*Name	Code	Comment	Data Type	Length	Precision	Primary	Foreign Key	Mandatory
图表ID	chart_ID		VARCHAR2(64)	64		TRUE	FALSE	TRUE
包ID	RESOURCE_ID		VARCHAR2(64)	64		FALSE	TRUE	FALSE
图表名称模板	chart_Name_Format		VARCHAR2(256)	256		FALSE	FALSE	FALSE
图表类型	chart_type		VARCHAR2(16)	16		FALSE	FALSE	FALSE
图表自定义属性	chart_design	json格式的图表自定义说明	CLOB			FALSE	FALSE	FALSE
更改人员	Recorder		VARCHAR2(32)	32		FALSE	FALSE	FALSE
更改时间	RecordDate		DATE			FALSE	FALSE	FALSE*/
@Data
@Entity
@ApiModel
@Table(name = "Q_CHART_MODEL")
public class ChartModel implements Serializable {

    private static final long serialVersionUID = -386841647338129662L;

    @Id
    @Column(name = "CHART_ID")
    @ApiModelProperty(value = "图表ID", hidden = true)
    @ValueGenerator(strategy = GeneratorType.UUID)
    private String chartId;

    @Column(name = "PACKET_ID")
    @ApiModelProperty(value = "包ID", required = true)
    @NotBlank
    private String packetId;

    @Column(name = "CHART_NAME_FORMAT")
    @ApiModelProperty(value = "图表名称模板", required = true)
    @NotBlank
    private String chartNameFormat;

    // C chart F form R report  default:C
    @Column(name = "CHART_CATALOG")
    @ApiModelProperty(value = "统计类型 C：图表；F：报表；R：文书", required = true)
    @NotBlank
    private String chartCatalog;

    @Column(name = "CHART_TYPE")
    @ApiModelProperty(value = "图表类型", required = true)
    @NotBlank
    private String chartType;

    @ApiModelProperty(value = "数据查询ID", hidden = true)
    @Column(name = "QUERY_ID")
    private String queryId;

    @Column(name = "DATA_SET_NAME")
    @ApiModelProperty(value = "数据集名", required = true)
    @NotBlank
    private String dataSetName;

    @JSONField(serialize=false)
    @Column(name = "CHART_DESIGN_JSON")
    @ApiModelProperty(value = "图表自定义属性 json格式的图表自定义说明", required = true)
    @Basic(fetch = FetchType.LAZY)
    private String chartDesignJson;

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

    public JSONObject getChartDesign() {
        if(StringUtils.isBlank(chartDesignJson)) {
            return null;
        }
        try {
            return JSONObject.parseObject(chartDesignJson);
        } catch (Exception e) {
            e.printStackTrace();
            return  null;
        }
    }
}
