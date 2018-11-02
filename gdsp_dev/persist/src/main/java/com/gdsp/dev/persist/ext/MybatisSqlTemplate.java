package com.gdsp.dev.persist.ext;

import static java.lang.reflect.Proxy.newProxyInstance;
import static org.apache.ibatis.reflection.ExceptionUtil.unwrapThrowable;
import static org.springframework.util.Assert.notNull;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.ibatis.exceptions.PersistenceException;
import org.apache.ibatis.executor.BatchResult;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.MyBatisExceptionTranslator;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.support.PersistenceExceptionTranslator;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import org.springframework.util.StringUtils;

import com.gdsp.dev.base.exceptions.BusinessException;
import com.gdsp.dev.base.lang.DDateTime;
import com.gdsp.dev.base.utils.UUIDUtils;
import com.gdsp.dev.core.common.AppContext;
import com.gdsp.dev.core.model.entity.IAuditableEntity;
import com.gdsp.dev.core.model.entity.IBaseEntity;

/**
 * spring管理的会话对象
 * @author paul.yang
 * @version 1.0 2014年9月24日
 * @since 1.6
 */
public class MybatisSqlTemplate extends SqlSessionTemplate implements SqlSession {

    /**
     * 操作日志
     * */
    private static final Logger                  opLogger = LoggerFactory.getLogger("operateLogger");
    /**
     * 日志对象
     */
    private static Logger                        logger   = LoggerFactory.getLogger(MybatisSqlTemplate.class);
    /**
     * 会话工厂
     */
    private final SqlSessionFactory              sqlSessionFactory;
    /**
     * 执行类型
     */
    private final ExecutorType                   executorType;
    /**
     * 会话代理对象
     */
    private final SqlSession                     sqlSessionProxy;
    /**
     * 异常转换对象
     */
    private final PersistenceExceptionTranslator exceptionTranslator;

    /**
     *  构造方法
     * @param sqlSessionFactory 会话工厂
     */
    public MybatisSqlTemplate(SqlSessionFactory sqlSessionFactory) {
        this(sqlSessionFactory, sqlSessionFactory.getConfiguration()
                .getDefaultExecutorType());
    }

    /**
     * 构造方法
     * @param sqlSessionFactory 会话工厂
     * @param executorType 执行类型
     */
    public MybatisSqlTemplate(SqlSessionFactory sqlSessionFactory,
            ExecutorType executorType) {
        this(sqlSessionFactory, executorType, new MyBatisExceptionTranslator(
                sqlSessionFactory.getConfiguration().getEnvironment()
                        .getDataSource(),
                true));
    }

    /**
     * 构造方法
     * @param sqlSessionFactory 会话工厂
     * @param executorType 执行类型
     * @param exceptionTranslator 异常转换器
     */
    public MybatisSqlTemplate(SqlSessionFactory sqlSessionFactory,
            ExecutorType executorType,
            PersistenceExceptionTranslator exceptionTranslator) {
        super(sqlSessionFactory);
        notNull(sqlSessionFactory, "Property 'sqlSessionFactory' is required");
        notNull(executorType, "Property 'executorType' is required");
        this.sqlSessionFactory = sqlSessionFactory;
        this.executorType = executorType;
        this.exceptionTranslator = exceptionTranslator;
        this.sqlSessionProxy = (SqlSession) newProxyInstance(
                SqlSessionFactory.class.getClassLoader(),
                new Class[] { SqlSession.class }, new SqlSessionInterceptor());
    }

    /**
     * 取得会话工厂
     * @return 会话工厂
     */
    public SqlSessionFactory getSqlSessionFactory() {
        return this.sqlSessionFactory;
    }

    /**
     * 取得执行类型
     * @return 执行类型
     */
    public ExecutorType getExecutorType() {
        return this.executorType;
    }

    /**
     * 取得异常转换器
     * @return 异常转换器
     */
    public PersistenceExceptionTranslator getPersistenceExceptionTranslator() {
        return this.exceptionTranslator;
    }

