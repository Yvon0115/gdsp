package com.gdsp.platform.grant.auth.service;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.gdsp.dev.core.model.param.Sorter;
import com.gdsp.dev.core.model.query.Condition;
import com.gdsp.platform.func.model.MenuRegisterVO;
import com.gdsp.platform.func.model.PageRegisterVO;
import com.gdsp.platform.grant.auth.model.PagePowerVO;
import com.gdsp.platform.grant.auth.model.PowerMgtVO;
import com.gdsp.platform.grant.role.model.RoleVO;
import com.gdsp.platform.grant.user.model.UserVO;

/**
 * 菜单、按钮权限服务
 * @author wwb
 * @version 1.0 2015/7/21
 */
public interface IPowerMgtService {

    /**
     * 角色增加菜单、按钮权限管理
     * @param roleID 角色
     * @param mgtIDs 菜单、按钮IDs
     */
    public List<PowerMgtVO> addPowerMgtOnRole(String roleID, String mgtIDs);

    /**
     * 删除角色分配的菜单、按钮权限
     * @param roleID 角色
     */
    public boolean deleteRolePowerMgt(String roleID);

    /**
     * 删除页面关联用户/角色
     * @param pageID 页面ID
     */
    public boolean deletePageRoleByPageID(String pageID);

    /**
     * 删除页面关联用户/角色
     * @param ids 关联用户/角色IDs
     */
    public boolean deletePageRole(String... ids);

	/**
	 * 无调用 wqh 2017/04/10 TODO 待删除
	 * @Title: queryMenuListByUser
	 * @Description: 查询用户有管理权限的菜单
	 * @param userID 用户id
	 * @return 参数说明
	 * @return List<MenuRegisterVO> 返回菜单list
	 * 	 */
//    public List<MenuRegisterVO> queryMenuListByUser(String userID);

    /**
     * 查询指定用户,指定菜单的有权限的下级菜单
     * @param userId 用户主键
     * @param menuId 菜单id
     * @param condition 过滤条件
     * @param pageable 分页请求
     * @return 分页结果
     */
    public Page<MenuRegisterVO> queryNextLevelMenuPageByUser(String userId, String menuId, Condition condition, Pageable pageable);

	/******************************************************
	 * 已移至公共接口  IPowerMgtQueryPubService
	 * 原因：权限拆分
	 * wqh 2016/12/26
	 *****************************************************/
    // /**
     // * 查询用户有管理权限下级菜单
     // * @param userID 用户
     // * @return List
     // */
    // public Map<String, List<MenuRegisterVO>> queryLowerLevelMenuMapByUser(String userID, String menuId);

	
	/******************************************************
	 * 已移至公共接口  IPowerMgtQueryPubService
	 * 原因： 权限拆分
	 * wqh 2016/12/26
	 *****************************************************/
    // /***
    // * @Description: 查询用户有管理权限的菜单、按钮
    // * @param userID 用户id
    // * @param isAuth 标记位，仅查看用false
    // * @return    参数说明
    // * @return Map    返回菜单的map
    // *      // */
    // public Map<String, List<MenuRegisterVO>> queryMenuMapByUser(String userID, boolean isAuth);

	
	
	/******************************************************
	 * 已移至公共接口  IPowerMgtQueryPubService
	 * 原因： 权限拆分
	 * wqh 2016/12/26
	 *****************************************************/
    // /**
     // * 查询用户有管理权限的页面菜单
     // * @param userID 用户
     // * @return Map
     // */
    // public Map<String, List<MenuRegisterVO>> queryPageMenuMapByUser(String userID);

    // /**
     // * 查询用户有管理权限的分组菜单
     // * @param userID 用户
     // * @return Map
     // */
    // public Map<String, List<MenuRegisterVO>> queryLevelMenuMapByUser(String userID);

	/******************************************************
	 * 已移至公共接口  IPowerMgtQueryPubService
	 * 原因： 权限拆分
	 * wqh 2016/12/26
	 *****************************************************/
    // /**
     // * 通过角色ID查询角色关联
     // * @param roleID
     // * @param orgCon
     // * @param pageable
     // * @param sort
     // * @return
     // */
    // public List<PowerMgtVO> queryPowerMgtByRoleId(String roleID, Condition orgCon, Sorter sort);

    // /**
     // * 查询用户有管理权限的页面
     // * @param userID 用户
     // * @return List
     // */
    // public Map queryPageListByUserForPower(String userID);
    
	
	/******************************************************
	 * 已移至公共接口  IPowerMgtQueryPubService
	 * 原因： 权限拆分
	 * wqh 2016/12/26
	 *****************************************************/
    // /**
     // * 根据页面id查询用户列表
     // * @param pageID 页面id
     // * @param page 分页请求
     // * @return 分页用户列表
     // */
    // public Page<PagePowerVO> queryPageUserByPageId(String pageID, String userID, Condition cond, Pageable page);

	
	/******************************************************
	 * 已移至公共接口  IPowerMgtQueryPubService
	 * 原因： 权限拆分
	 * wqh 2016/12/26
	 *****************************************************/
    // /**
     // * 根据页面id查询角色列表
     // * @param pageID 页面id
     // * @param page 分页请求
     // * @return 分页角色列表
     // */
    // public Page<PagePowerVO> queryPageRoleByPageId(String pageID, String userID, Condition cond, Pageable page);

