package com.gdsp.integration.framework.kpi.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.gdsp.dev.core.model.param.Sorter;
import com.gdsp.dev.core.model.query.Condition;
import com.gdsp.dev.persist.result.MapListResultHandler;
import com.gdsp.integration.framework.kpi.dao.IKpiReportDao;
import com.gdsp.integration.framework.kpi.model.KpisVO;
import com.gdsp.integration.framework.kpi.service.IKpiExtendService;
import com.gdsp.platform.systools.indexanddim.dao.IIndGroupDao;
import com.gdsp.platform.systools.indexanddim.model.IndGroupRltVO;
import com.gdsp.platform.systools.indexanddim.model.IndexInfoVO;
import com.gdsp.platform.systools.indexanddim.service.IIndGroupService;
import com.gdsp.platform.systools.indexanddim.service.IIndexManageService;

public class KpiExtendServiceImpl implements IKpiExtendService{
    @Autowired
    IIndexManageService indexService;
    @Autowired
    IIndGroupService indexGrpService;
    @Override
    public Map queryKpiLibraryMap() {
        MapListResultHandler<Map> handler = new MapListResultHandler<>("pk_fatherid");
        indexGrpService.queryKpiTreeMap(handler);
        return handler.getResult();
    }

    @Override
    public Page<IndGroupRltVO> queryKpiPage(String id, Condition condition, Sorter sort, Pageable page) {
        if (StringUtils.isNotBlank(id)) {
            condition.addExpression("c.id", id);
        } else {
            condition.addExpression("c.id", null, "is not null");
        }
        return indexGrpService.queryKpiPageByCondition(condition, page);
    }

    @Override
    public List<KpisVO> queryKpiList(List<String> ids) {
        List<IndexInfoVO> indexs= indexService.queryIndexByIds(ids);
        List<KpisVO> lists=new ArrayList<KpisVO>();
        for(IndexInfoVO index: indexs)
        {
            KpisVO vo=new KpisVO();
            vo.setId(index.getId());
            vo.setName(index.getIndexName());
            vo.setAlias(index.getIndexName());
            vo.setComments(index.getBusinessbore());
            lists.add(vo);
        }
        return lists;
    }

    @Override
    public Page<IndGroupRltVO> queryKpiPageByKpiId(List<String> kpilist, Condition condition, Pageable page, Sorter sort) {
        
        if(kpilist.size()>0)
        {
        condition.addExpression("a.id", kpilist,"in");
        return  indexGrpService.queryKpiPageByCondition(condition, page);
        }
        return null;
    }
}
