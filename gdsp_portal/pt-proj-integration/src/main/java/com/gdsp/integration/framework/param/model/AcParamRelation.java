package com.gdsp.integration.framework.param.model;

import com.gdsp.dev.core.model.entity.AuditableEntity;

public class AcParamRelation extends AuditableEntity {

    private static final long serialVersionUID = -8077001142112664693L;
    private String            paramId;                                 //参数ID
    private String            reportId;                                //报表ID

    public String getParamId() {
        return paramId;
    }

    public void setParamId(String paramId) {
        this.paramId = paramId;
    }

    public String getReportId() {
        return reportId;
    }

    public void setReportId(String reportId) {
        this.reportId = reportId;
    }
}