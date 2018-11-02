package com.gdsp.ptbase.appcfg.model;

import java.io.Serializable;

import com.gdsp.ptbase.portal.model.IPortlet;
import com.gdsp.ptbase.portal.model.IPortletLayoutBox;

/**
 * 布局容器对象
 */
public class PorletLayoutBox implements IPortletLayoutBox, Serializable {

    /**
     * 序列id
     */
    private static final long  serialVersionUID = -4657314495996966380L;
    /**
     * 布局描述信息
     */
    private IPortletLayoutMeta meta;
    /**
     * 包含的portlet列表
     */
    private IPortlet[]         portlets;

    public IPortletLayoutMeta getMeta() {
        return meta;
    }

    public void setMeta(IPortletLayoutMeta meta) {
        this.meta = meta;
    }

    @Override
    public IPortlet[] getPortlets() {
        return portlets;
    }

    public void setPortlets(IPortlet[] portlets) {
        this.portlets = portlets;
    }
}
