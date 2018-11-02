package com.gdsp.dev.web.attach;

import java.util.HashMap;
import java.util.Map;

/**
 * 进度实体
 * @author paul.yang
 * @version 1.0 2014年9月24日
 * @since 1.6
 */
public class Progress {

    /**
     * 当前读取大小
     */
    private long                readBytes    = 0L;
    /**
     * 内容大小
     */
    private long                contentBytes = 0L;
    /**
     * 顺序号
     */
    private int                 order;
    /**
     * 文件的id
     */
    private String              fileId;
    /**
     * 其他属性
     */
    private Map<String, Object> customParam  = new HashMap<String, Object>();

    public long getReadBytes() {
        return readBytes;
    }

    public void setReadBytes(long readBytes) {
        this.readBytes = readBytes;
    }

    public long getContentBytes() {
        return contentBytes;
    }

    public void setContentBytes(long contentBytes) {
        this.contentBytes = contentBytes;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public Map<String, Object> getCustomParam() {
        return customParam;
    }

    public void setCustomParam(Map<String, Object> customParam) {
        this.customParam = customParam;
    }

    public void setAttribute(String key, Object value) {
        this.customParam.put(key, value);
    }

    public void removeAttribute(String key) {
        this.customParam.remove(key);
    }

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    @Override
    public String toString() {
        return "ProgressEntity [readBytes=" + readBytes + ", contentBytes="
                + contentBytes + ", order=" + order + "]";
    }
}