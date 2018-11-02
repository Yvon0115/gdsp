package com.gdsp.dev.web.security.login;

import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gdsp.dev.base.utils.web.URLUtils;
import com.gdsp.dev.core.common.AppContext;
import com.gdsp.dev.web.security.shiro.AutoLoginToken;
import com.gdsp.dev.web.utils.WebOperationConst;

/**
 * 登录验证过滤
 * @author paul.yang
 * @version 1.0 2014年11月11日
 * @since 1.6
 */
public class LoginAuthenticationFilter extends FormAuthenticationFilter {

    /**登录日志*/
    private static final Logger loginInfoLogger = LoggerFactory.getLogger("loginInfoLogger");
    private static Logger       logger          = LoggerFactory.getLogger(LoginAuthenticationFilter.class);
    /**
     * 日志记录异常写入文件
     */
    private static Logger       logExFile       = LoggerFactory.getLogger("logExcepFile");
    /**
     * 是否通过url中带有用户名和密码进行隐式登录
     */
    private boolean             isUrlUserParamImplicitLogin;
    /**
     * 是否通过url中带有用户名和密码进行隐式登录失败时跳转的url
     */
    private String              implicitLoginFailureToUrl;

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
        if (request.getAttribute(getFailureKeyAttribute()) != null) {
            return true;
        }
        return super.onAccessDenied(request, response, mappedValue);
    }

    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        boolean isAllowed = super.isAccessAllowed(request, response, mappedValue);
        if (isAllowed)
            return true;
        Subject subject = getSubject(request, response);
        //如果 isAuthenticated 为 false 证明不是登录过的，同时 isRememberd 为true 证明是没登陆直接通过记住我功能进来的
        if (!subject.isAuthenticated()) {
            SecurityUtils.getSubject().logout();
            /*
             * 通过url带有用户名密码的url直接登录处理
             */
            if (isUrlUserParamImplicitLogin() && StringUtils.isNotEmpty(getUsername(request)) && StringUtils.isNotEmpty(getPassword(request))) {
                try {
                    if (!executeLogin(request, response) && StringUtils.isNotEmpty(getImplicitLoginFailureToUrl())) {
                        WebUtils.issueRedirect(request, response, getImplicitLoginFailureToUrl());
                    }
                } catch (Exception e) {
                    logger.debug(e.getMessage(), e);
                    return false;
                }
            } else if (subject.isRemembered()) {
                subject.login(new AutoLoginToken((String) subject.getPrincipal(), getHost(request), true));
            }
        }
        return super.isAccessAllowed(request, response, mappedValue);
    }

    /* (non-Javadoc)
    
     * @see org.apache.shiro.web.filter.authc.FormAuthenticationFilter#onLoginSuccess(org.apache.shiro.authc.AuthenticationToken, org.apache.shiro.subject.Subject, javax.servlet.ServletRequest, javax.servlet.ServletResponse)
     */
    protected boolean onLoginSuccess(AuthenticationToken token, Subject subject,
            ServletRequest request, ServletResponse response) throws Exception {
        String account = (String) subject.getPrincipal();
        if (account == null)
            return super.onLoginSuccess(token, subject, request, response);
        Map<String, LoginListener> loginEventMap = AppContext.getContext().getBeansOfType(LoginListener.class);
        for (LoginListener event : loginEventMap.values()) {
            event.afterLogin(account);
        }
        HttpServletRequest req = (HttpServletRequest) request;
        String ipAddr = URLUtils.getIPAddr(req);
        //登录日志不再记录MAC地址   lijun  2017/4/10
        /*
        String macAddr = "";
        try {
        	macAddr = URLUtils.getLocalMac(URLUtils.getIPAddr(req));
        } catch (Exception e1) {
        	logExFile.info(e1.getMessage()+"物理地址获取异常");
        	logger.debug(e1.getMessage(),e1);
        	//成功登录状态为Y
            String loginStatus = WebOperationConst.LOGIN_SUCCESS;
            try{
            	loginInfoLogger.info("loginInfoLogger", account, ipAddr,macAddr,loginStatus);
            	return super.onLoginSuccess(token, subject, request, response);
            }catch(Exception e){
            	logExFile.info(e.getMessage()+"登录日志写入异常");
            	logger.debug(e.getMessage(),e);
            	return super.onLoginSuccess(token, subject, request, response);
            }
        }
        */
        String loginStatus = WebOperationConst.LOGIN_SUCCESS;
        try {
            //loginInfoLogger.info("loginInfoLogger", account, macAddr, ipAddr, loginStatus);
            loginInfoLogger.info("loginInfoLogger", account, ipAddr, loginStatus);
        } catch (Exception e) {
            logExFile.info(e.getMessage() + "登录日志写入异常");
            logger.debug(e.getMessage(), e);
            return super.onLoginSuccess(token, subject, request, response);
        }
        return super.onLoginSuccess(token, subject, request, response);
    }

    protected boolean onLoginFailure(AuthenticationToken token, AuthenticationException e,
            ServletRequest request, ServletResponse response) {
        String account = getUsername(request);
        HttpServletRequest req = (HttpServletRequest) request;
        String ipAddr = URLUtils.getIPAddr(req);
        /*
        String macAddr = "";
        try {
            macAddr = URLUtils.getLocalMac(URLUtils.getIPAddr(req));
        } catch (Exception e1) {
            logExFile.info(e1.getMessage() + "物理地址获取异常");
            logger.debug(e1.getMessage(), e1);
            //失败登录状态为N
            String loginStatus = WebOperationConst.LOGIN_FAILURE;
            try {
                loginInfoLogger.info("loginInfoLogger", account, ipAddr, macAddr, loginStatus);
                return super.onLoginFailure(token, e, request, response);
            } catch (Exception exp) {
                logExFile.info(exp.getMessage() + "登录日志写入异常");
                logger.debug(exp.getMessage(), exp);
                return super.onLoginFailure(token, e, request, response);
            }
        }
        */
        //失败登录状态为N
        String loginStatus = WebOperationConst.LOGIN_FAILURE;
        try {
            //loginInfoLogger.info("loginInfoLogger", account, ipAddr, macAddr, loginStatus);
            loginInfoLogger.info("loginInfoLogger", account, ipAddr, loginStatus);
        } catch (Exception ex) {
            logExFile.info(ex.getMessage() + "登录日志写入异常");
            logger.debug(ex.getMessage(), ex);
            return super.onLoginFailure(token, e, request, response);
        }
        return super.onLoginFailure(token, e, request, response);
    }

    public boolean isUrlUserParamImplicitLogin() {
        return isUrlUserParamImplicitLogin;
    }

    public void setIsUrlUserParamImplicitLogin(boolean isUserPasswordTokenLogin) {
        this.isUrlUserParamImplicitLogin = isUserPasswordTokenLogin;
    }

    public String getImplicitLoginFailureToUrl() {
        return implicitLoginFailureToUrl;
    }

    public void setImplicitLoginFailureToUrl(String implicitLoginFailureToUrl) {
        this.implicitLoginFailureToUrl = implicitLoginFailureToUrl;
    }
}
