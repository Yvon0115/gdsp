package com.gdsp.platform.widgets.help.model;

import com.gdsp.dev.core.model.entity.AuditableEntity;

/**
 * 机构表
 * @author wwb
 * @version 1.0 2015/6/15
 */
public class HelpVO extends AuditableEntity {

    /**
     * 
     */
    private static final long serialVersionUID = -808011947827575920L;
    private String            title;
    private String            attach_name;                            //附件名称
    private String            attach_path;                            //附件路径
    private String            memo;                                   //描述

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAttach_name() {
        return attach_name;
    }

    public void setAttach_name(String attach_name) {
        this.attach_name = attach_name;
    }

    public String getAttach_path() {
        return attach_path;
    }

    public void setAttach_path(String attach_path) {
        this.attach_path = attach_path;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }
}
