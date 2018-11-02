package com.gdsp.platform.common.web;

import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.UnauthenticatedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gdsp.dev.core.common.AppConfig;
import com.gdsp.dev.core.common.AppContext;
import com.gdsp.dev.web.mvc.ext.ViewTab;
import com.gdsp.platform.config.customization.helper.SystemConfigConst;
import com.gdsp.platform.func.helper.FuncConst;
import com.gdsp.platform.func.model.MenuRegisterVO;
import com.gdsp.platform.func.service.IMenuRegisterService;
import com.gdsp.platform.grant.auth.service.IPowerMgtQueryPubService;

/**
 * 首页处理
 *
 * @author paul.yang
 * @version 1.0 2015-8-17
 * @since 1.6
 */
@Controller
public class IndexController {

    /**
     * 权限服务
     */
    @Autowired
    private IPowerMgtQueryPubService powerMgtQueryPubService;
    @Autowired
    private IPowerMgtQueryPubService powerMgtPubService;
    /**
     * 菜单服务
     */
    @Autowired
    private IMenuRegisterService     menuService;

    @RequestMapping("navigation.d")
    @ViewTab(tab = true, mode = ViewTab.MODE_REFRESH_MAIN)
    public String navigation()
    {
        String home="redirect:/index.d";
        String stateString = AppConfig.getProperty(SystemConfigConst.SYSTEMCONFIG_HOMEPAGE_STATE);
        if ("Y".equalsIgnoreCase(stateString)) {
            String url = AppConfig.getProperty(SystemConfigConst.SYSTEMCONFIG_HOMEPAGE_URL);
            if (!StringUtils.isEmpty(url)) {
                if (url.contains("http")) {
                    home="redirect:" + url;
                } else if (!url.startsWith("/")) {
                    home="redirect:/" + url;
                }
                else{
                    home= "redirect:" + url;
                }
            } else {
                Properties properties = AppConfig.getInstance().getConfig();
                properties.setProperty(SystemConfigConst.SYSTEMCONFIG_HOMEPAGE_STATE, "N");
            }
        } 
        return home;
    }
    /**
     * 首页
     * @return 首页页面路径
     */
    @RequestMapping("index.d")
    @ViewTab(tab = true, mode = ViewTab.MODE_REFRESH_MAIN)
    public String index(Model model) {
       
            String userId = AppContext.getContext().getContextUserId();
            List<MenuRegisterVO> pages = powerMgtPubService.queryPageMenuListByUser(userId);
            if (pages != null && pages.size() > 0) {
                MenuRegisterVO menu = pages.get(0).getChildrenPage().get(0);
                if (forwardUrl(menu.getUrl())) {
                	model.addAttribute("url", menu.getUrl());
                	model.addAttribute("name", menu.getFunname());
                    return "redirect:" + menu.getUrl();
                }
                //第一层为功能菜单
                String url = menu.getFuncUrl();
                if (!url.startsWith("/")) {
                    url = "/" + url;
                }
                String forwardUrl = processRegisterVOParam("forward:" + url, menu);
                String substrUrl = forwardUrl.substring("forward:".length());
                model.addAttribute("url", substrUrl);
                model.addAttribute("name", menu.getFunname());
                return forwardUrl;
            }
            List<MenuRegisterVO> menus = powerMgtPubService.queryFirstLevelMenuListByUser(userId);
            if (menus != null && menus.size() > 0) {
                //第一层为功能菜单
                MenuRegisterVO menu = menus.get(0);
                if (forwardUrl(menu.getUrl())) {
                	model.addAttribute("url", menu.getUrl());
                	model.addAttribute("name", menu.getFunname());
                    return "redirect:" + menu.getUrl();
                }
                String rs = forwardPage(menu);
                if (rs != null){
                	String url = rs.substring("forward:".length());
                	model.addAttribute("url", url);
                	model.addAttribute("name", menu.getFunname());
                    return rs;
                }
                return redirectNode(menu.getId(),model);
            }
       
        throw new UnauthenticatedException("无任何授权功能");
    }

