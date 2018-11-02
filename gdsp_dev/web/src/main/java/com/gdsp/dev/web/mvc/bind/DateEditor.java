package com.gdsp.dev.web.mvc.bind;

import java.beans.PropertyEditorSupport;
import java.text.ParseException;
import java.util.Date;

import org.springframework.util.StringUtils;

import com.gdsp.dev.base.lang.DDate;
import com.gdsp.dev.base.lang.DDateTime;
import com.gdsp.dev.base.lang.DTime;

/**
 * 日期编辑器兼容日期,时间和日期时间三种类型
 * @author paul.yang
 * @version 1.0 2014年9月24日
 * @since 1.6
 */
public class DateEditor extends PropertyEditorSupport {

    private boolean allowEmpty = true;

    /**
     * 构造方法
     */
    public DateEditor() {}

    /* (non-Javadoc)
     * @see java.beans.PropertyEditorSupport#setAsText(java.lang.String)
     */
    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        if (this.allowEmpty && !StringUtils.hasText(text)) {
            setValue(null);
            return;
        }
        try {
            if (text.length() > 10) {
                setValue(DDateTime.defaultFormat().parse(text));
            } else if (text.length() > 8) {
                setValue(DDate.defaultFormat().parse(text));
            } else {
                setValue(DTime.defaultFormat().parse(text));
            }
        } catch (ParseException ex) {
            throw new IllegalArgumentException("Could not parse date: " + ex.getMessage(), ex);
        }
    }

    /* (non-Javadoc)
     * @see java.beans.PropertyEditorSupport#getAsText()
     */
    @Override
    public String getAsText() {
        Date value = (Date) getValue();
        if (value == null)
            return null;
        return DDate.defaultFormat().format(value);
    }
}