package com.gdsp.dev.persist.ext;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.ibatis.executor.ErrorContext;
import org.apache.ibatis.executor.ExecutorException;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.mapping.ParameterMode;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.property.PropertyTokenizer;
import org.apache.ibatis.scripting.xmltags.ForEachSqlNode;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.type.TypeHandler;
import org.apache.ibatis.type.TypeHandlerRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.gdsp.dev.base.exceptions.DevRuntimeException;
import com.gdsp.dev.core.model.param.PageSerImpl;

/**
 * mybatis分页助手
 * @author paul.yang
 * @version 1.0 2014年9月24日
 * @since 1.6
 */
public class PageHelper {

    /**
     * 日志对象
     */
    private static Logger                logger     = LoggerFactory.getLogger(PageHelper.class.getName());
    /**
     * 线程变量
     */
    private static ThreadLocal<PageInfo> pagerLocal = new ThreadLocal<PageInfo>();

    /**
     * 取得分页信息
     * @return 分页信息
     */
    public static PageInfo getPage() {
        PageInfo page = pagerLocal.get();
        pagerLocal.remove();
        return page;
    }

    /**
     * 创建分页信息
     * @param pageable 分页请求
     * @param total 总记录数
     * @return 分页信息
     */
    public static PageInfo newPage(Pageable pageable, long total) {
        pagerLocal.remove();
        PageInfo page = new PageInfo(pageable, total);
        pagerLocal.set(page);
        return page;
    }

    /**
     * 包装分页信息
     * @param data 取得分页数据
     * @return 分页结果
     */
    public static <X> Page<X> packPage(List<X> data) {
        PageInfo pageInfo = getPage();
        Page<X> page = new PageSerImpl<X>(data, pageInfo.getPageable(), pageInfo.getTotal());
        return page;
    }

    /**
     * 包装分页信息,不设置分页结果
     * @return 分页结果
     */
    public static <X> Page<X> packPage() {
        return packPage(new ArrayList<X>());
    }

    /**
     * 根据查询sqlid自动计算记录数
     * @param sqlSession 会话对象
     * @param statementName 查询sqlid
     * @param parameter 参数
     * @return 记录数
     */
    public static int getRowCount(SqlSession sqlSession, String statementName, Object parameter) {
        Map<?, ?> parameterMap = toParameterMap(parameter);
        int count = 0;
        try {
            MappedStatement mst = sqlSession.getConfiguration().getMappedStatement(statementName);
            BoundSql boundSql = mst.getBoundSql(parameterMap);
            String sql = " select count(1) row_count from (" + boundSql.getSql() + ") ";
            PreparedStatement pstmt = sqlSession.getConnection().prepareStatement(sql);
            setParameters(pstmt, mst, boundSql, parameterMap);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                count = rs.getInt("row_count");
            }
            rs.close();
            pstmt.close();
        } catch (Exception e) {
            count = 0;
            throw new DevRuntimeException(e);
        }
        return count;
    }

    /**
     * 设置预编译参数
     * @param ps 预编译声明
     * @param mappedStatement mybatis映射描述
     * @param boundSql mybatis查询sql
     * @param parameter 参数
     * @throws SQLException
     */
    @SuppressWarnings("unchecked")
    public static void setParameters(PreparedStatement ps, MappedStatement mappedStatement,
            BoundSql boundSql, Object parameter) throws SQLException {
        ErrorContext.instance().activity("setting parameters").object(mappedStatement.getParameterMap().getId());
        List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
        if (parameterMappings == null)
            return;
        Configuration configuration = mappedStatement.getConfiguration();
        TypeHandlerRegistry typeHandlerRegistry = configuration.getTypeHandlerRegistry();
        MetaObject metaObject = parameter == null ? null : configuration.newMetaObject(parameter);
        for (int i = 0; i < parameterMappings.size(); i++) {
            ParameterMapping parameterMapping = parameterMappings.get(i);
            if (parameterMapping.getMode() == ParameterMode.OUT)
                continue;
            Object value;
            String propertyName = parameterMapping.getProperty();
            PropertyTokenizer prop = new PropertyTokenizer(propertyName);
            if (parameter == null) {
                value = null;
            } else if (typeHandlerRegistry.hasTypeHandler(parameter.getClass())) {
                value = parameter;
            } else if (boundSql.hasAdditionalParameter(propertyName)) {
                value = boundSql.getAdditionalParameter(propertyName);
            } else if (propertyName.startsWith(ForEachSqlNode.ITEM_PREFIX)
                    && boundSql.hasAdditionalParameter(prop.getName())) {
                value = boundSql.getAdditionalParameter(prop.getName());
                if (value != null) {
                    value = configuration.newMetaObject(value).getValue(propertyName.substring(prop.getName().length()));
                }
            } else {
                value = metaObject == null ? null : metaObject.getValue(propertyName);
            }
            @SuppressWarnings("rawtypes")
            TypeHandler typeHandler = parameterMapping.getTypeHandler();
            if (typeHandler == null) {
                throw new ExecutorException("There was no TypeHandler found for parameter "
                        + propertyName + " of statement " + mappedStatement.getId());
            }
            typeHandler.setParameter(ps, i + 1, value, parameterMapping.getJdbcType());
        }
    }

    /**
     * 将参数转换为map形式
     * @param parameter 原始参数
     * @return map参数
     */
    @SuppressWarnings("rawtypes")
    protected static Map toParameterMap(Object parameter) {
        if (parameter == null) {
            return new HashMap();
        }
        if (parameter instanceof Map) {
            return (Map<?, ?>) parameter;
        } else {
            try {
                return PropertyUtils.describe(parameter);
            } catch (Exception e) {
                logger.error("Convert parameter to map fail!", e);
                return null;
            }
        }
    }

    /**
     * 分页信息
     * @author yangbo
     * @version 1.0 2014-2-20
     * @since 1.6
     */
    public static class PageInfo {

        /**
         * 分页请求
         */
        private Pageable pageable = null;
        /**
         * 记录总数
         */
        private long     total    = 0;

        public PageInfo(Pageable pageable, long total) {
            this.pageable = pageable;
            this.total = total;
        }

        /**
         * 设置分页请求对象
         * @param pageable 分页请求对象
         */
        public void setPageable(Pageable pageable) {
            this.pageable = pageable;
        }

        /**
         * 取得分页请求对象
         * @return 分页请求对象
         */
        public Pageable getPageable() {
            return pageable;
        }

        /**
         * 设置记录总数
         * @param total 记录总数
         */
        public void setTotal(long total) {
            this.total = total;
        }

        /**
         * 取得记录总数
         * @return 记录总数
         */
        public long getTotal() {
            return total;
        }
    }
}