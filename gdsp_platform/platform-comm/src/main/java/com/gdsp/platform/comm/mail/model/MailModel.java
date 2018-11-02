package com.gdsp.platform.comm.mail.model;

import java.util.Properties;

import com.gdsp.dev.core.model.entity.AuditableEntity;

public class MailModel extends AuditableEntity {

	// 登陆邮件发送服务器的用户名和密码   
    private String   userName;
    private String   password;
    // 是否需要身份验证   
    private boolean  validate       = false;
 // 发送邮件的服务器的IP和端口   
    private String   host;
    private String   port = "25";
    private String 	appId;
    private String  fromAddress;
    
	private MailInfo mailInfo;
    
    public MailInfo getMailInfo() {
		return mailInfo;
	}
	public void setMailInfo(MailInfo mailInfo) {
		this.mailInfo = mailInfo;
	}
	public Properties getProperties() {
        Properties p = new Properties();
        p.put("mail.smtp.host", this.host);
        p.put("mail.smtp.port", this.port);
        p.put("mail.smtp.auth", validate ? "true" : "false");
        return p;
    }
	public String getFromAddress() {
		return fromAddress;
	}
	public void setFromAddress(String fromAddress) {
		this.fromAddress = fromAddress;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public boolean isValidate() {
		return validate;
	}
	public void setValidate(boolean validate) {
		this.validate = validate;
	}
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
	public String getPort() {
		return port;
	}
	public void setPort(String port) {
		this.port = port;
	}
	public String getAppId() {
		return appId;
	}
	public void setAppId(String appId) {
		this.appId = appId;
	}
    
    
}
