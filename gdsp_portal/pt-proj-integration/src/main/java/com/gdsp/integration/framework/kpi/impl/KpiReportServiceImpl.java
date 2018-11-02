package com.gdsp.integration.framework.kpi.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gdsp.dev.core.model.param.Sorter;
import com.gdsp.dev.core.model.query.Condition;
import com.gdsp.integration.framework.kpi.dao.IKpiReportDao;
import com.gdsp.integration.framework.kpi.model.KpiRepVO;
import com.gdsp.integration.framework.kpi.model.KpisVO;
import com.gdsp.integration.framework.kpi.service.IKpiExtendService;
import com.gdsp.integration.framework.kpi.service.IKpiReportService;
import com.gdsp.platform.systools.indexanddim.model.IndGroupRltVO;

@Service
@Transactional(readOnly = true)
public class KpiReportServiceImpl implements IKpiReportService {

    @Autowired
    private IKpiReportDao dao;
    @Autowired
    private IKpiExtendService kpiExtends;
    @Transactional(readOnly = false)
    @Override
    public void addKpiReport(String reportId, String[] kpiId) {
        if (kpiId != null && kpiId.length > 0 && StringUtils.isNotBlank(reportId)) {
            for (String kid : kpiId) {
                KpiRepVO vo = new KpiRepVO();
                vo.setKpiId(kid);
                vo.setReportId(reportId);
                KpiRepVO re=dao.selectOwn(kid,reportId);
                if(re==null)
                {
                dao.insert(vo);
                }
            }
        }
    }

    @Transactional(readOnly = false)
    @Override
    public void deleteKpiReport(String widgetId,String[] ids) {
        dao.delete(widgetId,ids);
    }

    @Override
    public List<String> queryKpiByReport(String reportId) {
        return dao.queryKpiIdsByReport(reportId);
    }

    @Override
    public Page<IndGroupRltVO> queryKpiPageByReport(String reportId, Condition condition, Sorter sort, Pageable page) {
        List<String>kpilist= dao.queryKpiIdsByReport(reportId);
        return kpiExtends.queryKpiPageByKpiId(kpilist,condition, page, sort);
    }
}
