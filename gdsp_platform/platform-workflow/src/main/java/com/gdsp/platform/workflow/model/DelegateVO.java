package com.gdsp.platform.workflow.model;

import com.gdsp.dev.base.lang.DDateTime;
import com.gdsp.dev.core.model.entity.AuditableEntity;

/**
 * 流程委托表
 * 
 * @author gdsp
 *
 */
public class DelegateVO extends AuditableEntity {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private DDateTime         startTime;            // 委托有效开始时间
    private DDateTime         endTime;              // 流程有效结束时间
    private String            userId;               // 委托人ID
    private String            acceptId;             // 被委托人ID
    //userName，是为了连接rms_user表进行查询，返回用户名
    private String            userName;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public DDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(DDateTime startTime) {
        this.startTime = startTime;
    }

    public DDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(DDateTime endTime) {
        this.endTime = endTime;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getAcceptId() {
        return acceptId;
    }

    public void setAcceptId(String acceptId) {
        this.acceptId = acceptId;
    }
}
