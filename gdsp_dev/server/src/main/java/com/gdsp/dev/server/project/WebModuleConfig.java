/*
 * Copyright(c) FastSpace Software 2014. All Rights Reserved.
 */
package com.gdsp.dev.server.project;

import java.io.File;
import java.util.Properties;

/**
 * 基于Web应用的模块级配置选项
 * @author yaboocn
 * @version 1.0 2014年5月5日
 * @since 1.7
 */
public class WebModuleConfig {

    /**
     * 模块web路径在配置中的键值
     */
    public final static String KEY_WEBPATH     = "web.path";
    /**
     * 默认的模块Web路径
     */
    public final static String DEFAULT_WEBPATH = "/src/main/webapp";
    /**
     * 应用配置所属的项目
     */
    private DevelopProject     project         = null;
    /**
     * 项目模块Web路径
     */
    private String             webPath         = null;

    /**
     * 构造方法
     */
    public WebModuleConfig() {}

    /**
     * 构造方法
     * @param project 开发时的项目
     */
    public WebModuleConfig(DevelopProject project) {
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
     * 取得项目模块的Web路径
     * @return 模块的Web路径
     */
    public String getWebPath() {
        return webPath;
    }

    /**
     * 设置模块的Web路径
     * @param webPath 模块的Web路径
     */
    public void setWebPath(String webPath) {
        this.webPath = webPath;
    }

    /**
     * 取得模块Web全路径
     * @return Web全路径
     */
    public String getFullWebPath() {
        return getProject().getPath() + webPath;
    }

    /**
     * 加载Web模块配置
     * @param properties 配置属性集
     * @param project 所属项目
     * @return Web模块配置
     */
    public static WebModuleConfig loadConfig(Properties properties, DevelopProject project) {
        String webPath = properties.getProperty(KEY_WEBPATH, DEFAULT_WEBPATH);
        if (project.isJar()) {
            File warFile = new File(project.getSource().replace(".jar", ".war"));
            if (!warFile.exists()) {
                return null;
            }
        } else {
            File pageDir = new File(project.getPath() + webPath);
            if (!pageDir.exists() || !pageDir.isDirectory())
                return null;
        }
        WebModuleConfig config = new WebModuleConfig(project);
        config.setWebPath(webPath);
        project.setWebModuleConfig(config);
        return config;
    }
}
