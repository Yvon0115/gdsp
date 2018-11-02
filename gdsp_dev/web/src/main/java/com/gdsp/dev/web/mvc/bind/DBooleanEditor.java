package com.gdsp.dev.web.mvc.bind;

import java.beans.PropertyEditorSupport;

import org.springframework.util.StringUtils;

import com.gdsp.dev.base.lang.DBoolean;

/**
 * 自定义布尔编辑器兼容true/false yes/no 1/0
 * @author paul.yang
 * @version 1.0 2014年9月24日
 * @since 1.6
 */
public class DBooleanEditor extends PropertyEditorSupport {

    /* (non-Javadoc)
     * @see java.beans.PropertyEditorSupport#setAsText(java.lang.String)
     */
    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        if (!StringUtils.hasText(text)) {
            setValue(null);
            return;
        }
        Object v = DBoolean.valueOf(text);
        setValue(v);
    }
}