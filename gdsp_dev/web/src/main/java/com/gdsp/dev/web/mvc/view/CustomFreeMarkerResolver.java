package com.gdsp.dev.web.mvc.view;

import java.util.Locale;

import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.freemarker.FreeMarkerViewResolver;

/**
 * 扩展freemarker resolver支持多view引擎
 *
 * @author paul.yang
 * @version 1.0 2015-8-10
 * @since 1.6
 */
public class CustomFreeMarkerResolver extends FreeMarkerViewResolver {

    @Override
    public View resolveViewName(String viewName, Locale locale) throws Exception {
        if (viewName.endsWith(".jsp"))
            return null;
        return super.resolveViewName(viewName, locale);
    }
}
