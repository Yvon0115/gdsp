package com.gdsp.platform.workflow.service;

import java.util.List;

import com.gdsp.dev.core.model.param.Sorter;
import com.gdsp.dev.core.model.query.Condition;
import com.gdsp.platform.workflow.model.EventTypeVO;

/**
 * 预置事件类别服务
 * @author sun
 */
public interface IEventTypeService {

    /**
     * 通用集合结果查询方法
     * @param condition 查询条件
     * @param sort 排序规则
     * @return List
     */
    public List<EventTypeVO> queryEventTypeListByCondition(Condition condition, Sorter sort);
}
