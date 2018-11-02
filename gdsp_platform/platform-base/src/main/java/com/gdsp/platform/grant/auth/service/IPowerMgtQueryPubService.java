package com.gdsp.platform.grant.auth.service;

import java.util.List;
import java.util.Map;

import com.gdsp.platform.func.model.MenuRegisterVO;
import com.gdsp.platform.func.model.PageRegisterVO;
import com.gdsp.platform.grant.auth.model.PagePowerVO;
import com.gdsp.platform.grant.auth.model.PowerMgtVO;
import com.gdsp.platform.grant.auth.model.UserDefaultPageVO;
import com.gdsp.platform.grant.role.model.RoleVO;
import com.gdsp.platform.grant.user.model.UserVO;

/**
 * 
 * @ClassName: IPowerMgtPubService 
 * @Description: 菜单、按钮权限服务
 * @author jingf
 * @date 2016年8月2日 下午5:24:01 
 *
 */
public interface IPowerMgtQueryPubService {

    /***
    * 查询用户有管理权限的菜单
    * @param userID 用户id
    * @return 功能菜单list  
     */
    public List<MenuRegisterVO> queryMenuListByUser(String userID);

    /**
     * 查询用户有权限的页面菜单
     * 
     * @param userID 用户
     * @return 页面菜单列表
     * @see queryPageListByRole 原接口已删除
     */
    public List<MenuRegisterVO> queryPageMenuListByUser(String userID);

    /**
     * 查询用户有权限的一级菜单
     * @param userID 用户
     * @return List
     */
    public List<MenuRegisterVO> queryFirstLevelMenuListByUser(String userID);

    /**
     * 根据角色id查询可以分配给角色的菜单、按钮列表
     * @param userID 用户id
     * @return 菜单列表
     */
    public List<MenuRegisterVO> queryMenuForRolePower(String roleID, String loginUserID);

    /**
     * 查询菜单下面的页面
     * @param userID 用户
     * @param menuID 菜单
     * @return List
     */
    public List<PageRegisterVO> queryPageListForMenu(String userID, String menuID);
    
    
    /**
     * 查询用户的默认首页列表  wqh 2016/11/22
     * @param userID
     * @return
     */
    public List<UserDefaultPageVO> findDefaultPageByUser(String userID);
    
    /**
     * 查询所有角色，菜单资源关系
     */
    public List<PowerMgtVO> queryAllRoleMenuList();
    
    /**
     * 查询所有角色，页面资源关系
     */
    public List<PowerMgtVO> queryAllRolePageList();
    
    /**
     * 查询菜单是否关联到了角色<br/>
     * 原位置： IPowerMgtService
     * @author wqh
     * @since 2016年12月23日
     * @param id
     * @return
     */
    public List<PowerMgtVO> isConnectToRole(String resourceId);

    /**
     * 查询用户有管理权限下级菜单<br>
     * 原位置： IPowerMgtService
     * @author wqh
     * @since 2016年12月23日
     * @param userID 用户
     * @param menuId
     * @return Map
     */
    public Map<String, List<MenuRegisterVO>> queryLowerLevelMenuMapByUser(String userID, String menuId);
    
    /**
     * 查询用户有权限的所有菜单
     * 原位置： IPowerMgtService
     * @author wqh
     * @since 2016年12月23日
     * @param userID
     * @return
     */
    public Map<String, List<MenuRegisterVO>> queryAllLevelMenuListByUser(String userID);
    
    /**
     * 查询用户有管理权限的分组菜单
     * 原位置： IPowerMgtService
     * @author wqh
     * @since 2016年12月23日
     * @param userID 用户
     * @return Map
     */
    public Map<String, List<MenuRegisterVO>> queryLevelMenuMapByUser(String userID);
    
