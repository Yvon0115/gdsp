package com.gdsp.platform.grant.auth.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gdsp.dev.core.common.AppConfig;
import com.gdsp.dev.core.model.query.Condition;
import com.gdsp.dev.persist.result.MapListResultHandler;
import com.gdsp.dev.web.utils.WebUtils;
import com.gdsp.platform.config.customization.service.ISystemConfExtService;
import com.gdsp.platform.config.global.service.IParamService;
import com.gdsp.platform.func.helper.FuncConst;
import com.gdsp.platform.func.helper.MenuConst;
import com.gdsp.platform.func.model.MenuRegisterVO;
import com.gdsp.platform.func.model.PageRegisterVO;
import com.gdsp.platform.func.service.IMenuRegisterService;
import com.gdsp.platform.grant.auth.dao.IPowerMgtDao;
import com.gdsp.platform.grant.auth.model.PagePowerVO;
import com.gdsp.platform.grant.auth.model.PowerMgtVO;
import com.gdsp.platform.grant.auth.model.UserDefaultPageVO;
import com.gdsp.platform.grant.auth.service.IPowerMgtQueryPubService;
import com.gdsp.platform.grant.auth.service.IUserRoleQueryPubService;
import com.gdsp.platform.grant.org.model.OrgVO;
import com.gdsp.platform.grant.org.service.IOrgQueryPubService;
import com.gdsp.platform.grant.role.dao.IRoleDao;
import com.gdsp.platform.grant.role.model.RoleVO;
import com.gdsp.platform.grant.user.model.UserVO;
import com.gdsp.platform.grant.user.service.IUserQueryPubService;
import com.gdsp.platform.grant.utils.GrantConst;
import com.gdsp.platform.grant.utils.GrantUtils;

@Service
@Transactional(readOnly = true)
public class PowerMgtQueryPubServiceImpl implements IPowerMgtQueryPubService {

    @Autowired
    private IPowerMgtDao         powerMgtDao;
    @Autowired
    private IUserQueryPubService userPubService;
    @Autowired
    private IMenuRegisterService menuService;
    @Resource
    private IParamService 		 paramservice;
    @Resource
    private IOrgQueryPubService  orgQueryPubService;
    @Autowired
    private IRoleDao             roleDao;
    @Autowired
    private IUserRoleQueryPubService      userRoleQueryPubService;
    @Autowired
	private ISystemConfExtService 	 confExtService;
    
    /**
     *
     * 根据用户类型查询菜单列表：超级管理员返回所有菜单，非超级管理员根据条件查询用户所关联的菜单及用户角色对应的菜单
     * @param userID 用户ID
     * @param addCond 查询条件字符串
     * @param isAuth 非超级管理员标记位，是否判断管理权限，仅展示菜单时为false
     * @return List<MenuRegisterVO>    返回菜单list
     */
    public List<MenuRegisterVO> queryMenuListByCond(String userID, String addCond, boolean isAuth) {
        UserVO userVO = userPubService.load(userID);
        String powerCond = "";
        // 根据用户类型查询
        if (userVO.getUsertype() == GrantConst.USERTYPE_ADMIN) {
            if (!isAuth) {
                // 超级管理员，管理所有管理类菜单
                powerCond = "ma.isrootmenu in ('y', 'Y')";
                if (StringUtils.isEmpty(addCond)) {
                    addCond = powerCond;
                } else {
                    addCond += " and " + powerCond;
                }
            }
            return powerMgtDao.queryMenuListByAddCond(addCond);
        } else {
            powerCond = "( mp.pk_role = '" + userID + "' or mp.pk_role in (select pk_role from rms_user_role where pk_user='" + userID + "'))";
            // 授权时，查询所有类型菜单，查询时，"只有管理权限"查询管理类菜单
            Object paramvalue = paramservice.queryParamValue(GrantConst.PARAM_ISLIMIT);
            if (!isAuth && "Y".equals(paramvalue.toString()) && "Y".equals(userVO.getOnlyadmin())) {
                powerCond += " and mb.funtype = 2";
            }
            if (StringUtils.isEmpty(addCond)) {
                addCond = powerCond;
            } else {
                addCond += " and " + powerCond;
            }
            // 用户有管理权限的菜单
            return menuService.queryMenuListByCondForPower(addCond,userID);
        }
    }

