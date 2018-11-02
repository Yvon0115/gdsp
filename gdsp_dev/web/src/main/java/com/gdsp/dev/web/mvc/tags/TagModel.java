package com.gdsp.dev.web.mvc.tags;

import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.TemplateModel;
import freemarker.template.TemplateModelException;
import freemarker.template.TemplateTransformModel;

/**
 * Freemark用户指令扩展模型类
 * @author yaboocn
 * @version 1.0 2010-3-11
 * @since 1.0
 */
public class TagModel implements TemplateTransformModel {

    /**
     * 日志变量
     */
    private static Logger         logger       = LoggerFactory.getLogger(TagModel.class);
    /**
     * 标签名
     */
    private String                tagName;
    /**
     * 标签工厂
     */
    private IFreemarkerTagFactory factory;
    /**
     * 标签对象,当标签为单例标签使用
     */
    private TemplateTag           singletonTag = null;

    /**
     * 构造方法
     * @param tagName 标签名
     * @param factory 工厂类
     */
    public TagModel(String tagName, IFreemarkerTagFactory factory) {
        this.tagName = tagName;
        this.factory = factory;
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    public Writer getWriter(Writer writer, Map properties) throws TemplateModelException, IOException {
        properties = unwrapProperties(properties);
        TemplateTag tag;
        if (singletonTag == null) {
            tag = factory.getTag(tagName);
            if (tag.isSingleton()) {
                singletonTag = tag;
            }
        } else {
            tag = singletonTag;
        }
        tag.setProperties(properties);
        return new CallbackWriter(tag, writer);
    }

    /**
     * 参数解包方法，将包装成TemplateModel的参数对象解包成普通参数
     * @param params 需解包的参数映射
     * @return 解包后的参数映射
     */
    protected Map<String, Object> unwrapProperties(Map<String, Object> params) {
        Map<String, Object> map = new HashMap<>();
        DefaultObjectWrapper objectWrapper = new DefaultObjectWrapper(Configuration.getVersion());
        for (Map.Entry<String, Object> entry : params.entrySet()) {
            Object value = entry.getValue();
            if (value == null)
                continue;
            // the value should ALWAYS be a decendant of TemplateModel
            if (value instanceof TemplateModel) {
                try {
                    value = objectWrapper.unwrap((TemplateModel) value);
                } catch (TemplateModelException e) {
                    logger.error("failed to unwrap [" + value + "] it will be ignored", e);
                }
                // if it doesn't, we'll do it the old way by just returning the toString() representation
            }
            map.put(entry.getKey(), value);
        }
        return map;
    }
}
