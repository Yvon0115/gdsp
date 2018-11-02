package com.gdsp.platform.log.model;


import com.gdsp.dev.core.model.entity.AuditableEntity;
import com.gdsp.platform.log.helper.PortalQueryConst;

public class OperationLogVO extends AuditableEntity {

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = -2408900524581273277L;
	private String            table_name;                           //表英文名
    private String            table_desc;                           //表中文名
    private String            type;                                 //操作类型
    private String            username;                             //操作人
	public String getTable_name() {
		return table_name;
	}
	public void setTable_name(String table_name) {
		this.table_name = table_name;
	}
	public String getTable_desc() {
		return table_desc;
	}
	public void setTable_desc(String table_desc) {
		this.table_desc = table_desc;
	}
	public String getType() {
		//操作类型 '1:增加,2:修改,3:删除'
        if ((PortalQueryConst.insertOperation).equals(type)) {
            return "增加";
        } else if ((PortalQueryConst.updateOperation).equals(type)) {
            return "修改";
        } else if ((PortalQueryConst.deleteOperation).equals(type)) {
            return "删除";
        } 
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	
}
