package com.gdsp.platform.grant.role.web;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gdsp.dev.base.exceptions.BusinessException;
import com.gdsp.dev.base.lang.DBoolean;
import com.gdsp.dev.base.utils.JsonUtils;
import com.gdsp.dev.core.common.AppContext;
import com.gdsp.dev.core.model.query.Condition;
import com.gdsp.dev.core.model.query.IExpression;
import com.gdsp.dev.core.model.query.ValueExpression;
import com.gdsp.dev.web.mvc.ext.ViewWrapper;
import com.gdsp.dev.web.mvc.model.AjaxResult;
import com.gdsp.platform.config.customization.helper.SystemConfigConst;
import com.gdsp.platform.config.customization.model.GrantAgingVO;
import com.gdsp.platform.config.customization.service.ISystemConfExtService;
import com.gdsp.platform.config.global.service.IParamService;
import com.gdsp.platform.func.model.MenuRegisterVO;
import com.gdsp.platform.grant.auth.model.OrgPowerVO;
import com.gdsp.platform.grant.auth.model.PowerMgtVO;
import com.gdsp.platform.grant.auth.service.IOrgPowerOptPubService;
import com.gdsp.platform.grant.auth.service.IOrgPowerQueryPubService;
import com.gdsp.platform.grant.auth.service.IPowerMgtOptPubService;
import com.gdsp.platform.grant.auth.service.IPowerMgtQueryPubService;
import com.gdsp.platform.grant.auth.service.IUserRoleOptPubService;
import com.gdsp.platform.grant.auth.service.IUserRoleQueryPubService;
import com.gdsp.platform.grant.org.model.OrgVO;
import com.gdsp.platform.grant.org.service.IOrgQueryPubService;
import com.gdsp.platform.grant.role.model.RoleVO;
import com.gdsp.platform.grant.role.service.IRoleOptPubService;
import com.gdsp.platform.grant.role.service.IRoleQueryPubService;
import com.gdsp.platform.grant.role.service.IRoleService;
import com.gdsp.platform.grant.user.model.UserVO;
import com.gdsp.platform.grant.utils.GrantConst;
import com.gdsp.platform.grant.utils.GrantUtils;

/**
 * 角色管理
 * Created on 2015/6/29
 */
@Controller
@RequestMapping("grant/role")
public class RoleController {

    @Autowired
    private IRoleService             roleService;
    @Autowired
    private IRoleQueryPubService     roleQueryPubService;
    @Autowired
    private IRoleOptPubService       roleOptPubService;
    @Autowired
    private IOrgQueryPubService      orgQueryPubService;
    @Autowired
    private IOrgPowerQueryPubService orgPowerPubService;
    @Autowired
    private IOrgPowerOptPubService   orgPowerOptPubService;
    @Autowired
    private IUserRoleQueryPubService userRoleQueryPubService;
    @Autowired
    private IUserRoleOptPubService   userRoleOptPubService;
    @Autowired
    private IPowerMgtOptPubService   powerMgtOptPubService;
    @Autowired
    private IPowerMgtQueryPubService powerMgtQueryPubService;
    @Autowired
    private IParamService            paramService;
    @Autowired
    private ISystemConfExtService    systemConfExtService ;
    
    /** 角色列表 - 初始加载 */
    @RequestMapping("/list.d")
    public String list(Model model, String pk_org, Pageable pageable) {
        List<RoleVO> roles = new ArrayList<RoleVO>();
        model.addAttribute("roles", roles);
//        String loginUserID = AppContext.getContext().getContextUserId();
//        List<OrgVO> orgVOs = orgPowerPubService.queryOrgListByUser(loginUserID);
//        OrgVO orgVO = orgVOs.get(0);
//        model.addAttribute("selOrgID", orgVO.getId());
//        model.addAttribute("nodes", orgVOs);
        Page<RoleVO> rolePage = new PageImpl<RoleVO>(roles, pageable, roles.size());
        model.addAttribute("rolePage", rolePage);
        return "grant/role/list";
    }

