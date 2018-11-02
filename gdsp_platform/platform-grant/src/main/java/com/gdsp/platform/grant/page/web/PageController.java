package com.gdsp.platform.grant.page.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gdsp.dev.base.utils.JsonUtils;
import com.gdsp.dev.core.common.AppContext;
import com.gdsp.dev.core.model.query.Condition;
import com.gdsp.dev.web.mvc.ext.ViewWrapper;
import com.gdsp.dev.web.mvc.model.AjaxResult;
import com.gdsp.platform.func.model.PageRegisterVO;
import com.gdsp.platform.func.service.IPageRegisterService;
import com.gdsp.platform.grant.auth.model.PagePowerVO;
import com.gdsp.platform.grant.auth.service.IPowerMgtOptPubService;
import com.gdsp.platform.grant.auth.service.IPowerMgtQueryPubService;
import com.gdsp.platform.grant.role.model.RoleVO;
import com.gdsp.platform.grant.utils.GrantConst;
import com.gdsp.platform.grant.utils.GrantUtils;

/**
 * Created on 2015/7/29
 */
@Controller
@RequestMapping("grant/page")
public class PageController {

    @Autowired
    private IPowerMgtQueryPubService powerMgtQueryPubService;
    @Autowired
    private IPowerMgtOptPubService   powerMgtUpdatePubService;
    @Autowired
    private IPageRegisterService     pageRegisterService;

    @RequestMapping("/mainList.d")
    public String mainList(Model model, String pageId, Condition condition, Pageable pageable) {
//    	 String loginUserID = AppContext.getContext().getContextUserId();
//         Map pageMap = powerMgtQueryPubService.queryPageListByUserForPower(loginUserID);
//         model.addAttribute("pageMap", pageMap);
        return "grant/page/page-main";
    }

    //新树加载节点数据
    @RequestMapping("/loadPage.d")
    @ResponseBody
    public Object loadPageTree() {
    	String loginUserID = AppContext.getContext().getContextUserId();
        Map pageMap = powerMgtQueryPubService.queryPageListByUserForPower(loginUserID);
        Map<String,String> nodeAttr = new HashMap<String,String>();
        nodeAttr.put("text", "tabName");
        if(pageMap.size() != 0){
        	return JsonUtils.formatMap2TreeViewJson(pageMap, nodeAttr);
        }else{
        	return "";
        }
    }
    
    //根据页面id获得页面信息并返回至js
    @RequestMapping("/getNode.d")
    @ResponseBody
    public Object getNode(String pageId) {
    	PageRegisterVO pageRegisterVO=pageRegisterService.loadPageRegister(pageId);
    	if(pageRegisterVO != null){
    		String menuId = pageRegisterVO.getMenuid();
        	AjaxResult ajaxResult = new AjaxResult(menuId);
            return ajaxResult;
    	}else{
    		String menuId = null;
        	AjaxResult ajaxResult = new AjaxResult(menuId);
            return ajaxResult;
    	}
    }
    
    @RequestMapping("/delete.d")
    @ResponseBody
    public Object delete(String id) {
        pageRegisterService.deletePageRegister(id);
        return AjaxResult.SUCCESS;
    }

    @RequestMapping("/reloadRole.d")
    @ViewWrapper(wrapped = false)
    public String reloadUserRole(Model model, String pageId, Condition condition, Pageable pageable) {
        String loginUserID = AppContext.getContext().getContextUserId();
        String rolename = condition.getFreeValue();
        model.addAttribute("pageId", pageId);
        
        /** ---- 查询更改  wqh 2016/12/22  原因：权限拆分 -------------------------*/
     // 更改前
//        model.addAttribute("pageRoles",powerMgtQueryPubService.queryPageRoleByPageId(pageId, loginUserID,rolename) );
     // 更改后
        List<PagePowerVO> queryPageRoles = powerMgtQueryPubService.queryPageRoleByPageId(pageId, loginUserID,rolename);
        Page<PagePowerVO> pageRoles = GrantUtils.convertListToPage(queryPageRoles, pageable);
        model.addAttribute("pageRoles",pageRoles );
        /**---------------------- 更改至此结束 ----------------------------------*/
        
        return "grant/page/rolelist-data";
    }

    @RequestMapping("/addRole.d")
    @ViewWrapper(wrapped = false, onlyAjax = true)
    public String addRole(Model model, String pageId, Condition condition, Pageable pageable) {
        String loginUserID = AppContext.getContext().getContextUserId();
        String rolename = condition.getFreeValue();
        model.addAttribute("pageId", pageId);
        // TODO 通过参照增加关联角色，查询需重写
        
        /** ---- 查询更改  wqh 2016/12/22  原因：权限拆分 -------------------------*/
        // 更改前
//        model.addAttribute("roles", powerMgtQueryPubService.queryRoleForPagePower(pageId, rolename, loginUserID));
        // 更改后
        List<RoleVO> queryRoles = powerMgtQueryPubService.queryRoleForPagePower(pageId, rolename, loginUserID);
        Page<RoleVO> pageRoles = GrantUtils.convertListToPage(queryRoles, pageable);
        model.addAttribute("roles",pageRoles);
        /**---------------------- 更改至此结束 ----------------------------------*/
        
        return "grant/page/role-add";
    }