    /***
     * 根据用户类型查询菜单分页列表。超级管理员返回所有菜单，非超级管理员根据查询用户所关联菜单及用户角色角色对应的菜单
     * @param userID 用户ID
     * @param addCond 查询条件字符串
     * @param isAuth 非超级管理员标记位，是否判断管理权限，仅展示菜单时为false
     * @param condition 输入过滤条件,一般为前台条件
     * @param page     分页请求
     * @return  分页数据
     */
    /*public Page<MenuRegisterVO> queryMenuPageByCond(String userID, String addCond, boolean isAuth, Condition condition, Pageable page) {
        UserVO userVO = userPubService.load(userID);
        String powerCond = "";
        // 根据用户类型查询
        if (userVO.getUsertype() == GrantConst.USERTYPE_ADMIN) {
            if (!isAuth) {
                // 超级管理员，管理所有管理类菜单
                powerCond = "ma.isrootmenu in ('y', 'Y')";
                if (StringUtils.isEmpty(addCond)) {
                    addCond = powerCond;
                } else {
                    addCond += " and " + powerCond;
                }
            }
            return powerMgtDao.queryMenuPageByCond(addCond, condition, page);
        } else {
            //
            powerCond = "( mp.pk_role = '" + StringEscapeUtils.escapeSql(userID) + "' or mp.pk_role in (select pk_role from rms_user_role where pk_user='" + userID + "'))";
            // 授权时，查询所有类型菜单，查询时，"只有管理权限"查询管理类菜单
            Object paramvalue = paramservice.queryParamValue(GrantConst.PARAM_ISLIMIT);
            if (!isAuth && "Y".equals(paramvalue.toString()) && "Y".equals(userVO.getOnlyadmin())) {
                powerCond += " and mb.funtype = 2";
            }
            if (StringUtils.isEmpty(addCond)) {
                addCond = powerCond;
            } else {
                addCond += " and " + powerCond;
            }
            // 用户有管理权限的菜单
            return powerMgtDao.queryMenuPageByCondForPower(addCond, condition, page);
        }
    }*/

    /***
    * @Title: queryMenuMapByCond
    * @Description: 根据用户类型查询菜单列表。超级管理员返回所有菜单，非超级管理员根据查询用户所关联菜单及用户角色角色对应的菜单
    * @param userID 用户ID
    * @param addCond 查询条件字符串
    * @param isAuth 非超级管理员标记位，是否判断管理权限,仅展示菜单时为false
    * @return    参数说明
    * @return Map<String,List<MenuRegisterVO>>    返回map
    *      */
    public Map<String, List<MenuRegisterVO>> queryMenuMapByCond(String userID, String addCond, boolean isAuth) {
        UserVO userVO = userPubService.load(userID);
        String powerCond = "";
        MapListResultHandler<MenuRegisterVO> handler = new MapListResultHandler<>("parentid");
        // 根据用户类型查询
        if (userVO != null && userVO.getUsertype() == GrantConst.USERTYPE_ADMIN) {
            if (!isAuth) {
                // 超级管理员，管理所有管理类菜单
                powerCond = "ma.isrootmenu in ('y', 'Y')";
                if (StringUtils.isEmpty(addCond)) {
                    addCond = powerCond;
                } else {
                    addCond += " and " + powerCond;
                }
            }
            powerMgtDao.queryMenuMapByAddCond(addCond, handler);
        } else {
            // 
            powerCond = "( mp.pk_role = '" + userID + "' or  mp.pk_role in (select pk_role from rms_user_role where pk_user='" + userID + "'))";
            // 授权时，查询所有类型菜单，查询时，"只有管理权限"查询管理类菜单
            Object paramvalue = paramservice.queryParamValue(GrantConst.PARAM_ISLIMIT);
            if (!isAuth && userVO != null && "Y".equals(paramvalue.toString()) && "Y".equals(userVO.getOnlyadmin())) {
                powerCond += " and mb.funtype = 2";
            }
            if (StringUtils.isEmpty(addCond)) {
                addCond = powerCond;
            } else {
                addCond += " and " + powerCond;
            }
            // 用户有管理权限的菜单
            powerMgtDao.queryMenuMapByCondForPower(addCond, handler);
        }
        return handler.getResult();
    }

    @Override
    public List<MenuRegisterVO> queryMenuListByUser(String userID) {
        return this.queryMenuListByCond(userID, null, false);
    }