    @RequestMapping("/orgTree.d")
    @ResponseBody
    public Object userOrgTree(){
    	List<OrgVO> orgVOs = orgPowerPubService.queryOrgListByUser(AppContext.getContext().getContextUserId());
    	Map<String,String> nodeAttr = new HashMap<String,String>();
    	nodeAttr.put("text", "orgname");
    	nodeAttr.put("selected", "true");
    	if(orgVOs.size() != 0){
    		return JsonUtils.formatList2TreeViewJson(orgVOs, nodeAttr);
    	}else{
    		return "";
    	}
    }
    /** 角色列表 - 条件查询 */
    @RequestMapping("/listData.d")
    @ViewWrapper(wrapped = false, onlyAjax = true)
    public String listData(Model model,RoleVO cond, Condition condition, Pageable pageable) {
        if (StringUtils.isEmpty(cond.getPk_org()))
            throw new BusinessException("请选择角色所在机构！");
        //解析条件
        List<IExpression> expressions = condition.getExpressions();
        //页面条件放入VO，还有一个页面传来的Pk_org
        if(expressions !=null && expressions.size() > 0){
        	for (int i = 0; i < expressions.size(); i++) {
        		ValueExpression expression = (ValueExpression)expressions.get(i);
        		if(expression.getPropertyName().equals("rolename")){
        			cond.setRolename((String)expression.getValue());
        		}
        		if(expression.getPropertyName().equals("memo")){
        			cond.setMemo((String)expression.getValue());
        		}
			}
        }
        // 查询机构下的角色
        List<RoleVO> roles = roleQueryPubService.queryRoleListByOrgId(cond,true);
        model.addAttribute("selOrgID", cond.getPk_org());
        //利用工具类假分页 lyf 2016.12.28
        Page<RoleVO> rolePage = GrantUtils.convertListToPage(roles, pageable);
        model.addAttribute("rolePage", rolePage);
        return "grant/role/list-data";
    }
    
    /**
     * 打开新建角色页面
     * @since wqh 2016/12/07 增加时效控制 
     * @param model
     * @param pk_org
     * @return
     */
    @RequestMapping("/add.d")
    @ViewWrapper(wrapped = false, onlyAjax = false)
    public String add(Model model, String pk_org) {
        if (StringUtils.isEmpty(pk_org)) {
            throw new BusinessException("请先选择机构，然后添加角色。");
        }
        // 权限时效相关 - 添加角色时是否限制时效
        DBoolean agingStatus = DBoolean.valueOf(systemConfExtService.queryGrantAgingConfigs().getStatus());
        model.addAttribute("agingStatus", agingStatus);
        if("Y".equalsIgnoreCase(agingStatus.toString())){
            GrantAgingVO agingVO = systemConfExtService.queryGrantAgingConfigs();
            model.addAttribute("agingVO", agingVO);//系统级权限时效相关
            String unitVal = "2";//系统级权限时效默认值单位为天
            model.addAttribute("unitVal", unitVal);
        }
        
        model.addAttribute("pk_org", pk_org);
        model.addAttribute("orgname", orgQueryPubService.load(pk_org).getOrgname());
        model.addAttribute("editType", "add");
        
        return "grant/role/form";
    }

    /**
     * 打开修改角色页面
     * @since wqh 2016/12/07 增加时效控制 
     * @param model
     * @param id
     * @return
     */
    @RequestMapping("/edit.d")
    @ViewWrapper(wrapped = false, onlyAjax = true)
    public String edit(Model model, String id) {
    	// 权限时效相关
        DBoolean agingStatus = DBoolean.valueOf(systemConfExtService.queryGrantAgingConfigs().getStatus());
        model.addAttribute("agingStatus", agingStatus);
        if("Y".equalsIgnoreCase(agingStatus.toString())){
            GrantAgingVO agingVO = systemConfExtService.queryGrantAgingConfigs();
            model.addAttribute("agingVO", agingVO);//系统级权限时效相关
            String unitVal = "2";//系统级权限时效默认值单位为天
            model.addAttribute("unitVal", unitVal);
        }
    	DBoolean roleAging = DBoolean.valueOf(roleQueryPubService.load(id).getAgingLimit());
    	model.addAttribute("isAgingRole", roleAging); 
		// 查询该角色下是否关联用户
    	List<UserVO> usersOnRole = userRoleQueryPubService.queryUserListByRoleId(id );
    	List<String> userIds = new ArrayList<String>();
    	for (UserVO user: usersOnRole) {
    		userIds.add(user.getId());
		}
        model.addAttribute("associateUsers",userIds.toString().replace("[", "").replace("]","").replace(" ", ""));
        model.addAttribute("editType", "edit");
        model.addAttribute("role", roleQueryPubService.load(id));
        return "grant/role/form";
    }

