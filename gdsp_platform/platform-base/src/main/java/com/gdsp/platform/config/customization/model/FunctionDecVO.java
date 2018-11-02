
package com.gdsp.platform.config.customization.model;

import java.util.List;

import com.gdsp.dev.core.model.entity.AuditableEntity;

/**
 * 
* @ClassName: FunctionDecVO
* (实体类)
* @author songxiang
* @date 2015年10月28日 上午11:08:55
*
 */
public class FunctionDecVO extends AuditableEntity {

	/**
	 * 编码
	 */
	private String funcode;
	/**
	 *  菜单名
	 */
	private String funname; 
	/**
	 * 内部码
	 */
	private String innercode; 
	/**
	 * 上级节点id
	 */
	private String parentid; 
	/**
	 * 菜单类型 '0:一级分类,1:下级分类,2:管理菜单,3:业务菜单,4:页面菜单' null default '0'
	 */
	private int funtype; 
	/**
	 * 权限控制
	 */
	private String ispower;
	/**
	 *  描述
	 */
	private String memo; 
	/**
	 * 图片路径
	 */
	private String fileUrl;
	private List<FuncDecVO> funcDecVO;
	
	public String getFileUrl() {
		return fileUrl;
	}
	public void setFileUrl(String fileUrl) {
		this.fileUrl = fileUrl;
	}

	public String getFuncode() {
		return funcode;
	}
	public void setFuncode(String funcode) {
		this.funcode = funcode;
	}
	public String getFunname() {
		return funname;
	}
	public void setFunname(String funname) {
		this.funname = funname;
	}
	public String getInnercode() {
		return innercode;
	}
	public void setInnercode(String innercode) {
		this.innercode = innercode;
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
	public String getIspower() {
		return ispower;
	}
	public void setIspower(String ispower) {
		this.ispower = ispower;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	public List<FuncDecVO> getFuncDecVO() {
		return funcDecVO;
	}
	public void setFuncDecVO(List<FuncDecVO> funcDecVO) {
		this.funcDecVO = funcDecVO;
	}
	
	
}
