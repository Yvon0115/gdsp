package com.gdsp.dev.web.utils;

import javax.servlet.http.HttpServletRequest;

/**
 * http工具类
 *
 * @author Paul.Yang E-mail:yaboocn@qq.com
 * @version 1.0 2015-12-25
 * @since 1.7
 */
public class HttpUtils {

    /**
     * 判断请求是否是include的
     * @param request 请求对象
     * @return 布尔值
     */
    public static boolean isInclude(HttpServletRequest request) {
        return request.getAttribute("javax.servlet.include.servlet_path") != null;
    }
}
