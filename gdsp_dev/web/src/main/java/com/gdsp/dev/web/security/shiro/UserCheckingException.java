package com.gdsp.dev.web.security.shiro;

import org.apache.shiro.authc.AuthenticationException;

/**
 * 用户审核中异常类
 * @author paul.yang
 * @version 1.0 14-12-20
 * @since 1.6
 */
public class UserCheckingException extends AuthenticationException {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public UserCheckingException() {}

    public UserCheckingException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserCheckingException(String message) {
        super(message);
    }

    public UserCheckingException(Throwable cause) {
        super(cause);
    }
}
