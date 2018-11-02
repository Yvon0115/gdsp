package com.gdsp.platform.log.model;

import java.util.List;

import com.gdsp.dev.base.lang.DDateTime;
import com.gdsp.dev.core.model.entity.AuditableEntity;

public class TempLogOpVO extends AuditableEntity{
	
	private String tableName;
	
	private String tableDesc;
	
	private String serviceId;
	
	private String dataId;
	
	private String type;
	
	private List<TempLogContentVO> logContents;

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getTableDesc() {
		return tableDesc;
	}

	public void setTableDesc(String tableDesc) {
		this.tableDesc = tableDesc;
	}

	public String getServiceId() {
		return serviceId;
	}

	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}

	public String getDataId() {
		return dataId;
	}

	public void setDataId(String dataId) {
		this.dataId = dataId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	public List<TempLogContentVO> getLogContents() {
		return logContents;
	}

	public void setLogContents(List<TempLogContentVO> logContents) {
		this.logContents = logContents;
	}
}