    /** 保存角色 */
    @RequestMapping("/save.d")
    @ResponseBody
    public Object save(RoleVO role) {
        if (StringUtils.isEmpty(role.getPk_org())) {
        	throw new BusinessException("请先选择机构，然后添加角色。");
        }
        if(role.getAgingLimit() == null||role.getAgingLimit().equalsIgnoreCase("N")){
            role.setAgingUnit(null);
            role.setPermissionAging(null);
        }
        if (StringUtils.isNotEmpty(role.getId())) {
            roleOptPubService.update(role);
            
        } else {
        	roleOptPubService.insert(role);
        }
        return AjaxResult.SUCCESS;
    }

    /** 删除角色 */
    @RequestMapping("/delete.d")
    @ResponseBody
    public Object delete(String... id) {
//        if (id != null && id.length > 0) {
//            for(String roleId : id){
//                List<UserVO> userList = userRoleQueryPubService.queryUsersByRoleId(roleId, "");
//            }
//        	roleOptPubService.deleteRole(id);
//        }
        List<UserVO> userList = null;
        if (id != null && id.length == 1) {
            userList = userRoleQueryPubService.queryUserListByRoleId(id[0] );
            if(userList != null && userList.size() > 0){
                return new AjaxResult(AjaxResult.STATUS_ERROR, "删除失败，该角色下有关联的用户，请先清空用户！");
            } else {
                roleOptPubService.deleteRole(id);
                return new AjaxResult(AjaxResult.STATUS_SUCCESS,"删除成功！");
            }
        } else {
            for(String roleId : id){
                userList = userRoleQueryPubService.queryUserListByRoleId(roleId );
                if(userList != null && userList.size() > 0){
                    break; 
                } else {
                    roleOptPubService.deleteRole(id);
                    return new AjaxResult(AjaxResult.STATUS_SUCCESS,"删除成功！");
                }
            }
            return new AjaxResult(AjaxResult.STATUS_ERROR, "删除失败，含有已关联用户的角色，请先清空角色下的用户！");
        }
    }

    /**
     * 向用户添加角色  // TODO 为什么会出现在角色管理中？
     * @param userId
     * @param roleIDs
     */
    /*
    @RequestMapping("/saveUserRoles.d")
    public Object saveUserRoles(String userId, String... roleIDs) {
    	userRoleOptPubService.addRolesToUser(userId, roleIDs);
        return AjaxResult.SUCCESS;
    }
    */
    
