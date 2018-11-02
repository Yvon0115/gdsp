package com.gdsp.dev.core.utils;

import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternUtils;

public class FileUtils {
    
    /**
     * Don't let anyone instantiate this class.
     */
    private FileUtils(){
        throw new IllegalAccessError("Utility class");
    }
    
	private static final Logger logger = LoggerFactory.getLogger(FileUtils.class);
	/**
     * 默认的application属性文件的路径
     */
    public static final String PROPERTY_CONFIG_AP  = "classpath*:application.properties";
    /**
     * 默认的datasource属性文件的路径
     */
    public static final String PROPERTY_CONFIG_DA  = "classpath*:datasource.properties";
    public static void close(Closeable obj) {
        try {
            if (obj != null) {
                obj.close();
            }
        } catch (Exception e) {
        	logger.error(e.getMessage(),e);
        }
    }

    public static void delete(String filePath) {
        if (filePath != null && !"".equals(filePath.trim())) {
            delete(new File(filePath));
        }
    }

    public static void delete(File file) {
        if (file != null && file.exists()) {
            if (file.isDirectory()) {
                File[] files = file.listFiles();
                for (File subFile : files) {
                    delete(subFile);
                }
            }
            file.delete();
        }
    }
    /**
     * 读取application属性文件信息
     * @param name
     * @return
     */
    public static String getFileIO(String name,boolean fileFlag) {
    	Properties prop = new Properties();
		InputStream fis = null;
        try {
        	ResourceLoader resourceLoader = new DefaultResourceLoader();
            ResourcePatternResolver resolver = ResourcePatternUtils.getResourcePatternResolver(resourceLoader);
			Resource[] resources;
			if(fileFlag){
				//读取的是application.xml文件
				resources = resolver.getResources(PROPERTY_CONFIG_AP);
			}else{
				//读取的是datasource.xml文件
				resources = resolver.getResources(PROPERTY_CONFIG_DA);
			}
			if(resources == null){
			    if(fileFlag){
			        //无法找到该配置文件
	                logger.info("Could not find properties from path:{}, {} ", PROPERTY_CONFIG_AP);
			    }else{
			        //无法找到该配置文件
	                logger.info("Could not find properties from path:{}, {} ", PROPERTY_CONFIG_DA);
			    }
			}
            for (Resource r : resources) {
            	fis = r.getInputStream();
            	prop.load(new InputStreamReader(fis, "utf-8"));
            	fis.close();
            }
            return prop.getProperty(name);
		} catch (IOException ex) {
			if(fileFlag){
				logger.info("Could not load properties from path:{}, {} ", PROPERTY_CONFIG_AP, ex.getMessage(),ex);
			}else{
				logger.info("Could not load properties from path:{}, {} ", PROPERTY_CONFIG_DA, ex.getMessage(),ex);
			}
        }
        return null;
    }
}
