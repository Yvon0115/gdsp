package com.gdsp.dev.persist.ext;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 标识日志相关的DAO,方便{@link org.mybatis.spring.mapper.MapperScannerConfigurer}的扫描。
 * 
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface LogDao {

}
