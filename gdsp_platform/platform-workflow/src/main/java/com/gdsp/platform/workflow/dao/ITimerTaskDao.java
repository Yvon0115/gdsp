package com.gdsp.platform.workflow.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.gdsp.dev.core.model.param.Sorter;
import com.gdsp.dev.core.model.query.Condition;
import com.gdsp.dev.persist.ext.MBDao;
import com.gdsp.platform.workflow.model.TimerTaskVO;

@MBDao
public interface ITimerTaskDao {

    public boolean saveTimerTask(TimerTaskVO timerTaskVO);

    public boolean deleteTimerTask(String[] ids);

    public boolean updateTimerTask(TimerTaskVO timerTaskVO);

    public TimerTaskVO findTimerTaskById(String id);

    public Page<TimerTaskVO> queryTimerTaskByCondition(Pageable pageable, @Param("condition") Condition condition, @Param("sort") Sorter sort);

    public List<TimerTaskVO> queryTimerTaskListByCondition(@Param("condition") Condition condition, @Param("sort") Sorter sort);

    public boolean deleteTimerTaskByDeploymentId(String delopymentID);

    public List<TimerTaskVO> queryAllTimerTaskList();
}
