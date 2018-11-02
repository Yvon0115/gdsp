package com.gdsp.platform.comm.mail.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gdsp.dev.base.exceptions.BusinessException;
import com.gdsp.platform.comm.mail.model.MailModel;

public class SimpleMailSender {
	private static final Logger logger = LoggerFactory.getLogger(SimpleMailSender.class);
    public boolean sendTextMail(MailModel model) {
        // 判断是否需要身份认证   
        MyAuthenticator authenticator = null;
        Properties pro = model.getProperties();
        if (model.isValidate()) {
            // 如果需要身份认证，则创建一个密码验证器   
            authenticator = new MyAuthenticator(model.getUserName(), model.getPassword());
        }
        // 根据邮件会话属性和密码验证器构造一个发送邮件的session   
        Session sendMailSession = Session.getDefaultInstance(pro, authenticator);
        try {
            // 根据session创建一个邮件消息   
            Message mailMessage = new MimeMessage(sendMailSession);
            // 创建邮件发送者地址   
            Address from = new InternetAddress(model.getUserName());
            // 设置邮件消息的发送者   
            mailMessage.setFrom(from);
            // 创建邮件的接收者地址，并设置到邮件消息中   
            // Address to = new InternetAddress(model.getToAddress());   
            //mailMessage.setRecipient(Message.RecipientType.TO,to);
            //创建邮件的接收地址（数组）
            String[] to = model.getMailInfo().getToAddress();
            InternetAddress[] sendTo = new InternetAddress[to.length];
            for (int i = 0; i < to.length; i++) {
                System.out.println("发送到:" + to[i]);
                sendTo[i] = new InternetAddress(to[i]);
            }
            mailMessage.setRecipients(javax.mail.internet.MimeMessage.RecipientType.TO, sendTo);
            // 设置邮件消息的主题   
            mailMessage.setSubject(model.getMailInfo().getSubject());
            // 设置邮件消息发送的时间   
            mailMessage.setSentDate(new Date());
            // 设置邮件消息的主要内容   
            String mailContent = model.getMailInfo().getContent();
            mailMessage.setText(mailContent);
            // 发送邮件   
            Transport.send(mailMessage);
            return true;
        } catch (MessagingException ex) {
        	logger.error(ex.getMessage(),ex);
        }
        return false;
    }

