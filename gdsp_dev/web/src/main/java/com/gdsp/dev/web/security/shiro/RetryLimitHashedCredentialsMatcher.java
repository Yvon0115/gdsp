package com.gdsp.dev.web.security.shiro;

import java.util.concurrent.atomic.AtomicInteger;

import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheManager;
import org.springframework.beans.factory.annotation.Autowired;

import com.gdsp.dev.core.common.AppConfig;
import com.gdsp.dev.web.security.login.AuthInfo;
import com.gdsp.dev.web.security.login.IUserGrantService;

/**
 * 加密匹配
 *
 * @author paul.yang
 * @version 1.0 2015-9-10
 * @since 1.6
 */
public class RetryLimitHashedCredentialsMatcher extends HashedCredentialsMatcher {
	@Autowired
	private IUserGrantService userGrantService;

    /**
     * 密码缓存
     */
    private Cache<String, AtomicInteger> passwordRetryCache;

    /**
     * 构造方法
     * @param cacheManager 缓存管理器
     */
    public RetryLimitHashedCredentialsMatcher(CacheManager cacheManager) {
        passwordRetryCache = cacheManager.getCache("passwordRetryCache");
    }

    @Override
    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
        String username = (String) token.getPrincipal();
        //retry count + 1
        AtomicInteger retryCount = passwordRetryCache.get(username);
        if (retryCount == null) {
            retryCount = new AtomicInteger(0);
            passwordRetryCache.put(username, retryCount);
        }
        if (AppConfig.getInstance().getInt("retryCount") != 0 &&
                retryCount.incrementAndGet() > AppConfig.getInstance().getInt("retryCount")) {
        	if(retryCount.get() > AppConfig.getInstance().getInt("retryCount") + 1){
        		AuthInfo user = userGrantService.findAuthInfoByAccount(username);
        		boolean lock = user.isLocked();
        		if(lock){
        			throw new ExcessiveAttemptsException();
        		}else{
        			retryCount.set(1);
        		}
        	}else{
                //if retry count > 5 throw
                throw new ExcessiveAttemptsException();
        	}
        }
        boolean matches = super.doCredentialsMatch(token, info);
        if (matches) {
            //clear retry count
            passwordRetryCache.remove(username);
        }
        return matches;
    }
}
