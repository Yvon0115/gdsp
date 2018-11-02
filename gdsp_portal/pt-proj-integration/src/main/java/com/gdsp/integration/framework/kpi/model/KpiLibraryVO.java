package com.gdsp.integration.framework.kpi.model;

import com.gdsp.dev.core.model.entity.AuditableEntity;

public class KpiLibraryVO extends AuditableEntity {

    private static final long serialVersionUID = 7511736810887897662L;
    private String            name;                                   //名称
    private String            pid;                                    //父ID
    private String            comments;                               //描述

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }
}
