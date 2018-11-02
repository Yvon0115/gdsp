package com.gdsp.dev.persist.ext;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Map;
import java.util.Properties;

import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.logging.Log;
import org.apache.ibatis.logging.LogFactory;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.apache.ibatis.reflection.factory.ObjectFactory;
import org.apache.ibatis.reflection.wrapper.ObjectWrapperFactory;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.RowBounds;
import org.springframework.data.domain.Pageable;

import com.gdsp.dev.core.data.jdbc.DialectHelper;
import com.gdsp.dev.core.model.param.Limit;

/**
 * mybatis的分页拦截器
 * @author paul.yang
 * @version 1.0 2014年9月24日
 * @since 1.6
 */
@Intercepts({ @Signature(type = StatementHandler.class, method = "prepare", args = { Connection.class }) })
public class ParameterInterceptor implements Interceptor {

    /**
     * 日志对象
     */
    private final static Log                  logger                         = LogFactory.getLog(ParameterInterceptor.class);
    /**
     * 对象构造工厂
     */
    private static final ObjectFactory        DEFAULT_OBJECT_FACTORY         = new MybatisObjectFactory();
    /**
     * 对象包装工厂
     */
    private static final ObjectWrapperFactory DEFAULT_OBJECT_WRAPPER_FACTORY = new MybatisObjectWrapperFactory();
    /**
     * 默认需要拦截的id(正则匹配)
     */
    private static String                     defaultPagePattern             = ".*Page$";
    /**
     * 需要拦截的id(正则匹配)
     */
    private static String                     pagePattern                    = null;
    /**
     * 配置对象
     */
    private Configuration                     configuration                  = null;