    /** TODO 代码须优化 */
    @Override
    public List<MenuRegisterVO> queryPageMenuListByUser(String userID) {
    	
        // 权限时效过滤
    	String agingStatus = confExtService.queryGrantAgingConfigs().getStatus();
    	// 查询出的页面菜单
    	List<MenuRegisterVO> pageMenuList = null;
    	if ("Y".equalsIgnoreCase(agingStatus)) {    						// 开启权限时效
    		List<String> filterRoleIds = getFilterRoleIds(userID);
    		pageMenuList = powerMgtDao.queryPageListByRole(filterRoleIds);
//    		return queryPageListByRole(filterRoleIds, userID); // 弃用的接口
		}else{    															// 关闭权限时效
			pageMenuList= powerMgtDao.queryPageMenuListByUser(userID);
		}
        if (pageMenuList == null || pageMenuList.size() == 0)  return null;
        // 菜单去重
        Set<String> set = new HashSet<String>();
        List<MenuRegisterVO> pageMenus = new ArrayList<MenuRegisterVO>();
        for (MenuRegisterVO page : pageMenuList) {
//	        	userPageMenuIds.add(menuRegisterVO.getId());
        	if (set.add(page.getTabId())) {
        		pageMenus.add(page);
			}
		}
    	/*
        // 原来的代码，一个菜单关联一个页面
        String lastId = "";
        for (MenuRegisterVO registerVO : registerVOs) {
            if (registerVO.getId().equals(lastId)){
                break;
            } else {
                resultVOs.add(registerVO);
                lastId = registerVO.getId();
            }
        }
	    */
        /******** 2016/11/18 更改，数据结构变更 ****************
	  	 * 页面菜单增加上下级关系，现为两级，即 页签 -> 页面(1-n)
	  	 * [parentA, parentB, parentC...]
	  	 * 	    |
	  	 *   childrenPage = [ parentA, pageA-2, pageA-3...]
	  	 * ***************************************************/
        // 设置用户的默认首页
	    List<UserDefaultPageVO> userDefaultPages = findDefaultPageByUser(userID);
	    setDefaultPages(pageMenus,userDefaultPages);
	    // 将页面菜单列表加工为具有上下级关系的页面菜单
        List<MenuRegisterVO> navMenus = convertToFiliationPages(pageMenus);
        // 排序，将默认首页设置到第一位
	    sortDefaultPages(navMenus,userDefaultPages);    
	    
	    /** 以下逻辑为：如果用户连一级页面菜单的权限都没有，则即使用户关联了页面，也不予展示。
	     *  TODO 在保证菜单查询数据正常的情况下，可优化掉大部分代码 
	     */
        List<MenuRegisterVO> userMenus = queryMenuListByUser(userID);
        List<String> userMenuIds = new ArrayList<String>();
        for (MenuRegisterVO menuRegisterVO : userMenus) {
        	userMenuIds.add(menuRegisterVO.getId());
		}
		Iterator<MenuRegisterVO> ite = navMenus.iterator();
		while (ite.hasNext()) {
			String pageMenuId = ite.next().getId();
			if (!userMenuIds.contains(pageMenuId)) {
				ite.remove();
			}
		}
	    return navMenus;
    	
    }

    /**
     * 将页面菜单列表转换成为具有父子关系的页面菜单列表
     * @param pageMenus 页面菜单列表
     * @return 具有父子关系的页面菜单列表
     */
	private List<MenuRegisterVO> convertToFiliationPages(List<MenuRegisterVO> pageMenus) {
		List<MenuRegisterVO> pageMenuList = new ArrayList<MenuRegisterVO>();
		String parentID = "";    // 父级ID
		int parentIndex = -1;    // 页面菜单下标
		MenuRegisterVO parentPage = null;
		List<MenuRegisterVO> chidrenPage = null;
		for (MenuRegisterVO vo : pageMenus) {
		  	String pageID = vo.getId();    // 页面菜单的ID
			if (parentID.equals(pageID)) {
				// if里仅在父级下添加子页面
				parentPage = pageMenuList.get(parentIndex);    // 获取父级
				parentPage.getChildrenPage().add(vo);    // 在父级下增加子页面  
			} else {
				// else里仅生成新的父级菜单和第一个子菜单
				chidrenPage = new ArrayList<MenuRegisterVO>();    // 生成新的子级菜单列表
				chidrenPage.add(vo);    // 将自身作为自己的第一个子级页面
				
				vo.setChildrenPage(chidrenPage);    // 新的子级菜单列表添加到新的父级下
				pageMenuList.add(vo);    // 新的父级
				
				parentID = pageID;
				parentIndex++;
			}
		}
		return pageMenuList;
	}

