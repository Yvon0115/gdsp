package com.gdsp.platform.security.helper;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gdsp.dev.base.lang.DDate;
import com.gdsp.dev.base.lang.DDateTime;
import com.gdsp.dev.core.model.query.Condition;
import com.gdsp.platform.func.model.MenuRegisterVO;
import com.gdsp.platform.func.model.PageRegisterVO;
import com.gdsp.platform.func.service.IMenuRegisterService;
import com.gdsp.platform.func.service.IPageRegisterService;
import com.gdsp.platform.grant.auth.model.PowerMgtVO;
import com.gdsp.platform.grant.auth.model.UserRoleVO;
import com.gdsp.platform.grant.auth.service.IPowerMgtQueryPubService;
import com.gdsp.platform.grant.auth.service.IUserRoleQueryPubService;
import com.gdsp.platform.grant.role.model.RoleVO;
import com.gdsp.platform.grant.role.service.IRoleQueryPubService;
import com.gdsp.platform.grant.user.model.UserVO;
import com.gdsp.platform.grant.user.service.IUserQueryPubService;
import com.gdsp.platform.grant.utils.GrantConst;

@Component
public class MenuContext {

    /**
     * 菜单类型枚举对象
     * @author Administrator
     *
     */
    public enum MenuType {
        Menu(0, new MenuVerifyProcessor()), Page(1, new PageVerifyProcessor()), Pass(2, new PassProcessor());

        //菜单类型
        private int                type;
        //url匹配处理对象
        private UrlVerifyProcessor urlVerifyProcessor;

        private MenuType(int type, UrlVerifyProcessor urlVerifyProcessor) {
            this.type = type;
            this.urlVerifyProcessor = urlVerifyProcessor;
        }

        public UrlVerifyProcessor getUrlMacthingProcessor() {
            return urlVerifyProcessor;
        }
    }

    @Autowired
    private IUserQueryPubService userPubService;
    
    @Autowired
    private IPowerMgtQueryPubService powerMgtQueryPubService;
    
    @Autowired
    private IMenuRegisterService menuRegisterService;
    @Autowired
    private IPageRegisterService pageRegisterService;
    @Autowired
    private IUserRoleQueryPubService userRoleQueryPubService;
    @Autowired
    private IRoleQueryPubService roleQueryPubService;

    private Map<String, MenuType> registerMenu() {
        //查询所有注册的菜单
        List<MenuRegisterVO> allMenuList = menuRegisterService.queryAllMenuList();
        Map<String, MenuType> menuTypeMap = new HashMap<String, MenuType>();
        if (allMenuList != null && allMenuList.size() != 0) {
            for (MenuRegisterVO menuVo : allMenuList) {
                String menuUrl = menuVo.getUrl();
                //截取菜单url作为map的键
                if (StringUtils.isNotEmpty(menuUrl)) {
                    if (StringUtils.isEmpty(menuVo.getPageid())) {
                        int index = menuUrl.lastIndexOf("/");
                        String processedUrl = menuUrl.substring(0, index + 1);
                        String processedMenuUrl = "/" + processedUrl;
                        menuTypeMap.put(processedMenuUrl, MenuType.Menu);
                    } else {
                        menuTypeMap.put(menuUrl, MenuType.Menu);
                    }
                }
            }
        }
        return menuTypeMap;
    }

    private Map<String, MenuType> registerPage() {
        //查询所有注册的页面
        List<PageRegisterVO> allPageList = pageRegisterService.queryAllPageList();
        Map<String, MenuType> pageTypeMap = new HashMap<String, MenuType>();
        if (allPageList != null && allPageList.size() != 0) {
            for (PageRegisterVO pageVo : allPageList) {
                String pageUrl = pageVo.getUrl();
                //处理页面url作为map的键
                if (StringUtils.isNotEmpty(pageUrl)) {
                    String processedPageUrl = "/" + pageUrl;
                    pageTypeMap.put(processedPageUrl, MenuType.Page);
                }
            }
        }
        return pageTypeMap;
    }

