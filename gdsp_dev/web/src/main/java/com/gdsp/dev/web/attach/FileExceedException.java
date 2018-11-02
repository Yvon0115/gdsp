package com.gdsp.dev.web.attach;

import org.springframework.web.multipart.MultipartException;

/**
 * 文件超过最大大小限制
 * @author paul.yang
 * @version 1.0 2014年9月24日
 * @since 1.6
 */
public class FileExceedException extends MultipartException {

    /**
     * 序列id
     */
    private static final long serialVersionUID = -2613051165155831152L;
    /**
     * 内容大小
     */
    private long              contentBytes;
    /**
     * 最大限制
     */
    private long              maxLimitBytes;

    /**
     * 构造方法
     * @param throwable 包装的异常 对象
     */
    public FileExceedException(Throwable throwable) {
        super("文件超过最大限制", throwable);
    }

    /**
     * 构造方法
     * @param contentBytes 内容字节数
     * @param maxLimitBytes 最大限制字节数
     * @param throwable 包装的异常 对象
     */
    public FileExceedException(long contentBytes, long maxLimitBytes, Throwable throwable) {
        super("文件超过最大限制", throwable);
        this.contentBytes = contentBytes;
        this.maxLimitBytes = maxLimitBytes;
    }

    /**
     * 取得文件内容大小
     * @return 文件内容大小
     */
    public long getContentBytes() {
        return contentBytes;
    }

    /**
     * 设置文件内容大小
     * @param contentBytes 文件内容大小
     */
    public void setContentBytes(long contentBytes) {
        this.contentBytes = contentBytes;
    }

    /**
     * 取得最大内容限制
     * @return 最大内容限制
     */
    public long getMaxLimitBytes() {
        return maxLimitBytes;
    }

    /**
     * 设置最大内容限制
     * @param maxLimitBytes 最大内容限制
     */
    public void setMaxLimitBytes(long maxLimitBytes) {
        this.maxLimitBytes = maxLimitBytes;
    }
}
