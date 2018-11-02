package com.gdsp.platform.common.web;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.ShiroException;
import org.apache.shiro.authz.UnauthenticatedException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.gdsp.dev.base.exceptions.BusinessException;

/**
 * 异常控制类
 * @author paul.yang
 * @version 1.0 2014年11月21日
 * @since 1.6
 */
@ControllerAdvice
public class ExceptionController {

    /**
     * 用于处理异常的
     * @return  系统异常
     */
    @ExceptionHandler({ UnauthenticatedException.class })
    @ResponseStatus(HttpStatus.EXPECTATION_FAILED)
    public String unauthorizedException(Exception e, HttpServletRequest request) {
        request.setAttribute("exception", e);
        return "forward:/unauthorized.d";
    }

    /**
     * 用于处理异常的
     * @return 业务异常
     */
    @ExceptionHandler({ ShiroException.class })
    @ResponseStatus(HttpStatus.EXPECTATION_FAILED)
    public String shiroException() {
        return "redirect:/logout.d";
    }

    /**  
     * 用于处理异常的  
     * @return 业务异常
     */
    @ExceptionHandler({ BusinessException.class })
    @ResponseStatus(HttpStatus.EXPECTATION_FAILED)
    public String businessException(BusinessException e, HttpServletRequest request) {
        request.setAttribute("exception", e);
        return "forward:/business_exception.d";
    }

    /**
     * 用于处理异常的
     * @return  系统异常
     */
    @ExceptionHandler({ RuntimeException.class, Exception.class })
    @ResponseStatus(HttpStatus.EXPECTATION_FAILED)
    public String systemException(Exception e, HttpServletRequest request) {
        request.setAttribute("exception", e);
        return "forward:/system_exception.d";
    }
}
