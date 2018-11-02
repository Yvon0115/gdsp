package com.gdsp.platform.comm.mail.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.gdsp.platform.comm.mail.model.MailInfo;
import com.gdsp.platform.comm.mail.service.IMailPubService;
/**
 * 
 * @author yxq
 *	邮件服务
 */
@Controller
@RequestMapping("mail")
public class MailController {
	/**
	 * 用于邮件发送
	 * @param mailInfo 邮件内容
	 * @param appId		调用者
	 */
	@Autowired
	private IMailPubService mailService;
	
	@RequestMapping("/sendMail.d")
	public void sendMail(MailInfo mailInfo,String appId){
		mailService.sendMail(mailInfo, appId);
	}
}
