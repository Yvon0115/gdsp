package com.gdsp.dev.web.security.shiro;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.servlet.Filter;
import javax.servlet.FilterChain;

import org.apache.shiro.config.Ini;
import org.apache.shiro.util.CollectionUtils;
import org.apache.shiro.util.Nameable;
import org.apache.shiro.util.StringUtils;
import org.apache.shiro.web.config.IniFilterChainResolverFactory;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.apache.shiro.web.filter.authc.AuthenticationFilter;
import org.apache.shiro.web.filter.authz.AuthorizationFilter;
import org.apache.shiro.web.filter.mgt.DefaultFilterChainManager;
import org.apache.shiro.web.filter.mgt.NamedFilterList;
import org.apache.shiro.web.filter.mgt.SimpleNamedFilterList;

/**
 * 自定义的过滤器链管理器
 * @author paul.yang
 * @version 1.0 2014年9月24日
 * @since 1.6
 */
public class CustomDefaultFilterChainManager extends DefaultFilterChainManager {

    /**
     * 过滤器链定义map,用于添加过滤器映射
     */
    private Map<String, String> filterChainDefinitionMap = null;
    /**
     * 登录连接
     */
    private String              loginUrl;
    /**
     * 登录成功连接
     */
    private String              successUrl;
    /**
     * 无权限时调用的页面
     */
    private String              unauthorizedUrl;

    /**
     * 构造方法
     */
    public CustomDefaultFilterChainManager() {
        setFilters(new LinkedHashMap<String, Filter>());
        setFilterChains(new LinkedHashMap<String, NamedFilterList>());
        addDefaultFilters(false);
    }

    /**
     * 取得配置中添加的url到角色的映射
     * @return url过滤器定义map
     */
    public Map<String, String> getFilterChainDefinitionMap() {
        return filterChainDefinitionMap;
    }

    /**
     * 设置配置中添加的url到角色的映射
     * @param filterChainDefinitionMap url过滤器定义map 
     */
    public void setFilterChainDefinitionMap(Map<String, String> filterChainDefinitionMap) {
        this.filterChainDefinitionMap = filterChainDefinitionMap;
    }

    /**
     * 设置自定义的过滤器
     * @param customFilters 自定义的过滤器
     */
    public void setCustomFilters(Map<String, Filter> customFilters) {
        for (Map.Entry<String, Filter> entry : customFilters.entrySet()) {
            addFilter(entry.getKey(), entry.getValue(), false);
        }
    }

    /**
     * 设置默认的过滤器定义
     * @param definitions 定义串
     */
    public void setDefaultFilterChainDefinitions(String definitions) {
        Ini ini = new Ini();
        ini.load(definitions);
        //did they explicitly state a 'urls' section?  Not necessary, but just in case:
        Ini.Section section = ini.getSection(IniFilterChainResolverFactory.URLS);
        if (CollectionUtils.isEmpty(section)) {
            //no urls section.  Since this _is_ a urls chain definition property, just assume the
            //default section contains only the definitions:
            section = ini.getSection(Ini.DEFAULT_SECTION_NAME);
        }
        setFilterChainDefinitionMap(section);
    }

    /**
     * 取得登录链接地址
     * @return 登录链接地址
     */
    public String getLoginUrl() {
        return loginUrl;
    }

    /**
     * 设置登录链接地址
     * @param loginUrl 登录链接地址
     */
    public void setLoginUrl(String loginUrl) {
        this.loginUrl = loginUrl;
    }

    /**
     * 取得登录成功后链接地址
     * @return 登录成功后链接地址
     */
    public String getSuccessUrl() {
        return successUrl;
    }

    /**
     * 设置登录成功后链接地址
     * @param successUrl 登录成功后链接地址
     */
    public void setSuccessUrl(String successUrl) {
        this.successUrl = successUrl;
    }

    /**
     * 取得无授权时显示的链接地址
     * @return 无授权时显示的链接地址
     */
    public String getUnauthorizedUrl() {
        return unauthorizedUrl;
    }

    /**
     * 设置无授权时显示的链接地址
     * @param unauthorizedUrl 无授权时显示的链接地址
     */
    public void setUnauthorizedUrl(String unauthorizedUrl) {
        this.unauthorizedUrl = unauthorizedUrl;
    }

