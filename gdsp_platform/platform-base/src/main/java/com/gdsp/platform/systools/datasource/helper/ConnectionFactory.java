package com.gdsp.platform.systools.datasource.helper;

import java.io.File;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.Properties;
import java.util.logging.Logger;

import javax.sql.DataSource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;

import com.gdsp.dev.core.utils.EncryptAndDecodeUtils;
import com.gdsp.dev.core.utils.FileUtils;
import com.gdsp.platform.config.global.helper.DefDocConst;
import com.gdsp.platform.systools.datasource.model.DataSourceVO;
import com.gdsp.platform.systools.datasource.service.IDataSourceService;

public class ConnectionFactory implements DataSource {

    private DataSourceVO dataSource;
    private ClassLoader  classLoader;
    private Driver       driver;
    /**
       * @Fields encryptKey : 由于数据源密码加密时用的秘钥是reportKey，所以从application.properties中读取秘钥的关键词是reportKey
       */
    private String       encryptKey = FileUtils.getFileIO("reportKey", true);
    private org.slf4j.Logger logger = LoggerFactory.getLogger(ConnectionFactory.class);


    public ConnectionFactory(DataSourceVO dataSource) throws ClassNotFoundException, MalformedURLException, InstantiationException, IllegalAccessException {
        super();
        this.dataSource = dataSource;
        this.initClassLoad();
    }

    @Override
    public PrintWriter getLogWriter() throws SQLException {
        return null;
    }

    @Override
    public void setLogWriter(PrintWriter out) throws SQLException {}

    @Override
    public void setLoginTimeout(int seconds) throws SQLException {}

    @Override
    public int getLoginTimeout() throws SQLException {
        return 0;
    }

    @Override
    public Logger getParentLogger() throws SQLFeatureNotSupportedException {
        return null;
    }

    @Override
    public <T> T unwrap(Class<T> iface) throws SQLException {
        return null;
    }

    @Override
    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        return false;
    }

    @Override
    public Connection getConnection() throws SQLException {
        return getConnection(this.dataSource.getUsername(), this.dataSource.getPassword());
    }

    @Override
    public Connection getConnection(String username, String password)
            throws SQLException {
    	String ds_type = this.dataSource.getType();
        String authtnType = this.dataSource.getAuthentication_model();
        if((ds_type != null && DefDocConst.DATASOURCE_HUAWEI_HIVE.equals(ds_type) 
    			&& authtnType != null && IDataSourceService.KERBEROS_MODEL.equals(authtnType))){
			//如果是安全模式，datasource参数中的用户名应该为空字符串
        	username = "";
    	}
        Properties properties = new Properties();
        properties.setProperty("user", username);
        properties.setProperty("password", EncryptAndDecodeUtils.getDecryptString(password, encryptKey).trim());
        return driver.connect(this.dataSource.getUrl(), properties);
    }
    
    public DataSource getSimpleDataSource() 
    		throws ClassNotFoundException, 
    		MalformedURLException, InstantiationException, 
    		IllegalAccessException{
    	
    	String url = this.dataSource.getUrl();
		Properties properties = new Properties();
        properties.setProperty("user", this.dataSource.getUsername());
        properties.setProperty("password", EncryptAndDecodeUtils.getDecryptString(this.dataSource.getPassword(), encryptKey).trim());
        SimpleDriverDataSource dmDatasource = new SimpleDriverDataSource();
		dmDatasource.setUrl(url);
		dmDatasource.setConnectionProperties(properties);
		dmDatasource.setDriver(driver);
		return dmDatasource;
    }

	/**
	* @Title: initClassLoad
	* @Description: 根据jarPath加载jar
	* @return 
	*/ 
    private void initClassLoad() throws ClassNotFoundException, MalformedURLException, InstantiationException, IllegalAccessException {
    	String path = this.dataSource.getPath();
    	if (StringUtils.isNotBlank(path)) {
	    	// 判断是否为系统资源
	    	URL dirUrl = ClassLoader.getSystemResource(path);
	    	if (dirUrl != null) {
	    		// 资源文件的相对路径获取绝对路径
		    		path = dirUrl.getPath();
		    		//TODO： 判断是否在jar中
		    		if (path.indexOf(".jar!") > -1) {
		    			// 如果在jar中则用_lib目录拼绝对路径，去掉”file:”（导出成带lib目录的可执行jar形式）
						path = path.replace(".jar!", "_lib").substring(path.indexOf(":") + 1);
					}
			}
	    	File fileDir = new File(path);
	    	if (!fileDir.exists()) {
	    		//  如果文件不存在，获取资源文件路径，加载默认驱动
	    		String classesPath = Thread.currentThread().getContextClassLoader().getResource("").getPath().toString();
				path = classesPath + path;
				fileDir = new File(path);
			}
	    	logger.info("要加载的驱动文件路径：" + path);
	    	if (fileDir != null) {
	    		logger.info("判断是否是文件夹：" + fileDir.isDirectory());
	    		URL[] urls = null;
	    		if (fileDir.isDirectory()) {
	    			// 如果是文件目录
	    			String[] fileNames = fileDir.list();
	    			if (fileNames != null && fileNames.length > 0) {
						urls = new URL[fileNames.length];
						for (int i = 0; i < urls.length; i++) {
							urls[i] = new URL("file:///" + path + File.separator + fileNames[i]);
						}
	    			}
				} else {
					// 只需加载单个jar
					urls = new URL[1];
					urls[0] = new URL("file:///" + path);
				}
				classLoader = new URLClassLoader(urls, Thread.currentThread().getContextClassLoader());		
		        Class<?> clazz = classLoader.loadClass(this.dataSource.getDriver());
		        driver = (Driver) clazz.newInstance();
			}
		}
    }
    
}