    /**
     * 根据角色ID查询角色有权限管理的菜单Map
     * @param roleID
     * @param condition
     * @param sort
     * @return 菜单Map
     */
    public Map queryPowerMgtMapByRoleId(String roleID);

	
	
	/******************************************************
	 * 已移至公共接口  IPowerMgtOptPubService
	 * 原因： 权限拆分
	 * wqh 2016/12/26
	 *****************************************************/
    // /**
     // * 设置【角色拥有的菜单权限】在【当前登陆用户拥有的菜单权限】中的选中状态  isChecked="Y"  用于回显
     // * @param menuMap
     // * @param roleMenuMap
     // */
    // public void setMenuIsChecked(Map menuMap, Map roleMenuMap);

	
	 /******************************************************
	 * 已移至公共接口  IPowerMgtOptPubService
	 * 原因： 权限拆分
	 * wqh 2016/12/26
	 *****************************************************/
    // /**
     // * 根据页面id查询可以分配页面的角色列表
     // * @param pageID 页面id
     // * @param loginUserID 登录用户
     // * @param page 分页请求
     // * @return 分页角色列表
     // */
    // public Page<RoleVO> queryRoleForPagePower(String pageID, Condition condition, String loginUserID, Pageable page);

	
	/******************************************************
	 * 已移至公共接口  IPowerMgtOptPubService
	 * 原因： 权限拆分
	 * wqh 2016/12/26
	 *****************************************************/
    // /**
     // * 根据页面id查询可以分配页面的用户列表
     // * @param pageID 页面id
     // * @param loginUserID 登录用户
     // * @param page 分页请求
     // * @return 分页用户列表
     // */
    // public Page<UserVO> queryUserForPagePower(String pageID, Condition condition, String loginUserID, Pageable page);

    /**
     * 页面增加关联用户/角色
     * @param pageID 页面id
     * @param roleIDs 角色IDs
     */
    public void addUserRoleOnPage(String pageID, int objtype, String... roleIDs);


	/******************************************************
   	 * 已移至公共接口  IPowerMgtQueryPubService
   	 * 原因： 权限拆分
   	 * wqh 2016/12/26
   	 *****************************************************/
    // /**
     // * 递归查询子菜单ID所有的父级菜单
     // * @param menuIdList 子菜单ID
     // * @return Map 
     // */
    // public Map queryMenuRecursive(List<String> menuIdList);

    /**
     * 是否拥有页面权限
     * @param pageID 页面id
     * @param userID 用户ID
     */
    public boolean isHavePowerForPage(String pageID, String userID);

	/**
	 * @Title: insertMenuPower
	 * (增加菜单权限)
	 * @param powerMgtVO 参数说明
	 * @return void
	 * 	 */
    public void insertMenuPower(PowerMgtVO powerMgtVO);

	
	/******************************************************
	 * 已移至公共接口  IPowerMgtOptPubService
	 * 原因： 权限拆分
	 * wqh 2016/12/26
	 *****************************************************/
    // /**
     // * 给用户或角色添加页面权限
     // * @param userMenuList 当前用户页面列表
     // * @param userId 授权用户
     // * @param menuIds 关联menu字符串
     // * @return
     // */
    // public Object addPowerMgtOnUser(List<MenuRegisterVO> userMenuList, String userId, String menuIds,String checkedMenuIds);

	
	
	/******************************************************
   	 * 已移至公共接口  IPowerMgtQueryPubService
   	 * 原因： 权限拆分
   	 * wqh 2016/12/26
   	 *****************************************************/
    // /**
     // * 根据菜单Url查询是否拥有菜单权限
     // * @param pageID 页面id
     // * @param userID 用户ID
     // */
    // public boolean isHavePowerForMenuByUrl(String menuUrl, String userID);

    // /**
     // * 查询用户有权限的所有菜单
     // * @param userID
     // * @return
     // */
    // public Map<String, List<MenuRegisterVO>> queryAllLevelMenuListByUser(String userID);

	
	
	/******************************************************
   	 * 已移至公共接口  IPowerMgtQueryPubService
   	 * 原因： 权限拆分
   	 * wqh 2016/12/26
   	 *****************************************************/
//    /**
//     * <功能简述> 查询菜单是否关联到了角色<br/>
//     * 2016/12/23移除此方法  原因:权限拆分
//     * @param id
//     * @return
//     */
//    public List<PowerMgtVO> isConnectToRole(String resourceId);

    // /***
     // * @Title: queryMenuListByUserVo
     // * @Description: 查询用户有管理权限的菜单
     // * @param userVO 用户对象
     // * @return    参数说明
     // * @return List<MenuRegisterVO>  返回菜单list  
     // *       // */
    // public List<MenuRegisterVO> queryMenuListByUserVo(UserVO userVO);
    
    
}
