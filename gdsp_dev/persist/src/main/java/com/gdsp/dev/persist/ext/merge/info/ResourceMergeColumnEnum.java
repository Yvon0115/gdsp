package com.gdsp.dev.persist.ext.merge.info;

/**
 * 列值枚举
 * @author xiangguo
 * @since
 */
public enum ResourceMergeColumnEnum {
    ID("id"), PROJ("proj"), XML("sqlxml");

    private String name;

    private ResourceMergeColumnEnum(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }
}
