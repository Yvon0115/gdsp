package com.gdsp.dev.web.mvc.ext;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Spring MVC view包装类，所有页面请求方式都需要访问都需要进行的包装
 * @author paul.yang
 * @version 1.0 2014年9月24日
 * @since 1.6
 */
@Target({ ElementType.TYPE, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface ViewWrapper {

    /**
     * 包装路径
     */
    String value() default "";

    /**
     * 是否包装
     */
    boolean wrapped() default true;

    /**
     * 是否仅在ajax时生效包装
     */
    boolean onlyAjax() default false;
}
