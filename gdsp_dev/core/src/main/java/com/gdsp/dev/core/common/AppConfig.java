package com.gdsp.dev.core.common;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.Properties;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternUtils;

import com.gdsp.dev.base.lang.DDate;
import com.gdsp.dev.base.lang.DDateTime;
import com.gdsp.dev.base.lang.DTime;
import com.gdsp.dev.base.utils.data.TypeConvert;

/**
 * 全局配置类暂时
 * @author paul.yang
 * @version 1.0 2014年9月24日
 * @since 1.6
 */
public class AppConfig implements Serializable {

    /**
     * 序列id
     */
    private static final long  serialVersionUID = 8100144845986715737L;
    /**
     * 日志变量
     */
    protected static final Logger    logger           = LoggerFactory.getLogger(AppConfig.class);
    /**
     * 默认的属性文件的路径
     */
    public final static String PROPERTY_CONFIG  = "classpath*:gdsp/conf/gdsp-*.properties";
    /**
     * 默认的属性文件的路径
     */
    public final static String KEY_APPNAME      = "applicationName";
    /**
     * 默认的属性文件的路径
     */
    public final static String DEFAULT_APPNAME  = "gdsp Server";
    /**
     * 存储配置的map
     */
    private Properties         configs          = new Properties();
    /**
     * 应用名称
     */
    private String             appName          = null;
    /**
     * 单例类
     */
    private static AppConfig   instance         = new AppConfig();

    /**
     * 构造方法
     */
    private AppConfig() {
        configs = new Properties();
        logger.debug("Loading properties file from path:{}", PROPERTY_CONFIG);
        InputStream is = null;
        try {
            ResourceLoader resourceLoader = new DefaultResourceLoader();
            ResourcePatternResolver resolver = ResourcePatternUtils.getResourcePatternResolver(resourceLoader);
            Resource[] resources = resolver.getResources(PROPERTY_CONFIG);
            if (resources == null)
                return;
            for (Resource r : resources) {
                is = r.getInputStream();
                configs.load(new InputStreamReader(is, "utf-8"));
                is.close();
            }
        } catch (IOException ex) {
            logger.info("Could not load properties from path:{}, {} ", PROPERTY_CONFIG, ex.getMessage(),ex);
        } finally {
            IOUtils.closeQuietly(is);
            logger.debug("Loaded properties file");
        }
    }

    /**
     * 取得配置实例
     * @return 配置对象
     */
    public static AppConfig getInstance() {
        return instance;
    }

    /**
     * 取得所有配置
     * @return 所有配置项
     */
    public Properties getConfig() {
        return configs;
    }

    /**
     * 取得应用名称
     * @return 应用名称
     */
    public String getApplicationName() {
        if (appName == null)
            appName = getString(KEY_APPNAME, DEFAULT_APPNAME);
        return appName;
    }

    /**
     * 根据配置名称取得字符串配置值（配置名形式为nodeName.nodeName）
     * @param name 配置名称
     * @return 指定配置字符串值
     */
    public String getString(String name) {
        return configs.getProperty(name);
    }

    /**
     * 根据配置名称取得字符串配置值，如果配置值为空使用指定的默认值
     * @param name 配置名称
     * @param defaultValue 指定默认值
     * @return 最终配置值
     */
    public String getString(String name, String defaultValue) {
        String value = getString(name);
        if (StringUtils.isEmpty(value)) {
            return defaultValue;
        }
        return value;
    }

    /**
     * 根据配置名称取得布尔配置值
     * @param name 配置名称
     * @return 布尔配置值
     */
    public boolean getBoolean(String name) {
        return TypeConvert.parseBoolean(getString(name));
    }

    /**
     * 根据配置名称取得布尔配置值，如果配置值为空使用指定的默认值
     * @param name 配置名称
     * @param defaultValue 默认布尔值
     * @return 最终配置值
     */
    public boolean getBoolean(String name, boolean defaultValue) {
        String value = getString(name);
        if (StringUtils.isEmpty(value))
            return defaultValue;
        return TypeConvert.parseBoolean(value);
    }

    /**
     * 根据配置名称取得整型配置值
     * @param name 配置名称
     * @return 整型配置值
     */
    public int getInt(String name) {
        return TypeConvert.parseInt(getString(name));
    }

