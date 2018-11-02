package com.gdsp.platform.workflow.service;

import com.gdsp.platform.workflow.model.BussinessFormVO;

public interface IBussinessFormService {
	
	/** 未开始 */
	public static final int PROCESS_STATUS_NOSTARTED=0;
	/** 审批中 */
	public static final int PROCESS_STATUS_PROCESSING=1;
	/** 已结束 */
	public static final int PROCESS_STATUS_END=2;

	/**
	 * 添加表单
	 */
	void save(BussinessFormVO bussinessFormVO);
	
	/**
	 * 根据formid查询表单
	 */
	public BussinessFormVO queryFormVariableValueByFormId(String formId);

	/**
	 * 保存formId以及deploymentId
	 * @param deploymentCode
	 * @param formId
	 */
	void saveBusinessForm(String deploymentCode, String formId);

//	/**
//	 * 查询是否已存在deploymentId、formid
//	 * @param deploymentId
//	 * @param formid
//	 * @return boolean
//	 */
//	boolean isExistFormId(String deploymentId, String formid);

	/**
	 * 更新表单
	 * @param formVariableValueVO
	 */
	void update(BussinessFormVO bussinessFormVO);

	/**
	 * 根据formi与deploymentid查询表单
	 */
	BussinessFormVO queryFormVariableValuerByDepidAndFormid(String deploymentId, String formid);

	/**
	 * 保存附件url
	 */
	void saveAttachmentUrl(String deploymentCode, String formid, String url);

	

	/**
	 * 
	 * @param formId
	 * @param processStatusEnd
	 */
	void updateBusinessStatus(String formId, int status);

	/**
	 * 根据proinstId查询对应formId
	 * @param processInstanceId
	 * @return
	 */
	BussinessFormVO queryFormIdByInstId(String processInstanceId);

}
