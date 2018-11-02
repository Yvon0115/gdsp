package com.gdsp.platform.config.customization.model;

import com.gdsp.dev.core.model.entity.AuditableEntity;

/**
 * 
* @ClassName: FuncDecVO
* (实体类)
* @author songxiang
* @date 2015年10月28日 上午11:08:20
*
 */
public class FuncDecVO extends AuditableEntity {

	private static final long serialVersionUID = -3418732346435470185L;
	/**
	 * 名称
	 */
	private String name;
	/**
	 * 描述
	 */
	private String memo;
	/**
	 * 图片Url
	 */
	private String fileUrl;
	/**
	 * 菜单id
	 */
	private String menuid;
	/**
	 * 分类号
	 */
	private int sortnum;
	/**
	 * 内部编码
	 */
	private String innercode;
	//以下字段为st_menuregister表中的字段
	private String menuName;

	
	public String getInnercode() {
		return innercode;
	}
	public void setInnercode(String innercode) {
		this.innercode = innercode;
	}
	public String getMenuName() {
		return menuName;
	}
	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	public String getFileUrl() {
		return fileUrl;
	}
	public void setFileUrl(String fileUrl) {
		this.fileUrl = fileUrl;
	}
	public String getMenuid() {
		return menuid;
	}
	public void setMenuid(String menuid) {
		this.menuid = menuid;
	}
	public int getSortnum() {
		return sortnum;
	}
	public void setSortnum(int sortnum) {
		this.sortnum = sortnum;
	}
	
	
	
}
