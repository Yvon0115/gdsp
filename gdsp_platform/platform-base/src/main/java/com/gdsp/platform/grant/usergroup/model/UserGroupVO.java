package com.gdsp.platform.grant.usergroup.model;

import com.gdsp.dev.core.model.entity.AuditableEntity;

/**
 * 用户组表
 * @author wwb 
 * @version 1.0 2015/6/15
 */
public class UserGroupVO extends AuditableEntity {

    private static final long serialVersionUID = 1L;
    private String            groupname;            // 用户组名
    private String            parentid;             // 上级用户组
    private String            innercode;            // 内部码
    private String            pk_org;               // 所属机构
    private String            memo;                 // 描述

    public String getGroupname() {
        return groupname;
    }

    public void setGroupname(String groupname) {
        this.groupname = groupname;
    }

    public String getParentid() {
        return parentid;
    }

    public void setParentid(String parentid) {
        this.parentid = parentid;
    }

    public String getInnercode() {
        return innercode;
    }

    public void setInnercode(String innercode) {
        this.innercode = innercode;
    }

    public String getPk_org() {
        return pk_org;
    }

    public void setPk_org(String pk_org) {
        this.pk_org = pk_org;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }
}
