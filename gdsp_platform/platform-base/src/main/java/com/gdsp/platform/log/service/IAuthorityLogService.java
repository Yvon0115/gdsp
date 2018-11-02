package com.gdsp.platform.log.service;

import java.util.List;

import com.gdsp.platform.grant.role.model.RoleVO;
import com.gdsp.platform.grant.user.model.UserVO;
import com.gdsp.platform.systools.datadic.model.RoleAuthorityVO;

public interface IAuthorityLogService {
	
	/**
     * 查询用户角色的list
     * @return
     */
	public List<UserVO> getUserRoleList();
	
	/**
     * 查询角色功能的list
     * @return
     */
    public List<RoleVO> getRoleFuncList();
    
    /**
     * 查询角色数据权限的list
     * @return
     */
    public List<RoleAuthorityVO> getRoleDataLists();
    
    /**
     * 条件查询用户角色list
     */
    public List<UserVO> queryUserRoleList(String uaoParam,String rname);
    
    /**
     * 条件查询角色功能list
     */
    public List<RoleVO> queryRoleFuncList(String rolename,String funcname);
    
    /**
     * 条件查询用户功能list
     */
    public List<UserVO> queryUserFuncList(String uaoParameter,String functionname);
    
    /**
     * 条件查询角色数据权限list
     */
    public List<RoleAuthorityVO> queryRoleDataList(String rlname,String datalimited);
    
    /**
     * 条件查询用户数据权限list
     */
    public List<RoleAuthorityVO> queryUserDataList(String uaoParam,String dataauthority);
}