    // 查询用户有权限的一级菜单，在用户登录后首页跳转和导航菜单加载的时候用到
    @Override
    public List<MenuRegisterVO> queryFirstLevelMenuListByUser(String userID) {
        return this.queryMenuListByCond(userID, " ma.funtype<>4 and length(ma.innercode)=4 ", false);
    }

    @Override
    public List<MenuRegisterVO> queryMenuForRolePower(String roleID, String loginUserID) {
        return this.queryMenuListByCond(loginUserID, null, true);
    }

    @Override
    public List<PageRegisterVO> queryPageListForMenu(String userID, String menuID) {
        //权限过滤
        return powerMgtDao.queryPageListByMenu(menuID);
    }

    /** 用户默认首页 */
	@Override
	public List<UserDefaultPageVO> findDefaultPageByUser(String userID) {
		// 写点什么
		return powerMgtDao.queryUserDefaultPages(userID);
	}
	
	/**
	 * 根据用户设置的默认首页对页面进行排序
	 * @param navMenus
	 * @param userDefaultPages
	 */
	private void setDefaultPages(List<MenuRegisterVO> registerVOs,List<UserDefaultPageVO> userDefaultPages ){
		if (userDefaultPages != null && userDefaultPages.size() > 0) {
			for (MenuRegisterVO page : registerVOs) {
				for (UserDefaultPageVO dhp : userDefaultPages) {
					if (page.getPageid().equals(dhp.getPage_id())&& page.getId().equals(dhp.getMenu_id())) {
						page.setDefaultDisplay(true);
					}
				}
			}
		}
	}
	
	
	/**
	 * 排序，只是将默认首页排到第一位
	 * @param navMenus
	 * @param userDefaultPages
	 */
	private void sortDefaultPages(List<MenuRegisterVO> navMenus,List<UserDefaultPageVO> userDefaultPages) {
		
		if (userDefaultPages != null && userDefaultPages.size() > 0) {
			for (MenuRegisterVO menu : navMenus) {
				List<MenuRegisterVO> childrenPage = menu.getChildrenPage();
				for (int i = 0; i < childrenPage.size(); i++) {
					MenuRegisterVO page = childrenPage.get(i);
					if (page.isDefaultDisplay()) {
						Collections.swap(menu.getChildrenPage(), i, 0);
					}
				}
				
			}
		}
	}
	
	/**
     * 查询所有角色，菜单资源关系
     */
    @Override
    public List<PowerMgtVO> queryAllRoleMenuList(){
    	return powerMgtDao.findAllRoleMenuList();
    }
    
    /**
     * 查询所有角色，页面资源关系
     */
    @Override
    public List<PowerMgtVO> queryAllRolePageList(){
    	return powerMgtDao.findAllRolePageList();
    }

    
	@Override
	public List<PowerMgtVO> isConnectToRole(String resourceId) {
		return powerMgtDao.isConnectToRole(resourceId);
	}
	
	
	@Override
    public Map<String, List<MenuRegisterVO>> queryLowerLevelMenuMapByUser(String userID, String menuId) {
        if (StringUtils.isEmpty(menuId))
            return null;
        UserVO userVO = userPubService.load(userID);
        MenuRegisterVO menuVO = menuService.loadMenuRegisterVOById(menuId);
        String addCond = " ma.innercode like '" + menuVO.getInnercode() + "%' and ma.id <> '" + menuId + "' ";
        // 根据用户类型查询
        if (userVO != null && userVO.getUsertype() == GrantConst.USERTYPE_ADMIN) {
            String powerCond = "ma.isrootmenu in ('y', 'Y')";
            if (StringUtils.isEmpty(addCond)) {
                addCond = powerCond;
            } else {
                addCond += " and " + powerCond;
            }
        }
        
        /* --============================菜单支持自定义图标相对路径读取修改================================-- */
        /* --=========================修改人：yucl；修改日期：10月24日17:35=============================-- */
        Map<String, List<MenuRegisterVO>> menuMapByCond = this.queryMenuMapByCond(userID, addCond, false);
        String menuIconFieldUrl = AppConfig.getProperty("menuIconFieldPathPrefix");
        Iterator<Entry<String, List<MenuRegisterVO>>> entries = menuMapByCond.entrySet().iterator();  
        while (entries.hasNext()) {  
            Entry<String, List<MenuRegisterVO>> entry = entries.next();  
            List<MenuRegisterVO> menuRegList = entry.getValue();
            for(MenuRegisterVO VO : menuRegList){
                if(StringUtils.isEmpty(VO.getIconField())) {
                    continue;
                }
                if(StringUtils.isNotEmpty(menuIconFieldUrl) && (!"default".equalsIgnoreCase(menuIconFieldUrl))){
                    VO.setIconField(menuIconFieldUrl+VO.getIconField());
                } else {
                    VO.setIconField(WebUtils.getImagePath() + VO.getIconField());
                }
            }
        }
        /* --===================================修改至此结束========================================-- */
        return menuMapByCond;
    }

