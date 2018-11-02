package com.gdsp.dev.core.ext;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.gdsp.dev.core.utils.EncryptAndDecodeUtils;
import com.gdsp.dev.core.utils.FileUtils;

import ch.qos.logback.core.db.DriverManagerConnectionSource;

/**
 * 数据源连接管理(加解密控制)
 */
public class EncryptDriverManagerConnectionSource extends DriverManagerConnectionSource {
	//从application.properties中读取秘钥
	private String encryptKey = FileUtils.getFileIO("encryptKey",true);
	//从application.properties中读取系统模式
	private String dsPasswordModel = FileUtils.getFileIO("dsPasswordModel",true);
	
	@Override
	public Connection getConnection() throws SQLException {
	    if (getUser() == null) {
	      return DriverManager.getConnection(getUrl());
	    } else {
	      //返回连接时，对用户密码进行解密
	    	if("show".equals(dsPasswordModel)){
	    		//是开发模式,不进行解密
	    		return DriverManager.getConnection(getUrl(), getUser(), getPassword());
	    	}else{
	    		return DriverManager.getConnection(getUrl(), EncryptAndDecodeUtils.getDecryptString(getUser(),encryptKey), EncryptAndDecodeUtils.getDecryptString(getPassword(),encryptKey));
	    	}
	    }
	  }
}
