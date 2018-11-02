package com.gdsp.dev.core.model.entity;

/**
 * 基础实体接口
 * @author paul.yang
 * @version 1.0 2014年9月24日
 * @since 1.6
 */
public interface IBaseEntity extends Comparable<IBaseEntity> {

    /**
     * 取得实体主键
     * @return 实体主键
     */
    public String getId();

    /**
     * 设置实体主键
     * @param id 实体主键
     */
    public void setId(String id);

    /**
     * 取得版本信息
     * @return 版本信息
     */
    public Integer getVersion();

    /**
     * 设置版本信息
     * @param version
     */
    public void setVersion(Integer version);

    /**
     * 取得实体状态
     * @return 实体状态
     */
    public EntityStatus getInnerStatus();

    /**
     * 设置实体状态
     * @param status 实体状态
     */
    public void setInnerStatus(EntityStatus status);
}