    /**
     * 初始化加载角色授权页面
     * @param model
     * @param roleID 角色ID
     * @param orgID	机构ID
     * @param pageable
     * @return String    返回已授权用户列表页面
     */
    @RequestMapping("/editRolePower.d")
    @ViewWrapper(wrapped = false, onlyAjax = true)
    public String editRoleUser(Model model, String roleID, String orgID, Pageable pageable) {
        String loginUserID = AppContext.getContext().getContextUserId();
        //查询机构树。条件为机构ID。
        /**lyf 2016.12.22修改 权限拆分start**/
        List<OrgVO> orgListById = orgQueryPubService.querySelftAndChildOrgListById(orgID);
        /***end*/
//        List<OrgVO> orgListById = orgQueryPubService.queryChildOrgListById(orgID);
        //查询机构角色关系。条件为角色ID。
        List<OrgPowerVO> orgPowerlist = orgPowerPubService.queryOrgPowerListByRole(roleID);
        //遍历设置是否选中。
        //选中的id字符串
        String checkedOrgIds = "";
        if (orgPowerlist != null && orgPowerlist.size() > 0) {
            for (OrgPowerVO orgPowerVO : orgPowerlist) {
                if (orgListById != null && orgListById.size() > 0) {
                    for (OrgVO orgVO : orgListById) {
                        if (orgVO.getId().equals(orgPowerVO.getResource_id())) {
                            orgVO.setIsChecked("Y");
                            checkedOrgIds += orgVO.getId() + ",";
                        }
                    }
                }
            }
        }
        model.addAttribute("checkedOrgIds", checkedOrgIds);
        model.addAttribute("orgNodes", orgListById);
        //查询登陆用户有权限的菜单
        Map<String, List<MenuRegisterVO>>  menuMap = powerMgtQueryPubService.queryMenuMapByUser(loginUserID, true);
        //角色有权限操作的菜单
        List<PowerMgtVO> queryPowerMgtByRoleId = powerMgtQueryPubService.queryPowerMgtByRoleId(roleID);
        
        List<String> menuIdList = null;
        String checkedMenuIds = "";
        if (queryPowerMgtByRoleId != null && queryPowerMgtByRoleId.size() > 0) {
            menuIdList = new ArrayList<String>();
            for (PowerMgtVO powerMgtVO : queryPowerMgtByRoleId) {
                menuIdList.add(powerMgtVO.getResource_id());
                checkedMenuIds += powerMgtVO.getResource_id() + ",";
            }
        }
        Map roleMenuMaps = powerMgtQueryPubService.queryMenuRecursive(menuIdList);
        //设置是否选中。
        powerMgtOptPubService.setMenuIsChecked(menuMap, roleMenuMaps);
        model.addAttribute("checkedMenuIds", checkedMenuIds);
        model.addAttribute("menuNodes", menuMap);
        model.addAttribute("isshowsafelevel", (DBoolean) paramService.queryParamValue(GrantConst.PARAM_ISSHOWSAFELEVEL));
        
        /** 更改方法调用(原因：加入权限时效后原处理逻辑不再适用)  wqh 2016/12/13  */
        // 查询角色关联的用户 - 初始化查询，无search条件
        List<UserVO> roleUsers = userRoleQueryPubService.queryUserListByRoleId(roleID );
        model.addAttribute("roleUsers", GrantUtils.convertListToPage(roleUsers, pageable));
//        model.addAttribute("roleUsers", userRoleService.queryUserRoleByRoleId(id, condition, pageable));
        model.addAttribute("roleId", roleID);
        model.addAttribute("orgID", orgID);
        // 权限时效相关
        DBoolean agingStatus = DBoolean.valueOf(systemConfExtService.queryGrantAgingConfigs().getStatus());
        model.addAttribute("agingStatus", agingStatus);
    	DBoolean roleAging = DBoolean.valueOf(roleQueryPubService.load(roleID).getAgingLimit());
    	model.addAttribute("isAgingRole", roleAging); 
    	
        return "grant/role/userlist";
    }

    /** 已授权用户列表数据 */
    @RequestMapping("/reloadUserList.d")
    @ViewWrapper(wrapped = false)
    public String reloadUserRole(Model model, String id, Condition cond, Pageable pageable) {
    	// 权限时效相关
        DBoolean agingStatus = DBoolean.valueOf(systemConfExtService.queryGrantAgingConfigs().getStatus());
        model.addAttribute("agingStatus", agingStatus);
    	DBoolean roleAging = DBoolean.valueOf(roleQueryPubService.load(id).getAgingLimit());
    	model.addAttribute("isAgingRole", roleAging); 
        model.addAttribute("roleId", id);
        
        // 带条件的查询，查询条件为用户名
//        String username = cond.getFreeValue();
        List<UserVO> roleUsers = userRoleQueryPubService.queryUsersByCondition(id,cond );
        model.addAttribute("roleUsers", GrantUtils.convertListToPage(roleUsers, pageable));
//        model.addAttribute("roleUsers", userRoleQueryPubService.queryUsersByRoleId(id, username));
        
        return "grant/role/userlist-data";
    }
    