	@Override
    public Map<String, List<MenuRegisterVO>> queryAllLevelMenuListByUser(String userID) {
        return this.queryMenuMapByCond(userID, null, false);
    }

	@Override
    public Map<String, List<MenuRegisterVO>> queryLevelMenuMapByUser(String userID) {
        return this.queryMenuMapByCond(userID, " ( ma.funtype = " + MenuConst.MENUTYPE_DIR.getValue() + " or ma.funtype = " + MenuConst.MENUTYPE_MULTILEVEL.intValue() + ")",
                true);
    }

	@Override
	public List<MenuRegisterVO> queryMenuListByUserVo(UserVO userVO) {
	    return queryMenuListByCond(userVO, null, false);
	}
 
	 /***
    * @Title: queryMenuListByCond
    * @Description: 根据用户类型查询菜单列表。超级管理员返回所有菜单，非超级管理员根据查询用户所关联菜单及用户角色角色对应的菜单
    * @param UserVO 用户对象
    * @param addCond 查询条件字符串
    * @param isAuth 非超级管理员标记位，是否判断管理权限，仅展示菜单时为false
    * @return    参数说明
    * @return List<MenuRegisterVO>    返回菜单list
    *      */
    public List<MenuRegisterVO> queryMenuListByCond(UserVO userVO, String addCond, boolean isAuth) {
        String powerCond = "";
        if (userVO == null || userVO.getId() == null || userVO.getUsertype() == null) {
            return new ArrayList<MenuRegisterVO>();
        }
        // 根据用户类型查询
        if (userVO.getUsertype() == GrantConst.USERTYPE_ADMIN) {
            if (!isAuth) {
                // 超级管理员，管理所有管理类菜单
                powerCond = "ma.isrootmenu in ('y', 'Y')";
                if (StringUtils.isEmpty(addCond)) {
                    addCond = powerCond;
                } else {
                    addCond += " and " + powerCond;
                }
            }
            return powerMgtDao.queryMenuListByAddCond(addCond);
        } else {
            //
            powerCond = "( mp.pk_role = '" + userVO.getId() + "' or mp.pk_role in (select pk_role from rms_user_role where pk_user='" + userVO.getId() + "'))";
            // 授权时，查询所有类型菜单，查询时，"只有管理权限"查询管理类菜单
            Object paramvalue = paramservice.queryParamValue(GrantConst.PARAM_ISLIMIT);
            if (!isAuth && "Y".equals(paramvalue.toString()) && "Y".equals(userVO.getOnlyadmin())) {
                powerCond += " and mb.funtype = 2";
            }
            if (StringUtils.isEmpty(addCond)) {
                addCond = powerCond;
            } else {
                addCond += " and " + powerCond;
            }
            // 用户有管理权限的菜单
            return menuService.queryMenuListByCondForPower(addCond,userVO.getId());
        }
    }

    @Override
    public Map queryPageListByUserForPower(String userID) {
        UserVO userVO = userPubService.load(userID);
        String addCond = "";
        // 根据用户类型查询
        if (userVO != null && userVO.getUsertype() != GrantConst.USERTYPE_ADMIN) {
            // 超级管理员，管理所有管理类菜单
            addCond = "";
        }
        MapListResultHandler handler = new MapListResultHandler("menuid");
        powerMgtDao.queryPageListForPowerByAddCond(addCond, handler);
        return handler.getResult();
    }

    
    
	
	
	@Override
    public Map<String, List<MenuRegisterVO>> queryPageMenuMapByUser(String userID) {
        return this.queryMenuMapByCond(userID, " ma.funtype = " + MenuConst.MENUTYPE_PAGE.intValue(), true);
    }
	
