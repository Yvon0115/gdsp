package com.gdsp.platform.comm.mail.service;

import com.gdsp.platform.comm.mail.model.MailInfo;
/*
 * 邮件服务接口
 */
public interface IMailPubService {
	public void sendMail(MailInfo mailInfo,String appId);
}
