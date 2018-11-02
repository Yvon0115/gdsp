package com.gdsp.platform.workflow.service;

import java.util.List;

import org.activiti.engine.task.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.gdsp.dev.core.model.param.Sorter;
import com.gdsp.dev.core.model.query.Condition;
import com.gdsp.platform.workflow.model.HistoryVO;
import com.gdsp.platform.workflow.model.ProcessHistoryVO;

/**
 * 流程审批历史扩展表服务
 * @author sun
 * 无update()操作
 */
public interface IProcessHistoryService {
	
	 /**
     * 保存流程审批历史扩展
     * @param vo 流程审批历史扩展
     * @return 是否成功
     */
    public boolean saveProcessHistory(ProcessHistoryVO processHistoryVO);

    /**
     * 保存流程审批历史扩展
     * @param vo 流程审批历史扩展
     * @return 是否成功
     */
    public boolean saveProcessHistory(Task task, String userId,String result,String options,String formId);

    /**
     * 删除流程审批历史扩展
     * @param ids 
     */
    public boolean deleteProcessHistory(String... ids);

    /**
     * 根据实例id撤回流程
     * @param taskId
     */
    public void withdrawProcessHistoryByInsId(String... procInsId);

    /**
     * 根据主键查询
     * @return 流程历史对象
     */
    public ProcessHistoryVO findProcessHistoryById(String id);

    /**
     * 通用分页查询方法
     * @param condition 查询条件
     * @param page 分页请求
     * @param sort 排序规则
     * @return 分页结果
     */
    public Page<ProcessHistoryVO> queryProcessHistoryByCondition(Condition condition, Pageable pageable, Sorter sort);

    /**
     * 通用集合结果查询方法
     * @param condition 查询条件
     * @param sort 排序规则
     * @return List
     */
    public List<ProcessHistoryVO> queryProcessHistoryListByCondition(Condition condition, Sorter sort);

    /**
     * 查询所有流程历史(管理员权限)
     * @param condition
     * @param pageable
     * @param sort
     * @return page
     */
    public Page<HistoryVO> queryAllHistoryForAdmin(Condition condition, Pageable pageable, Sorter sort);

    /**
     * 查询单个流程历史详情
     * @param procInsId
     * @param sort
     * @return
     */
    public Page<ProcessHistoryVO> queryProcessHistoryByInsId(String procInsId, Sorter sort, Pageable pageable);
    
    /**
     * 查询所有流程历史(测试)
     */
    public Page<HistoryVO> queryAllHistory();
    
    /**
     * 通过流程实例id查询审批结果
     * @param proInstId
     * @return result(审批结果)
     */
    public int queryApproveResultByInstId(String proInstId);

    /**
     * 根据taskId和proinstId删除历史记录
     * @param taskId
     * @param processInstanceId
     */
	public void deleteProcessHisByTaskIdAndProinstId(String taskId, String processInstanceId);
}
