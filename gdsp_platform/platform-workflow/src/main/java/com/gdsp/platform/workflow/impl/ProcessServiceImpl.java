package com.gdsp.platform.workflow.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.activiti.bpmn.model.BpmnModel;
import org.activiti.bpmn.model.FlowNode;
import org.activiti.bpmn.model.SequenceFlow;
import org.activiti.engine.HistoryService;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.history.HistoricVariableInstance;
import org.activiti.engine.impl.RepositoryServiceImpl;
import org.activiti.engine.impl.bpmn.behavior.UserTaskActivityBehavior;
import org.activiti.engine.impl.javax.el.ExpressionFactory;
import org.activiti.engine.impl.juel.ExpressionFactoryImpl;
import org.activiti.engine.impl.juel.SimpleContext;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.persistence.entity.TaskEntity;
import org.activiti.engine.impl.pvm.PvmActivity;
import org.activiti.engine.impl.pvm.PvmTransition;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.impl.pvm.process.ProcessDefinitionImpl;
import org.activiti.engine.impl.pvm.process.TransitionImpl;
import org.activiti.engine.impl.task.TaskDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.IdentityLink;
import org.activiti.engine.task.Task;
import org.activiti.spring.ProcessEngineFactoryBean;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.gdsp.dev.base.exceptions.BusinessException;
import com.gdsp.dev.base.lang.DDate;
import com.gdsp.dev.base.utils.UUIDUtils;
import com.gdsp.dev.core.model.query.Condition;
import com.gdsp.platform.grant.auth.service.IUserGroupRltService;
import com.gdsp.platform.grant.auth.service.IUserRoleQueryPubService;
import com.gdsp.platform.grant.user.model.UserVO;
import com.gdsp.platform.grant.user.service.IUserQueryPubService;
import com.gdsp.platform.comm.message.service.IMessageService;
import com.gdsp.platform.workflow.model.ApprAuthorityVO;
import com.gdsp.platform.workflow.model.BussinessFormVO;
import com.gdsp.platform.workflow.model.DelegateVO;
import com.gdsp.platform.workflow.model.DeploymentVO;
import com.gdsp.platform.workflow.model.EventVO;
import com.gdsp.platform.workflow.model.FormProcinstRltVO;
import com.gdsp.platform.workflow.model.FormVariableVO;
import com.gdsp.platform.workflow.model.NodeInfoVO;
import com.gdsp.platform.workflow.model.ProcessHistoryVO;
import com.gdsp.platform.workflow.service.IApprAuthorityService;
import com.gdsp.platform.workflow.service.IBussinessFormService;
import com.gdsp.platform.workflow.service.ICategoryService;
import com.gdsp.platform.workflow.service.IDelegateService;
import com.gdsp.platform.workflow.service.IDeploymentService;
import com.gdsp.platform.workflow.service.IEventService;
import com.gdsp.platform.workflow.service.IExtendEventService;
import com.gdsp.platform.workflow.service.IFormProcinstRltService;
import com.gdsp.platform.workflow.service.IFormVariableService;
import com.gdsp.platform.workflow.service.INodeInfoService;
import com.gdsp.platform.workflow.service.IProcessHistoryService;
import com.gdsp.platform.workflow.service.IProcessService;
import com.gdsp.platform.workflow.service.ITimerTaskService;
import com.gdsp.platform.workflow.service.IVariableService;
import com.gdsp.platform.workflow.utils.TemplateEmail;
import com.gdsp.platform.workflow.utils.UserUtils;
import com.gdsp.platform.workflow.utils.WorkflowConst;

@Service
public class ProcessServiceImpl implements IProcessService {
	private static final Logger logger = LoggerFactory.getLogger(ProcessServiceImpl.class);
    @Autowired
    protected RepositoryService      repositoryService;
    @Autowired
    protected RuntimeService         runtimeService;
    @Autowired
    protected TaskService            taskService;
    @Autowired
    protected HistoryService         historyService;
    @Autowired
    ProcessEngineConfiguration       processEngineConfiguration;
    @Autowired
    ProcessEngineFactoryBean         processEngine;
    @Autowired
    protected TemplateEmail          templateEmail;
    @Autowired
    protected IProcessHistoryService processHistoryService;
    @Autowired
    protected INodeInfoService       nodeInfoService;
    @Autowired
    protected ICategoryService       categoryService;
    @Autowired
    private IFormVariableService     formVariableService;
    @Autowired
    private IDeploymentService       deploymentService;
    @Autowired
    private IApprAuthorityService    apprAuthorityService;
    @Autowired
    private IDelegateService         delegateService;
    @Autowired
    ITimerTaskService                timerTaskService;
    @Autowired
    IEventService                    eventService;
    @Autowired
    private IUserGroupRltService     userGroupRltService;
    @Autowired
    IUserQueryPubService             userPubService;
    @Autowired
    private IUserRoleQueryPubService userRoleQueryPubService;
    @Autowired
    IVariableService                 variableService;
    @Autowired
    private IBussinessFormService bussinessFormService;
    @Autowired
    private IFormProcinstRltService formProcinstRltService;
    @Autowired
    private IMessageService messageService;

