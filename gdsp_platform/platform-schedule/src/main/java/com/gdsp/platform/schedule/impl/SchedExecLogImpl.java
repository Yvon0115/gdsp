package com.gdsp.platform.schedule.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gdsp.dev.core.model.param.Sorter;
import com.gdsp.dev.core.model.query.Condition;
import com.gdsp.platform.schedule.dao.ISchedExecLogDao;
import com.gdsp.platform.schedule.model.SchedExecLogVO;
import com.gdsp.platform.schedule.service.ISchedExecLogService;

@Service
@Transactional(readOnly = true)
public class SchedExecLogImpl implements ISchedExecLogService {

    @Autowired
    private ISchedExecLogDao execLogDao;

    @Transactional
    @Override
    public void saveSchedExecLog(SchedExecLogVO vo) {
        execLogDao.insertSchedExecLog(vo);
    }

    @Override
    public Page<SchedExecLogVO> findSchedExecLogByCondition(Condition condition, Pageable page, Sorter sort) {
        return execLogDao.findSchedExecLogByCondition(condition, sort, page);
    }

    @Transactional
    @Override
    public void deletes(String... ids) {
        execLogDao.deleteSchedExecLog(ids);
    }

    @Override
    public SchedExecLogVO logInf(String id) {
        return execLogDao.load(id);
    }
}
