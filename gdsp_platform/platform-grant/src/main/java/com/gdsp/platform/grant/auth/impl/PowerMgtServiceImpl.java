package com.gdsp.platform.grant.auth.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gdsp.dev.core.model.query.Condition;
import com.gdsp.dev.persist.result.MapListResultHandler;
import com.gdsp.platform.config.global.service.IParamService;
import com.gdsp.platform.func.model.MenuRegisterVO;
import com.gdsp.platform.func.service.IMenuRegisterService;
import com.gdsp.platform.grant.auth.dao.IPowerMgtDao;
import com.gdsp.platform.grant.auth.model.PagePowerVO;
import com.gdsp.platform.grant.auth.model.PowerMgtVO;
import com.gdsp.platform.grant.auth.service.IPowerMgtService;
import com.gdsp.platform.grant.org.service.IOrgQueryPubService;
import com.gdsp.platform.grant.role.dao.IRoleDao;
import com.gdsp.platform.grant.user.model.UserVO;
import com.gdsp.platform.grant.user.service.IUserQueryPubService;
import com.gdsp.platform.grant.utils.GrantConst;
import com.gdsp.platform.log.service.OpLog;

@Service
@Transactional(readOnly = true)
public class PowerMgtServiceImpl implements IPowerMgtService {

    @Autowired
    private IPowerMgtDao         powerMgtDao;
    @Resource
    private IUserQueryPubService userPubService;
    @Autowired
    private IRoleDao             roleDao;
    @Resource
    private IMenuRegisterService menuService;
    @Resource
    private IOrgQueryPubService  orgQueryPubService;
    @Resource
	private IParamService paramservice;

    @Transactional
    @OpLog
    @Override
    public List<PowerMgtVO> addPowerMgtOnRole(String roleID, String menuIdStr) {
        if (StringUtils.isEmpty(roleID))
            return null;
        //删除已有的关联		
        powerMgtDao.deleteRoleMenuPower(new String[] { roleID });
        //插入新的关系start
        String[] mgtIDs = menuIdStr.split(",");
        //处理菜单ID，只插入最后一级借点，类型为，234.（管理菜单，业务菜单，页面菜单）
        List<String> queryMenuIdsByType345 = menuService.queryMenuIdsByType234(mgtIDs);
        PowerMgtVO powerMgtVO;
        if (queryMenuIdsByType345 != null) {
            for (String menuID : queryMenuIdsByType345) {
                powerMgtVO = new PowerMgtVO();
                powerMgtVO.setPk_role(roleID);
                powerMgtVO.setResource_id(menuID);
                powerMgtDao.insert(powerMgtVO);
            }
        }
        //插入新的关系end
        //查询新的关系
        Condition condition = new Condition();
        condition.addExpression("m.pk_role", roleID);
        return powerMgtDao.queryMenuRoleListByRoleId(condition, null);
    }

    @Transactional
    @OpLog
    @Override
    public boolean deleteRolePowerMgt(String roleID) {
        powerMgtDao.deleteRoleMenuPower(new String[] { roleID });
        return true;
    }

    @Transactional
    @OpLog
    @Override
    public boolean deletePageRoleByPageID(String pageID) {
        powerMgtDao.deletePagePowerByPageID(pageID);
        return true;
    }

    @Transactional
    @OpLog
    @Override
    public boolean deletePageRole(String... ids) {
        powerMgtDao.deletePagePower(ids);
        return true;
    }

    /***
    * @Title: queryMenuListByCond
    * @Description: 根据用户类型查询菜单列表。超级管理员返回所有菜单，非超级管理员根据查询用户所关联菜单及用户角色角色对应的菜单
    * @param userID 用户ID
    * @param addCond 查询条件字符串
    * @param isAuth 非超级管理员标记位，是否判断管理权限，仅展示菜单时为false
    * @return    参数说明
    * @return List<MenuRegisterVO>    返回菜单list
    *      */
    /*public List<MenuRegisterVO> queryMenuListByCond(String userID, String addCond, boolean isAuth) {
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
            //
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
            return menuService.queryMenuListByCondForPower(addCond);
        }
    }*/

    /*@Override
    public List<MenuRegisterVO> queryMenuListByUserVo(UserVO userVO) {
        return queryMenuListByCond(userVO, null, false);
    }*/

