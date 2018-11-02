/*
 * Copyright(c) FastSpace Software 2014. All Rights Reserved.
 */
package com.gdsp.dev.core.model.entity;

import com.gdsp.dev.base.lang.DDateTime;

/**
 * 审计信息类接口
 * @author yaboocn
 * @version 1.0 2014年5月8日
 * @since 1.7
 */
public interface IAuditableEntity extends IBaseEntity {

    /**
     * 取得创建时间
     * @return 创建时间
     */
    public DDateTime getCreateTime();

    /**
     * 设置创建时间,记录插入时有效
     * @param createTime 创建时间
     */
    public void setCreateTime(DDateTime createTime);

    /**
     * 取得创建实体的操作员的登录名
     * @return 操作员标识
     */
    public String getCreateBy();

    /**
     * 设置创建实体的操作员标识，记录插入时有效
     * @param createBy 操作员标识
     */
    public void setCreateBy(String createBy);

    /**
     * 取得最后修改时间.
     * @return 最后修改时间
     */
    public DDateTime getLastModifyTime();

    /**
     * 设置最后修改时间.记录更新时有效
     * @param lastModifyTime 最后修改时间
     */
    public void setLastModifyTime(DDateTime lastModifyTime);

    /**
     * 取得最后修改的操作员的登录名.
     * @return 操作员的登录名.
     */
    public String getLastModifyBy();

    /**
     * 设置最后修改的操作员的登录名,记录更新时有效
     * @param lastModifyBy 操作员的登录名.
     */
    public void setLastModifyBy(String lastModifyBy);
}
