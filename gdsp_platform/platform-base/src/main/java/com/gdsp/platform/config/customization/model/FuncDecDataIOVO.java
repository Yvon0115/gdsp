package com.gdsp.platform.config.customization.model;

import com.gdsp.dev.core.utils.excel.AbstractDataIOVO;
import com.gdsp.dev.core.utils.excel.ExcelVOAttribute;

public class FuncDecDataIOVO implements AbstractDataIOVO {

	@ExcelVOAttribute(name = "菜单ID", column = "A", isExport = true)
	private String id;
	
	
	@ExcelVOAttribute(name = "菜单名称", column = "B", isExport = true)
	private String funname;
	
	
	@ExcelVOAttribute(name = "菜单描述", column = "D", isExport = true)
	private String memo;
	
	@ExcelVOAttribute(name = "URL", column = "E", isExport = true)
	private String url;
	
	@ExcelVOAttribute(name = "PARENTID", column = "F", isExport = true)
	private String parentid;
	
	@ExcelVOAttribute(name = "菜单类型", column = "G", isExport = true)
	private int funtype;
	
	@ExcelVOAttribute(name = "菜单类型", column = "H", isExport = true)
	private String funcode;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}


	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getFunname() {
		return funname;
	}

	public void setFunname(String funname) {
		this.funname = funname;
	}

	public String getParentid() {
		return parentid;
	}

	public void setParentid(String parentid) {
		this.parentid = parentid;
	}

	public int getFuntype() {
		return funtype;
	}

	public void setFuntype(int funtype) {
		this.funtype = funtype;
	}

	public String getFuncode() {
		return funcode;
	}

	public void setFuncode(String funcode) {
		this.funcode = funcode;
	}

	
	
	
}
