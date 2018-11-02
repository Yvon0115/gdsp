package com.gdsp.dev.web.context;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gdsp.dev.core.common.AppContext;

/**
 * Http上下文工厂类
 * @author paul.yang
 * @version 1.0 2014年9月24日
 * @since 1.6
 */
public final class HttpContextFactory {

    /**
     * 上下文在Request中的键值
     */
    public static final String CONTEXT_KEY = AppContext.class.getName();

    /**
     * 构造方法
     */
    private HttpContextFactory() {}

    /**
     * 取得Http上下文对象
     * @param request 请求对象
     * @return http上下文对象
     */
    public static HttpAppContext getContext(HttpServletRequest request, HttpServletResponse response) {
        HttpAppContext context;
        if (request == null) {
            context = new HttpAppContext();
            AppContext.registerContext(context);
            return context;
        }
        context = (HttpAppContext) request.getAttribute(CONTEXT_KEY);
        if (context == null) {
            context = new HttpAppContext();
            context.setRequest(request);
            context.setResponse(response);
            request.setAttribute(CONTEXT_KEY, context);
            AppContext.registerContext(context);
        }
        return context;
    }
}
