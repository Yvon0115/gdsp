/*
 * Copyright(c) FastSpace Software 2014. All Rights Reserved.
 */
package com.gdsp.dev.server.project;

import java.io.File;
import java.io.IOException;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.gdsp.dev.server.utils.XmlParserUtils;

/**
 * 开发状态下的模块信息
 * @author yaboocn
 * @version 1.0 2011-4-27
 * @since 1.0
 */
public class DevelopProject {

    private static Logger       logger         = LoggerFactory.getLogger(DevelopProject.class);
    /**
     * 模块名在配置中的键值
     */
    public final static String KEY_MODULENAME  = "moduleName";
    /**
     * 以jar包形式存在的项目临时路径变量，用于被替换
     */
    public static final String TMP_PATH        = "$$TMP_PATH$$";
    /**
     * 项目标识（maven的坐标）
     */
    private String             id              = null;
    /**
     * 项目名称
     */
    private String             name            = null;
    /**
     * 项目路径
     */
    private String             path            = null;
    /**
     * 项目是否是以jar包形式存在
     */
    private boolean            isJar           = false;
    /**
     * 只有当isJar为true时才有值，值为jar包的路径
     */
    private String             source          = null;
    /**
     * 项目版本
     */
    private String             version         = null;
    /**
     * 项目资源文件路径
     */
    private String             resourcePath    = null;
    /**
     * 项目在整个web项目中的模块名，此命名将在
     * 页面定义、静态页面、皮肤中作为模块url上下文
     */
    private String             moduleName      = null;
    /**
     * 项目包含的Web模块配置信息
     */
    private WebModuleConfig    webModuleConfig = null;
    /**
     * 项目包含的Web应用配置信息
     */
    private WebAppConfig       webAppConfig    = null;
    /**
     * 项目包含的web相关配置，为了同时在一个webapp下不同web模块起多个webapp服务
     */
    private WebConfig          webConfig       = null;

    /**
     * 根据文件路径加载项目信息
     * @param path 项目路径
     * @return 项目信息对象
     */
    public static DevelopProject loadProjectInfo(String path) {
        if (path == null) {
            return null;
        }
        //加载.project文件中的配置信息
        File prjFolder = new File(path);
        if (!prjFolder.exists() || prjFolder.getParent() == null) {
            return null;
        }
        DevelopProject project = null;
        if (!prjFolder.isDirectory()) {
            //加载jar包引用
            if (prjFolder.getName().endsWith(".jar")) {
                project = loadProjectInfoWithJar(prjFolder.getPath());
            }
        } else {
            //加载项目引用
            project = loadProjectInfoWithDir(path);
        }
        return project;
    }

    private static DevelopProject loadProjectInfoWithDir(String path) {
        if (path == null) {
            return null;
        }
        //加载.project文件中的配置信息
        File prjFolder = new File(path);
        if (!prjFolder.exists() || !prjFolder.isDirectory() || prjFolder.getParent() == null) {
            return null;
        }
        File prjFile = new File(path + "/.project");
        File pomFile = new File(path + "/pom.xml");
        if (!prjFile.exists() && !pomFile.exists()) {
            return loadProjectInfoWithDir(prjFolder.getParent());
        }
        DevelopProject project = null;
        try {
            if (pomFile.exists()) {
                project = loadPomProjectConfig(pomFile);
            } else {
                project = loadProjectConfig(prjFile);
            }
            project.setPath(prjFolder.getAbsolutePath());
            project.setResourcePath("/target/classes");
        } catch (Exception e) {
            logger.debug(e.getMessage(), e);
            return null;
        }
        //加载开发配置
        DevConfig.loadDevConfig(path + "/devconfig");
        //读取开发配置
        Properties properties = DevConfig.getDevConfig().getProperties();
        //加载模块名
        String moduleName = properties.getProperty(KEY_MODULENAME, project.getName());
        if (moduleName.startsWith("/")) {
            moduleName = moduleName.substring(1);
        }
        project.setModuleName(moduleName);
        //		System.out.println("loaded!");
        WebModuleConfig.loadConfig(properties, project);
        WebAppConfig.loadConfig(properties, project);
        if (!project.isWebApp()) {
            WebConfig.loadConfig(properties, project, false);
        }
        return project;
    }

