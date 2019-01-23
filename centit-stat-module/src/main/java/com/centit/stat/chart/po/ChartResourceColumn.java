package com.centit.stat.chart.po;

import com.alibaba.fastjson.annotation.JSONField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Pattern;

@Data
@Entity
@Table(name = "Q_CHART_RESOURCE_Column")
@ApiModel(value = "图表列")
public class ChartResourceColumn {

    @Id
    @Column(name = "CHART_ID")
    @ApiModelProperty(value = "图表ID", hidden = true)
    @JSONField(serialize = false)
    private String chartId;

    @Id
    @Column(name = "COLUMN_CODE")
    @ApiModelProperty(value = "列代码")
    private String columnCode;

    @ApiModelProperty(value = "显示类型(X轴:X;Y轴:Y)")
    @Pattern(regexp = "[XY]")
    @Column(name = "SHOW_TYPE")
    private String showType;
}