    @Override
    public boolean start(String deploymentId, String formid, Map<String, Object> variableMap) throws BusinessException {
        String processDefinitionKey = repositoryService.createProcessDefinitionQuery()
                .processDefinitionId(deploymentService.findDeploymentById(deploymentId).getProcDefId())
                .singleResult().getKey();
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(processDefinitionKey);
        //Map<String,Object> map = new HashMap<String,Object>();
        Task task = this.taskService.createTaskQuery().processInstanceId(processInstance.getId()).singleResult();
        //设置第一个节点的发起人为当前用户
        taskService.setAssignee(task.getId(), UserUtils.getCurrentUserID());
      
        String params=JSONObject.fromObject(variableMap).toString();
        String procinstid=processInstance.getProcessInstanceId();
        BussinessFormVO fvbVO=bussinessFormService.queryFormVariableValuerByDepidAndFormid(deploymentId,formid);
        
        if(null !=fvbVO){
        	fvbVO.setDeploymentId(deploymentId);
        	fvbVO.setFormId(formid);
        	fvbVO.setParams(params);
        	fvbVO.setStatus(IBussinessFormService.PROCESS_STATUS_PROCESSING);
        	bussinessFormService.update(fvbVO);
        	
            int maxVersion=formProcinstRltService.queryMaxVersion();
            FormProcinstRltVO fp=new FormProcinstRltVO();
            fp.setProceinstId(procinstid);
            fp.setPk_businessformid(fvbVO.getId());
            fp.setVersion(maxVersion+1);
            fp.setId(UUIDUtils.getRandomUUID());
            formProcinstRltService.save(fp);
        	
        }else{
        	fvbVO=new BussinessFormVO();
        	fvbVO.setDeploymentId(deploymentId);
        	fvbVO.setFormId(formid);
        	fvbVO.setParams(params);
        	fvbVO.setStatus(IBussinessFormService.PROCESS_STATUS_PROCESSING);
        	bussinessFormService.save(fvbVO);
        	
        	BussinessFormVO newfvbVO=bussinessFormService.queryFormVariableValuerByDepidAndFormid(deploymentId,formid);
            int maxVersion=formProcinstRltService.queryMaxVersion();
            FormProcinstRltVO fp=new FormProcinstRltVO();
            fp.setProceinstId(procinstid);
            fp.setPk_businessformid(newfvbVO.getId());
            fp.setVersion(maxVersion+1);
            fp.setId(UUIDUtils.getRandomUUID());
            formProcinstRltService.save(fp);
        }
        //查询出当前节点所需要的流程变量并设置到Map中
        /*Condition condition = new Condition();
        //condition.addExpression("fromTypeId","");
        List<FormVariableVO> formVariableList = formVariableService.queryFormVariableListByCondition(condition, null);
        String[] varaibleName= new String[formVariableList.size()];
        String formName=null;
        for (int i = 0; i < formVariableList.size(); i++) {
        FormVariableVO formVariableVO = formVariableList.get(i);
        varaibleName[i]=formVariableVO.getVariableName();
        //变量值FormValue需要从表单获取       
        //map.put(formVariableVO.getVariableName(), "FormValue");
        }
        map = variableService.getFormVariable(formName, formid, varaibleName);*/
        //如果下一节点是会签节点,封装会签变量信息
        Map<String, Object> multiVari = procNextMultiIns(deploymentId, task, variableMap);
        //办理任务 默认发起跳过第一个自定义的发起人节点 如果是会签节点传入会签变量
        taskService.complete(task.getId(), multiVari);
        //获取任务当前流程的所有任务
        List<Task> taskList = this.taskService.createTaskQuery().processInstanceId(task.getProcessInstanceId()).list();
        //如果开始节点后就有分支需要循环设置下几个节点的执行人(会签节点除外)
        if (taskList.size() > 0) {
            for (int i = 0; i < taskList.size(); i++) {
                //如果办理前的节点id和办理后的节点id一样说明是会签节点，则不需要进行消息提醒，只有当前节点通过后才进行消息提醒和办理人处理
                if (!task.getTaskDefinitionKey().equals(taskList.get(i).getTaskDefinitionKey())) {
                    //消息提醒及办理人处理
                    processTask(taskList.get(i), deploymentId);
                }
            }
        }
        //保存发起节点的流程历史  节点信息比较特殊单独保存处理
        ProcessHistoryVO processHistoryVO = new ProcessHistoryVO();
        processHistoryVO.setUserId(UserUtils.getCurrentUserID());
        processHistoryVO.setDeploymentId(deploymentService.findByProcDefId(task.getProcessDefinitionId()).getId());
        processHistoryVO.setProcessInsId(processInstance.getProcessInstanceId());
        processHistoryVO.setActId("startuser");
        processHistoryVO.setActName("发起节点");
        processHistoryVO.setResult("5");
        processHistoryVO.setTaskId(task.getId());
        processHistoryVO.setFormId(formid);
        processHistoryService.saveProcessHistory(processHistoryVO);
        return true;
    }

    
    @Override
	public boolean startProcess(String deploymentCode, String formid, Map<String, Object> variableMap) throws BusinessException {
       
    	//根据流程Code查询流程Id;
    	Condition cond1=new Condition();
    	cond1.addExpression("DEPLOYMENTCODE", deploymentCode);
    	List<DeploymentVO> deVOs = deploymentService.queryDeploymentListByCondition(cond1, null);
    	DeploymentVO dVO=deVOs.get(0);
    	String deploymentId=dVO.getId();
    	//获取注册参数
//		Condition condition=new Condition();
//		condition.addExpression("id", deploymentId);
//		List<DeploymentVO> deVOs =deploymentService.queryDeploymentListByCondition(condition, null);
//		DeploymentVO dVO=deVOs.get(0);
		Condition cond=new Condition();
		cond.addExpression("formtypeid", dVO.getFormTypeId());
		List<FormVariableVO> formVariableVOs = formVariableService.queryFormVariableListByCondition(cond, null);
		Map<String, Object> actiParamMap=new HashMap<>();
		for (FormVariableVO formVariableVO : formVariableVOs) {
			if(variableMap.containsKey(formVariableVO.getVariableName())){
				actiParamMap.put(formVariableVO.getVariableName(), variableMap.get(formVariableVO.getVariableName()));
			}else{
				return false;
			}
		}
		
    	String processDefinitionKey = repositoryService.createProcessDefinitionQuery()
                .processDefinitionId(deploymentService.findDeploymentById(deploymentId).getProcDefId())
                .singleResult().getKey();
    	String params=JSONObject.fromObject(variableMap).toString();
    	
    	ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(processDefinitionKey);
        String procinstid=processInstance.getProcessInstanceId();
        //判断是否存在deploymentCode，formid
        //存在做修改，不存在 直接添加
        BussinessFormVO fvbVO=bussinessFormService.queryFormVariableValuerByDepidAndFormid(deploymentId,formid);
        
        if(null !=fvbVO){
        	fvbVO.setDeploymentId(deploymentId);
        	fvbVO.setFormId(formid);
        	fvbVO.setParams(params);
        	fvbVO.setStatus(IBussinessFormService.PROCESS_STATUS_PROCESSING);
        	bussinessFormService.update(fvbVO);
        	
            int maxVersion=formProcinstRltService.queryMaxVersion();
            FormProcinstRltVO fp=new FormProcinstRltVO();
            fp.setProceinstId(procinstid);
            fp.setPk_businessformid(fvbVO.getId());
            fp.setVersion(maxVersion+1);
            fp.setId(UUIDUtils.getRandomUUID());
            formProcinstRltService.save(fp);
        	
        }else{
        	fvbVO=new BussinessFormVO();
        	fvbVO.setDeploymentId(deploymentId);
        	fvbVO.setFormId(formid);
        	fvbVO.setParams(params);
        	fvbVO.setStatus(IBussinessFormService.PROCESS_STATUS_PROCESSING);
        	bussinessFormService.save(fvbVO);
        	
        	BussinessFormVO newfvbVO=bussinessFormService.queryFormVariableValuerByDepidAndFormid(deploymentId,formid);
            int maxVersion=formProcinstRltService.queryMaxVersion();
            FormProcinstRltVO fp=new FormProcinstRltVO();
            fp.setProceinstId(procinstid);
            fp.setPk_businessformid(newfvbVO.getId());
            fp.setVersion(maxVersion+1);
            fp.setId(UUIDUtils.getRandomUUID());
            formProcinstRltService.save(fp);
        }
        
//        BussinessFormVO newfvbVO=bussinessFormService.queryFormVariableValuerByDepidAndFormid(deploymentId,formid);
//        int maxVersion=formProcinstRltService.queryMaxVersion();
//        FormProcinstRltVO fp=new FormProcinstRltVO();
//        fp.setProceinstId(procinstid);
//        fp.setPk_businessformid(fvbVO.getId());
//        fp.setVersion(maxVersion+1);
//        fp.setId(UUIDUtils.getRandomUUID());
//        if(0==maxVersion){
//        	fp.setVersion(1);
//        }else{
//        	fp.setVersion(maxVersion+1);
//        }
//        formProcinstRltService.save(fp);
        
        //Map<String,Object> map = new HashMap<String,Object>();
        Task task = this.taskService.createTaskQuery().processInstanceId(processInstance.getId()).singleResult();
        //设置第一个节点的发起人为当前用户
        taskService.setAssignee(task.getId(), UserUtils.getCurrentUserID());
        //查询出当前节点所需要的流程变量并设置到Map中
        /*Condition condition = new Condition();
        //condition.addExpression("fromTypeId","");
        List<FormVariableVO> formVariableList = formVariableService.queryFormVariableListByCondition(condition, null);
        String[] varaibleName= new String[formVariableList.size()];
        String formName=null;
        for (int i = 0; i < formVariableList.size(); i++) {
        FormVariableVO formVariableVO = formVariableList.get(i);
        varaibleName[i]=formVariableVO.getVariableName();
        //变量值FormValue需要从表单获取       
        //map.put(formVariableVO.getVariableName(), "FormValue");
        }
        map = variableService.getFormVariable(formName, formid, varaibleName);*/
        //如果下一节点是会签节点,封装会签变量信息
        Map<String, Object> multiVari = procNextMultiIns(deploymentId, task, actiParamMap);
        //办理任务 默认发起跳过第一个自定义的发起人节点 如果是会签节点传入会签变量
        taskService.complete(task.getId(), multiVari);
        //获取任务当前流程的所有任务
        List<Task> taskList = this.taskService.createTaskQuery().processInstanceId(task.getProcessInstanceId()).list();
        //如果开始节点后就有分支需要循环设置下几个节点的执行人(会签节点除外)
        if (taskList.size() > 0) {
            for (int i = 0; i < taskList.size(); i++) {
                //如果办理前的节点id和办理后的节点id一样说明是会签节点，则不需要进行消息提醒，只有当前节点通过后才进行消息提醒和办理人处理
                if (!task.getTaskDefinitionKey().equals(taskList.get(i).getTaskDefinitionKey())) {
                    //消息提醒及办理人处理
                    processTask(taskList.get(i), deploymentId);
                }
            }
        }
        //保存发起节点的流程历史  节点信息比较特殊单独保存处理
        ProcessHistoryVO processHistoryVO = new ProcessHistoryVO();
        processHistoryVO.setUserId(UserUtils.getCurrentUserID());
        processHistoryVO.setDeploymentId(deploymentService.findByProcDefId(task.getProcessDefinitionId()).getId());
        processHistoryVO.setProcessInsId(processInstance.getProcessInstanceId());
        processHistoryVO.setActId("startuser");
        processHistoryVO.setActName("发起节点");
        processHistoryVO.setResult("5");
        processHistoryVO.setTaskId(task.getId());
        processHistoryVO.setFormId(formid);
        processHistoryService.saveProcessHistory(processHistoryVO);
        return true;
    }
    
    
    
