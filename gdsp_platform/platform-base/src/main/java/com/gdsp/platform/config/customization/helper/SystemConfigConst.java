package com.gdsp.platform.config.customization.helper;

/**
 * 定义系统配置使用的常量
 * @author lijy
 *
 */
public interface SystemConfigConst {

    String SYSTEMCONFIG_HOMEPAGE_STATE = "systemConfig.sysHomePage.state"; // 系统配置首页启用状态
    String SYSTEMCONFIG_HOMEPAGE_URL   = "systemConfig.sysHomePage.url";   // 系统配置系统首页地址
    String SYSTEMCONFIG_REPORTINTSINFO = "systemConfig.reportIntsInfo";    // 系统配置报表集成信息
    String SYSTEMCONFIG_MAILSERVICESTATE = "systemConfig.mailServiceState";    // 系统配置邮箱服务启用状态

    String PSP_CATG_CODE = "category001";
    String PSP_TIME_LIMIT = "ext001";
    String PSP_LENGTH = "ext002";
    String PSP_NUMBER_STATE = "ext003";
    String PSP_CARACTER_STATE = "ext004";
    String PSP_CASE_STATE = "ext005";
    String PSP_ENGLISH_STATE = "ext008";
    
    /** 权限时效分类编码 */
    String GRANT_AGING_CATG_CODE = "category002";
    /** 权限时效开关 */
    String GRANT_AGING_STATUS = "ext006";
    /** 权限时效提醒前置时间 */
    String GRANT_AGING_LEADTIME = "ext007";
    /** 权限时效默认值 */
    String GRANT_AGING_DEFAULTAGINGTIME = "ext009";
    /** 权限时效单位值 ：小时*/
    String GRANT_AGING_UNIT_HOUR = "1";
    /** 权限时效单位值 ：天*/
    String GRANT_AGING_UNIT_DAY = "2";
    
    /** 系统邮件服务配置分类编码 */
    String SYS_MAILSERVICE_CATG_CODE = "category003";
    /** 系统邮件服务开关 */
    String SYS_MAILSERVICE_STATUS = "ext010";
    /**  系统邮件服务器配置读取位置 */
    String SYS_MAILCONF_LOCATION = "ext011";
    /** 邮件服务器配置读取位置：数据库 */
    String SYS_MAILCONF_LOCATION_DB = "0";
    /** 邮件服务器配置读取位置：属性文件 */
    String SYS_MAILCONF_LOCATION_PROP = "1";

}
