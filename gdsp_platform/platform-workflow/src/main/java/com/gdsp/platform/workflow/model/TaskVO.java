package com.gdsp.platform.workflow.model;

import com.gdsp.dev.base.lang.DDateTime;

public class TaskVO {

    private String    id;             // 任务id
    private String    procInsId;      // 流程实例ID
    private String    procDefId;      // 流程定义ID
    private String    nodeId;         // 当前节点ID
    private String    nodeName;       // 当前节点名称
    private String    deploymentId;   // 部署id
    private String    deploymentCode; // 部署编码
    private String    deploymentName; // 部署名称
    private String    categoryName;   // 流程类别名称
    private String    userId;         // 流程发起人
    private String    taskId;         // 任务id
    private DDateTime createTime;     // 任务创建时间
    private DDateTime dueTime;        // 任务持续时间
    private String    url;            // 表单URL
    private String    formid;         // 表单ID

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProcInsId() {
        return procInsId;
    }

    public void setProcInsId(String procInsId) {
        this.procInsId = procInsId;
    }

    public String getProcDefId() {
        return procDefId;
    }

    public void setProcDefId(String procDefId) {
        this.procDefId = procDefId;
    }

    public String getNodeId() {
        return nodeId;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }

    public String getDeploymentCode() {
        return deploymentCode;
    }

    public void setDeploymentCode(String deploymentCode) {
        this.deploymentCode = deploymentCode;
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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public DDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(DDateTime createTime) {
        this.createTime = createTime;
    }

    public DDateTime getDueTime() {
        return dueTime;
    }

    public void setDueTime(DDateTime dueTime) {
        this.dueTime = dueTime;
    }

    public String getNodeName() {
        return nodeName;
    }

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getFormid() {
        return formid;
    }

    public void setFormid(String formid) {
        this.formid = formid;
    }

    public String getDeploymentId() {
        return deploymentId;
    }

    public void setDeploymentId(String deploymentId) {
        this.deploymentId = deploymentId;
    }
}