    /***
    * @Title: queryMenuListByCond
    * @Description: 根据用户类型查询菜单列表。超级管理员返回所有菜单，非超级管理员根据查询用户所关联菜单及用户角色角色对应的菜单
    * @param UserVO 用户对象
    * @param addCond 查询条件字符串
    * @param isAuth 非超级管理员标记位，是否判断管理权限，仅展示菜单时为false
    * @return    参数说明
    * @return List<MenuRegisterVO>    返回菜单list
    *      */
    /*public List<MenuRegisterVO> queryMenuListByCond(UserVO userVO, String addCond, boolean isAuth) {
        String powerCond = "";
        if (userVO == null || userVO.getId() == null || Integer.valueOf(userVO.getUsertype()) == null) {
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
            return menuService.queryMenuListByCondForPower(addCond);
        }
    }*/

    /***
     * 根据用户类型查询菜单分页列表。超级管理员返回所有菜单，非超级管理员根据查询用户所关联菜单及用户角色角色对应的菜单
     * @param userID 用户ID
     * @param addCond 查询条件字符串
     * @param isAuth 非超级管理员标记位，是否判断管理权限，仅展示菜单时为false
     * @param condition 输入过滤条件,一般为前台条件
     * @param page     分页请求
     * @return  分页数据
     */
    public Page<MenuRegisterVO> queryMenuPageByCond(String userID, String addCond, boolean isAuth, Condition condition, Pageable page) {
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
    }

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

   /* @Override
    public List<MenuRegisterVO> queryMenuListByUser(String userID) {
        return this.queryMenuListByCond(userID, null, false);
    }*/

	/******************************************************
	 * 已移至公共接口  IPowerMgtQueryPubService
	 * 原因：权限拆分
	 * wqh 2016/12/26
	 *****************************************************/
    /*@Override
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
        return this.queryMenuMapByCond(userID, addCond, false);
    }*/
    
    @Override
    public Page<MenuRegisterVO> queryNextLevelMenuPageByUser(String userId, String menuId, Condition condition, Pageable pageable) {
        if (StringUtils.isEmpty(menuId))
            return null;
//        MenuRegisterVO menuVO = menuService.loadMenuRegisterVOById(menuId);
//        String addCond = " ma.parentid = '" + StringEscapeUtils.escapeSql(menuId) + "'";
        String addCond = " ma.parentid = '" + StringUtils.replace(menuId, "'", "''") + "'";
        String userID = StringUtils.replace(userId, "'", "''");
        return this.queryMenuPageByCond(userID, addCond, false, condition, pageable);
    }

    /******************************************************
	 * 已移至公共接口  IPowerMgtQueryPubService
	 * 原因： 权限拆分
	 * wqh 2016/12/26
	 *****************************************************/
    /*@Override
    public Map<String, List<MenuRegisterVO>> queryMenuMapByUser(String userID, boolean isAuth) {
        if (StringUtils.isBlank(userID)) {
            return null;
        }
        return this.queryMenuMapByCond(userID, null, isAuth);
    }*/

    /******************************************************
	 * 已移至公共接口  IPowerMgtQueryPubService
	 * 原因： 权限拆分
	 * wqh 2016/12/26
	 *****************************************************/
    /*@Override
    public Map<String, List<MenuRegisterVO>> queryPageMenuMapByUser(String userID) {
        //
        return this.queryMenuMapByCond(userID, " ma.funtype = " + FuncConst.MENUTYPE_PAGE, true);
    }*/

    /******************************************************
	 * 已移至公共接口  IPowerMgtQueryPubService
	 * 原因： 权限拆分
	 * wqh 2016/12/26
	 *****************************************************/
    /*@Override
    public Map<String, List<MenuRegisterVO>> queryLevelMenuMapByUser(String userID) {
        return this.queryMenuMapByCond(userID, " ( ma.funtype = " + FuncConst.MENUTYPE_FIRSTLEVEL + " or ma.funtype = " + FuncConst.MENUTYPE_MULTILEVEL + ")",
                true);
    }*/

    /******************************************************
	 * 已移至公共接口  IPowerMgtQueryPubService
	 * 原因： 权限拆分
	 * wqh 2016/12/26
	 *****************************************************/
    /*@Override
    public List<PowerMgtVO> queryPowerMgtByRoleId(String roleID, Condition condition, Sorter sort) {
        condition.addExpression("m.pk_role", roleID);
        return powerMgtDao.queryMenuRoleListByRoleId(condition, null);
    }*/

