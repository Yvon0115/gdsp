package com.gdsp.platform.common.helper;

import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;

import com.gdsp.dev.base.utils.web.URLUtils;
import com.gdsp.dev.core.common.AppConfig;
import com.gdsp.dev.core.common.AppContext;
import com.gdsp.dev.web.mvc.ext.ViewTab;
import com.gdsp.platform.func.model.BaseTabsRegisterVO;
import com.gdsp.platform.func.model.MenuRegisterVO;

/**
 * Tabs模式下，对请求进行转发、标记
 * @author xiangguo
 * 
 */
public class TabsHelper {

    public static String initTabFlagParam(HttpServletRequest request, String viewName, String tabMode, String forceTabs) {
        if (StringUtils.isNotBlank(viewName)) {
            String menuId = URLUtils.getParamByName(viewName, MenuRegisterVO.MENUIDPARAM);
            menuId = StringUtils.isNotBlank(menuId) ? menuId : "";
            String tabId = URLUtils.getParamByName(viewName, ViewTab.PARAM_TABID);
            String tabEntityType = URLUtils.getParamByName(viewName, ViewTab.PARAM_TABENTITYEYPE);
            if ("yes".equals(forceTabs)) {
                tabId = request.getParameter(ViewTab.PARAM_TABID);
                tabEntityType = request.getParameter(ViewTab.PARAM_TABENTITYEYPE);
                menuId = request.getParameter(MenuRegisterVO.MENUIDPARAM);
            }
            StringBuilder paramTemplate = new StringBuilder();
            paramTemplate.append(ViewTab.PARAM_TABID + "=" + tabId);
            paramTemplate.append("&" + ViewTab.PARAM_TABENTITYEYPE + "=" + tabEntityType);
            paramTemplate.append("&" + ViewTab.PARAM_LAYOUOTMODE + "=" + tabMode);
            paramTemplate.append("&logger=#{logger}#");
            String paramMenuIdParam = MenuRegisterVO.MENUIDPARAM + "=" + menuId;
            if (viewName.startsWith("forward:/") || "yes".equals(forceTabs)) {
                //做中转处理返回空页面，真实内容页面由iframe src发起
                StringBuilder forwardViewNameBuffer = new StringBuilder("forward:/module/forward.d?" + paramMenuIdParam);
                forwardViewNameBuffer.append("&" + paramTemplate.toString());
                String forwardViewName = forwardViewNameBuffer.toString().replace("#{logger}#", "no");
                //
                if ("yes".equals(forceTabs)) {
                    forwardViewName += "&" + ViewTab.PARAM_FORCETABS + "=initParam";
                }
                //属于中转请求不进行日志记录
                return forwardViewName;
            } else if (viewName.startsWith("redirect:/")) {
                //例如empty.d
                StringBuilder redirectViewNameBuffer = new StringBuilder(viewName.indexOf("?") != -1 ? viewName + "&" : viewName + "?");
                if (viewName.indexOf(MenuRegisterVO.MENUIDPARAM) != -1) {
                    redirectViewNameBuffer.append(paramTemplate.toString());
                } else {
                    redirectViewNameBuffer.append(paramMenuIdParam);
                    redirectViewNameBuffer.append("&" + paramTemplate.toString());
                }
                String redirectViewName = redirectViewNameBuffer.toString().replace("#{logger}#", "no");
                //属于中转请求不进行日志记录
                return redirectViewName;
            } else {
                //中转处理
                return viewName;
            }
        } else {
            return viewName;
        }
    }

    /**
     * 根据中转请求设置页签刷新包装修饰模式
     * @param request
     */
    public static boolean initTabParam(HttpServletRequest request, BaseTabsRegisterVO vo) {
        String foreTabs = request.getParameter(ViewTab.PARAM_FORCETABS);
        String layoutTabMode = request.getParameter(ViewTab.PARAM_LAYOUOTMODE);
        if ((ViewTab.MODE_REFRESH_MAIN.equals(layoutTabMode) || "initParam".equals(foreTabs)) && "tabs".equals(AppConfig.getProperty("view.breadCrumbType"))) {
            request.setAttribute(ViewTab.PARAM_LAYOUOTMODE, layoutTabMode);
            String url = vo != null ? vo.getTabUrl() : "";
            if (!StringUtils.isEmpty(url)) {
                if (url.startsWith("/")) {
                    url = AppContext.getContextPath() + url;
                } else {
                    url = AppContext.getContextPath() + "/" + url;
                }
            } else {
                url = AppContext.getContextPath() + "/" + AppConfig.getInstance().getString("view.blankController", "empty.d");
                url += MenuRegisterVO.MENUIDPARAM + "=" + (vo != null ? vo.getId() : "");
            }
            request.setAttribute(ViewTab.TAB_URL, url);
            request.setAttribute(ViewTab.TAB_ID, vo != null ? vo.getId() : UUID.randomUUID().toString());
            request.setAttribute(ViewTab.TAB_TITLE, vo != null ? vo.getFunname() : "异常页");
            String logger = request.getParameter("logger");
            //针对中转的请求不进行记录
            return "no".equals(logger) ? false : true;
        }
        return true;
    }
}
