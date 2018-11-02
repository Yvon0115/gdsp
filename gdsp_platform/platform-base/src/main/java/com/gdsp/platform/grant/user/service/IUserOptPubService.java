package com.gdsp.platform.grant.user.service;

import com.gdsp.platform.grant.user.model.UserVO;
/**
 * 用户相关操作公共接口
 * @author 
 * @see UserVO 用户信息对象 : {@code UserVO}
 * @since 2017年12月6日 下午3:18:48
 */
public interface IUserOptPubService {

	/**
	 * 添加新用户
	 * @param userVO 用户信息对象
	 */
    public void insert(UserVO userVO);

    /**
     * 修改用户
     * @param userVO 用户信息对象
     */
    public void update(UserVO userVO);

    /**
     * 根据用户id删除用户
     * <p>
     * <b>当用户存在关联业务时将不能删除该用户</b>
     * @param id 用户id
     * @return String 用户存在的具体关联业务<br>
     * 用户存在的关联业务有 : 角色、菜单、机构、用户组
     */
    public String deleteUser(String id);

    /**
     * 用户机构变更
     * @param userId 用户id
     * @param orgId 变更后机构id
     */
    public void transOrg(String userId, String orgId);

    /**
     * 用户密码修改
     * @param userVO 用户信息对象
     */
    public void resetUserPasssword(UserVO userVO);

    /**
    * 重置个人信息
    * @param userVO 用户信息对象
    */
    public void resetPersonalInf(UserVO userVO);

    /**
    * 用户密码修改
    * @param userVO 用户信息对象
    */
	public void updateUserPasssword(UserVO userVO);

	/**
	 * spring管理调用Ehcache清除登录缓存
	 * @param username 用户名
	 */
    public void clearCache(String username);
    
    /**
     * 停用用户
     * @param ids 用户id集合
     * @return boolean 是否停用成功
     */
    public boolean disable(String[] ids);
    
    /**
     * 启用用户
     * @param ids 用户id集合
     * @return boolean 是否启用成功
     */
    public boolean enable(String[] ids);
   
   /**
    * 锁定用户   <b>区别于停用用户</b>
    * @param ids 用户id集合
    * @return boolean 是否锁定成功
    */
   public boolean lockUser(String[] ids);
   
   /**
    * 解锁用户  <b>区别于启用用户</b>
    * @param ids 用户id集合
    * @return boolean 是否解锁成功
    */
   public boolean unlockUser(String[] ids);
}
