package com.gdsp.platform.common.web;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.gdsp.dev.base.exceptions.BusinessException;
import com.gdsp.dev.base.utils.data.ValuePair;
import com.gdsp.dev.core.common.AppConfig;
import com.gdsp.dev.core.common.AppContext;
import com.gdsp.dev.core.common.RequestInfo;
import com.gdsp.dev.web.mvc.ext.ViewTab;
import com.gdsp.dev.web.scanner.Interceptor;
import com.gdsp.platform.common.helper.TabsHelper;
import com.gdsp.platform.func.helper.FuncConst;
import com.gdsp.platform.func.helper.MenuConst;
import com.gdsp.platform.func.model.BaseTabsRegisterVO;
import com.gdsp.platform.func.model.MenuRegisterVO;
import com.gdsp.platform.func.model.PageRegisterVO;
import com.gdsp.platform.func.model.ResourceRegisterVO;
import com.gdsp.platform.func.service.IMenuRegisterService;
import com.gdsp.platform.func.service.IPageRegisterService;
import com.gdsp.platform.func.service.IResourceRegisterService;
import com.gdsp.platform.grant.auth.service.IPowerMgtQueryPubService;

/**
 * 上下文拦截器
 * @author paul.yang
 * @version 1.0 2014年9月24日
 * @since 1.6
 */
@Interceptor("/**/*.d")
@Order(0)
public class PageRequestInterceptor implements HandlerInterceptor {

    /**业务日志*/
    private static final Logger      busInfoLogger      = LoggerFactory.getLogger("busInfoLogger");
    private static Logger       logger         = LoggerFactory.getLogger(PageRequestInterceptor.class);
    /**
	 * 日志记录异常写入文件
	 */
    private static Logger logExFile = LoggerFactory.getLogger("logExcepFile");
    /**
     * 菜单服务
     */
    private IMenuRegisterService     menuService        = null;
    private IPageRegisterService     pageService        = null;
    private IResourceRegisterService resourceService    = null;

    /**
     * 权限服务
     */
    private IPowerMgtQueryPubService powerMgtPubService = null;

    /* (non-Javadoc)
     * @see org.springframework.web.servlet.HandlerInterceptor#preHandle(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, java.lang.Object)
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
            Object handler) throws Exception {
        AppContext context = AppContext.getContext();
        String menuId = request.getParameter(MenuRegisterVO.MENUIDPARAM);
        if (StringUtils.isEmpty(menuId)) {
            menuId = request.getHeader(MenuRegisterVO.MENUIDPARAM);
        }
        MenuRegisterVO vo;
        if (StringUtils.isNotEmpty(menuId)) {
            vo = getMenuService().loadMenuRegisterVOById(menuId);
        } else {
            String url = request.getRequestURI().substring(AppContext.getContextPath().length() + 1);
            vo = getMenuService().loadMenuRegisterVOByUrl(url);
        }
        if (vo != null) {
            request.setAttribute("type", vo.getFuntype());
        }
        String tabId = request.getParameter(ViewTab.PARAM_TABID);
        String tabEntityType = request.getParameter(ViewTab.PARAM_TABENTITYEYPE);
        BaseTabsRegisterVO bvo = this.getRegisterVO(menuId, tabId, tabEntityType, vo);
        TabsHelper.initTabParam(request, bvo);
        if (vo != null) {
            RequestInfo req = new RequestInfo();
            req.setMenuCode(vo.getFuncode());
            req.setMenuName(vo.getFunname());
            req.setMenuType(vo.getFuntype());
            req.setMenuId(vo.getId());
            List<ValuePair> breadCrumb = new ArrayList<>();
            if (!MenuConst.HOME_FUNCODE.getValue().equals(vo.getFuncode())) {
                String userId = context.getContextUserId();
                List<MenuRegisterVO> menus = getPowerService().queryPageMenuListByUser(userId);
                if (menus != null && menus.size() > 0) {
                    MenuRegisterVO first = menus.get(0);
                    if (!vo.getId().equals(first.getId())) {
                        if (first.getFuntype() != MenuConst.MENUTYPE_PAGE.intValue()) {
                            breadCrumb.add(new ValuePair(first.getFunname(), "index.d"));
                        }
                    }
                }
            }
            if (vo.getFuntype() != MenuConst.MENUTYPE_PAGE.intValue()) {
                List<MenuRegisterVO> parentPath = getMenuService().queryMenuPaths(vo.getId());
                List<String> paths = new ArrayList<>();
                if (parentPath != null && parentPath.size() > 0) {
                    req.setModuleId(parentPath.get(0).getId());
                    for (MenuRegisterVO parent : parentPath) {
                        if (parent.getFuntype() != MenuConst.MENUTYPE_PAGE.intValue()) {
                            breadCrumb.add(new ValuePair(parent.getFunname(), null));
                            paths.add(parent.getId());
                        }
                    }
                }
                paths.add(vo.getId());
                req.setPaths(paths);
                breadCrumb.add(new ValuePair(vo.getFunname(), vo.getFuncUrl()));
            }
            req.setBreadCrumb(breadCrumb);
            context.setRequestInfo(req);
            //操作日志记录
            //res_id,name, type, button, url, msg,
            if (!StringUtils.isEmpty(vo.getFuncUrl())) {
                if ("Y".equals(AppConfig.getInstance().getString("log.accesslog.switch"))) {
                	try{
                    busInfoLogger.info("busInfoLogger",
                            vo.getId(),
                            vo.getFuncode() + "_" + vo.getFunname(),
                            //业务功能，报表
                            vo.getPageid() == null ? "sysfun" : "report",
                            vo.getFuncUrl(),
                            vo.getFuncUrl(),
                            AppContext.getContext().getContextUserId());
                	}catch(Exception e){
                		logExFile.info(e.getMessage()+"访问日志写入异常");
                		logger.debug(e.getMessage(),e);
                		return true;
                	}
                }
            }
        }
        return true;
    }

    /* (non-Javadoc)
     * @see org.springframework.web.servlet.HandlerInterceptor#postHandle(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, java.lang.Object, org.springframework.web.servlet.ModelAndView)
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response,
            Object handler, ModelAndView modelAndView) throws Exception {
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        ViewTab viewTab = handlerMethod.getMethodAnnotation(ViewTab.class);
        String foreTabs = request.getParameter(ViewTab.PARAM_FORCETABS);
        if ((viewTab != null && viewTab.tab() && ViewTab.MODE_REFRESH_MAIN.equals(viewTab.mode()) || "yes".equals(foreTabs)) && "tabs".equals(AppConfig.getProperty("view.breadCrumbType"))
                && StringUtils.isNotBlank(modelAndView.getViewName())) {
            //发现刷新主界面标记
            String viewName = TabsHelper.initTabFlagParam(request, modelAndView.getViewName(), ViewTab.MODE_REFRESH_MAIN, foreTabs);
            modelAndView.setViewName(viewName);
        }
    }

    /* (non-Javadoc)
     * @see org.springframework.web.servlet.HandlerInterceptor#afterCompletion(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, java.lang.Object, java.lang.Exception)
     */
    @Override
    public void afterCompletion(HttpServletRequest request,
            HttpServletResponse response, Object handler, Exception e)
            throws Exception {}

