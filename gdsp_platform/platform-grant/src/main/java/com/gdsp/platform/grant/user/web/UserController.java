package com.gdsp.platform.grant.user.web;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.gdsp.dev.base.exceptions.BusinessException;
import com.gdsp.dev.base.lang.DBoolean;
import com.gdsp.dev.base.utils.JsonUtils;
import com.gdsp.dev.core.common.AppContext;
import com.gdsp.dev.core.model.query.Condition;
import com.gdsp.dev.core.utils.excel.ExcelHelper;
import com.gdsp.dev.web.mvc.ext.ViewWrapper;
import com.gdsp.dev.web.mvc.model.AjaxResult;
import com.gdsp.dev.web.security.login.AuthInfo;
import com.gdsp.dev.web.security.shiro.EncodePasswordService;
import com.gdsp.platform.config.customization.model.PasswordConfVO;
import com.gdsp.platform.config.customization.service.ISystemConfExtService;
import com.gdsp.platform.config.global.service.IParamService;
import com.gdsp.platform.grant.auth.service.IOrgPowerQueryPubService;
import com.gdsp.platform.grant.auth.service.IUserRoleOptPubService;
import com.gdsp.platform.grant.auth.service.IUserRoleQueryPubService;
import com.gdsp.platform.grant.org.model.OrgVO;
import com.gdsp.platform.grant.org.service.IOrgQueryPubService;
import com.gdsp.platform.grant.role.model.RoleVO;
import com.gdsp.platform.grant.role.service.IRoleQueryPubService;
import com.gdsp.platform.grant.user.model.UserDataIOVO;
import com.gdsp.platform.grant.user.model.UserVO;
import com.gdsp.platform.grant.user.service.IUserOptPubService;
import com.gdsp.platform.grant.user.service.IUserQueryPubService;
import com.gdsp.platform.grant.user.service.IUserService;
import com.gdsp.platform.grant.utils.GrantConst;
import com.gdsp.platform.grant.utils.GrantUtils;

/**
 * Created on 2015/6/29
 */
@Controller
@RequestMapping("grant/user")
public class UserController {

    private static final Logger      logger = LoggerFactory.getLogger(UserController.class);
    @Autowired
    private IUserService             userService;
    @Autowired
    private IUserQueryPubService     userQueryPubService;
    @Autowired
    private IUserOptPubService       userOptPubService;
    @Autowired
    private IOrgPowerQueryPubService orgPowerPubService;
    @Autowired
    private IOrgQueryPubService      orgQueryPubService;
    @Autowired
    private IParamService            paramService;
    @Autowired
    private EncodePasswordService    passwordService;
    @Autowired
    private ISystemConfExtService    systemConfExtService;
    @Autowired
    private IRoleQueryPubService     roleQueryPubService;
    @Autowired
    private IUserRoleOptPubService   iUserRoleOptPubService;
    @Autowired
    private IUserRoleQueryPubService userRoleQueryPubService;

    /**
     * 用户管理主界面
     * @param model
     * @param pk_org
     * @param condition
     * @param pageable
     * @return
     */
    @RequestMapping("/list.d")
    public String list(Model model, String pk_org, Pageable pageable) {
        //机构树
        List<OrgVO> orgVOs = orgPowerPubService.queryOrgListByUser(AppContext.getContext().getContextUserId());
        model.addAttribute("nodes", orgVOs);
        List<UserVO> users = new ArrayList<>();
        Page<UserVO> userPage = new PageImpl<>(users, pageable, users.size());
        model.addAttribute("userPage", userPage);
        Object imptSize = model.asMap().get("imptSize");//获取导入用户数量，用于弹出框提示  lijun 20170504       
        model.addAttribute("imptSize", imptSize);
        //检查是否是管理员登录
        String loginUserID = AppContext.getContext().getContextUserId();
        UserVO userVO = userQueryPubService.load(loginUserID);
        Integer usertype = userVO.getUsertype();
        if(GrantConst.USERTYPE_ADMIN==usertype){
        	model.addAttribute("userType", "admin");
        }
        return "grant/user/list";
    }

    @RequestMapping("/orgTree.d")
    @ResponseBody
    public Object userOrgTree() {
        List<OrgVO> orgVOs = orgPowerPubService.queryOrgListByUser(AppContext.getContext().getContextUserId());
        Map<String, String> nodeAttr = new HashMap<>();
        nodeAttr.put("text", "orgname");
        if (!orgVOs.isEmpty()) {
            return JsonUtils.formatList2TreeViewJson(orgVOs, nodeAttr);
        } else {
            return "";
        }
    }