    private static DevelopProject loadProjectInfoWithJar(String path) {
        if (path == null) {
            return null;
        }
        //加载.project文件中的配置信息
        File prjFolder = new File(path);
        if (!prjFolder.exists()) {
            return null;
        }
        File pomFile = new File(prjFolder.getPath().replace(".jar", ".pom"));
        DevelopProject project = null;
        try {
            if (pomFile.exists()) {
                project = loadPomProjectConfig(pomFile);
            }
            if (project != null) {
                Node node = getPomNode(pomFile);
                String groupId = getGroupId(node);
                if (groupId.startsWith(DevelopHelper.GROUP_ID_PREFIX)) {
                    String version = project.getVersion();
                    //项目的临时目录当前无法确认，暂时以临时变量代替
                    project.setPath(TMP_PATH + File.separator + groupId.replaceAll("\\.", "\\" + File.separator) + File.separator + version);
                    project.setResourcePath(WebModuleConfig.DEFAULT_WEBPATH + "/WEB-INF/classes");
                    //jar包项目特有属性
                    project.setJar(true);
                    project.setSource(prjFolder.getPath());
                } else {
                    project = null;
                }
            }
        } catch (Exception e) {
            //throw new RuntimeException(e);
            logger.error(e.getMessage(), e);
        }
        if (project == null) {
            return null;
        }
        //加载开发配置
        Properties properties = DevConfig.getDevConfig().getProperties();
        //加载模块名
        String moduleName = properties.getProperty(KEY_MODULENAME, project.getName());
        if (moduleName.startsWith("/")) {
            moduleName = moduleName.substring(1);
        }
        project.setModuleName(moduleName);
        WebModuleConfig.loadConfig(properties, project);
        WebAppConfig.loadConfig(properties, project);
        if (!project.isWebApp()) {
            WebConfig.loadConfig(properties, project, false);
        }
        return project;
    }

    /**
     * 根据pom文件解析项目信息
     * @param file pom文件
     * @return 项目对象
     * @throws IOException 
     * @throws SAXException 
     */
    private static DevelopProject loadPomProjectConfig(File file) throws SAXException, IOException  {
        Node node = getPomNode(file);
        String groupId = getGroupId(node);
        String artifactId = getArtifactId(node);
        String version = getVersion(node);
        if (artifactId == null) {
            artifactId = file.getParentFile().getName();
        }
        DevelopProject project = new DevelopProject();
        project.setName(artifactId);
        project.setId(groupId + "." + artifactId);
        project.setVersion(version);
        return project;
    }

    private static Node getPomNode(File file) throws SAXException, IOException  {
        Document doc = XmlParserUtils.getDocument(file);
        Node node = doc.getFirstChild();
        return node;
    }

