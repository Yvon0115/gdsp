package com.gdsp.platform.comm.mail.impl;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gdsp.dev.base.exceptions.BusinessException;
import com.gdsp.dev.core.utils.FileUtils;
import com.gdsp.platform.comm.mail.dao.MailRegistryDao;
import com.gdsp.platform.comm.mail.model.MailInfo;
import com.gdsp.platform.comm.mail.model.MailModel;
import com.gdsp.platform.comm.mail.service.IMailPubService;
import com.gdsp.platform.comm.mail.utils.MailUtils;
import com.gdsp.platform.config.customization.helper.SystemConfigConst;
import com.gdsp.platform.config.customization.model.MailServiceConfVO;
import com.gdsp.platform.config.customization.service.ISystemConfExtService;
@Service
public class MailPubServiceImpl implements IMailPubService {
	
	private static final Logger logger = LoggerFactory.getLogger(MailPubServiceImpl.class);
	@Autowired
    private ISystemConfExtService 		iSystemConfExtService;
	@Autowired
	private MailRegistryDao mailRegistryDao;
	@Override
	public void sendMail(MailInfo mailInfo, String appId) {
		//TODO查询系统配置邮件服务项是否开启
		MailServiceConfVO mailServiceConfVO = iSystemConfExtService.queryMailServiceConfs();
		if(mailInfo != null && mailServiceConfVO.getStatus().equalsIgnoreCase("y")){
			//BusinessException businessException = new BusinessException();
			//邮件配置信息读取方式：1读取属性文件，0读取数据库
			if(mailServiceConfVO.getConfLocation().equalsIgnoreCase(SystemConfigConst.SYS_MAILCONF_LOCATION_PROP)){
				// 发送邮件
		        MailModel mailModel = new MailModel();
		        mailModel.setHost(FileUtils.getFileIO("mail.host",true));
		        mailModel.setPort(FileUtils.getFileIO("mail.port",true));
				mailModel.setValidate(Boolean.parseBoolean(FileUtils.getFileIO("mail.smtp.auth",true)));
				mailModel.setUserName(FileUtils.getFileIO("mail.username",true));
				mailModel.setPassword(FileUtils.getFileIO("mail.password",true));//您的邮箱密码   
				mailModel.setUserName(FileUtils.getFileIO("mail.username",true));
		        mailModel.setMailInfo(mailInfo);
		        MailUtils.senderHtmlMail(mailModel);
		        
			}else{
				if(StringUtils.isNotBlank(appId)){
					MailModel  mailModel= mailRegistryDao.findMRByAppkey(appId);
					if(mailModel != null && mailInfo != null){
						mailModel.setMailInfo(mailInfo);
						logger.info("准备发送邮件.....");
							MailUtils.senderHtmlMail(mailModel);
						logger.info("邮件发送成功");
					}else {
						throw new BusinessException("未注册邮件服务！");
					}
				}
			}
			
			
		}else {
			logger.info("邮件服务未开启");
			throw new BusinessException("邮件服务未开启！");
		}
		
	}

}
