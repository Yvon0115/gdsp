package com.gdsp.dev.web.mvc.view;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import com.gdsp.dev.base.exceptions.DevRuntimeException;
import com.gdsp.dev.core.common.AppContext;
import com.gdsp.dev.web.context.HttpAppContext;
import com.gdsp.dev.web.mvc.model.ContextVarsHelper;
import com.gdsp.dev.web.utils.WebUtils;

import freemarker.ext.servlet.AllHttpScopesHashModel;
import freemarker.ext.servlet.FreemarkerServlet;
import freemarker.ext.servlet.HttpRequestHashModel;
import freemarker.ext.servlet.HttpRequestParametersHashModel;
import freemarker.ext.servlet.HttpSessionHashModel;
import freemarker.ext.servlet.ServletContextHashModel;
import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapperBuilder;
import freemarker.template.ObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateException;

/**
 * Freemark模板助手
 * @author paul.yang
 * @version 1.0 14-12-31
 * @since 1.6
 */
public final class FreemarkerTemplateHelper {

    /**
     * 日志对象
     */
    protected static Logger logger = LoggerFactory.getLogger(FreemarkerTemplateHelper.class);

    /**
     * 模板合并
     * @param model 上下问变量
     * @param path 模板路径
     * @return 合并后的串
     */
	@SuppressWarnings("deprecation")
	public static String merge(Map<String, Object> model, String path) {
        HttpAppContext context = (HttpAppContext) AppContext.getContext();
        FreeMarkerConfigurer configure = context.lookup("freemarkerConfig", FreeMarkerConfigurer.class);
        Configuration config = configure.getConfiguration();
        ObjectWrapper ow = config.getObjectWrapper();
        ow = (ow != null ? ow : new DefaultObjectWrapperBuilder(Configuration.getVersion()).build());
        try {
            Template template = config.getTemplate(path);
            HttpServletRequest request = context.getRequest();
            HttpServletResponse response = context.getResponse();
            HttpSession session = context.getSession();
            ServletContext sc = context.getApplication();
            AllHttpScopesHashModel hashModel = new AllHttpScopesHashModel(ow, sc, request);
            hashModel.put(FreemarkerServlet.KEY_APPLICATION, new ServletContextHashModel(sc, ow));
            hashModel.put(FreemarkerServlet.KEY_SESSION, new HttpSessionHashModel(session, ow));
            hashModel.put(FreemarkerServlet.KEY_REQUEST, new HttpRequestHashModel(request, response, ow));
            hashModel.put(FreemarkerServlet.KEY_REQUEST_PARAMETERS, new HttpRequestParametersHashModel(request));
            hashModel.put("Context", context);
            hashModel.put("ContextPath", AppContext.getContextPath());
            hashModel.put("__httpServer", WebUtils.getHttpServer());
            hashModel.put("__cssPath", WebUtils.getCssPath());
            hashModel.put("__imagePath", WebUtils.getImagePath());
            hashModel.put("__htmlPath", WebUtils.getHtmlPath());
            hashModel.put("__themePath", WebUtils.getThemePath());
            hashModel.put("__scriptPath", WebUtils.getScriptPath());
            hashModel.put("__uploadPath", WebUtils.getUploadPath());
            hashModel.putAll(ContextVarsHelper.getInstance().getVars());
            hashModel.putAll(model);
            StringWriter writer = new StringWriter();
            template.process(hashModel, writer);
            return writer.toString();
        } catch (IOException e) {
            logger.error("模板读取出错", e);
            throw new DevRuntimeException("模板读取出错", e);
        } catch (TemplateException e) {
            logger.error("模板合并出错", e);
            throw new DevRuntimeException("模板合并出错", e);
        }
    }
}
