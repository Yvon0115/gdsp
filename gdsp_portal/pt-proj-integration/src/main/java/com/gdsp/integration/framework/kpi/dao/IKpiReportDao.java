package com.gdsp.integration.framework.kpi.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.gdsp.dev.core.model.param.Sorter;
import com.gdsp.dev.core.model.query.Condition;
import com.gdsp.dev.persist.ext.MBDao;
import com.gdsp.integration.framework.kpi.model.KpiRepVO;
import com.gdsp.integration.framework.kpi.model.KpisVO;

@MBDao
public interface IKpiReportDao {

    void insert(KpiRepVO vo);

    void delete(@Param("widgetId") String widgetId,@Param("ids") String[] ids);

    List<String> queryKpiIdsByReport(@Param("reportId") String reportId);
  
    KpiRepVO load(String id);

    KpiRepVO selectOwn(@Param("kid")String kid, @Param("reportId")String reportId);
}
