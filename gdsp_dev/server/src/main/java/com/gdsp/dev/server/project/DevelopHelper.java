/*
 * Copyright(c) FastSpace Software 2014. All Rights Reserved.
 */
package com.gdsp.dev.server.project;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.zip.ZipException;

import org.apache.commons.collections4.map.LinkedMap;
import org.apache.commons.lang3.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gdsp.dev.server.utils.FileUtils;
import com.gdsp.dev.server.utils.ZipUtils;
import com.gdsp.dev.server.utils.ZipUtils.INameFilter;

/**
 * 项目开发助手类
 * @author yaboocn
 * @version 1.0 2014年5月5日
 * @since 1.7
 */
public final class DevelopHelper {
    /**
     * 日志对象
     */
    private static Logger      logger          = LoggerFactory.getLogger(DevelopHelper.class);
    private static DevelopHelper              helper          = new DevelopHelper();
    /**
     * 系统属性中的项目路径属性
     */
    public final static String                PROJECT_PATH    = "flane.projects";
    /**
     * 主项目名称
     */
    public final static String                MAIN_PROJECT    = "flane.mainProject";
    /**
     * 项目运行时的路径
     */
    public final static String                CLASS_PROJECT   = "java.class.path";
    public final static String                GROUP_ID_PREFIX = "com.gdsp";
    public final static String                TMP_DIR         = System.getProperty("java.io.tmpdir");
    private String                            tmpWebRoot      = null;
    /**
     * 项目运行时的classpath
     */
    private String                            classpath       = null;
    /**
     * 引用项目的扩展classpath
     */
    private String                            extClasspath    = null;
    /**
     * 所有web资源路径
     */
    private String                            webPaths        = null;
    /**
     * 项目的web资源路径
     */
    private List<String>                      prjWebPaths     = new ArrayList<String>();
    /**
     * 当前项目所包含的所有工程模块
     */
    private LinkedMap<String, DevelopProject> projects        = null;
    /**
     * 将要运行的web应用项目
     */
    private DevelopProject                    webApp          = null;
    /**
     * 判断项目是否在开发状态
     */
    private boolean                           isDevelop       = false;

    private DevelopHelper() {}

    public static DevelopHelper cur() {
        return helper;
    }

    /**
     * 载入项目配置
     * @return 
     */
    public Collection<DevelopProject> resolveProjects() {
        String classpaths = getClasspath();
        if (StringUtils.isEmpty(classpaths)) {
            return null;
        }
        Properties properties = System.getProperties();
        String split = properties.getProperty("path.separator", ";");
        String[] paths = classpaths.split(split);
        projects = new LinkedMap<String, DevelopProject>();
        //如果不为空，覆盖webapp中的webconfig
        WebConfig webConfig = null;
        for (String path : paths) {
            DevelopProject project = DevelopProject.loadProjectInfo(path);
            if (project == null || !project.isWebModule()) {
                continue;
            }
            if (project.isWebApp() && webApp == null) {
                webApp = project;
                if (webConfig != null) {
                    webApp.getWebAppConfig().setWebConfig(webConfig);
                }
            } else if (webConfig == null && project.hasWebConfig()) {
                webConfig = project.getWebConfig();
            }
            projects.put(project.getModuleName(), project);
        }
        //确认临时web资源路径
        tmpWebRoot = initTmpWebRoot();
        StringBuilder webpaths = new StringBuilder();
        StringBuilder extClasspaths = new StringBuilder();
        //修复project path、获取web资源目录、获取扩展classpath
        for (DevelopProject prj : projects.values()) {
            //修复project path
            if (prj.getPath().startsWith(DevelopProject.TMP_PATH)) {
                prj.setPath(prj.getPath().replace(DevelopProject.TMP_PATH, tmpWebRoot));
                //获取扩展classpath
                String resourcePath = prj.getResourcePath();
                if (!classpath.contains(resourcePath) && extClasspaths.indexOf(resourcePath) == -1) {
                    extClasspaths.append(resourcePath).append(",");
                }
            }
            //获取web资源目录
            String curWebPath = prj.getWebModuleConfig().getFullWebPath();
            if (webpaths.length() > 0) {
                if (webpaths.indexOf(curWebPath) == -1) {
                    webpaths.append(",").append(curWebPath);
                }
            } else {
                webpaths.append(curWebPath);
            }
        }
        isDevelop = !projects.isEmpty();
        webPaths = webpaths.toString();
        extClasspath = extClasspaths.toString();
        return projects.values();
    }