    /****
     * 刷新界面
     * 
     * @param model
     * @param pk_org
     * @param condition
     * @param pageable
     * @return
     */
    @RequestMapping("/listData.d")
    @ViewWrapper(wrapped = false)
    public String listData(Model model, UserVO userVO, Condition cond, Pageable pageable) {
        if (StringUtils.isEmpty(userVO.getPk_org())) {
            throw new BusinessException("请选择用户所在机构！");
        }
        //        List<IExpression> expressions = cond.getExpressions();
        //        //页面条件放入VO，还有一个页面传来的Pk_org
        //        if(expressions !=null && expressions.size() > 0){
        //        	for (int i = 0; i < expressions.size(); i++) {
        //        		ValueExpression expression = (ValueExpression)expressions.get(i);
        //        		if(expression.getPropertyName().equals("username")){
        //        			userVO.setUsername((String)expression.getValue());
        //        		}
        //        		if(expression.getPropertyName().equals("account")){
        //        			userVO.setAccount((String)expression.getValue());
        //        		}
        //        		if(expression.getPropertyName().equals("sex")){
        //        			userVO.setSex(Integer.valueOf((String)expression.getValue()));
        //        		}
        //			}
        //        }
        //固定条件，查询不是管理员的用户
        userVO.setUsertype(GrantConst.USERTYPE_USER);
        model.addAttribute("orgID", userVO.getPk_org());
        List<UserVO> users = userQueryPubService.queryUsersByCondtion(userVO, AppContext.getContext().getContextUserId(), true, cond);
        //利用工具类重新写假分页  lyf 2016.12.28
        if (users != null) {
            Page<UserVO> userPage = GrantUtils.convertListToPage(users, pageable);
            model.addAttribute("userPage", userPage);
        }
        return "grant/user/list-data";
    }

    /*****
     * 添加用户界面
     * 
     * @param model
     * @param pk_org
     * @return
     */
    @RequestMapping("/add.d")
    @ViewWrapper(wrapped = false, onlyAjax = true)
    public String add(Model model, String pk_org) {
        model.addAttribute("pk_org", pk_org);
        model.addAttribute("orgname", orgQueryPubService.load(pk_org).getOrgname());
        model.addAttribute("editType", "add");
        DBoolean isLimit = (DBoolean) (paramService.queryParamValue(GrantConst.PARAM_ISLIMIT));
        model.addAttribute("isLimit", isLimit);
        return "grant/user/form";
    }

    /***
     * 编辑用户界面
     * 
     * @param model
     * @param id
     * @return
     */
    @RequestMapping("/edit.d")
    @ViewWrapper(wrapped = false, onlyAjax = true)
    public String edit(Model model, String id) {
        model.addAttribute("editType", "edit");
        model.addAttribute("user", userQueryPubService.load(id));
        DBoolean isLimit = (DBoolean) (paramService.queryParamValue(GrantConst.PARAM_ISLIMIT));
        model.addAttribute("isLimit", isLimit);
        return "grant/user/form";
    }

    /**
     * 
     * @Title: save 
     * @Description: 保存用户
     * @param user	用户VO
     * @return Object    返回成功信息
     */
    @RequestMapping("/save.d")
    @ResponseBody
    public Object save(UserVO user) {
        if (user.getIslocked() == null) {
            user.setIslocked("N"); // 默认不锁定
        }
        if (user.getOnlyadmin() == null) {
            user.setOnlyadmin("N"); // 默认可以有管理权限以外的权限
        }
        if (user.getIsreset() == null) {
            user.setIsreset("Y"); // 是否需要重置密码
        }
        if (user.getIsdisabled() == null) {
            user.setIsdisabled("N"); // 默认启用
        }
        // 设置默认值
        user.setUsertype(GrantConst.USERTYPE_USER);
        if (StringUtils.isNotEmpty(user.getId())) {
            userOptPubService.update(user);
        } else {
            // 密码加密
            String password = passwordService.encodePassword(user.getAccount(), user.getUser_password());
            user.setUser_password(password);
            userOptPubService.insert(user);
        }
        return AjaxResult.SUCCESS;
    }

