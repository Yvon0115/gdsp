package com.gdsp.integration.framework.reportentry.service;

import java.util.List;

import org.springframework.ui.Model;

import com.gdsp.integration.framework.reportentry.model.ReportVO;
import com.gdsp.platform.systools.datasource.model.DataSourceVO;

public interface IReportOprationService {

    public void login(DataSourceVO vo);

    public List<ReportVO> getReportFromRemote(String Path);
    
    public List<ReportVO> getReportFromRemote(DataSourceVO dataSourceVO);

    public String forwardToPage(Model model, ReportVO reportVO, List<String> param, List<String> kpi);
}
