package com.gdsp.platform.systools.indexanddim.model;

import com.gdsp.dev.core.model.entity.AuditableEntity;

public class IndexDimTreeVO extends AuditableEntity {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private String            disname;              //节点显示名称
    private String            indexid;              //指标ID
    private String            groupid;              //指标组ID
    private String            pk_fatherid;          //父类ID
    private String            leaf;                 //是否是叶子节点

    public String getDisname() {
        return disname;
    }

    public void setDisname(String disname) {
        this.disname = disname;
    }

    public String getIndexid() {
        return indexid;
    }

    public void setIndexid(String indexid) {
        this.indexid = indexid;
    }

    public String getGroupid() {
        return groupid;
    }

    public void setGroupid(String groupid) {
        this.groupid = groupid;
    }

    public String getPk_fatherid() {
        return pk_fatherid;
    }

    public void setPk_fatherid(String pk_fatherid) {
        this.pk_fatherid = pk_fatherid;
    }

    public String getLeaf() {
        return leaf;
    }

    public void setLeaf(String leaf) {
        this.leaf = leaf;
    }
}
