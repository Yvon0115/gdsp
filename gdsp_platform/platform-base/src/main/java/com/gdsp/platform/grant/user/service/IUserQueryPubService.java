package com.gdsp.platform.grant.user.service;

import java.util.List;
import java.util.Map;

import com.gdsp.dev.core.model.query.Condition;
import com.gdsp.dev.web.security.login.AuthInfo;
import com.gdsp.platform.grant.user.model.UserVO;

public interface IUserQueryPubService {

    /**
     * 根据主键查询用户
     * @return 用户对象
     */
    public UserVO load(String id);

    /**
     * 根据用户名()查询用户的验证信息<br>
     * 此查询对用户私人及敏感信息作相应限制，仅用来验证用户的某些状态
     * @param account 用户名
     * @return AuthInfo
     * @author wqh
     * @since 2017年4月5日
     */
    public AuthInfo queryUserAuthInfoByAccount(String account);
    
    /**
     * 根据用户名查询用户对象
     * @param account 用户名
     * @return 用户对象
     */
    public UserVO queryUserByAccount(String account);

    /**
     * 
     *  通过机构id查询用户列表<br/>
     * 
     * @param list	机构id集合
     * @return
     */
    public List<UserVO> querUsersByOrgIds(List orgIds);

    /**
     * 
     * @Title: queryUserByCondtion 
     * @Description: 根据条件查询用户集合（查询结果不包含当前登录用户，不包含管理员用户）
     * @param userVO	查询用户时，条件封装到VO中。
						平台主要传入参数：
						pk_org		机构ID
						username	用户名(模糊匹配 ‘XX%’)
						account		账号(模糊匹配 ‘XX%’)
						usertype	用户类型(等于1,普通用户)
						sex			性别(0男1女)
     * @param loginUserId	当前登录用户ID
     * @param containLower	是否包含下级机构用户
     * @return List<UserVO>    返回用户结婚
     */
    public List<UserVO> queryUserByCondtion(UserVO userVO, String loginUserId, boolean containLower);

    /**
     * 
     *<功能简述>用户id查询用户信息 <br/>
     *<功能详细描述>
     * @param list
     * @return
     */
    public List<UserVO> querUsersByUserIds(List userIds);
    /**
     * 
     * 查询所有用户(单表查询) <br/>
     * 
     * @param list
     * @return
     */
    public List<UserVO> findAllUsersList();
  /**
   * 
   * @param pk_org 机构id
   * @param userType 用户类型
   * @param userId  用户id
   * @param userName 用户名称
   * @param containLower
   * @return
   * 2016年12月23日
   * win
   */
    public List<UserVO> queryUserByOrgId(String pk_org,Integer userType,String userId,String userName,boolean containLower);
  /**
   * 
   * @param userId
   * @param userType
   * @param userName
   * @return
   * 2016年12月23日
   * win
   */
    public List<UserVO> queryUserInfo(String userId,Integer userType,String userName);
    /**
     * 
     * @param userId
     * @param userName
     * @return
     * 2016年12月23日
     * win
     */
    public List<UserVO> queryUserList(String userId, String userName);
    /**
     * 
     * @param userId
     * @param containSelf
     * @param addCond
     * @return
     * 2016年12月23日
     * win
     */
    public List<UserVO> queryUserListByUserAndCond(String userId, boolean containSelf, String addCond);
    /**
     * 
     * @param userIds
     * @return
     * 2016年12月26日
     * win
     */
    public Map<String, List<UserVO>> queryUserByUserIds(String[] userIds);
    /**
     * 辖区管理使用
     * @param userIds
     * @param orgIds
     * @param userName
     * @return
     * 2016年12月26日
     * win
     */
    public List<UserVO> queryUserForAreaPower(String userIds,String orgIds,String userName,boolean flag);
    /**
     * 通过用户id查询用户信息，可通过用户名称模糊搜索
     * @param userIds
     * @param username  可为空
     * @return
     * 2016年12月27日
     * win
     */
    public  List<UserVO> queryUserListByUserIds(List<String> userIds,String username);
    /**
     * 查询机构下未关联角色的用户
     * @param userIds
     * @param orgIds
     * @param userType
     * @return
     * 2016年12月27日
     * win
     */
    public List<UserVO> queryNoAuthUserListByOrgIds(List<String> userIds,List<String> orgIds,Integer userType,String userName);

    /**
     * 查询机构下未关联角色的用户
     * @param userIds
     * @param orgIds
     * @param userType
     * @param cond
     * @return
     */
    public List<UserVO> queryNoAuthUserListByCondition(List<String> userIds,List<String> orgIds,Integer userType,Condition cond);
    
    /**工作流模块使用，先不做修改
     * 用户预览查询，查询用户及所属用户组名、角色名、机构名
     * @return List<UserVO>
     */
    public List<UserVO> queryUserPreByIds(String[] userIds, String[] userGroupIds, String[] roleIds, String[] orgIds);
    
    /**
     * 根据用户ids和condition查询用户
     * @param userIds
     * @param condition
     * @return
     */
    public List<UserVO> queryUserListByCondition(List<String> userIds,Condition condition);
    
    /**
     * 
     * @param userVO	查询用户时，条件封装到VO中。
						平台主要传入参数：
						pk_org		机构ID
						usertype	用户类型(等于1,普通用户)
     * @param loginUserId 当前登录用户ID
     * @param containLower 是否包含下级机构用户
     * @param cond
     * @return
     */
    public List<UserVO> queryUsersByCondtion(UserVO userVO, String loginUserId, boolean containLower, Condition cond); 
  

}
