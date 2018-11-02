package com.gdsp.platform.workflow.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gdsp.dev.core.model.param.Sorter;
import com.gdsp.dev.core.model.query.Condition;
import com.gdsp.platform.workflow.dao.ITimerTaskDao;
import com.gdsp.platform.workflow.model.TimerTaskVO;
import com.gdsp.platform.workflow.service.ITimerTaskService;

@Service
@Transactional(readOnly = true)
public class TimerTaskServiceImpl implements ITimerTaskService {

    @Autowired
    private ITimerTaskDao timerTaskDao;

    @Transactional
    @Override
    public boolean saveTimerTask(TimerTaskVO timerTaskVO) {
        
        if (StringUtils.isNotEmpty(timerTaskVO.getId())) {
            return timerTaskDao.updateTimerTask(timerTaskVO);
        } else {
            return timerTaskDao.saveTimerTask(timerTaskVO);
        }
    }

    @Transactional
    @Override
    public boolean deleteTimerTask(String... ids) {
        
        return timerTaskDao.deleteTimerTask(ids);
    }

    @Override
    public TimerTaskVO findTimerTaskById(String id) {
        
        return timerTaskDao.findTimerTaskById(id);
    }

    @Override
    public Page<TimerTaskVO> queryTimerTaskByCondition(Condition condition,
            Pageable pageable, Sorter sort) {
        
        return timerTaskDao.queryTimerTaskByCondition(pageable, condition, sort);
    }

    @Override
    public List<TimerTaskVO> queryTimerTaskListByCondition(Condition condition,
            Sorter sort) {
        
        return timerTaskDao.queryTimerTaskListByCondition(condition, sort);
    }

    @Override
    @Transactional
    public boolean deleteTimerTaskByDeploymentId(String delopymentID) {
        
        return timerTaskDao.deleteTimerTaskByDeploymentId(delopymentID);
    }

    @Override
    public List<TimerTaskVO> queryAllTimerTaskList() {
        
        return timerTaskDao.queryAllTimerTaskList();
    }
}
