package com.gdsp.platform.workflow.helper.listener;


import java.io.IOException;
import java.util.List;

import org.activiti.engine.RepositoryService;
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;
import org.activiti.engine.impl.RepositoryServiceImpl;
import org.activiti.engine.repository.ProcessDefinition;
import org.apache.commons.collections4.map.LinkedMap;
import org.apache.http.HttpException;

import com.gdsp.dev.base.utils.web.HttpClientUtils;
import com.gdsp.dev.core.common.AppContext;
import com.gdsp.dev.core.model.query.Condition;
import com.gdsp.platform.workflow.impl.ApprAuthorityServiceImpl;
import com.gdsp.platform.workflow.impl.BussinessFormServiceImpl;
import com.gdsp.platform.workflow.impl.NodeInfoServiceImpl;
import com.gdsp.platform.workflow.impl.NotifyEventDetailServiceImpl;
import com.gdsp.platform.workflow.impl.ProcessHistoryServiceImpl;
import com.gdsp.platform.workflow.model.ApprAuthorityVO;
import com.gdsp.platform.workflow.model.BussinessFormVO;
import com.gdsp.platform.workflow.model.NodeInfoVO;
import com.gdsp.platform.workflow.model.NotifyEventDetailVO;
import com.gdsp.platform.workflow.model.ProcessHistoryVO;
import com.gdsp.platform.workflow.service.IApprAuthorityService;
import com.gdsp.platform.workflow.service.IBussinessFormService;
import com.gdsp.platform.workflow.service.INodeInfoService;
import com.gdsp.platform.workflow.service.INotifyEventDetailService;
import com.gdsp.platform.workflow.service.IProcessHistoryService;
/**
 * 扩展事件对应的监听事件
 * @author wxl
 *
 */
public class NodifyTaskListener implements TaskListener {
	
	
	private static final long serialVersionUID = 4572781504654226752L;
	private transient RepositoryService        repositoryService     = AppContext.lookupBean("repositoryService", RepositoryServiceImpl.class);        //流程资源接口
    protected transient IProcessHistoryService processHistoryService = AppContext.lookupBean("processHistoryService", ProcessHistoryServiceImpl.class);
    private transient INodeInfoService         nodeInfoService       = AppContext.lookupBean("nodeInfoService", NodeInfoServiceImpl.class);
    private transient INotifyEventDetailService notifyEventDetailService= AppContext.lookupBean("notifyEventDetailService", NotifyEventDetailServiceImpl.class);
    private transient IApprAuthorityService apprAuthorityService=AppContext.lookupBean("apprAuthorityService", ApprAuthorityServiceImpl.class);
    private transient IBussinessFormService bussinessFormService	 = AppContext.lookupBean("bussinessFormService", BussinessFormServiceImpl.class);
	@Override
	public void notify(DelegateTask delegateTask) {
		String eventName = delegateTask.getEventName();
		if(EVENTNAME_COMPLETE.equals(eventName)){
			 //获取节点ID
            String actid = delegateTask.getTaskDefinitionKey();
            String proDefid=delegateTask.getProcessDefinitionId();
            String processInstanceId = delegateTask.getProcessInstanceId();
            Condition cond1 = new Condition();
            cond1.addExpression("ACTID", actid);
            
            //获取nodeinfoId
            ProcessDefinition pd = repositoryService.getProcessDefinition(proDefid);
            cond1.addExpression("PROCDEFKEY", pd.getKey());
            List<NodeInfoVO> nodeInfoVOs = nodeInfoService.queryNodeInfoListByCondition(cond1, null);
            //nodeInfoVos唯一？
//            if(nodeInfoVOs.size()==1){
//            	String ndinfoId=nodeInfoVOs.get(0).getId();
//            }
            String nfId=nodeInfoVOs.get(0).getId();
            //通过nodeinfoId查询通知url
            //唯一吗？？
            List<NotifyEventDetailVO> notifyEventDetailVOs= notifyEventDetailService.queryNotifyEventDetailByNodeinfoId(nfId);
            //根据proinstid 和actid 查询审批结果
            Condition cond2 = new Condition();
            cond2.addExpression("ACTID", actid);
            cond2.addExpression("PROCESSINSID", processInstanceId);
            List<ProcessHistoryVO> historyList = processHistoryService.queryProcessHistoryListByCondition(cond2, null);
//            if(historyList.size()==1){
//            	String res=historyList.get(0).getResult();
//            }
            //historyList唯一？
            if(0==historyList.size()){
            	return ;
            }
            String res=historyList.get(0).getResult();
            String option=historyList.get(0).getOptions();
            if (Integer.parseInt(res) == 1) {
            	res="审批通过";
            } else if (Integer.parseInt(res) == 2) {
            	res="审批不通过";
            } else if (Integer.parseInt(res) == 3) {
            	res="驳回到发起人";
            } else if (Integer.parseInt(res) == 4) {
            	res="驳回上一级";
            } else if (Integer.parseInt(res) == 5) {
            	res="发起流程";
            }

            //查询业务key:formId
            BussinessFormVO bfVO=bussinessFormService.queryFormIdByInstId(processInstanceId);
            
            LinkedMap<String, Object> map=new LinkedMap<>();
            Condition cond3=new Condition();
            cond3.addExpression("NODEINFOID", nfId);
            List<ApprAuthorityVO> ApprAuthorityVO = apprAuthorityService.queryApprAuthorityListByCondition(cond3, null);
            map.put("taskId", historyList.get(0).getTaskId());
            map.put("apprName", ApprAuthorityVO.get(0).getApprTypeName());
            map.put("result", res);
            map.put("formId", bfVO.getFormId());
            map.put("option", option);
            	for (NotifyEventDetailVO notifyEventDetailVO : notifyEventDetailVOs) {
            		try {
						HttpClientUtils.httpGetText(notifyEventDetailVO.getNotifyUrl(), map);
					} catch (HttpException | IOException e) {
						e.printStackTrace();
						continue;// TODO    日志
					}
				}
				
		}
//		System.out.println("获取监听");
		
	}

//			url="http://localhost:7777/ssm/request/updateRequests.d";
//			org.apache.commons.collections4.map.LinkedMap<String, Object> queryParams = new org.apache.commons.collections4.map.LinkedMap<>();
//			queryParams.put("isComplete", "Y");
//			String reqResult = HttpClientUtils.httpGetText(url, queryParams);
//			System.out.println("a");
//			System.err.println(reqResult);


}
