package com.gdsp.dev.core.utils.tree.model;

import com.gdsp.dev.core.model.entity.IBaseEntity;

/**
 * 树形接口
 * @author yaboocn
 * @version 1.0 2011-9-19
 * @since 1.6
 */
public interface ITreeEntity extends IBaseEntity {

    /**
     * 实体属性树形编码
     */
    public final static String PROP_INNERCODE = "innercode";

    /**
     * 取得树形编码
     * @return 树形编码
     */
    public String getInnercode();

    /**
     * 设置树形编码
     * @param innercode 设置树形编码
     */
    public void setInnercode(String innercode);

    /**
     * 取得当前实体表名
     * @return 表名
     */
    public String getTableName();
}
