package com.gdsp.platform.grant.auth.service;

import java.util.List;

import com.gdsp.platform.grant.auth.model.UserRoleVO;

/**
 * 用户关联角色相关操作公共接口
 * @author wwb
 * @see UserRoleVO 用户角色关联关系对象 : {@code UserRoleVO}
 * @version 1.0 2015/6/23
 */
public interface IUserRoleOptPubService {

	/**
	 * 给角色增加用户
	 * @param roleId 角色id
	 * @param userIds 用户Id集合
	 * @param agingEndDate 时效截止日期
	 * @return {@code List<UserRoleVO>} 用户角色关联关系列表
	 */
    public List<UserRoleVO> addUserRoleOnRole(String roleId, String[] userIds,String agingEndDate);

    /**
     * 给用户增加角色
     * @param userIs 用户Id
     * @param roleIds 角色id集合
     * @param isAgingRole 是否时效角色
     * @return {@code List<UserRoleVO>} 用户角色关联关系列表
     */
    public List<UserRoleVO> addUserRoleOnUser(String userId, String[] roleIds,boolean isAgingRole);

    /**
     * 删除用户关联的角色
     * @param loginUserId 
     * @param ids 角色id集合
     * @return boolean 是否成功删除
     */
    public boolean deleteUserRole(String loginUserId, String[] ids);

    /**
     * 根据角色ID删除关联的用户
     * @param roleId 角色Id
     * @param ids 用户Id集合
     * @return boolean 删除是否成功
     */
    public boolean deleteUserRoles(String roleId, String[] ids);
    
    /**
     * 根据ID删除用户角色关联关系
     * @param ids 关联表Id集合
     * @return boolean 是否成功删除
     */
    public boolean deleteUserRoleRelations(String[] ids);
    
    /**
     * 批量更改角色下关联的用户时效
     * @author wqh 2016/12/21
     * @param roleId 角色Id
     * @param userIds 用户Id集合
     * @param agingEndDate 权限截止日期
     * @param dateType 实体类型 1：用户 ；2：角色
     * @return boolean 是否更改成功
     */
    public boolean modifyUserAgingOnRole(String roleId, String[] userIds,String agingEndDate,String dateType);

    /**
     * 批量更改用户下关联的角色时效  
     * @author wqh 2016/12/21
     * @param userId 用户id
     * @param roleIds 角色id集合
     * @param aging_enddate 权限截止日期
     * @param dateType 实体类型 1：用户 ；2：角色
     * @return boolean 是否更改成功
     */
    public boolean modifyRoleAgingOnUser(String userId, String[] roleIds, String aging_enddate,String dateType);
    
    /**
     * 更新权限时效到期提醒状态(是否已提醒)
     * @author wqh 2016/12/21
     * @param ids  关联表IDs
     * @param isPrompted  是否已提醒
     */
    public void updateIsPromptedState(String[] ids);
    
    /** TODO 实现里联表查 待更改
     * 删除过期角色用户关联关系(执行后台任务)
     * @author wqh 2016/12/21
     */
    public void deleteOverdueRelations();
    
}
