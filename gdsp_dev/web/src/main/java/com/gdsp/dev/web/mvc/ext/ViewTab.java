package com.gdsp.dev.web.mvc.ext;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Spring MVC Tab，处理tab显示
 * @author xiangguo
 * @version 1.0 2015年04月12日
 * @since 1.8
 */
@Target({ ElementType.TYPE, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface ViewTab {

    //refresh_main刷新整个页面，初始化首个tab，进行包装修饰
    //add_tab增加单个页签 进行修饰
    //refresh_tab 进行修饰
    //ajax 不进行包装修饰
    public static final String MODE_REFRESH_MAIN   = "refresh_main";
    public static final String MODE_ERROR          = "error";
    public static final String PARAM_FORCETABS     = "__force__jtabs__";
    public static final String PARAM_TABID         = "__jtabs__tabId__";
    public static final String PARAM_TABENTITYEYPE = "__jtabs__tabentitytype__";
    public static final String PARAM_LAYOUOTMODE   = "__jtabs__layout__";
    public static final String TAB_URL             = "__tab_url__";
    public static final String TAB_ID              = "__tab_id__";
    public static final String TAB_TITLE           = "__tab_title__";

    /**
     * tab模式
     */
    String mode() default "";

    /**
     * 是否tab
     */
    boolean tab() default false;
}
