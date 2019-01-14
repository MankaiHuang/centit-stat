package com.centit.stat.chart.po;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "Q_CHART_RESOURCE_Column")
public class ChartResourceColumn {

    @Id
    @Column(name = "CHART_ID")
    private String chartId;

    @Id
    @Column(name = "COLUMN_CODE")
    private String columnCode;
}
