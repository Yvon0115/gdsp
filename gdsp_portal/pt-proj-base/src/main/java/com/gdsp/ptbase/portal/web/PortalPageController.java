package com.gdsp.ptbase.portal.web;

import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gdsp.dev.core.common.AppConfig;
import com.gdsp.dev.core.common.AppContext;
import com.gdsp.dev.web.mvc.model.AjaxResult;
import com.gdsp.ptbase.portal.helper.PortalHelper;
import com.gdsp.ptbase.portal.model.IPortalPage;
import com.gdsp.ptbase.portal.service.IPortalDefaultHomePageService;
import com.gdsp.ptbase.portal.service.IPortalPageHandler;
import com.gdsp.ptbase.portal.service.IPortalPageLoadService;

/**
 * portal页处理
 *
 * @author paul.yang
 * @version 1.0 2015-7-30
 * @since 1.6
 */
@Controller
@RequestMapping("portal")
public class PortalPageController {

    /**
     * Portal页相关服务
     */
    @Resource
    private IPortalPageLoadService service;
    /**
     * 默认的页面模板
     */
    private String                 defaultPageTemplate = null;

    /**
     * 默认首页服务
     */
    @Autowired
    private IPortalPageHandler portalPageHandler;
    
    /**
     * 页面加载
     * @param model 模型对象
     * @param key 页面key
     * @return 页面路径
     */
    @RequestMapping(value = "/page/{key}.d")
    public String load(Model model, @PathVariable String key) {
        AppContext context = AppContext.getContext();
        String user = context.getContextUserId();
        IPortalPage page = service.loadPortalPage(key);
        model.addAttribute("page", page);
        String url = page.getPageURL();
//        String url="test";
        if (StringUtils.isNotEmpty(url))
            return "forward:" + url;
        String beanName = page.getHandlerBean();
        if (StringUtils.isNotEmpty(beanName)) {
            IPortalPageHandler handler = context.lookup(beanName, IPortalPageHandler.class);
            if (handler != null) {
                Map<String, Object> d = handler.prepareData(user);
                model.addAllAttributes(d);
            }
        }
        String template = page.getTemplate();
        if (StringUtils.isNotEmpty(template)) {
            return template;
        }
        return getDefaultPageTemplate();
    }

    /**
     * 取得默认模板
     * @return 默认模板路径
     */
    protected String getDefaultPageTemplate() {
        if (defaultPageTemplate == null) {
            defaultPageTemplate = AppConfig.getInstance().getString(PortalHelper.OPTIONS_DEFAULTPORTALPAGETEMPLATE, PortalHelper.DEFAULT_PAGETEMPLATE);
        }
        return defaultPageTemplate;
    }
    
    
    
    /**
     * 设置默认首页
     */
    @RequestMapping("/page/setDefaultPage.d")
    @ResponseBody
    public Object setDefaultPage(String menuID,String pageID) {
    	
    	String userID = AppContext.getContext().getContextUserId();
    	
    	portalPageHandler.setDefaultHomePage(userID, pageID, menuID);
    	
    	return AjaxResult.SUCCESS;
    	
	}
    
    
    
}
