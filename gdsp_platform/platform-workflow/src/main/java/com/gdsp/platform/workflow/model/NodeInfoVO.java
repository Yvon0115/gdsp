package com.gdsp.platform.workflow.model;

import com.gdsp.dev.core.model.entity.AuditableEntity;

/**
 * 流程节点信息表
 * @author gdsp
 *
 */
public class NodeInfoVO extends AuditableEntity {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private String            procDefKey;           // 流程定义key
    private String            deploymentId;         // 流程部署ID
    private String            actId;                // 活动节点ID
    private String            actName;              // 活动节点名称		
    private String            formTypeId;           // 单据类型ID
    private String            timerTaskId;          // 定时任务ID
    private int               notice;               // 办理通知方式
    private String            multiInsType;         // 会签规则类型
    private int               multiInsValue;        // 会签规则值
    private String            eventTypeId;          // 事件类型ID

    public String getProcDefKey() {
        return procDefKey;
    }

    public void setProcDefKey(String procDefKey) {
        this.procDefKey = procDefKey;
    }

    public String getDeploymentId() {
        return deploymentId;
    }

    public void setDeploymentId(String deploymentId) {
        this.deploymentId = deploymentId;
    }

    public String getActId() {
        return actId;
    }

    public void setActId(String actId) {
        this.actId = actId;
    }

    public String getActName() {
        return actName;
    }

    public void setActName(String actName) {
        this.actName = actName;
    }

    public String getFormTypeId() {
        return formTypeId;
    }

    public void setFormTypeId(String formTypeId) {
        this.formTypeId = formTypeId;
    }

    public String getTimerTaskId() {
        return timerTaskId;
    }

    public void setTimerTaskId(String timerTaskId) {
        this.timerTaskId = timerTaskId;
    }

    public int getNotice() {
        return notice;
    }

    public void setNotice(int notice) {
        this.notice = notice;
    }

    public String getMultiInsType() {
        return multiInsType;
    }

    public void setMultiInsType(String multiInsType) {
        this.multiInsType = multiInsType;
    }

    public int getMultiInsValue() {
        return multiInsValue;
    }

    public void setMultiInsValue(int multiInsValue) {
        this.multiInsValue = multiInsValue;
    }

    public String getEventTypeId() {
        return eventTypeId;
    }

    public void setEventTypeId(String eventTypeId) {
        this.eventTypeId = eventTypeId;
    }
}
