package com.gdsp.dev.web.mvc.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.UrlBasedViewResolver;

import com.gdsp.dev.base.lang.DBoolean;
import com.gdsp.dev.base.utils.clazz.ClassEasyUtils;
import com.gdsp.dev.core.common.AppConfig;
import com.gdsp.dev.core.common.AppContext;
import com.gdsp.dev.web.context.HttpContextFactory;
import com.gdsp.dev.web.mvc.ext.ViewDecorate;
import com.gdsp.dev.web.mvc.ext.ViewTab;
import com.gdsp.dev.web.mvc.ext.ViewWrapper;
import com.gdsp.dev.web.mvc.model.OpenMode;
import com.gdsp.dev.web.scanner.Interceptor;
import com.gdsp.dev.web.utils.HttpUtils;

/**
 * 上下文拦截器
 * @author paul.yang
 * @version 1.0 2014年9月24日
 * @since 1.6
 */
@Interceptor("/**/*.d")
@Order(Ordered.HIGHEST_PRECEDENCE)
public class DefaultMvcInterceptor implements HandlerInterceptor {

    /**
     * 默认的View包装文件
     */
    private String defaultWrapperView        = "~";
    /**
     * 是否默认包装截面
     */
    private int    defaultWrapper            = -1;
    /**
     * 默认的View修饰文件
     */
    private String defaultDecorateView       = "~";
    /**
     * 默认的在iframe中的View修饰文件
     */
    private String defaultIframeDecorateView = "~";
    /**
     * 是否默认修饰截面
     */
    private int    defaultDecorate           = -1;

    /* (non-Javadoc)
     * @see org.springframework.web.servlet.HandlerInterceptor#preHandle(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, java.lang.Object)
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
            Object handler)  {
        if (HttpUtils.isInclude(request))
            return true;
        HttpContextFactory.getContext(request, response);
        return true;
    }

    /* (non-Javadoc)
     * @see org.springframework.web.servlet.HandlerInterceptor#postHandle(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, java.lang.Object, org.springframework.web.servlet.ModelAndView)
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response,
            Object handler, ModelAndView mv)  {
        String contentType = response.getContentType();
        if (HttpUtils.isInclude(request) || contentType != null && !contentType.toLowerCase().startsWith("text/html"))
            return;
        if (mv == null)
            return;
        String viewName = mv.getViewName();
        if(viewName.contains("redirect:/homepage.d")){//使用多页签情况下会把首页处理为多页签，此处去掉多页签处理参数
        	mv.setViewName("redirect:/homepage.d");
        }
        //redirect、forward不做包装处理
        if (viewName.startsWith(UrlBasedViewResolver.REDIRECT_URL_PREFIX) || viewName.startsWith(UrlBasedViewResolver.FORWARD_URL_PREFIX))
            return;
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        boolean isAjax = HttpContextFactory.getContext(request, response).isAjaxRequest();
        String tabMode = request.getParameter(ViewTab.PARAM_LAYOUOTMODE);
        handleWrapper(handlerMethod, mv, isAjax, tabMode);
        OpenMode mode = OpenMode.WIN;
        if (isAjax) {
            mode = OpenMode.AJAX;
        } else {
            String iframe = request.getParameter("__iframe");
            if (StringUtils.isEmpty(iframe)) {
                iframe = request.getHeader("__iframe");
            }
            if (DBoolean.valueOf(iframe).booleanValue())
                mode = OpenMode.IFRAME;
        }
        handleDecorate(handlerMethod, mv, mode, tabMode);
    }

    /* (non-Javadoc)
     * @see org.springframework.web.servlet.HandlerInterceptor#afterCompletion(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, java.lang.Object, java.lang.Exception)
     */
    @Override
    public void afterCompletion(HttpServletRequest request,
            HttpServletResponse response, Object handler, Exception e)
             {
        if (HttpUtils.isInclude(request))
            return;
        AppContext.registerContext(null);
    }

    /**
     * 处理包装类
     * @param handlerMethod spingmvc处理方法
     * @param mv 模型和视图对象
     * @param isAjax 是否为ajax模式
     */
    protected void handleWrapper(HandlerMethod handlerMethod, ModelAndView mv, boolean isAjax, String tabMode) {
        //非tabs模式需要根据情况进行包裹，tabs模式下刷新全界面需要包裹
        if (!"tabs".equals(AppConfig.getProperty("view.breadCrumbType"))) {
            String viewName = mv.getViewName();
            ViewWrapper wrapper = handlerMethod.getMethodAnnotation(ViewWrapper.class);
            //默认不做修饰处理，包装方法注解不存在，则不做修饰
            //如果为指定修饰的view则使用默认的
            String newViewName = null;
            boolean isWrapper = false;
            if (wrapper != null && (wrapper.onlyAjax() && isAjax || !wrapper.onlyAjax())) {
                if (!wrapper.wrapped())
                    return;
                newViewName = wrapper.value();
                if (StringUtils.isNotEmpty(newViewName)) {
                    mv.addObject("__wrapperContent", viewName);
                    mv.setViewName(newViewName);
                    return;
                }
                isWrapper = true;
            }
            ViewWrapper classWrapper = handlerMethod.getClass().getAnnotation(ViewWrapper.class);
            if (classWrapper != null && (classWrapper.onlyAjax() && isAjax || !classWrapper.onlyAjax())) {
                if (!classWrapper.wrapped() && !isWrapper)
                    return;
                if (newViewName == null) {
                    newViewName = classWrapper.value();
                }
                //是否默认做修饰处理
            } else if (!isDefaultWrapperView() && !isWrapper) {
                return;
            }
            if (StringUtils.isEmpty(newViewName))
                newViewName = getDefaultWrapperView();
            if (StringUtils.isEmpty(newViewName))
                return;
            mv.addObject("__wrapperContent", viewName);
            mv.setViewName(newViewName);
        } else if ("tabs".equals(AppConfig.getProperty("view.breadCrumbType")) && ViewTab.MODE_REFRESH_MAIN.equals(tabMode)) {
            String viewName = mv.getViewName();
            mv.addObject("__wrapperContent", viewName);
            mv.setViewName(getDefaultTabsWrapperView());
        } else {
            //其他方式不进行包装
        }
    }