    /**
     * 取得会话代理对象
     * @return 代理对象
     */
    public SqlSession getSqlSessionProxy() {
        return sqlSessionProxy;
    }

    /**
     * {@inheritDoc}
     */
    public <T> T selectOne(String statement) {
        return getSqlSessionProxy().<T> selectOne(statement);
    }

    /**
     * {@inheritDoc}
     */
    public <T> T selectOne(String statement, Object parameter) {
        return getSqlSessionProxy().<T> selectOne(statement, parameter);
    }

    /**
     * {@inheritDoc}
     */
    public <K, V> Map<K, V> selectMap(String statement, String mapKey) {
        return getSqlSessionProxy().<K, V> selectMap(statement, mapKey);
    }

    /**
     * {@inheritDoc}
     */
    public <K, V> Map<K, V> selectMap(String statement, Object parameter,
            String mapKey) {
        return getSqlSessionProxy().<K, V> selectMap(statement, parameter,
                mapKey);
    }

    /**
     * {@inheritDoc}
     */
    public <K, V> Map<K, V> selectMap(String statement, Object parameter,
            String mapKey, RowBounds rowBounds) {
        return getSqlSessionProxy().<K, V> selectMap(statement, parameter,
                mapKey, rowBounds);
    }

    /**
     * {@inheritDoc}
     */
    public <E> List<E> selectList(String statement) {
        return getSqlSessionProxy().<E> selectList(statement);
    }

    /**
     * {@inheritDoc}
     */
    public <E> List<E> selectList(String statement, Object parameter) {
        return getSqlSessionProxy().<E> selectList(statement, parameter);
    }

    /**
     * {@inheritDoc}
     */
    public <E> List<E> selectList(String statement, Object parameter,
            RowBounds rowBounds) {
        return getSqlSessionProxy().<E> selectList(statement, parameter,
                rowBounds);
    }

    /**
     * {@inheritDoc}
     */
    public void select(String statement, ResultHandler handler) {
        getSqlSessionProxy().select(statement, handler);
    }

    /**
     * {@inheritDoc}
     */
    public void select(String statement, Object parameter, ResultHandler handler) {
        getSqlSessionProxy().select(statement, parameter, handler);
    }

    /**
     * {@inheritDoc}
     */
    public void select(String statement, Object parameter, RowBounds rowBounds,
            ResultHandler handler) {
        getSqlSessionProxy().select(statement, parameter, rowBounds, handler);
    }

    /**
     * {@inheritDoc}
     */
    public int insert(String statement) {
        return getSqlSessionProxy().insert(statement);
    }

    /**
     * {@inheritDoc}
     */
    public int insert(String statement, Object parameter) {
        return getSqlSessionProxy().insert(statement, parameter);
    }

    /**
     * {@inheritDoc}
     */
    public int update(String statement) {
        return getSqlSessionProxy().update(statement);
    }

    /**
     * {@inheritDoc}
     */
    public int update(String statement, Object parameter) {
        return getSqlSessionProxy().update(statement, parameter);
    }

    /**
     * {@inheritDoc}
     */
    public int delete(String statement) {
        return getSqlSessionProxy().delete(statement);
    }

    /**
     * {@inheritDoc}
     */
    public int delete(String statement, Object parameter) {
        return getSqlSessionProxy().delete(statement, parameter);
    }

    /**
     * {@inheritDoc}
     */
    public <T> T getMapper(Class<T> type) {
        return getConfiguration().getMapper(type, this);
    }

    /**
     * {@inheritDoc}
     */
    public void commit() {
        throw new UnsupportedOperationException(
                "Manual commit is not allowed over a Spring managed SqlSession");
    }

    /**
     * {@inheritDoc}
     */
    public void commit(boolean force) {
        throw new UnsupportedOperationException(
                "Manual commit is not allowed over a Spring managed SqlSession");
    }

