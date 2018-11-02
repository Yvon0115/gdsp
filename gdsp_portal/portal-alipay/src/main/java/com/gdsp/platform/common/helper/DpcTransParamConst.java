package com.gdsp.platform.common.helper;

/**
 * @author yucl
 * @version 0.0.1 2018/5/17 10:24
 * @since 1.6
 */
public interface DpcTransParamConst {

    /**
     * 阿里单点登录客户端key
     **/
    String DPC_CUSTOMER_KEY = "dpc_customer_id";

    /**
     * 阿里单点登录tokenkey
     */
    String LOGIN_TOKEN_KEY = "dpc_login_token";
    /**
	 * alisis查询时的method
	 */
	String ALISIS_METHOD_QUERY = "alisis.method.query";
	
	/**
	 * alisis授权时的method
	 */
	String ALISIS_METHOD_GRANT = "alisis.method.grant";
	
	/**
	 * alisis去掉权限时的method
	 */
	String ALISIS_METHOD_REVOKE = "alisis.method.revoke";
	/**
	 * alisis的Client请求时的endpoint
	 */
	String ALISIS_ENDPOINT = "alisis.endpoint";
	
	/**
	 * alisis的Client请求时的accessId
	 */
	String ALISIS_ACCESSID = "alisis.accessId";
	
	/**
	 * alisis的Client请求时的accessSecret 
	 */
	String ALISIS_ACCESSSECRET = "alisis.accessSecret";
	
	/**
	 * 租户管理员id
	 */
	String ALISIS_OPERATORID = "operatorId";
	
	/**
	 * 租户id
	 */
	String ALISIS_TENANTID = "tenantId";
	
	/**
	 * 空间id
	 */
	String ALISIS_NAMESPACEID = "namespaceId";
	
	/**
	 * 查询用户id
	 */
	String ALISIS_TARGETUSERID = "targetUserId";
	
	/**
	 * 查询对象类型
	 */
	String ALISIS_TARGETTYPE = "targetType";
	
	/**
	 * 分页数
	 */
	String ALISIS_PAGENUM = "pageNum";
	
	/**
	 * 页大小
	 */
	String ALISIS_PAGESIZE = "pageSize";
	
	/**
	 * 拼接报表查询url时候，需要传递的报表资源id的key值
	 */
	String ALISIS_REPORT_ID = "reportId";
	
}
