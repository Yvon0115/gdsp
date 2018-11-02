package com.gdsp.dev.web.security;

import com.gdsp.dev.core.common.AppContext;
import com.gdsp.dev.web.security.login.IUserGrantService;

/**
 * 安全工具类
 *
 * @author paul.yang
 * @version 1.0 2015-8-19
 * @since 1.6
 */
public final class SecurityHelper {

    /**
     * 授权服务
     */
    private static IUserGrantService grantService = null;

    /**
     * 取得授权服务
     * @return 授权服务
     */
    public static IUserGrantService getGrantService() {
        if (grantService == null)
            grantService = AppContext.lookupBean(IUserGrantService.class);
        return grantService;
    }
}
