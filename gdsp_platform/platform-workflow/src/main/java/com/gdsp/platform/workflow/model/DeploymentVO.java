package com.gdsp.platform.workflow.model;

import com.gdsp.dev.core.model.entity.AuditableEntity;

/**
 * 流程部署扩展表
 * @author gdsp
 *
 */
public class DeploymentVO extends AuditableEntity {
	private static final long serialVersionUID = 4053715457523754185L;
	private String            deploymentCode;       // 流程编码
    private String            deploymentName;       // 流程名称
    private String            deployId;             // 流程部署ID
    private String            procDefId;            // 流程定义ID
    private int               state;                // 流程状态
    private String            formTypeId;           // 单据类型ID
    private String            categoryId;           // 流程类型ID
    private Object  		  metaInfo;   			// 节点元信息
    private String            memo;                 // 描述信息
    private String            categoryType;         // 流程类型

    public String getCategoryType() {
        return categoryType;
    }

    public void setCategoryType(String categoryType) {
        this.categoryType = categoryType;
    }

    public Object getMetaInfo() {
        return metaInfo;
    }

    public void setMetaInfo(Object metaInfo) {
        this.metaInfo = metaInfo;
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

    public String getDeployId() {
        return deployId;
    }

    public void setDeployId(String deployId) {
        this.deployId = deployId;
    }

    public String getProcDefId() {
        return procDefId;
    }

    public void setProcDefId(String procDefId) {
        this.procDefId = procDefId;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getFormTypeId() {
        return formTypeId;
    }

    public void setFormTypeId(String formTypeId) {
        this.formTypeId = formTypeId;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }
}
