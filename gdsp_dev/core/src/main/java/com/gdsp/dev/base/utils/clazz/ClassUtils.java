package com.gdsp.dev.base.utils.clazz;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.net.URL;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;

/**
 * Class操作工具类
 * @author paul.yang
 * @version 1.0 2014年9月24日
 * @since 1.6
 */
public class ClassUtils {

    /**日志对象不能依赖common包*/
    private static final Logger logger = LoggerFactory.getLogger(ClassUtils.class);

    /**
     * 取得当前默认的ClassLoader
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
            loader = ClassUtils.class.getClassLoader();
        }
        return loader;
    }

    /**
     * 取得指定类的ClassLoader，如果此类不存在则返回默认的
     * @param clazz 指定的类
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
     * @param className 指定类名
     * @param fromClass 调用类
     * @return 加载到的类
     * @throws ClassNotFoundException 
     */
    public static Class<?> loadClass(String className, Class<?> fromClass) throws ClassNotFoundException {
        Class<?> targetClass = null;
        if (className == null)
            return targetClass;
        ClassLoader loader = null;
        //通过调用类的加载器加载类
        if (fromClass != null) {
            loader = fromClass.getClassLoader();
            while (loader != null) {
                try {
                    targetClass = loader.loadClass(className);
                } catch (ClassNotFoundException e) {
                    logger.debug(null, e);
                }
                if (targetClass != null) {
                    return targetClass;
                }
                loader = loader.getParent();
            }
        }
        //如果调用类的加载器加载不到则取默认加载器加载类
        loader = getDefaultClassLoader();
        while (loader != null) {
            try {
                targetClass = loader.loadClass(className);
            } catch (ClassNotFoundException e) {
                logger.debug(null, e);
            }
            if (targetClass != null) {
                return targetClass;
            }
            loader = loader.getParent();
        }
        //最后取系统类加载器加载类
        loader = ClassLoader.getSystemClassLoader();
        targetClass = loader.loadClass(className);
        return targetClass;
    }

    /**
     * 根据类名加载指定的类
     * @param className 指定类名
     * @return 加载到的类
     * @throws ClassNotFoundException 
     */
    public static Class<?> loadClass(String className) throws ClassNotFoundException {
        return loadClass(className, null);
    }

    /**
     * 根据类名取得类的无参构造方法的类实例
     * @param className 类名
     * @return 类实例
     */
    public static Object newInstance(String className) {
        Class<?> clazz = null;
        
        try {
            clazz = loadClass(className);
            return clazz.newInstance();
        } catch (InstantiationException e) {
            
            Exception cause = (Exception) ExceptionUtils.getRootCause(e);
            if (cause != null) {
                logger.error(cause.getMessage(),cause);
            }
            else{
                logger.error(e.getMessage(),e);
            }
           
        } catch (IllegalAccessException e) {
            
            logger.error(e.getMessage(),e);
        } catch (ClassNotFoundException e) {
            logger.error(e.getMessage(),e);
        }
        return clazz;
    }

    /**
     * 根据类名，参数数组，参数类型列表取得类实例
     * @param className 类名
     * @param args 参数数组
     * @param argTypes 参数类型列表
     * @return 类实例
     */
    public static Object newInstance(String className, Object[] args, Class<?>... argTypes) {
        Class<?> clazz = null;
        Constructor<?> constructor = null;
        try {
            clazz = loadClass(className);
            constructor = clazz.getConstructor(argTypes);
        } catch (NoSuchMethodException e) {
            
            logger.error(e.getMessage(),e);
        } catch (SecurityException e) {
            
            logger.error(e.getMessage(),e);
        } catch (ClassNotFoundException e) {
            logger.error(e.getMessage(),e);
        }
        return BeanUtils.instantiateClass(constructor, args);
    }

    /**
     * 根据对象类对象，参数数组，参数类型列表取得类实例
     * @param clazz 类对象
     * @param args 参数数组
     * @param argTypes 参数类型列表
     * @return 类实例
     */
    public static Object newInstance(Class<?> clazz, Object[] args, Class<?>... argTypes) {
        Constructor<?> constructor = null;
        try {
            constructor = clazz.getConstructor(argTypes);
        } catch (NoSuchMethodException e) {
            
            logger.error(e.getMessage(),e);
        } catch (SecurityException e) {
            
            logger.error(e.getMessage(),e);
        }
        return BeanUtils.instantiateClass(constructor, args);
    }

    /**
     * 根据类名，调用类加载指定的资源文件
     * @param resource 指定资源文件路径
     * @param fromClass 调用类
     * @return 加载到的资源文件
     */
    public static URL loadResource(String resource, Class<?> fromClass) {
        URL url = null;
        if (resource == null)
            return url;
        String resPath = resolveName(resource);
        ClassLoader loader = null;
        //通过调用类的加载器加载资源文件
        if (fromClass != null) {
            url = fromClass.getResource(resource);
            if (url != null)
                return url;
            loader = fromClass.getClassLoader();
            while (loader != null) {
                url = loader.getResource(resPath);
                if (url != null) {
                    return url;
                }
                loader = loader.getParent();
            }
        }
        //如果调用类的加载器加载不到则取默认加载器加载类
        loader = getDefaultClassLoader();
        while (loader != null) {
            url = loader.getResource(resPath);
            if (url != null) {
                return url;
            }
            loader = loader.getParent();
        }
        //最后取系统类加载器加载类
        loader = ClassLoader.getSystemClassLoader();
        url = loader.getResource(resource);
        return url;
    }

    /**
     * 根据类名加载指定的资源文件
     * @param resource 指定资源文件路径
     * @return 加载到的资源文件
     */
    public static URL loadResource(String resource) {
        return loadResource(resource, null);
    }

    /**
     * 根据类名，调用类加载指定的资源文件流
     * @param resource 指定资源文件路径
     * @param fromClass 调用类
     * @return 加载到的资源文件流
     * @throws IOException 
     */
    public static InputStream loadResourceAsStream(String resource, Class<?> fromClass) throws IOException {
        URL url = loadResource(resource, fromClass);
        if (url != null)
            return url.openStream();
        return null;
    }

    /**
     * 根据类名加载指定的资源文件流
     * @param resource 指定资源文件路径
     * @return 加载到的资源文件流
     * @throws IOException 
     */
    public static InputStream loadResourceAsStream(String resource) throws IOException {
        return loadResourceAsStream(resource, null);
    }

    /**
     * Add a package name prefix if the name is not absolute Remove leading "/"
     * if name is absolute
     */
    private static String resolveName(String name) {
        if (name == null) {
            return name;
        }
        if (name.startsWith("/")) {
            name = name.substring(1);
        }
        return name;
    }
}
