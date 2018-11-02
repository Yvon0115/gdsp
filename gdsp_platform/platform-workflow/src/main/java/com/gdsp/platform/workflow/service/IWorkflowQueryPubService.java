package com.gdsp.platform.workflow.service;

import java.util.List;

import com.gdsp.platform.workflow.model.ProcessHistoryVO;

public interface IWorkflowQueryPubService {

	/**
	 * 根据formId查询审批记录
	 * @param formId
	 * @return
	 */
	List<ProcessHistoryVO> queryApproveInfoByFormId(String formId);
	
	/**
	 * 根据taskId查询下一节点
	 * @param taskId
	 * @return
	 */
	Boolean findNextTask(String taskId);
}