    @Override
    public Map queryPowerMgtMapByRoleId(String roleID) {
        MapListResultHandler handler = new MapListResultHandler("parentid");
        String addCond = "mp.pk_role = '" + roleID + "'";
        powerMgtDao.queryMenuMapByCondForPower(addCond, handler);
        return handler.getResult();
    }

    /*@Override
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
    }*/

    
    // ①
    /******************************************************
	 * 已移至公共接口  IPowerMgtQueryPubService
	 * 原因： 权限拆分
	 * wqh 2016/12/26
	 *****************************************************/
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

    // ②
    /******************************************************
	 * 已移至公共接口  IPowerMgtQueryPubService
	 * 原因： 权限拆分
	 * wqh 2016/12/26
	 *****************************************************/
    /* @Override
    public Page<PagePowerVO> queryPageRoleByPageId(String pageID, String userID, Condition cond, Pageable page) {
        if (StringUtils.isEmpty(pageID))
            return null;
        // 登录用户有权限的机构的角色，不含待分配用户已经关联的角色
        UserVO userVO = userPubService.load(userID);
        //String addCond = "( p.resource_id ='" + pageID + "') ";
        cond.addExpression("p.resource_id", pageID);
        // 根据用户类型设置条件查询
        if (userVO != null && userVO.getUsertype() != GrantConst.USERTYPE_ADMIN) {
            // 用户有管理权限的机构用户
            //addCond += " and " + GrantUtil.getOrgPowerCondByUser(userID, "r.pk_org");
            cond.addExpression("r.pk_org", GrantUtils.getOrgPowerStrByUser(userID), "in");
        }
        Page<PagePowerVO> queryPageRoleByAddCond = powerMgtDao.queryPageRoleByAddCond(cond, page);
        List<PagePowerVO> PagePowerVOContent = queryPageRoleByAddCond.getContent();
        List<String> list = new ArrayList<String>();
        if (PagePowerVOContent != null && PagePowerVOContent.size() > 0) {
            for (PagePowerVO pagePowerVO : PagePowerVOContent) {
                list.add(pagePowerVO.getRoleVO().getPk_org());
            }
        }
        if (list.size() > 0) {
            List<OrgVO> queryOrgListByIDs = orgQueryPubService.queryOrgListByIDs(list);
            for (PagePowerVO pagePowerVO : PagePowerVOContent) {
                for (OrgVO orgVO : queryOrgListByIDs) {
                    if (pagePowerVO.getRoleVO().getPk_org().equals(orgVO.getId())) {
                        pagePowerVO.getRoleVO().setOrgname(orgVO.getOrgname());
                        break;
                    }
                }
            }
        }
        return queryPageRoleByAddCond;
    }*/
    
    /******************************************************
	 * 已移至公共接口  IPowerMgtOptPubService
	 * 原因： 权限拆分
	 * wqh 2016/12/26
	 *****************************************************/
    /* @Override
    public void setMenuIsChecked(Map userMenuMap, Map roleMenuMap) {
        if (roleMenuMap != null && userMenuMap != null && userMenuMap.size() > 0) {
            Set roleMenuKeySet = roleMenuMap.keySet();
            Set userMenuKeySet = userMenuMap.keySet();
            Iterator roleIter = roleMenuKeySet.iterator();
            Iterator userIter = userMenuKeySet.iterator();
            while (roleIter.hasNext()) {
                String roleKey = (String) roleIter.next();
                if (roleKey.equals("__null_key__")) {
                    List<MenuRegisterVO> roleMenuListNullKey = (List) roleMenuMap.get("__null_key__");
                    List<MenuRegisterVO> userMenuListNullKey = (List) userMenuMap.get("__null_key__");
                    if (roleMenuListNullKey != null && roleMenuListNullKey.size() > 0) {
                        for (MenuRegisterVO user : userMenuListNullKey) {
                            if (userMenuListNullKey != null && userMenuListNullKey.size() > 0) {
                                for (MenuRegisterVO role : roleMenuListNullKey) {
                                    if (role.getId().equals(user.getId())) {
                                        user.setIsChecked("Y");
                                    }
                                }
                            }
                        }
                    }
                } else {
                    List<MenuRegisterVO> roleMenuListParentId = (List) roleMenuMap.get(roleKey);
                    List<MenuRegisterVO> userMenuListNullKey = (List) userMenuMap.get(roleKey);
                    if (userMenuListNullKey != null && userMenuListNullKey.size() > 0) {
                        for (MenuRegisterVO user : userMenuListNullKey) {
                            if (userMenuListNullKey != null && userMenuListNullKey.size() > 0) {
                                if (roleMenuListParentId != null && roleMenuListParentId.size() > 0) {
                                    for (MenuRegisterVO role : roleMenuListParentId) {
                                        if (role.getId().equals(user.getId())) {
                                            user.setIsChecked("Y");
                                        }
                                    }
                                }
                            }
                        }
                    }
                } //end else
            } //end while
        } // end if
    }*/

    
    // ③&④
    /******************************************************
	 * 已移至公共接口  IPowerMgtOptPubService
	 * 原因： 权限拆分
	 * wqh 2016/12/26
	 *****************************************************/
    /*@Override
    public Page<RoleVO> queryRoleForPagePower(String pageID, Condition condition, String loginUserID, Pageable page) {
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
        Page<RoleVO> proleVOs = roleDao.queryRoleByAddCond(condition, addCond, page);
        //角色赋予orgname
        List<RoleVO> roleVOs = proleVOs.getContent();
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
        return proleVOs;
    }*/