    /**
     * 办理任务
     * @throws Exception 
     */
    public boolean complete(String taskId, String result, String options, String formId) throws BusinessException {
    	String currentUserID = UserUtils.getCurrentUserID();
        //查询当前任务
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        //发起人节点直接通过
        if ("startuser".equals(task.getTaskDefinitionKey()) && WorkflowConst.FlowResult.AGREE == Integer.parseInt(result)) {
            result = "5";
        }
        Map<String, Object> map = new HashMap<String, Object>();
        //判断当前节点是否是会签节点
        Condition con2 = new Condition();
        con2.addExpression("actId", task.getTaskDefinitionKey());
        con2.addExpression("deploymentId", deploymentService.findByProcDefId(task.getProcessDefinitionId()).getId());
        List<NodeInfoVO> nodeList = nodeInfoService.queryNodeInfoListByCondition(con2, null);
        int multiInsValues = nodeList.get(0).getMultiInsValue();
        //如果是会签节点不允许驳回发起人和驳回上一节点
        if (multiInsValues != 0 && (Integer.parseInt(result) == WorkflowConst.FlowResult.SENDBACK_SPONSOR || Integer.parseInt(result) == WorkflowConst.FlowResult.SENDBACK_PREVIOUS)) {
            return false;//会签节点不允许驳回发起人和驳回上一节点
        }
        /* 判断审批意见,1.审批通过,2.审批不通过,3.驳回发起人,4.驳回上一节点(如果上一节点
         * 为会签节点不允许驳回),6.发起人撤回流程                                                                                   */
        
        //2:审批不通过、8:管理员不通过、12:系统自动不通过--->结束流程
        if ((Integer.parseInt(result) == WorkflowConst.FlowResult.REJECT || Integer.parseInt(result) == WorkflowConst.FlowResult.ADMINREJECT|| 
        	Integer.parseInt(result) == WorkflowConst.FlowResult.SYSTEMREJECT)&& multiInsValues == 0) {
            //直接跳到结束节点
            ActivityImpl endActivity = null;
            try {
            	bussinessFormService.updateBusinessStatus(formId, IBussinessFormService.PROCESS_STATUS_END);
                endActivity = findActivitiImpl(taskId, "end");
//                saveHistory(task, result, options, formId);
                //系统自动处理时，处理人为"系统"
                if(Integer.parseInt(result) == WorkflowConst.FlowResult.SYSTEMREJECT){
                	currentUserID = "系統";}
                processHistoryService.saveProcessHistory(task, currentUserID,result, options, formId);
                commitProcess(taskId, null, endActivity.getId());
            } catch (Exception e) {
            	logger.error(e.getMessage(),e);
            	processHistoryService.deleteProcessHisByTaskIdAndProinstId(taskId, task.getProcessInstanceId());
            }
            return true;
        }//3:驳回发起人、9:管理员驳回发起人、13:系统驳回发起人
        else if (Integer.parseInt(result) == WorkflowConst.FlowResult.SENDBACK_SPONSOR || Integer.parseInt(result) == WorkflowConst.FlowResult.ADMINSENDBACK_SPONSOR||
        		 Integer.parseInt(result) == WorkflowConst.FlowResult.SYSTEMSENDBACK_SPONSOR){
            try {
                backProcess(taskId, "startuser", map, result, options, formId);
                Task userTask = taskService.createTaskQuery().processInstanceId(task.getProcessInstanceId()).singleResult();
                Condition condition = new Condition();
                condition.addExpression("actid", "startuser");
                condition.addExpression("processinsid", task.getProcessInstanceId());
                List<ProcessHistoryVO> historyList = processHistoryService.queryProcessHistoryListByCondition(condition, null);
                if (historyList.size() > 0) {
                    taskService.setAssignee(userTask.getId(), historyList.get(0).getUserId());
                }
            } catch (Exception e) {
            	logger.error(e.getMessage(),e);
                return false;//驳回发起人失败
            }
            return true;
        }//4:驳回上一节点、14:系统驳回上一节点
        else if (Integer.parseInt(result) == WorkflowConst.FlowResult.SENDBACK_PREVIOUS || Integer.parseInt(result) == WorkflowConst.FlowResult.SYSTEMSENDBACK_PREVIOUS) {
            try {
                Map<String, FlowNode> nodeMap = findTask(task.getId(), "previous");
                for (Iterator<String> it = nodeMap.keySet().iterator(); it.hasNext();) {
                    String next = it.next();
                    Condition condition = new Condition();
                    condition.addExpression("actId", next);
                    condition.addExpression("deploymentId", deploymentService.findByProcDefId(task.getProcessDefinitionId()).getId());
                    List<NodeInfoVO> list = nodeInfoService.queryNodeInfoListByCondition(condition, null);
                    int multiInsValue = list.get(0).getMultiInsValue();
                    //判断下一节点是否是会签节点
                    if (multiInsValue == 0) {
                        backProcess(taskId, next, map, result, options, formId);
                        List<Task> list2 = taskService.createTaskQuery().processInstanceId(task.getProcessInstanceId()).list();
                        //如果有分支会出现多个task(不考虑会签情况)
                        for (int i = 0; i < list.size(); i++) {
                            condition = new Condition();
                            condition.addExpression("actId", list2.get(i).getTaskDefinitionKey());
                            condition.addExpression("deploymentId", deploymentService.findByProcDefId(task.getProcessDefinitionId()).getId());
                            List<NodeInfoVO> nodeInfo = nodeInfoService.queryNodeInfoListByCondition(condition, null);
                            if (nodeInfo.get(0).getMultiInsValue() == 0) {
                                //设置当前节点的执行人,同时查询当前节点是否有委托,同时把委托人设置进去
                                setTaskAssignee(list2.get(i), nodeInfo.get(0), deploymentService.findByProcDefId(task.getProcessDefinitionId()).getId());
                            }
                        }
                    } else {
                        System.out.println("上一节点是会签节点不允许被驳回!");
                    }
                }
            } catch (Exception e) {
            	logger.error(e.getMessage(),e);
                return false;//驳回上一节点失败
            }
            return true;
        } //发起人撤回
        else if (Integer.parseInt(result) == WorkflowConst.FlowResult.RETRACT && multiInsValues == 0) {
            //直接跳到结束节点
            ActivityImpl endActivity = null;
            try {
            	bussinessFormService.updateBusinessStatus(formId, IBussinessFormService.PROCESS_STATUS_END);
                endActivity = findActivitiImpl(taskId, "end");
                commitProcess(taskId, null, endActivity.getId());
//                saveHistory(task, result, options, formId);
                processHistoryService.saveProcessHistory(task, currentUserID,result, options, formId);
            } catch (Exception e) {
                logger.error(e.getMessage(),e);
            }
            return true;
        }
        //查询出当前节点所需要的流程变量并设置到Map中
        /*Condition condition = new Condition();
        //condition.addExpression("fromTypeId","");
        List<FormVariableVO> formVariableList = formVariableService.queryFormVariableListByCondition(condition, null);
        String[] varaibleName= new String[formVariableList.size()];
        String formName=null;
        for (int i = 0; i < formVariableList.size(); i++) {
        FormVariableVO formVariableVO = formVariableList.get(i);
        varaibleName[i]=formVariableVO.getVariableName();
        //变量值FormValue需要从表单获取       
        //map.put(formVariableVO.getVariableName(), "FormValue");
        }
        map = variableService.getFormVariable(formName, formId, varaibleName);*/
        map = taskService.getVariables(taskId);
        //如果下一节点为会签节点，设置会签变量信息
        Map<String, Object> multiVari = procNextMultiIns(deploymentService.findByProcDefId(task.getProcessDefinitionId()).getId(), task, map);
        //保存流程历史记录(由于会签节点判断的原因所以需要把保存历史放到前面来)
//        saveHistory(task, result, options, formId);
      //系统自动通过时，处理人为"系统"
        if(Integer.parseInt(result) == WorkflowConst.FlowResult.SYSTEMAGREE){
        	currentUserID = "系統";}
        processHistoryService.saveProcessHistory(task, currentUserID,result, options, formId);
        //执行前置事件
        Condition con = new Condition();
        String deploymentId = deploymentService.findByProcDefId(task.getProcessDefinitionId()).getId();
        con.addExpression("actId", task.getTaskDefinitionKey());
        con.addExpression("deploymentId", deploymentId);
        List<NodeInfoVO> nodeInfoList = nodeInfoService.queryNodeInfoListByCondition(con, null);
        NodeInfoVO currentVO = nodeInfoList.get(0);
        String eventID = currentVO.getEventTypeId();
        IExtendEventService extendEventService;
        if (!"startuser".equals(currentVO.getActId()) && !StringUtils.isBlank(eventID)) {
            List<EventVO> eventList = eventService.queryEventListByInput("%" + eventID + "%");
            if (eventList.size() > 0) {
                EventVO eventVO = eventList.get(0);
                try {
                    extendEventService = (IExtendEventService) Class.forName(eventVO.getImplement()).newInstance();
                    extendEventService.beforeEvent();
                } catch (InstantiationException e) {
                	logger.error(e.getMessage(),e);
                } catch (IllegalAccessException e) {
                	logger.error(e.getMessage(),e);
                } catch (ClassNotFoundException e) {
                	logger.error(e.getMessage(),e);
                }
            }
        }
        //办理任务
        Map<String, FlowNode> findTask = findTask(task.getId(), "next");
        if(findTask.isEmpty()){
        	bussinessFormService.updateBusinessStatus(formId, IBussinessFormService.PROCESS_STATUS_END);
        }
        taskService.complete(taskId, multiVari);
        
        //执行后置事件
        if (!"startuser".equals(currentVO.getActId()) && !StringUtils.isBlank(eventID)) {
            List<EventVO> eventList = eventService.queryEventListByInput("%" + eventID + "%");
            if (eventList.size() > 0) {
                EventVO eventVO = eventList.get(0);
                try {
                    extendEventService = (IExtendEventService) Class.forName(eventVO.getImplement()).newInstance();
                    extendEventService.afterEvent();
                } catch (InstantiationException e) {
                	logger.error(e.getMessage(),e);
                } catch (IllegalAccessException e) {
                	logger.error(e.getMessage(),e);
                } catch (ClassNotFoundException e) {
                	logger.error(e.getMessage(),e);
                }
            }
        }
        //获取任务当前流程的所有任务
        List<Task> taskList = this.taskService.createTaskQuery().processInstanceId(task.getProcessInstanceId()).list();
        //如果开始节点后就有分支需要循环设置下几个节点的执行人(会签节点除外)
        if (taskList.size() > 0) {
            for (int i = 0; i < taskList.size(); i++) {
                //如果办理前的节点id和办理后的节点id一样说明是会签节点，则不需要进行消息提醒，只有当前节点通过后才进行消息提醒和办理人处理
                if (!task.getTaskDefinitionKey().equals(taskList.get(i).getTaskDefinitionKey())) {
                    //消息提醒及办理人处理
                    processTask(taskList.get(i), deploymentId);
                }
            }
        }
        return true;
    }

