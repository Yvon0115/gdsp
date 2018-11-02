package com.gdsp.platform.files.model;

import com.gdsp.dev.base.utils.web.MIMETypeUtils;
import com.gdsp.dev.core.model.entity.AuditableEntity;

/**
 * 文件信息实体
 *
 * @author paul.yang
 * @version 1.0 2015-10-15
 * @since 1.6
 */
public class FileInfo extends AuditableEntity {

    /**
     * 序列id
     */
    private static final long serialVersionUID = 1276145346414682522L;
    /**
     * 文件名
     */
    private String            name;
    /**
     * 文件路径
     */
    private String            path;
    /**
     * 文件大小
     */
    private long              size;
    /**
     * 访问的相对url
     */
    private String            url;
    /**
     * 相关业务
     */
    private String            business;
    /**
     * 是否为临时文件
     */
    private boolean           temp;

    /**
     * 取得文件名，带扩展类型
     *
     * @return 文件名
     */
    public String getName() {
        return name;
    }

    /**
     * 文件名，带扩展类型
     *
     * @param name 文件名
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 取得文件所在位置
     * @return 文件所在位置
     */
    public String getLocation() {
        return path.substring(0, path.length() - getName().length() - 1);
    }

    /**
     * 取得文件全路径
     *
     * @return 文件全路径
     */
    public String getPath() {
        return path;
    }

    /**
     * 设置文件路径
     * @param path 文件路径
     */
    public void setPath(String path) {
        this.path = path;
    }

    /**
     * 取得文件大小
     *
     * @return 文件到校
     */
    public long getSize() {
        return size;
    }

    /**
     * 设置文件大小
     *
     * @param size 文件大小
     */
    public void setSize(long size) {
        this.size = size;
    }

    /**
     * 设置文件类型MIME类型
     *
     * @return 文件类型MIME类型
     */
    public String getType() {
        return MIMETypeUtils.getContentType(getName());
    }

    /**
     * 取得文件扩展名
     * @return 文件扩展名
     */
    public String getExtension() {
        String name = getName();
        int lastIndex = name.lastIndexOf(".");
        if (lastIndex > 0) {
            return name.substring(lastIndex + 1);
        }
        return null;
    }
}
