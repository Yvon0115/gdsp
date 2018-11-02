package com.gdsp.platform.common.model;

/**
 * tabs接口类
 * @author xiangguo
 * 
 */
public abstract class ITabs {

    protected String tabId;
    protected String tabName;
    protected String tabUrl;

    public abstract String getTabId();

    public abstract String getTabName();

    public abstract String getTabUrl();
}
