package com.gdsp.platform.workflow.helper.listener;

import org.activiti.engine.delegate.event.ActivitiEvent;
import org.activiti.engine.delegate.event.ActivitiEventListener;
import org.activiti.engine.delegate.event.ActivitiEventType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gdsp.dev.core.common.AppContext;
import com.gdsp.dev.core.model.query.Condition;
import com.gdsp.platform.comm.message.impl.MessageServiceImpl;
import com.gdsp.platform.comm.message.service.IMessageService;
import com.gdsp.platform.workflow.impl.ProcessHistoryServiceImpl;
import com.gdsp.platform.workflow.service.IProcessHistoryService;

public class ProcessEndListener implements ActivitiEventListener{

	private static final Logger logger = LoggerFactory.getLogger(ProcessEndListener.class);
	
	@Override
	public void onEvent(ActivitiEvent event) {
		ActivitiEventType aetype = event.getType();
		if((ActivitiEventType.PROCESS_COMPLETED.name()).equals(aetype.name())){
			IProcessHistoryService processHistoryService= AppContext.lookupBean("processHistoryService", ProcessHistoryServiceImpl.class); 
			IMessageService messageService = AppContext.lookupBean("messageService", MessageServiceImpl.class);
			try {
				//流程实例id
				String processInstanceId = event.getProcessInstanceId();
				Condition cond = new Condition();
				cond.addExpression("ACTID", "startuser");
		        cond.addExpression("PROCESSINSID", processInstanceId);
		        String touserid = processHistoryService.queryProcessHistoryListByCondition(cond,null).get(0).getCreateBy();
		        messageService.senderMessage(touserid, "", "", "系统", "流程结束通知", "您的流程已经办理结束！");
		        
			} catch (Exception e) {
				e.printStackTrace();
				logger.error(e.getMessage(),e);
			}
		}
	}
			
	@Override
	public boolean isFailOnException() {
		return false;
	}



	
}
