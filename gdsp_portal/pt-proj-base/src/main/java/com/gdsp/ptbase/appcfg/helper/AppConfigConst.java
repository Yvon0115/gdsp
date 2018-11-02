package com.gdsp.ptbase.appcfg.helper;

public interface AppConfigConst {

    /**
     * 目录类型-部件目录
     */
    String DIR_WIDGETMETA                       = "widgetmeta";
    /** 页面根目录 */
    String PK_PUB_PAGEDIR_ROOT                  = "100001";
    //	/** 公共资源 */
    //	String PK_PUB_RESOURCE = "100002";
    /**
     * 
     * 资源类型常量
     */
    String RES_TYPE_ALL                         = "all";
    String RES_TYPE_COGNOS                      = "cognos";
    String RES_TYPE_MSTR                        = "mstr";
    String RES_TYPE_PUB                         = "pub";
    //------自定义档案KEY start-------------
    /**
     * 组件数据类型
     */
    String WIDGET_PARAM_DATA_TYPE               = "ac_data_type";
    /**
     * 组件数据源类型
     */
    String WIDGET_DATA_SOURCE_TYPE              = "param_source";
    /**
     * 组件显示类型
     */
    String WIDGET_AC_COMP_TYPE                  = "ac_comp_type";
    /**
     * 资源类型
     */
    String WIDGET_AC_RES_TYPE                   = "ac_res_type";
    /**
     * 组件显示类型
     */
    String WIDGET_AC_PARAM_TYPE                 = "ac_param_type";
    /**
     * 自定义函数(取默认值）
     */
    String WIDGET_AC_PARAM_DFVALUE              = "ac_param_dfvalue";
    /**
     * 报表类型
     */
    String WIDGET_AC_RPT_TYPE                   = "ac_rpt_type";
    /**
     * 时间函数
     */
    String WIDGET_AC_COGNOS_PARAM_FUNTYPE       = "sys_datefun";
    /**
     * 数据权限
     */
    String WIDGET_AC_DATA_POWER                 = "ac_data_power";
    //------自定义档案KEY end -------------
    /**
     * 页面配置 功能发布URL
     */
    String PORTAL_PUBLISH_MAINURL               = "portal.publish.mainurl";
    /**
     * #功能说明存储地址
     */
    String PORTAL_FUNCTIONDEC_IMAGES_ADDR       = "portal.functiondec.images.addr";
    /**
     * #功能说明存储地址
     */
    String PORTAL_FUNCTIONDEC_IMAGES_IMAGESPATH = "/images";
    /**
     * #离线报表存储地址
     */
    String PORTAL_OFFLINERRPORT_UPLOAD_ADDR     = "portal.offlineReport.upload.addr";
    /**
     * #报表导出类型
     */
    String COGNOS_EXPORT_TYPES                  = "cognos.export.types";
}
