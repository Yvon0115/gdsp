package com.gdsp.dev.web.mvc.view;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.view.freemarker.FreeMarkerView;

import com.gdsp.dev.core.common.AppConfig;
import com.gdsp.dev.core.common.AppContext;
import com.gdsp.dev.web.mvc.ext.ViewTab;
import com.gdsp.dev.web.mvc.model.ContextVarsHelper;
import com.gdsp.dev.web.mvc.tags.IFreemarkerTagFactory;
import com.gdsp.dev.web.mvc.tags.TagModel;
import com.gdsp.dev.web.utils.ActionUtils;
import com.gdsp.dev.web.utils.WebUtils;

import freemarker.ext.servlet.FreemarkerServlet;
import freemarker.ext.servlet.IncludePage;
import freemarker.template.SimpleHash;

/**
 * 扩展ftl的上下文
 * @author paul.yang
 * @version 1.0 2014年10月20日
 * @since 1.6
 */
public class CustomFreeMarkerView extends FreeMarkerView {

    /**
     * 扩展的freemarker标签
     */
    private Map<String, Map<String, TagModel>> tagModels;

    protected SimpleHash buildTemplateModel(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response) {
        SimpleHash fmModel = super.buildTemplateModel(model, request, response);
        AppContext context = AppContext.getContext();
        fmModel.put("Context", context);
        fmModel.put("ContextPath", AppContext.getContextPath());
        fmModel.put("__httpServer", WebUtils.getHttpServer());
        fmModel.put("__cssPath", WebUtils.getCssPath());
        fmModel.put("__imagePath", WebUtils.getImagePath());
        fmModel.put("__htmlPath", WebUtils.getHtmlPath());
        fmModel.put("__themePath", WebUtils.getThemePath());
        fmModel.put("__scriptPath", WebUtils.getScriptPath());
        fmModel.put("__jsPath", WebUtils.getJsPath());
        fmModel.put("__uploadPath", WebUtils.getUploadPath());
        fmModel.put("utils", ActionUtils.getInstance());
        initTabsParam(fmModel, request);
        //fmModel.put("Cookies", request.getCookies()[0].g);
        fmModel.putAll(ContextVarsHelper.getInstance().getVars());
        fmModel.putAll(getTags());
        //jsp include init
        fmModel.put(FreemarkerServlet.KEY_INCLUDE, new IncludePage(request, response));
        return fmModel;
    }

    /**
     * 处理Tabs参数
     * @param fmModel
     * @param request
     */
    private void initTabsParam(SimpleHash fmModel, HttpServletRequest request) {
        fmModel.put("__modeRefreshMain", "no");
        String layoutTabMode = request.getParameter(ViewTab.PARAM_LAYOUOTMODE);
        if (ViewTab.MODE_REFRESH_MAIN.equals(layoutTabMode) && "tabs".equals(AppConfig.getProperty("view.breadCrumbType"))) {
            //设置是否显示页签模式
            fmModel.put("__modeRefreshMain", "yes");
            //设置初始Tab参数
            String tabUrl = (String) request.getAttribute(ViewTab.TAB_URL);
            fmModel.put(ViewTab.TAB_URL, tabUrl);
            String tabId = (String) request.getAttribute(ViewTab.TAB_ID);
            fmModel.put(ViewTab.TAB_ID, tabId);
            String tabTitle = (String) request.getAttribute(ViewTab.TAB_TITLE);
            fmModel.put(ViewTab.TAB_TITLE, tabTitle);
        }
    }

    /**
     * 取得扩展标签集
     * @return 扩展标签集
     */
    protected Map<String, Map<String, TagModel>> getTags() {
        if (tagModels == null) {
            tagModels = new HashMap<>();
            Map<String, IFreemarkerTagFactory> beans = AppContext.getContext().getBeansOfType(IFreemarkerTagFactory.class);
            if (beans == null)
                return tagModels;
            for (IFreemarkerTagFactory f : beans.values()) {
                String ns = f.getNamespace();
                Map<String, TagModel> tagMap = tagModels.get(ns);
                if (tagMap == null) {
                    tagMap = new HashMap<>();
                    tagModels.put(ns, tagMap);
                }
                String[] tagNames = f.getTags();
                for (String tagName : tagNames) {
                    tagMap.put(tagName, new TagModel(tagName, f));
                }
            }
        }
        return tagModels;
    }
}
