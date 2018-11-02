package com.gdsp.platform.grant.auth.model;

import com.gdsp.dev.core.model.entity.AuditableEntity;
import com.gdsp.dev.core.model.entity.BaseEntity;

/**
 * 用户默认首页VO
 * @author wqh
 * 2016年11月22日 下午1:57:14
 */
public class UserDefaultPageVO extends AuditableEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected String pk_user;    // 用户id
	protected String page_id;    // 页面id
	protected String menu_id;    // 页签id
	
	
	public String getPk_user() {
		return pk_user;
	}
	public void setPk_user(String pk_user) {
		this.pk_user = pk_user;
	}
	public String getPage_id() {
		return page_id;
	}
	public void setPage_id(String page_id) {
		this.page_id = page_id;
	}
	public String getMenu_id() {
		return menu_id;
	}
	public void setMenu_id(String menu_id) {
		this.menu_id = menu_id;
	}
	
	
	
	
	
	
}