	@Override
	public Map<String, List<MenuRegisterVO>> queryMenuMapByUser(String userID, boolean isAuth) {
        if (StringUtils.isBlank(userID)) {
            return null;
        }
        return this.queryMenuMapByCond(userID, null, isAuth);
    }
	
	@Override
    public List<PowerMgtVO> queryPowerMgtByRoleId(String roleID) {
		Condition condition = new Condition();
		if(roleID!=null){
			condition.addExpression("m.pk_role", roleID);
		}else{
			condition.addExpression("m.pk_role","''","=");
		}
        return powerMgtDao.queryMenuRoleListByRoleId(condition, null);
    }
	 
	@Override
	public List<String> queryPowerMgtByRoleIdList(List<String> roleIDs) {
		Condition condition = new Condition();
		if(roleIDs!=null&&roleIDs.size()>0){
			// 权限时效是否开启
			String agingStatus = confExtService.queryGrantAgingConfigs().getStatus();
//	        if ("Y".equalsIgnoreCase(agingStatus)) {
	        	/*String loginUserID = AppContext.getContext().getContextUserId();
	        	List<RoleVO> rolevos = userRoleQueryPubService.queryFilteredRoleListByUserId(loginUserID);
	        	List<String> roleIds = new ArrayList<String>();
	        	for (RoleVO roleVO : rolevos) {
	        		roleIds.add(roleVO.getId());
				}
	        	condition.addExpression("m.pk_role", roleIds,"in");	*/
//	        }else{
	        	condition.addExpression("m.pk_role", roleIDs,"in");	
//	        }
			
		}else{
			condition.addExpression("m.pk_role", "("+"''"+")","in");
		}
        return powerMgtDao.queryMenuIdsByRoleId(condition, null);
	}

