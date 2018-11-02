package com.gdsp.platform.workflow.model;

import com.gdsp.dev.core.model.entity.AuditableEntity;
import com.gdsp.dev.core.utils.tree.model.ITreeEntity;

/**
 * 流程类型表
 * @author gdsp
 *
 */
public class CategoryVO extends AuditableEntity implements ITreeEntity {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private static String     tableName        = "flow_category";
    private String            categoryCode;                      // 类型编码
    private String            categoryName;                      // 类型名称
    private String            innercode;                         // 内部码
    private String            pk_fathercode;                     // 上极类型
    private String            memo;                              // 描述

    public String getPk_fathercode() {
        return pk_fathercode;
    }

    public void setPk_fathercode(String pk_fathercode) {
        this.pk_fathercode = pk_fathercode;
    }

    public String getCategoryCode() {
        return categoryCode;
    }

    public void setCategoryCode(String categoryCode) {
        this.categoryCode = categoryCode;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getTableName() {
        return tableName;
    }

    /**
     * 取得树形编码
     * @return 树形编码
     */
    public String getInnercode() {
        return innercode;
    }

    /**
     * 设置树形编码
     * @param innercode 设置树形编码
     */
    public void setInnercode(String innercode) {
        this.innercode = innercode;
    }
}
