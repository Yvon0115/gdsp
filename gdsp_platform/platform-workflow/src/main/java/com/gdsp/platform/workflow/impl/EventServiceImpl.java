package com.gdsp.platform.workflow.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gdsp.dev.core.model.param.Sorter;
import com.gdsp.dev.core.model.query.Condition;
import com.gdsp.platform.workflow.dao.IEventDao;
import com.gdsp.platform.workflow.model.EventVO;
import com.gdsp.platform.workflow.service.IEventService;

/**
 * 
 * @author sun
 *
 */
@Service
@Transactional(readOnly = true)
public class EventServiceImpl implements IEventService {

    @Autowired
    private IEventDao eventDao;

    @Override
    public List<EventVO> queryEventListByCondition(Condition condition, Sorter sort) {
        return eventDao.queryEventListByCondition(condition, sort);
    }

    @Override
    public List<EventVO> queryEventListByInput(String event) {
        return eventDao.queryEventListByInput(event);
    }
}
