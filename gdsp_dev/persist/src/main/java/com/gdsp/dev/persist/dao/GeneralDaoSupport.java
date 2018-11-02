package com.gdsp.dev.persist.dao;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import com.gdsp.dev.base.exceptions.DevRuntimeException;
import com.gdsp.dev.core.model.param.PageSerRequest;
import com.gdsp.dev.persist.ext.PageHelper;

/**
 * 通用dao接口实现
 * @author paul.yang
 * @version 1.0 2014年9月24日
 * @since 1.6
 */
public class GeneralDaoSupport extends SqlSessionDaoSupport implements GeneralDao {

    /**
     * 日志对象
     */
    private static Logger      logger          = LoggerFactory.getLogger(GeneralDaoSupport.class);
    /**
     * 缺省的查询语句的名称
     */
    public static final String SQL_QUERY       = "query";
    /**
     * 缺省的查询总记录个数语句的名称
     */
    public static final String SQL_QUERY_COUNT = "query_COUNT";
    /**
     * 缺省的插入语句的名称
     */
    public static final String SQL_INSERT      = "insert";
    /**
     * 缺省的更新语句的名称
     */
    public static final String SQL_UPDATE      = "update";
    /**
     * 缺省的删除语句的名称
     */
    public static final String SQL_DELETE      = "delete";

    @Override
    public <X> X query(String namespace, Object parameter) {
        return query(namespace, SQL_QUERY, parameter);
    }

    @Override
    public <X> X query(String namespace, String sqlId, Object parameter) {
        StringBuilder sb = new StringBuilder(namespace);
        sb.append(".").append(sqlId);
        try {
            return getSqlSession().selectOne(sb.toString(), parameter);
        } catch (DataAccessException e) {
            logger.error(e.getMessage(), e);
            throw new DevRuntimeException("查询数据时发生错误,请检查输入是否正确");
        }
    }

    @Override
    public <X> List<X> queryList(String namespace, Object parameter) {
        return queryList(namespace, SQL_QUERY, parameter);
    }

    @Override
    public <X> List<X> queryList(String namespace, String sqlId,
            Object parameter) {
        StringBuilder sb = new StringBuilder(namespace);
        sb.append(".").append(sqlId);
        try {
            return getSqlSession().selectList(sb.toString(), parameter);
        } catch (DataAccessException e) {
            logger.error(e.getMessage(), e);
            throw new DevRuntimeException("查询数据时发生错误,请检查输入是否正确");
        }
    }

    @Override
    public <X> Page<X> queryPage(String namespace, Pageable pageable,
            Object parameter) {
        return queryPage(namespace, pageable, SQL_QUERY, parameter);
    }

    @Override
    public <X> Page<X> queryPage(String namespace, Pageable pageable,
            String sqlId, Object parameter) {
        StringBuilder sb = new StringBuilder(namespace);
        sb.append(".").append(sqlId);
        try {
            return selectPagination(sb.toString(), parameter, pageable, null);
        } catch (DataAccessException e) {
            logger.error(e.getMessage(), e);
            throw new DevRuntimeException("查询数据时发生错误,请检查输入是否正确");
        }
    }

    @Override
    public int insert(String namespace, Object parameter) {
        return insert(namespace, SQL_INSERT, parameter);
    }

    @Override
    public int insert(String namespace, String sqlId, Object parameter) {
        StringBuilder sb = new StringBuilder(namespace);
        sb.append(".").append(sqlId);
        try {
            return getSqlSession().insert(sb.toString(), parameter);
        } catch (DataAccessException e) {
            logger.error(e.getMessage(), e);
            throw new DevRuntimeException("新增数据时发生错误,请检查输入是否正确", e);
        }
    }

    @Override
    public int update(String namespace, Object para) {
        return update(namespace, SQL_UPDATE, para);
    }

    @Override
    public int update(String namespace, String sqlId, Object para) {
        StringBuilder sb = new StringBuilder(namespace);
        sb.append(".").append(sqlId);
        try {
            return getSqlSession().update(sb.toString(), para);
        } catch (DataAccessException e) {
            logger.error(e.getMessage(), e);
            throw new DevRuntimeException("保存数据时发生错误,请检查输入是否正确", e);
        }
    }

    @Override
    public int delete(String namespace, Object parameter) {
        return delete(namespace, SQL_DELETE, parameter);
    }

    @Override
    public int delete(String namespace, String sqlId, Object parameter) {
        StringBuilder sb = new StringBuilder(namespace);
        sb.append(".").append(sqlId);
        try {
            return getSqlSession().delete(sb.toString(), parameter);
        } catch (DataAccessException e) {
            logger.error(e.getMessage(), e);
            throw new DevRuntimeException("删除数据时发生错误,请检查输入是否正确", e);
        }
    }

    /**
     * 查询数据库，返回分页，指定页码的记录集
     * @param queryStatementName 查询定义名
     * @param pageable 分页请求参数
     * @return 分页数据
     */
    public <X> Page<X> selectPagination(String queryStatementName, Pageable pageable) {
        return selectPagination(queryStatementName, null, pageable, null);
    }

    /**
     * 查询数据库，返回分页，指定页码的记录集
     * @param queryStatementName 查询定义名
     * @param parameter 查询参数
     * @param pageable 分页请求参数
     * @param countStatementName 计数查询定义名
     * @return 分页数据
     */
    public <X> Page<X> selectPagination(String queryStatementName, Object parameter,
            Pageable pageable, String countStatementName) {
        if (pageable == null) {
            pageable = new PageSerRequest(0, 25);
        }
        SqlSession sqlSession = getSqlSession();
        int rowCount = 0;
        if (StringUtils.isEmpty(countStatementName)) {
            rowCount = PageHelper.getRowCount(sqlSession, queryStatementName, parameter);
        } else {
            rowCount = (Integer) sqlSession.selectOne(countStatementName, parameter);
        }
        Page<X> page = null;
        if (rowCount == 0) {
            page = new PageImpl<X>(null, pageable, 0);
        } else {
            RowBounds rowBounds = new RowBounds(pageable.getOffset(), pageable.getPageSize());
            List<X> resultList = sqlSession.selectList(queryStatementName, parameter, rowBounds);
            page = new PageImpl<X>(resultList, pageable, rowCount);
        }
        return page;
    }
}