    /***
     * 解锁用户功能(区别于启用)
     * 
     * @param id
     * @return
     */
    @RequestMapping("/unlockUser.d")
    @ResponseBody
    public Object unlockUser(String... id) {
        userOptPubService.unlockUser(id);
        return AjaxResult.SUCCESS;
    }

    /****
     * 删除用户，可多个
     * 
     * @param id
     * @return
     */
    @RequestMapping("/delete.d")
    @ResponseBody
    public Object delete(String id) {
        if (id == null)
            return new AjaxResult(AjaxResult.STATUS_ERROR, "页面出现问题，请刷新页面后再试。");
        String reslut = userOptPubService.deleteUser(id);
        if (StringUtils.isEmpty(reslut)) {
            return new AjaxResult(AjaxResult.STATUS_SUCCESS, "操作成功");
        } else {
            return new AjaxResult(AjaxResult.STATUS_ERROR, "无法删除，用户存在关联业务：" + reslut + "。");
        }
    }

    /***
     * 机构变更界面
     * 
     * @param model
     * @param userId
     * @return
     */
    @RequestMapping("/transOrg.d")
    @ViewWrapper(wrapped = false)
    public String transOrg(Model model, String userId) {
        model.addAttribute("user", userQueryPubService.load(userId));
        return "grant/user/transOrg";
    }

    @RequestMapping("/transOrgTree.d")
    @ViewWrapper(wrapped = false)
    @ResponseBody
    public String transOrgTree(String userId) {
        String loginUserID = AppContext.getContext().getContextUserId();
        List<OrgVO> orgVOs = orgPowerPubService.queryOrgListByUser(loginUserID);
        Map<String, String> nodeAttr = new HashMap<>();
        nodeAttr.put("text", "orgname");
        if (!orgVOs.isEmpty()) {
            return JsonUtils.formatList2TreeViewJson(orgVOs, nodeAttr);
        } else {
            return "";
        }
    }

    /***
     * 机构变更功能
     * 
     * @param user
     * @param transOrg
     * @return
     */
    @RequestMapping("/execTransOrg.d")
    @ResponseBody
    public Object execTransOrg(UserVO user, String transOrg) {
        try {
            userOptPubService.transOrg(user.getId(), transOrg);
        } catch (BusinessException e) {
            logger.error(e.getMessage(), e);
            return new AjaxResult(AjaxResult.STATUS_ERROR, e.getMessage());
        }
        return AjaxResult.SUCCESS;
    }

    /***
     * 修改密码界面
     * 
     * @param model
     * @param userId
     * @return
     */
    @RequestMapping("/resetPassword.d")
    @ViewWrapper(wrapped = false)
    public String resetPassword(Model model, String userId) {
        model.addAttribute("user", userQueryPubService.load(userId));
        return "grant/user/resetPassword";
    }

    /***
     * 修改密码功能
     * 
     * @param user
     * @return
     */
    @RequestMapping("/execResetPassword.d")
    @ResponseBody
    public Object execResetPassword(UserVO user) {
        userOptPubService.resetUserPasssword(user);
        userOptPubService.clearCache(user.getUsername());
        return new AjaxResult(AjaxResult.STATUS_SUCCESS, "密码重置成功");
    }

    // ///////////////////////////////////用户关联角色Start/////////////////////////////////////////////////////
    /**
     * 加载已关联的角色列表(页面)
     * @param model
     * @param userId 选中用户id
     * @param conds 查询条件
     * @return
     */
    @RequestMapping("/loadRoles.d")
    @ViewWrapper(wrapped = false, onlyAjax = true)
    public String impowerToRole(Model model, String userId, Condition conds, Pageable page) {
        List<RoleVO> roleVOs = getGrantedRolesPage(userId, conds);
        //利用工具类重新写假分页  lyf 2016.12.28
        Page<RoleVO> rolevos = GrantUtils.convertListToPage(roleVOs, page);
        // 权限时效相关 - 添加角色时是否限制时效
        DBoolean agingStatus = DBoolean.valueOf(systemConfExtService.queryGrantAgingConfigs().getStatus());
        model.addAttribute("agingStatus", agingStatus);
        List<String> agingRoleId = getAgingRoleId(roleVOs);
        String agingroleId = listToString(agingRoleId);
        model.addAttribute("agingroleId", agingroleId);
        model.addAttribute("userId", userId);
        model.addAttribute("roleVO", rolevos);
        return "grant/user/rolelist";
    }

