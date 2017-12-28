package com.centit.stat.po;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import org.hibernate.validator.constraints.NotBlank;


@Embeddable
public class QueryColumnId implements java.io.Serializable {
    private static final long serialVersionUID =  1L;
     @Column(name = "MODELNAME")
     @NotBlank(message = "字段不能为空")
    private String modelName;
     @Column(name = "COLNAME")
        @NotBlank(message = "字段不能为空")
    private String colName;

    // Constructors
    public QueryColumnId() {
    }
    //full constructor
    public QueryColumnId(String modelName, String colName) {

        this.modelName = modelName;
        this.colName = colName;
    }


    public String getModelName() {
        return this.modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public String getColName() {
        return this.colName;
    }

    public void setColName(String colName) {
        this.colName = colName;
    }


    public boolean equals(Object other) {
        if ((this == other))
            return true;
        if ((other == null))
            return false;
        if (!(other instanceof QueryColumnId))
            return false;

        QueryColumnId castOther = (QueryColumnId) other;
        boolean ret = true;

        ret = ret && ( this.getModelName() == castOther.getModelName() ||
                       (this.getModelName() != null && castOther.getModelName() != null
                               && this.getModelName().equals(castOther.getModelName())));

        ret = ret && ( this.getColName() == castOther.getColName() ||
                       (this.getColName() != null && castOther.getColName() != null
                               && this.getColName().equals(castOther.getColName())));

        return ret;
    }

    public int hashCode() {
        int result = 17;

        result = 37 * result +
             (this.getModelName() == null ? 0 :this.getModelName().hashCode());

        result = 37 * result +
             (this.getColName() == null ? 0 :this.getColName().hashCode());

        return result;
    }
}
