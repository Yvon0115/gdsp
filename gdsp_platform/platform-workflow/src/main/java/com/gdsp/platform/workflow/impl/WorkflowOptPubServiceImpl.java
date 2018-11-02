package com.gdsp.platform.workflow.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gdsp.dev.base.exceptions.BusinessException;
import com.gdsp.platform.workflow.service.IBussinessFormService;
import com.gdsp.platform.workflow.service.INotifyEventDetailService;
import com.gdsp.platform.workflow.service.IProcessService;
import com.gdsp.platform.workflow.service.IWorkflowOptPubService;

/**
 * 对外操作接口实现
 * @author wxl
 *
 */
@Service
public class WorkflowOptPubServiceImpl implements IWorkflowOptPubService{

    @Autowired
    private IProcessService processService;
	@Autowired
	private INotifyEventDetailService notifyEventDetailService;
	@Autowired
	private IBussinessFormService bussinessFormService;
   
	/**
	 * 注册通知url
	 */
	@Override
	public boolean registNotifyEvent(String deploymentCode,String url){

		return notifyEventDetailService.registNotifyEvent(deploymentCode,url);
	}

	/**
	 * 开启流程
	 */
	@Override
	public boolean startProcess(String deploymentCode, String formid, Map<String, Object> variableMap) throws BusinessException {
		
		return processService.startProcess(deploymentCode, formid, variableMap);

	}

	/**
	 * 保存业务formKey
	 */
	@Override
	public boolean saveBusinessForm(String deploymentCode, String formId) {
		try {
			bussinessFormService.saveBusinessForm(deploymentCode,formId);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		
	}

	/**
	 * 保存附件地址
	 */
	@Override
	public boolean saveAttachmentUrl(String deploymentCode, String formId, String url) {
		try {
			bussinessFormService.saveAttachmentUrl(deploymentCode,formId,url);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	

}
