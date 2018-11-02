package com.gdsp.platform.workflow.model;

import com.gdsp.dev.core.model.entity.AuditableEntity;

public class NotifyEventDetailVO extends AuditableEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3624131467587436897L;

	private String deploymentId;
	private String nodeinfoId; 
	private String eventTypeId;
	private String notifyUrl;
	
	public String getDeploymentId() {
		return deploymentId;
	}
	public void setDeploymentId(String deploymentId) {
		this.deploymentId = deploymentId;
	}
	public String getNodeinfoId() {
		return nodeinfoId;
	}
	public void setNodeinfoId(String nodeinfoId) {
		this.nodeinfoId = nodeinfoId;
	}
	public String getEventTypeId() {
		return eventTypeId;
	}
	public void setEventTypeId(String eventTypeId) {
		this.eventTypeId = eventTypeId;
	}
	public String getNotifyUrl() {
		return notifyUrl;
	}
	public void setNotifyUrl(String notifyUrl) {
		this.notifyUrl = notifyUrl;
	}
	
	
	
}
