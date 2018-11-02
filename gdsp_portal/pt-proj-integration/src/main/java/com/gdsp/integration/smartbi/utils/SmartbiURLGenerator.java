package com.gdsp.integration.smartbi.utils;

import com.gdsp.dev.core.common.AppConfig;
import com.gdsp.integration.smartbi.model.SmartbiURLPropVO;

public class SmartbiURLGenerator {
	public static final String  URL_PARAM_SEPARATOR = "&";
	/**
	 * smartbi参数----所有资源类型
	 */
	//user：登录 Smartbi 的用户名。
	public static final String  USER_KEY = "user=";
	//password：登录 Smartbi 的密码。
	public static final String  PASSWORD_KEY = "password=";
	//resid：Smartbi 资源唯一 ID 值。可以从任意资源的“资源属性”对话框上“节点ID”中获取该值
	public static final String  RESID_KEY = "resid=";
	//hiddenParamPanel：是否隐藏参数面板，默认为false。该参数在 v3.0.6 及以后版本上可以使用。
	public static final String  HIDDEN_PARAM_PANEL_KEY = "hiddenParamPanel=";
	
	/**
	 * smartbi参数----附加参数说明
	 */
	//showtoolbar：控制是否在报表的分析页面显示工具栏，默认为true。
	public static final String SHOWTOOLBAR_KEY = "showtoolbar=";
//	public static final String SHOWTOOLBAR_VALUE_TRUE = "true";
//	public static final String SHOWTOOLBAR_VALUE_FALSE = "false";
	//shorttoolbar：控制是否在报表的分析页面显示快速工具栏，默认为false不显示。
	public static final String SHORTTOOLBAR_KEY = "shorttoolbar=";
	//refresh：打开报表是否刷新数据，默认为false。
	public static final String REFRESH_KEY = "refresh=";
	//hidetoolbaritems：控制报表工具栏中需要隐藏的button，其格式如下： REFRESH SAVE SAVEAS MYFAVORITE 
//		中间以空格分割，每个button字符串具体说明如下：
//		•REFRESH 刷新；
//		•SAVE 保存；
//		•SAVEAS 另存为；
//		•MYFAVORITE 保存到收藏夹；
//		•EXPORT 导出；
//		•LOCALFILTERS 局部过滤器管理
//		•CHOOSEFILTERS 选择过滤器
//		•CHOOSEFIELDS 选择输出字段
//		•MULTIHEADER 多重表头
//		•PARAMSLAYOUT 参数布局
//		•ADVSETTING 高级设置
//		•VIEWSQL 查看SQL
//		•VIEW 视图；
//		•SELECTFIELD 增加/删除字段；
//		•CHARTSETTING 图形设置；
//		•PRINT 打印；
//		•PARAMSETTING 参数设置；
//		•SUBTOTAL 分类汇总；
//		•REPORTSETTING 报表设置。
	public static final String HIDETOOLBARITEMS_KEY = "hidetoolbaritems=";
	//shorttoolbaralign：控制快速工具栏对齐方式，取值如下： 
//	•center 居中；
//	•left 居左；
//	•right 居右。
	public static final String SHORTTOOLBARALIGN_KEY = "shorttoolbaralign=";

	public static String getSmartbiURL(SmartbiURLPropVO propertiesVO, String needLogin) {
		StringBuilder sbURL = new StringBuilder();
        sbURL.append(propertiesVO.getBaseURL()).append(URL_PARAM_SEPARATOR);
        //拼接一些具体参数
        sbURL.append(SHOWTOOLBAR_KEY).append(AppConfig.getInstance().getString("smartbi.showtoobar_value"));
        if("Y".equals(needLogin)){
            sbURL.append(URL_PARAM_SEPARATOR);
            sbURL.append(USER_KEY).append(propertiesVO.getUsername());
            sbURL.append(URL_PARAM_SEPARATOR);
            sbURL.append(PASSWORD_KEY).append(propertiesVO.getPassword());
        }
        return sbURL.toString();
    }
	

}
