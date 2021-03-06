package com.gdsp.dev.base.exceptions;

/**
 * 平台扩展异常
 * @author paul.yang
 * @version 1.0 2014年9月24日
 * @since 1.6
 */
public class DevException extends Exception {

    /**
     * 序列id
     */
    private static final long serialVersionUID = 745610190989691756L;
    /**
     * 异常编码
     */
    private int               code             = -1;

    /**
     * 无参数构造方法
     */
    public DevException() {
        super();
    }

    /**
     * 通过信息串构造异常
     * @param message 信息串
     */
    public DevException(String message) {
        super(message);
    }

    /**
     * 通过可抛出对象构造异常
     * @param t 可抛出对象
     */
    public DevException(Throwable t) {
        super(t);
    }

    /**
     * 通过消息和可抛出对象构造异常
     * @param message 信息串
     * @param t 可抛出对象
     */
    public DevException(String message, Throwable t) {
        super(message, t);
    }

    /**
     * 通过编码构造异常
     * @param code    错误编码
     */
    public DevException(int code) {
        this();
        this.code = code;
    }

    /**
     * 通过错误编码信息串构造异常
     * @param code    错误编码
     * @param message 信息串
     */
    public DevException(int code, String message) {
        this(message);
        this.code = code;
    }

    /**
     * 通过错误编码可抛出对象构造异常
     * @param code 错误编码
     * @param t 可抛出对象
     */
    public DevException(int code, Throwable t) {
        this(t);
        this.code = code;
    }

    /**
     * 通过错误编码、消息和可抛出对象构造异常
     * @param code    错误编码
     * @param message 信息串
     * @param t 可抛出对象
     */
    public DevException(int code, String message, Throwable t) {
        this(message, t);
        this.code = code;
    }

    /**
     * 取得异常编码
     * @return 异常编码
     */
    public int getCode() {
        return code;
    }
}