    //消息提醒及办理人处理
    private void processTask(Task task, String deploymentId) {
        //通过自定义表获取当前节点的节点信息
        String act_id = task.getTaskDefinitionKey();
        Condition condition = new Condition();
        condition.addExpression("actId", act_id);
        condition.addExpression("deploymentId", deploymentId);
        List<NodeInfoVO> nodeInfoList2 = nodeInfoService.queryNodeInfoListByCondition(condition, null);
        if (nodeInfoList2.size() > 0) {
            if (nodeInfoList2.get(0).getMultiInsValue() == 0) {//排除会签节点
                //设置节点的执行人,同时查询当前节点是否有委托,同时把委托人设置进去
                setTaskAssignee(task, nodeInfoList2.get(0), deploymentId);
            }
            
            //根据配置信息是否发送邮件消息通知
            int notice = nodeInfoList2.get(0).getNotice();
            dealInform(notice, task.getId());
        }
    }

    /**
     * 设置节点执行人
     * @param task
     * @param nodeInfo
     * @param deploymentId
     */
    private void setTaskAssignee(Task task, NodeInfoVO nodeInfo, String deploymentId) {
        String id = nodeInfo.getId();
        Condition condition = new Condition();
        condition.addExpression("nodeInfoId", id);
        //通过节点信息表的配置设置候选人
        List<ApprAuthorityVO> apprAuthorityList = apprAuthorityService.queryApprAuthorityListByCondition(condition, null);
        for (int i = 0; i < apprAuthorityList.size(); i++) {
            ApprAuthorityVO apprAuthorityVO = apprAuthorityList.get(i);
            String apprType = apprAuthorityVO.getApprType();
            if ("user".equals(apprType)) {
                taskService.addCandidateUser(task.getId(), apprAuthorityVO.getApprTypeId());
            } else if ("role".equals(apprType)) {
                //通过角色ID查询所有的用户，同时把用户添加到候选人中
            	
            	/** ---- 查询更改  wqh 2016/12/23  原因：权限拆分 -------------------------*/
            	// 更改前
//            	List<UserVO> listUsers = userRoleService.queryUserListByRoleId(apprAuthorityVO.getApprTypeId(), null);
            	// 更改后
            	List<UserVO> listUsers = userRoleQueryPubService.queryUserListByRoleId(apprAuthorityVO.getApprTypeId());
            	/**---------------------- 更改至此结束 ----------------------------------*/
                
                for (int j = 0; j < listUsers.size(); j++) {
                    taskService.addCandidateUser(task.getId(), listUsers.get(j).getId());
                }
            } else if ("group".equals(apprType)) {
                List<UserVO> listUsers = userGroupRltService.queryUserListByGroupId(apprAuthorityVO.getApprTypeId(), null);
                for (int j = 0; j < listUsers.size(); j++) {
                    taskService.addCandidateUser(task.getId(), listUsers.get(j).getId());
                }
            } else if ("org".equals(apprType)) {
            	//权限拆分---------------------------------------2016.12.26
            	//condition = new Condition();
            	//Sorter sort = new Sorter(Direction.ASC, "id");
            	//List<UserVO> listUsers = userService.queryUserByOrgId(apprAuthorityVO.getApprTypeId(),true);
                List<UserVO> listUsers = userPubService.queryUserByOrgId(apprAuthorityVO.getApprTypeId(), null, null,null, true);
                for (int j = 0; j < listUsers.size(); j++) {
                    taskService.addCandidateUser(task.getId(), listUsers.get(j).getId());
                }
            }
        }
        List<IdentityLink> identityLinks = taskService.getIdentityLinksForTask(task.getId());
        for (int i = 0; i < identityLinks.size(); i++) {
            IdentityLink identityLink = identityLinks.get(i);
            //有委托的把被委托人也加入到候选人中
            List<DelegateVO> delegateList = delegateService.findAcceptId(identityLink.getUserId(), deploymentId);
            for (int j = 0; j < delegateList.size(); j++) {
                DelegateVO delegateVO = delegateList.get(j);
                //判断在有效期内的
                if (delegateVO.getStartTime().before(new DDate(new Date())) && delegateVO.getEndTime().after(new DDate(new Date()))) {
                    taskService.addCandidateUser(task.getId(), delegateVO.getAcceptId());
                }
            }
        }
    }





