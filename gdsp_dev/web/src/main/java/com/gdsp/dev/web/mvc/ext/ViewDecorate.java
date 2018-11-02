package com.gdsp.dev.web.mvc.ext;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Spring MVC 修饰类，当全页面访问时为页面添加外框
 * @author paul.yang
 * @version 1.0 2014年9月24日
 * @since 1.6
 */
@Target({ ElementType.TYPE, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface ViewDecorate {

    /**
     * 包装路径
     */
    String value() default "";

    /**
     * 是否包装
     */
    boolean decorate() default true;
}