    /**
     * 加载已关联的角色列表(数据)
     * @param model
     * @param userId 选中用户id
     * @param conds 查询条件
     * @return
     */
    @RequestMapping("/loadRolesData.d")
    @ViewWrapper(wrapped = false)
    public String queryRelateRoleByUser(Model model, String userId, Condition conds, Pageable page) {
        List<RoleVO> roleVOs = getGrantedRolesPage(userId, conds);
        //利用工具类重新写假分页  lyf 2016.12.28
        Page<RoleVO> rolevos = GrantUtils.convertListToPage(roleVOs, page);
        // 权限时效相关 - 添加角色时是否限制时效
        DBoolean agingStatus = DBoolean.valueOf(systemConfExtService.queryGrantAgingConfigs().getStatus());
        model.addAttribute("agingStatus", agingStatus);
        List<String> agingRoleId = getAgingRoleId(roleVOs);
        String agingroleId = listToString(agingRoleId);
        model.addAttribute("agingroleId", agingroleId);
        model.addAttribute("userId", userId);
        model.addAttribute("roleVO", rolevos);
        return "grant/user/rolelist-data";
    }

    /**
     * 根据用户id和角色id删除用户与角色的关联关系
     * @param userId 选中用户id
     * @param id 角色id
     * @return
     */
    @RequestMapping("/deleteRolesOnUser.d")
    @ResponseBody
    @ViewWrapper(wrapped = false, onlyAjax = true)
    public Object doDeleteRoleRelationFromUser(String[] userId, String[] id) {
        for (String ID : id) {
            iUserRoleOptPubService.deleteUserRoles(ID, userId);
        }
        return AjaxResult.SUCCESS;
    }

    /**
     * 添加用户与角色的关联关系(页面)
     * @param model
     * @param id 选中用户id
     * @return
     */
    @RequestMapping("/openRoleListTab.d")
    @ViewWrapper(wrapped = false, onlyAjax = true)
    public String openRenoleListTab(Model model, String userId, Pageable page) {
        List<RoleVO> roleVOs = getNoGrantedRolesPage(userId, null);
        //利用工具类重新写假分页  lyf 2016.12.28
        Page<RoleVO> roleVO = GrantUtils.convertListToPage(roleVOs, page);
        List<String> agingRoleId = getAgingRoleId(roleVOs);
        String agingroleId = listToString(agingRoleId);
        DBoolean agingStatus = DBoolean.valueOf(systemConfExtService.queryGrantAgingConfigs().getStatus());
        model.addAttribute("agingStatus", agingStatus);
        model.addAttribute("agingRoleId", agingroleId);
        model.addAttribute("roleVO", roleVO);
        model.addAttribute("userId", userId);
        return "grant/user/rolelist-add";
    }

    /**
     * 加载未关联的角色列表(数据)
     * @param model
     * @param userId 选中用户id
     * @param conds 查询条件
     * @return
     */
    @RequestMapping("/loadRoleListData.d")
    @ViewWrapper(wrapped = false)
    public String reloadAddRoleList(Model model, String userId, Condition conds, Pageable page) {
        List<RoleVO> roleVOs = getNoGrantedRolesPage(userId, conds);
        //利用工具类重新写假分页  lyf 2016.12.28
        Page<RoleVO> roleVO = GrantUtils.convertListToPage(roleVOs, page);
        List<String> agingRoleId = getAgingRoleId(roleVOs);
        String agingroleId = listToString(agingRoleId);
        DBoolean agingStatus = DBoolean.valueOf(systemConfExtService.queryGrantAgingConfigs().getStatus());
        model.addAttribute("agingStatus", agingStatus);
        model.addAttribute("agingRoleId", agingroleId);
        model.addAttribute("roleVO", roleVO);
        model.addAttribute("userId", userId);
        return "grant/user/rolelist-add-data";
    }

