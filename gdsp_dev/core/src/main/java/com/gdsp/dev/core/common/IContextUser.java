package com.gdsp.dev.core.common;

/**
 * 上下文用户接口
 * @author paul.yang
 * @version 1.0 2014年11月4日
 * @since 1.6
 */
public interface IContextUser {

    /**
     * 取得用户id
     * @return 用户id
     */
    public String getId();

    /**
     * 取得账户名
     * @return 账户名
     */
    public String getAccount();
}
