package com.gdsp.platform.workflow.model;

import com.gdsp.dev.core.model.entity.AuditableEntity;

/**
 * 业务关联表
 * @author wangxl
 *
 */
public class FormProcinstRltVO extends AuditableEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String pk_businessformid;		//业务单据表主键
	private String proceinstId;				//流程实例Id
	public String getPk_businessformid() {
		return pk_businessformid;
	}
	public void setPk_businessformid(String pk_businessformid) {
		this.pk_businessformid = pk_businessformid;
	}
	public String getProceinstId() {
		return proceinstId;
	}
	public void setProceinstId(String proceinstId) {
		this.proceinstId = proceinstId;
	}
	
	

}
