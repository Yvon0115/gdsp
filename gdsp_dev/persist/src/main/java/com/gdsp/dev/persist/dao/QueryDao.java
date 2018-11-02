package com.gdsp.dev.persist.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.gdsp.dev.core.model.query.Condition;

/**
 * 基于mybatis，增删改查基础接口
 * @author paul.yang
 * @version 1.0 2014年9月24日
 * @since 1.6
 */
public interface QueryDao<T> {

    /**
     * 载入所有的实体对象
     * @return 载入所有的实体对象
     * @ 
     */
    List<T> findAll();

    /**
     * 载入指定页的实体对象
     * @param pageable 指定的页信息
     * @return 实体对象集合页
     * @ 
     */
    Page<T> findPage(Pageable pageable);

    /**
     * 根据条件表达式集查询数据列表
     * @param condition    条件表达式集
     * @return 加载后的数据列表
     * @ 
     */
    List<T> findListByCondition(@Param("cond") Condition condition);

    /**
     * 根据条件表达式集查询分页数据
     * @param condition 条件表达式集
     * @param page 分页数据
     * @return 加载后的分页数据
     * @ 
     */
    Page<T> findPageByCondition(@Param("cond") Condition condition, Pageable page);

    /**
     * 根据id数组载入对象列表
     * @param ids 对象id数组
     * @return 对象列表
     */
    List<T> findListByIds(String[] ids);
}
