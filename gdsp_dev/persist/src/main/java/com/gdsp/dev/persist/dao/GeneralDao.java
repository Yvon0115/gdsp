package com.gdsp.dev.persist.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.gdsp.dev.base.exceptions.BusinessException;

/**
 * 通用dao接口
 * @author paul.yang
 * @version 1.0 2014年9月24日
 * @since 1.6
 */
public interface GeneralDao {

    /**
     * 使用默认查询单实体对象
     * @param namespace mybatis命名空间
     * @param parameter 查询参数
     * @return 实体对象
     * @throws BusinessException
     */
    public <X> X query(String namespace, Object parameter) throws BusinessException;

    /**
     * 使用指定查询定义,查询单实体对象
     * @param namespace mybatis命名空间
     * @param sqlId 查询id
     * @param parameter 查询参数
     * @return 实体对象
     * @throws BusinessException
     */
    public <X> X query(String namespace, String sqlId, Object parameter);

    /**
     * 使用默认查询定义,查询实体列表
     * @param namespace mybatis命名空间
     * @param parameter 查询参数
     * @return 实体列表
     * @throws BusinessException
     */
    public <X> List<X> queryList(String namespace, Object parameter);

    /**
     * 使用指定查询定义,查询实体列表
     * @param namespace mybatis命名空间
     * @param sqlId 查询id
     * @param parameter 查询参数
     * @return 实体列表
     * @throws BusinessException
     */
    public <X> List<X> queryList(String namespace, String sqlId, Object parameter);

    /**
     * 使用默认查询定义,查询分页实体数据
     * @param namespace mybatis命名空间
     * @param pageable 分页请求
     * @param parameter 查询参数
     * @return 分页实体数据
     * @throws BusinessException
     */
    public <X> Page<X> queryPage(String namespace, Pageable pageable, Object parameter);

    /**
     * 使用指定查询定义,查询分页实体数据
     * @param namespace mybatis命名空间
     * @param pageable 分页请求
     * @param sqlId 查询id
     * @param parameter 查询参数
     * @return 分页实体数据
     * @throws BusinessException
     */
    public <X> Page<X> queryPage(String namespace, Pageable pageable, String sqlId, Object parameter);

    /**
     * 使用默认插入定义,插入实体数据
     * @param namespace mybatis命名空间
     * @param parameter 插入参数
     * @return 执行成功记录数
     * @throws BusinessException
     */
    public int insert(String namespace, Object parameter);

    /**
     * 使用指定插入定义,插入实体数据
     * @param namespace mybatis命名空间
     * @param sqlId 插入id
     * @param parameter 插入参数
     * @return 执行成功记录数
     * @throws BusinessException
     */
    public int insert(String namespace, String sqlId, Object parameter);

    /**
     * 使用默认更新定义,更新实体数据
     * @param namespace mybatis命名空间
     * @param parameter 参数
     * @return 执行成功记录数
     * @throws BusinessException
     */
    public int update(String namespace, Object parameter);

    /**
     * 使用指定更新定义,更新实体数据
     * @param namespace mybatis命名空间
     * @param sqlId 更新id
     * @param parameter 参数
     * @return 执行成功记录数
     * @throws BusinessException
     */
    public int update(String namespace, String sqlId, Object parameter);

    /**
     * 使用默认删除定义,删除实体数据
     * @param namespace mybatis命名空间
     * @param parameter 参数
     * @return 执行成功记录数
     * @throws BusinessException
     */
    public int delete(String namespace, Object parameter);

    /**
     * 使用指定删除定义,删除实体数据
     * @param namespace mybatis命名空间
     * @param sqlId 删除id
     * @param parameter 参数
     * @return 执行成功记录数
     * @throws BusinessException
     */
    public int delete(String namespace, String sqlId, Object parameter);
}
