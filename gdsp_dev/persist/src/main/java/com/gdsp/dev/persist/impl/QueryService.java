package com.gdsp.dev.persist.impl;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.gdsp.dev.core.cases.service.IQueryService;
import com.gdsp.dev.core.model.query.Condition;
import com.gdsp.dev.persist.dao.QueryDao;

/**
 * 查询服务抽象类
 * @author paul.yang
 * @version 1.0 2014年10月23日
 * @since 1.6
 */
public abstract class QueryService<T> implements IQueryService<T> {

    @Override
    public List<T> findAll() {
        return getDao().findAll();
    }

    @Override
    public Page<T> findPage(Pageable pageable) {
        return getDao().findPage(pageable);
    }

    @Override
    public List<T> findListByCondition(Condition condition) {
        return getDao().findListByCondition(condition);
    }

    @Override
    public Page<T> findPageByCondition(Condition condition, Pageable page) {
        return getDao().findPageByCondition(condition, page);
    }

    @Override
    public List<T> findListByIds(String[] ids) {
        return getDao().findListByIds(ids);
    }

    protected abstract QueryDao<T> getDao();
}