    /**
     * 添加用户与角色的关联关系(页面数据)
     * @param model
     * @param userId 选中用户id
     * @param id 角色id
     * @return
     */
    @RequestMapping("/addRoleOnUser.d")
    @ResponseBody
    @ViewWrapper(wrapped = false, onlyAjax = true)
    public Object doAddRoleRelationToUser(String userId, String id) {
        String[] ids = id.split(",");
        List<String> roleIds = Arrays.asList(ids);
        /** ---- 查询更改  wqh 2016/12/22  原因：权限拆分 -------------------------*/
        // 更改前
        //        List<RoleVO> rolevos = iRoleService.queryRoleListByCondition(condition, sort);
        // 更改后
        List<RoleVO> rolevos = roleQueryPubService.queryRoleListByRoleIds(roleIds);
        /**---------------------- 更改至此结束 ----------------------------------*/
        List<String> agingRoleId = getAgingRoleId(rolevos);
        List<String> noAgingRoleId = getNoAgingRoleId(rolevos);
        String status = systemConfExtService.queryGrantAgingConfigs().getStatus();
        if (CollectionUtils.isNotEmpty(agingRoleId)&& "Y".equalsIgnoreCase(status)) {
            int size = agingRoleId.size();
            if (CollectionUtils.isNotEmpty(noAgingRoleId)) {
                int siz = 0;
                siz = noAgingRoleId.size();
                String[] noAgingRoleIds = (String[]) noAgingRoleId.toArray(new String[siz]);
                iUserRoleOptPubService.addUserRoleOnUser(userId, noAgingRoleIds, false);
            }
            String[] agingRoleIds = (String[]) agingRoleId.toArray(new String[size]);
            iUserRoleOptPubService.addUserRoleOnUser(userId, agingRoleIds, true);
            return new AjaxResult(AjaxResult.STATUS_SUCCESS, "请到用户角色关联界面设置时效，权限时效默认为各自时效角色中设置的权限时效默认值");
        } else {
            iUserRoleOptPubService.addUserRoleOnUser(userId, ids, false);
            return AjaxResult.SUCCESS;
        }
    }

    @RequestMapping("/setAgingDate.d")
    @ViewWrapper(wrapped = false)
    public String setAgingDate(Model model, String id, String userId) {
        model.addAttribute("userId", userId);
        model.addAttribute("selAgingRoleIds", id);
        return "grant/user/setAging-enddate";
    }

    /**
     * 保存设置的截止时效
     * @param userId 用户id
     * @param aging_enddate 设置的时效时间
     * @param id 角色id
     * @return
     */
    @RequestMapping("/saveAgingEndDate.d")
    @ResponseBody
    @ViewWrapper(wrapped = false, onlyAjax = true)
    public Object saveAgingEndDate(String userId, String id, String aging_enddate, String dateType) {
        String[] ids = id.split(",");
        iUserRoleOptPubService.modifyRoleAgingOnUser(userId, ids, aging_enddate, dateType);
        return new AjaxResult(AjaxResult.STATUS_SUCCESS, "截止日期设置成功");
    }

    /**
     * 获取所选用户的已授权角色列表
     * @param userId
     * @return
     */
    private List<RoleVO> getGrantedRolesPage(String userId, Condition conds) {
        List<RoleVO> loginUserRoles = getLoginedUserRoles(); // 登录用户的角色列表
        List<RoleVO> grantedRoles = userRoleQueryPubService.queryRoleListByUserId(userId); // 所选用户角色列表
        // search框的条件查询
        String roleName = conds.getFreeValue();
        List<RoleVO> rolesByConds = roleQueryPubService.fuzzySearchRoleListByCondition(roleName);
        grantedRoles.retainAll(loginUserRoles); // 所选用户角色与登录用户角色的交集
        grantedRoles.retainAll(rolesByConds); // 交集与search框查询结果的交集即为最终展示到页面的角色列表
        setOrgName(grantedRoles); // 查询并关联机构名称
        return grantedRoles;
    }

    /**
     * 获取所选用户的未授权角色列表
     * @param userId
     * @return
     */
    private List<RoleVO> getNoGrantedRolesPage(String userId, Condition conds) {
        List<RoleVO> loginUserRoles = getLoginedUserRoles(); // 当前登录用户的角色列表
        List<RoleVO> grantedRoles = userRoleQueryPubService.queryRoleListByUserId(userId); // 所选用户角色列表
        String roleName;
        // 模糊匹配的用户列表 - 条件来自页面search框
        if (conds != null) {
            roleName = conds.getFreeValue();
        } else {
            roleName = "";
        }
        List<RoleVO> rolesByConds = roleQueryPubService.fuzzySearchRoleListByCondition(roleName);
        // 登录用户的角色列表除去所选用户已关联的角色即为“当前登录用户可授权的角色列表”
        loginUserRoles.removeAll(grantedRoles);
        loginUserRoles.retainAll(rolesByConds); // 匹配搜索框
        setOrgName(loginUserRoles);
        return loginUserRoles;
    }