    /**
     * 模块跳转
     * @param key 节点id
     * @return 功能节点url
     */
    @RequestMapping("module/func/{key}.d")
    @ViewTab(tab = true, mode = ViewTab.MODE_REFRESH_MAIN)
    public String redirectNode(@PathVariable String key,Model model) {
        // 机构树，暂时用1
        String userId = AppContext.getContext().getContextUserId();
        Map<String, List<MenuRegisterVO>> subMenu = powerMgtQueryPubService.queryLowerLevelMenuMapByUser(userId, key);
        if (subMenu == null || subMenu.size() == 0) {
            MenuRegisterVO menu = menuService.loadMenuRegisterVOById(key);
            if (forwardUrl(menu.getUrl())) {
            	model.addAttribute("url", menu.getUrl());
            	model.addAttribute("name", menu.getFunname());
                return "redirect:" + menu.getUrl();
            }
            String rs = forwardPage(menu);
            if (rs != null){
            	String url = rs.substring("forward:".length());
            	model.addAttribute("url", url);
            	model.addAttribute("name", menu.getFunname());
                return rs;
            }
            model.addAttribute("url", "/empty.d?__mn=" + key);
            model.addAttribute("name", "");
            return "redirect:/empty.d?__mn=" + key;
        }
        List<MenuRegisterVO> sub = subMenu.get(key);
        MenuRegisterVO target = sub.get(0);
        if (forwardUrl(target.getUrl())) {
        	model.addAttribute("url", target.getUrl());
            return "redirect:" + target.getUrl();
        }
        while (FuncConst.MENUTYPE_BUSI != target.getFuntype() && FuncConst.MENUTYPE_ADMIN != target.getFuntype()) {
            sub = subMenu.get(target.getId());
            if (sub == null || sub.size() == 0) {
            	model.addAttribute("url", "/empty.d?__mn=" + target.getId());
            	model.addAttribute("name", "");
                return "redirect:/empty.d?__mn=" + target.getId();
            }
            target = sub.get(0);
            if (forwardUrl(target.getUrl())) {
            	model.addAttribute("url", target.getUrl());
            	model.addAttribute("name", target.getFunname());
            	
                return "redirect:" + target.getUrl();
            }
        }
        String forwardUrl =  forwardPage(target);
        String url = forwardUrl.substring("forward:".length());
        model.addAttribute("url", url);
        model.addAttribute("name", target.getFunname());
        return "redirect:" + url;
//        return forwardUrl;  // forward不会经过url验证
    }

    /**
     * 返回跳转到指定菜单的结果串，如果菜单不合法则返回null
     * @param menu 菜单对象
     * @return 结果串
     */
    public String forwardPage(MenuRegisterVO menu) {
        if (FuncConst.MENUTYPE_BUSI != menu.getFuntype() && FuncConst.MENUTYPE_ADMIN != menu.getFuntype()) {
            return null;
        }
        String url = menu.getFuncUrl();
        if (!url.startsWith("/")) {
            url = "/" + url;
        }
        return processRegisterVOParam("forward:" + url, menu);
    }

    /**
     * 进行包裹和修饰但不进行具体内容显示
     * @return
     */
    @RequestMapping("module/forward.d")
    public String forward() {
        return "tabs/blankWrapperContent";
    }

    
    private String processRegisterVOParam(String url, MenuRegisterVO menu) {
        StringBuilder param = new StringBuilder();
        param.append(ViewTab.PARAM_TABID + "=" + (menu != null ? menu.getTabId() : ""));
        param.append("&" + ViewTab.PARAM_TABENTITYEYPE + "=" + (menu != null ? menu.getTabEntityType() : ""));
        if (StringUtils.isNotBlank(url)) {
            if (url.indexOf("?") > -1) {
                url += "&" + param.toString();
            } else {
                url += "?" + param.toString();
            }
        }
        return url;
    }

    private boolean forwardUrl(String url) {
        if (url == null) {
            return false;
        }
        Pattern exp = Pattern.compile("^http\\:\\/\\/.+$", Pattern.CASE_INSENSITIVE);
        //url正则表达式
//        String expression = "^[https|http|ftp|rtsp|mms]";
//        boolean a = exp.matcher(url).matches();
        //判断是否是完整的url
        return exp.matcher(url).matches();
    }
    
    @RequestMapping("homepage.d")
    public String homepage(Model model){
        //隐藏首页的菜单头
        model.addAttribute("hideHeaderMenu", "Y");
        model.addAttribute("hideHeaderLogo", "Y");
        return "common/login/home";
    }
}
