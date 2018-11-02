package com.gdsp.platform.workflow.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gdsp.dev.core.model.param.Sorter;
import com.gdsp.dev.core.model.query.Condition;
import com.gdsp.platform.workflow.dao.IEventTypeDao;
import com.gdsp.platform.workflow.model.EventTypeVO;
import com.gdsp.platform.workflow.service.IEventTypeService;

/**
 * 
 * @author sun
 *
 */
@Service
@Transactional(readOnly = true)
public class EventTypeServiceImpl implements IEventTypeService {

    @Autowired
    private IEventTypeDao eventTypeDao;

    @Override
    public List<EventTypeVO> queryEventTypeListByCondition(Condition condition, Sorter sort) {
        return eventTypeDao.queryEventTypeListByCondition(condition, sort);
    }
}
