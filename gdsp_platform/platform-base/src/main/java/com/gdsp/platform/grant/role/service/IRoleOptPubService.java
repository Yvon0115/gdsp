package com.gdsp.platform.grant.role.service;

import com.gdsp.platform.grant.role.model.RoleVO;
/**
 * 角色相关操作公共接口
 * @author 
 * @see RoleVO 角色对象 : {@code RoleVO}
 * @date 2017年12月6日 下午2:45:01 
 */
public interface IRoleOptPubService {

	/**
	 * 增加角色
	 * @param roleVOvo 角色对象
	 */
	public void insert(RoleVO roleVOvo);

	/**
	 * 修改角色
	 * @param roleVOvo 角色对象
	 */
    public void update(RoleVO vo);

    /**
     * 根据角色id删除角色
     * @param ids 角色id集合
     * @return boolean 是否删除成功 
     */
    public boolean deleteRole(String[] ids);
    
    /**
     * 设置角色时效控制开启状态
     * @param roleId 角色id
     * @param agingLimit 时效角色标识
     * @return boolean 是否开启成功 
     */
    public boolean setRoleAgingStatus(String roleId, String agingLimit);
    
}
