package com.gdsp.ptbase.appcfg.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gdsp.dev.core.model.param.Sorter;
import com.gdsp.dev.core.model.query.Condition;
import com.gdsp.ptbase.appcfg.dao.IWidgetQueryDao;
import com.gdsp.ptbase.appcfg.model.LayoutColumnVO;
import com.gdsp.ptbase.appcfg.model.PageVO;
import com.gdsp.ptbase.appcfg.service.IWidgetQueryService;

@Service
@Transactional(readOnly = true)
public class WidgetQueryServiceImpl implements IWidgetQueryService {

    @Resource
    private IWidgetQueryDao wqDao;

    @Override
    public List<PageVO> queryOutResByCondition(Condition cond, Sorter sort) {
        return wqDao.queryOutResByCondition(cond, sort);
    }

    @Override
    public List<LayoutColumnVO> getColumnInfoByLayout(String layout_id) {
        return wqDao.getColumnInfoByLayout(layout_id);
    }
}
