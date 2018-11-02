package com.gdsp.dev.base.utils.clazz;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Class操作工具类,无异常
 * 
 * @author zhaojianjun
 *
 */
public class ClassEasyUtils {

    /** 日志对象不能依赖common包 */
    private static final Logger logger = LoggerFactory.getLogger(ClassEasyUtils.class);

    /**
     * 取得当前默认的ClassLoader
     * 
     * @return 默认的ClassLoader
     */
    public static ClassLoader getDefaultClassLoader() {
        ClassLoader loader = null;
        try {
            loader = Thread.currentThread().getContextClassLoader();
        } catch (Exception ex) {
            logger.debug("Cannot access thread context ClassLoader - falling back to system class loader", ex);
        }
        if (loader == null) {
            loader = ClassEasyUtils.class.getClassLoader();
        }
        return loader;
    }

    /**
     * 取得指定类的ClassLoader，如果此类不存在则返回默认的
     * 
     * @param clazz
     *            指定的类
     * @return ClassLoader
     */
    public static ClassLoader getClassLoader(Class<?> clazz) {
        ClassLoader loader = clazz.getClassLoader();
        if (loader == null)
            loader = getDefaultClassLoader();
        return loader;
    }

    /**
     * 根据类名，调用类加载指定的类
     * 
     * @param className
     *            指定类名
     * @param fromClass
     *            调用类
     * @return 加载到的类
     * @throws ClassNotFoundException
     */
    public static Class<?> loadClass(String className, Class<?> fromClass) {
        try {
            return ClassUtils.loadClass(className, fromClass);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * 根据类名加载指定的类
     * 
     * @param className
     *            指定类名
     * @return 加载到的类
     * @throws ClassNotFoundException
     */
    public static Class<?> loadClass(String className) {
        return loadClass(className, null);
    }

    /**
     * 根据类名取得类的无参构造方法的类实例
     * 
     * @param className
     *            类名
     * @return 类实例
     */
    public static Object newInstance(String className) {
        try {
            return ClassUtils.newInstance(className);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * 根据类名，参数数组，参数类型列表取得类实例
     * 
     * @param className
     *            类名
     * @param args
     *            参数数组
     * @param argTypes
     *            参数类型列表
     * @return 类实例
     */
    public static Object newInstance(String className, Object[] args, Class<?>... argTypes) {
        try {
            return ClassUtils.newInstance(className, args, argTypes);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * 根据对象类对象，参数数组，参数类型列表取得类实例
     * 
     * @param clazz
     *            类对象
     * @param args
     *            参数数组
     * @param argTypes
     *            参数类型列表
     * @return 类实例
     */
    public static Object newInstance(Class<?> clazz, Object[] args, Class<?>... argTypes) {
        try {
            return ClassUtils.newInstance(clazz, args, argTypes);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * 根据类名，调用类加载指定的资源文件
     * 
     * @param resource
     *            指定资源文件路径
     * @param fromClass
     *            调用类
     * @return 加载到的资源文件
     */
    public static URL loadResource(String resource, Class<?> fromClass) {
        return ClassUtils.loadResource(resource, fromClass);
    }

    /**
     * 根据类名加载指定的资源文件
     * 
     * @param resource
     *            指定资源文件路径
     * @return 加载到的资源文件
     */
    public static URL loadResource(String resource) {
        return loadResource(resource, null);
    }

    /**
     * 根据类名，调用类加载指定的资源文件流
     * 
     * @param resource
     *            指定资源文件路径
     * @param fromClass
     *            调用类
     * @return 加载到的资源文件流
     * @throws IOException
     */
    public static InputStream loadResourceAsStream(String resource, Class<?> fromClass) {
        InputStream is = null;
        try {
            is = ClassUtils.loadResourceAsStream(resource, fromClass);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
        return is;
    }

    /**
     * 根据类名加载指定的资源文件流
     * 
     * @param resource
     *            指定资源文件路径
     * @return 加载到的资源文件流
     * @throws IOException
     */
    public static InputStream loadResourceAsStream(String resource) {
        return loadResourceAsStream(resource, null);
    }
}