    /*
    @RequestMapping("/reloadOrgList.d")
    @ViewWrapper(wrapped = false)
    public String reloadOrgList(Model model, String id, Condition condition, Pageable pageable) {
        model.addAttribute("id", id);
        condition.addExpression("ro.pk_role", id);
        model.addAttribute("roleOrgs", orgPowerService.queryOrgRoleByRoleId(id, condition, pageable, null));
        return "grant/role/orglist-data";
    }
     */
    
   /**
    * 到添加关联用户页面
    * @param model	
    * @param id 角色ID
    * @param condition
    * @return String    未关联的用户
    */
    @RequestMapping("/toAddUserHome.d")
    @ViewWrapper(wrapped = false, onlyAjax = true)
    public String toAddUserHome(Model model, String id, Condition cond, Pageable pageable) {
        String username = cond.getFreeValue();
        String loginUserID = AppContext.getContext().getContextUserId();
        model.addAttribute("roleID", id);
        RoleVO rolevo = roleQueryPubService.load(id);
        if(rolevo.getAgingLimit() != null &&rolevo.getAgingLimit().equalsIgnoreCase("Y")){
            String permissionAging = rolevo.getPermissionAging();
            String agingUnit = rolevo.getAgingUnit();
            model.addAttribute("permissionAging", permissionAging);
            model.addAttribute("agingUnit", agingUnit);
        }
        // 权限时效相关
        DBoolean agingStatus = DBoolean.valueOf(systemConfExtService.queryGrantAgingConfigs().getStatus());
        model.addAttribute("agingStatus", agingStatus);
    	DBoolean roleAging = DBoolean.valueOf(roleQueryPubService.load(id).getAgingLimit());
    	model.addAttribute("isAgingRole", roleAging); 
        
    	List<UserVO> users = userRoleQueryPubService.queryRoleUnauthorizedUsers(id, loginUserID, username);
        model.addAttribute("users", GrantUtils.convertListToPage(users, pageable));
        return "grant/role/userlist-add";
    }

   /**
    * 未关联用户分页数据
    * @param model
    * @param id 角色ID
    * @param condition
    * @param pageable
    * @return    参数说明
    * @return String    返回页面
    * @throws
    */
    @RequestMapping("/userlist-add.d")
    @ViewWrapper(wrapped = false, onlyAjax = true)
    public String toAddUserHomeList(Model model, String id, Condition cond,Pageable pageable) {
//        String username = cond.getFreeValue();
        String loginUserID = AppContext.getContext().getContextUserId();
        model.addAttribute("id", id);
        
        List<UserVO> users = userRoleQueryPubService.queryRoleUnauthorizedUsersByCondition(id, loginUserID, cond);
        model.addAttribute("users", GrantUtils.convertListToPage(users, pageable));
        // 权限时效相关
        DBoolean agingStatus = DBoolean.valueOf(systemConfExtService.queryGrantAgingConfigs().getStatus());
        model.addAttribute("agingStatus", agingStatus);
    	DBoolean roleAging = DBoolean.valueOf(roleQueryPubService.load(id).getAgingLimit());
    	model.addAttribute("isAgingRole", roleAging); 
        
        return "grant/role/userlist-add-data";
    }