    /**
     * 初始化方法
     */
    @PostConstruct
    public void init() {
        //Apply the acquired and/or configured filters:
        Map<String, Filter> filters = getFilters();
        if (!CollectionUtils.isEmpty(filters)) {
            for (Map.Entry<String, Filter> entry : filters.entrySet()) {
                String name = entry.getKey();
                Filter filter = entry.getValue();
                applyGlobalPropertiesIfNecessary(filter);
                if (filter instanceof Nameable) {
                    ((Nameable) filter).setName(name);
                }
            }
        }
        //build up the chains:
        Map<String, String> chains = getFilterChainDefinitionMap();
        if (!CollectionUtils.isEmpty(chains)) {
            for (Map.Entry<String, String> entry : chains.entrySet()) {
                String url = entry.getKey();
                String chainDefinition = entry.getValue();
                createChain(url, chainDefinition);
            }
        }
    }

    @Override
    protected void initFilter(Filter filter) {
        //ignore
    }

    /**
     * 过滤器链代理方法添加过滤器链
     * @param original 原始过滤器链
     * @param chainNames 新的过滤器链名
     * @return 过滤器链对象
     */
    public FilterChain proxy(FilterChain original, List<String> chainNames) {
        NamedFilterList configured = new SimpleNamedFilterList(chainNames.toString());
        for (String chainName : chainNames) {
            configured.addAll(getChain(chainName));
        }
        return configured.proxy(original);
    }

    /**
     * 对AccessControlFilter类型的过滤器设置登录连接,全局设置
     * @param filter 过滤器对象
     */
    private void applyLoginUrlIfNecessary(Filter filter) {
        String loginUrl = getLoginUrl();
        if (!StringUtils.hasText(loginUrl) || !(filter instanceof AccessControlFilter))
            return;
        AccessControlFilter acFilter = (AccessControlFilter) filter;
        //only apply the login url if they haven't explicitly configured one already:
        String existingLoginUrl = acFilter.getLoginUrl();
        if (AccessControlFilter.DEFAULT_LOGIN_URL.equals(existingLoginUrl)) {
            acFilter.setLoginUrl(loginUrl);
        }
    }

    /**
     * 对AuthenticationFilter类型过滤器设置登录成功后的连接,全局初始化设置
     * @param filter 过滤器对象
     */
    private void applySuccessUrlIfNecessary(Filter filter) {
        String successUrl = getSuccessUrl();
        if (StringUtils.hasText(successUrl) && (filter instanceof AuthenticationFilter)) {
            AuthenticationFilter authcFilter = (AuthenticationFilter) filter;
            //only apply the successUrl if they haven't explicitly configured one already:
            String existingSuccessUrl = authcFilter.getSuccessUrl();
            if (AuthenticationFilter.DEFAULT_SUCCESS_URL.equals(existingSuccessUrl)) {
                authcFilter.setSuccessUrl(successUrl);
            }
        }
    }

    /**
     * 对AuthenticationFilter类型过滤器设置无授权的连接,全局初始化设置
     * @param filter 过滤器对象
     */
    private void applyUnauthorizedUrlIfNecessary(Filter filter) {
        String unauthorizedUrl = getUnauthorizedUrl();
        if (StringUtils.hasText(unauthorizedUrl) && (filter instanceof AuthorizationFilter)) {
            AuthorizationFilter authzFilter = (AuthorizationFilter) filter;
            //only apply the unauthorizedUrl if they haven't explicitly configured one already:
            String existingUnauthorizedUrl = authzFilter.getUnauthorizedUrl();
            if (existingUnauthorizedUrl == null) {
                authzFilter.setUnauthorizedUrl(unauthorizedUrl);
            }
        }
    }

    /**
     * 应用全局过滤器初始化设置
     * @param filter 过滤器对象
     */
    private void applyGlobalPropertiesIfNecessary(Filter filter) {
        applyLoginUrlIfNecessary(filter);
        applySuccessUrlIfNecessary(filter);
        applyUnauthorizedUrlIfNecessary(filter);
    }
}
