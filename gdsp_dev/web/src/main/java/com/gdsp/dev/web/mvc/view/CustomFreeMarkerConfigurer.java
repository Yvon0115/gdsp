package com.gdsp.dev.web.mvc.view;

import java.io.IOException;

import org.springframework.ui.freemarker.SpringTemplateLoader;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import com.gdsp.dev.core.common.AppContext;

import freemarker.cache.TemplateLoader;

/**
 * @author paul.yang
 * @version 1.0 2014年10月23日
 * @since 1.6
 */
public class CustomFreeMarkerConfigurer extends FreeMarkerConfigurer {

    /* (non-Javadoc)
     * @see org.springframework.ui.freemarker.FreeMarkerConfigurationFactory#getTemplateLoaderForPath(java.lang.String)
     */
    @Override
    protected TemplateLoader getTemplateLoaderForPath(String templateLoaderPath) {
        if (AppContext.isDevelop()) {
            if (isPreferFileSystemAccess()) {
                // Try to load via the file system, fall back to SpringTemplateLoader
                // (for hot detection of template changes, if possible).
                try {
                    return new DevelopTemplateLoader(templateLoaderPath);
                } catch (IOException ex) {
                    if (logger.isDebugEnabled()) {
                        logger.debug("Cannot resolve template loader path [" + templateLoaderPath +
                                "] to [java.io.File]: using SpringTemplateLoader as fallback", ex);
                    }
                    return new SpringTemplateLoader(getResourceLoader(), templateLoaderPath);
                }
            } else {
                // Always load via SpringTemplateLoader (without hot detection of template changes).
                logger.debug("File system access not preferred: using SpringTemplateLoader");
                return new SpringTemplateLoader(getResourceLoader(), templateLoaderPath);
            }
        } else {
            return super.getTemplateLoaderForPath(templateLoaderPath);
        }
    }
}
