package com.gdsp.dev.web.context;

import java.util.Set;

import com.gdsp.dev.base.utils.data.VariantSet;
import com.gdsp.dev.core.common.AppContext;

/**
 * 基于Web的View层上下文
 * @author paul.yang
 * @version 1.0 2014年9月24日
 * @since 1.6
 */
public class HttpViewContext {

    /**缓存在HttpServletRequest中的键值*/
    private static final String CONTEXT_ATTRIBUTE = HttpViewContext.class.getName();
    /**View级属性变量*/
    VariantSet                  properties        = null;
    /**应用上下文*/
    AppContext                  context           = null;

    /**
     * 构造方法，使用系统应用上下文构造
     * @param context 系统应用上下文
     */
    public HttpViewContext(AppContext context) {
        this.context = context;
        properties = new VariantSet();
    }

    /**
     * 取得缓存在HttpServletRequest中的键值
     * @param context 应用上下文
     * @return 键值串
     */
    private static String getRequestKey(AppContext context) {
        String namespace = "$";
        return CONTEXT_ATTRIBUTE + "." + namespace;
    }

    /**
     * 取得View上下文对象，此时使用当前全局应用上下文
     * @return View上下文对象
     */
    public static HttpViewContext getViewContext() {
        return getViewContext(null);
    }

    /**
     * 根据指定应用上下文取得View上下文对象；
     * @param context 应用上下文
     * @return 取得View上下文对象
     */
    public static HttpViewContext getViewContext(AppContext context) {
        if (context == null)
            context = AppContext.getContext();
        String contextKey = getRequestKey(context);
        HttpViewContext viewContext = (HttpViewContext) context.getAttribute(AppContext.REQUEST, contextKey);
        if (viewContext == null) {
            viewContext = new HttpViewContext(context);
            context.setAttribute(AppContext.REQUEST, contextKey, viewContext);
            return viewContext;
        } else {
            return viewContext;
        }
    }

    /**
     * 取得View级所有属性名
     * @return 属性名集合
     */
    public Set<String> getAttributeNames() {
        return properties.getVariantNames();
    }

    /**
     * 取得指定的属性值
     * @param name 属性名
     * @return 属性值
     */
    public Object getAttribute(String name) {
        return properties.getValue(name);
    }

    /**
     * 设置View级属性值
     * @param name 属性名
     * @param value 属性值
     */
    public void setAttribute(String name, Object value) {
        properties.setValue(name, value);
    }

    /**
     * 移除指定View集属性
     * @param name 属性名
     */
    public void removeAttribute(String name) {
        properties.remove(name);
    }

    /**
     * 取得View的属性对象
     * @return
     */
    public VariantSet properties() {
        return properties;
    }
}
