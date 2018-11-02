package com.gdsp.integration.framework.reportentry.utils;

public interface ReportConst {

    //cognos
    /**
     * COGNOS_REPORT_TYPE_FOLDER
     */
    String COGNOS_REPORT_TYPE_FOLDER   = "folder";
    /**
     * COGNOS_REPORT_TYPE_QUERY
     */
    String COGNOS_REPORT_TYPE_QUERY    = "query";
    /**
     * COGNOS_REPORT_TYPE_REPORT
     */
    String COGNOS_REPORT_TYPE_REPORT   = "report";
    /**
     * COGNOS_REPORT_TYPE_ANALYSIS
     */
    String COGNOS_REPORT_TYPE_ANALYSIS = "analysis";
    /**
     * cognos原生报表
     */
    String COGNOS_REPORT_TYPE_FIX      = "fix_report";
    /**
     * COGNOS_REPORT_TYPE_DEMO
     */
    String COGNOS_REPORT_TYPE_DEMO     = "demo";
    /**cognos——cube报表参数*/
    String cUBE_PARAM_VALUEString      = "@value@";
    String Cognos                      = "cognos";
    /**
     * cognos包类型
     */
    String COGNOS_REPORT_TYPE_PACKAGE  = "package";
    /////////////////////////////////////////////////////////////////
    //公共属性
    /**连接过期时间（设置为4分钟）*/
    Long   EXPIRED_TIME                = (long) (4 * 60 * 1000);
    /**参数无条件*/
    String CONDITION_TYPE_NO           = "no";
    /**参数模板*/
    String PARAM_TEMPLATE              = "template";
    /**参数自游定义*/
    String PARAM_free                  = "free";
    /**
     * 同步到组件时的url
     */
    String LOAD_URL                    = "/portal/portlet/report.d";
    /**报表同步到本地获取路径属性后缀**/
    String REPORT_SYN_PATH             = ".path";
    ///////////////////////////////////////////////////////////////////////
    //润乾
    String ECODING_METHOD_UTF          = "GBK";
    String SUFFIX                      = ".raq";
    String ARG_SUFFIX                  = "_arg.raq";
    String RUNQIAN_QUERY_SUFFIX        = "/reportJsp/showReport.jsp?raq=";
    String RUNQIAN_EXPORT_SUFFIX       = "/reportJsp/open.jsp";
    /**
    * 模板文件相对路径
    */
    String TEMPLATE_PATH               = "/gdsp/page/portlet/condition";
    String RUNQIAN                     = "runqian";
    //////////////////////////////////////////////////////////////////////////
    //birt
    String BIRT                        = "birt";
    String BIRT_QUERY_SUFFIX           = "__report=";
    String BIRT_EXPORT_SUFFIX          = "&__format=";
    /////////////////////////////////////////////////////////////////
    /**
     * 参数属性
     */
    String DOC_LIST                    = "doclist";
    String MANUAL                      = "manual";
    
    //smartbi
    /**
     * 01.分析类型资源
     */
    // 透视分析
    String SMARTBI_REPORT_TYPE_INSIGHT = "INSIGHT";
    // 组合分析
    String SMARTBI_REPORT_TYPE_COMBINED_QUERY = "COMBINED_QUERY";
    // 灵活分析
    String SMARTBI_REPORT_TYPE_SIMPLE_REPORT = "SIMPLE_REPORT";
    // 仪表分析
    String SMARTBI_REPORT_TYPE_DASHBOARD = "Dashboard";
    // 地图分析
    String SMARTBI_REPORT_TYPE_DASHBOARDMAP = "DashboardMap";
    // 多维分析
    String SMARTBI_REPORT_TYPE_OLAP_REPORT = "OLAP_REPORT";
    // 电子表格
    String SMARTBI_REPORT_TYPE_SPREADSHEET_REPORT = "SPREADSHEET_REPORT";
    // Office分析
    String SMARTBI_REPORT_TYPE_OFFICE_REPORT = "OFFICE_REPORT";
    // 页面
    String SMARTBI_REPORT_TYPE_PAGE = "PAGE";
    // WEB链接
    String SMARTBI_REPORT_TYPE_URL = "URL";
    // 本地文档
    String SMARTBI_REPORT_TYPE_FILE_RESOURCE = "FILE_RESOURCE";
    /**
     * 02.查询类型资源
     */
    // 可视化查询
    String SMARTBI_REPORT_TYPE_BUSINESS_VIEW = "BUSINESS_VIEW";
    // SQL查询
    String SMARTBI_REPORT_TYPE_TEXT_BUSINESS_VIEW = "TEXT_BUSINESS_VIEW";
    // 原生SQL查询
    String SMARTBI_REPORT_TYPE_RAWSQL_BUSINESS_VIEW = "RAWSQL_BUSINESS_VIEW";
    // 存储过程查询
    String SMARTBI_REPORT_TYPE_PROC_BUSINESS_VIEW = "PROC_BUSINESS_VIEW";
    // JAVA查询
    String SMARTBI_REPORT_TYPE_JAVA_BUSINESS_VIEW = "JAVA_BUSINESS_VIEW";
    /**
     * 03.其他类型
     */
    // 公共文件夹
    String SMARTBI_REPORT_TYPE_DEFAULT_TREENODE = "DEFAULT_TREENODE";
    // 私有文件夹
    String SMARTBI_REPORT_TYPE_SELF_TREENODE = "SELF_TREENODE";
    // 复杂报表
    String SMARTBI_REPORT_TYPE_FREE_REPORT = "FREE_REPORT";
    // Excel导入模板
    String SMARTBI_REPORT_TYPE_DAQ_IMPORTCONFIG = "DAQ_IMPORTCONFIG";
    
    //报表参数类型：无参数
    String SMARTBI_CONDITION_TYPE_NO = "no";
    //查看报表前缀
    String SMARTBI_QUERY_SUFFIX = "/vision/openresource.jsp?resid=";
    //导出报表前缀
    String SMARTBI_EXPORT_SUFFIX = "/vision/openresource.jsp";
    
    String AC_DATASOURCE_TYPE="bi_type";
}
