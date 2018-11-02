package com.gdsp.dev.base.utils.web;

import java.io.File;

import javax.activation.MimetypesFileTypeMap;

/**
 * MIME类型工具类
 * @author paul.yang
 * @version 1.0 2014年9月24日
 * @since 1.6
 */
public final class MIMETypeUtils {

    /**
     * MIME类型映射
     */
    private static MimetypesFileTypeMap mimetypesFileTypeMap = new MimetypesFileTypeMap();
    static {
        mimetypesFileTypeMap.addMimeTypes("text/css css");
        mimetypesFileTypeMap.addMimeTypes("text/javascript js");
        mimetypesFileTypeMap.addMimeTypes("text/xml xml");
    }

    /**
     * 添加MIME类型
     * @param mimeTypes MIME类型串（如text/html html）
     */
    public static void addMimeTypes(String mimeTypes) {
        mimetypesFileTypeMap.addMimeTypes(mimeTypes);
    }

    /**
     * 取得文件名的内容类型
     * @param fileName 文件名
     * @return 内容类型
     */
    public static String getContentType(String fileName) {
        return mimetypesFileTypeMap.getContentType(fileName);
    }

    /**
     * 取得文件的内容类型
     * @param file 文件
     * @return 内容类型
     */
    public static String getContentType(File file) {
        return mimetypesFileTypeMap.getContentType(file);
    }
}
