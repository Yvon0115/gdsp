package com.gdsp.ptbase.portal.model;

import java.util.List;

/**
 * 添加注释
 *
 * @author paul.yang
 * @version 1.0 2015-7-30
 * @since 1.6
 */
public interface IPortletMeta extends IStyle {

	
	/**
	 * @Fields EXTERNALRESOURCE(外部资源标识)
	 */ 
	public static final String EXTERNALRESOURCE="externalResource";
    /**
     * 取得portlet标识
     * @return portlet标识
     */
    String getPortletId();

    /**
     * 取得portlet标题
     * @return portlet标题
     */
    String getPortletTitle();

    /**
     * 取得Portlet界面URL
     * @return 界面URL
     */
    String getPortletURL();

    /**
     * 取得相关配置参数
     * @param key 参数标识
     * @return 参数值
     */
    String getPreference(String key);

    /**
     * 取得操作列表
     * @return 操作列表
     */
    List<IPortletAction> getActions();

	/**
	 * 设置配置属性
	 * @param key    配置键  
	 * @param value  配置值
	 */
	void setPreference(String key, String value);
}
