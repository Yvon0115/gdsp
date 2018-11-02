package com.gdsp.dev.web.mvc.tags;

import java.io.IOException;
import java.io.Writer;
import java.util.Map;

import javax.servlet.ServletException;

/**
 * 模板标签，分为三部分开始，结束和内容（可选)
 * @author yaboocn
 * @version 1.0 2010-2-27
 * @since 1.0
 */
public interface TemplateTag {

    /**
     * 设置标签属性
     * @param properties 标签属性
     */
    void setProperties(Map<String, Object> properties);

    /**
     * 输出开始部分
     * @param writer 输出器对象
     * @return 内容是否应该被估值
     */
    boolean start(Writer writer) throws ServletException, IOException;

    /**
     * 输出结束部分
     * @param writer 输出器对象
     * @param body 内容串
     * @return 内容是否需要再估值
     */
    boolean end(Writer writer, String body);

    /**
     * 关闭标签
     */
    void close();

    /**
     * 判断是否在组件中使用内容
     * @return 布尔值
     */
    boolean useBody();

    /**
     * 是否为单例标签
     * @return 单例标签
     */
    boolean isSingleton();
}