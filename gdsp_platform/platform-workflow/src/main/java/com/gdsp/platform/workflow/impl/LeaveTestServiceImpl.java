package com.gdsp.platform.workflow.impl;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gdsp.platform.workflow.dao.ILeaveDao;
import com.gdsp.platform.workflow.model.LeaveVO;
import com.gdsp.platform.workflow.service.ILeaveTestService;

@Service
@Transactional(readOnly = true)
public class LeaveTestServiceImpl implements ILeaveTestService {

    @Autowired
    private ILeaveDao leaveDao;

    @Transactional
    @Override
    public boolean saveLeave(LeaveVO vo) {
        if(null!=vo && StringUtils.isNotEmpty(vo.getId())){
            leaveDao.update(vo);
        }else{
            leaveDao.insert(vo);
        }
        return true;
    }

    @Override
    public LeaveVO load(String id) {
        return leaveDao.load(id);
    }
}
