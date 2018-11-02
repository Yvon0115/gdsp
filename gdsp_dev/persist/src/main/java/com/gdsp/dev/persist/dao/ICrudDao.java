package com.gdsp.dev.persist.dao;

/**
 * 基于mybatis，增删改查基础接口
 * @author paul.yang
 * @version 1.0 2014年9月24日
 * @since 1.6
 */
public interface ICrudDao<T> {

    /**
     * 新增业务实体
     * @param entity 实体对象
     */
    int insert(T entity);

    /**
     * 更新业务实体
     * @param entity 实体
     */
    int update(T entity);

    /**
     * 根据业务实体id加载业务实体
     * @param id 业务实体id
     * @return 业务实体对象
     */
    T load(String id);

    /**
     * 根据id数组删除指定的实体对象
     * @param ids 实体对象id数组
     */
    int delete(String[] ids);
}