    private static Node getChildNode(Node node, String name) {
        Node child = null;
        NodeList nodeList = node.getChildNodes();
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node item = nodeList.item(i);
            if (name.equalsIgnoreCase(item.getNodeName())) {
                child = item;
                break;
            }
        }
        return child;
    }

    private static String getGroupId(Node node) {
        Node childNode = getChildNode(node, "groupId");
        if (childNode == null || childNode.getTextContent() == null) {
            childNode = getChildNode(getChildNode(node, "parent"), "groupId");
        }
        return childNode.getTextContent();
    }

    private static String getArtifactId(Node node) {
        Node childNode = getChildNode(node, "artifactId");
        if (childNode == null || childNode.getTextContent() == null) {
            childNode = getChildNode(getChildNode(node, "parent"), "artifactId");
        }
        return childNode.getTextContent();
    }

    private static String getVersion(Node node) {
        Node childNode = getChildNode(node, "version");
        if (childNode == null || childNode.getTextContent() == null) {
            childNode = getChildNode(getChildNode(node, "parent"), "version");
        }
        return childNode.getTextContent();
    }

    /**
     * 加载.project配置文件
     * @param file 配置文件对象
     * @return 项目对象
     * @throws IOException 
     * @throws SAXException 
     */
    private static DevelopProject loadProjectConfig(File file) throws SAXException, IOException  {
        Document doc = XmlParserUtils.getDocument(file);
        Node node = doc.getFirstChild();
        NodeList nodeList = node.getChildNodes();
        String name = null;
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node item = nodeList.item(i);
            if ("name".equalsIgnoreCase(item.getNodeName())) {
                name = item.getTextContent();
                break;
            }
        }
        if (name == null) {
            name = file.getParentFile().getName();
        }
        DevelopProject project = new DevelopProject();
        project.setName(name);
        return project;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    /**
     * 取得项目名称
     * @return 项目名称
     */
    public String getName() {
        return name;
    }

    /**
     * 设置项目名称
     * @param name 项目名称
     */
    public void setName(String name) {
        this.name = name;
    }

    public boolean isJar() {
        return isJar;
    }

    public void setJar(boolean isJar) {
        this.isJar = isJar;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    /**
     * 取得项目路径
     * @return 项目路径
     */
    public String getPath() {
        return path;
    }

    /**
     * 设置项目路径
     * @param path 项目路径
     */
    public void setPath(String path) {
        this.path = path.replaceAll("\\\\", "/");
    }

    public String getResourcePath() {
        return getPath() + resourcePath;
    }

    public void setResourcePath(String resourcePath) {
        this.resourcePath = resourcePath.replaceAll("\\\\", "/");
    }

    /**
     * 取得项目在整个web项目中的模块名，此命名将在
     * 页面定义、静态页面、皮肤中作为模块url上下文
     * @return 模块名
     */
    public String getModuleName() {
        return moduleName;
    }

    /**
     * 设置项目在整个web项目中的模块名，此命名将在
     * 页面定义、静态页面、皮肤中作为模块url上下文
     * @param moduleName 模块名
     */
    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    /**
     * 取得项目包含的Web模块配置信息
     * @return Web模块配置信息
     */
    public WebModuleConfig getWebModuleConfig() {
        return webModuleConfig;
    }

    /**
     * 设置项目包含的Web模块配置信息
     * @param webModuleConfig Web模块配置信息
     */
    public void setWebModuleConfig(WebModuleConfig webModuleConfig) {
        this.webModuleConfig = webModuleConfig;
    }

    /**
     * 取得项目包含的Web应用配置
     * @return Web应用配置
     */
    public WebAppConfig getWebAppConfig() {
        return webAppConfig;
    }

    /**
     * 设置项目包含的Web应用配置
     * @param webAppConfig 项目包含的Web应用配置
     */
    public void setWebAppConfig(WebAppConfig webAppConfig) {
        this.webAppConfig = webAppConfig;
    }

    /**
     * 取得web相关配置，为了同时在一个webapp下不同web模块起多个webapp服务
     * @return web相关配置
     */
    public WebConfig getWebConfig() {
        return webConfig;
    }

    /**
     * 设置web相关配置，为了同时在一个webapp下不同web模块起多个webapp服务
     * @param webConfig web相关配置
     */
    public void setWebConfig(WebConfig webConfig) {
        this.webConfig = webConfig;
    }

    /**
     * 判断项目是否为web模块
     * @return 布尔值
     */
    public boolean isWebModule() {
        return getWebModuleConfig() != null;
    }

    /**
     * 判断是否含有web应用
     * @return 布尔值
     */
    public boolean isWebApp() {
        return getWebAppConfig() != null;
    }

    /**
     * 判断是否含有web应用
     * @return 布尔值
     */
    public boolean hasWebConfig() {
        return getWebConfig() != null;
    }
}