    //获取会签变量信息
    @Override
    public Map<String, Object> procNextMultiIns(String deploymentId, Task task, Map<String, Object> map) throws BusinessException {
        int multiInsValue = -1;
        //>>>>>>>>>>>>>>>>>>>>>>暂时未做分支条件变量设置<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
        //获取下一节点信息
        Map<String, FlowNode> nodeMap = findTask(task.getId(), "next");
        for (Iterator<String> it = nodeMap.keySet().iterator(); it.hasNext();) {
            String next = it.next();
            Condition condition = new Condition();
            condition.addExpression("actId", next);
            condition.addExpression("deploymentId", deploymentId);
            List<NodeInfoVO> list = nodeInfoService.queryNodeInfoListByCondition(condition, null);
            multiInsValue = list.get(0).getMultiInsValue();
            //判断下一节点是否是会签节点
            if (multiInsValue != 0) {
                String id = list.get(0).getId();
                condition = new Condition();
                condition.addExpression("nodeInfoId", id);
                List<String> userList = new ArrayList<String>();//存储参与人的ID
                //通过节点信息表的配置设置候选人
                List<ApprAuthorityVO> apprAuthorityList = apprAuthorityService.queryApprAuthorityListByCondition(condition, null);
                for (int i = 0; i < apprAuthorityList.size(); i++) {
                    ApprAuthorityVO apprAuthorityVO = apprAuthorityList.get(i);
                    String apprType = apprAuthorityVO.getApprType();
                    if ("user".equals(apprType)) {
                        userList.add(apprAuthorityVO.getApprTypeId());
                    } else if ("role".equals(apprType)) {
                        //通过角色ID查询所有的用户，同时把用户添加到候选人中
                    	
                    	/** ---- 查询更改  wqh 2016/12/23  原因：权限拆分 -------------------------*/
                    	// 更改前
//                    	List<UserVO> listUsers = userRoleService.queryUserListByRoleId(apprAuthorityVO.getApprTypeId(), null);
                    	// 更改后
                    	List<UserVO> listUsers = userRoleQueryPubService.queryUserListByRoleId(apprAuthorityVO.getApprTypeId() );
                    	/**---------------------- 更改至此结束 ----------------------------------*/
                        
                        for (int j = 0; j < listUsers.size(); j++) {
                            userList.add(listUsers.get(j).getId());
                        }
                    } else if ("group".equals(apprType)) {
                        List<UserVO> listUsers = userGroupRltService.queryUserListByGroupId(apprAuthorityVO.getApprTypeId(), null);
                        for (int j = 0; j < listUsers.size(); j++) {
                            userList.add(listUsers.get(j).getId());
                        }
                    } else if ("org".equals(apprType)) {
                    	//权限拆分---------------------------------------2016.12.26
                        //condition = new Condition();
                        //Sorter sort = new Sorter(Direction.ASC, "id");
                       // List<UserVO> listUsers = userService.queryUserByOrgId(apprAuthorityVO.getApprTypeId(), condition, null, sort, true).getContent();
                        List<UserVO> listUsers = userPubService.queryUserByOrgId(apprAuthorityVO.getApprTypeId(), null, null,null, true);
                        for (int j = 0; j < listUsers.size(); j++) {
                            userList.add(listUsers.get(j).getId());
                        }
                    }
                }
                List<String> userList2 = userList;
                for (int i = 0; i < userList2.size(); i++) {
                    String userId = userList2.get(i);
                    //有委托的把被委托人也加入到候选人中
                    List<DelegateVO> delegateList = delegateService.findAcceptId(userId, deploymentId);
                    for (int j = 0; j < delegateList.size(); j++) {
                        DelegateVO delegateVO = delegateList.get(j);
                        //判断在有效期内的
                        if (delegateVO.getStartTime().before(new DDate(new Date())) && delegateVO.getEndTime().after(new DDate(new Date()))) {
                            userList.add(delegateVO.getAcceptId());//添加被委托人到会签人列表中
                            userList.remove(delegateVO.getUserId());//去掉委托用户ID
                        }
                    }
                }
                //是会签节点需要设置会签参与人
                map.put("users", userList);//会签参与人的list
                map.put("couts", userList.size());//会签参与数量
            }
        }
        return map;
    }