    public static boolean sendHtmlMail(MailModel model) throws UnsupportedEncodingException {
        // 判断是否需要身份认证   
        MyAuthenticator authenticator = null;
        Properties pro = model.getProperties();
        //如果需要身份认证，则创建一个密码验证器    
        if (model.isValidate()) {
            authenticator = new MyAuthenticator(model.getUserName(), model.getPassword());
        }
        // 根据邮件会话属性和密码验证器构造一个发送邮件的session   
        Session sendMailSession = Session.getInstance(pro, authenticator);
        
        try {
            // 根据session创建一个邮件消息   
            Message mailMessage = new MimeMessage(sendMailSession);
            // 创建邮件发送者地址   
            Address from = new InternetAddress(model.getUserName());
            // 设置邮件消息的发送者   
            mailMessage.setFrom(from);
            //创建邮件的接收地址（数组）
            String[] to = model.getMailInfo().getToAddress();
            InternetAddress[] sendTo = new InternetAddress[to.length];
            for (int i = 0; i < to.length; i++) {
                System.out.println("发送到:" + to[i]);
                sendTo[i] = new InternetAddress(to[i]);
            }
            //创建抄送者接收地址
            String[] cc = model.getMailInfo().getCcs();
            if(cc != null && cc.length > 0){
            	InternetAddress[] sendCc = new InternetAddress[cc.length];
                for (int i = 0; i < cc.length; i++) {
                    System.out.println("抄送到:" + cc[i]);
                    sendCc[i] = new InternetAddress(cc[i]);
                }
                mailMessage.setRecipients(javax.mail.internet.MimeMessage.RecipientType.CC, sendCc);
            }
          //创建密送者接收地址
            String[] Bcc = model.getMailInfo().getBccs();
            if(Bcc!= null && Bcc.length > 0){
            	InternetAddress[] sendBcc = new InternetAddress[Bcc.length];
                for (int i = 0; i < Bcc.length; i++) {
                    System.out.println("密送到:" + Bcc[i]);
                    sendBcc[i] = new InternetAddress(Bcc[i]);
                }
                mailMessage.setRecipients(javax.mail.internet.MimeMessage.RecipientType.BCC, sendBcc);
            }
            mailMessage.setRecipients(javax.mail.internet.MimeMessage.RecipientType.TO, sendTo);
            /*设置邮件消息的主题 
             2017/3/31 为邮件标题添加编码   update by zyq */  
            mailMessage.setSubject(MimeUtility.encodeText(model.getMailInfo().getSubject(), MimeUtility.mimeCharset("utf-8"), null));
            // 设置邮件消息发送的时间   
            mailMessage.setSentDate(new Date());
            // MiniMultipart类是一个容器类，包含MimeBodyPart类型的对象   
            Multipart mainPart = new MimeMultipart();
            // 创建一个包含HTML内容的MimeBodyPart   
            BodyPart html = new MimeBodyPart();
            // 设置HTML内容     建立第一部分： 文本正文
            html.setContent(model.getMailInfo().getContent(), "text/html; charset=utf-8");
            mainPart.addBodyPart(html);
            // 将MiniMultipart对象设置为邮件内容   建立第二部分：附件
            mailMessage.setContent(mainPart);
            
            String[] fileName = null;
			File[] file = null;
			//判断是否有附件
			if(model.getMailInfo().getAttachment() != null && model.getMailInfo().getAttachment().length > 0){
				//根据附件个数，定义保存附件名称的数组
				fileName =new String[model.getMailInfo().getAttachment().length];
				//根据附件个数，定义保存附件的数组
				file = new File[model.getMailInfo().getAttachment().length];
				/**
				 * 以下逻辑是通过流将MultipartFile对象转为File对象
				 */
				File f = null;
				InputStream ins;
				for(int i = 0;i < model.getMailInfo().getAttachment().length;i++){
					fileName[i] = model.getMailInfo().getAttachment()[i].getOriginalFilename();
					
					if(model.getMailInfo().getAttachment()[i].equals("")){  
						model.getMailInfo().getAttachment()[i] = null;  
		            }else{  
		                try {
							ins = model.getMailInfo().getAttachment()[i].getInputStream();
							f=new File(model.getMailInfo().getAttachment()[i].getOriginalFilename());  
			                try {  
			                    OutputStream os = new FileOutputStream(f);  
			                    int bytesRead = 0;  
			                    byte[] buffer = new byte[8192];  
			                    while ((bytesRead = ins.read(buffer, 0, 8192)) != -1) {  
			                     os.write(buffer, 0, bytesRead);  
			                    }  
			                    os.close();  
			                    ins.close(); 
			                    file[i] = f;
			                   
			                   } catch (Exception e) {  
			                    e.printStackTrace();  
			                   }   
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}  
		                
		            } 
				}
				if (fileName.length > 0 && file.length>0) {
	                for (int i = 0; i < fileName.length; i++) {
	                    if (!fileName[i].equals("")) {
	                        // 建立第二部分：附件
	                        html = new MimeBodyPart();
	                        // 获得附件
	                        DataSource source = new FileDataSource(file[i]);
	                        
	                        System.out.println(source.getContentType());
	                        // 设置附件的数据处理器
	                        html.setDataHandler(new DataHandler(source));
	                        // 设置附件文件名
	                        html.setFileName(MimeUtility.encodeText(fileName[i]));
	                        
	                        // 加入第二部分
	                        mainPart.addBodyPart(html);
	                    }
	                }
	            }
				
			}
            
            // 发送邮件   
            Transport.send(mailMessage);
            //删除通过流将MultipartFile对象转为File对象的临时文件。此文件一般在D:\codept\Gdsp\gdsp_portal\pt-proj-integration路径下
			if(file != null){
				 for(int i = 0;i < file.length; i++){
						File del = new File(file[i].toURI());  
						 del.delete();
					}
			}
           
			return true;
            
        } catch (MessagingException ex) {
        	logger.error(ex.getMessage(),ex);
            throw new BusinessException("发送邮件错误！");
        }
    }
}