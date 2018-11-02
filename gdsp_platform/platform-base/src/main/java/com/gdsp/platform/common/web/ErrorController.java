package com.gdsp.platform.common.web;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * 异常控制类
 * @author paul.yang
 * @version 1.0 2014年11月21日
 * @since 1.6
 */
@Controller
public class ErrorController {

    /**  
     * 用于处理异常的  
     * @return  系统异常
     */
    @RequestMapping("404.d")
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String noPage() {
        return "exception/404";
    }

    /**  
     * 用于处理异常的  
     * @return  系统异常
     */
    @RequestMapping("500.d")
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String systemError() {
        return "exception/500";
    }

    /**  
     * 用于处理异常的  
     * @return  系统异常
     */
    @RequestMapping("unauthorized.d")
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public String unauthorized() {
        return "exception/unauthorized";
    }

    /**
     * 用于处理异常的
     * @return 业务异常
     */
    @RequestMapping("business_exception.d")
    @ResponseStatus(HttpStatus.EXPECTATION_FAILED)
    public String businessException() {
        return "exception/business";
    }

    /**
     * 用于处理异常的
     * @return  系统异常
     */
    @RequestMapping("system_exception.d")
    @ResponseStatus(HttpStatus.EXPECTATION_FAILED)
    public String systemException() {
        return "exception/system";
    }

    /**
     * 用于处理异常的
     * @return  系统异常
     */
    @RequestMapping("empty.d")
    public String emptyPage() {
        return "exception/empty";
    }
}