    /* (non-Javadoc)
     * @see org.apache.ibatis.plugin.Interceptor#intercept(org.apache.ibatis.plugin.Invocation)
     */
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        StatementHandler statementHandler = (StatementHandler) invocation.getTarget();
        MetaObject metaObject = MetaObject.forObject(statementHandler, DEFAULT_OBJECT_FACTORY,
                DEFAULT_OBJECT_WRAPPER_FACTORY);
        // 分离代理对象链(由于目标类可能被多个拦截器拦截，从而形成多次代理，通过下面的两次循环可以分离出最原始的的目标类)
        while (metaObject.hasGetter("h")) {
            Object object = metaObject.getValue("h");
            metaObject = MetaObject.forObject(object, DEFAULT_OBJECT_FACTORY, DEFAULT_OBJECT_WRAPPER_FACTORY);
        }
        // 分离最后一个代理对象的目标类
        while (metaObject.hasGetter("target")) {
            Object object = metaObject.getValue("target");
            metaObject = MetaObject.forObject(object, DEFAULT_OBJECT_FACTORY, DEFAULT_OBJECT_WRAPPER_FACTORY);
        }
        MappedStatement mappedStatement = (MappedStatement) metaObject.getValue("delegate.mappedStatement");
        if (mappedStatement.getId().endsWith("$count"))
            return invocation.proceed();
        if (configuration == null) {
            configuration = (Configuration) metaObject.getValue("delegate.configuration");
        }
        BoundSql boundSql = (BoundSql) metaObject.getValue("delegate.boundSql");
        Object parameterObj = boundSql.getParameterObject();
        String originSql = boundSql.getSql();
        Object paramObj = findNeedHandleParameter(parameterObj);
        String sql = null;
        if (paramObj != null) {
            if (paramObj instanceof Pageable) {
                String countStatementId = mappedStatement.getId() + "$count";
                Pageable pageable = (Pageable) paramObj;
                if (configuration.hasStatement(countStatementId, true)) {
                    int total = SqlTemplateHelper.getSqlSession().selectOne(countStatementId, parameterObj);
                    PageHelper.newPage(pageable, total);
                } else {
                    Connection connection = (Connection) invocation.getArgs()[0];
                    String countSql = "select count(1) from (" + originSql + ") tmp_count"; // 记录统计
                    PreparedStatement countStmt = connection.prepareStatement(countSql);
                    BoundSql countBS = new BoundSql(configuration, countSql, boundSql.getParameterMappings(), parameterObj);
                    MetaObject countBsObject = SystemMetaObject.forObject(countBS);
                    MetaObject boundSqlObject = SystemMetaObject.forObject(boundSql);
                    countBsObject.setValue("metaParameters", boundSqlObject.getValue("metaParameters"));
                    PageHelper.setParameters(countStmt, mappedStatement, countBS, parameterObj);
                    ResultSet rs = countStmt.executeQuery();
                    long total = 0;
                    if (rs.next()) {
                        total = rs.getLong(1);
                    }
                    rs.close();
                    countStmt.close();
                    PageHelper.newPage(pageable, total);
                }
                sql = DialectHelper.getDialect().getPaginationSql(originSql, pageable.getOffset(), pageable.getPageSize());
            } else if (paramObj instanceof Limit) {
                Limit limit = (Limit) paramObj;
                sql = DialectHelper.getDialect().getPaginationSql(originSql, limit.getOffset(), limit.getLimit());
            }
        } else {
            if (pagePattern == null) {
                pagePattern = (configuration.getVariables()) == null ? null : (configuration.getVariables().getProperty("pageSqlId"));
                if (null == pagePattern || "".equals(pagePattern)) {
                    logger.warn("Property pageSqlId is not setted,use default '" + defaultPagePattern + "' ");
                    pagePattern = defaultPagePattern;
                }
            }
            if (!mappedStatement.getId().matches(pagePattern)) {
                return invocation.proceed();
            }
            RowBounds rowBounds = (RowBounds) metaObject.getValue("delegate.rowBounds");
            if (rowBounds == null || rowBounds == RowBounds.DEFAULT) {
                return invocation.proceed();
            }
            // 只重写需要分页的sql语句。通过MappedStatement的ID匹配，默认重写以Page结尾的MappedStatement的sql
            sql = DialectHelper.getDialect().getPaginationSql(boundSql.getSql(), rowBounds.getOffset(), rowBounds.getLimit());
        }
        if (sql == null)
            return invocation.proceed();
        // 重写sql
        metaObject.setValue("delegate.boundSql.sql", sql);
        // 采用物理分页后，就不需要mybatis的内存分页了，所以重置下面的两个参数
        metaObject.setValue("delegate.rowBounds.offset", RowBounds.NO_ROW_OFFSET);
        metaObject.setValue("delegate.rowBounds.limit", RowBounds.NO_ROW_LIMIT);
        logger.debug("生成分页SQL : " + boundSql.getSql());
        // 将执行权交给下一个拦截器
        return invocation.proceed();
    }

    /**
     * 从参数中找出需处理的参数对象,如分页请求,指定记录限制数
     * @param param 参数对象
     * @return 获取的需要处理的参数对象
     */
    protected Object findNeedHandleParameter(Object param) {
        if (param instanceof Pageable) {
            return param;
        }
        if (param instanceof Limit) {
            return param;
        }
        if (param instanceof Map) {
            @SuppressWarnings("unchecked")
            Map<String, Object> map = (Map<String, Object>) param;
            for (Object p : map.values()) {
                if (p instanceof Pageable) {
                    return p;
                }
                if (p instanceof Limit) {
                    return p;
                }
            }
        }
        return null;
    }

    /* (non-Javadoc)
     * @see org.apache.ibatis.plugin.Interceptor#plugin(java.lang.Object)
     */
    @Override
    public Object plugin(Object target) {
        // 当目标类是StatementHandler类型时，才包装目标类，否者直接返回目标本身,减少目标被代理的次数
        if (target instanceof StatementHandler) {
            return Plugin.wrap(target, this);
        } else {
            return target;
        }
    }

    /* (non-Javadoc)
     * @see org.apache.ibatis.plugin.Interceptor#setProperties(java.util.Properties)
     */
    @Override
    public void setProperties(Properties properties) {}
}
