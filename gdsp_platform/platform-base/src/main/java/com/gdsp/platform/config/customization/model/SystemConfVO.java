package com.gdsp.platform.config.customization.model;

import com.gdsp.dev.core.model.entity.AuditableEntity;

public class SystemConfVO extends AuditableEntity {

    /**
     * 序列化id
     */
    private static final long serialVersionUID = 4591647392733772742L;
    private String            sysHomeState;                           // 系统首页状态 0：启用，1：不启用
    private String            sysHomeUrl;                             // 系统首页URL
    private String            reportInts;                             // 报表集成信息
    private String            mailServiceState;                     //邮箱服务开关状态 Y：启用， N：停用

    public String getSysHomeState() {
        return sysHomeState;
    }

    public void setSysHomeState(String sysHomeState) {
        this.sysHomeState = sysHomeState;
    }

    public String getSysHomeUrl() {
        return sysHomeUrl;
    }

    public void setSysHomeUrl(String sysHomeUrl) {
        this.sysHomeUrl = sysHomeUrl;
    }

    public String getReportInts() {
        return reportInts;
    }

    public void setReportInts(String reportInts) {
        this.reportInts = reportInts;
    }

    public String getMailServiceState() {
        return mailServiceState;
    }

    public void setMailServiceState(String mailServiceState) {
        this.mailServiceState = mailServiceState;
    }
}
