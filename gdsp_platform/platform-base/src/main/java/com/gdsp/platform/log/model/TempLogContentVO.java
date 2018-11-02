/**
 * 
 */
package com.gdsp.platform.log.model;

import com.gdsp.dev.core.model.entity.AuditableEntity;

/**
 * 
 * @Description:
 * @author
 * @date 2016年12月19日
 */
public class TempLogContentVO  extends AuditableEntity{

	private String logId;
	
	private String colName;
	
	private String colDesc;
	
	private String newData;
	
	private String oldData;

	public String getLogId() {
		return logId;
	}

	public void setLogId(String logId) {
		this.logId = logId;
	}

	public String getColName() {
		return colName;
	}

	public void setColName(String colName) {
		this.colName = colName;
	}

	public String getColDesc() {
		return colDesc;
	}

	public void setColDesc(String colDesc) {
		this.colDesc = colDesc;
	}

	public String getNewData() {
		return newData;
	}

	public void setNewData(String newData) {
		this.newData = newData;
	}

	public String getOldData() {
		return oldData;
	}

	public void setOldData(String oldData) {
		this.oldData = oldData;
	}
}
