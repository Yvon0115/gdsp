/*
 * Copyright(c) FastSpace Software 2014. All Rights Reserved.
 */
package com.gdsp.dev.server.project;

import java.io.File;
import java.util.Properties;

import org.eclipse.jetty.webapp.WebAppContext;

/**
 * 基于Web应用的配置信息，一个或多个项目组成一个项目应用
 * @author yaboocn
 * @version 1.0 2014年5月5日
 * @since 1.7
 */
public class WebAppConfig {

    /**
     * web应用路径在配置中的键值
     */
    public final static String KEY_WEBAPPPATH = "webapp.path";
    /**
     * web应用路径是否为相对路径默认为true
     */
    public final static String KEY_ISRELPATH  = "webapp.isrelpath";
    /**
     * 默认的web应用路径
     */
    public final static String DEFAULT_WEBAPP = "/src/main/webapp";
    /**
     * 应用配置所属的项目
     */
    private DevelopProject     project        = null;
    /**
     * web应用路径
     */
    private String             webAppPath     = null;
    /**
     * web相关配置
     */
    private WebConfig          webConfig      = null;
    /**
     * web应用对应的上下文
     */
    private WebAppContext      context        = null;

    /**
     * 构造方法
     */
    public WebAppConfig() {}

    /**
     * 构造方法
     * @param project 开发时的项目
     */
    public WebAppConfig(DevelopProject project) {
        this.project = project;
    }

    /**
     * 取得模块所属的项目
     * @return 所属的项目
     */
    public DevelopProject getProject() {
        return project;
    }

    /**
     * 设置所属的项目
     * @param project 所属的项目
     */
    public void setProject(DevelopProject project) {
        this.project = project;
    }

    /**
     * 取得Web应用的项目相对路径
     * @return Web应用的项目相对路径
     */
    public String getWebAppPath() {
        return webAppPath;
    }

    /**
     * 设置Web应用的项目相对路径
     * @param webAppPath Web应用的项目相对路径
     */
    public void setWebAppPath(String webAppPath) {
        this.webAppPath = webAppPath;
    }

    /**
     * 取得web相关配置，主要是上下文及端口
     * @return web相关配置
     */
    public WebConfig getWebConfig() {
        return webConfig;
    }

    /**
     * 设置web相关配置
     * @param webConfig web相关配置
     */
    public void setWebConfig(WebConfig webConfig) {
        this.webConfig = webConfig;
    }

    /**
     * 取得web应用端口
     * @return web应用端口
     */
    public int getServerPort() {
        return webConfig.getServerPort();
    }

    /**
     * 取得web应用对应的服务器中的上下文对象
     * @return 服务器中的上下文对象
     */
    public WebAppContext getWebAppContext() {
        if (context != null)
            return context;
        context = new DevelopWebAppContext();
        context.setDescriptor(getWebAppPath() + "/WEB-INF/web.xml");
        context.setResourceBase(getWebAppPath());
        context.setContextPath(webConfig.getWebContextPath());
        context.setParentLoaderPriority(true);
        return context;
    }

    /**
     * 加载Web应用配置
     * @param properties 配置属性集
     * @param project 所属项目
     * @return Web应用配置
     */
    public static WebAppConfig loadConfig(Properties properties, DevelopProject project) {
        String webAppPath = properties.getProperty(KEY_WEBAPPPATH, DEFAULT_WEBAPP);
        String isRelPath = properties.getProperty(KEY_ISRELPATH, "true");
        if ("true".equals(isRelPath.toLowerCase())) {
            webAppPath = project.getPath() + webAppPath;
        }
        File webAppFile = new File(webAppPath + "/WEB-INF/web.xml");
        if (!webAppFile.exists())
            return null;
        WebAppConfig config = new WebAppConfig(project);
        config.setWebAppPath(webAppPath);
        WebConfig webConfig = WebConfig.loadConfig(properties, project, true);
        config.setWebConfig(webConfig);
        project.setWebAppConfig(config);
        return config;
    }
}
