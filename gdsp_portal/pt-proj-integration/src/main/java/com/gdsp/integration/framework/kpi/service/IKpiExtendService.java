package com.gdsp.integration.framework.kpi.service;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.gdsp.dev.core.model.param.Sorter;
import com.gdsp.dev.core.model.query.Condition;
import com.gdsp.integration.framework.kpi.model.KpiLibraryVO;
import com.gdsp.integration.framework.kpi.model.KpisVO;
import com.gdsp.platform.systools.indexanddim.model.IndGroupRltVO;

public interface IKpiExtendService {
    /**
     * 
     * @Description 获取指标分类树型
     * @return
     */
    public Map<String, List<KpiLibraryVO>> queryKpiLibraryMap();
    /**
     * 
     * @Title: queryKpiPage 
     * @Description: 获取指标列表分页
     * @param id    指标分类ID
     * @param condition
     * @param sort
     * @param page
     * @return Page<KpisVO>    返回指标信息集合分页
     */
    public Page<IndGroupRltVO> queryKpiPage(String id, Condition condition, Sorter sort, Pageable page);
    /**
     * 
     * @Description 根据参数id集合获取参数列表
     * @param ids
     * @return
     */
    public List<KpisVO> queryKpiList(List<String> ids);
    /**
     * 分页
     * @param kpilist
     * @param condition
     * @param page
     * @param sort
     * @return
     */
    public Page<IndGroupRltVO> queryKpiPageByKpiId(List<String> kpilist, Condition condition, Pageable page, Sorter sort);
}
