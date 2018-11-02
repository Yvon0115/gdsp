package com.gdsp.platform.workflow.model;

import com.gdsp.dev.core.model.entity.AuditableEntity;

/**
 * 审批权限表
 * @author gdsp
 *
 */
public class ApprAuthorityVO extends AuditableEntity {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private String            nodeInfoId;           // 流程节点信息表ID
    private String            apprType;             // 审批类型
    private String            apprTypeId;           // 审批类型ID
    private String            apprTypeCode;         // 审批类型编码
    private String            apprTypeName;         // 审批类型名称

    public String getNodeInfoId() {
        return nodeInfoId;
    }

    public void setNodeInfoId(String nodeInfoId) {
        this.nodeInfoId = nodeInfoId;
    }

    public String getApprType() {
        return apprType;
    }

    public void setApprType(String apprType) {
        this.apprType = apprType;
    }

    public String getApprTypeId() {
        return apprTypeId;
    }

    public void setApprTypeId(String apprTypeId) {
        this.apprTypeId = apprTypeId;
    }

    public String getApprTypeCode() {
        return apprTypeCode;
    }

    public void setApprTypeCode(String apprTypeCode) {
        this.apprTypeCode = apprTypeCode;
    }

    public String getApprTypeName() {
        return apprTypeName;
    }

    public void setApprTypeName(String apprTypeName) {
        this.apprTypeName = apprTypeName;
    }
}
