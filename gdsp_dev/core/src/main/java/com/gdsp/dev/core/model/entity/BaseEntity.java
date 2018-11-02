package com.gdsp.dev.core.model.entity;

import java.io.Serializable;

/**
 * 实体基类
 * @author yaboocn
 * @version 1.0 2014年5月8日
 * @since 1.7
 */
public abstract class BaseEntity implements IBaseEntity, Serializable, Cloneable {

    /**
     * 序列id
     */
    private static final long  serialVersionUID = -3231114188611235386L;
    /*实体属性名常量*/
    /**
     * 实体属性主键id
     */
    public final static String PROP_ID          = "id";
    /**
     * 实体属性来源
     */
    public final static String PROP_ORIGIN      = "origin";
    /**
     * 实体属性版本
     */
    public final static String PROP_VERSION     = "version";
    /**
     * hash码
     */
    private int                hashCode         = Integer.MIN_VALUE;
    /**
     * 实体类必须有的属性
     */
    protected String           id;
    /**
     * 实体类必须有的属性  数据来源
     */
    private String             origin;
    /**
     * 版本信息
     */
    protected Integer          version;
    /**
     * 实体状态
     */
    protected EntityStatus     innerStatus;

    /**
     * 取得实体主键
     * @return 实体主键
     */
    public String getId() {
        return id;
    }

    /**
     * 设置实体主键
     * @param id 实体主键
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 得到来源
     * @return
     */
    public String getOrigin() {
        return origin;
    }

    /**
     * 设置来源
     * @param origin
     */
    public void setOrigin(String origin) {
        this.origin = origin;
    }

    /**
     * 取得版本信息
     * @return 版本信息
     */
    public Integer getVersion() {
        return version;
    }

    /**
     * 设置版本信息
     * @param version
     */
    public void setVersion(Integer version) {
        this.version = version;
    }

    /**
     * 取得实体状态
     * @return 实体状态
     */
    public EntityStatus getInnerStatus() {
        return innerStatus;
    }

    /**
     * 设置实体状态
     * @param status 实体状态
     */
    public void setInnerStatus(EntityStatus innerStatus) {
        this.innerStatus = innerStatus;
    }

    /**
     * 设置实体状态
     * @param status 实体状态
     */
    public void setStrInnerStatus(String status) {
        if (status == null)
            return;
        setInnerStatus(EntityStatus.valueOf(status.toUpperCase()));
    }

    /* (non-Javadoc)
     * @see Object#equals(Object)
     */
    public boolean equals(Object obj) {
        if (null == obj)
            return false;
        if (!(obj instanceof BaseEntity))
            return false;
        else {
            BaseEntity entity = (BaseEntity) obj;
            if (null == this.getId() || null == entity.getId()) {
                return this == entity;
            } else {
                return (this.getId().equals(entity.getId()));
            }
        }
    }

    /* (non-Javadoc)
     * @see Object#hashCode()
     */
    public int hashCode() {
        if (Integer.MIN_VALUE == this.hashCode) {
            if (null == this.getId())
                return super.hashCode();
            else {
                String hashStr = this.getClass().getName() + ":" + this.getId().hashCode();
                this.hashCode = hashStr.hashCode();
            }
        }
        return this.hashCode;
    }

    /* (non-Javadoc)
     * @see Comparable#compareTo(Object)
     */
    public int compareTo(IBaseEntity obj) {
        if (obj.hashCode() > hashCode())
            return 1;
        else if (obj.hashCode() < hashCode())
            return -1;
        else
            return 0;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#clone()
     */
    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
