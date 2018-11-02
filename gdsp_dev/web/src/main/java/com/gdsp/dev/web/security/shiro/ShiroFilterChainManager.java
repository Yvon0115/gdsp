package com.gdsp.dev.web.security.shiro;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.apache.shiro.web.filter.mgt.DefaultFilterChainManager;
import org.apache.shiro.web.filter.mgt.NamedFilterList;
import org.springframework.beans.factory.annotation.Autowired;

import com.gdsp.dev.web.security.login.GrantUrlRoles;

/**
 * 为限制URL创建的类,增加动态增加URL到ROLE的映射
 * @author paul.yang
 * @version 1.0 2014年9月24日
 * @since 1.6
 */
public class ShiroFilterChainManager {

    /**
     * 过滤器链管理对象,用于注册url到role的映射
     */
    @Autowired
    private DefaultFilterChainManager    filterChainManager;
    /**
     * 缓存原始的过滤器链
     */
    private Map<String, NamedFilterList> defaultFilterChains;

    /**
     * 初始化方法
     */
    @PostConstruct
    public void init() {
        //备份原始的过滤器链
        defaultFilterChains = new HashMap<String, NamedFilterList>(filterChainManager.getFilterChains());
    }

    /**
     * 授权变更重置权限映射
     * @param urlRoles url到角色的映射
     */
    public void resetFilterChains(List<GrantUrlRoles> urlRoles) {
        //1、首先删除以前老的filter chain并注册默认的  
        filterChainManager.getFilterChains().clear();
        if (defaultFilterChains != null) {
            filterChainManager.getFilterChains().putAll(defaultFilterChains);
        }
        //2、循环URL Filter 注册filter chain  
        for (GrantUrlRoles urlRole : urlRoles) {
            String url = urlRole.getUrl();
            //注册roles filter  
            if (urlRole.getRoles() != null && urlRole.getRoles().size() > 0) {
                filterChainManager.addToChain(url, "roles", urlRole.getRolesChainString());
            }
        }
    }
}