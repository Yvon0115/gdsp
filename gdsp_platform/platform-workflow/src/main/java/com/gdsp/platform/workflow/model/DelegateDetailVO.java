package com.gdsp.platform.workflow.model;

import com.gdsp.dev.core.model.entity.BaseEntity;

/**
 * 流程委托表明细表
 * @author MYZhao
 */
public class DelegateDetailVO extends BaseEntity {

    private static final long serialVersionUID = 1L;
    private String            kId;                  // 流程委托主表的id
    private String            deploymentId;         // 流程部署ID

    public String getKid() {
        return kId;
    }

    public void setKid(String kid) {
        this.kId = kid;
    }

    public String getDeploymentId() {
        return deploymentId;
    }

    public void setDeploymentId(String deploymentId) {
        this.deploymentId = deploymentId;
    }
}