    /** 
     * 驳回流程 
     *  
     * @param taskId 当前任务ID
     * @param activityId 驳回节点ID
     * @param variables 流程存储参数
     * @param options 流程审批附件意见
     * @param formId 表单ID
     *            
     * @throws Exception
     */
    public void backProcess(String taskId, String activityId, Map<String, Object> variables, String result,String options, String formId) throws BusinessException {
    	String currentUserID = UserUtils.getCurrentUserID();
    	//保存流程历史记录
        Task task2 = taskService.createTaskQuery().taskId(taskId).singleResult();
        //驳回发起人
        if ("startuser".equals(activityId)) {
//            saveHistory(task2, "3", options, formId);//审批意见
        	if(Integer.parseInt(result) == WorkflowConst.FlowResult.SYSTEMSENDBACK_SPONSOR){
            	currentUserID = "系統";}
            processHistoryService.saveProcessHistory(task2, currentUserID, result, options, formId);
        } else {
//            saveHistory(task2, "4", options, formId);//审批意见
        	if(Integer.parseInt(result) == WorkflowConst.FlowResult.SYSTEMSENDBACK_PREVIOUS){
            	currentUserID = "系統";}
            processHistoryService.saveProcessHistory(task2, currentUserID, result, options, formId);
        }
        if (StringUtils.isBlank(activityId)) {
        	logger.error("驳回目标节点ID为空！");
            throw new BusinessException("驳回目标节点ID为空！");
        }
        // 查询本节点发起的会签任务，并结束
        List<Task> tasks = taskService.createTaskQuery().taskId(taskId).taskDescription("jointProcess").list();
        for (Task task : tasks) {
            commitProcess(task.getId(), null, null);
        }
        // 查找所有并行任务节点，同时驳回 
        List<Task> taskList = findTaskListByKey(findProcessInstanceByTaskId(taskId).getId(), findTaskById(taskId).getTaskDefinitionKey());
        for (Task task : taskList) {
            commitProcess(task.getId(), variables, activityId);
        }
    }

    /**
     * 根据任务ID获取对应的流程实例
     * 
     * @param taskId
     *            任务ID
     * @return
     * @throws Exception
     */
    private ProcessInstance findProcessInstanceByTaskId(String taskId) throws BusinessException {
        // 找到流程实例
        ProcessInstance processInstance = runtimeService
                .createProcessInstanceQuery().processInstanceId(findTaskById(taskId).getProcessInstanceId())
                .singleResult();
        if (processInstance == null) {
        	logger.error("流程实例未找到!");
            throw new BusinessException("流程实例未找到!");
        }
        return processInstance;
    }

    /**
     * 根据流程实例ID和任务key值查询所有同级任务集合
     * 
     * @param processInstanceId
     * @param key
     * @return
     */
    private List<Task> findTaskListByKey(String processInstanceId, String key) {
        return taskService.createTaskQuery().processInstanceId(
                processInstanceId).taskDefinitionKey(key).list();
    }

    /**
     * 根据任务ID和节点ID获取活动节点 <br>
     * 
     * @param taskId
     *            任务ID
     * @param activityId
     *            活动节点ID <br>
     *            如果为null或""，则默认查询当前活动节点 <br>
     *            如果为"end"，则查询结束节点 <br>
     * @return
     * @throws Exception
     */
    private ActivityImpl findActivitiImpl(String taskId, String activityId)
            throws BusinessException {
        // 取得流程定义 
        ProcessDefinitionEntity processDefinition = findProcessDefinitionEntityByTaskId(taskId);
        // 获取当前活动节点ID
        if (StringUtils.isBlank(activityId)) {
            activityId = findTaskById(taskId).getTaskDefinitionKey();
        }
        // 根据流程定义，获取该流程实例的结束节点 
        if (activityId.toUpperCase().equals("END")) {
            for (ActivityImpl activityImpl : processDefinition.getActivities()) {
                List<PvmTransition> pvmTransitionList = activityImpl.getOutgoingTransitions();
                if (pvmTransitionList.isEmpty()) {
                    return activityImpl;
                }
            }
        }
        // 根据节点ID，获取对应的活动节点 
        ActivityImpl activityImpl = ((ProcessDefinitionImpl) processDefinition).findActivity(activityId);
        return activityImpl;
    }

    /**
     * 根据任务ID获取流程定义
     * 
     * @param taskId
     *            任务ID
     * @return
     * @throws Exception
     */
    private ProcessDefinitionEntity findProcessDefinitionEntityByTaskId(
            String taskId) throws BusinessException {
        // 取得流程定义
        ProcessDefinitionEntity processDefinition = (ProcessDefinitionEntity) ((RepositoryServiceImpl) repositoryService)
                .getDeployedProcessDefinition(findTaskById(taskId).getProcessDefinitionId());
        if (processDefinition == null) {
        	logger.error("流程实例未找到!");
            throw new BusinessException("流程实例未找到!");
        }
        return processDefinition;
    }

    /** 
     * 根据任务ID获得任务实例
     * 
     * @param taskId 任务ID
     * @return
     * @throws Exception
     */
    private TaskEntity findTaskById(String taskId) throws BusinessException{
        TaskEntity task = (TaskEntity) taskService.createTaskQuery().taskId(taskId).singleResult();
        if (task == null) {
            logger.error("任务实例未找到!");
            throw new BusinessException("任务实例未找到!");
        }
        return task;
    }

    /**
     * @param taskId 当前任务ID
     * @param variables 流程变量
     * @param activityId
     *            流程转向执行任务节点ID<br>
     *            此参数为空，默认为提交操作
     * @throws Exception
     */
    private void commitProcess(String taskId, Map<String, Object> variables, String activityId) throws BusinessException {
        if (variables == null) {
            variables = new HashMap<String, Object>();
        }
        // 跳转节点为空，默认提交操作 
        if (StringUtils.isBlank(activityId)) {
            taskService.complete(taskId, variables);
        } else {// 流程转向操作
            turnTransition(taskId, activityId, variables);
        }
    }

