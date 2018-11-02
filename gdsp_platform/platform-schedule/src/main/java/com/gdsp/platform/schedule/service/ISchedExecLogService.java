package com.gdsp.platform.schedule.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.gdsp.dev.core.model.param.Sorter;
import com.gdsp.dev.core.model.query.Condition;
import com.gdsp.platform.schedule.model.SchedExecLogVO;

public interface ISchedExecLogService {

    /**
     * 保存任务执行日志
     * @param vo 任务类型
     * @return
     */
    void saveSchedExecLog(SchedExecLogVO vo);

    /**
    * 通用查询方法
    * @param condition 查询条件
    * @param page 分页请求
    * @param sort 排序规则
    * @return 分页结果
    */
    Page<SchedExecLogVO> findSchedExecLogByCondition(Condition condition, Pageable page, Sorter sort);

    /**
     * 删除任务执行日志
     * @param ids 任务执行ids
     */
    void deletes(String... ids);

    /**
    * @Title: logInf
    * (查询消息详情)
    * @param id 主键
    * @return SchedExecLogVO   消息对象
    *     */
    public SchedExecLogVO logInf(String id);
}
