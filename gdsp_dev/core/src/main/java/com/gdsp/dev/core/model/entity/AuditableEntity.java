package com.gdsp.dev.core.model.entity;

import com.gdsp.dev.base.lang.DDateTime;

/**
 * 审计信息父类
 * @author paul.yang
 * @version 1.0 2014年9月24日
 * @since 1.6
 */
public abstract class AuditableEntity extends BaseEntity implements IAuditableEntity {

    /**
     * 序列id
     */
    private static final long  serialVersionUID      = -6039076670318997447L;
    /*实体属性名常量*/
    /**
     * 实体属性创建时间
     */
    public final static String PROP_CREATE_TIME      = "createTime";
    /**
     * 实体属性创建人
     */
    public final static String PROP_CREATE_BY        = "createBy";
    /**
     * 实体属性最后一次修改时间
     */
    public final static String PROP_LAST_MODIFY_TIME = "lastModifyTime";
    /**
     * 实体属性最后一次修改人
     */
    public final static String PROP_LAST_MODIFY_BY   = "lastModifyBy";
    /**
     * 创建时间
     */
    protected DDateTime        createTime;
    /**
     * 创建人
     */
    protected String           createBy;
    /**
     * 最后一次修改时间
     */
    protected DDateTime        lastModifyTime;
    /**
     * 最后一下修改人
     */
    protected String           lastModifyBy;

    /**
     * 取得创建时间
     * @return 创建时间
     */
    public DDateTime getCreateTime() {
        return createTime;
    }

    /**
     * 设置创建时间,记录插入时有效
     * @param createTime 创建时间
     */
    public void setCreateTime(DDateTime createTime) {
        this.createTime = createTime;
    }

    /**
     * 取得创建实体的操作员的登录名
     * @return 操作员标识
     */
    public String getCreateBy() {
        return createBy;
    }

    /**
     * 设置创建实体的操作员标识，记录插入时有效
     * @param createBy 操作员标识
     */
    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    /**
     * 取得最后修改时间.
     * @return 最后修改时间
     */
    public DDateTime getLastModifyTime() {
        return lastModifyTime;
    }

    /**
     * 设置最后修改时间.记录更新时有效
     * @param lastModifyTime 最后修改时间
     */
    public void setLastModifyTime(DDateTime lastModifyTime) {
        this.lastModifyTime = lastModifyTime;
    }

    /**
     * 取得最后修改的操作员的登录名.
     * @return 操作员的登录名.
     */
    public String getLastModifyBy() {
        return lastModifyBy;
    }

    /**
     * 设置最后修改的操作员的登录名,记录更新时有效
     * @param lastModifyBy 操作员的登录名.
     */
    public void setLastModifyBy(String lastModifyBy) {
        this.lastModifyBy = lastModifyBy;
    }
}