    /**
     * 流程转向操作
     * 
     * @param taskId 当前任务ID
     * @param activityId 目标节点任务ID
     * @param variables 流程变量
     * @throws Exception
     */
    private void turnTransition(String taskId, String activityId,
            Map<String, Object> variables) throws BusinessException {
        // 当前节点
        ActivityImpl currActivity = findActivitiImpl(taskId, null);
        // 清空当前流向
        List<PvmTransition> oriPvmTransitionList = clearTransition(currActivity);
        // 创建新流向
        TransitionImpl newTransition = currActivity.createOutgoingTransition();
        // 目标节点
        ActivityImpl pointActivity = findActivitiImpl(taskId, activityId);
        // 设置新流向的目标节点
        newTransition.setDestination(pointActivity);
        // 执行转向任务
        taskService.complete(taskId, variables);
        // 删除目标节点新流入
        pointActivity.getIncomingTransitions().remove(newTransition);
        // 还原以前流向
        restoreTransition(currActivity, oriPvmTransitionList);
    }

    /**
     * 清空指定活动节点流向
     * 
     * @param activityImpl 活动节点
     * @return 节点流向集合
     */
    private List<PvmTransition> clearTransition(ActivityImpl activityImpl) {
        // 存储当前节点所有流向临时变量
        List<PvmTransition> oriPvmTransitionList = new ArrayList<PvmTransition>();
        // 获取当前节点所有流向，存储到临时变量，然后清空
        List<PvmTransition> pvmTransitionList = activityImpl.getOutgoingTransitions();
        for (PvmTransition pvmTransition : pvmTransitionList) {
            oriPvmTransitionList.add(pvmTransition);
        }
        pvmTransitionList.clear();
        return oriPvmTransitionList;
    }

    /**
     * 还原指定活动节点流向
     * 
     * @param activityImpl
     *            活动节点
     * @param oriPvmTransitionList
     *            原有节点流向集合
     */
    private void restoreTransition(ActivityImpl activityImpl, List<PvmTransition> oriPvmTransitionList) {
        // 清空现有流向
        List<PvmTransition> pvmTransitionList = activityImpl.getOutgoingTransitions();
        pvmTransitionList.clear();
        // 还原以前流向
        for (PvmTransition pvmTransition : oriPvmTransitionList) {
            pvmTransitionList.add(pvmTransition);
        }
    }

    /**
     * 根据实例编号查找下一个任务节点
     * @param String procInstId ：实例编号
     * @return
     */
    public TaskDefinition preTaskDefinition(String procInstId) {
        //流程标示
        String processDefinitionId = historyService.createHistoricProcessInstanceQuery().processInstanceId(procInstId).singleResult().getProcessDefinitionId();
        //执行实例
        ExecutionEntity execution = (ExecutionEntity) runtimeService.createProcessInstanceQuery().processInstanceId(procInstId).singleResult();
        //当前实例的执行到哪个节点
        String activitiId = execution.getActivityId();
        ProcessDefinitionEntity def = (ProcessDefinitionEntity) ((RepositoryServiceImpl) repositoryService).getDeployedProcessDefinition(processDefinitionId);
        List<ActivityImpl> activitiList = def.getActivities();
        //获得当前任务的所有节点 
        String id = null;
        for (ActivityImpl activityImpl : activitiList) {
            id = activityImpl.getId();
            if (activitiId.equals(id)) {
                System.out.println("当前任务：" + activityImpl.getProperty("name"));
                return nextTaskDefinition(activityImpl, activityImpl.getId());
            }
        }
        return null;
    }

    /**
     * 根据实例编号查找下一个任务节点
     * @param String procInstId ：实例编号
     * @return
     */
    public TaskDefinition nextTaskDefinition(String procInstId) {
        //流程标示  
        String processDefinitionId = historyService.createHistoricProcessInstanceQuery().processInstanceId(procInstId).singleResult().getProcessDefinitionId();
        //执行实例  
        ExecutionEntity execution = (ExecutionEntity) runtimeService.createProcessInstanceQuery().processInstanceId(procInstId).singleResult();
        //当前实例的执行到哪个节点  
        String activitiId = execution.getActivityId();
        ProcessDefinitionEntity def = (ProcessDefinitionEntity) ((RepositoryServiceImpl) repositoryService).getDeployedProcessDefinition(processDefinitionId);
        List<ActivityImpl> activitiList = def.getActivities();
        //获得当前任务的所有节点  
        String id = null;
        for (ActivityImpl activityImpl : activitiList) {
            id = activityImpl.getId();
            if (activitiId.equals(id)) {
                System.out.println("当前任务：" + activityImpl.getProperty("name"));
                return nextTaskDefinition(activityImpl, activityImpl.getId());
            }
        }
        return null;
    }

    /** 
     * 下一个任务节点 
     * @param activityImpl 
     * @param activityId 
     * @param elString 
     * @return 
     */
    private TaskDefinition nextTaskDefinition(ActivityImpl activityImpl, String activityId) {
        if ("userTask".equals(activityImpl.getProperty("type")) && !activityId.equals(activityImpl.getId())) {
            TaskDefinition taskDefinition = ((UserTaskActivityBehavior) activityImpl.getActivityBehavior()).getTaskDefinition();
            return taskDefinition;
        } else {
            List<PvmTransition> outTransitions = activityImpl.getOutgoingTransitions();
            List<PvmTransition> outTransitionsTemp = null;
            for (PvmTransition tr : outTransitions) {
                PvmActivity ac = tr.getDestination(); //获取线路的终点节点    
                if ("exclusiveGateway".equals(ac.getProperty("type"))) {
                    outTransitionsTemp = ac.getOutgoingTransitions();
                    if (outTransitionsTemp.size() == 1) {
                        return nextTaskDefinition((ActivityImpl) outTransitionsTemp.get(0).getDestination(), activityId);
                    }
                } else if ("userTask".equals(ac.getProperty("type"))) {
                    return ((UserTaskActivityBehavior) ((ActivityImpl) ac).getActivityBehavior()).getTaskDefinition();
                } else {}
            }
            return null;
        }
    }

    /**
     * 查询流程当前节点的上一步节点或下一步节点。
     * @param taskId
     * @param type 获取节点类型(上一节,下一节点)
     * @return
     */
    @Override
    public Map<String, FlowNode> findTask(String taskId, String type) throws BusinessException {
        Map<String, org.activiti.bpmn.model.FlowNode> nodeMap = new HashMap<String, org.activiti.bpmn.model.FlowNode>();
        //ProcessInstance processInstance = findProcessInstanceByTaskId(taskId);
        Task singleResult = taskService.createTaskQuery().taskId(taskId).singleResult();
        if (singleResult == null) {
            return null;
        }
        String processInstanceId = singleResult.getProcessInstanceId();
        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
        // 查询当前节点
        HistoricTaskInstance histask = historyService.createHistoricTaskInstanceQuery().taskId(taskId).processInstanceId(processInstanceId).singleResult();
        // 查询流程定义 。
        BpmnModel bpmnModel = repositoryService.getBpmnModel(processInstance.getProcessDefinitionId());
        List<org.activiti.bpmn.model.Process> listp = bpmnModel.getProcesses();
        org.activiti.bpmn.model.Process process = listp.get(0);
        // 当前节点流定义
        FlowNode sourceFlowElement = (FlowNode) process.getFlowElement(histask.getTaskDefinitionKey());
        // 找到当前任务的流程变量
        List<HistoricVariableInstance> listVar = historyService
                .createHistoricVariableInstanceQuery()
                .processInstanceId(processInstance.getId()).list();
        if ("next".equals(type))
            iteratorNextNodes(process, sourceFlowElement, nodeMap, listVar);
        if ("previous".equals(type))
            iteratorPreviousNodes(process, sourceFlowElement, nodeMap, listVar);
        return nodeMap;
    }

