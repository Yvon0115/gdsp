package com.gdsp.ptbase.portal.service;

import com.gdsp.platform.grant.auth.model.UserDefaultPageVO;

/**
 * 页面菜单默认首页服务
 * @author wqh
 * 2016年11月22日 下午4:13:11
 */
public interface IPortalDefaultHomePageService {

	/**
	 * 设置默认首页
	 * @param vo
	 * @return
	 */
	public boolean setDefaultHomePage(String userID,String pageID,String menuID);
	
	
	/**
	 * 取消默认首页
	 * @param vo
	 * @return
	 */
	public boolean revokeDefaultHomePage(UserDefaultPageVO vo);
	
	
}
