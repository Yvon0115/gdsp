package com.gdsp.ptbase.portal.service;

import java.util.Map;

/**
 * portal页加工处理  服务接口
 *
 * @author paul.yang
 * @author wqh
 * @version 1.0 2015-7-30
 * @since 1.6
 */
public interface IPortalPageHandler {

    /**
     * 为portal页准备数据
     * @return 准备好的数据
     */
    public Map<String, Object> prepareData(String user);
    
    
    /**
	 * 设置默认首页
	 * @param vo
	 * @return
	 */
	public boolean setDefaultHomePage(String userID,String pageID,String menuID);
	
	
	/**
	 * 取消默认首页(当前暂未实现)
	 * @param vo
	 * @return
	 */
	public boolean revokeDefaultHomePage(String userID,String pageID,String menuID);
    
}
