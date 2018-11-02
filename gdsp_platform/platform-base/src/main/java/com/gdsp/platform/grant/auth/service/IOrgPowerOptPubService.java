package com.gdsp.platform.grant.auth.service;

import java.util.List;

import com.gdsp.platform.grant.auth.model.OrgPowerVO;

/**
 * 机构权限相关操作公共接口
 * @author jingf
 * @see OrgPowerVO 机构权限对象 : {@code OrgPowerVO}
 * @date 2016年8月2日 下午4:30:22 
 */
public interface IOrgPowerOptPubService {

    /**
     * 添加机构角色关系
     * @param orgPowerVO 机构权限对象
     */
    public void insert(OrgPowerVO orgPowerVO);

    /**
     * 删除机构权限管理
     * @param ids 机构权限id集合
     * @return boolean 是否删除成功
     */
    public boolean deleteOrgPower(String[] ids);

    /**
     * 角色添加机构权限管理
     * @param roleId 角色id
     * @param orgIds 机构id集合
     * @param checkedIds 新增机构id集合
     * @return List<OrgPowerVO> 机构权限列表
     */
    public List<OrgPowerVO> addOrgPowerOnRole(String roleId, String[] orgIds,String[] checkedIds);

    /**
     * 删除角色分配的机构权限
     * @param roleId 角色id
     * @return boolean 是否删除成功
     */
    public boolean deleteRoleOrgPower(String roleId);
    
    /**
     * 删除角色分配的机构权限
     * @param ids 角色id集合
     */
	public void deleteRoleOrgPower(String[] ids);
}