    @RequestMapping("/reloadRoleList.d")
    @ViewWrapper(wrapped = false)
    public String reloadRoleList(Model model, String pageId, Condition condition, Pageable pageable) {
        String loginUserID = AppContext.getContext().getContextUserId();
        String rolename = condition.getFreeValue();
        if (StringUtils.isNotEmpty(rolename)) {
            rolename = rolename.trim();
            condition.addExpression("rolename", rolename, "like");
        }
        model.addAttribute("pageId", pageId);
        
        /** ---- 查询更改  wqh 2016/12/22  原因：权限拆分 -------------------------*/
        // 更改前
//        model.addAttribute("roles", powerMgtQueryPubService.queryRoleForPagePower(pageId, rolename, loginUserID));
        // 更改后
        List<RoleVO> queryRoles = powerMgtQueryPubService.queryRoleForPagePower(pageId, rolename, loginUserID);
        Page<RoleVO> roles = GrantUtils.convertListToPage(queryRoles, pageable);
        model.addAttribute("roles", roles);
        /**---------------------- 更改至此结束 ----------------------------------*/
        
        return "grant/pub/rolelist-data";
    }

    @RequestMapping("/saveRoles.d")
    @ResponseBody
    public Object saveRoles(String pageId, String... id) {
        powerMgtUpdatePubService.addUserRoleOnPage(pageId, GrantConst.OBJ_TYPE_ROLE, id);
        return AjaxResult.SUCCESS;
    }

    @RequestMapping("/deleteRoles.d")
    @ResponseBody
    public Object deleteRoles(String... id) {
        if (id != null && id.length > 0) {
            powerMgtUpdatePubService.deletePageRole(id);
        }
        return AjaxResult.SUCCESS;
    }

    @RequestMapping("/sortPage.d")
    @ViewWrapper(wrapped = false)
    public String sortPage(Model model, String menuId){
        String loginUserID = AppContext.getContext().getContextUserId();
        model.addAttribute("menuID", menuId);
        model.addAttribute("page_list", powerMgtQueryPubService.queryPageListForMenu(loginUserID, menuId));
        return "/grant/page/sortPage";
    }

    @RequestMapping("/saveSort.d")
    @ResponseBody
    public Object saveSort(Model model, String[] pageIDs){
    	if (pageIDs.length<0) {
    		return new AjaxResult(AjaxResult.STATUS_ERROR, "请选择要排序的菜单或页面！");
		}else {
			pageRegisterService.sort(pageIDs);
			return AjaxResult.SUCCESS;
		}
    }
    
/*	页面授权只授到角色，不在授权到用户
    @RequestMapping("/reloadUser.d")
    @ViewWrapper(wrapped = false)
    public String reloadUser(Model model, String pageId, Condition condition, Pageable pageable) {
        String loginUserID = AppContext.getContext().getContextUserId();
        model.addAttribute("pageId", pageId);
        model.addAttribute("pageUsers", powerMgtService.queryPageUserByPageId(pageId, loginUserID, condition, pageable));
        return "grant/page/userlist-data";
    }

    @RequestMapping("/addUser.d")
    @ViewWrapper(wrapped = false, onlyAjax = true)
    public String addUser(Model model, String pageId, Condition condition, Pageable pageable) {
        String loginUserID = AppContext.getContext().getContextUserId();
        model.addAttribute("pageId", pageId);
        model.addAttribute("users", powerMgtService.queryUserForPagePower(pageId, condition, loginUserID, pageable));
        return "grant/page/user-add";
    }
    
    @RequestMapping("/reloadUserList.d")
    @ViewWrapper(wrapped = false)
    public String reloadUserList(Model model, String pageId, Condition condition, Pageable pageable) {
        String loginUserID = AppContext.getContext().getContextUserId();
        if (condition.getFreeValue() != null) {
            condition.setFreeValue(condition.getFreeValue().trim());
        }
        model.addAttribute("pageId", pageId);
        model.addAttribute("users", powerMgtService.queryUserForPagePower(pageId, condition, loginUserID, pageable));
        return "grant/pub/userlist-data";
    }

    @RequestMapping("/saveUsers.d")
    @ResponseBody
    public Object saveUsers(String pageId, String... id) {
        powerMgtUpdatePubService.addUserRoleOnPage(pageId, GrantConst.OBJ_TYPE_USER, id);
        return AjaxResult.SUCCESS;
    }
   
    @RequestMapping("/deleteUsers.d")
    @ResponseBody
    public Object deleteUsers(String... id) {
        if (id != null && id.length > 0) {
            powerMgtUpdatePubService.deletePageRole(id);
        }
        return AjaxResult.SUCCESS;
    }
*/

}
