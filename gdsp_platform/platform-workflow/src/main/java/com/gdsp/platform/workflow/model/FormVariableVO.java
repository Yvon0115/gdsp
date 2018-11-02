package com.gdsp.platform.workflow.model;

import com.gdsp.dev.core.model.entity.AuditableEntity;

/**
 * 预置单据内变量信息表
 * @author gdsp
 *
 */
public class FormVariableVO extends AuditableEntity {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private String            fromTypeId;           // 单据类型ID
    private String            variableName;         // 变量名称
    private String            displayName;          // 变量显示名称
    private String            memo;                 // 描述

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getFromTypeId() {
        return fromTypeId;
    }

    public void setFromTypeId(String fromTypeId) {
        this.fromTypeId = fromTypeId;
    }

    public String getVariableName() {
        return variableName;
    }

    public void setVariableName(String variableName) {
        this.variableName = variableName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }
}
