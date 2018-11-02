package com.gdsp.dev.base.exceptions;

/**
 * 业务异常
 * @author paul.yang
 * @version 1.0 2014年9月24日
 * @since 1.6
 */
public class BusinessException extends MessageException {

    /**
     * 序列id
     */
    private static final long serialVersionUID = 4000962758575611879L;

    /**
     * 无参数构造方法
     */
    public BusinessException() {
        super();
    }

    /**
     * 通过信息串构造异常
     * @param message 信息串
     */
    public BusinessException(String message) {
        super(message);
    }

    /**
     * 通过可抛出对象构造异常
     * @param t 可抛出对象
     */
    public BusinessException(Throwable t) {
        super(t);
    }

    /**
     * 通过消息和可抛出对象构造异常
     * @param message 信息串
     * @param t 可抛出对象
     */
    public BusinessException(String message, Throwable t) {
        super(message, t);
    }

    /**
     * 通过编码构造异常
     * @param code    错误编码
     */
    public BusinessException(int code) {
        super(code);
    }

    /**
     * 通过错误编码信息串构造异常
     * @param code    错误编码
     * @param message 信息串
     */
    public BusinessException(int code, String message) {
        super(code, message);
    }

    /**
     * 通过错误编码可抛出对象构造异常
     * @param code 错误编码
     * @param t 可抛出对象
     */
    public BusinessException(int code, Throwable t) {
        super(code, t);
    }

    /**
     * 通过错误编码、消息和可抛出对象构造异常
     * @param code    错误编码
     * @param message 信息串
     * @param t 可抛出对象
     */
    public BusinessException(int code, String message, Throwable t) {
        super(code, message, t);
    }
}
