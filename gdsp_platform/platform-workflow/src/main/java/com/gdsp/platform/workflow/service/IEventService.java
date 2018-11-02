package com.gdsp.platform.workflow.service;

import java.util.List;

import com.gdsp.dev.core.model.param.Sorter;
import com.gdsp.dev.core.model.query.Condition;
import com.gdsp.platform.workflow.model.EventVO;

/**
 * 预置事件服务
 * @author sun
 */
public interface IEventService {

    /**
     * 通用集合结果查询方法
     * @param condition 查询条件
     * @param sort 排序规则
     * @return List
     */
    public List<EventVO> queryEventListByCondition(Condition condition, Sorter sort);

    /**
     *根据输入的event与事件名字或事件实事类进行模糊查询
     * @return List
     */
    public List<EventVO> queryEventListByInput(String event);
}
