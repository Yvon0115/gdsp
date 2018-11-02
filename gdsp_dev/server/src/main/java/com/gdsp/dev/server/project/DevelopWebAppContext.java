/*
 * Copyright(c) FastSpace Software 2014. All Rights Reserved.
 */
package com.gdsp.dev.server.project;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections4.map.LinkedMap;
import org.eclipse.jetty.util.URIUtil;
import org.eclipse.jetty.util.resource.Resource;
import org.eclipse.jetty.webapp.WebAppContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 开发平台上下文
 * @author yangbo
 * @version 1.0 2013年12月9日
 * @since 1.6
 */
public class DevelopWebAppContext extends WebAppContext {

    protected Logger       logger         = LoggerFactory.getLogger(getClass());
    /**
     * 资源项目名称到资源目录的映射
     */
    private List<Resource> baseResources  = null;
    /**
     * web应用resource
     */
    private Resource       webappResource = null;

    /* (non-Javadoc)
    * @see org.eclipse.jetty.webapp.WebAppContext#getResource(java.lang.String)
    */
    public Resource getResource(String path) throws MalformedURLException {
        //非开发态直接使用服务器的资源获取方法
        if (!DevelopHelper.cur().isDevelop())
            return super.getResource(path);
        //校验路径
        if (path == null || !path.startsWith(URIUtil.SLASH))
            throw new MalformedURLException(path);
        List<Resource> bases = getBaseResources();
        if (bases == null || bases.size() == 0)
            return null;
        if (path.equals("/")) {
            try {
                return webappResource.addPath(path);
            } catch (IOException e) {
                logger.debug("Real path has error!",e);
            }
        } else {
            Resource resource = null;
            for (Resource base : bases) {
                try {
                    resource = base.addPath(path);
                    if (resource != null && resource.exists()) {
                        logger.debug("resourcepath:" + resource.getURL());
                        return resource;
                    }
                } catch (IOException e) {
                    logger.debug(e.getMessage(),e);
                    
                }
            }
        }
        return super.getResource(path);
    }

    /**
     * 取得项目的web目录资源
     * @return 项目根目录资源列表
     */
    public List<Resource> getBaseResources() {
        if (baseResources != null)
            return baseResources;
        baseResources = new ArrayList<Resource>();
        LinkedMap<String, DevelopProject> projects = DevelopHelper.cur().getProjects();
        for (int i = 0; i < projects.size(); i++) {
            DevelopProject project = projects.getValue(i);
            String webFolder = project.getWebModuleConfig().getFullWebPath();
            try {
                Resource resource = newResource(webFolder);
                baseResources.add(resource);
                if (webappResource == null && project.isWebApp()) {
                    webappResource = resource;
                }
            } catch (IOException e) {
                logger.debug(e.getMessage(),e);
                return null;
            }
        }
        return baseResources;
    }
}
