package com.gdsp.dev.web.mvc.bind;

import java.beans.PropertyEditorSupport;

import org.springframework.util.StringUtils;

import com.gdsp.dev.base.lang.DDate;
import com.gdsp.dev.base.lang.DDateTime;
import com.gdsp.dev.base.lang.DTime;

/**
 * 自定义日期编辑器兼容日期,时间和日期时间三种类型
 * @author paul.yang
 * @version 1.0 2014年9月24日
 * @since 1.6
 */
public class DDateEditor extends PropertyEditorSupport {

    /**
     * 类型标志
     */
    private int typeFlag = 0;

    /**
     * 构造方法
     */
    public DDateEditor(Class<? extends DDate> clazz) {
        if (clazz == null) {
            typeFlag = 0;
        } else if (clazz.equals(DDateTime.class)) {
            typeFlag = 1;
        } else if (clazz.equals(DTime.class)) {
            typeFlag = 2;
        }
    }

    /* (non-Javadoc)
     * @see java.beans.PropertyEditorSupport#setAsText(java.lang.String)
     */
    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        if (!StringUtils.hasText(text)) {
            setValue(null);
            return;
        }
        Object v;
        if (typeFlag == 1) {
            v = DDateTime.parseDDateTime(text);
        } else if (typeFlag == 2) {
            v = DTime.parseDTime(text);
        } else {
            v = DDate.parseDDate(text);
        }
        setValue(v);
    }
}