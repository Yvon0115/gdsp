package com.gdsp.dev.persist.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 数据源标识，可以设置方法使用的数据源
 * @author paul.yang
 * @version 1.0 2014年9月24日
 * @since 1.6
 */
@Target({ ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface DataSource {

    /**
     * 指定数据源id
     * @return 数据源id
     */
    String value() default "";
}
