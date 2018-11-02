package com.gdsp.dev.base.exceptions;

/**
 * 消息异常，需显示的异常
 * @author paul.yang
 * @version 1.0 2014年9月24日
 * @since 1.6
 */
public class MessageException extends DevRuntimeException {

    /**
     * 序列id
     */
    private static final long serialVersionUID = 7716625105510460070L;

    /**
     * 无参数构造方法
     */
    public MessageException() {
        super();
    }

    /**
     * 通过信息串构造异常
     * @param message 信息串
     */
    public MessageException(String message) {
        super(message);
    }

    /**
     * 通过可抛出对象构造异常
     * @param t 可抛出对象
     */
    public MessageException(Throwable t) {
        super(t);
    }

    /**
     * 通过消息和可抛出对象构造异常
     * @param message 信息串
     * @param t 可抛出对象
     */
    public MessageException(String message, Throwable t) {
        super(message, t);
    }

    /**
     * 通过编码构造异常
     * @param code    错误编码
     */
    public MessageException(int code) {
        super(code);
    }

    /**
     * 通过错误编码信息串构造异常
     * @param code    错误编码
     * @param message 信息串
     */
    public MessageException(int code, String message) {
        super(code, message);
    }

    /**
     * 通过错误编码可抛出对象构造异常
     * @param code 错误编码
     * @param t 可抛出对象
     */
    public MessageException(int code, Throwable t) {
        super(code, t);
    }

    /**
     * 通过错误编码、消息和可抛出对象构造异常
     * @param code    错误编码
     * @param message 信息串
     * @param t 可抛出对象
     */
    public MessageException(int code, String message, Throwable t) {
        super(code, message, t);
    }
}
