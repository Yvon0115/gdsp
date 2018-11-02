package com.gdsp.dev.persist.impl;

import org.springframework.transaction.annotation.Transactional;

import com.gdsp.dev.core.cases.service.ICrudService;
import com.gdsp.dev.core.model.entity.IBaseEntity;
import com.gdsp.dev.persist.dao.ICrudDao;

/**
 * 增删改抽象服务
 * @author paul.yang
 * @version 1.0 2014年10月22日
 * @since 1.6
 */
public abstract class CrudService<T extends IBaseEntity> implements ICrudService<T> {

    @Transactional(readOnly = true)
    @Override
    public T load(String id) {
        return getDao().load(id);
    }

    @Transactional
    @Override
    public T insert(T entity) {
        validate(entity);
        getDao().insert(entity);
        return entity;
    }

    @Transactional
    @Override
    public T update(T entity) {
        validate(entity);
        getDao().update(entity);
        return entity;
    }

    @Transactional
    @Override
    public void delete(String... id) {
        getDao().delete(id);
    }

    public void validate(T entity) {}

    public abstract ICrudDao<T> getDao();
}
