package com.gdsp.ptbase.portal.web.tags;

import java.io.IOException;
import java.io.Writer;
import java.util.Map;

import javax.servlet.ServletException;

import org.apache.commons.lang3.StringUtils;

import com.gdsp.dev.base.exceptions.DevRuntimeException;
import com.gdsp.dev.core.common.AppConfig;
import com.gdsp.dev.web.mvc.tags.alib.IncludeTag;
import com.gdsp.ptbase.portal.helper.PortalHelper;
import com.gdsp.ptbase.portal.model.IPortlet;

/**
 * portlet标签
 *
 * @author paul.yang
 * @version 1.0 2015-7-31
 * @since 1.6
 */
public class PortletTag extends IncludeTag {
    /**
     * portlet配置参数
     */
    public static final String PROP_PORTLET = "config";
    /**
     * 配置属性
     */
    Map<String, Object>        properties   = null;
    /**
     * 默认的portlet url
     */
    private String             defultPortletURL;

    @Override
    public void setProperties(Map<String, Object> properties) {
        this.properties = properties;
    }

    @Override
    public boolean start(Writer writer) throws ServletException, IOException {
        Object propValue = properties.get(PROP_PORTLET);
        if (propValue == null || !(propValue instanceof IPortlet))
            throw new DevRuntimeException("Please set valid 'portlet' property");
        IPortlet portlet = (IPortlet) propValue;
        String url = portlet.getMeta().getPortletURL();
        if (StringUtils.isEmpty(url)) {
            url = getDefultPortletURL();
        }
        PortalHelper.setCurrentPortlet(portlet);
        include(url, writer);
        PortalHelper.removeCurrentPortlet();
        return false;
    }

    @Override
    public boolean end(Writer writer, String body) {
        return false;
    }

    @Override
    public void close() {}

    @Override
    public boolean useBody() {
        return false;
    }

    @Override
    public boolean isSingleton() {
        return false;
    }

    /**
     * 取得默认的portlet url
     * @return url串
     */
    public String getDefultPortletURL() {
        if (defultPortletURL == null) {
            defultPortletURL = AppConfig.getInstance().getString(PortalHelper.OPTIONS_PORTLETDEFAULTURL, PortalHelper.DEFAULT_PORTLETURL);
        }
        return defultPortletURL;
    }
}
