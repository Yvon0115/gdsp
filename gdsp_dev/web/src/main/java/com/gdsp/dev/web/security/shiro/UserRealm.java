package com.gdsp.dev.web.security.shiro;

import java.util.Map;

import javax.annotation.Resource;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gdsp.dev.base.exceptions.BusinessException;
import com.gdsp.dev.core.common.AppContext;
import com.gdsp.dev.web.security.login.AuthInfo;
import com.gdsp.dev.web.security.login.IUserGrantService;
import com.gdsp.dev.web.security.login.LogoutListener;

/**
 * 基于shiro的认证授权类实现
 * @author paul.yang
 * @version 1.0 2014年9月24日
 * @since 1.6
 */
public class UserRealm extends AuthorizingRealm {

    /**
     * 日志对象
     */
    private static Logger         logger = LoggerFactory.getLogger(UserRealm.class);
    /**
     * 授权服务
     */
    @Resource
    private IUserGrantService     grantService;
    /**
     * 密码服务
     */
    @Resource
    private EncodePasswordService encode;

    /**
     * 无参构造方法
     */
    public UserRealm() {
        super();
    }

    /**
     * 缓存参数构造方法
     * @param cacheManager 缓存管理骐
     */
    public UserRealm(CacheManager cacheManager) {
        super(cacheManager);
    }

    /* (non-Javadoc)
     * @see org.apache.shiro.realm.AuthorizingRealm#doGetAuthorizationInfo(org.apache.shiro.subject.PrincipalCollection)
     */
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        try {
            authorizationInfo.addRoles(grantService.findRolesByUserId(AppContext.getContext().getContextUserId()));
        } catch (BusinessException e) {
            logger.error(e.getMessage(),e);
        } //授予登陆用户admin的角色
        return authorizationInfo;
    }

    @Override
    public boolean supports(AuthenticationToken token) {
        return super.supports(token);
    }

    /* (non-Javadoc)
         * @see org.apache.shiro.realm.AuthenticatingRealm#doGetAuthenticationInfo(org.apache.shiro.authc.AuthenticationToken)
         */
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) throws AuthenticationException {
        //接收认证信息
        String account = (String) authcToken.getPrincipal();
        //UsernamePasswordToken token = (UsernamePasswordToken)authcToken;
        logger.info("authc name:" + account);
        AuthInfo authInfo = grantService.findAuthInfoByAccount(account);
        if (authInfo == null)
            return null;
        if (authInfo.isLocked())
            throw new LockedAccountException("用户被锁定");
        if (authInfo.isDisabled())
            throw new LockedAccountException("用户被停用");
        //认证通过(用户名,密码,realm name)
        //String tokenString = SecurityHelper.buildSecurityString(authInfo.getId(), account);
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(account, authInfo.getPassword(), getName());
        authenticationInfo.setCredentialsSalt(ByteSource.Util.bytes(encode.getSalt(authInfo.getAccount())));
        return authenticationInfo;
    }

    /* (non-Javadoc)
     * @see org.apache.shiro.realm.CachingRealm#onLogout(org.apache.shiro.subject.PrincipalCollection)
     */
    @Override
    public void onLogout(PrincipalCollection principals) {
        String account = (String) principals.getPrimaryPrincipal();
        if (account == null)
            return;
        AuthInfo authInfo = grantService.findAuthInfoByAccount(account);
        Map<String, LogoutListener> listenerMap = AppContext.getContext().getBeansOfType(LogoutListener.class);
        for (LogoutListener listener : listenerMap.values()) {
            listener.onLogout(authInfo);
        }
        super.onLogout(principals);
    }
}
