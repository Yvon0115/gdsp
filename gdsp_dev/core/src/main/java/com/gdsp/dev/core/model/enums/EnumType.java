package com.gdsp.dev.core.model.enums;

import java.util.List;

/**
 * 枚举序列值接口
 * @author paul.yang
 * @version 1.0 2014年9月24日
 * @since 1.6
 */
public interface EnumType {

    /**
     * 取得枚举对象列表
     * @return 枚举对象列表
     */
    List<EnumItem> getEnumItems();
}
