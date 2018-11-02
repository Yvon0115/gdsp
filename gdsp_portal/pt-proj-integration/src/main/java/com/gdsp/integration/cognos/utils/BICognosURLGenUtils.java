package com.gdsp.integration.cognos.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gdsp.dev.core.common.AppConfig;
import com.gdsp.integration.cognos.model.BICognoURLPropertiesVO;
import com.gdsp.integration.framework.reportentry.utils.BIAppConfigUtils;
import com.gdsp.integration.framework.reportentry.utils.ReportConst;

/**
 * Congos URL生成工具
 * @author hongwei.xu
 * @date 2015年7月17日 下午2:33:11
 */
public class BICognosURLGenUtils {

    /**
     * cognos显示方式
     */
    public static final String  UI_TOOL_VALUE_COGNOSVIEWER   = "CognosViewer";
    public static final String  UI_TOOL_VALUE_QUERYSTUDIO    = "QueryStudio";
    public static final String  UI_TOOL_VALUE_ANALYSISSTUDIO = "AnalysisStudio";
    /**
     * --------------------URLKEY ---------------------
     */
    public static final String  UI_TOOL_KEY                  = "ui.tool=";
    public static final String  UI_OBJECT_KEY                = "ui.object=";
    public static final String  UI_ACTION_KEY                = "ui.action=";
    public static final String  UI_GATEWAY_KEY               = "ui.gateway=";
    /**
     * 是否显示表头
     */
    public static final String  UI_HEADER_KEY                = "cv.header=";
    /**
     * 是否显示工具栏
     */
    public static final String  UI_TOOLBAR_KEY               = "cv.toolbar=";
    /**
     * 是否显示参数输入
     * 一般不显示，通过URL传递参数
     */
    public static final String  RUN_PROMPT_KEY               = "run.prompt=";
    /**
     * 输出格式
     */
    public static final String  RUN_OUTPUTFORMAT_KEY         = "run.outputFormat=";
    /**是否同一个窗口打开 true:是 false 新窗口*/
    public static final String  LAUNCH_OPENJSSTUDIOINFRAME   = "launch.openJSStudioInFrame=";
    /**
     * UIACTION:对对象的操作
     * Run, New, Edit
     * Note: Not all of these are valid for all Cognos
     * components. See Appendix or examples when
     * to use these parameters
     */
    public static final String  UI_ACTION_VALUE_RUN          = "run";
    public static final String  UI_ACTION_VALUE_EDIT         = "edit";
    public static final String  UI_ACTION_VALUE_NEW          = "new";
    /**
     * URL 分隔符
     */
    public static final String  URL_SEPARATOR                = "&";
    /**
     * URL 等号符
     */
    public static final String  URL_EQUALS                   = "=";
    /**
     * 日志变量
     */
    private static final Logger logger                       = LoggerFactory.getLogger(BICognosURLGenUtils.class);

    /**
     * 	@Title: getCognosViewerCognosURL<BR>
    * Full URL:<BR>
    * http://server-name/Cognos8/cgibin/<BR>
    * cognos.cgi?b_action=xts.run&m=portal/launch.xts&ui.tool=CognosViewer&ui.action=run&ui.object=/content<BR>
    * (getCognosViewerCognosURL)<BR>
    * @Title: getCognosViewerCognosURL
    * (这里用一句话描述这个方法的作用)
    * @param urlVO
    * @return String   
    *      */
    public static String getCognosViewerCognosURL(BICognoURLPropertiesVO urlVO) {
        StringBuilder sbURL = new StringBuilder();
        //gate way
        sbURL.append(getCognosBasicURL(urlVO));
        sbURL.append("b_action=cognosViewer").append(URL_SEPARATOR);
        //		sbURL.append(UI_TOOL_KEY).append(UI_TOOL_VALUE_COGNOSVIEWER).append(URL_SEPARATOR);
        sbURL.append(UI_ACTION_KEY).append(UI_ACTION_VALUE_RUN);
        return sbURL.toString();
    }

    /**
     * 	@Title: getCognosQueryStudioURL<BR>
     * 启动 Query Studio 时:<BR>
     * ui.tool 需要设置为 “QueryStudio”。<BR>
     * ui.action 可以设置为 “new” 或 “edit”。<BR>
     * 只能查看在Query Studio 中创建的报表。<BR>
    * Full URL:<BR>
    * http://server-name/Cognos8/cgibin<BR>
    * cognos.cgi?b_action=xts.run&m=portal/launch.xts&ui.tool=QueryStudio&ui.object=/content&ui.action=edit<BR>
    * (getCognosQueryStudioURL)<BR>
    * (ui.action 可以设置为 “new” 或 “edit”。)
    * @param urlVO
    *      */
    public static String getCognosQueryStudioURL(BICognoURLPropertiesVO urlVO) {
    	StringBuilder sbURL = new StringBuilder();
        //gate way
        sbURL.append(getCognosBasicURL(urlVO));
        sbURL.append("b_action=xts.run").append(URL_SEPARATOR);
        sbURL.append(UI_TOOL_KEY).append(UI_TOOL_VALUE_QUERYSTUDIO).append(URL_SEPARATOR);
        sbURL.append(UI_ACTION_KEY).append(UI_ACTION_VALUE_EDIT);
        return sbURL.toString();
    }

