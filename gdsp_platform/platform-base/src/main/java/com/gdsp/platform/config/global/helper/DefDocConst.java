package com.gdsp.platform.config.global.helper;

/**
 * 系统码表常量
 * @author pc
 */
public interface DefDocConst {

    /** 组件数据类型 */
    String WIDGET_PARAM_DATA_TYPE         = "ac_data_type";
    /** 组件数据源类型 */
    String WIDGET_DATA_SOURCE_TYPE        = "ac_datasource_type";
    /**  组件显示类型  */
    String WIDGET_AC_COMP_TYPE            = "ac_comp_type";
    /** 资源类型 */
    String WIDGET_AC_RES_TYPE             = "ac_res_type";
    /** 组件显示类型 */
    String WIDGET_AC_PARAM_TYPE           = "ac_param_type";
    /**  自定义函数(取默认值） */
    String WIDGET_AC_PARAM_DFVALUE        = "ac_param_dfvalue";
    /**  cognos报表类型 */
    String WIDGET_AC_COGNOS_RPT_TYPE      = "ac_cognos_rpt_type";
    /** 时间函数 */
    String WIDGET_AC_COGNOS_PARAM_FUNTYPE = "sys_datefun";
    /** 数据权限 */
    String WIDGET_AC_DATA_POWER           = "ac_data_power";
    
    /** 数据源类型 */
    final String DATASOURCE_TYPE          = "DatasourceType";
    /** 数据库数据源类型 */
    final String DATASOURCE_TYPE_DB       = "DBType";
    /** 报表工具数据源类型 */
    final String DATASOURCE_TYPE_BI       = "BIType";
    /** 大数据数据源类型 */
    final String DATASOURCE_TYPE_BGDB     = "BigDataDBType";
    /** 对应码表中的大数据数据源类型*/
	final String       DATASOURCE_HUAWEI_HIVE		  	= "HuaweiHive";
	final String 	   DATASOURCE_HUAWEI_MPP		  	= "HuaweiMpp";
	final String       DATASOURCE_XINGHUAN_INCEPTOR  	= "XinghuanInceptor";
	final String       DATASOURCE_CDH_HIVE           	= "CDHHive";
	
	/**	数据源分类*/
	final String DATASOURCE_CLASSIFY	  = "DatasourceClassify";
	/** 默认数据源分类*/
	final String DATASOURCE_DEFAULTCLASSIFY		  = "common";
}
