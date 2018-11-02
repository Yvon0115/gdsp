package com.gdsp.dev.web.security.login;

import com.gdsp.dev.base.lang.DDateTime;
import com.gdsp.dev.core.common.IContextUser;

/**
 * 验证信息
 * @author paul.yang
 * @version 1.0 2014年9月24日
 * @since 1.6
 */
public interface AuthInfo extends IContextUser {

    /**
     * 取得密码
     * @return 密码
     */
    public String getPassword();

    /**
     * 用户是否已被锁定
     * @return 是否锁定
     */
    public boolean isLocked();

    /**
     * 密码是否需重置
     * @author wqh
     * @since 2017年4月5日
     * @return 重置状态
     */
    public boolean isNeedReset();

    /**
     * 取得用户类型
     * @return  用户类型
     * @author wqh
     * @since 2017年4月5日
     */
    public int getType(); 

    /**
     * 取得用户上次密码修改时间
     * @return 用户上次密码修改时间
     * @author wqh
     * @since 2017年4月6日
     */
    public DDateTime getLastUpdatePasswordTime();

    /**
     * 用户是否停用
     * @return 是否停用
     */
    public boolean isDisabled();
    
}