    private BaseTabsRegisterVO getRegisterVO(String menuId, String tabId, String tabEntityType, MenuRegisterVO vo) {
        if (StringUtils.isNotBlank(menuId) && StringUtils.isNotBlank(tabId) && menuId.equals(tabId) || StringUtils.isBlank(tabId)) {
            return vo;
        }
        BaseTabsRegisterVO bvo = null;
        if (PageRegisterVO.class.getSimpleName().equals(tabEntityType)) {
            bvo = this.getPageService().loadPageRegister(tabId);
            //pagemenu 需要
            MenuRegisterVO mvo = new MenuRegisterVO();
            mvo.setId(bvo != null ? bvo.getId() : "");
            mvo.setUrl(bvo != null ? bvo.getUrl() : "");
            mvo.setFunname(bvo != null ? bvo.getFunname() : "");
            mvo.setTabEntityType(bvo != null ? bvo.getTabEntityType() : "");
            bvo = mvo;
        } else if (MenuRegisterVO.class.getSimpleName().equals(tabEntityType)) {
            bvo = this.getMenuService().loadMenuRegisterVOById(tabId);
        } else if (ResourceRegisterVO.class.getSimpleName().equals(tabEntityType)) {
            bvo = this.getResourceService().getResourceByID(tabId);
        } else {
            throw new BusinessException("找不到类型：" + tabEntityType);
        }
        return bvo;
    }

    public IMenuRegisterService getMenuService() {
        if (menuService == null)
            menuService = AppContext.lookupBean(IMenuRegisterService.class);
        return menuService;
    }

    public IPowerMgtQueryPubService getPowerService() {
        if (powerMgtPubService == null)
            powerMgtPubService = AppContext.lookupBean(IPowerMgtQueryPubService.class);
        return powerMgtPubService;
    }

    public IPageRegisterService getPageService() {
        if (pageService == null)
            pageService = AppContext.lookupBean(IPageRegisterService.class);
        return pageService;
    }

    public IResourceRegisterService getResourceService() {
        if (resourceService == null)
            resourceService = AppContext.lookupBean(IResourceRegisterService.class);
        return resourceService;
    }
}
