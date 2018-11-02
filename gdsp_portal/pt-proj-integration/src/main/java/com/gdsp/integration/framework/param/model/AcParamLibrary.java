package com.gdsp.integration.framework.param.model;

import com.gdsp.dev.core.model.entity.AuditableEntity;

public class AcParamLibrary extends AuditableEntity {

    private static final long serialVersionUID = -1622903326347715883L;
    private String            name;                                    //参数分类名称
    private String            comments;                                //参数分类描述
    private String            pid;                                     //参数分类父ID

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }
}