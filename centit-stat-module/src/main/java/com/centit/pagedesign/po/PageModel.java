package com.centit.pagedesign.po;

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

/*Name	Code	Comment	Data Type	Length	Precision	Primary	Foreign Key	Mandatory
页面代码	PAGE_Code		VARCHAR2(64)	64		TRUE	FALSE	TRUE
页面名称	PAGE_Name		VARCHAR2(64)	64		FALSE	TRUE	FALSE
页面描述	PAGE_Comment	VARCHAR2(256)	256		FALSE	FALSE	FALSE
页面类别	PAGE_Type		VARCHAR2(16)	16		FALSE	FALSE	FALSE
页面模板	PAGE_DESIGN_JSON CLOB			FALSE	FALSE	FALSE
更改人员	Recorder		VARCHAR2(32)	32		FALSE	FALSE	FALSE
更改时间	last_modify_Date		DATE			FALSE	FALSE	FALSE*/
@Data
@Entity
@ApiModel
@Table(name = "M_PAGE_MODEL")
public class PageModel implements Serializable {

    private static final long serialVersionUID = 1176407979669503919L;

    @Id
    @Column(name = "PAGE_CODE")
    @ApiModelProperty(value = "页面代码", hidden = true)
    @ValueGenerator(strategy = GeneratorType.UUID)
    private String pageCode;

    @Column(name = "PAGE_NAME")
    @ApiModelProperty(value = "页面名称", required = true)
    @NotBlank
    private String pageName;

    @ApiModelProperty(value = "页面描述")
    @Column(name = "PAGE_COMMENT")
    private String pageComment;

    @Column(name = "PAGE_TYPE")
    @ApiModelProperty(value = "页面类别,R 只读（视图、查询），A  新增（只能新增一条），W 修改 ，L 编辑列表（增删改）")
    @NotBlank
    private String pageType;

    @JSONField(serialize=false)
    @Column(name = "PAGE_DESIGN_JSON")
    @ApiModelProperty(value = "页面模板", required = true)
    @NotBlank
    private String pageDesignJson;

    @Column(name = "RECORDER")
    @ApiModelProperty(value = "更改人员", hidden = true)
    private String recorder;

    @Column(name = "LAST_MODIFY_DATE")
    @ApiModelProperty(value = "更改时间", hidden = true)
    @ValueGenerator(strategy = GeneratorType.FUNCTION, occasion = GeneratorTime.NEW_UPDATE, condition = GeneratorCondition.ALWAYS, value = "today()")
    private Date lastModifyDate;

    public JSONObject getPageDesign() {
        if(StringUtils.isBlank(pageDesignJson)) {
            return null;
        }
        try {
            return JSONObject.parseObject(pageDesignJson);
        } catch (Exception e) {
            e.printStackTrace();
            return  null;
        }
    }
}
