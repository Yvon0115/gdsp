package com.gdsp.dev.web.security.shiro;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.cache.CacheManager;

import com.gdsp.dev.web.security.SecurityHelper;
import com.gdsp.dev.web.security.login.AuthInfo;

/**
 * 自动登录的处理
 * @author paul.yang
 * @version 1.0 2014年9月24日
 * @since 1.6
 */
public class AutoLoginRealm extends UserRealm {

    /**
     * 无参构造方法
     */
    public AutoLoginRealm() {
        super();
    }

    /**
     * 缓存参数构造方法
     * @param cacheManager 缓存管理骐
     */
    public AutoLoginRealm(CacheManager cacheManager) {
        super(cacheManager);
    }

    @Override
    protected void assertCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) throws AuthenticationException {}

    /* (non-Javadoc)
         * @see org.apache.shiro.realm.AuthenticatingRealm#doGetAuthenticationInfo(org.apache.shiro.authc.AuthenticationToken)
         */
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        //接收认证信息
        String account = (String) token.getPrincipal();
        AuthInfo authInfo = SecurityHelper.getGrantService().findAuthInfoByAccount(account);
        if (authInfo == null) {
            throw new IncorrectCredentialsException("登录信息已过期，请重新登录");
        }
        if (authInfo.isLocked())
            throw new LockedAccountException("用户被锁定");
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(account, account, getName());
        return authenticationInfo;
    }

    @Override
    public Class<?> getAuthenticationTokenClass() {
        return AutoLoginToken.class;
    }
}
