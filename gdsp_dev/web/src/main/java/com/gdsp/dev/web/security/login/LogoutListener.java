package com.gdsp.dev.web.security.login;

/**
 * 用户退出监听接口，用户退出时会调用onLogout方法
 * @author paul.yang
 * @version 1.0 2014年9月24日
 * @since 1.6
 */
public interface LogoutListener {

    /**
     * 用户退出登陆后调用此方法
     * @param account 用户名
     */
    public void onLogout(AuthInfo account);
}
