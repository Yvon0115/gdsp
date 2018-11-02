package com.gdsp.ptbase.portal.web;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gdsp.dev.core.common.AppConfig;
import com.gdsp.dev.web.mvc.ext.ViewDecorate;
import com.gdsp.dev.web.mvc.ext.ViewWrapper;
import com.gdsp.ptbase.portal.helper.PortalHelper;
import com.gdsp.ptbase.portal.model.IPortlet;
import com.gdsp.ptbase.portal.model.IPortletMeta;

/**
 * 默认的portlet处理类
 *
 * @author paul.yang
 * @version 1.0 2015-7-30
 * @since 1.6
 */
@Controller
@RequestMapping("portal")
public class DefaultPorletController {

    //@Resource
    //private IKpiService kpiService;
    /**
     * 默认的portlet模板
     */
    private String defaultPortletTemplate = null;

    /**
     * 页面加载
     * @return 页面路径
     */
    @RequestMapping(value = "/portlet/default.d")
    @ViewWrapper(wrapped = false)
    @ViewDecorate(decorate = false)
    public String defaultHandle(Model model) {
        IPortlet portlet = PortalHelper.getCurrentPortlet();
        String url = portlet.getTemplate();
        IPortletMeta meta = portlet.getMeta();
        //处理报表说明显示
        // KpiVO kpivo = kpiService.findKpiVOByWidgetID(meta.getPortletId());
        // model.addAttribute("kpivo", kpivo);
        if (StringUtils.isNotEmpty(url))
            return url;
        return getDefaultPortletTemplate();
    }

    /**
     * 取得默认模板
     * @return 默认模板路径
     */
    protected String getDefaultPortletTemplate() {
        if (defaultPortletTemplate == null) {
            defaultPortletTemplate = AppConfig.getInstance().getString(PortalHelper.OPTIONS_DEFAULTPORTLETTEMPLATE, PortalHelper.DEFAULT_PORTLETEMPLATE);
        }
        return defaultPortletTemplate;
    }
}