    /**
     * 处理修饰类
     * @param handlerMethod spingmvc处理方法
     * @param mv 模型和视图对象
     * @param mode          打开方式
     */
    protected void handleDecorate(HandlerMethod handlerMethod, ModelAndView mv, OpenMode mode, String tabMode) {
        //ajax请求不做包装处理
        if (mode == OpenMode.AJAX) {
            return;
        }
        //先处理方法上的包装注解，如果不存在，则再处理类上的包装注解
        ViewDecorate decorate = handlerMethod.getMethodAnnotation(ViewDecorate.class);
        //包装方法注解不存在，则不做修饰
        //如果为指定修饰的view则使用默认的
        String viewName = mv.getViewName();//实际请求页面
        String newViewName = null;//包装模板页面
        boolean isDecorate = false;//是否要进行包装的标识，默认为不做修饰处理
        if (decorate != null) {
            //判断是否要进行包装
            if (!decorate.decorate()) {
                return;
            }
            newViewName = decorate.value();
            //如果指定了包装模板，则按指定的模板走
            if (StringUtils.isNotEmpty(newViewName)) {
                mv.addObject("__decorateContent", viewName);
                mv.setViewName(newViewName);
                //进一步包装处理
                moreDecoratorHandle(mv);
                return;
            }
            //如果未指定模板，则进行下一步处理
            isDecorate = true;
        }
        //方法注解处理结束
        //开始进行类注解处理
        ViewDecorate classDecorate = handlerMethod.getClass().getAnnotation(ViewDecorate.class);
        if (classDecorate != null) {
            //如果类和方法都不进行包装处理，则直接返回
            if (!classDecorate.decorate() && !isDecorate) {
                return;
            }
            //获取类的包装模板
            newViewName = classDecorate.value();
        } else if (!isDecorate && !isDefaultDecorateView()) {
            //当方法和类都不添加包装注解时，读取系统变量看是否默认启用包装修饰，如不启用，则返回
            return;
        }
        //类包装注解处理结束
        //多页签与单页面的逻辑处理
        if ("tabs".equals(AppConfig.getProperty("view.breadCrumbType")) && ViewTab.MODE_REFRESH_MAIN.equals(tabMode)
                || !"tabs".equals(AppConfig.getProperty("view.breadCrumbType"))) {
            //单页面时如果未指定包装模板，则获取默认模板
            if (StringUtils.isEmpty(newViewName)) {
                newViewName = getDefaultDecorateView(mode);
            }
        } else {
            //多页签使用新包裹
            if (StringUtils.isEmpty(newViewName)) {
                newViewName = getDefaultTabsDecorateView(mode);
            }
        }
        if (StringUtils.isEmpty(newViewName)) {
            return;
        }
        mv.addObject("__decorateContent", viewName);
        mv.setViewName(newViewName);
        //进一步包装处理
        moreDecoratorHandle(mv);
    }

    /**
     * 更多的包装数据处理
     * @param mv
     */
    private void moreDecoratorHandle(ModelAndView mv) {
        String key = mv.getViewName();
        //根据包装模板路径，获取对应的处理类
        String decoratorStr = AppConfig.getProperty(key);
        if (StringUtils.isNotEmpty(decoratorStr)) {
            IDecorator decorator = (IDecorator) ClassEasyUtils.newInstance(decoratorStr);
            if (decorator != null) {
                decorator.handleModelAndView(mv);
            }
        }
    }

    protected String getDefaultWrapperView() {
        if ("~".equals(defaultWrapperView)) {
            defaultWrapperView = AppConfig.getInstance().getString("view.defaultWrapper");
        }
        return defaultWrapperView;
    }

    protected String getDefaultTabsWrapperView() {
        return AppConfig.getInstance().getString("view.defaultTabsWrapper");
    }

    protected boolean isDefaultWrapperView() {
        if (defaultWrapper == -1)
            defaultWrapper = AppConfig.getInstance().getBoolean("view.isWrapper", true) ? 1 : 0;
        return defaultWrapper == 1;
    }

    protected String getDefaultTabsDecorateView(OpenMode mode) {
        return AppConfig.getInstance().getString("view.defaultTabsDecorate");
    }

    protected String getDefaultDecorateView(OpenMode mode) {
        if (mode == OpenMode.IFRAME) {
            if ("~".equals(defaultIframeDecorateView)) {
                defaultIframeDecorateView = AppConfig.getInstance().getString("view.defaultIframeDecorate");
            }
            if (StringUtils.isNotEmpty(defaultIframeDecorateView))
                return defaultIframeDecorateView;
        }
        if ("~".equals(defaultDecorateView)) {
            defaultDecorateView = AppConfig.getInstance().getString("view.defaultDecorate");
        }
        return defaultDecorateView;
    }

    /**
     * 当方法和类都不添加包装注解时，则读取系统变量看是否默认启用包装修饰
     * @return
     */
    protected boolean isDefaultDecorateView() {
        if (defaultDecorate == -1)
            defaultDecorate = AppConfig.getInstance().getBoolean("view.isDecorate", true) ? 1 : 0;
        return defaultDecorate == 1;
    }
}
