package com.gdsp.ptbase.portal.helper;

import com.gdsp.dev.core.common.AppContext;
import com.gdsp.ptbase.portal.model.IPortlet;

/**
 * Portal相关敞亮
 *
 * @author paul.yang
 * @version 1.0 2015-7-31
 * @since 1.6
 */
public final class PortalHelper {

    /**
     * 私有构造方法
     */
    private PortalHelper() {}

    /**
     * 默认的页面模板配置键值
     */
    public static final String CURRENT_PORTLET                   = "__currentPortlet";
    /**
     * 默认的页面模板配置键值
     */
    public static final String OPTIONS_DEFAULTPORTALPAGETEMPLATE = "portal.page.defaultTemplate";
    /**
     * 默认的页面模板配置键值
     */
    public static final String OPTIONS_DEFAULTPORTLETTEMPLATE    = "portal.page.defaultTemplate";
    /**
     * 默认的页面模板配置键值
     */
    public static final String OPTIONS_PORTLETDEFAULTURL         = "portal.portlet.defaultUrl";
    /**
     * 默认的页面模板
     */
    public static final String DEFAULT_PAGETEMPLATE              = "portal/page/default";
    /**
     * 默认的portlet模板
     */
    public static final String DEFAULT_PORTLETEMPLATE            = "portal/portlet/default";
    /**
     * 默认的portlet处理url
     */
    public static final String DEFAULT_PORTLETURL                = "/portal/portlet/default.d";

    /**
     * 设置当前的porlet配置
     * @param portlet 配置
     */
    public static void setCurrentPortlet(IPortlet portlet) {
        AppContext.getContext().setAttribute(AppContext.REQUEST, CURRENT_PORTLET, portlet);
    }

    /**
     * 取得当前的porlet
     * @return portlet配置
     */
    public static IPortlet getCurrentPortlet() {
        return (IPortlet) AppContext.getContext().getAttribute(AppContext.REQUEST, CURRENT_PORTLET);
    }

    /**
     * 删除当前的porlet配置
     */
    public static void removeCurrentPortlet() {
        AppContext.getContext().removeAttribute(AppContext.REQUEST, CURRENT_PORTLET);
    }
}
