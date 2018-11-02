package com.gdsp.dev.core.utils.excel;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ java.lang.annotation.ElementType.FIELD })
/**
 * 
* @ClassName: ExcelVOAttribute
* (这里用一句话描述这个类的作用)
* @author harvey.xu
* @date 2014年12月11日 上午11:06:03
*
 */
public @interface ExcelVOAttribute {

	/**
	 * 是否是编号
	 * @Title: isNo
	 * @return boolean 返回类型
	 * @date 2014年12月12日 上午11:12:02
	 */
    public abstract boolean isNo() default false;

    /**
     * 导出到Excel中的名字.
     */
    public abstract String name();

    /**
     * 配置列的名称,对应A,B,C,D....
     */
    public abstract String column();

    /**
     * 提示信息
     */
    public abstract String prompt() default "";

    /**
     * 设置只能选择不能输入的列内容.
     */
    public abstract String[] combo() default {};

    /**
     * 是否导出数据,应对需求:有时我们需要导出一份模板,这是标题需要但内容需要用户手工填写.
     */
    public abstract boolean isExport() default true;

    /**
     * 对应数据缓存 
     * @return String
     * @date 2014年12月12日 下午2:09:04
     */
    public abstract String dbdata_cache() default "";

	/**
	 * @Title: dbdata_column (这里用一句话描述这个方法的作用)
	 * @param @return 设定文件
	 * @return String 返回类型
	 * @date 2014年12月12日 下午2:09:20
	 */
    public abstract String dbdata_column() default "";

	/**
	 * (子表)
	 * @return String 
	 * @date 2014年12月24日 上午11:31:49
	 */
    public abstract String subclass() default "";
}