    /** 角色关联用户 - 添加用户 */
    @RequestMapping("/addUserToRole.d")
    @ResponseBody
    public Object addUserToRole(String roleId, String tdate, String permissionAging, String agingUnit, String... ids) {
    	RoleVO rolevo = roleQueryPubService.load(roleId);
    	String agingLimit = rolevo.getAgingLimit();
    	String status = systemConfExtService.queryGrantAgingConfigs().getStatus();
    	if("Y".equalsIgnoreCase(agingLimit)&&"Y".equalsIgnoreCase(status)){
    		Date nowDate = new Date();
    		String newDate = null;
       	 	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            if(SystemConfigConst.GRANT_AGING_UNIT_DAY.equals(agingUnit)){
                Calendar ca = Calendar.getInstance();
                int permissionTime = Integer.parseInt(permissionAging);
                ca.add(Calendar.DATE, permissionTime);
                nowDate = ca.getTime();
                String date1 = format.format(nowDate);
                newDate = date1.substring(0, 11)+"23:59:59";
            }else if(SystemConfigConst.GRANT_AGING_UNIT_HOUR.equals(agingUnit)){
                Calendar ca = Calendar.getInstance();
                int permissionTime = Integer.parseInt(permissionAging);
                ca.add(Calendar.HOUR, permissionTime);
                nowDate = ca.getTime();
                String date1 = format.format(nowDate);
                newDate = date1.substring(0, 13)+":00:00";
            }
        	userRoleOptPubService.addUserRoleOnRole(roleId,ids,newDate);
        	
            return new AjaxResult(AjaxResult.STATUS_SUCCESS,"请到用户角色关联界面设置时效，权限时效默认为:"+newDate);
    	}else{
    		userRoleOptPubService.addUserRoleOnRole(roleId,ids,null);
    		return new AjaxResult(AjaxResult.STATUS_SUCCESS,"操作成功！");
    	}
    }

    /** 角色关联用户 - 删除关联用户 */
    @RequestMapping("/deleteUserRoles.d")
    @ResponseBody
    public Object deleteUserRoles(String roleId, String... id) {
    	userRoleOptPubService.deleteUserRoles(roleId, id);
        return AjaxResult.SUCCESS;
    }

    
    /**
     * 角色关联机构
     * @param model
     * @param roleId
     * @param orgIds
     * @param orgID
     * @param checkedOrgIds
     * @return
     * @author wqh
     * @since 2016年12月29日
     */
    @RequestMapping("/addOrgToRole.d")
    @ResponseBody
    @ViewWrapper(wrapped = false, onlyAjax = true)
    public Object addOrgToRole(Model model, String roleId, String orgIds, String orgID,String checkedOrgIds) {
        if (StringUtils.isBlank(orgIds)) {
            orgIds = "";
        }
        if(StringUtils.isBlank(checkedOrgIds)){
        	checkedOrgIds = "";
        }
        String[] orgIDs = orgIds.split(",");
        String[] checkedOrgIDs = checkedOrgIds.split(",");
        
        //保存角色机构关系，并返回关系List
        orgPowerOptPubService.addOrgPowerOnRole(roleId, orgIDs,checkedOrgIDs);
        return new AjaxResult(AjaxResult.STATUS_SUCCESS,"保存成功！");
    }

    /**
     * 给角色添加菜单权限
     * @param model 
     * @param roleID 当前操作的角色ID
     * @param selectedMenuIds 页面选中的菜单IDs
     * @param exsitingMenuIds 操作的角色现有的菜单IDs
     * @return 
     */
    @RequestMapping("/addMenuToRole.d")
    @ResponseBody
    public Object addMenuToRole(Model model, String roleID, String selectedMenuIds, String exsitingMenuIds) {
        if (StringUtils.isBlank(selectedMenuIds)) {
        	selectedMenuIds = "";
        }
        if(StringUtils.isBlank(exsitingMenuIds)){
        	exsitingMenuIds = "";
        }
        String loginUserID = AppContext.getContext().getContextUserId();
        // 查询当前登录用户的菜单权限
        List<MenuRegisterVO> userMenuList = powerMgtQueryPubService.queryMenuForRolePower(roleID, loginUserID);
        // 保存菜单权限到角色
        powerMgtOptPubService.addPowerMgtOnRole(userMenuList, roleID, selectedMenuIds,exsitingMenuIds);
        return AjaxResult.SUCCESS;
    }

//    @RequestMapping("/addMenuToRole.d")
//    public String toOrgRoleTreePage(){
//    	
//    }
    /**
     * 
     * @param id
     * @return
     * @author wqh
     * @since 2016年12月29日
     */
    @RequestMapping("/deleteOrgOnRoles.d")
    @ResponseBody
    public Object deleteOrgOnRoles(String... id) {
    	orgPowerOptPubService.deleteOrgPower(id);
        return AjaxResult.SUCCESS;
    }

