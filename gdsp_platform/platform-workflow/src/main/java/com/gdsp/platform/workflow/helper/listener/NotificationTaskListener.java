package com.gdsp.platform.workflow.helper.listener;

import java.util.List;

import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;

import com.gdsp.dev.core.common.AppContext;
import com.gdsp.dev.core.model.query.Condition;
import com.gdsp.platform.comm.message.impl.MessageServiceImpl;
import com.gdsp.platform.comm.message.service.IMessageService;
import com.gdsp.platform.workflow.impl.ApprAuthorityServiceImpl;
import com.gdsp.platform.workflow.impl.DeploymentServiceImpl;
import com.gdsp.platform.workflow.impl.NodeInfoServiceImpl;
import com.gdsp.platform.workflow.impl.ProcessHistoryServiceImpl;
import com.gdsp.platform.workflow.model.ApprAuthorityVO;
import com.gdsp.platform.workflow.model.ProcessHistoryVO;
import com.gdsp.platform.workflow.service.IApprAuthorityService;
import com.gdsp.platform.workflow.service.IDeploymentService;
import com.gdsp.platform.workflow.service.INodeInfoService;
import com.gdsp.platform.workflow.service.IProcessHistoryService;

/**
 * 流程节点消息通知
 * @author lt
 * @date 2018年1月26日 下午3:14:06
 */
public class NotificationTaskListener implements TaskListener{
	
	private static final long serialVersionUID = 8452065858763043079L;
	
	private transient IMessageService messageService = AppContext.lookupBean("messageService", MessageServiceImpl.class);
	private transient INodeInfoService nodeInfoService = AppContext.lookupBean("nodeInfoService", NodeInfoServiceImpl.class);
	private transient IDeploymentService deploymentService = AppContext.lookupBean("deploymentService", DeploymentServiceImpl.class);
	private transient IApprAuthorityService apprAuthorityService = AppContext.lookupBean("apprAuthorityService", ApprAuthorityServiceImpl.class);
	private transient IProcessHistoryService processHistoryService= AppContext.lookupBean("processHistoryService", ProcessHistoryServiceImpl.class); 
	
	@Override
	public void notify(DelegateTask delegateTask) {
		String eventName = delegateTask.getEventName();
		if(EVENTNAME_CREATE.equals(eventName)){
			String taskName = delegateTask.getName();
			if(!taskName.equals("发起人")){
				//流程定义id
				String proDefid = delegateTask.getProcessDefinitionId();
				//流程部署id
				String deploymentid = deploymentService.findByProcDefId(proDefid).getId();
				//根据活动节点id和部署id获取nodeInfoid
				String actid = delegateTask.getTaskDefinitionKey();
				Condition cond = new Condition();
				cond.addExpression("ACTID", actid);
				cond.addExpression("DEPLOYMENTID", deploymentid);
				String nodeInfoid = nodeInfoService.queryNodeInfoListByCondition(cond, null).get(0).getId();
				Condition cond1 = new Condition();
				cond1.addExpression("NODEINFOID", nodeInfoid);
				List<ApprAuthorityVO> appr = apprAuthorityService.queryApprAuthorityListByCondition(cond1, null);
				for (ApprAuthorityVO id : appr) {
					String touserid = id.getApprTypeId();
					messageService.senderMessage(touserid, "", "", "系统", "流程待办通知", "您有一个待办流程尚未处理，请您尽快处理！");
					}
			}else{
				String processInsId = delegateTask.getProcessInstanceId();
				Condition condition = new Condition();
				condition.addExpression("ACTID", "startuser");
				condition.addExpression("PROCESSINSID", processInsId);
				List<ProcessHistoryVO> prohis = processHistoryService.queryProcessHistoryListByCondition(condition,null);
				if(prohis.size()>0){//驳回发起人节点
					String touserid = prohis.get(0).getUserId();
					messageService.senderMessage(touserid, "", "", "系统", "流程驳回通知", "您的流程已被驳回，请重新发起流程！");
					}
				}
			}
		}
	}