package com.gdsp.dev.core.model.enums;

/**
 * 枚举对象接口
 * @author paul.yang
 * @version 1.0 2014年9月24日
 * @since 1.6
 */
public interface EnumItem {

    /**
     * 获取枚举显示值
     * @return 枚举显示值
     */
    String getLabel();

    /**
     * 获取枚举值
     * @return 枚举值
     */
    String getValue();
}