    public MenuType getMenuType(String url) {
        //得到所有注册的菜单与页面map，键为经过处理的url，值为对应的MenuType对象
        Map<String, MenuType> menuTypeMap = registerMenu();
        Map<String, MenuType> pageTypeMap = registerPage();
        menuTypeMap.putAll(pageTypeMap);
        MenuType type = null;
        //如果url中包含map中的键，则返回对应的MenuType对象
        for (String key : menuTypeMap.keySet()) {
            if (url.contains(key)) {
                type = menuTypeMap.get(key);
            }
        }
        if (type == null) {
            type = MenuType.Pass;
        }
        return type;
    }

    public Map<String, String> getUserMenuMap(String loginName) {
        //根据用户名查询对应用户所具有权限的菜单
        UserVO userVO = userPubService.queryUserByAccount(loginName);
        List<MenuRegisterVO> menuList = powerMgtQueryPubService.queryMenuListByUserVo(userVO);
        if(!(userVO.getUsertype() == GrantConst.USERTYPE_ADMIN)){
	        List<String> roleids = new ArrayList<String>();
	        roleids = getFilterRoleIds(userVO);
	        List<MenuRegisterVO> mgvos = new ArrayList<MenuRegisterVO>();
	        List<String> powerMgtids = new ArrayList<String>();
	          //根据角色id查询与之关联的菜单
	        powerMgtids = powerMgtQueryPubService.queryPowerMgtByRoleIdList(roleids);
	        Condition cond = new Condition();
	        if(powerMgtids!=null&&powerMgtids.size()>0){
	        	cond.addExpression("id", powerMgtids, "in");
	        }else{
	        	cond.addExpression("id", "("+"''"+")", "in");
	        }
	        mgvos = menuRegisterService.queryMenuRegisterVOsByCond(cond,null);
	        menuList.retainAll(mgvos);           
        }
        Map<String, String> menuMap = new HashMap<String, String>();
        //处理菜单url，以处理过的url为键
        if (menuList != null && menuList.size() != 0) {
            for (MenuRegisterVO menuVo : menuList) {
                String menuUrl = menuVo.getUrl();
                if (StringUtils.isNotEmpty(menuUrl)) {
                	String processedMenuUrl =null;
                	/** 增加判断逻辑，当页面为portal页面时，获取完整url为键(leiting 2018/2/6) */
                	if(menuUrl.contains("portal/page/")){
                		processedMenuUrl = "/" + menuUrl;
                	}else{
                		int index = menuUrl.lastIndexOf("/");
                		String processedUrl = menuUrl.substring(0, index + 1);
                		processedMenuUrl = "/" + processedUrl;
                	}
                	menuMap.put(processedMenuUrl, menuVo.getId());
                }
            }
        }
        return menuMap;
    }

    public Map<String, String> getUserPageMap(String loginName) {
        // 根据用户名查询对应用户所具有权限的页面
        UserVO userVO = userPubService.queryUserByAccount(loginName);
        List<String> roleids = new ArrayList<String>();
        roleids = getFilterRoleIds(userVO);
        //获得过滤权限时效后的页面list
        List<MenuRegisterVO> pageList = powerMgtQueryPubService.queryPageMenuListByUser(userVO.getId());
        Map<String, String> menuMap = new HashMap<String, String>();
        //处理页面url，以处理过的url为键
        if (pageList != null && pageList.size() != 0) {
        	
        	/** 循环方式更改,因为页面菜单变成了两级(WangQinghua 2016/11/18) */
            for (int i = 0; i < pageList.size(); i++) {
            	MenuRegisterVO parent =  pageList.get(i);
            	List<MenuRegisterVO> children = parent.getChildrenPage();
            	for (int j = 0; j < children.size(); j++) {
            		MenuRegisterVO child = children.get(j);
            		String pageUrl = child.getUrl();
            		if (StringUtils.isNotEmpty(pageUrl)) {
                        String processedPageUrl = "/" + pageUrl;
                        menuMap.put(processedPageUrl, child.getId());
                    }
				}
            }
        }
        return menuMap;
    }
    
    //根据用户得到过滤权限后的角色id TODO  代码优化 可以挪至接口
	private List<String> getFilterRoleIds(UserVO userVO) {
		List<String> roleids = new ArrayList<String>();
        String userID = userVO.getId();
        List<RoleVO> rolevos = userRoleQueryPubService.queryFilteredRoleListByUserId(userID);
        for (RoleVO roleVO : rolevos) {
			String id = roleVO.getId();
			roleids.add(id);
		}
		return roleids;
	}
}