    /*@RequestMapping("/test.d")
    public Object test(Model model, String id, String orgID, Pageable pageable) {
        //查询机构树
        orgID = "1";
        id = "ae9eb53158d74103863c1a659df54ac7";
        *//**lyf 2016.12.23修改 权限拆分start**//*
        List<OrgVO> orgListById = orgQueryPubService.queryChildOrgListById(orgID,true);
        *//** end**//*
        Condition orgCon = new Condition();
        Sorter sort = new Sorter(Direction.ASC, "innercode");
        List<OrgVO> orgListById = orgService.queryChildOrgListById(orgID, orgCon, sort, true);
        Condition orgPowerCon = new Condition();
        Page<OrgPowerVO> page = orgPowerService.queryOrgRoleByRoleId(id, orgPowerCon, pageable, null);
        //设置机构选中
        if (page != null) {
            List<OrgPowerVO> orgPowerlist = page.getContent();
            if (orgPowerlist != null && orgPowerlist.size() > 0) {
                for (OrgPowerVO orgPowerVO : orgPowerlist) {
                    if (orgListById != null && orgListById.size() > 0) {
                        for (OrgVO orgVO : orgListById) {
                            if (orgVO.getId().equals(orgPowerVO.getResource_id())) {
                                orgVO.setIsChecked("Y");
                            }
                        }
                    }
                }
            }
        }
        model.addAttribute("orgNodes", orgListById);
        return "grant/role/test";
    }*/

    @RequestMapping("/uniqueCheck.d")
    @ResponseBody
    public Object uniqueCheck(RoleVO role) {
        return roleService.uniqueCheck(role);
    }
    
    
    /**
     * 时效角色设置与用户的关联时效
     * @return
     */
    @RequestMapping("setGrantAging.d")
    public String checkAging(/*String roleID,String... userIDs*/) {
    	
    	return "grant/role/userlist-add-aging";
	}
    
    /**
     * 更新关联关系的时效
     * @return
     */
    @RequestMapping("updateRelationAging.d")
    @ResponseBody
    public Object updateRelationAging(String roleId, String date, String[] ids,String dateType) {
    	
    	userRoleOptPubService.modifyUserAgingOnRole(roleId, ids, date,dateType);
    	return AjaxResult.SUCCESS;
	}
    
    
    /**
     * 打开时效设置弹出层
     * 角色启用时效权限时
     * @return
     */
    @RequestMapping("openRoleAging.d")
    public String openRoleAging(/*String roleID,String... userIDs*/) {
    	return "grant/role/form-aging";
	}
    
    
    /**
     * 设置角色的时效开启状态
     * @return
     */
    @RequestMapping("setRoleAgingStatus.d")
    @ResponseBody
    public Object setRoleAgingStatus(String roleID,String agingLimit) {
    	roleOptPubService.setRoleAgingStatus(roleID, agingLimit);
    	return AjaxResult.SUCCESS;
	}
    
    /**
    * 刷新角色有权限的菜单ids
    * @param roleID
    * @return    参数说明
     */
    @RequestMapping("refreshMenuIDsOfRole.d")
    @ResponseBody
    public Object refreshMenuIDsOfRole(String roleID){
    	//角色有权限操作的菜单
        List<PowerMgtVO> queryPowerMgtByRoleId = powerMgtQueryPubService.queryPowerMgtByRoleId(roleID);
    	String checkedMenuIds = "";
        if (queryPowerMgtByRoleId != null && queryPowerMgtByRoleId.size() > 0) {
            for (PowerMgtVO powerMgtVO : queryPowerMgtByRoleId) {
                checkedMenuIds += powerMgtVO.getResource_id() + ",";
            }
        }
        return checkedMenuIds;
    }
    
}
