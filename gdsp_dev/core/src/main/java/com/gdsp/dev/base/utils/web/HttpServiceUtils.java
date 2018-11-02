package com.gdsp.dev.base.utils.web;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.zip.GZIPOutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * HTTP传输常用方法
 * @author paul.yang
 * @version 1.0 2014年9月24日
 * @since 1.6
 */
public class HttpServiceUtils {

    /**
     * 需要被Gzip压缩的Mime类型. 
     */
    private static final String[] GZIP_MIME_TYPES  = { "text/html", "text/xml", "text/plain", "text/css",
            "text/javascript", "application/xml", "application/xhtml+xml", "application/x-javascript" };
    /**
     * 需要被Gzip压缩的最小文件大小. 
     */
    private static final int      GZIP_MINI_LENGTH = 512;

    /**
     * 检查浏览器客户端是否支持gzip编码.
     * @param request http请求
     * @return 是否支持GZip编码
     */
    public static boolean checkAccetptGzip(HttpServletRequest request) {
        //Http1.1 header
        String acceptEncoding = request.getHeader("Accept-Encoding");
        return StringUtils.contains(acceptEncoding, "gzip");
    }

    /**
     * 构建HTTP响应的GZip输出流
     * @param response HTTP响应
     * @return Zip输出流
     * @throws IOException
     */
    public static OutputStream buildGzipOutputStream(HttpServletResponse response) throws IOException {
        response.setHeader("Content-Encoding", "gzip");
        response.setHeader("Vary", "Accept-Encoding");
        return new GZIPOutputStream(response.getOutputStream());
    }

    /**
     * 判断文件是否需要GZIP输出
     * @param file 文件对象
     * @return 布尔值
     */
    public static boolean isNeedGzip(File file) {
        return isNeedGzip(file.getName(), file.length());
    }

    /**
     * 判断文件是否需要GZIP输出
     * @param file 文件对象
     * @return 布尔值
     */
    public static boolean isNeedGzip(String fileName, long fileLength) {
        if (fileLength >= GZIP_MINI_LENGTH && ArrayUtils.contains(GZIP_MIME_TYPES, MIMETypeUtils.getContentType(fileName))) {
            return true;
        } else {
            return false;
        }
    }
}
