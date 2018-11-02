package com.gdsp.integration.framework.reportentry.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.gdsp.dev.core.model.query.Condition;
import com.gdsp.integration.framework.reportentry.model.ReportVO;
import com.gdsp.platform.systools.datasource.model.DataSourceVO;

public interface IWidgetReportService {

    public ReportVO load(String reportId);

    public void deleteReport(String[] ids);

    public void updateReport(ReportVO vo);

    public Page<ReportVO> queryReportPage(String path, Condition condition, Pageable page);

    public List<ReportVO> queryReportByParentPath(String path, String datasourceID, boolean leaf);

    public void synLocalReportfromRemote(DataSourceVO dataSourceVO);
}
