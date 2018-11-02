package com.gdsp.integration.smartbi.model;

import java.util.HashMap;
import java.util.Map;

import com.gdsp.dev.core.model.entity.BaseEntity;

/**
 * 
* @ClassName: BICognoAccessPropertiesVO
* (这里用一句话描述这个类的作用)
* @author hongwei.xu
* @date 2015年7月17日 上午10:54:34
*
 */
public class SmartbiURLPropVO extends BaseEntity {
	private static final long serialVersionUID = -4259182249318977172L;
	/**
     * Base URL
     */
    private String url;
    /**
     * 具有访问权限的用户
     */
    private String username;
    /**
     * 具有访问权限的用户对应的密码
     */
    private String password;
    /**
     * 传递参数MAP
     * 报表参数MAP，Key：Value p_strat_date:2014-04-02
     */
    private Map<String, Object> parameters = new HashMap<String, Object>();
    /**
     * 报表唯一URL
     */
    private String            ui_object;
    /**
     * smartbiBasicURL
     */
    private String            baseURL;
    /**
     * 指定是否显示Cognos 标题栏，默认不显示
     * “True” – Header will be displayed. This is set
     * by default.
     * “False” – Header will not be displayed.
     */
    private boolean           isHeaderShow     = false;
    /**
     * 指定是否显示Cognos 工具栏，默认不显示
     * “True” – Toolbar will be displayed. This is set
     * by default.
     * “False” – Toolbar will not be displayed.
     */
    private boolean           isToolbarShow    = false;
    /**
     * 是否显示参数输入：false：不显示，true：显示
     * 默认不显示
     */
    private boolean           isPromptShow     = false;
    /**
     * 可用的报表格式包括：
     * CSV, HTML, HTMLFragment, MHT, PDF, singleXLS, XHTML, XLS, XLWA,XML
     */
    private String            outputFormat     = "HTML";

    
    
    public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Map<String, Object> getParameters() {
		return parameters;
	}

	public void setParameters(Map<String, Object> parameters) {
		this.parameters = parameters;
	}

	public String getBaseURL() {
		return baseURL;
	}

	public void setBaseURL(String baseURL) {
		this.baseURL = baseURL;
	}

	public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUi_object() {
        return ui_object;
    }

    public void setUi_object(String ui_object) {
        this.ui_object = ui_object;
    }

    public String getOutputFormat() {
        return outputFormat;
    }

    public void setOutputFormat(String outputFormat) {
        this.outputFormat = outputFormat;
    }

    public boolean isHeaderShow() {
        return isHeaderShow;
    }

    public void setHeaderShow(boolean isHeaderShow) {
        this.isHeaderShow = isHeaderShow;
    }

    public boolean isToolbarShow() {
        return isToolbarShow;
    }

    public void setToolbarShow(boolean isToolbarShow) {
        this.isToolbarShow = isToolbarShow;
    }

    public boolean isPromptShow() {
        return isPromptShow;
    }

    public void setPromptShow(boolean isPromptShow) {
        this.isPromptShow = isPromptShow;
    }
}
