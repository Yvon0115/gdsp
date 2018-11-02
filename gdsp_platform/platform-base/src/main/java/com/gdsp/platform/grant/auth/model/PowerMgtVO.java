package com.gdsp.platform.grant.auth.model;

import com.gdsp.dev.base.lang.DDateTime;
import com.gdsp.dev.core.model.entity.BaseEntity;

/**
 * 权限分配表
 * @author wwb 
 * @version 1.0 2015/6/15
 */
public class PowerMgtVO extends BaseEntity {

    private static final long serialVersionUID = 1L;
    private String            pk_role;              // 角色
    private String            resource_id;          // 资源标识
    private String            tableName;            // 资源表名
    protected DDateTime       createTime;           //创建时间
    protected String          createBy;             //创建人

    public String getPk_role() {
        return pk_role;
    }

    public void setPk_role(String pk_role) {
        this.pk_role = pk_role;
    }

    public String getResource_id() {
        return resource_id;
    }

    public void setResource_id(String resource_id) {
        this.resource_id = resource_id;
    }

    public DDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(DDateTime createTime) {
        this.createTime = createTime;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }
}
