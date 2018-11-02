package com.gdsp.integration.framework.kpi.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.gdsp.dev.core.model.param.Sorter;
import com.gdsp.dev.core.model.query.Condition;
import com.gdsp.integration.framework.kpi.model.KpisVO;
import com.gdsp.platform.systools.indexanddim.model.IndGroupRltVO;

/**
 * (指标与报表关系服务)
 * @author zhangwuzheng@chinasofti.com 
 * @version 创建时间：2016年8月18日 下午15:15:17 
 */
public interface IKpiReportService {

    /**
     * 
     * @Description 保存指标与报表关系
     * @param reportId
     * @param kpiId
     */
    public void addKpiReport(String reportId, String[] kpiId);

    /**
     * 
     * @param widgetId 
     * @Description 删除指标
     * @param ids
     */
    public void deleteKpiReport(String widgetId, String[] ids);

    /**
     * 
     * @Description 根据报表id查询指标ID集合
     * @param reportId
     * @return
     */
    public List<String> queryKpiByReport(String reportId);

    /**
     * 
     * @Description 根据报表查询指标信息(分页)
     * @param reportId	报表ID
     * @param condition	
     * @param sort
     * @param page
     * @return	Page<KpisVO>	返回分页指标集合
     */
    public Page<IndGroupRltVO> queryKpiPageByReport(String reportId, Condition condition, Sorter sort, Pageable page);
}
