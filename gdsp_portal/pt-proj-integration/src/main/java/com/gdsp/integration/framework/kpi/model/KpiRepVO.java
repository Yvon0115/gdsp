package com.gdsp.integration.framework.kpi.model;

import com.gdsp.dev.core.model.entity.AuditableEntity;

public class KpiRepVO extends AuditableEntity {

    private static final long serialVersionUID = -1813074682139937651L;
    private String            kpiId;                                   //指标ID
    private String            reportId;                                //报表ID

    public String getKpiId() {
        return kpiId;
    }

    public void setKpiId(String kpiId) {
        this.kpiId = kpiId;
    }

    public String getReportId() {
        return reportId;
    }

    public void setReportId(String reportId) {
        this.reportId = reportId;
    }
}
