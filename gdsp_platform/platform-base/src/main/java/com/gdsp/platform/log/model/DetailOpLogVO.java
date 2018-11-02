package com.gdsp.platform.log.model;

import com.gdsp.dev.core.model.entity.AuditableEntity;

public class DetailOpLogVO extends AuditableEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8043263906239084931L;
	
	private String log_id;            //上级表id
	private String col_name;             //字段英文名
	private String col_desc;             //字段中文名
	private String old_data;           //字段旧值
	private String new_data;              //字段新值
	public String getLog_id() {
		return log_id;
	}
	public void setLog_id(String log_id) {
		this.log_id = log_id;
	}
	public String getCol_name() {
		return col_name;
	}
	public void setCol_name(String col_name) {
		this.col_name = col_name;
	}
	public String getCol_desc() {
		return col_desc;
	}
	public void setCol_desc(String col_desc) {
		this.col_desc = col_desc;
	}
	public String getOld_data() {
		return old_data;
	}
	public void setOld_data(String old_data) {
		this.old_data = old_data;
	}
	public String getNew_data() {
		return new_data;
	}
	public void setNew_data(String new_data) {
		this.new_data = new_data;
	}
	

}
