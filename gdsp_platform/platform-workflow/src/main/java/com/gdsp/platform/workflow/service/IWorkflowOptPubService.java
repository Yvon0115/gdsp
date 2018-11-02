package com.gdsp.platform.workflow.service;

import java.util.Map;

import com.gdsp.dev.base.exceptions.BusinessException;

/**
 * 工作流相关操作公共接口
 * @author wxl
 * @date 2017年12月7日 上午11:41:19
 */
public interface IWorkflowOptPubService {
	
	/**
	 * 注册通知url
	 * @param deploymentCode 流程编码
	 * @param url  通知url
	 * @return boolean 是否注册成功
	 */
	boolean registNotifyEvent(String deploymentCode, String url);
	
	/**
	 * 保存业务formKey
	 * @param deploymentCode 流程编码
	 * @param formId  表单id
	 * @return boolean 是否保存成功
	 */
	boolean saveBusinessForm(String deploymentCode, String formId);

	/**
	 * 保存附件地址
	 * @param deploymentCode 流程编码
	 * @param formId 表单id
	 * @param url 通知url
	 * @return boolean 是否保存成功
	 */
	boolean saveAttachmentUrl(String deploymentCode, String formId,String url);

	/**
	 * 开启流程
	 * @param deploymentCode 流程编码
	 * @param formid 表单id
	 * @param variableMap 
	 * @return boolean 是否开启成功
	 * @throws BusinessException
	 */
	boolean startProcess(String deploymentCode, String formid, Map<String, Object> variableMap) throws BusinessException;
}