	/**
     * 根据菜单Url查询是否拥有菜单权限
     * @param pageID 页面id
     * @param userID 用户ID
     */
    @Override
    public boolean isHavePowerForMenuByUrl(String menuUrl, String userID) {
        int num = powerMgtDao.existPowerForMenuByUrl(menuUrl, userID);
        if (num > 0) {
            return true;
        } else {
            return false;
        }
    }
	
    
    @Override
    public List<PagePowerVO> queryPageRoleByPageId(String pageID, String userID,String rolename) {
        if (StringUtils.isEmpty(pageID))
            return null;
        Condition condition = new Condition();
        if (rolename != null) {
        	condition.addExpression("ROLENAME", rolename,"like");
		}
        // 登录用户有权限的机构的角色，不含待分配用户已经关联的角色
        UserVO userVO = userPubService.load(userID);
        //String addCond = "( p.resource_id ='" + pageID + "') ";
        condition.addExpression("p.resource_id", pageID);
        // 根据用户类型设置条件查询
        if (userVO != null && userVO.getUsertype() != GrantConst.USERTYPE_ADMIN) {
            // 用户有管理权限的机构用户
            //addCond += " and " + GrantUtil.getOrgPowerCondByUser(userID, "r.pk_org");
        	condition.addExpression("r.pk_org", GrantUtils.getOrgPowerStrByUser(userID), "in");
        }
        /** ---- 查询更改  wqh 2016/12/22  原因：权限拆分 -------------------------*/
        // 更改前
//        List<PagePowerVO> queryPageRoleByAddCond = powerMgtDao.queryPageRoleByAddCond(condition);
//        List<PagePowerVO> PagePowerVOContent = queryPageRoleByAddCond.getContent();
        // 更改后
        List<PagePowerVO> pagePowerVOContent = powerMgtDao.queryPageRoleByAddCond(condition);
        
        /**---------------------- 更改至此结束 ----------------------------------*/

        
        List<String> list = new ArrayList<String>();
        if (pagePowerVOContent != null && pagePowerVOContent.size() > 0) {
            for (PagePowerVO pagePowerVO : pagePowerVOContent) {
                list.add(pagePowerVO.getRoleVO().getPk_org());
            }
        }
        if (list.size() > 0) {
            List<OrgVO> queryOrgListByIDs = orgQueryPubService.queryOrgListByIDs(list);
            for (PagePowerVO pagePowerVO : pagePowerVOContent) {
                for (OrgVO orgVO : queryOrgListByIDs) {
                    if (pagePowerVO.getRoleVO().getPk_org().equals(orgVO.getId())) {
                        pagePowerVO.getRoleVO().setOrgname(orgVO.getOrgname());
                        break;
                    }
                }
            }
        }
        return pagePowerVOContent;
    }
    
    
    /*@Override
    public Page<PagePowerVO> queryPageUserByPageId(String pageID, String userID, Condition cond, Pageable page) {
        if (StringUtils.isEmpty(pageID))
            return null;
        Sorter sort = new Sorter(Direction.ASC, "u.account");
        Condition condition = new Condition();
        Page<PagePowerVO> queryPageUserByAddCond = null;
        List<PagePowerVO> pagePowerVOcontent = new ArrayList<PagePowerVO>();
        // 登录用户有权限的机构的角色，不含待分配用户已经关联的角色
        UserVO userVO = userPubService.load(userID);
        if (cond != null) {
            String userIDs = "";
            List<UserVO> queryUserListByCond = userService.queryUserListByCondition(cond, sort);
            if (queryUserListByCond != null && queryUserListByCond.size() > 0) {
                for (UserVO uservo : queryUserListByCond) {
                    userIDs += "'" + uservo.getId() + "',";
                }
                if (userIDs.length() > 0) {
                    userIDs = userIDs.substring(0, userIDs.length() - 1);
                } else {
                    userIDs = "''";
                }
            }
            condition.addExpression("p.pk_role", userIDs, "in");
            queryPageUserByAddCond = powerMgtDao.queryPageUserByAddCond(pageID, condition, page);
            pagePowerVOcontent = queryPageUserByAddCond.getContent();
        } else {
            queryPageUserByAddCond = powerMgtDao.queryPageUserByAddCond(pageID, condition, page);
            pagePowerVOcontent = queryPageUserByAddCond.getContent();
        }
        String userId = "";
        if (pagePowerVOcontent != null && pagePowerVOcontent.size() > 0) {
            for (PagePowerVO pagePowerVO : pagePowerVOcontent) {
                userId += "'" + pagePowerVO.getPk_role() + "',";
            }
        }
        if (userId.length() > 0) {
            userId = userId.substring(0, userId.length() - 1);
            cond.addExpression("u.id", userId, "in");
        }
        // 根据用户类型设置条件查询
        if (userVO != null && userVO.getUsertype() != GrantConst.USERTYPE_ADMIN) {
            // 用户有管理权限的机构用户
            cond.addExpression("u.pk_org", GrantUtils.getOrgPowerStrByUser(userID), "in");
        }
        List<UserVO> queryUserListByCondition = userService.queryUserListByCondition(cond, sort);
        if (queryUserListByCondition != null && queryUserListByCondition.size() > 0) {
            for (PagePowerVO pagePowerVO : queryPageUserByAddCond) {
                for (UserVO uservo : queryUserListByCondition) {
                    if (pagePowerVO.getPk_role().equals(uservo.getId())) {
                        pagePowerVO.getUserVO().setAccount(uservo.getAccount());
                        pagePowerVO.getUserVO().setUsername(uservo.getUsername());
                        pagePowerVO.getUserVO().setMobile(uservo.getMobile());
                        pagePowerVO.getUserVO().setEmail(uservo.getEmail());
                        pagePowerVO.getUserVO().setMemo(uservo.getMemo());
                    }
                }
            }
        }
        return queryPageUserByAddCond;
    }*/
    
    
    @Override
    public List<RoleVO> queryRoleForPagePower(String pageID, String rolename, String loginUserID) {
    	Condition condition = new Condition();
    	if (rolename != null) {
			condition.addExpression("rolename", rolename,"like");
		}
        // 查询用户所在机构
        // 根据内部码查询用户所在机构的最上级机构
        // 登录用户有权限的机构的角色，不含待分配页面已经关联的角色
        UserVO userVO = userPubService.load(loginUserID);
        String addCond = " r.id not in (select pk_role from rms_power_page where resource_id ='" + pageID + "') ";
        // 根据用户类型设置条件查询
        if (userVO.getUsertype() != GrantConst.USERTYPE_ADMIN) {
            // 用户有管理权限的机构用户
            addCond += " and " + GrantUtils.getOrgPowerCondByUser(loginUserID, "r.pk_org");
        }
        
        //List<OrgVO> orgVOs= orgService.queryOrgListByCondition(orgCond, sort);
//        Page<RoleVO> proleVOs = roleDao.queryRoleByAddCond(condition, addCond, pageable);
        //角色赋予orgname
//        List<RoleVO> roleVOs = proleVOs.getContent();
        List<RoleVO> roleVOs = roleDao.queryRoleByAddCond(condition, addCond);
        if (roleVOs != null && roleVOs.size() > 0) {
            List<String> orgids = new ArrayList<String>();
            for (RoleVO roleVO : roleVOs) {
                orgids.add(roleVO.getPk_org());
            }
            List<OrgVO> orgVOs = orgQueryPubService.queryOrgListByIDs(orgids);
            for (RoleVO roleVO : roleVOs) {
                for (OrgVO orgVO : orgVOs) {
                    if (roleVO.getPk_org().equals(orgVO.getId())) {
                        roleVO.setOrgname(orgVO.getOrgname());
                        break;
                    }
                }
            }
        }
        return roleVOs;
    }
    
