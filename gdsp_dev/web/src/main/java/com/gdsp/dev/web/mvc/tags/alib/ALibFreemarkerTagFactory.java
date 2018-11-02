package com.gdsp.dev.web.mvc.tags.alib;

import org.springframework.stereotype.Service;

import com.gdsp.dev.web.mvc.tags.IFreemarkerTagFactory;
import com.gdsp.dev.web.mvc.tags.TemplateTag;

/**
 * portal扩展的freemarker标签
 *
 * @author paul.yang
 * @version 1.0 2015-7-31
 * @since 1.6
 */
@Service
public class ALibFreemarkerTagFactory implements IFreemarkerTagFactory {

    /**
     * 工厂中的include标签
     */
    public static final String TAG_INCLUDE = "Include";
    /**
     * 标签名称
     */
    private String[]           tags        = { TAG_INCLUDE };

    @Override
    public String getNamespace() {
        return "a";
    }

    @Override
    public String[] getTags() {
        return tags;
    }

    @Override
    public TemplateTag getTag(String name) {
        switch (name) {
            case TAG_INCLUDE:
                return new IncludeTag();
        }
        return null;
    }
}
