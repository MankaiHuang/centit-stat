package com.centit.stat.form.po;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/*Name	Code	Comment	Data Type	Length	Precision	Primary	Foreign Key	Mandatory
报表ID	form_ID		VARCHAR2(64)	64		TRUE	TRUE	TRUE
字段代码	column_code		VARCHAR2(64)	64		TRUE	FALSE	TRUE
数据操作	opt_type	0 无操作 1 合计 2 平均 3 平均合计	CHAR			FALSE	FALSE	FALSE
表头属性	column_Type	R 行头 、 C 列头 、 D 数据	CHAR			FALSE	FALSE	FALSE
数据钻取连接url模板	mine_url_Format	模板可以引用行数据和 数据包查询参数	VARCHAR2(512)	512		FALSE	FALSE	FALSE
字段序号	column_order		number(4,0)	4		FALSE	FALSE	FALSE*/
@Data
@Entity
@ApiModel
@Table(name = "Q_FORM_COLUMN")
public class FormColumn {

    @Id
    @Column(name = "FORM_ID")
    private String formId;
    @Column(name = "COLUMN_CODE")
    private String columnCode;

    @ApiModelProperty(value = "0:无操作 1:合计 2:平均 3:平均合计")
    @Column(name = "OPT_TYPE")
    private String optType;

    @ApiModelProperty(value = "R:行头、 C:列头、 D:数据")
    @Column(name = "COLUMN_TYPE")
    private String columnType;
    @Column(name = "MINE_URL_FORMAT")
    private String mineUrlFormat;
    @Column(name = "COLUMN_ORDER")
    private int columnOrder;
}
