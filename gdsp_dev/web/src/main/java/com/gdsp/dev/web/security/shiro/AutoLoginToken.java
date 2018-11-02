package com.gdsp.dev.web.security.shiro;

import org.apache.shiro.authc.HostAuthenticationToken;
import org.apache.shiro.authc.RememberMeAuthenticationToken;

/**
 * 自动登录的token
 * @author paul.yang
 * @version 1.0 2014年9月24日
 * @since 1.6
 */
public class AutoLoginToken implements HostAuthenticationToken, RememberMeAuthenticationToken {

    /**
     * 序列id
     */
    private static final long serialVersionUID = 5380681335867082523L;
    /**
     * 登录账号
     */
    private String            account;
    /**
     * Whether or not 'rememberMe' should be enabled for the corresponding login attempt;
     * default is <code>false</code>
     */
    private boolean           rememberMe       = false;
    /**
     * The location from where the login attempt occurs, or <code>null</code> if not known or explicitly
     * omitted.
     */
    private String            host;

    /**
     * 构造方法
     * @param account    账户
     */
    public AutoLoginToken(String account, String host, boolean rememberMe) {
        this.account = account;
        this.host = host;
        this.rememberMe = rememberMe;
    }

    public String getPrincipal() {
        return account;
    }

    @Override
    public Object getCredentials() {
        return account;
    }

    @Override
    public String getHost() {
        return host;
    }

    @Override
    public boolean isRememberMe() {
        return rememberMe;
    }
}