    /**
     * 查询用户有管理权限的菜单
     * 原位置： IPowerMgtService
     * @author wqh
     * @since 2016年12月23日
     * @param userVO 用户对象
     * @return List<MenuRegisterVO>  返回菜单list  
     */
    public List<MenuRegisterVO> queryMenuListByUserVo(UserVO userVO);
    
    
    /**
     * 查询用户有管理权限的页面
     * 原位置： IPowerMgtService
     * @author wqh
     * @since 2016年12月23日
     * @param userID 用户
     * @return List
     */
    public Map queryPageListByUserForPower(String userID);
    
    
    
    
    
    
    /**
     * 查询用户有管理权限的页面菜单
     * 原位置： IPowerMgtService
     * @author wqh
     * @since 2016年12月23日
     * @param userID 用户
     * @return Map
     */
    public Map<String, List<MenuRegisterVO>> queryPageMenuMapByUser(String userID);
    
    
    /**
     * 递归查询子菜单ID所有的父级菜单
     * 原位置： IPowerMgtService
     * @author wqh
     * @since 2016年12月23日
     * @param menuIdList 子菜单ID
     * @return Map
     */
    public Map queryMenuRecursive(List<String> menuIdList);
    
    /**
     * 查询用户有管理权限的菜单、按钮
     * 原位置： IPowerMgtService
     * @author wqh
     * @since 2016年12月23日
     * @param userID 用户id
     * @param isAuth 标记位，仅查看用false
     * @return Map    返回菜单的map
     */
     public Map<String, List<MenuRegisterVO>> queryMenuMapByUser(String userID, boolean isAuth);
    
     /**
      * 通过角色ID查询角色关联
      * 原位置： IPowerMgtService
      * @author wqh
      * @since 2016年12月23日
      * @param roleID
      * @param orgCon
      * @param pageable
      * @param sort
      * @return
      */
     public List<PowerMgtVO> queryPowerMgtByRoleId(String roleID);
     
     /**
      * 通过角色id查询关联的菜单id
      * @param roleID
      * @return
      */
     public List<String> queryPowerMgtByRoleIdList(List<String> roleIDs);
     
     /**
      * 根据菜单Url查询是否拥有菜单权限
      * 原位置： IPowerMgtService
      * @author wqh
      * @since 2016年12月23日
      * @param pageID 页面id
      * @param userID 用户ID
      * @return
      */
     public boolean isHavePowerForMenuByUrl(String menuUrl, String userID);
     
     /**
      * 根据已过滤掉权限时效的角色ids查询用户有权限的页面
      * @param roleids
      * @param userID
      * @return
      * TODO 代码优化须删除此接口，因为此接口实现包含大量重复代码
      * @see queryPageMenuListByUser
      */
//     public List<MenuRegisterVO> queryPageListByRole(List<String> roleids,String userID);
     
     
     
     
     
     
     
     
     
     
     
     
     
     
     // 序号标记的均为 PageController中所调用
     // 1111111111111111111111111
     /** @TODO 未完成标识
      * 根据页面id查询用户列表
      * 原位置： IPowerMgtService
      * @author wqh
      * @since 2016年12月23日
      * @param pageID 页面id
      * @param userID
      * @param username
      * @param rolename
      * @return 分页用户列表
      */
//     public Page<PagePowerVO> queryPageUserByPageId(String pageID, String userID, String username);
     
     
     
     // 2222222222222222222222222
     /** @TODO 未完成标识
      * 根据页面id查询角色列表
      * @param pageID 页面id
      * @param page 分页请求
      * @return 分页角色列表
      */
     public List<PagePowerVO> queryPageRoleByPageId(String pageID, String userID, String rolename);
     
     
     // 3333333333333333333 &&& 4444444444444
     /** @TODO 未完成标识
      * 根据页面id查询可以分配页面的角色列表
      * 原位置： IPowerMgtService
      * @author wqh
      * @since 2016年12月23日
      * @param pageID 页面id
      * @param loginUserID 登录用户
      * @param page 分页请求
      * @return 分页角色列表
      * @param condition
      * @return
      */
     public List<RoleVO> queryRoleForPagePower(String pageID, String rolename, String loginUserID);
     
     
     /**
      * 根据页面id查询可以分配页面的用户列表
      * @param pageID 页面id
      * @param loginUserID 登录用户
      * @param page 分页请求
      * @return 分页用户列表
      */
//     public Page<UserVO> queryUserForPagePower(String pageID, Condition condition, String loginUserID, Pageable page);
     
}
