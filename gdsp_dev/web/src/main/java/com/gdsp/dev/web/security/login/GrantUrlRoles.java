package com.gdsp.dev.web.security.login;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

/**
 * url权限到role的映射
 * @author paul.yang
 * @version 1.0 2014年9月24日
 * @since 1.6
 */
public class GrantUrlRoles {

    /**
     * 权限资源控制url
     */
    private String       url;
    /**
     * 与权限资源控制标识关联的角色标识列表
     */
    private List<String> roles;

    /**
     * 取得权限资源控制url
     * @return 权限资源控制url
     */
    public String getUrl() {
        return url;
    }

    /**
     * 设置权限资源控制url
     * @param url 权限资源控制url
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * 取得与权限资源控制标识关联的角色标识列表
     * @return 角色标识列表
     */
    public List<String> getRoles() {
        return roles;
    }

    /**
     * 设置与权限资源控制标识关联的角色标识列表
     * @param role 角色标识列表
     */
    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    /**
     * 取得逗号隔开的角色列表串
     * @return 逗号隔开的角色列表串
     */
    public String getRolesChainString() {
        List<String> roles = getRoles();
        if (roles == null || roles.size() == 0)
            return null;
        return StringUtils.join(roles.iterator(), ",");
    }
}