    /**
     * 获取当前登录用户的角色列表
     * @return
     */
    private List<RoleVO> getLoginedUserRoles() {
        String loginUserID = AppContext.getContext().getContextUserId();
        /** 2017.01.13 modifyBy lyf 原因：加载的角色列表应为当前登录用户所关联的所有角色所关联的所有机构下所属的角色 **/
        List<OrgVO> loginUserOrgs = orgPowerPubService.queryOrgListByUser(loginUserID);
        List<String> orgids = new ArrayList<>();
        for (OrgVO loginUserOrg : loginUserOrgs) {
            String orgid = loginUserOrg.getId();
            orgids.add(orgid);
        }
        List<RoleVO> rolevos = roleQueryPubService.queryRoleListByOrgIds(orgids);
        /** 修改至此结束**/
        return rolevos;
    }

    // ///////////////////////////////////用户关联角色End/////////////////////////////////////////////////////
    /**
     * @Title: doExportUserModel 
     * @Description: 下载用户模板文件
     * @param response
     * @param request
     * @throws IOException
     * @return void    返回类型
     */
    @RequestMapping("/doExportUserModel.d")
    @ViewWrapper(wrapped = false)
    @ResponseBody
    public void doExportUserModel(HttpServletResponse response, HttpServletRequest request) throws IOException {
        List<UserVO> datas = new ArrayList<>();
        UserVO userVO = new UserVO();
        datas.add(userVO);
        Integer column = 10;
        ExcelHelper<UserVO> util = new ExcelHelper(UserVO.class);
        String fileName = "用户导入模板.xls";
        fileName = new String(fileName.getBytes("GBK"), "ISO-8859-1");
        response.reset();
        response.setContentType("application/ms-excel;charset=UTF-8");
        response.setHeader("Content-disposition", "attachment;filename=" + fileName);
        OutputStream out = response.getOutputStream();
        util.exportExcel(datas, "用户信息", 60000, out, column);
        out.flush();
    }

    /**
     * 
     * @Title: importUser 
     * @Description: 到导入用户界面
     * @return String	返回页面
     */
    @RequestMapping("/importUser.d")
    @ViewWrapper(wrapped = false)
    public String importUser() {
        return "grant/user/importUser";
    }

    /******
     * 导入用户实现
     * 
     * @param request
     * @param model
     * @return 用户列表
     */
    @RequestMapping("/execImportUser.d")
    public Object execImportUser(MultipartHttpServletRequest request, Model model, RedirectAttributes redirectAttributes) {
        MultipartHttpServletRequest mulRequest = (MultipartHttpServletRequest) request;
        MultipartFile file = mulRequest.getFile("excelFile");
        UserImport imp = new UserImport(UserDataIOVO.class);
        List<UserDataIOVO> vos = imp.doImport("用户导入", file);
        int com = 0 ;
        if (vos == null || vos.isEmpty()) {
            throw new BusinessException("导入数据为空或者格式有误，请确认！");
        }
        try {
           com = userService.importUsers(vos);
        } catch (Exception e) {
            model.addAttribute("exception", e);
            return "grant/user/importError";
        }
        redirectAttributes.addFlashAttribute("imptSize", com);
        return "redirect:list.d";
    }

    /**
    * @Title: personalInformation
    * @Description: 获取个人信息
    * @return    参数说明
    * @return Object    返货ajax结果，包括状态信息、用户信息
    *      */
    @RequestMapping("/personalInformation.d")
    @ViewWrapper(wrapped = false, onlyAjax = true)
    public Object personalInformation(Model model) {
        String loginUserID = AppContext.getContext().getContextUserId();
        UserVO vo = userQueryPubService.load(loginUserID);
        PasswordConfVO conf = systemConfExtService.queryPasswordConf();
        String help = "";
        if (conf.getPwdLength() > 0) {
            help += "不小于" + conf.getPwdLength() + "位";
        }
        if ("Y".equals(conf.getPwdNumberState())) {
            if (help.length() > 0) {
                help += ";包含数字";
            } else {
                help += "包含数字";
            }
        }
        if ("Y".equals(conf.getPwdCharacterState())) {
            if (help.length() > 0) {
                help += ";包含@,#,$之一";
            } else {
                help += "包含@,#,$之一";
            }
        }
        if ("Y".equals(conf.getPwdEnglishState())) {
            if (help.length() > 0) {
                help += ";包含英文字母";
            } else {
                help += "包含英文字母";
            }
        }
        if ("Y".equals(conf.getPwdCaseState())) {
            if (help.length() > 0) {
                help += ";包含大写英文字母";
            } else {
                help += "包含大写英文字母";
            }
        }
        if (help.length() > 0) {
            help = "密码组成: " + help;
        }
        model.addAttribute("user", vo);
        model.addAttribute("pwdSecurity", conf);
        model.addAttribute("help", help);
        return "grant/user/personalInformation";
    }