    /**
     * 
     * @Title: parsePomFile 
     * @Description: 解析项目中的Pom文件
     * @return List<String>  返回应该加载的war集合
     * @throws DocumentException 
     */
    public List<String> recycleParsePomFile() throws DocumentException   {
        List<String> list = new ArrayList<String>();
        String classpath = getClasspath();
        if (StringUtils.isEmpty(classpath)) {
            return null;
        }
        Properties properties = System.getProperties();
        String split = properties.getProperty("path.separator", ";");
        String[] paths = classpath.split(split);
        for (String path : paths) {
            List<String> warList = findProjectWarInfo(path);
            if (warList != null) {
                list.addAll(warList);
            }
        }
        return list;
    }

    private List<String> findProjectWarInfo(String path) throws DocumentException  {
        if (path == null) {
            return null;
        }
        File prjFolder = new File(path);
        if (!prjFolder.exists() || prjFolder.getParent() == null) {
            return null;
        }
        List<String> listWar = null;
        if (prjFolder.isDirectory()) {
            File prjFile = new File(path + "/.project");
            File pomFile = new File(path + "/pom.xml");
            if (!prjFile.exists() && !pomFile.exists()) {
                return findProjectWarInfo(prjFolder.getParent());
            }
            if (pomFile.exists()) {
                listWar = parsePomFile(pomFile);
            }
        }
        return listWar;
    }

    @SuppressWarnings("unchecked")
    private List<String> parsePomFile(File pomFile) throws DocumentException {
        if (!pomFile.exists()) {
            return null;
        }
        List<String> list = new ArrayList<String>();
        SAXReader reader = new SAXReader();
        Document document = reader.read(pomFile);
        Element rootElement = document.getRootElement();
        Element dependenciesElement = rootElement.element("dependencies");
        if (dependenciesElement != null) {
            List<Element> elements = dependenciesElement.elements();
            if (elements != null) {
                for (Element element : elements) {
                    Element typeElement = element.element("type");
                    if (typeElement != null && typeElement.getTextTrim().equals("war")) {
                        String groupId = element.element("groupId").getTextTrim();
                        String artifactId = element.element("artifactId").getTextTrim();
                        list.add(groupId + "." + artifactId);
                    }
                }
            }
        }
        return list;
    }

    /**
     * 解压项目模块的web资源
     * @Title: unzipProjectWebRes 
     * @Description: 解压项目模块的web资源
     * @param requiredProjectWarList
     * @return
     * @return String    返回类型
     */
    public List<String> unzipProjectWebRes(List<String> requiredProjectWarList, Map<String, Object> resFileLocationMap) {
        for (DevelopProject prj : projects.values()) {
            if (prj.isJar() && requiredProjectWarList.contains(prj.getId())) {
                String prjWebPath = unzipWebRes(prj.getPath(), prj.getSource(), resFileLocationMap);
                if (prjWebPath != null && !prjWebPaths.contains(prjWebPath)) {
                    prjWebPaths.add(prjWebPath);
                }
            }
        }
        return prjWebPaths;
    }

    /**
     * 解压web资源文件,路过WEB-INF下的classes和lib包
     * @param prjFolder
     * @param groupId
     * @return
     * @throws IOException 
     * @throws ZipException 
     */
    private String unzipWebRes(String prjPath, String prjSource, Map<String, Object> resFileLocationMap) {
        File war = new File(prjSource.replace(".jar", ".war"));
        if (!war.exists()) {
            return null;
        } else {
            String destDir = new File(prjPath + WebModuleConfig.DEFAULT_WEBPATH).getAbsolutePath();
            //是否覆盖
            boolean cover = false;
            if (needClean()) {
                cover = true;
            } else {
                File dir = new File(destDir);
                if (!dir.exists() || dir.list().length == 0) {
                    //目录文件夹不存在，或者为空
                    cover = true;
                }
            }
            final Properties p = new Properties();
            try {
                p.load(DevelopProject.class.getResourceAsStream("/gdsp/ignoreUnzipFile.properties"));
            } catch (IOException e) {
                System.out.println("加载忽略解压文件列表出错！");
//                throw new RuntimeException(e);
                logger.error("加载忽略解压文件列表出错！", e);
            }
            ZipUtils.unzipWar(war, destDir, resFileLocationMap, cover, new INameFilter() {

                @Override
                public boolean filter(String name) {
                    boolean flag = false;
                    String fileSet = p.getProperty("fileSet");
                    //过滤jar包
                    if (fileSet != null) {
                        String[] list = fileSet.split(",");
                        if (list != null && list.length > 0) {
                            for (String string : list) {
                                flag = name.contains(string);
                                if (flag) {
                                    return flag;
                                }
                            }
                        }
                    }
                    return flag;
                }
            });
            System.out.println("[" + (cover ? "cover" : "not cover") + "] unpacking " + war.getName() + " to " + destDir);
        }
        return prjPath;
    }

