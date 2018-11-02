package com.gdsp.ptbase.portal.model;

import java.util.List;

/**
 * Portlet在页面中的配置
 *
 * @author paul.yang
 * @version 1.0 2015-7-30
 * @since 1.6
 */
public interface IPortlet extends IStyle {

    /**
     * 取得portlet标题
     * @return portlet标题
     */
    String getPortletTitle();

    /**
     * 取得使用的portlet对象
     * @return portlet对象
     */
    IPortletMeta getMeta();

    /**
     * 取得操作列表
     * @return 操作列表
     */
    List<IPortletAction> getPortletActions();

    /**
     * 取得相关配置参数
     * @param key 参数标识
     * @return 参数值
     */
    String getPreference(String key);
}
