/**  
* @Title: DemoOrgVO.java
* @Package com.gdsp.platform.demo.model
* (用一句话描述该文件做什么)
* @author 连长
* @date 2017年6月13日 下午4:40:46
* @version V1.0  
*/ 
package com.gdsp.platform.demo.model;

import com.gdsp.platform.grant.org.model.OrgVO;

/**
* @ClassName: DemoOrgVO
* (这里用一句话描述这个类的作用)
* @author shiyingjie
* @date 2017年6月13日 下午4:40:46
*
*/
public class ZtreeOrgVO extends OrgVO {
	
	private String 				nodeDisable; 		//节点禁用
	private String 				chkEnabled;			//节点复选框可用
	private String              chkDisabled;		//节点复选框禁用
	private String              checked;			//节点初始化时选中
	private String              openUrl;			//节点展开时的自定义图片路径
	private String              closeUrl;			//节点折叠时的自定义图片路径
	private String              iconUrl;			//节点展开和折叠时的自定义图片路径
	private String              isParent;			//判断是否是父节点
	
	
	public String getNodeDisable() {
		return nodeDisable;
	}
	public void setNodeDisable(String nodeDisable) {
		this.nodeDisable = nodeDisable;
	}
	public String getChkEnabled() {
		return chkEnabled;
	}
	public void setChkEnabled(String chkEnabled) {
		this.chkEnabled = chkEnabled;
	}
	public String getChkDisabled() {
		return chkDisabled;
	}
	public void setChkDisabled(String chkDisabled) {
		this.chkDisabled = chkDisabled;
	}
	public String getChecked() {
		return checked;
	}
	public void setChecked(String checked) {
		this.checked = checked;
	}
	public String getOpenUrl() {
		return openUrl;
	}
	public void setOpenUrl(String openUrl) {
		this.openUrl = openUrl;
	}
	public String getCloseUrl() {
		return closeUrl;
	}
	public void setCloseUrl(String closeUrl) {
		this.closeUrl = closeUrl;
	}
	public String getIconUrl() {
		return iconUrl;
	}
	public void setIconUrl(String iconUrl) {
		this.iconUrl = iconUrl;
	}
	public String getIsParent() {
		return isParent;
	}
	public void setIsParent(String isParent) {
		this.isParent = isParent;
	}
	
}
