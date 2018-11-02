package com.gdsp.ptbase.portal.model;

/**
 * 布局框
 *
 * @author paul.yang
 * @version 1.0 2015-7-30
 * @since 1.6
 */
public interface IPortletLayoutBox {

    /**
     * 取得布局框包含的portlet列表
     * @return 布局框包含的portlet列表
     */
    IPortlet[] getPortlets();
}
