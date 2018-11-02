package com.gdsp.dev.core.cases.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.gdsp.dev.base.exceptions.DevException;
import com.gdsp.dev.core.model.query.Condition;

/**
 * 增删改查的基础服务接口
 * @author paul.yang
 * @version 1.0 2014年9月24日
 * @since 1.6
 */
public interface IQueryService<T> {

    /**
     * 载入所有的实体对象
     * @return 载入所有的实体对象
     * @throws DevException
     */
    public List<T> findAll() throws DevException;

    /**
     * 载入指定页的实体对象
     * @param pageable 指定的页信息
     * @return 实体对象集合页
     * @throws DevException
     */
    public Page<T> findPage(Pageable pageable) throws DevException;

    /**
     * 根据条件表达式集查询数据列表
     * @param conditons 条件表达式集
     * @return 加载后的数据列表
     * @throws DevException
     */
    public List<T> findListByCondition(Condition condition) throws DevException;

    /**
     * 根据条件表达式集查询分页数据
     * @param condition 条件表达式集
     * @param page 分页数据
     * @return 加载后的分页数据
     * @throws DevException
     */
    public Page<T> findPageByCondition(Condition condition, Pageable page) throws DevException;

    /**
     * 根据id数组载入对象列表
     * @param ids 对象id数组
     * @return 对象列表
     */
    public List<T> findListByIds(String[] ids) throws DevException;
}
