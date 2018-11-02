/*
 * Copyright (c)  Business Intelligence Unified Portal. All rights reserved.
 */
package com.gdsp.dev.web.security.shiro;

import javax.annotation.Resource;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.cas.CasRealm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gdsp.dev.core.common.AppContext;
import com.gdsp.dev.web.security.login.AuthInfo;
import com.gdsp.dev.web.security.login.IUserGrantService;

/**
 * 基于cas的权限领域类
 * @author yucl
 * @version 3.1 2018-3-5
 * @since 1.6
 */
public class CasClientRealm extends CasRealm {

	/** 日志对象 */
	private static Logger logger = LoggerFactory.getLogger(CasClientRealm.class);
	/** 授权服务 */
	@Resource
	private IUserGrantService grantService;
	/** 密码服务 */
	@Resource
	private EncodePasswordService encode;

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		AuthenticationInfo authc = super.doGetAuthenticationInfo(token);
		String account = (String) authc.getPrincipals().getPrimaryPrincipal();
		logger.info("authc name:" + account);
		AuthInfo authinfo = grantService.findAuthInfoByAccount(account);
		AppContext.getContext().setContextUser(authinfo);
		return authc;
	}
}
