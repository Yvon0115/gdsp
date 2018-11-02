package com.gdsp.platform.config.customization.model;

import com.gdsp.dev.core.model.entity.AuditableEntity;

/**
 * @author yucl
 * @version 3.1 2018/6/21 14:03
 * @since 1.6
 */
public class MailServiceConfVO extends AuditableEntity {

    /**
	 * 序列化id
	 */
	private static final long serialVersionUID = 7954081693436577812L;
	private String                      status;                       //邮箱服务开关状态 Y：启用， N：停用
    private String                      confLocation;            //邮件服务器配置读取位置  “0”： 数据库，“1”： 配置文件;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getConfLocation() {
        return confLocation;
    }

    public void setConfLocation(String confLocation) {
        this.confLocation = confLocation;
    }
}
