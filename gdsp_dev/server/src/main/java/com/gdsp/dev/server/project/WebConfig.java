/*
 * Copyright(c) FastSpace Software 2014. All Rights Reserved.
 */
package com.gdsp.dev.server.project;

import java.util.Properties;

/**
 * web相关配置，webapp配置已包含相关配置，为了同时在一个webapp下不同web模块起多个webapp服务
 * @author yaboocn
 * @version 1.0 2014年5月5日
 * @since 1.7
 */
public class WebConfig {

    /**
     * web应用路径在配置中的键值
     */
    public final static String KEY_WEBCONTEXT     = "webapp.webcontext";
    /**
     * web应用端口在配置中的键值
     */
    public final static String KEY_SERVERPORT     = "webapp.port";
    /**
     * 默认的web应用端口
     */
    public final static int    DEFAULT_SERVERPORT = 8080;
    /**
     * 应用配置所属的项目
     */
    private DevelopProject     project            = null;
    /**
     * Web应用上下文路径
     */
    private String             webContextPath     = null;
    /**
     * web应用的端口，目前所有web应用端口配置中只有一个端口生效
     */
    private int                serverPort         = 0;

    /**
     * 构造方法
     */
    public WebConfig() {}

    /**
     * 构造方法
     * @param project 开发时的项目
     */
    public WebConfig(DevelopProject project) {
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
     * 取得web应用使用的端口
     * @return web应用使用的端口
     */
    public int getServerPort() {
        return serverPort;
    }

    /**
     * 设置web应用使用的端口
     * @param port web应用使用的端口
     */
    public void setServerPort(int port) {
        this.serverPort = port;
    }

    /**
     * 取得Web应用的上下文路径
     * @return Web应用的上下文路径
     */
    public String getWebContextPath() {
        return webContextPath;
    }

    /**
     * 设置Web应用的上下文路径
     * @param webContextPath Web应用的上下文路径
     */
    public void setWebContextPath(String webContextPath) {
        this.webContextPath = webContextPath;
    }

    /**
     * 加载Web应用配置
     * @param properties 配置属性集
     * @param project 所属项目
     * @param useDefault 是否使用默认值
     * @return Web应用配置
     */
    public static WebConfig loadConfig(Properties properties, DevelopProject project, boolean useDefault) {
        if (!useDefault && !properties.containsKey(KEY_WEBCONTEXT) && !properties.containsKey(KEY_SERVERPORT)) {
            return null;
        }
        WebConfig config = new WebConfig(project);
        String contextPath = properties.getProperty(KEY_WEBCONTEXT, project.getModuleName());
        if (!contextPath.startsWith("/")) {
            contextPath = "/" + contextPath;
        }
        config.setWebContextPath(contextPath);
        String serverPort = properties.getProperty(KEY_SERVERPORT, Integer.toString(DEFAULT_SERVERPORT));
        config.setServerPort(Integer.valueOf(serverPort));
        project.setWebConfig(config);
        return config;
    }
}
