package com.gdsp.platform.workflow.model;

import com.gdsp.dev.core.model.entity.AuditableEntity;

/**
 * 业务单据表
 * @author wxl
 *
 */
public class BussinessFormVO extends AuditableEntity{

	
	private static final long serialVersionUID = 1534192839800707746L;

	private String deploymentId;	//流程部署id
	private String formId;			//单据key
	private String params;			//流程参数
//	private String procinstId;
	private String downloadurl;		//附加url
	private int status;				//业务状态
	
	
	

	public String getDownloadurl() {
		return downloadurl;
	}
	public void setDownloadurl(String downloadurl) {
		this.downloadurl = downloadurl;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}

	public String getDeploymentId() {
		return deploymentId;
	}
	public void setDeploymentId(String deploymentId) {
		this.deploymentId = deploymentId;
	}
	public String getFormId() {
		return formId;
	}
	public void setFormId(String formId) {
		this.formId = formId;
	}
	public String getParams() {
		return params;
	}
	public void setParams(String params) {
		this.params = params;
	}
	
	
	
}
