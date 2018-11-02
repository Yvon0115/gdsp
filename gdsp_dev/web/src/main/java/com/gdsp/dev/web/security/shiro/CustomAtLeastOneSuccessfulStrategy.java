package com.gdsp.dev.web.security.shiro;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.pam.AtLeastOneSuccessfulStrategy;
import org.apache.shiro.realm.Realm;

/**
 * 扩展的验证成功策略,原有策略在多个realm时会吃掉异常信息
 *
 * @author paul.yang
 * @version 1.0 2015-9-16
 * @since 1.6
 */
public class CustomAtLeastOneSuccessfulStrategy extends AtLeastOneSuccessfulStrategy {

    @Override
    public AuthenticationInfo afterAttempt(Realm realm, AuthenticationToken token, AuthenticationInfo singleRealmInfo, AuthenticationInfo aggregateInfo, Throwable t) throws AuthenticationException {
        if (t != null) {
            if (t instanceof RuntimeException)
                throw (RuntimeException) t;
        }
        return super.afterAttempt(realm, token, singleRealmInfo, aggregateInfo, t);
    }

    @Override
    public AuthenticationInfo afterAllAttempts(AuthenticationToken token, AuthenticationInfo aggregate) throws AuthenticationException {
        if (aggregate == null)
            throw new UnknownAccountException("用户不存在");
        return super.afterAllAttempts(token, aggregate);
    }
}
