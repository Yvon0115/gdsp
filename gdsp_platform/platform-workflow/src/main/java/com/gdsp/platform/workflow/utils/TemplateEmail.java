package com.gdsp.platform.workflow.utils;

import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import freemarker.template.Template;

/** 
 * 发送邮件 可以自己编写html模板 
 */
public class TemplateEmail extends SimpleMailMessage {
	private static final long serialVersionUID = -3610390038756241867L;
	private static final Logger log = Logger.getLogger(TemplateEmail.class);
    @Autowired
    private JavaMailSender       mailSender;
    @Autowired
    private FreeMarkerConfigurer freemarkerConfig; //FreeMarker的技术类 

    /** 
     * 生成html模板字符串 
     * @param root 存储动态数据的map 
     * @return 
     */
    private String getMailText(Map<String, Object> root, String templateName) {
        String htmlText = "";
        try {
            //通过指定模板名获取FreeMarker模板实例            	
            Template tpl = freemarkerConfig.getConfiguration().getTemplate(templateName);
            htmlText = FreeMarkerTemplateUtils.processTemplateIntoString(tpl, root);
        } catch (Exception e) {
        	log.error(e.getMessage(),e);
        }
        return htmlText;
    }

    /** 
     * 发送邮件 
     * @param root 存储动态数据的map 
     * @param toEmail 邮件地址 
     * @param subject 邮件主题 
     * @return 
     */
    public boolean sendTemplateMail(Map<String, Object> root, String toEmail, String subject, String templateName) {
        try {
            MimeMessage msg = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(msg, false, "utf-8");//由于是html邮件，不是mulitpart类型
            helper.setFrom("li.xu8@***.com");
            helper.setTo(toEmail);
            helper.setSubject(subject);
            String htmlText = getMailText(root, templateName);//使用模板生成html邮件内容
            helper.setText(htmlText, true);
            mailSender.send(msg);
            return true;
        } catch (MailException e) {
            log.error(e.getMessage(),e);
            return false;
        } catch (MessagingException e) {
            log.error(e.getMessage(),e);
            return false;
        }
    }
}