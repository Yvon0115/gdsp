package com.gdsp.platform.workflow.service;

import java.util.List;

import com.gdsp.dev.core.model.param.Sorter;
import com.gdsp.dev.core.model.query.Condition;
import com.gdsp.platform.workflow.model.FormVariableVO;

/**
 * 预置单据内变量信息服务
 * @author sun
 */
public interface IFormVariableService {

    /**
     * 通用集合结果查询方法
     * @param condition 查询条件
     * @param sort 排序规则
     * @return List
     */
    public List<FormVariableVO> queryFormVariableListByCondition(Condition condition, Sorter sort);
}