    /**
     * {@inheritDoc}
     */
    public void rollback() {
        throw new UnsupportedOperationException(
                "Manual rollback is not allowed over a Spring managed SqlSession");
    }

    /**
     * {@inheritDoc}
     */
    public void rollback(boolean force) {
        throw new UnsupportedOperationException(
                "Manual rollback is not allowed over a Spring managed SqlSession");
    }

    /**
     * {@inheritDoc}
     */
    public void close() {
        throw new UnsupportedOperationException(
                "Manual close is not allowed over a Spring managed SqlSession");
    }

    /**
     * {@inheritDoc}
     */
    public void clearCache() {
        getSqlSessionProxy().clearCache();
    }

    /**
     * {@inheritDoc}
     * 
     */
    public Configuration getConfiguration() {
        return getSqlSessionFactory().getConfiguration();
    }

    /**
     * {@inheritDoc}
     */
    public Connection getConnection() {
        return getSqlSessionProxy().getConnection();
    }

    /**
     * {@inheritDoc}
     * 
     * @since 1.0.2
     * 
     */
    public List<BatchResult> flushStatements() {
        return getSqlSessionProxy().flushStatements();
    }

    /**
     * Proxy needed to route MyBatis method calls to the proper SqlSession got
     * from Spring's Transaction Manager It also unwraps exceptions thrown by
     * {@code Method#invoke(Object, Object...)} to pass a
     * {@code PersistenceException} to the
     * {@code PersistenceExceptionTranslator}.
     */
    private class SqlSessionInterceptor implements InvocationHandler {

        /**
         * custom mybatis config
         */
        private MybatisConfiguration config     = null;
        private DataSource           dataSource = null;

        public SqlSessionInterceptor() {
            Configuration config = sqlSessionFactory.getConfiguration();
            dataSource = config.getEnvironment().getDataSource();
            if (config instanceof MybatisConfiguration) {
                this.config = (MybatisConfiguration) config;
            }
        }

