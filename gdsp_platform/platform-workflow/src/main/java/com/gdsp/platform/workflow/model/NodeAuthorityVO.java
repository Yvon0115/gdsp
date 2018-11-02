package com.gdsp.platform.workflow.model;

import com.gdsp.dev.core.model.entity.AuditableEntity;

/**
 * 
 * NodeAuthorityVO
 * <p>Title: 平台工作流权限设置对应pojo</p>
 *
 * <p>Description: 平台工作流权限设置对应pojo</p>
 *
 */
public class NodeAuthorityVO extends AuditableEntity {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private String            deploymentId;         //流程部署ID
    private String            actionType;           //执行人类型(0、固定执行人,1、直接上级,2、角色,3、组织机构)
    private String            actionid;             //执行人ID
    private String            eventid;              //流程节点id
    private String            eventname;            //流程节点名称
    private String            formUrl;              //表单URL
    private String            isMultiInstance;      //是否会签节点
    private Integer           multiType;            //会签审批通过条件（0、一票否决,1、半数通过）
    //private Integer multiResultType;	//会签审批结果判断类型（0、所有参与人审批完成再决定审批结果,1、每审批一次判断一次审批结果）
    private Integer           sendEmail;            //是否发送Email（0、发送(默认),1、不发送）
    private String            emailTitle;           //Email标题
    private String            emailTemplete;        //Email模板
    private String            nodeBeforeJavaFace;   //节点执行前二次开发接口
    private String            nodeAfterJavaFace;    //节点执行后二次开发接口

    public String getDeploymentId() {
        return deploymentId;
    }

    public void setDeploymentId(String deploymentId) {
        this.deploymentId = deploymentId;
    }

    public String getActionType() {
        return actionType;
    }

    public void setActionType(String actionType) {
        this.actionType = actionType;
    }

    public String getActionid() {
        return actionid;
    }

    public void setActionid(String actionid) {
        this.actionid = actionid;
    }

    public String getEventid() {
        return eventid;
    }

    public void setEventid(String eventid) {
        this.eventid = eventid;
    }

    public String getEventname() {
        return eventname;
    }

    public void setEventname(String eventname) {
        this.eventname = eventname;
    }

    public String getFormUrl() {
        return formUrl;
    }

    public void setFormUrl(String formUrl) {
        this.formUrl = formUrl;
    }

    public String getIsMultiInstance() {
        return isMultiInstance;
    }

    public void setIsMultiInstance(String isMultiInstance) {
        this.isMultiInstance = isMultiInstance;
    }

    public Integer getMultiType() {
        return multiType;
    }

    public void setMultiType(Integer multiType) {
        this.multiType = multiType;
    }

    public Integer getSendEmail() {
        return sendEmail;
    }

    public void setSendEmail(Integer sendEmail) {
        this.sendEmail = sendEmail;
    }

    public String getEmailTitle() {
        return emailTitle;
    }

    public void setEmailTitle(String emailTitle) {
        this.emailTitle = emailTitle;
    }

    public String getEmailTemplete() {
        return emailTemplete;
    }

    public void setEmailTemplete(String emailTemplete) {
        this.emailTemplete = emailTemplete;
    }

    public String getNodeBeforeJavaFace() {
        return nodeBeforeJavaFace;
    }

    public void setNodeBeforeJavaFace(String nodeBeforeJavaFace) {
        this.nodeBeforeJavaFace = nodeBeforeJavaFace;
    }

    public String getNodeAfterJavaFace() {
        return nodeAfterJavaFace;
    }

    public void setNodeAfterJavaFace(String nodeAfterJavaFace) {
        this.nodeAfterJavaFace = nodeAfterJavaFace;
    }
}