    /**
     * 	@Title: getAnalysisQueryStudioURL<BR>
     * 启动 Analysis Studio 时:<BR>
     * • ui.gateway 必须设置. 例如：ui.gateway=http://server-name/Cognos8/cgi-bin/Cognos.cgi。<BR>
     * • ui.tool 需要设置为 “AnalysisStudio”。<BR>
     * • ui.action 可以设置为 “new” 或 “edit”。<BR>
     * • 只能查看在Analysis Studio 中创建的报表。<BR>
     * • 默认情况下 Analysis Studio 在一个独立的窗口中打开，要在同一窗口或frame 中打开设置参数<BR>
     * launch.launchJSStudioInFrame=true<BR>
     * ------------------------------------------------------------------------------
     * 使用指定包打开Analysis Studio<BR>
     * http://192.168.130.128:9300/p2pd/servlet/dispatch?b_action=xts.run&m=portal/launch.xts&<BR>
     * ui.object=%2Fcontent%2Ffolder%5B%40name%3D%27%E7%A4%BA%E6%A0%B7%27%5D%2Ffolder%5B%40name%3D%27%E5%A4%9A%E7%BB%B4%E6%95%B0%E6%8D%AE%E9%9B%86%27%5D%2Fpackage%5B%40name%3D%27Sales+and+Marketing+Zh+%28cube%29%27%5D%2Ffolder%5B%40name%3D%27Analysis+Studio+%E6%8A%A5%E8%A1%A8%E7%A4%BA%E6%A0%B7%27%5D%2Fanalysis%5B%40name%3D%27%E8%87%AA%E5%AE%9A%E4%B9%89%E6%8E%92%E5%90%8D%E7%A4%BA%E6%A0%B7%27%5D<BR>
     *&ui.drillThroughTargetParameterValues=&<BR>
     *ui.gateway=%2fp2pd%2fservlet%2fdispatch&<BR>
     *ui.tool=AnalysisStudio&<BR>
     *ui.action=edit&<BR>
     * 新窗口打开属性 true:同一个窗口打开<BR>
     *launch.openJSStudioInFrame=true<BR>
    * -------------------------------------------------------------------------------
    * (getAnalysisQueryStudioURL)<BR>
    * (ui.action 可以设置为 “new” 或 “edit”。)
    * @param urlVO
    *      */
    public static String getAnalysisQueryStudioURL(BICognoURLPropertiesVO urlVO) {
    	StringBuilder sbURL = new StringBuilder();
        sbURL.append(getCognosBasicURL(urlVO));
        //Analysis Studio 时 ui.gateway是必须使用的
        sbURL.append("b_action=xts.run&ui.drillThroughTargetParameterValues=").append(URL_SEPARATOR);
        sbURL.append(UI_GATEWAY_KEY).append(URLEncoder.encode(getUIGatewayByUi_gateway(urlVO.getUi_gateway()))).append(URL_SEPARATOR);
        sbURL.append(UI_TOOL_KEY).append(UI_TOOL_VALUE_ANALYSISSTUDIO).append(URL_SEPARATOR);
        sbURL.append(UI_ACTION_KEY).append(UI_ACTION_VALUE_EDIT).append(URL_SEPARATOR);
        sbURL.append(LAUNCH_OPENJSSTUDIOINFRAME).append(Boolean.TRUE);
        return sbURL.toString();
    }

    /**
     * 
    * @Title: getCogbosBasicURL
    * (构造访问cognos基础URL)
    * @return String  
    *      */
    private static String getCognosBasicURL(BICognoURLPropertiesVO urlVO) {
    	StringBuilder sbURL = new StringBuilder();
        //gate way
        sbURL.append(urlVO.getUi_gateway());
        if (!StringUtils.endsWith(sbURL.toString(), "?")) {
            sbURL.append("?");
        }
        sbURL.append("m=portal/launch.xts").append(URL_SEPARATOR);
        //Analysis Studio 时 ui.gateway是必须使用的
        if (StringUtils.isEmpty(BIAppConfigUtils.COGNOS_REPORT_ENCODETYPE)) {
            sbURL.append(UI_OBJECT_KEY).append(urlVO.getUi_object()).append(URL_SEPARATOR);
        } else {
            try {
                sbURL.append(UI_OBJECT_KEY).append(URLEncoder.encode(urlVO.getUi_object(), BIAppConfigUtils.COGNOS_REPORT_ENCODETYPE)).append(URL_SEPARATOR);
            } catch (UnsupportedEncodingException e) {
            	logger.error(e.getMessage(),e);
            }
        }
        sbURL.append(UI_HEADER_KEY).append(urlVO.isHeaderShow()).append(URL_SEPARATOR);
        sbURL.append(UI_TOOLBAR_KEY).append(urlVO.isToolbarShow()).append(URL_SEPARATOR);
        sbURL.append(RUN_PROMPT_KEY).append(urlVO.isPromptShow()).append(URL_SEPARATOR);
        //导出格式在页面端进行动态设置
        /*sbURL.append(RUN_OUTPUTFORMAT_KEY).append(urlVO.getOutputFormat()).append(URL_SEPARATOR);*/
        //处理参数 采用 form提交，无需处理参数
        Map<String, Object> mapParams = urlVO.getParameters();
        Iterator<String> it = mapParams.keySet().iterator();
        while (it.hasNext()) {
            String param = it.next();
            Object obj = mapParams.get(param);
            sbURL.append(param).append(URL_EQUALS).append(obj).append(URL_SEPARATOR);
        }
        return sbURL.toString();
    }

