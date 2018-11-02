package com.gdsp.platform.grant.auth.service;

import java.util.List;

import com.gdsp.dev.core.model.query.Condition;
import com.gdsp.platform.grant.auth.model.UserRoleVO;
import com.gdsp.platform.grant.role.model.RoleVO;
import com.gdsp.platform.grant.user.model.UserVO;

/**
 * 用户关联角色查询服务 - 公共接口
 * @author wwb
 * @version 1.0 2015/6/23
 */
public interface IUserRoleQueryPubService {

    /**
     * 根据用户id查询用户的角色列表
     * @param userID 用户id
     * @return 
     */
    public List<RoleVO> queryRoleListByUserId(String userID);
    
    /**
     * 根据角色id查询该角色下的用户列表<br>
     * @param roleID 角色id
     * @return List<UserVO> 某角色下的用户集合
     */
    public List<UserVO> queryUserListByRoleId(String roleID);
    
    /**
     * 根据用户id查询用户的角色列表
     * @param userID 用户id
     * @return 过滤掉了当天过期但是未删除角色用户关联关系的角色信息
     */
    public List<RoleVO> queryFilteredRoleListByUserId(String userID);

    /** 无调用者 TODO 待确认后删除 */
    /**
     * 根据用户查询拥有的权限
     * @param id
     * @return List<RoleVO>
     */
//    public List<RoleVO> queryRolePorwer(String userId);

    /**
     * 根据角色id查询该角色下的用户列表<br>
     * @param roleID 角色id
     * @param cond 条件
     * @return 某角色下的用户集合
     */
    public List<UserVO> queryUsersByCondition(String roleID, Condition cond);

    /* 无调用者  TODO 待确认后删除 */
    /*
     * 根据角色id查询可以分配给角色的用户列表
     * @param condition 
     * @param roleID 角色id
     * @return 用户列表
     */
//    public List<UserVO> queryUserForRolePower(String roleID, String loginUserID);

    /**
     * 根据角色id查询可以分配给角色的用户列表
     * @param roleId	角色ID	
     * @param loginUserID	用户ID
     * @param username	查询条件“用户名或者账号”
     * @return List<UserVO>    返回用户集合
     */
    public List<UserVO> queryRoleUnauthorizedUsers(String roleId, String loginUserID, String username);

    /**
     * 根据角色id查询可以分配给角色的用户列表
     * @param roleId
     * @param loginUserID
     * @param cond
     * @return
     */
    public List<UserVO> queryRoleUnauthorizedUsersByCondition(String roleId, String loginUserID, Condition cond);
    
    /**
     * 查询所有的用户与角色关联关系  联表查询
     * @return List<UserRoleVO>
     */
    public List<UserRoleVO> findAllUserRoleRelations();
    
    /**
     * 查询所有的用户与角色关系(单表查询)
     */
    public List<UserRoleVO> queryAllUserRoleRelations();
    /**
     * 根据用户id查询用户与角色关系
     * @return
     */
    public List<UserRoleVO> queryUserRoleRelationsByUserId(String userID);
    
    
    
}
