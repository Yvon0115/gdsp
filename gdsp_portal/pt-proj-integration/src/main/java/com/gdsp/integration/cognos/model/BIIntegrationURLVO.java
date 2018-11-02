package com.gdsp.integration.cognos.model;

import java.util.HashMap;
import java.util.Map;

import com.gdsp.dev.core.model.entity.BaseEntity;

/**
 * 
* @ClassName: BIIntegrationURLVO
* (这里用一句话描述这个类的作用)
* @author hongwei.xu
* @date 2015年7月17日 上午10:54:34
*
 */
public class BIIntegrationURLVO extends BaseEntity {

    /**
    * @Fields serialVersionUID 
    */
    private static final long   serialVersionUID = -6178964943926776906L;
    /**
     * Base URL
     */
    private String              url;
    /**
     * 具有访问权限的用户
     */
    private String              user;
    /**
     * 具有访问权限的用户对应的密码
     */
    /**
     * 传递参数MAP
     * 报表参数MAP，Key：Value p_strat_date:2014-04-02
     */
    private Map<String, Object> parameters       = new HashMap<String, Object>();

    public Map<String, Object> getParameters() {
        return parameters;
    }

    public void setParameters(Map<String, Object> parameters) {
        this.parameters = parameters;
    }

    private String password;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