    /**
     * 
    * @Title: getCognosURL
    * (构造CognosUI)
    * @param urlPropertiesVO  
    * @return void  
    *      */
    public static String getCognosURL(BICognoURLPropertiesVO propertiesVO) {
        //设置cognosURL
        propertiesVO.setUi_gateway(propertiesVO.getCognosBasicURL());
        String strCognosURL = null;
        //report 类型
        //query 类型
        if (ReportConst.COGNOS_REPORT_TYPE_QUERY.equals(propertiesVO.getUi_tool())) {
            strCognosURL = BICognosURLGenUtils.getCognosQueryStudioURL(propertiesVO);
        }
        // analysis类型
        else if (ReportConst.COGNOS_REPORT_TYPE_ANALYSIS.equals(propertiesVO.getUi_tool())) {
            strCognosURL = BICognosURLGenUtils.getAnalysisQueryStudioURL(propertiesVO);
        } else {
            strCognosURL = BICognosURLGenUtils.getCognosViewerCognosURL(propertiesVO);
        }
        logger.debug(strCognosURL);
        return strCognosURL + getLoginUrl();
    }

    /**
     * 
    * @Title: getLoginUrl
    * (cognos认证URL)
    * @return    参数说明
    * @return String
    *      */
    private static String getLoginUrl() {
        String needLogin = AppConfig.getInstance().getString("cognos.login.switch", "N");
        //匿名不需要认证
        if ("N".equals(needLogin)) {
            return "";
        }
        //一个用户进行认证
        else {
            StringBuilder sbStringBuffer = new StringBuilder();
            String cnameString = BIAppConfigUtils.COGNOS_NAME;
            String cpsw = BIAppConfigUtils.COGNOS_PSW;
            String cspan = BIAppConfigUtils.COGNOS_SPAN;
            sbStringBuffer.append("&CAMUsername=");
            sbStringBuffer.append(cnameString);
            sbStringBuffer.append("&CAMPassword=");
            sbStringBuffer.append(cpsw);
            sbStringBuffer.append("&CAMNamespace=");
            sbStringBuffer.append(cspan);
            return sbStringBuffer.toString();
        }
    }

    /**
     * 
    * @Title: getCognosURLMap
    * (批量获取URLMmap)
    * @param mapPropertiesVO
    * key:portletkey<BR>
    * value:BICognoURLPropertiesVO<BR>
    * @return Map<String,String> 
    *      */
    public static Map<String, String> getCognosURLMap(Map<String, BICognoURLPropertiesVO> mapPropertiesVO) {
        Map<String, String> urlMap = new HashMap<String, String>();
        //设置cognosURL
        if (mapPropertiesVO == null || mapPropertiesVO.isEmpty()) {
            return urlMap;
        }
        Iterator<String> it = mapPropertiesVO.keySet().iterator();
        while (it.hasNext()) {
            String key = it.next();
            BICognoURLPropertiesVO propertiesVO = mapPropertiesVO.get(key);
            String strCognosURL = BICognosURLGenUtils.getCognosURL(propertiesVO);
            urlMap.put(key, strCognosURL);
        }
        return urlMap;
    }

    /**
     * 
    * @Title: getUIGatewayByUi_gateway
    * (更具UI_getWay 获得 uigetewaykey)
    * @param Ui_gateway
    * @return String 
    *      */
    private static String getUIGatewayByUi_gateway(String Ui_gateway) {
        //Ui_gateway：http://192.168.130.128:9300/p2pd/servlet/dispatch?
        ///Ui_gatewaykey:p2pd/servlet/dispatch?
        if (StringUtils.isEmpty(Ui_gateway)) {
            return null;
        }
        String midkey = StringUtils.substringAfter(StringUtils.replace(Ui_gateway, "http://", ""), "/");
        return "/" + midkey;
    }
}
