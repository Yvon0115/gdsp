package com.gdsp.integration.cognos.model;

/**
 * @ClassName: BICognoAccessPropertiesVO
 * (这里用一句话描述这个类的作用)
 * @author hongwei.xu
 * @date 2015年7月17日 上午10:54:34
 */
public class BICognoURLPropertiesVO extends BIIntegrationURLVO {

    /**
    * @Fields serialVersionUID 
    */
    private static final long serialVersionUID = -7336387284182991267L;
    /**
     * cognos网关
     */
    private String            ui_gateway;
    /**
     * 显示工具<BR>
     * ICognosConstant.report：固定报表<BR>
     * ICognosConstant.query：查询报表<BR>
     * ICognosConstant.analysis:分析报表<BR>
     */
    private String            ui_tool;
    /**
     * 报表唯一URL
     */
    private String            ui_object;
    /**
     * cognosBasicURL
     */
    private String            cognosBasicURL;
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
    /**
     * cognos登录命名空间
     */
    private String            span;
    /**
     * cognos登录用户名
     */
    private String            username;
    /**
     * cognos登录密码
     */
    private String            password;

    public String getSpan() {
        return span;
    }

    public void setSpan(String span) {
        this.span = span;
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

    public String getUi_gateway() {
        return ui_gateway;
    }

    public void setUi_gateway(String ui_gateway) {
        this.ui_gateway = ui_gateway;
    }

    public String getUi_tool() {
        return ui_tool;
    }

    public String getCognosBasicURL() {
        return cognosBasicURL;
    }

    public void setCognosBasicURL(String cognosBasicURL) {
        this.cognosBasicURL = cognosBasicURL;
    }

    /**
     * 设置显示工具<BR>
     * CognosViewer：固定报表<BR>
     * QueryStudio：查询报表<BR>
     * Analysis Studio:分析报表<BR>
     */
    public void setUi_tool(String ui_tool) {
        this.ui_tool = ui_tool;
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
