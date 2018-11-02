package com.gdsp.dev.web.utils;

import org.apache.commons.lang3.StringUtils;

import com.gdsp.dev.core.common.AppConfig;
import com.gdsp.dev.core.common.AppContext;

/**
 * 基于Web前台的工具类
 * @author paul.yang
 * @version 1.0 2014年10月20日
 * @since 1.6
 */
public final class WebUtils {

    /**
     * 默认静态资源服务器地址，包括上下问
     */
    private static String httpServer = null;
    /**
     * html服务器地址
     */
    private static String htmlPath   = null;
    /**
     * 业务脚本资源地址
     */
    private static String scriptPath = null;
    /**
     * 框架脚本资源地址
     */
    private static String jsPath     = null;
    /**
     * 样式表服务器地址
     */
    private static String cssPath    = null;
    /**
     * 图片服务器地址
     */
    private static String imagePath  = null;
    /**
     * 样式主题地址
     */
    private static String themesPath = null;
    /**
     * 上传文件访问地址
     */
    private static String uploadPath = null;

    /**
     * 转换页面路径
     * @param path 页面路径
     * @return 转换后路径
     */
    public static String convertPagePath(String path) {
        return getTheme() + "/page/" + path;
    }

    /**
     * 取得页面所在路径
     * @return 取得页面所在路径
     */
    public static String getTheme() {
        return AppContext.getContext().getTheme();
    }

    /**
     * 取得默认静态资源服务器地址
     * @return 静态资源服务器地址
     */
    public static String getHttpServer() {
        if (httpServer == null) {
            AppConfig appConfig = AppConfig.getInstance();
            httpServer = appConfig.getString("server.http", AppContext.getContextPath());
        }
        return httpServer;
    }

    /**
     * 取得html服务器地址
     * @return html服务器地址
     */
    public static String getHtmlPath() {
        if (StringUtils.isEmpty(htmlPath)) {
            htmlPath = AppConfig.getInstance().getString("server.html", getHttpServer()) + "/" + AppConfig.getInstance().getString("webpath.html", "gdsp/html");
        }
        return htmlPath;
    }

    /**
     * 取得业务脚本资源地址
     * @return 脚本资源地址
     */
    public static String getScriptPath() {
        if (StringUtils.isEmpty(scriptPath)) {
            scriptPath = AppConfig.getInstance().getString("server.script", getHttpServer()) + "/" + AppConfig.getInstance().getString("webpath.script", "gdsp/script");
        }
        return scriptPath;
    }

    /**
     * 取得框架脚本资源地址
     * @return 框架脚本资源地址
     */
    public static String getJsPath() {
        if (StringUtils.isEmpty(jsPath)) {
            jsPath = AppConfig.getInstance().getString("server.js", getHttpServer()) + "/" + AppConfig.getInstance().getString("webpath.js", "gdsp/js");
        }
        return jsPath;
    }

    /**
     * 取得样式表服务器地址
     * @return 样式表服务器地址
     */
    public static String getCssPath() {
        if (StringUtils.isEmpty(cssPath)) {
            cssPath = AppConfig.getInstance().getString("server.css", getHttpServer()) + "/" + AppConfig.getInstance().getString("webpath.css", "gdsp/css");
        }
        return cssPath;
    }

    /**
     * 取得图片服务器地址
     * @return 图片服务器地址
     */
    public static String getImagePath() {
        if (StringUtils.isEmpty(imagePath)) {
            imagePath = AppConfig.getInstance().getString("server.image", getHttpServer()) + "/" + AppConfig.getInstance().getString("webpath.images", "gdsp/images");
        }
        return imagePath;
    }

    /**
     * 取得样式主题地址
     * @return 样式主题地址
     */
    public static String getThemesPath() {
        if (StringUtils.isEmpty(themesPath)) {
            themesPath = AppConfig.getInstance().getString("server.themes", getHttpServer() + "/" + AppConfig.getInstance().getString("webpath.themes", "gdsp/themes"));
        }
        return themesPath;
    }

    /**
     * 取得当前主题路径
     * @return 当前主题路径
     */
    public static String getThemePath() {
        return getThemesPath() + "/" + getTheme();
    }

    /**
     * 取得样式主题地址
     * @return 样式主题地址
     */
    public static String getUploadPath() {
        if (StringUtils.isEmpty(uploadPath)) {
            uploadPath = AppConfig.getInstance().getString("server.upload", getHttpServer()) + "/" + AppConfig.getInstance().getString("webpath.upload", "gdsp/upload");
        }
        return uploadPath;
    }
}
