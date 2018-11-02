package com.gdsp.dev.server.jetty;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.webapp.WebAppContext;

import com.gdsp.dev.server.project.DevelopHelper;
import com.gdsp.dev.server.project.DevelopProject;
import com.gdsp.dev.server.project.WebAppConfig;

/**
 * jetty web容器启动
 * @author paul.yang
 * @version 1.0 2014年10月9日
 * @since 1.6
 */
public class WebServer {

    /**
     * Jetty服务启动
     * @param args 启动参数
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        long startTime = System.currentTimeMillis();
        System.out.println("Starting Server……");
        DevelopHelper helper = DevelopHelper.cur();
        //分析当前工程引用了其他哪些工程模块
        System.out.println("Resolve projects……");
        helper.resolveProjects();
        System.out.println("Resolve completed! " + "Cost " + (System.currentTimeMillis() - startTime) + "ms.");
        //判断是否存在web.xml
        DevelopProject webApp = helper.getWebAppProject();
        if (webApp == null) {
            System.err.println("当前项目不存在带有web应用的项目或主项目配置错误！");
            return;
        }
        //解析工程的pom文件，获取要加载哪些war包
        System.out.println("Parsing current project \"pom.xml\" file ...");
        List<String> requiredProjectWarList = helper.recycleParsePomFile();
        System.out.println("Parsing completed." + "cost " + (System.currentTimeMillis() - startTime) + "ms.");
        //拿到解压配置文件目录。
        Map<String, Object> configFileDir = helper.getConfigFileDir();
        //清除旧的资源文件
        helper.clearTmpWebRoot();
        //解压依赖工程模块war包中的web资源文件
        System.out.println("Unzip web resources……");
        List<String> prjWebPaths = helper.unzipProjectWebRes(requiredProjectWarList, configFileDir);
        System.out.println("Unzip completed!" + "cost " + (System.currentTimeMillis() - startTime) + "ms.");
        //设置系统运行参数
        WebAppConfig appConfig = webApp.getWebAppConfig();
        System.setProperty("server.root", appConfig.getWebAppPath());
        System.setProperty("project.webpaths", helper.getWebPaths());
        System.setProperty("project.develop", Boolean.toString(helper.isDevelop()));
        Server server = new Server();
        ServerConnector connector = new ServerConnector(server);
        connector.setPort(appConfig.getServerPort());
        connector.setIdleTimeout(30000);
        server.addConnector(connector);
        WebAppContext context = getWebAppContext(appConfig);
        server.setHandler(context);
        server.start();
        //打印调试信息
        printDebugInfo(startTime, helper, prjWebPaths, appConfig, context);
        //打印Logo
        printLogo(webApp.getVersion());
        server.join();
    }

    private static WebAppContext getWebAppContext(WebAppConfig appConfig) {
        WebAppContext context = appConfig.getWebAppContext();
        context.getInitParams().put("org.eclipse.jetty.servlet.Default.useFileMappedBuffer", "false");
        String extraClasspath = context.getExtraClasspath();
        extraClasspath = extraClasspath == null ? "" : extraClasspath + ",";
        extraClasspath = extraClasspath + DevelopHelper.cur().getExtClasspath();
        context.setExtraClasspath(extraClasspath);
        return context;
    }

    private static void printDebugInfo(long t1, DevelopHelper helper, List<String> prjWebPaths, WebAppConfig appConfig, WebAppContext context) {
        System.out.println("**************SERVER STARTED******************");
        System.out.println("Run Web Project Modules: ");
        for (DevelopProject prj : helper.getProjects().values()) {
            System.out.println("        " + prj.getName());
        }
        if (prjWebPaths.size() > 0) {
            System.out.println("Platform Web Resources Dir: ");
            for (String prjWebPath : prjWebPaths) {
                System.out.println("        " + prjWebPath);
            }
        }
        System.out.println("Cost Total Time: " + (System.currentTimeMillis() - t1) + "ms.");
        System.out.println("Please Browse: " + "http://localhost:" + appConfig.getServerPort() + context.getContextPath());
        System.out.println("**************SERVER STARTED******************");
    }

    private static void printLogo(String version) {
        System.out.println();
        System.out.println("Welcome to");
        System.out.println("                  __");
        System.out.println("        _____ ___/ /____ ___");
        System.out.println("      / __  / __  /  __/ __ \\");
        System.out.println("     / /_  / /_  /_\\ \\/ __/ /");
        System.out.println("     \\__  /\\ ___/____/ ____/   " + (StringUtils.isNotEmpty(version) ? "version " + version : ""));
        System.out.println("   /____ /          /_/");
    }
}