    /**
     * 查询流程当前节点的下一步节点。
     * @param process
     * @param sourceFlowElement
     * @param nodeMap
     * @param listVar
     * @throws Exception
     */
    private void iteratorNextNodes(org.activiti.bpmn.model.Process process,
            FlowNode sourceFlowElement, Map<String, FlowNode> nodeMap,
            List<HistoricVariableInstance> listVar) throws BusinessException {
        List<SequenceFlow> list = sourceFlowElement.getOutgoingFlows();
        for (SequenceFlow sf : list) {
            sourceFlowElement = (FlowNode) process.getFlowElement(sf.getTargetRef());
            if (StringUtils.isNotEmpty(sf.getConditionExpression())) {
                ExpressionFactory factory = new ExpressionFactoryImpl();
                SimpleContext context = new SimpleContext();
                for (HistoricVariableInstance var : listVar) {
                    if (var.getValue() != null)
                        context.setVariable(var.getVariableName(), factory
                                .createValueExpression(var.getValue(), var.getValue().getClass()));
                }
                //ValueExpression e = factory.createValueExpression(context,sf.getConditionExpression(), boolean.class);
                /*if (context!=null&&e.getValue(context) instanceof Boolean &&(Boolean) e.getValue(context)) {
                    nodeMap.put(sourceFlowElement.getId(), sourceFlowElement);
                    break;
                }*/
            }
            if (sourceFlowElement instanceof org.activiti.bpmn.model.UserTask) {
                nodeMap.put(sourceFlowElement.getId(), sourceFlowElement);
                //break;
            } else if (sourceFlowElement instanceof org.activiti.bpmn.model.ExclusiveGateway) {
                iteratorNextNodes(process, sourceFlowElement, nodeMap, listVar);
            } else if (sourceFlowElement instanceof org.activiti.bpmn.model.ParallelGateway) {
                iteratorNextNodes(process, sourceFlowElement, nodeMap, listVar);
            }
        }
    }

    /**
     * 查询流程当前节点的上一个节点。
     * @param process
     * @param sourceFlowElement
     * @param nodeMap
     * @param listVar
     * @throws Exception
     */
    private void iteratorPreviousNodes(org.activiti.bpmn.model.Process process,
            FlowNode sourceFlowElement, Map<String, FlowNode> nodeMap,
            List<HistoricVariableInstance> listVar) throws BusinessException {
        List<SequenceFlow> list = sourceFlowElement.getIncomingFlows();
        for (SequenceFlow sf : list) {
            sourceFlowElement = (FlowNode) process.getFlowElement(sf.getSourceRef());
            if (StringUtils.isNotEmpty(sf.getConditionExpression())) {
                ExpressionFactory factory = new ExpressionFactoryImpl();
                SimpleContext context = new SimpleContext();
                for (HistoricVariableInstance var : listVar) {
                    context.setVariable(var.getVariableName(), factory
                            .createValueExpression(var.getValue(), var.getValue().getClass()));
                }
                /*ValueExpression e = factory.createValueExpression(context,
                        sf.getConditionExpression(), boolean.class);
                if ((Boolean) e.getValue(context)) {
                    nodeMap.put(sourceFlowElement.getId(), sourceFlowElement);
                    break;
                }*/
            }
            if (sourceFlowElement instanceof org.activiti.bpmn.model.UserTask) {
                nodeMap.put(sourceFlowElement.getId(), sourceFlowElement);
                //break;
            } else if (sourceFlowElement instanceof org.activiti.bpmn.model.ExclusiveGateway) {
                iteratorNextNodes(process, sourceFlowElement, nodeMap, listVar);
            } else if (sourceFlowElement instanceof org.activiti.bpmn.model.ParallelGateway) {
                iteratorNextNodes(process, sourceFlowElement, nodeMap, listVar);
            }
        }
    }

    /*
     * 发送通知
     */
    public void dealInform(int notice, String taskId) {
        if (notice == WorkflowConst.InformDealPerson.SYSTEM_INFORM) {
            //发送系统通知
            sendSystemNotice(taskId);
        } else if (notice == WorkflowConst.InformDealPerson.EMAIL_INFORM) {
            //发送邮件
            sendEmail(taskId);
        } else if (notice == WorkflowConst.InformDealPerson.SYSTEM_EMAIL_INFORM) {
            //发送邮件和系统通知
            sendEmail(taskId);
            sendSystemNotice(taskId);
        }
    }

    /**
     * 发送系统通知
     * @param procInsId
     */
    private void sendSystemNotice(String taskId) {}

    /**
     * 发送邮件
     * @param procInsId
     */
    public void sendEmail(String taskId) {
        //发送代办信息邮件通知
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        Map<String, Object> root = new HashMap<String, Object>();
        root.put("categoryname", "");
        root.put("username", "");
        root.put("deployname", "");
        root.put("lastAuditUser", "");
        root.put("serialNo", "");
        root.put("startUser", "");
        root.put("taskname", task.getName());
        root.put("createtime", "");
        root.put("duedate", "");
        //得到节点的下一节点的执行人列表
        List<IdentityLink> idList = this.taskService.getIdentityLinksForTask(task.getId());
        for (IdentityLink il : idList) {
            //可能有多个执行人
            String userSetId = il.getUserId();
            userSetId = userSetId.replaceAll(",,", ",");
            String[] userIdArray = userSetId.split(",");
            for (String uid : userIdArray) {
                if (uid == null || uid.trim().equals("")) {
                    continue;
                }
                UserVO userVO = userPubService.load(uid);
                String emailAddress = userVO.getEmail();
                System.out.println(emailAddress);
                //templateEmail.sendTemplateMail(root,emailAddress, "系统流程审批提醒通知", "/page/mailTemplate/mailtemplate.ftl");
            }
        }
    }

    @Override
    public int processInstanceStatus(String formid) {
        Condition condition = new Condition();
        condition.addExpression("formid", formid);
        List<ProcessHistoryVO> list = processHistoryService.queryProcessHistoryListByCondition(condition, null);
        String pid = list.get(0).getProcessInsId();
        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(pid).singleResult();
        if (processInstance == null) {
            for (ProcessHistoryVO phVO : list) {
                if (phVO.getResult() == "2") {
                    return WorkflowConst.FlowResult.REJECT;
                }
            }
            return WorkflowConst.FlowResult.AGREE;
        } else {
            return 0;
        }
    }

    @Override
    public Page<UserVO> queryCandidateById(String[] userIds,
            String[] userGroupIds, String[] roleIds, String[] orgIds,
            Pageable pageable) {
        
        return null;
    }

    @Override
    public List<UserVO> queryCandidateById(String[] userIds,
            String[] userGroupIds, String[] roleIds, String[] orgIds) {
        
        return null;
    }
}
