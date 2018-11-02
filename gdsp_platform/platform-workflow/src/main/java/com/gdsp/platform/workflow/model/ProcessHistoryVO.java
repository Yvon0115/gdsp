package com.gdsp.platform.workflow.model;

import com.gdsp.dev.core.model.entity.AuditableEntity;

/**
 * 
 * <p>Title:工作流审批历史</p>
 * 
 * <p>Description:工作流审批历史 --> 更详细的审批信息可以关联ACT_HI_ACTINST
 *
 */
public class ProcessHistoryVO extends AuditableEntity {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private String            deploymentId;         // 流程部署ID	
    private String            processInsId;         // 流程实例ID
    private String            actId;                // 活动节点ID
    private String            actName;              // 活动节点名称
    private String            options;              // 审批附加意见
    private String            userId;               // 审批人
    private String            result;               // 审批结果（1.审批通过,2.审批不通过,3.驳回发起人,4.驳回上一节点）
    private String            formId;               // 表单ID
    private String            taskId;               // 任务ID

    public String getDeploymentId() {
        return deploymentId;
    }

    public void setDeploymentId(String deploymentId) {
        this.deploymentId = deploymentId;
    }

    public String getProcessInsId() {
        return processInsId;
    }

    public void setProcessInsId(String processInsId) {
        this.processInsId = processInsId;
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

    public String getOptions() {
        return options;
    }

    public void setOptions(String options) {
        this.options = options;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getFormId() {
        return formId;
    }

    public void setFormId(String formId) {
        this.formId = formId;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }
}
