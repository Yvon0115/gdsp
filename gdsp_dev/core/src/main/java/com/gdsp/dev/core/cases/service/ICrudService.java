package com.gdsp.dev.core.cases.service;

import com.gdsp.dev.core.model.entity.IBaseEntity;

/**
 * 增删改查的基础服务接口
 * @author paul.yang
 * @version 1.0 2014年9月24日
 * @since 1.6
 */
public interface ICrudService<T extends IBaseEntity> {

    /**
     * 根据对象id载入对象
     * @param id 对象id
     * @return 对象
     */
    T load(String id);

    /**
     * 插入指定的实体对象
     * @param entity 实体对象
     * @return 插入后实体对象
     */
    T insert(T entity);

    /**
     * 更新指定的实体对象
     * @param entity 实体对象
     * @return 更新后
     */
    T update(T entity);

    /**
     * 根据id删除指定的实体对象
     * @param id 实体对象id
     */
    void delete(String... id);
}
