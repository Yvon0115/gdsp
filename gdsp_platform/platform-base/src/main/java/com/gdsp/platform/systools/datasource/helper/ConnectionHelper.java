package com.gdsp.platform.systools.datasource.helper;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;

import com.gdsp.platform.systools.datasource.model.DataSourceVO;

/**
 * 大数据建立连接的帮助类
 * @author lyf
 *
 */
public class ConnectionHelper {

	
	/**
	 * 华为Hive大数据平台安全验证
	 * @throws IOException,IllegalAccessException,InstantiationException 
	 */
	public static boolean safeAuthenOfHuaweiHive(DataSourceVO dataSource) throws InstantiationException,
    IllegalAccessException,IOException,Exception{
		String ZOOKEEPER_DEFAULT_LOGIN_CONTEXT_NAME = "Client";
		String ZOOKEEPER_SERVER_PRINCIPAL_KEY = "zookeeper.server.principal";    
		String ZOOKEEPER_DEFAULT_SERVER_PRINCIPAL = "zookeeper/hadoop";
		String KRB5_FILE = null;
		String USER_NAME = null;
		String USER_KEYTAB_FILE = null;
		Configuration CONF = new Configuration();
		USER_NAME = dataSource.getUsername();
		USER_KEYTAB_FILE = dataSource.getKeytabPath();
		KRB5_FILE = dataSource.getKrbConfPath();
		LoginHelper.setJaasConf(ZOOKEEPER_DEFAULT_LOGIN_CONTEXT_NAME, USER_NAME, USER_KEYTAB_FILE);
		LoginHelper.setZookeeperServerPrincipal(ZOOKEEPER_SERVER_PRINCIPAL_KEY,
				ZOOKEEPER_DEFAULT_SERVER_PRINCIPAL);
		// Zookeeper登录认证
		LoginHelper.login(USER_NAME, USER_KEYTAB_FILE, KRB5_FILE, CONF);
		return true;
	}
}
