package com.gdsp.platform.func.model;

import com.gdsp.dev.core.model.entity.AuditableEntity;

/**
 * Register 父类
 * 由于需要使用多态特性，需要将register提炼出父类
 * @author xiangguo
 *
 */
public abstract class BaseRegisterVO extends AuditableEntity {

    private static final long serialVersionUID = 1L;
    protected String          funname;              //名称
    protected String          url;                  //访问url
    protected String          memo;                 // 描述

    public String getFunname() {
        return funname;
    }

    public void setFunname(String funname) {
        this.funname = funname;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }
}
