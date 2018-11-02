package com.gdsp.dev.core.common;

import java.io.Serializable;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 应用访问上下文类，主要负责上下文的属性和参数的操作
 * @author paul.yang
 * @version 1.0 2014年9月24日
 * @since 1.6
 */
public abstract class AppContext implements Serializable {

    /**
     * 序列id
     */
    private static final long                  serialVersionUID   = 697592850956445675L;
    /*上下文属性所属范围*/
    /**请求对象属性范围*/
    public static final int                    REQUEST            = 1;
    /**视图对象属性范围*/
    public static final int                    VIEW               = 2;
    /**会话对象属性范围*/
    public static final int                    SESSION            = 3;
    /**应用对象属性范围*/
    public static final int                    APPLICATION        = 4;
    /**
     * 上个下问前缀
     */
    private static final String                CONTEXT_PROFIX     = AppContext.class.getSimpleName().toUpperCase() + "_";
    /**数据源名键值*/
    public static final String                 DATASOURCE_KEY     = CONTEXT_PROFIX + "DATASOURCENAME";
    /**主题键值*/
    public static final String                 THEME_KEY          = CONTEXT_PROFIX + "_THEME";
    /**
     * 用户键值
     */
    public static final String                 USER_KEY           = "__loginmember";
    /**
     * Theme parameter key  used to set current theme  by http request parameter
     */
    public static final String                 PARAMKEY_THEME     = "__theme";
    /**
     * Locale parameter key  used to set current locale  by http request parameter
     */
    public static final String                 PARAMKEY_LOCALE    = "__locale";
    /**
     * 日志变量
     */
    protected static final Logger                    logger             = LoggerFactory.getLogger(AppContext.class);
    /**线程变量*/
    private static ThreadLocal<AppContext>     contextLocal       = new ThreadLocal<AppContext>();
    /**上下文路径*/
    private static String                      contextPath        = "/";
    /**
     * 上下文路径对应的真实路径,即应用的真实路径
     */
    private static String                      appRealPath        = null;
    /**区域设置*/
    public static final String                 CONTEXT_KEY_LOCALE = AppContext.class.getPackage().getName() + "contextLocale";
    /**
     * spring的应用上下文
     */
    private volatile static ApplicationContext applicationContext = null;
    /**
     * 当前应用是否处于开发态
     */
    private volatile static Boolean            isDevelop          = null;
    /**
     * Temp data source,Usually as once invoke database,after invoke,it will clear to null
     */
    private String                             dataSource         = null;
    /**
     * 请求信息
     */
    private RequestInfo                        requestInfo;

    /**
     * 构造方法
     */
    public AppContext() {}

    /**
     * 注册当前上下文对象
     * @param context 上下文对象
     */
    public static void registerContext(AppContext context) {
        contextLocal.set(context);
    }

    /**
     * 取得当前线程中的上下文对象
     * @return 上下文对象
     */
    public static AppContext getContext() {
        AppContext context = (AppContext) contextLocal.get();
        //当上下文为空时使用默认上下文
        if (context != null)
            return context;
        synchronized (contextLocal) {
            context = (AppContext) contextLocal.get();
            if (context != null)
                return context;
            context = new DefaultAppContext();
            registerContext(context);
        }
        return context;
    }

    /**
     * 反注册当前上下文对象
     */
    public static void unregisterContext() {
        contextLocal.remove();
    }

    /**
     * 判断当前项目是否属于开发状态
     * @return 布尔值
     */
    public static boolean isDevelop() {
        if (isDevelop == null) {
            String dev = System.getProperty("project.develop");
            if (dev == null) {
                isDevelop = Boolean.FALSE;
            } else {
                isDevelop = "TRUE".equalsIgnoreCase(dev) || "Y".equalsIgnoreCase(dev) ? Boolean.TRUE : Boolean.FALSE;
            }
        }
        return isDevelop.booleanValue();
    }

    /**
     * 取得上下文区域设置
     * @return 区域对象
     */
    public Locale getLocale() {
        String l = getParameter(PARAMKEY_LOCALE);
        Locale locale = null;
        if (StringUtils.isNotEmpty(l)) {
            locale = CommonHelper.getLocale(l);
            if (locale != null) {
                return locale;
            }
        }
        locale = (Locale) getAttribute(CONTEXT_KEY_LOCALE);
        if (locale == null)
            locale = CommonHelper.getDefaultLocale();
        return locale;
    }

    /**
     * 取得上下文区域设置
     * @return 区域对象
     */
    public Locale getScopeLocale(int scope) {
        return (Locale) getAttribute(scope, CONTEXT_KEY_LOCALE);
    }

    /**
     * 取得上下文区域设置
     * @return 区域对象
     */
    public void setScopeLocale(int scope, Locale locale) {
        setAttribute(scope, CONTEXT_KEY_LOCALE, locale);
    }

