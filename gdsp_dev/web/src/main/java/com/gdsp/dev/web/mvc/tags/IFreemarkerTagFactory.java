package com.gdsp.dev.web.mvc.tags;

/**
 * freemarker标签工厂
 *
 * @author paul.yang
 * @version 1.0 2015-7-31
 * @since 1.6
 */
public interface IFreemarkerTagFactory {

    /**
     * 取得标签命名空间
     * @return 标签命名空间
     */
    String getNamespace();

    /**
     * 取得标签列表
     * @return 标签列表
     */
    String[] getTags();

    /**
     * 根据标签名取得标签对象
     * @param name 标签名
     * @return 标签对象
     */
    TemplateTag getTag(String name);
}
