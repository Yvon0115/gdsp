package com.gdsp.integration.framework.param.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gdsp.dev.core.model.param.Sorter;
import com.gdsp.dev.core.model.query.Condition;
import com.gdsp.integration.framework.param.dao.IAcParamRelationDao;
import com.gdsp.integration.framework.param.model.AcParam;
import com.gdsp.integration.framework.param.model.AcParamRelation;
import com.gdsp.integration.framework.param.service.IParamReportService;

@Service
@Transactional(readOnly = true)
public class ParamReportServiceImpl implements IParamReportService {

    @Autowired
    private IAcParamRelationDao dao;

    @Transactional(readOnly = false)
    @Override
    public void addParamRelation(AcParamRelation vo) {
        dao.insert(vo);
    }

    @Transactional(readOnly = false)
    @Override
    public void deleteParamRelation(String[] ids) {
        dao.deleteByIds(ids);
    }

    @Override
    public List<String> queryParamIdListByReport(String id) {
        return dao.queryParamIdListByReport(id);
    }

    @Override
    public Page<AcParam> queryParamPageByReport(String id, Condition condition,
            Sorter sort, Pageable page) {
        return dao.queryParamPageByReport(id, condition, sort, page);
    }
}