    /*@Override
    public Page<UserVO> queryUserForPagePower(String pageID, Condition condition, String loginUserID, Pageable page) {
        // 登录用户有权限的机构的用户，不含已经关联页面的用户及登录用户
        UserVO userVO = userPubService.load(loginUserID);
        String addCond = "  u.usertype = " + GrantConst.USERTYPE_USER + " and u.id not in (select pk_role from rms_power_page where resource_id ='" + pageID + "') ";
        // 根据用户类型设置条件查询
        if (userVO.getUsertype() != GrantConst.USERTYPE_ADMIN) {
            // 用户有管理权限的机构用户
            addCond += " and " + GrantUtils.getOrgPowerCondByUser(loginUserID, "u.pk_org");
            addCond += " and u.id <>'" + loginUserID + "' ";
        }
        return userService.queryUserPageByAddCond(condition, addCond, page);
    }*/

    @Override
    public Map queryMenuRecursive(List<String> menuIdList) {
        if (menuIdList == null) {
            return null;
        }
        Set idSet = new HashSet();
        for (String menuId : menuIdList) {
            this.queryMenuParentId(menuId, idSet);
        }
        return menuService.queryMenuMapByIds(idSet);
    }
    
    public Set<String> queryMenuParentId(String menuId, Set<String> set) {
        if (StringUtils.isNotBlank(menuId)) {
            set.add(menuId);
            menuId = menuService.queryMenuParentId(menuId);
            queryMenuParentId(menuId, set);
        }
        return set;
    }

	/* TODO 重复代码 待删除
	@Override
	public List<MenuRegisterVO> queryPageListByRole(List<String> roleids,String userID) {
		// 导航栏菜单
        List<MenuRegisterVO> navMenus = new ArrayList<MenuRegisterVO>();
        List<MenuRegisterVO> registerVOs = powerMgtDao.queryPageListByRole(roleids);
        if (registerVOs == null || registerVOs.size() == 0)
            return null;
        // 用户的默认首页
	    List<UserDefaultPageVO> userDefaultPages = findDefaultPageByUser(userID);
	    // 如果设置了默认首页，就要将默认首页展示到第一位
	    if (userDefaultPages != null && userDefaultPages.size() > 0) {
	    	setDefaultPages(registerVOs,userDefaultPages);
		}
	    convertToFiliationPages(navMenus, registerVOs);
	    if (userDefaultPages != null && userDefaultPages.size() > 0) {
	    	sortDefaultPages(navMenus);
		}
	    // 用户有管理权的菜单
        List<MenuRegisterVO> userMenus = queryMenuListByUser(userID);
        List<String> userMenuIds = new ArrayList<String>();
        for (MenuRegisterVO menuRegisterVO : userMenus) {
        	userMenuIds.add(menuRegisterVO.getId());
		}
	    // 迭代循环，如果用户角色关联的菜单中不包括页面菜单(部门首页之类)，去除
		Iterator<MenuRegisterVO> ite = navMenus.iterator();
		while (ite.hasNext()) {
			String pageMenuId = ite.next().getId();
			if (!userMenuIds.contains(pageMenuId)) {
				ite.remove();
			}
		}
		
	    return navMenus;
	}*/
	
	
	//根据用户得到过滤权限后的角色id TODO  代码优化
	private List<String> getFilterRoleIds(String userID){
		List<String> roleids = new ArrayList<String>();
        List<RoleVO> rolevos = userRoleQueryPubService.queryFilteredRoleListByUserId(userID);
        for (RoleVO roleVO : rolevos) {
			String id = roleVO.getId();
			roleids.add(id);
		}
		return roleids;
	}
    
}
