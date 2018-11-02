package com.gdsp.platform.grant.auth.service;

import java.util.List;
import java.util.Map;

import com.gdsp.platform.func.model.MenuRegisterVO;
import com.gdsp.platform.grant.auth.model.PowerMgtVO;

/**
 * 菜单、按钮权限相关操作公共接口
 * @author jingf
 * @see PowerMgtVO 权限分配对象:{@code PowerMgtVO}
 * @date 2016年8月2日 下午5:24:01 
 */
public interface IPowerMgtOptPubService {

	/**
	 * 添加菜单权限
	 * @param powerMgtVO 权限分配对象
	 */
    public void insertMenuPower(PowerMgtVO powerMgtVO);

    /**
     * 删除角色分配的菜单、按钮权限
     * @param roleIds 角色id集合
     * @return boolean 是否删除成功
     */
    public boolean deleteRolePowerMgt(String[] roleIds);

    /**
     * 删除已发布页面
     * @param pageId 页面ID
     * @return boolean 是否删除成功
     */
    public boolean deletePageRoleByPageID(String pageId);

    /**
     * 删除页面关联的角色
     * @param ids 页面关联角色关系ID (主键)
     * @return boolean 是否删除成功
     */
    public boolean deletePageRole(String[] ids);
    
    /**
     * 添加页面关联角色
     * @param pageId 页面id
     * @param objtype 关联实体类型<br>
     * <i>实体类型 1：用户、2：角色</i>    
     * @param roleIds 角色Id集合
     */
    public void addUserRoleOnPage(String pageId, int objtype, String[] roleIds);
 
    /**
     * 删除角色关联页面信息
     * @param roleIds 角色Id集合
     */
	public void deletePagePowerByRoleIds(String[] roleIds);
	
	/**
	 * 给角色添加菜单权限<br>
	 * 菜单包括功能菜单和页面菜单<br>
	 * 原位置： IPowerMgtService
	 * @author wqh
	 * @since 2016年12月23日
     * @param userMenuList 当前登录用户有权限的菜单列表
     * @param roleID 当前操作的角色的ID
     * @param selectedMenuIds 页面上选中的菜单IDs
	 * @param exsitingMenuIds 选中的角色现有的菜单IDs
	 * @see MenuRegisterVO 菜单注册 : {@code MenuRegisterVO}
	 */
    public Object addPowerMgtOnRole(List<MenuRegisterVO> userMenuList, String roleID, String selectedMenuIds,String exsitingMenuIds);
    
    /**
     * 设置【角色拥有的菜单权限】在【当前登陆用户拥有的菜单权限】中的选中状态  isChecked="Y"  用于回显
     * 原位置： IPowerMgtService
     * @author wqh
     * @since 2016年12月23日
     * @param menuMap 当前登陆用户有权限的菜单map
     * @param roleMenuMap 角色有管理权限的菜单map
     */
     public void setMenuIsChecked(Map menuMap, Map roleMenuMap);
    
}
