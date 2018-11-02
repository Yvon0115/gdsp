package com.gdsp.platform.workflow.service;

import com.gdsp.platform.workflow.model.LeaveVO;

public interface ILeaveTestService {

    public boolean saveLeave(LeaveVO vo);

    /**
     * 根据主键查询请假单
     */
    public LeaveVO load(String id);
}
