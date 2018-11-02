package com.gdsp.platform.workflow.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.gdsp.dev.core.model.param.Sorter;
import com.gdsp.dev.core.model.query.Condition;
import com.gdsp.platform.workflow.model.TimerTaskVO;

public interface ITimerTaskService {

    /**
     * 保存定时任务
     * @param timerTaskVO 定时任务
     * @return 是否生效
     */
    public boolean saveTimerTask(TimerTaskVO timerTaskVO);

    /**
     * 删除定时任务
     * @param ids 主键
     * @return 是否生效
     */
    public boolean deleteTimerTask(String... ids);

    /**
     * 根据主键查询定时任务
     * @param id 
     * @return 定时任务
     */
    public TimerTaskVO findTimerTaskById(String id);

    /**
     * 通用分页查询（定时任务）
     * @param condition 查询条件
     * @param pageable	分页请求
     * @param sort		排序规则
     */
    public Page<TimerTaskVO> queryTimerTaskByCondition(Condition condition, Pageable pageable, Sorter sort);

    /**
     * 通用集合查询（定时任务）
     * @param condition 查询条件
     * @param sort		排序规则
     * @return 列表(List)
     */
    public List<TimerTaskVO> queryTimerTaskListByCondition(Condition condition, Sorter sort);

    /**
     * 删除定时任务
     * @param delopymentID 流程部署id
     * @return 是否生效
     */
    public boolean deleteTimerTaskByDeploymentId(String delopymentID);

    /**
     * 查询所有的当前任务相关的定时任务
     * @return
     */
    public List<TimerTaskVO> queryAllTimerTaskList();
}
