package com.gdsp.dev.web.mvc.tags.alib;

import java.io.IOException;
import java.io.Writer;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.web.servlet.view.AbstractTemplateView;

import com.gdsp.dev.base.exceptions.DevRuntimeException;
import com.gdsp.dev.core.common.AppContext;
import com.gdsp.dev.web.context.HttpAppContext;
import com.gdsp.dev.web.mvc.tags.ServletResponseWrapperFreemarkerInclude;
import com.gdsp.dev.web.mvc.tags.TemplateTag;

/**
 * c命名空间下的的Include标签
 *
 * @author paul.yang
 * @version 1.0 2015-7-31
 * @since 1.7
 */
public class IncludeTag implements TemplateTag {
//    private static Logger       logger         = LoggerFactory.getLogger(IncludeTag.class);

    /**
     * portlet配置参数
     */
    public static final String PROP_SRC   = "src";
    /**
     * 配置属性
     */
    Map<String, Object>        properties = null;

    @Override
    public void setProperties(Map<String, Object> properties) {
        this.properties = properties;
    }

    @Override
    public boolean start(Writer writer) throws ServletException, IOException {
        Object propValue = properties.get(PROP_SRC);
        if (propValue == null || propValue.toString().length() == 0)
            throw new DevRuntimeException("Please set valid 'url' property");
        String url = propValue.toString();
        include(url, writer);
        return false;
    }

    /**
     * 通过requestDispatcher include url
     * @param url 请求地址
     */
    protected void include(String url, Writer writer) throws ServletException, IOException {
        HttpAppContext context = (HttpAppContext) AppContext.getContext();
        HttpServletRequest request = context.getRequest();
        Object remain = request.getAttribute(AbstractTemplateView.SPRING_MACRO_REQUEST_CONTEXT_ATTRIBUTE);
        request.removeAttribute(AbstractTemplateView.SPRING_MACRO_REQUEST_CONTEXT_ATTRIBUTE);
        request.getRequestDispatcher(url.trim()).include(request, new ServletResponseWrapperFreemarkerInclude(context.getResponse(), writer));
        if (remain != null) {
            request.setAttribute(AbstractTemplateView.SPRING_MACRO_REQUEST_CONTEXT_ATTRIBUTE, remain);
        }
    }

    @Override
    public boolean end(Writer writer, String body)  {
        return false;
    }

    @Override
    public void close() {}

    @Override
    public boolean useBody() {
        return false;
    }

    @Override
    public boolean isSingleton() {
        return false;
    }
}