    /**
     * 取得数据源名称
     * @return 数据源名称
     */
    public String getDataSource() {
        if (StringUtils.isNotEmpty(dataSource))
            return dataSource;
        return (String) getAttribute(DATASOURCE_KEY);
    }

    /**
     * Set temp data source name
     * @param dataSource temp data source name
     */
    public void setDataSource(String dataSource) {
        this.dataSource = dataSource;
    }

    /**
     * 取得指定范围数据源名称
     * 
     * @return 数据源名称
     */
    public String getScopeDataSource(int scope) {
        return (String) getAttribute(scope, DATASOURCE_KEY);
    }

    /**
     * 设置指定范围的数据源
     * @param scope 上下文范围
     * @param datasource 数据源
     */
    public void setScopeDataSource(int scope, String datasource) {
        setAttribute(scope, DATASOURCE_KEY, datasource);
    }

    /**
     * 取得当前主题名称
     * @return 主题名称
     */
    public String getTheme() {
        String theme = getParameter(PARAMKEY_THEME);
        if (StringUtils.isNotEmpty(theme))
            return theme;
        theme = (String) getAttribute(THEME_KEY);
        if (theme == null)
            theme = CommonHelper.getDefaultTheme();
        return theme;
    }

    /**
     * 取得指定范围主题名称
     * @param scope 上下文范围
     * @return 主题名称
     */
    public String getScopeTheme(int scope) {
        return (String) getAttribute(scope, THEME_KEY);
    }

    /**
     * 设置指定范围的主题名称
     * @param scope 上下文范围
     * @param theme 主题名称
     */
    public void setScopeTheme(int scope, String theme) {
        setAttribute(scope, THEME_KEY, theme);
    }

    /**
     * 取得上下文路径
     * @return 上下文路径
     */
    public static String getContextPath() {
        return contextPath;
    }

    /**
     * 取得上下文路径
     * @param path 上下文路径
     */
    public static void setContextPath(String path) {
        contextPath = path;
    }

    /**
     * 取得应用上下文路径名
     * @return 应用上下文路径名
     */
    public String getAppContextPath() {
        return getContextPath();
    }

    /**
     * 取得应用的绝对路径
     * @return 应用的绝对路径
     */
    public static String getAppRealPath() {
        return appRealPath;
    }

    /**
     * 设置应用的绝对路径
     * @param realPath 应用的绝对路径
     */
    public static void setAppRealPath(String realPath) {
        appRealPath = realPath;
    }

    /**
     * 在所有属性范围内按照从从小到达顺序取得指定名称的属性值
     * @param name 指定名称
     * @return 属性值
     */
    public Object getAttribute(String name) {
        Object value = getAttribute(REQUEST, name);
        if (value != null)
            return value;
        value = getAttribute(VIEW, name);
        if (value != null)
            return value;
        value = getAttribute(SESSION, name);
        if (value != null)
            return value;
        value = getAttribute(APPLICATION, name);
        return value;
    }

    /**
     * 取得系统配置信息
     * @return 系统配置信息
     */
    public AppConfig getConfig() {
        return AppConfig.getInstance();
    }

    /**
     * 确定应用级上下文
     * @return 应用上下文
     */
    public abstract Object getApplication();

    /**
     * 确定会话级上下文
     * @return 会话级上下文
     */
    public abstract Object getSession();

    /**
     * 确定页面级上下文
     * @return 页面级上下文
     */
    public abstract Object getViewContext();

    /**
     * 确定请求级上下文
     * @return 请求级上下文
     */
    public abstract Object getRequest();

    /**
     * 根据参数名取得参数值
     * @param name 参数名
     * @return 参数值
     */
    public abstract String getParameter(String name);

    /**
     * 根据参数名取得对应的参数数组值
     * @param name 参数名
     * @return 参数数组
     */
    public abstract String[] getParameters(String name);

    /**
     * 根据参数名取得对应的参数映射
     * @return 参数映射
     */
    public abstract Map<String, String[]> getParameterMap();

    /**
     * 取得指定范围内指定名称的属性值
     * @param scope 指定属性范围
     * @param name 属性名称
     * @return 属性值
     */
    public abstract Object getAttribute(int scope, String name);

    /**
     * 设置指定范围的属性值
     * @param scope 指定范围
     * @param name 属性名
     * @param value 属性值
     */
    public abstract void setAttribute(int scope, String name, Object value);

    /**
     * 移除指定范围内指定名称的属性
     * @param scope 指定范围
     * @param name 指定名称
     */
    public abstract void removeAttribute(int scope, String name);

    /**
     * 判断指定范围的属性是否为空
     * @param scope 指定范围
     * @return 布尔值
     */
    public abstract boolean isEmptyInAttributes(int scope);

    /**
     * 判断上下文所有范围属性是否为空
     * @return 布尔值
     */
    public abstract boolean isEmpty();

