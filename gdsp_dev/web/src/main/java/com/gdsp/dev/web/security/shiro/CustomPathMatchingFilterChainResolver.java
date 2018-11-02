package com.gdsp.dev.web.security.shiro;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.shiro.web.filter.mgt.FilterChainManager;
import org.apache.shiro.web.filter.mgt.PathMatchingFilterChainResolver;

/**
 * 默认实现有点小问题：
 * 如果多个拦截器链都匹配了当前请求URL，那么只返回第一个找到的拦截器链；后续我们可以修改此处的代码，将多个匹配的拦截器链合并返回
 * 默认的PathMatchingFilterChainResolver区别是，此处得到所有匹配的拦截器链，
 * 然后通过调用CustomDefaultFilterChainManager.proxy(originalChain, chainNames)进行合并后代理
 * @author paul.yang
 * @version 1.0 2014年9月24日
 * @since 1.6
 */
public class CustomPathMatchingFilterChainResolver extends PathMatchingFilterChainResolver {

    /**
     * 自定义的过滤器管理
     */
    private CustomDefaultFilterChainManager customDefaultFilterChainManager;

    /**
     * 自定义的过滤器管理注入方法
     * @param customDefaultFilterChainManager 自定义的过滤器管理
     */
    public void setCustomDefaultFilterChainManager(CustomDefaultFilterChainManager customDefaultFilterChainManager) {
        this.customDefaultFilterChainManager = customDefaultFilterChainManager;
        setFilterChainManager(customDefaultFilterChainManager);
    }

    /* (non-Javadoc)
     * @see org.apache.shiro.web.filter.mgt.PathMatchingFilterChainResolver#getChain(javax.servlet.ServletRequest, javax.servlet.ServletResponse, javax.servlet.FilterChain)
     */
    public FilterChain getChain(ServletRequest request, ServletResponse response, FilterChain originalChain) {
        FilterChainManager filterChainManager = getFilterChainManager();
        if (!filterChainManager.hasChains()) {
            return null;
        }
        String requestURI = getPathWithinApplication(request);
        List<String> chainNames = new ArrayList<String>();
        for (String pathPattern : filterChainManager.getChainNames()) {
            if (pathMatches(pathPattern, requestURI)) {
                chainNames.add(pathPattern);
            }
        }
        if (chainNames.size() == 0) {
            return null;
        }
        return customDefaultFilterChainManager.proxy(originalChain, chainNames);
    }
}