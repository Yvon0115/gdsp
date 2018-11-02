package com.gdsp.dev.web.scanner;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 扫描bean定义的注解类
 * @author paul.yang
 * @version 1.0 15-1-9
 * @since 1.6
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface Interceptor {

    /**
     * 拦截规则
     * @return 字符串
     */
    String[] value() default "*";

    String[] excudes() default {};
}