    /**
     * 根据配置名称取得整型配置值，如果配置值为空使用指定的默认值
     * @param name 配置名称
     * @param defaultValue 默认整型值
     * @return 最终配置值
     */
    public int getInt(String name, int defaultValue) {
        String value = getString(name);
        if (StringUtils.isEmpty(value))
            return defaultValue;
        return TypeConvert.parseInt(value);
    }

    /**
     * 根据配置名称取得长整型配置值
     * @param name 配置名称
     * @return 长整型配置值
     */
    public long getLong(String name) {
        return TypeConvert.parseLong(getString(name));
    }

    /**
     * 根据配置名称取得长整型配置值，如果配置值为空使用指定的默认值
     * @param name 配置名称
     * @param defaultValue 默认长整型值
     * @return 最终配置值
     */
    public long getLong(String name, long defaultValue) {
        String value = getString(name);
        if (StringUtils.isEmpty(value))
            return defaultValue;
        return TypeConvert.parseLong(value);
    }

    /**
     * 根据配置名称取得浮点型配置值
     * @param name 配置名称
     * @return 浮点型配置值
     */
    public float getFloat(String name) {
        return TypeConvert.parseFloat(getString(name));
    }

    /**
     * 根据配置名称取得浮点型配置值，如果配置值为空使用指定的默认值
     * @param name 配置名称
     * @param defaultValue 默认浮点型值
     * @return 最终配置值
     */
    public float getFloat(String name, float defaultValue) {
        String value = getString(name);
        if (StringUtils.isEmpty(value))
            return defaultValue;
        return TypeConvert.parseFloat(value);
    }

    /**
     * 根据配置名称取得双浮点型配置值
     * @param name 配置名称
     * @return 双浮点型配置值
     */
    public double getDouble(String name) {
        return TypeConvert.parseDouble(getString(name));
    }

    /**
     * 根据配置名称取得双浮点型配置值，如果配置值为空使用指定的默认值
     * @param name 配置名称
     * @param defaultValue 默认双浮点型值
     * @return 最终配置值
     */
    public double getDouble(String name, double defaultValue) {
        String value = getString(name);
        if (StringUtils.isEmpty(value))
            return defaultValue;
        return TypeConvert.parseDouble(value);
    }

    /**
     * 根据配置名称取得自定义日期型配置值
     * @param name 配置名称
     * @return 自定义日期型配置值
     */
    public DDate getDate(String name) {
        return TypeConvert.parseDDate(getString(name));
    }

    /**
     * 根据配置名称取得自定义日期型配置值，如果配置值为空使用指定的默认值
     * @param name 配置名称
     * @param defaultValue 默认自定义日期型值
     * @return 最终配置值
     */
    public DDate getDate(String name, DDate defaultValue) {
        String value = getString(name);
        if (StringUtils.isEmpty(value))
            return defaultValue;
        return TypeConvert.parseDDate(value);
    }

    /**
     * 根据配置名称取得自定义时间型配置值
     * @param name 配置名称
     * @return 自定义时间型配置值
     */
    public DTime getTime(String name) {
        return TypeConvert.parseDTime(getString(name));
    }

    /**
     * 根据配置名称取得自定义时间型配置值，如果配置值为空使用指定的默认值
     * @param name 配置名称
     * @param defaultValue 默认自定义时间值
     * @return 最终配置值
     */
    public DTime getTime(String name, DTime defaultValue) {
        String value = getString(name);
        if (StringUtils.isEmpty(value))
            return defaultValue;
        return TypeConvert.parseDTime(value);
    }

    /**
     * 根据配置名称取得自定义日期时间型配置值
     * @param name 配置名称
     * @return 自定义日期时间型配置值
     */
    public DDateTime getDateTime(String name) {
        return TypeConvert.parseDDateTime(getString(name));
    }

    /**
     * 根据配置名称取得自定义日期时间型配置值，如果配置值为空使用指定的默认值
     * @param name 配置名称
     * @param defaultValue 默认自定义日期时间型值
     * @return 最终配置值
     */
    public DDateTime getDateTime(String name, DDateTime defaultValue) {
        String value = getString(name);
        if (StringUtils.isEmpty(value))
            return defaultValue;
        return TypeConvert.parseDDateTime(value);
    }

    /**
     * 增加静态读取配置属性方法 <br/>
     * @param name 属性名称
     * @return 属性值
     */
    public static String getProperty(String name) {
        return getInstance().getString(name);
    }
}
