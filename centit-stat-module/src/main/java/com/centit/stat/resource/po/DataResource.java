package com.centit.stat.resource.po;

import com.centit.framework.core.dao.DictionaryMap;
import com.centit.support.database.orm.GeneratorCondition;
import com.centit.support.database.orm.GeneratorTime;
import com.centit.support.database.orm.GeneratorType;
import com.centit.support.database.orm.ValueGenerator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "Q_DATA_RESOURCE")
@ApiModel(value = "数据包")
public class DataResource implements Serializable {

    private static final long serialVersionUID = 286774459298411368L;

    @Id
    @Column(name = "RESOURCE_ID")
    @ValueGenerator(strategy = GeneratorType.UUID)
    @ApiModelProperty(value = "数据包ID", hidden = true)
    private String resourceId;

    @Column(name = "DATABASE_CODE")
    @ApiModelProperty(value = "数据库代码")
    private String databaseCode;

    @Column(name = "OWNER_TYPE")
    @ApiModelProperty(value = "属主类别（D:部门；U:用户）")
    private String ownerType;

    @Column(name = "OWNER_CODE")
    @ApiModelProperty(value = "属主代码")
    private String ownerCode;

    @Column(name = "QUERY_SQL")
    @ApiModelProperty(value = "查询SQL")
    private String querySql;

    @Column(name = "QUERY_DESC")
    @ApiModelProperty(value = "查询描述")
    private String queryDesc;

    @Column(name = "RESOURCE_NAME_FORMAT")
    @ApiModelProperty(value = "数据包名称模板")
    private String resourceNameFormat;

    @Column(name = "RECORDER")
    @ApiModelProperty(value = "修改人", hidden = true)
    @DictionaryMap(fieldName = "recorderName", value = "userCode")
    private String recorder;

    @Column(name = "RECORDER_DATE")
    @ApiModelProperty(value = "修改时间", hidden = true)
    @ValueGenerator(strategy = GeneratorType.FUNCTION, occasion = GeneratorTime.NEW_UPDATE, condition = GeneratorCondition.ALWAYS, value = "today()")
    private Date recorderDate;

    @OneToMany(targetEntity = DataResourceColumn.class)
    @JoinColumn(name = "resourceId", referencedColumnName = "resourceId")
    private List<DataResourceColumn> columns;

    @OneToMany(targetEntity = DataResourceParam.class)
    @JoinColumn(name = "resourceId", referencedColumnName = "resourceId")
    private List<DataResourceParam> params;
}
