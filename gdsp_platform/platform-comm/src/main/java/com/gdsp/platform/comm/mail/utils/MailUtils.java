package com.gdsp.platform.comm.mail.utils;

import java.io.UnsupportedEncodingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gdsp.dev.base.exceptions.BusinessException;
import com.gdsp.platform.comm.mail.model.MailModel;

public class MailUtils {
	private static final Logger logger = LoggerFactory.getLogger(MailUtils.class);

	/**
	 * 发送邮件
	 * @Description 发送Html邮件,支持多附件，多接收人
	 * @param model 设定文件
	 * @return void 返回类型
	 * @since 2017/03/13
	 */
    @SuppressWarnings("static-access")
	public static void senderHtmlMail(MailModel model) {
    	//这个类主要来发送邮件  
        SimpleMailSender sms = new SimpleMailSender();
        //sms.sendTextMail(mailInfo);//发送文体格式   
        try {
            sms.sendHtmlMail(model);
        } catch (UnsupportedEncodingException e) {
        	logger.error(e.getMessage(),e);
            throw new BusinessException("发送邮件错误！");
        } //发送html格式  
    }
    @SuppressWarnings("static-access")
	public static void sendTextMail(MailModel model) {
    	//这个类主要来发送邮件  
        SimpleMailSender sms = new SimpleMailSender();
        //sms.sendTextMail(mailInfo);//发送文体格式   
        sms.sendTextMail(model);
        
    }
    
}
