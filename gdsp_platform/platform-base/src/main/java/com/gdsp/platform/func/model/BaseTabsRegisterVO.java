package com.gdsp.platform.func.model;

/**
 * tab页签
 * @author xiangguo
 *
 */
public abstract class BaseTabsRegisterVO extends BaseRegisterVO {

    private static final long serialVersionUID = 1L;
    //临时属性，表中并无此字段
    protected String          tabId;
    protected String          tabName;
    protected String          tabUrl;
    protected String          tabEntityType;

    public String getTabId() {
        if (this.tabId != null && !"".equals(this.tabId)) {
            return this.tabId;
        } else {
            return this.getId();
        }
    }

    public String getTabName() {
        if (this.tabName != null && !"".equals(this.tabName)) {
            return this.tabName;
        } else {
            return this.funname;
        }
    }

    public String getTabUrl() {
        if (this.tabUrl != null && !"".equals(this.tabUrl)) {
            return this.tabUrl;
        } else {
            return this.url;
        }
    }

    public abstract String getTabEntityType();

    public void setTabId(String tabId) {
        this.tabId = tabId;
    }

    public void setTabName(String tabName) {
        this.tabName = tabName;
    }

    public void setTabUrl(String tabUrl) {
        this.tabUrl = tabUrl;
    }

    public void setTabEntityType(String tabEntityType) {
        this.tabEntityType = tabEntityType;
    }
}
