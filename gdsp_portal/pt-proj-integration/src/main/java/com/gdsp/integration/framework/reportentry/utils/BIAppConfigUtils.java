package com.gdsp.integration.framework.reportentry.utils;

import java.io.Serializable;

import com.gdsp.dev.core.common.AppConfig;

/**
 * 
* @ClassName: AppConfig
* (这里用一句话描述这个类的作用)
* @author hongwei.xu
* @date 2015年7月15日 下午3:22:15
*
 */
public class BIAppConfigUtils implements Serializable {

    /**
    * @Fields serialVersionUID 
    */
    private static final long serialVersionUID = -7859704915440594863L;
    /**
     * cognos server URL
     */
    public static final String      COGNOS_URL;
    /**
     * cognos server password
     */
    public static final String      COGNOS_PSW;
    /**
     * cognos server login user
     */
    public static final String      COGNOS_NAME;
    /**
     * cognos server span 区域
     */
    public static final String      COGNOS_SPAN;
    /**
     * URL编码方式
     */
    public static final String      COGNOS_REPORT_ENCODETYPE;
    /**
     * cognos server 报表读取目录
     */
    public static final String      COGNOS_BASEID;
    public static final String      COGNOS_PERMISSION;
    /**
     * MSTR URL Properties
     */
    public static final String      MSTR_PASSWORD;
    public static final String      MSTR_USER;
    public static final String      MSTR_URL;
    static {
        //cognos properties
        COGNOS_URL = AppConfig.getInstance().getString("base.db.cognos.url");/*AppConfig.getInstance().getString("base.db.cognos.url");*//*"http://192.168.247.205:8081/ibmcognos/cgi-bin/cognos.cgi"*///cognos8
        COGNOS_NAME = AppConfig.getInstance().getString("base.db.cognos.user");/*"admin"*/
        COGNOS_PSW = AppConfig.getInstance().getString("base.db.cognos.password");/*"admin"*/
        COGNOS_SPAN = AppConfig.getInstance().getString("base.db.cognos.span");/*"dbAuth"*/
        COGNOS_BASEID = AppConfig.getInstance().getString("base.db.cognos.baseforder");/*"/content/package[@name='资本市场运行监测系统']/folder[@name='1.资本市场运行监测系统_月报']"*/
        COGNOS_PERMISSION = AppConfig.getInstance().getString("base.db.cognos.permissionurl");
        COGNOS_REPORT_ENCODETYPE = AppConfig.getInstance().getString("cognos.report.encodetype");
        //MSTR properties
        MSTR_PASSWORD = AppConfig.getInstance().getString("base.db.mstr.password");
        MSTR_USER = AppConfig.getInstance().getString("base.db.mstr.user");
        MSTR_URL = AppConfig.getInstance().getString("base.db.mstr.url");
    }
}
