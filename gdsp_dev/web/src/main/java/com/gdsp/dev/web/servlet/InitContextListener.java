package com.gdsp.dev.web.servlet;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.gdsp.dev.base.exceptions.DevRuntimeException;
import com.gdsp.dev.core.common.AppConfig;
import com.gdsp.dev.core.common.AppContext;
import com.gdsp.dev.core.common.IAppModule;

/**
 * servlet上下文监听器
 * @author paul.yang
 * @version 1.0 2014年9月24日
 * @since 1.6
 */
public class InitContextListener implements ServletContextListener {

    /**
     * 日志对象
     */
    protected static Logger logger = LoggerFactory.getLogger(InitContextListener.class);

    /* (non-Javadoc)
     * @see javax.servlet.ServletContextListener#contextInitialized(javax.servlet.ServletContextEvent)
     */
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext context = sce.getServletContext();
        String contextPath = context.getContextPath();
        if ("/".equals(contextPath)) {
            AppContext.setContextPath("");
        } else {
            AppContext.setContextPath(contextPath);
        }
        String realPath = context.getRealPath("/");
        AppContext.setAppRealPath(realPath);
        /**
         * 设置主题变量
         */
        String theme = AppConfig.getInstance().getString("theme");
        if (StringUtils.isNotBlank(theme)) {
            context.setAttribute(AppContext.THEME_KEY, theme);
        }
        WebApplicationContext springContext = WebApplicationContextUtils.getWebApplicationContext(context);
        AppContext.setApplicationContext(springContext);
        //启动时执行模块初始化
        Map<String, IAppModule> moduleMap = springContext.getBeansOfType(IAppModule.class);
        if (moduleMap == null)
            return;
        Map<String, String> deadLockMap = new HashMap<String, String>();
        Set<String> moduleNames = moduleMap.keySet();
        for (String moduleName : moduleNames) {
            init(moduleName, moduleMap, deadLockMap);
        }
        System.out.println("*************WEBAPP INFO*****************");
        System.out.println("REAL PATH:   '" + realPath + "'");
        System.out.println("CONTEXT PATH:'" + contextPath + "'");
        System.out.println("*****************************************");
    }

    /**
     * 初始化模块
     * @param moduleName 模块名
     * @param moduleMap 所有模块名称到模块的映射
     * @param deadLockMap 防死锁Map
     */
    protected void init(String moduleName, Map<String, IAppModule> moduleMap, Map<String, String> deadLockMap) {
        IAppModule module = moduleMap.get(moduleName);
        if (module == null || module.isInit())
            return;
        deadLockMap.put(moduleName, moduleName);
        String[] dependModuleNames = module.getDependModules();
        if (dependModuleNames != null && dependModuleNames.length == 0) {
            for (String dependModuleName : dependModuleNames) {
                if (deadLockMap.containsKey(dependModuleName))
                    throw new DevRuntimeException("模块依赖存在死锁！");
                init(dependModuleName, moduleMap, deadLockMap);
            }
        }
        module.init();
        deadLockMap.remove(moduleName);
    }

    /* (non-Javadoc)
     * @see javax.servlet.ServletContextListener#contextDestroyed(javax.servlet.ServletContextEvent)
     */
    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        Map<String, IAppModule> moduleMap = AppContext.getContext().getBeansOfType(IAppModule.class);
        if (moduleMap == null)
            return;
        Collection<IAppModule> modules = moduleMap.values();
        for (IAppModule module : modules) {
            if (module == null)
                continue;
            module.destory();
        }
    }
}