    /**
     * 根据类名搜索相应的bean对象
     * @param <T> 类模板标识返回值类型
     * @param clazz 模板类
     * @return bean对象
     */
    public <T> T lookup(Class<T> clazz) {
        if (getBeanContext() == null)
            return null;
        try {
            return getBeanContext().getBean(clazz);
        } catch (NoSuchBeanDefinitionException e) {
            logger.debug(e.getMessage(),e);
            return null;
        }
    }

    /**
     * 根据类型取得所有
     * @param type 接口类
     * @return 取得所有的实现bean
     */
    public <T> Map<String, T> getBeansOfType(Class<T> type) {
        return getBeanContext().getBeansOfType(type);
    }

    /**
     * 根据bean名称和bean类型搜索相应的bean对象
     * @param <T> 类模板标识返回值类型
     * @param beanName bean名称
     * @param clazz 模板类
     * @return bean对象
     */
    public <T> T lookup(String beanName, Class<T> clazz) {
        if (getBeanContext() == null)
            return null;
        try {
            return getBeanContext().getBean(beanName, clazz);
        } catch (NoSuchBeanDefinitionException e) {
            logger.debug(e.getMessage(),e);
            return null;
        }
    }

    /**
     * 根据bean名称搜索相应的bean对象
     * @param beanName bean名称
     * @return bean对象
     */
    public Object lookup(String beanName) {
        if (getBeanContext() == null)
            return null;
        try {
            return getBeanContext().getBean(beanName);
        } catch (NoSuchBeanDefinitionException e) {
            logger.debug(e.getMessage(),e);
            return null;
        }
    }

    /**
     * 取得Bean的上下文对象
     * @return Bean的上下文对象
     */
    public ApplicationContext getBeanContext() {
        return getApplicationContext();
    }

    /**
     * 根据类名搜索相应的bean对象
     * @param <T> 类模板标识返回值类型
     * @param clazz 模板类
     * @return bean对象
     */
    public static <T> T lookupBean(Class<T> clazz) {
        if (getApplicationContext() == null)
            return null;
        try {
            return getApplicationContext().getBean(clazz);
        } catch (NoSuchBeanDefinitionException e) {
            logger.debug(e.getMessage(),e);
            return null;
        }
    }

    /**
     * 根据类型取得所有
     * @param type 接口类
     * @return 取得所有的实现bean
     */
    public static <T> Map<String, T> getBeansMapOfType(Class<T> type) {
        return getApplicationContext().getBeansOfType(type);
    }

    /**
     * 根据bean名称和bean类型搜索相应的bean对象
     * @param <T> 类模板标识返回值类型
     * @param beanName bean名称
     * @param clazz 模板类
     * @return bean对象
     */
    public static <T> T lookupBean(String beanName, Class<T> clazz) {
        if (getApplicationContext() == null)
            return null;
        try {
            return getApplicationContext().getBean(beanName, clazz);
        } catch (NoSuchBeanDefinitionException e) {
            logger.debug(e.getMessage(),e);
            return null;
        }
    }

    /**
     * 根据bean名称搜索相应的bean对象
     * @param beanName bean名称
     * @return bean对象
     */
    public Object lookupBean(String beanName) {
        if (getApplicationContext() == null)
            return null;
        try {
            return getApplicationContext().getBean(beanName);
        } catch (NoSuchBeanDefinitionException e) {
            logger.debug(e.getMessage(),e);
            return null;
        }
    }

    /**
     * 取得spring的上下文对象
     * @return spring的上下文对象
     */
    private static ApplicationContext getApplicationContext() {
        if (applicationContext == null) {
            String[] xmls = new String[] { "ApplicationContext.xml" };
            applicationContext = new ClassPathXmlApplicationContext(xmls);
        }
        return applicationContext;
    }

    /**
     * 设置spring上下文
     * @param context spring上下文
     */
    public static void setApplicationContext(ApplicationContext context) {
        AppContext.applicationContext = context;
    }

    /**
     * 取得当前会话用户信息
     * @return 会话用户信息
     */
    public IContextUser getContextUser() {
        return (IContextUser) getAttribute(AppContext.SESSION, USER_KEY);
    }

    /**
     * 设置会话用户信息
     * @param user 会话用户信息
     */
    public void setContextUser(IContextUser user) {
        setAttribute(SESSION, USER_KEY, user);
    }

    /**
     * 设置会话用户id
     * @return  会话用户id
     */
    public String getContextUserId() {
        IContextUser obj = getContextUser();
        if (obj == null)
            return null;
        return obj.getId();
    }

    /**
     * 设置会话用户账号
     * @return  会话用户账号
     */
    public String getContextAccount() {
        IContextUser obj = getContextUser();
        if (obj == null)
            return null;
        return obj.getAccount();
    }

    /**
     * 取得页面相关信息
     * @return 页面相关信息
     */
    public RequestInfo getRequestInfo() {
        return requestInfo;
    }

    /**
     * 设置页面相关信息
     * @param pageInfo 页面相关信息
     */
    public void setRequestInfo(RequestInfo pageInfo) {
        this.requestInfo = pageInfo;
    }
}
