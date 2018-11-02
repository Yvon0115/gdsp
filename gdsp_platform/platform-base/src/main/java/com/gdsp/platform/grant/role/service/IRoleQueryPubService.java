package com.gdsp.platform.grant.role.service;

import java.util.List;
import java.util.Map;

import com.gdsp.platform.grant.role.model.RoleVO;

/**
 * 角色查询 - 公共接口
 * @author wqh
 * @since 2016年12月27日 上午9:36:07
 */
public interface IRoleQueryPubService {

    /**
     * 根据主键查询角色
     * @return 角色对象
     */
    public RoleVO load(String id);

    /**
     * 通过机构ID集合查询角色列表
     * @param list	机构ID集合
     * @return List<RoleVO>    返回角色List集合
     */
    public List<RoleVO> queryRoleListByOrgIds(List orgIds);
    
    /**
     * 根据机构ID以及其他条件查询角色集合
     * @param cond	条件
     * @param isContainChild	是否包含下级角色
     * @return List<RoleVO>    返回角色List集合
     */
	public List<RoleVO> queryRoleListByOrgId(RoleVO cond, boolean isContainChild);
	
	/**
	 * 查询单个机构下角色信息
	 * @param OrgId
	 * @return 只返回角色名称，角色id
	 */
	public List<Map<String,String>> queryRoleList(String OrgId);
	
	/**
	 * 查询所有角色
	 * @param list
	 * @return
	 */
	public List<RoleVO> findAllRoleList();
	
	/**
	 * 模糊查询 - 角色列表<br>
	 * 所有条件均以‘like’ 匹配
	 * @author wqh
	 * @since 2016/12/22
	 * @param roleName  角色名称（可为空）
	 * @return List 角色列表
	 */
	public List<RoleVO> fuzzySearchRoleListByCondition(String roleName);
	
	/**
	 * 根据角色ID集合查询角色列表
	 * @author wqh
	 * @since 2016/12/22
	 * @param roleIds  角色ID集合
	 * @return 角色列表
	 */
	public List<RoleVO> queryRoleListByRoleIds(List roleIds);
	
	
}
