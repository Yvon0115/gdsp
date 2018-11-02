package com.gdsp.integration.framework.reportentry.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.gdsp.dev.core.model.query.Condition;
import com.gdsp.dev.persist.ext.MBDao;
import com.gdsp.integration.framework.reportentry.model.ReportVO;

@MBDao
public interface WidgetReportDao {

    ReportVO load(@Param("id") String reportId);

    void deleteReport(String[] ids);

    void updateReport(ReportVO vo);

    Page<ReportVO> queryReportPage(@Param("path") String path, @Param("condition") Condition condition, Pageable page);

    void insert(List<ReportVO> listNodes);

    List<ReportVO> queryReportByDataSource(@Param("condition") Condition condition);
}
