package com.gdsp.platform.log.model;

import com.gdsp.dev.base.lang.DDateTime;
import com.gdsp.dev.core.model.entity.AuditableEntity;

public class LoginLogVO extends AuditableEntity {

    /**
     * 
     */
    private static final long serialVersionUID = -1656372949992701300L;
    private String            login_account;                           //账号
    private String            username;                                //姓名
    private String            ip_addr;                                 //ip地址
//    private String            mac_addr;                                //mac地址
    private String            login_status;                            //登录状态
    private DDateTime         login_time;                              //登录时间

    public String getLogin_account() {
        return login_account;
    }

    public void setLogin_account(String login_account) {
        this.login_account = login_account;
    }

    public String getIp_addr() {
        return ip_addr;
    }

    public void setIp_addr(String ip_addr) {
        this.ip_addr = ip_addr;
    }

//    public String getMac_addr() {
//        return mac_addr;
//    }
//
//    public void setMac_addr(String mac_addr) {
//        this.mac_addr = mac_addr;
//    }

    public String getLogin_status() {
        return login_status;
    }

    public void setLogin_status(String login_status) {
        this.login_status = login_status;
    }

    public DDateTime getLogin_time() {
        return login_time;
    }

    public void setLogin_time(DDateTime login_time) {
        this.login_time = login_time;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

}
