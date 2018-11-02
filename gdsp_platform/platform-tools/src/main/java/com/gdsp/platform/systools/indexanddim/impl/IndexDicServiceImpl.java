package com.gdsp.platform.systools.indexanddim.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gdsp.dev.core.model.param.Sorter;
import com.gdsp.dev.core.model.query.Condition;
import com.gdsp.dev.persist.result.MapListResultHandler;
import com.gdsp.platform.systools.indexanddim.dao.IndexDicDao;
import com.gdsp.platform.systools.indexanddim.model.IndexDicVO;
import com.gdsp.platform.systools.indexanddim.service.IIndexDicService;

@Service
@Transactional(readOnly = true)
public class IndexDicServiceImpl implements IIndexDicService {

    @Resource
    private IndexDicDao indexDicDao;

    @Override
    public void queryAllByHandler(MapListResultHandler<IndexDicVO> map,
            Condition cond, Sorter sort) {
        indexDicDao.queryAllByHandler(map, cond, sort);
    }
}