    /***
    * @Title: execResetPersonalInf
    * @Description:重置个人信息
    * @param user 个人信息
    * @return    参数说明
    * @return Object    返回成功状态
    *      */
    @RequestMapping("/execResetPersonalInf.d")
    @ResponseBody
    public Object execResetPersonalInf(UserVO user) {
        userOptPubService.resetPersonalInf(user);
        //修改界面显示的用户名称
        AppContext context = AppContext.getContext();
        AuthInfo authUser = userQueryPubService.queryUserAuthInfoByAccount(user.getAccount());//用户信息更新后需要查一次，保证数据为最新    lijun  20170428
        context.setAttribute(AppContext.SESSION, AppContext.USER_KEY, authUser);
        return AjaxResult.SUCCESS;
    }

    /**
     * 账号唯一性校验
     * @param account
     * @return
     */
    @RequestMapping("/synchroCheck.d")
    @ResponseBody
    public Object synchroCheck(String account) {
        return userService.synchroCheck(account);
    }
    /**
     * 密码不允许包含空格
     * @param account
     * @return
     */
    @RequestMapping("/pwdBlankCheck.d")
    @ResponseBody
    public Object pwdBlankCheck(String user_password) {
        String msg = "";
        if(user_password.contains(" ")){
            msg = "密码不允许包含空格！";
            return msg;
        }
        return Boolean.TRUE;
    }

    /**
     * 设置角色的机构名
     * @param roleList
     */
    private void setOrgName(List<RoleVO> roleList) {
        List<OrgVO> orgs = orgQueryPubService.queryAllOrgList();
        for (RoleVO vo : roleList) {
            String orgid = vo.getPk_org();
            for (OrgVO orgVO : orgs) {
                if (orgid.equals(orgVO.getId())) {
                    vo.setOrgname(orgVO.getOrgname());
                }
            }
        }
    }

    /**
     * 获取时效角色id
     * @param loginUserRoles
     * @return
     */
    private List<String> getAgingRoleId(List<RoleVO> userRoles) {
        List<String> rolevos = new ArrayList<>();
        for (RoleVO userRole : userRoles) {
            if (userRole.getAgingLimit() != null && "Y".equalsIgnoreCase(userRole.getAgingLimit())) {
                rolevos.add(userRole.getId());
            }
        }
        return rolevos;
    }

    /**
     * 获取非时效角色id
     * @param loginUserRoles
     * @return
     */
    private List<String> getNoAgingRoleId(List<RoleVO> loginUserRoles) {
        List<String> rolevos = new ArrayList<>();
        for (RoleVO loginUserRole : loginUserRoles) {
            if ((loginUserRole.getAgingLimit() != null && "N".equalsIgnoreCase(loginUserRole.getAgingLimit())) || loginUserRole.getAgingLimit() == null) {
                rolevos.add(loginUserRole.getId());
            }
        }
        return rolevos;
    }

    /**
     * 将list<String>转化为String
     * @param agingRoleId
     * @return
     */
    private String listToString(List<String> agingRoleId) {
        String[] agingRoleIds = new String[agingRoleId.size()];
        for (int i = 0; i < agingRoleId.size(); i++) {
            agingRoleIds[i] = agingRoleId.get(i);
        }
        String agingroleId = StringUtils.join(agingRoleIds, ",");
        return agingroleId;
    }

    /***
     * 启用用户功能
     * @param id
     * @return
     */
    @RequestMapping("/enable.d")
    @ResponseBody
    public Object enable(String... id) {
        userOptPubService.enable(id);
        return AjaxResult.SUCCESS;
    }

    /***
     *停用用户功能
     * @param id
     * @return
     */
    @RequestMapping("/disable.d")
    @ResponseBody
    public Object disable(String... id) {
        userOptPubService.disable(id);
        return AjaxResult.SUCCESS;
    }
}
