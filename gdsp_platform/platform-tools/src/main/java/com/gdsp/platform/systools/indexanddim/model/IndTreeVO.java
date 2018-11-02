package com.gdsp.platform.systools.indexanddim.model;

import com.gdsp.dev.core.model.entity.AuditableEntity;
import com.gdsp.dev.core.utils.tree.model.ITreeEntity;

public class IndTreeVO extends AuditableEntity implements ITreeEntity {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private static String     tableName        = "bp_indgroups";//表名
    private String            groupName;                        //指标组名称
    private String            groupCode;                        //指标组编码
    private String            detail;                           //描述
    private String            innercode;                        //内部码
    private String            pk_fatherid;                      //父id

    public String getTableName() {
        return tableName;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getGroupCode() {
        return groupCode;
    }

    public void setGroupCode(String groupCode) {
        this.groupCode = groupCode;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getInnercode() {
        return innercode;
    }

    public void setInnercode(String innercode) {
        this.innercode = innercode;
    }

    public String getLastModifyBy() {
        return lastModifyBy;
    }

    public void setLastModifyBy(String lastModifyBy) {
        this.lastModifyBy = lastModifyBy;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    public String getPk_fatherid() {
        return pk_fatherid;
    }

    public void setPk_fatherid(String pk_fatherid) {
        this.pk_fatherid = pk_fatherid;
    }
}
