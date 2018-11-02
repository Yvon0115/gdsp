package com.gdsp.dev.web.security.login;

/**
 * 登录事件监听接口,在登录后会调用所有实现该接口的服务
 * @author paul.yang
 * @version 1.0 2014年9月24日
 * @since 1.6
 */
public interface LoginListener {

    /**
     * 登陆之后调用此方法
     * @param account 用户名
     */
    public void afterLogin(String account);
}
