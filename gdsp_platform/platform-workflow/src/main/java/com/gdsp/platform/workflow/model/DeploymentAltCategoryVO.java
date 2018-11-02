package com.gdsp.platform.workflow.model;

/**
 * 流程部署扩展表
 * @author ming
 */
public class DeploymentAltCategoryVO {

    private String  id;
    private String  deploymentCode; // flow_deployment表中  流程编码 
    private String  deploymentName; // flow_deployment表中  流程名称
    private String  categoryName;   // flow_category 类型名称
    //isChecked是用来回显的时候用的
    private boolean ischecked;

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

    public boolean isIschecked() {
        return ischecked;
    }

    public void setIschecked(boolean ischecked) {
        this.ischecked = ischecked;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
