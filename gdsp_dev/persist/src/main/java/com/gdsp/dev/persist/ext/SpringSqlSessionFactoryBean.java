package com.gdsp.dev.persist.ext;

import static org.springframework.util.Assert.notNull;
import static org.springframework.util.ObjectUtils.isEmpty;
import static org.springframework.util.StringUtils.hasLength;
import static org.springframework.util.StringUtils.tokenizeToStringArray;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.Properties;

import javax.sql.DataSource;
import javax.xml.transform.TransformerException;

import org.apache.ibatis.executor.ErrorContext;
import org.apache.ibatis.logging.Log;
import org.apache.ibatis.logging.LogFactory;
import org.apache.ibatis.mapping.DatabaseIdProvider;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.mapping.VendorDatabaseIdProvider;
import org.apache.ibatis.ognl.OgnlRuntime;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.reflection.factory.ObjectFactory;
import org.apache.ibatis.reflection.wrapper.ObjectWrapperFactory;
import org.apache.ibatis.scripting.xmltags.DynamicContext;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.type.TypeHandler;
import org.mybatis.spring.transaction.SpringManagedTransactionFactory;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.NestedIOException;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.TransactionAwareDataSourceProxy;
import org.xml.sax.SAXException;

import com.gdsp.dev.base.exceptions.DevException;
import com.gdsp.dev.persist.ext.merge.assistant.ResourceMergeXMLMapperBuilder;
import com.gdsp.dev.persist.ext.merge.handler.ResourceMergeHandler;

/**
 * 会话工厂bean，参考org.mybatis.spring.SqlSessionFactoryBean 使得事务和链接托管给spring
 * @author paul.yang
 * @version 1.0 2014年10月27日
 * @since 1.6
 */