    /**
     * 清除项目的临时web目录<br/>
     * 当平台项目以jar形式引入时，平台的web资源将解压到临时web目录中，工程启动时清空临时web目录，重新加载平台web资源
     */
    public void clearTmpWebRoot() {
        if (needClean()) {
            System.out.println("Cleaning Temp WebRoot : " + tmpWebRoot);
            FileUtils.delete(tmpWebRoot);
            System.out.println("Cleaning finished!");
        }
    }

    /**
     * 初始化临时web目录
     * @Title: initTmpWebRoot 
     * @Description: 这里用一句话描述这个方法的作用
     * @return
     * @return String    返回类型
     */
    private String initTmpWebRoot() {
        WebAppConfig appConfig = getWebAppProject().getWebAppConfig();
        String port = String.valueOf(appConfig.getServerPort());
        String context = appConfig.getWebAppContext().getContextPath();
        context = context.startsWith("/") || context.startsWith("\\") ? context.substring(1) : context;
        String host = "localhost_" + port + "_" + context.replaceAll("\\\\", "_").replaceAll("\\/", "_");
        return TMP_DIR + host;
    }

    private boolean needClean() {
        String clean = System.getProperty("clean");
        if ("true".equals(clean)) {
            return true;
        }
        return false;
    }

    public String getClasspath() {
        if (StringUtils.isEmpty(classpath)) {
            Properties properties = System.getProperties();
            //properties.getProperty(PROJECT_PATH);
            classpath = properties.getProperty(CLASS_PROJECT);
            classpath = classpath.replaceAll("\\\\", "/");
        }
        return classpath;
    }

    /**
     * 引用项目的扩展classpath路径,路径是以逗号分隔
     * @Title: getExtClasspath 
     * @Description: 这里用一句话描述这个方法的作用
     * @return
     * @return String    返回类型
     */
    public String getExtClasspath() {
        return extClasspath;
    }

    /**
     * 根据项目名称取得项目信息
     * @return 项目信息集
     */
    public LinkedMap<String, DevelopProject> getProjects() {
        return projects;
    }

    /**
     * 根据项目名称取得项目信息
     * @param name 项目名称
     * @return 项目信息
     */
    public DevelopProject getProject(String name) {
        if (projects == null)
            return null;
        return (DevelopProject) projects.get(name);
    }

    /**
     * 取得Web应用项目列表
     * @return Web应用项目列表
     */
    public DevelopProject getWebAppProject() {
        return webApp;
    }

    /**
     * 判断当前是否在开发状态
     * @return 是否为开发状态
     */
    public boolean isDevelop() {
        return isDevelop;
    }

    public String getWebPaths() {
        return webPaths;
    }

    public List<String> getPrjWebPaths() {
        return prjWebPaths;
    }

    public static void main(String[] args) throws DocumentException  {
        File file = new File("E:\\ws05\\gdsp_platform\\platform-base\\pom.xml");
        new DevelopHelper().parsePomFile(file);
    }

    /**
     * 
     * @Title: getConfigFileDir 
     * @Description: 拿到配置文件解压的位置。如：E:/ws05/gdsp_app/app-flexquery/target/classes
     * @return
     * @return Map<String,Object>    返回类型
     */
    public Map<String, Object> getConfigFileDir() {
        Map<String, Object> map = new HashMap<String, Object>();
        String classpath = getClasspath();
        if (StringUtils.isEmpty(classpath)) {
            return null;
        }
        Properties properties = System.getProperties();
        String split = properties.getProperty("path.separator", ";");
        String[] paths = classpath.split(split);
        if (paths != null && paths.length > 0) {
            List<String> list = new ArrayList<String>();
            for (int i = 0; i < paths.length; i++) {
                File file = new File(paths[i]);
                //文件存在，并且是目录
                if (file.exists() && file.isDirectory()) {
                    list.add(paths[i]);
                    if (i == 0) {
                        map.put("targetDir", paths[i]);
                    }
                }
            }
            map.put("resourceFiles", list);
        }
        return map;
    }
}