        @SuppressWarnings("unchecked")
		public Object invoke(Object proxy, Method method, Object[] args)
                throws Throwable {
            boolean autoCommit = !PersistUtils.hasTransaction(dataSource);
            final SqlSession sqlSession = sqlSessionFactory.openSession(executorType, autoCommit);
            String methodName = method.getName();
            try {
                //取得请求菜单
//                String[] menus = AppContext.getContext().getParameterMap().get("menu");
                boolean hasArg = args != null && args.length > 1;
                boolean hasEntity = false;
                if (hasArg) {
                    if (args[1] instanceof Object[]) {
                        hasEntity = ((Object[]) args[1])[0] instanceof IBaseEntity;
                    } else if (args[1] instanceof List) {
                        hasEntity = ((List<?>) args[1]).get(0) instanceof IBaseEntity;
                    } else if (args[1] instanceof IBaseEntity) {
                        hasEntity = args[1] instanceof IBaseEntity;
                    }
                }
                hasEntity = hasArg && hasEntity;
                AppContext context = AppContext.getContext();
                String statementId = (String) args[0];
                if (config != null) {
                    context.setDataSource(config.getDataSource(statementId));
                }
                if (methodName.equalsIgnoreCase("insert") && hasArg && hasEntity) {
                    List<IBaseEntity> listBaseEntity = new ArrayList<IBaseEntity>();
                    if (args[1] instanceof Object[]) {
                        if (((Object[]) args[1])[0] instanceof IBaseEntity) {
                            listBaseEntity = Arrays.asList((IBaseEntity[]) args[1]);
                        }
                    } else if (args[1] instanceof List) {
                        if (((List<?>) args[1]).get(0) instanceof IBaseEntity) {
                            listBaseEntity = (List<IBaseEntity>) args[1];
                        }
                    } else if (args[1] instanceof IBaseEntity) {
                        listBaseEntity.add((IBaseEntity) args[1]);
                    }
                    for (IBaseEntity entity : listBaseEntity) {
                        if (StringUtils.isEmpty(entity.getId()))
                            entity.setId(UUIDUtils.getRandomUUID());
                        if (entity instanceof IAuditableEntity) {
                            IAuditableEntity audit = (IAuditableEntity) entity;
                            String loginName = context.getContextUserId();
                            DDateTime current = new DDateTime();
                            //创建新对象
                            audit.setCreateTime(current);
                            audit.setCreateBy(loginName);
                            logger.info("{}对象(ID:{}) 被 {} 在 {} 新增", new Object[] { audit.getClass().getName(), entity.getId(),
                                    loginName, current });
                        }
                    }
                    //pk_user,menu_id,optype,url,method,sql,param,createBy
                    opLogger.info("operateLogger",
                            AppContext.getContext().getContextUserId(),
                            "", //菜单
                            "新增", //类型
                            "", //url
                            args[0],
                            "",
                            args[1],
                            AppContext.getContext().getContextUserId());
                } else if (methodName.equalsIgnoreCase("update") && hasArg && hasEntity) {
                    List<IBaseEntity> listBaseEntity = new ArrayList<IBaseEntity>();
                    if (args[1] instanceof Object[]) {
                        if (((Object[]) args[1])[0] instanceof IBaseEntity) {
                            listBaseEntity = Arrays.asList((IBaseEntity[]) args[1]);
                        }
                    } else if (args[1] instanceof List) {
                        if (((List<?>) args[1]).get(0) instanceof IBaseEntity) {
                            listBaseEntity = (List<IBaseEntity>) args[1];
                        }
                    } else if (args[1] instanceof IBaseEntity) {
                        listBaseEntity.add((IBaseEntity) args[1]);
                    }
                    for (IBaseEntity entity : listBaseEntity) {
                        String id = entity.getId();
                        String load = statementId.substring(0, statementId.lastIndexOf(".")) + ".load";
                        if (entity.getVersion() != null && sqlSession.getConfiguration().hasStatement(load)) {
                            IBaseEntity old = sqlSession.selectOne(load, id);
                            if (old == null) {
                                throw new BusinessException("当前数据已经被删除");
                            } else if (old != null && old.getVersion() != null && old.getVersion() > entity.getVersion())
                                throw new BusinessException("当前数据已经被修改,请刷新后再保存");
                        }
                        //处理版本
                        entity.setVersion((entity.getVersion() == null ? 0 : entity.getVersion()) + 1);
                        if (entity instanceof IAuditableEntity) {
                            IAuditableEntity audit = (IAuditableEntity) entity;
                            String loginName = context.getContextUserId();
                            DDateTime current = new DDateTime();
                            //创建新对象
                            audit.setLastModifyTime(current);
                            audit.setLastModifyBy(loginName);
                            logger.info("{}对象(ID:{}) 被 {} 在 {} 修改", new Object[] { audit.getClass().getName(), entity.getId(),
                                    loginName, current });
                        }
                    }
                }
                Object result = method.invoke(sqlSession, args);
                if (!TransactionSynchronizationManager.isSynchronizationActive() && !methodName.startsWith("select")) {
                    sqlSession.commit();
                }
                context.setDataSource(null);
                return result;
            } catch (Exception t) {
                if (autoCommit && !methodName.startsWith("select")) {
                    sqlSession.rollback();
                }
                Throwable unwrapped = unwrapThrowable(t);
                if (exceptionTranslator != null
                        && unwrapped instanceof PersistenceException) {
                    Throwable translated = exceptionTranslator
                            .translateExceptionIfPossible((PersistenceException) unwrapped);
                    if (translated != null) {
                        unwrapped = translated;
                    }
                }
                logger.error("mybatis error", unwrapped);
                throw unwrapped;
            } finally {
                if (autoCommit) {
                    sqlSession.close();
                }
            }
        }
    }
}