    // ⑤&⑥
    /******************************************************
	 * 已移至公共接口  IPowerMgtOptPubService
	 * 原因： 权限拆分
	 * wqh 2016/12/26
	 *****************************************************/
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
    }
    */

    @Transactional
    @OpLog
    @Override
    public void addUserRoleOnPage(String pageID, int objtype, String... roleIDs) {
        PagePowerVO powerVO;
        for (String roleID : roleIDs) {
            powerVO = new PagePowerVO();
            powerVO.setPk_role(roleID);
            powerVO.setResource_id(pageID);
            powerVO.setObjtype(objtype);
            powerMgtDao.insertPagePower(powerVO);
        }
    }

    /******************************************************
	 * 已移至公共接口  IPowerMgtOptPubService
	 * 原因： 权限拆分
	 * wqh 2016/12/26
	 *****************************************************/
    /*@Override
    public Map queryMenuRecursive(List<String> menuIdList) {
        if (menuIdList == null) {
            return null;
        }
        Set idSet = new HashSet();
        for (String menuId : menuIdList) {
            this.queryMenuParentId(menuId, idSet);
        }
        return menuService.queryMenuMapByIds(idSet);
    }*/

    /******************************************************
	 * 已移至公共接口  IPowerMgtOptPubService
	 * 原因： 权限拆分
	 * wqh 2016/12/26
	 *****************************************************/
    /*public Set<String> queryMenuParentId(String menuId, Set<String> set) {
        if (StringUtils.isNotBlank(menuId)) {
            set.add(menuId);
            menuId = menuService.queryMenuParentId(menuId);
            queryMenuParentId(menuId, set);
        }
        return set;
    }*/

    @Override
    public boolean isHavePowerForPage(String pageID, String userID) {
        // 判定用户是否拥有菜单页面权限
        if (powerMgtDao.existPowerMenu(pageID, userID) == 0) {
            // 如果没有，判定用户 是否拥有页面权限
            return powerMgtDao.existPowerPage(pageID, userID) != 0;
        }
        return true;
    }

    @OpLog
    @Override
    public void insertMenuPower(PowerMgtVO powerMgtVO) {
        powerMgtDao.insert(powerMgtVO);
    }
    
    /******************************************************
	 * 已移至公共接口  IPowerMgtOptPubService
	 * 原因： 权限拆分
	 * wqh 2016/12/26
	 *****************************************************/
    /*@Transactional
    @Override
    public Object addPowerMgtOnUser(List<MenuRegisterVO> userMenuList, String roleId, String menuIds,String checkedMenuIds) {
        if (StringUtils.isEmpty(roleId))
            return false;
        
        String[] menuIDs = menuIds.split(",");
        String[] checkedMenuIDs = checkedMenuIds.split(",");
        //如果修改后没有选中菜单，则把角色与菜单关系全部删除
        if ((ArrayUtils.isEmpty(menuIDs) || StringUtils.isEmpty(menuIDs[0])) && (ArrayUtils.isNotEmpty(checkedMenuIDs) && StringUtils.isNotEmpty(checkedMenuIDs[0]))) {
            // 删除已有的关联		
        	powerMgtDao.deleteUserMenuPower(userMenuList, roleId);
            return true;
        }
        
        //如果修改前没有机构与菜单关系,则增加修改够选择的所有机构与角色关系
        if(ArrayUtils.isNotEmpty(menuIDs) && StringUtils.isNotEmpty(menuIDs[0]) && (ArrayUtils.isEmpty(checkedMenuIDs) || StringUtils.isEmpty(checkedMenuIDs[0]))){
            //处理菜单ID，只插入最后一级借点，类型为，234.（管理菜单，业务菜单，页面菜单）
            List<String> queryMenuIdsByType345 = menuService.queryMenuIdsByType234(menuIDs);
            List<PowerMgtVO> list = new ArrayList<>();
            if (queryMenuIdsByType345 != null) {
                for (String menuID : queryMenuIdsByType345) {
                	PowerMgtVO powerMgtVO = new PowerMgtVO();
                    powerMgtVO.setPk_role(roleId);
                    powerMgtVO.setResource_id(menuID);
                    list.add(powerMgtVO);
                }
            }
        	powerMgtDao.insertBatch(list);
            return true;
        }
        
        //修改后相比修改前新增的机构
        String addIds = "";
        //修改后相比修改前减少的机构
        String delIds = "";
        for(String menuID : menuIDs){
        	boolean isExists = false;
        	for(String checkedMenuID : checkedMenuIDs){
        		if(menuID.equals(checkedMenuID)){
        			isExists = true;
        			break;
        		}
        	}
        	if(!isExists){
        		addIds += menuID + ",";
        	}
        }
        for(String checkedMenuID : checkedMenuIDs){
        	boolean isExists = false;
        	for(String menuID : menuIDs){
        		if(checkedMenuID.equals(menuID)){
        			isExists = true;
        			break;
        		}
        	}
        	if(!isExists){
        		delIds += checkedMenuID + ",";
        	}
        }
        String[] addIDs = addIds.split(",");
        String[] deleteIDs = delIds.split(",");
        if (!ArrayUtils.isEmpty(deleteIDs) && !StringUtils.isEmpty(deleteIDs[0])) {
            //删除减少的菜单与角色关联		
        	powerMgtDao.deleteByRoleIdAndMenuIds(roleId, deleteIDs);
        }
        if(!ArrayUtils.isEmpty(addIDs) && !StringUtils.isEmpty(addIDs[0])){
        	//处理菜单ID，只插入最后一级借点，类型为，234.（管理菜单，业务菜单，页面菜单）
            List<String> queryAddMenuIdsByType345 = menuService.queryMenuIdsByType234(addIDs);
        	List<PowerMgtVO> addList = new ArrayList<PowerMgtVO>();
        	if (queryAddMenuIdsByType345 != null) {
	            for (String menuID : queryAddMenuIdsByType345) {
	            	PowerMgtVO powerMgtVO = new PowerMgtVO();
	                powerMgtVO.setPk_role(roleId);
	                powerMgtVO.setResource_id(menuID);
	                addList.add(powerMgtVO);
	            }
        	}
            //新增增加的机构与角色关联
            powerMgtDao.insertBatch(addList);
        }
        return true;
    }*/

    /******************************************************
   	 * 已移至公共接口  IPowerMgtQueryPubService
   	 * 原因： 权限拆分
   	 * wqh 2016/12/26
   	 *****************************************************/
    /**
     * 根据菜单Url查询是否拥有菜单权限
     * @param pageID 页面id
     * @param userID 用户ID
     */
    /*@Override
    public boolean isHavePowerForMenuByUrl(String menuUrl, String userID) {
        int num = powerMgtDao.existPowerForMenuByUrl(menuUrl, userID);
        if (num > 0) {
            return true;
        } else {
            return false;
        }
    }*/

    /*@Override
    public Map<String, List<MenuRegisterVO>> queryAllLevelMenuListByUser(String userID) {
        return this.queryMenuMapByCond(userID, null, false);
    }*/
    
    
    /******************************************************
   	 * 已移至公共接口  IPowerMgtQueryPubService
   	 * 原因： 权限拆分
   	 * wqh 2016/12/26
   	 *****************************************************/
    /*@Override
    public List<PowerMgtVO> isConnectToRole(String id) {
        return powerMgtDao.isConnectToRole(id);
    }*/
    
}
