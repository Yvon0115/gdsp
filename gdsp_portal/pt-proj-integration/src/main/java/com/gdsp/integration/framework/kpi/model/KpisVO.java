package com.gdsp.integration.framework.kpi.model;

import com.gdsp.dev.core.model.entity.AuditableEntity;

public class KpisVO extends AuditableEntity {

    private static final long serialVersionUID = -70487702357713084L;
    private String            name;
    private String            alias;
    private String            comments;
    private String            pid;
    private String relationId;

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

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    
    public String getRelationId() {
        return relationId;
    }

    
    public void setRelationId(String relationId) {
        this.relationId = relationId;
    }
    
}
