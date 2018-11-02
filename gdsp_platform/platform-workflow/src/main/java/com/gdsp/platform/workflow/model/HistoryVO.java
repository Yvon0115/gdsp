package com.gdsp.platform.workflow.model;

import com.gdsp.dev.base.lang.DDateTime;

public class HistoryVO {

    private String    id;             // VO类id
    private String    procInsId;      // 流程实例ID
    private String    deploymentName; // 流程名称
    private String    categoryName;   // 流程类型
    private String    startUsers;     // 发起人
    private DDateTime startTime;      // 开始时间
    private DDateTime endTime;        // 结束时间
    private String    duration;       // 时长
    private String    formId;         // 单据表id
    private String    url;            // 行url
    private String    userId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFormId() {
        return formId;
    }

    public void setFormId(String formId) {
        this.formId = formId;
    }

    public String getProcInsId() {
        return procInsId;
    }

    public void setProcInsId(String procInsId) {
        this.procInsId = procInsId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDeploymentName() {
        return deploymentName;
    }

    public void setDeploymentName(String deploymentName) {
        this.deploymentName = deploymentName;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
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

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getStartUsers() {
        return startUsers;
    }

    public void setStartUsers(String startUsers) {
        this.startUsers = startUsers;
    }
}
