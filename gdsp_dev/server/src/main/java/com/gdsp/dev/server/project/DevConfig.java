package com.gdsp.dev.server.project;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gdsp.dev.server.utils.FileUtils;

public class DevConfig {
    /**
     * 日志对象
     */
    private static Logger      logger          = LoggerFactory.getLogger(DevConfig.class);

    private static DevConfig config = new DevConfig();
    private Properties       properties;

    private DevConfig() {
        properties = new Properties();
    }

    /**
     * 加载开发配置
     * @param configPath
     */
    public static void loadDevConfig(String configPath) {
        //加载开发配置
        File file = new File(configPath);
        if (file.exists() && file.isFile()) {
            InputStream is = null;
            try {
                is = new FileInputStream(file);
                Properties properties = new Properties();
                properties.load(is);
                config.properties = properties;
            } catch (Exception e) {
                System.out.println("devconfig文件读取出错，请确认是否是正确的properties文件");
                //throw new RuntimeException(e);
                logger.error(e.getMessage(), e);
                
            } finally {
                FileUtils.close(is);
            }
        }
    }

    public static DevConfig getDevConfig() {
        return config;
    }

    public Properties getProperties() {
        return properties;
    }
}
