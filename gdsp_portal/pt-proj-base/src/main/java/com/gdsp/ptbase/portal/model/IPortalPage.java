package com.gdsp.ptbase.portal.model;

import java.util.List;

/**
 * portal页接口规范
 *
 * @author paul.yang
 * @version 1.0 2015-7-30
 * @since 1.6
 */
public interface IPortalPage extends IStyle {

    /**
     * 取得自己定制的Portal页面处理地址
     * @return Portal页面处理地址
     */
    String getPageURL();

    /**
     * 取得数据处理bean
     * @return 数据处理bean
     */
    String getHandlerBean();

    /**
     * 取得布局框类型,暂无实际用途
     * @return 布局框类型
     */
    String getLayoutType();

    /**
     * 取得布局列表
     * @return 布局列表
     */
    List<IPortletLayoutBox> getLayouts();
}