public class SpringSqlSessionFactoryBean implements
        FactoryBean<SqlSessionFactory>, InitializingBean,
        ApplicationListener<ApplicationEvent> {

    /**
     * 日志对象
     */
    private final Log                logger                   = LogFactory.getLog(getClass());
    /**
     * 防止发生异常时多次扫描配置文件
     */
    private static boolean           scanComplete             = false;
    /**
     * 配置文件字段对象
     */
    private Resource                 configLocation;
    /**
     * mapper文件资源对象
     */
    private Resource[]               mapperLocations;
    /**
     * 数据员
     */
    private DataSource               dataSource;
    /**
     * 事务工厂
     */
    private TransactionFactory       transactionFactory;
    /**
     * 配置属性
     */
    private Properties               configurationProperties;
    /**
     * 会话工厂构建者
     */
    private SqlSessionFactoryBuilder sqlSessionFactoryBuilder = new SqlSessionFactoryBuilder();
    /**
     * 会话工厂
     */
    private SqlSessionFactory        sqlSessionFactory;
    /**
     * 环境名称
     */
    private String                   environment              = SpringSqlSessionFactoryBean.class
            .getSimpleName();                                                                    // EnvironmentAware requires spring 3.1
    /**
     * 确保所有mapper文件全不加载标志
     */
    private boolean                  failFast                 = false;
    /**
     * 插件
     */
    private Interceptor[]            plugins;
    /**
     * 类型处理器
     */
    private TypeHandler<?>[]         typeHandlers;
    /**
     * 类型处理所在包名可以逗号分割多个包
     */
    private String                   typeHandlersPackage;
    /**
     * 类型别名类
     */
    private Class<?>[]               typeAliases;
    /**
     * 类型别名所在包
     */
    private String                   typeAliasesPackage;
    /**
     * 类型别名超类型
     */
    private Class<?>                 typeAliasesSuperType;
    /**
     * 数据库id提供者
     */
    private DatabaseIdProvider       databaseIdProvider       = new VendorDatabaseIdProvider();
    /**
     * 对象工厂
     */
    private ObjectFactory            objectFactory;
    /**
     * 包装对象工厂
     */
    private ObjectWrapperFactory     objectWrapperFactory;
    /**
     * 映射注册类,为了扩展基于annotation的功能，如扩展集合类型增加yangbo
     */
    private MybatisMapperRegistry    mapperRegistry           = null;
    /**
     * resource合并处理器
     */
    private ResourceMergeHandler     mergeHandler             = null;

    /**
     * 取得合并处理器
     * @param mergeHandler
     */
    public void setMergeHandler(ResourceMergeHandler mergeHandler) {
        this.mergeHandler = mergeHandler;
    }

    /**
     * 取得映射注册类，为了扩展基于annotation的功能，如扩展集合类型增加yangbo
     * @return 映射注册类
     */
    public MybatisMapperRegistry getMapperRegistry() {
        return mapperRegistry;
    }

    /**
     * 设置映射注册类，为了扩展基于annotation的功能，如扩展集合类型增加yangbo
     * @param mapperRegistryClass 映射注册类
     */
    public void setMapperRegistry(MybatisMapperRegistry mapperRegistryClass) {
        this.mapperRegistry = mapperRegistryClass;
    }

    /**
     * 设置对象工厂
     * @param objectFactory 对象工厂
     */
    public void setObjectFactory(ObjectFactory objectFactory) {
        this.objectFactory = objectFactory;
    }

    /**
     * 设置包装对象工厂
     * @param objectWrapperFactory 包装对象工厂
     */
    public void setObjectWrapperFactory(
            ObjectWrapperFactory objectWrapperFactory) {
        this.objectWrapperFactory = objectWrapperFactory;
    }

    /**
     * 取得数据库id提供者
     * @return 数据库id提供者
     */
    public DatabaseIdProvider getDatabaseIdProvider() {
        return databaseIdProvider;
    }

    /**
     * 设置数据库id提供者
     * @param databaseIdProvider 数据库id提供者
     */
    public void setDatabaseIdProvider(DatabaseIdProvider databaseIdProvider) {
        this.databaseIdProvider = databaseIdProvider;
    }

    /**
     * 设置插件
     * @param plugins 插件
     */
    public void setPlugins(Interceptor[] plugins) {
        this.plugins = plugins;
    }

    /**
     * 设置类型那个别名包路径，用逗号分割多个包
     * @param typeAliasesPackage
     */
    public void setTypeAliasesPackage(String typeAliasesPackage) {
        this.typeAliasesPackage = typeAliasesPackage;
    }

    /**
     * 设置类型别名超类
     * @param typeAliasesSuperType 类型别名超类
     */
    public void setTypeAliasesSuperType(Class<?> typeAliasesSuperType) {
        this.typeAliasesSuperType = typeAliasesSuperType;
    }

    /**
     * 设置类型处理包路径，用逗号分割多个包
     * @param typeHandlersPackage
     */
    public void setTypeHandlersPackage(String typeHandlersPackage) {
        this.typeHandlersPackage = typeHandlersPackage;
    }

    /**
     * 设置类型处理器 通过annotation(@code MappedTypes)注入
     * @param typeHandlers  类型处理器列表
     */
    public void setTypeHandlers(TypeHandler<?>[] typeHandlers) {
        this.typeHandlers = typeHandlers;
    }

    /**
     * 设置类型别名类
     * @param typeAliases 别名类数组
     */
    public void setTypeAliases(Class<?>[] typeAliases) {
        this.typeAliases = typeAliases;
    }

    /**
     * 设置确保所有mapper文件全部加载标志
     * @param failFast 布尔值
     */
    public void setFailFast(boolean failFast) {
        this.failFast = failFast;
    }

    /**
     * 设置mybatis配置文件资源
     * @param configLocation mybatis配置文件资源
     */
    public void setConfigLocation(Resource configLocation) {
        this.configLocation = configLocation;
    }

    /**
     * 设置mybatis映射文件资源
     * @param mapperLocations mybatis映射文件资源列表
     */
    public void setMapperLocations(Resource[] mapperLocations) {
        this.mapperLocations = mapperLocations;
    }

    /**
     * 设置会话工厂属性
     * @param sqlSessionFactoryProperties 会话工厂属性
     */
    public void setConfigurationProperties(
            Properties sqlSessionFactoryProperties) {
        this.configurationProperties = sqlSessionFactoryProperties;
    }

    /**
     * 设置数据源对象
     * @param dataSource 数据员对象
     */
    public void setDataSource(DataSource dataSource) {
        if (dataSource instanceof TransactionAwareDataSourceProxy) {
            this.dataSource = dataSource;
        } else {
            this.dataSource = new TransactionAwareDataSourceProxy(dataSource);
        }
    }

    /**
     * 设置会话工厂构建器
     * @param sqlSessionFactoryBuilder 会话工厂构建器
     */
    public void setSqlSessionFactoryBuilder(
            SqlSessionFactoryBuilder sqlSessionFactoryBuilder) {
        this.sqlSessionFactoryBuilder = sqlSessionFactoryBuilder;
    }

    /**
     * 设置事务工厂类
     * @param transactionFactory 事务工厂
     */
    public void setTransactionFactory(TransactionFactory transactionFactory) {
        this.transactionFactory = transactionFactory;
    }

    /**
     * 设置环境对象
     * @param environment 环境对象
     */
    public void setEnvironment(String environment) {
        this.environment = environment;
    }

    /* (non-Javadoc)
     * @see org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
     */
    public void afterPropertiesSet() throws Exception {
        notNull(dataSource, "Property 'dataSource' is required");
        notNull(sqlSessionFactoryBuilder,
                "Property 'sqlSessionFactoryBuilder' is required");
        if (scanComplete)
            return;
        try {
            this.sqlSessionFactory = buildSqlSessionFactory();
        } catch (Exception ex) {
            logger.error("mybatis init error", ex);
            scanComplete = true;
            throw ex;
        }
    }

    /**
     * 构建会话工厂
     * @return 会话工厂对象
     * @throws IOException 
     * @throws SAXException 
     * @throws DevException 
     * @throws TransformerException 
     * @throws Exception 
     */
    protected SqlSessionFactory buildSqlSessionFactory() throws IOException, DevException, SAXException, TransformerException  {
        Configuration configuration;
        XMLConfigBuilder xmlConfigBuilder = null;
        if (this.configLocation != null) {
            xmlConfigBuilder = new XMLConfigBuilder(
                    this.configLocation.getInputStream(), null,
                    this.configurationProperties);
            configuration = xmlConfigBuilder.getConfiguration();
        } else {
            if (this.logger.isDebugEnabled()) {
                this.logger
                        .debug("Property 'configLocation' not specified, using default MyBatis Configuration");
            }
            configuration = new Configuration();
            configuration.setVariables(this.configurationProperties);
        }
        if (this.objectFactory != null) {
            configuration.setObjectFactory(this.objectFactory);
        }
        if (this.objectWrapperFactory != null) {
            configuration.setObjectWrapperFactory(this.objectWrapperFactory);
        }
        if (mapperRegistry != null && configuration instanceof MybatisConfiguration) {
            mapperRegistry.setConfig((MybatisConfiguration) configuration);
            ((MybatisConfiguration) configuration).setMapperRegistry(mapperRegistry);
        }
        if (hasLength(this.typeAliasesPackage)) {
            String[] typeAliasPackageArray = tokenizeToStringArray(
                    this.typeAliasesPackage,
                    ConfigurableApplicationContext.CONFIG_LOCATION_DELIMITERS);
            for (String packageToScan : typeAliasPackageArray) {
                configuration.getTypeAliasRegistry().registerAliases(
                        packageToScan,
                        typeAliasesSuperType == null ? Object.class
                                : typeAliasesSuperType);
                if (this.logger.isDebugEnabled()) {
                    this.logger.debug("Scanned package: '" + packageToScan
                            + "' for aliases");
                }
            }
        }
        if (!isEmpty(this.typeAliases)) {
            for (Class<?> typeAlias : this.typeAliases) {
                configuration.getTypeAliasRegistry().registerAlias(typeAlias);
                if (this.logger.isDebugEnabled()) {
                    this.logger.debug("Registered type alias: '" + typeAlias
                            + "'");
                }
            }
        }
        if (!isEmpty(this.plugins)) {
            for (Interceptor plugin : this.plugins) {
                configuration.addInterceptor(plugin);
                if (this.logger.isDebugEnabled()) {
                    this.logger.debug("Registered plugin: '" + plugin + "'");
                }
            }
        }
        if (hasLength(this.typeHandlersPackage)) {
            String[] typeHandlersPackageArray = tokenizeToStringArray(
                    this.typeHandlersPackage,
                    ConfigurableApplicationContext.CONFIG_LOCATION_DELIMITERS);
            for (String packageToScan : typeHandlersPackageArray) {
                configuration.getTypeHandlerRegistry().register(packageToScan);
                if (this.logger.isDebugEnabled()) {
                    this.logger.debug("Scanned package: '" + packageToScan
                            + "' for type handlers");
                }
            }
        }
        if (!isEmpty(this.typeHandlers)) {
            for (TypeHandler<?> typeHandler : this.typeHandlers) {
                configuration.getTypeHandlerRegistry().register(typeHandler);
                if (this.logger.isDebugEnabled()) {
                    this.logger.debug("Registered type handler: '"
                            + typeHandler + "'");
                }
            }
        }
        if (xmlConfigBuilder != null) {
            try {
                xmlConfigBuilder.parse();
                if (this.logger.isDebugEnabled()) {
                    this.logger.debug("Parsed configuration file: '"
                            + this.configLocation + "'");
                }
            } catch (Exception ex) {
                throw new NestedIOException("Failed to parse config resource: "
                        + this.configLocation, ex);
            } finally {
                ErrorContext.instance().reset();
            }
        }
        if (this.transactionFactory == null) {
            this.transactionFactory = new SpringManagedTransactionFactory();
        }
        Environment environment = new Environment(this.environment,
                this.transactionFactory, this.dataSource);
        configuration.setEnvironment(environment);
        if (this.databaseIdProvider != null) {
            try {
                configuration.setDatabaseId(this.databaseIdProvider
                        .getDatabaseId(this.dataSource));
            } catch (SQLException e) {
                throw new NestedIOException("Failed getting a databaseId", e);
            }
        }
        if (!isEmpty(this.mapperLocations)) {
            //合并前初始化资源
            if (this.mergeHandler != null) {
                this.mergeHandler.init(mapperLocations);
            }
            for (Resource mapperLocation : this.mapperLocations) {
                if (mapperLocation == null) {
                    continue;
                }
                //进行资源合并
                InputStream mapperLocationStream = mapperLocation.getInputStream();
                if (this.mergeHandler != null) {
                    mapperLocationStream = this.mergeHandler.doMerge(mapperLocation.getDescription());
                }
                try {
                    ResourceMergeXMLMapperBuilder xmlMapperBuilder = new ResourceMergeXMLMapperBuilder(
                            mapperLocationStream, configuration,
                            mapperLocation.toString(),
                            configuration.getSqlFragments());
                    xmlMapperBuilder.parse();
                } catch (Exception e) {
                    throw new NestedIOException(
                            "Failed to parse mapping resource: '"
                                    + mapperLocation + "'",
                            e);
                } finally {
                    ErrorContext.instance().reset();
                }
                if (this.logger.isDebugEnabled()) {
                    this.logger.debug("Parsed mapper file: '" + mapperLocation
                            + "'");
                }
            }
        } else {
            if (this.logger.isDebugEnabled()) {
                this.logger
                        .debug("Property 'mapperLocations' was not specified or no matching resources found");
            }
        }
        //为了将Condition对象转换为map
        DynamicContext temp = new DynamicContext(configuration, null);
        OgnlRuntime.setPropertyAccessor(temp.getBindings().getClass(), new ContextAccessor());
        return this.sqlSessionFactoryBuilder.build(configuration);
    }

    /* (non-Javadoc)
     * @see org.springframework.beans.factory.FactoryBean#getObject()
     */
    public SqlSessionFactory getObject() throws Exception {
        if (this.sqlSessionFactory == null) {
            afterPropertiesSet();
        }
        return this.sqlSessionFactory;
    }

    /* (non-Javadoc)
     * @see org.springframework.beans.factory.FactoryBean#getObjectType()
     */
    public Class<? extends SqlSessionFactory> getObjectType() {
        return this.sqlSessionFactory == null ? SqlSessionFactory.class
                : this.sqlSessionFactory.getClass();
    }

    /* (non-Javadoc)
     * @see org.springframework.beans.factory.FactoryBean#isSingleton()
     */
    public boolean isSingleton() {
        return true;
    }

    /* (non-Javadoc)
     * @see org.springframework.context.ApplicationListener#onApplicationEvent(org.springframework.context.ApplicationEvent)
     */
    public void onApplicationEvent(ApplicationEvent event) {
        if (failFast && event instanceof ContextRefreshedEvent) {
            // fail-fast -> check all statements are completed
            this.sqlSessionFactory.getConfiguration().getMappedStatementNames();
        }
    }
}
