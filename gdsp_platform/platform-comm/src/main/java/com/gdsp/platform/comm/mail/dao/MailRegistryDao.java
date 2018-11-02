package com.gdsp.platform.comm.mail.dao;

import com.gdsp.dev.persist.ext.MBDao;
import com.gdsp.platform.comm.mail.model.MailModel;

@MBDao
public interface MailRegistryDao {
	//根据Appkey查询邮件注册对象
	public MailModel findMRByAppkey(String Appkey);
	//保存邮件注册对象
	public void saveMailRegistryVO(MailModel mailModel);
}
