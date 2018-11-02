package com.gdsp.dev.web.security.login;

import java.util.List;

/**
 * 授权服务接口
 * @author paul.yang
 * @version 1.0 2014年9月24日
 * @since 1.6
 */
public interface IUserGrantService {

    /**
     * 根据用户主键加载授予用户的角色标识
     * @param userId 用户主键
     * @return 角色标识列表
     * @
     */
    List<String> findRolesByUserId(String userId);

    /**
     * 根据用户账号加载验证信息
     * @param account 用户账号
     * @return 验证信息对象
     * @
     */
    AuthInfo findAuthInfoByAccount(String account);

    /**
     * 根据用户主键加载验证信息
     * @param userId 用户主键
     * @return 验证信息对象
     * @paul.yang大神的接口，现发现无人调用
     */
//    AuthInfo findAuthInfoByUserId(String userId);
}
