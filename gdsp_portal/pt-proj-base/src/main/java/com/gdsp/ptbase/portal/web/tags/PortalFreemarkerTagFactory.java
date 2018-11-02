package com.gdsp.ptbase.portal.web.tags;

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
public class PortalFreemarkerTagFactory implements IFreemarkerTagFactory {

    public static final String TAG_PORTLET = "Portlet";
    /**
     * 标签名称
     */
    private String[]           tags        = { TAG_PORTLET };

    @Override
    public String getNamespace() {
        return "pt";
    }

    @Override
    public String[] getTags() {
        return tags;
    }

    @Override
    public TemplateTag getTag(String name) {
        switch (name) {
            case TAG_PORTLET:
                return new PortletTag();
        }
        return null;
    }
}
