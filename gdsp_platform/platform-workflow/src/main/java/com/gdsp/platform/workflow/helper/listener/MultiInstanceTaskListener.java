package com.gdsp.platform.workflow.helper.listener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.HistoryService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.TaskService;
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.impl.HistoryServiceImpl;
import org.activiti.engine.impl.RepositoryServiceImpl;
import org.activiti.engine.impl.TaskServiceImpl;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.persistence.entity.TaskEntity;
import org.activiti.engine.impl.pvm.PvmTransition;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.impl.pvm.process.ProcessDefinitionImpl;
import org.activiti.engine.impl.pvm.process.TransitionImpl;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.task.Task;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gdsp.dev.base.exceptions.BusinessException;
import com.gdsp.dev.core.common.AppContext;
import com.gdsp.dev.core.model.query.Condition;
import com.gdsp.platform.workflow.impl.DeploymentServiceImpl;
import com.gdsp.platform.workflow.impl.NodeInfoServiceImpl;
import com.gdsp.platform.workflow.impl.ProcessHistoryServiceImpl;
import com.gdsp.platform.workflow.model.DeploymentVO;
import com.gdsp.platform.workflow.model.NodeInfoVO;
import com.gdsp.platform.workflow.model.ProcessHistoryVO;
import com.gdsp.platform.workflow.service.IDeploymentService;
import com.gdsp.platform.workflow.service.INodeInfoService;
import com.gdsp.platform.workflow.service.IProcessHistoryService;

/**
 * <p>Name:MultiInstanceTaskListener</p>
 * 
 * <p>Title:</p>
 * 
 * <p>Description: 会签步骤的侦听器</p>
 * 
 * <p>Copyright: Copyright (c) 2015</p>
 * 
 * 
 * @author 孙艳涛
 * @version 1.0
 */
public class MultiInstanceTaskListener implements TaskListener {
	private static final long serialVersionUID = 4572781504654226752L;
	private static final Logger logger = LoggerFactory.getLogger(MultiInstanceTaskListener.class);
	private transient HistoryService           historyService        = AppContext.lookupBean("historyService", HistoryServiceImpl.class);              //历史记录接口
    private transient RepositoryService        repositoryService     = AppContext.lookupBean("repositoryService", RepositoryServiceImpl.class);        //流程资源接口
    protected transient IProcessHistoryService processHistoryService = AppContext.lookupBean("processHistoryService", ProcessHistoryServiceImpl.class);
    private transient INodeInfoService         nodeInfoService       = AppContext.lookupBean("nodeInfoService", NodeInfoServiceImpl.class);;
    private transient IDeploymentService       deploymentService     = AppContext.lookupBean("deploymentService", DeploymentServiceImpl.class);
    private transient TaskService              taskService           = AppContext.lookupBean("taskService", TaskServiceImpl.class);

    @Override
    public void notify(DelegateTask delegateTask) {
        //事件名称
        String eventName = delegateTask.getEventName();
        if (EVENTNAME_CREATE.equals(eventName)) {
            //执行到会签节点时需要进行的业务处理
        } else if (EVENTNAME_COMPLETE.equals(eventName)) {//办理任务时会触发complete事件
            //获取节点ID
            String actid = delegateTask.getTaskDefinitionKey();
            int isComplete = 0;
            //会签实例总数
            int instances = (Integer) delegateTask.getVariable("nrOfInstances");
            //完成的实例数，审批总数(并非是同意的个数)
            int completes = (Integer) delegateTask.getVariable("nrOfCompletedInstances");
            //获取流程实例ID
            String processInstanceId = delegateTask.getProcessInstanceId();
            //定义同意的个数
            int complete = 0;
            //定义不同意的个数
            double refuse = 0;
            //查看审批记录中的审批历史
            List<HistoricTaskInstance> hitaskList = this.historyService.createHistoricTaskInstanceQuery().taskDefinitionKey(actid).processInstanceId(processInstanceId).list();
            //遍历审批历史
            for (HistoricTaskInstance ht : hitaskList) {
                Condition condition = new Condition();
                condition.addExpression("processInsId", processInstanceId);
                condition.addExpression("actId", actid);
                condition.addExpression("taskId", ht.getId());
                List<ProcessHistoryVO> historyList = processHistoryService.queryProcessHistoryListByCondition(condition, null);
                if (historyList.size() > 0) {
                    ProcessHistoryVO processHistoryVO = historyList.get(0);
                    //每一个同意让同意的个数加一
                    if (Integer.parseInt(processHistoryVO.getResult()) == 1) {
                        complete += 1;
                    }
                    if (Integer.parseInt(processHistoryVO.getResult()) == 2) {
                        refuse += 1;
                    }
                }
            }
            Task task = taskService.createTaskQuery().processInstanceId(processInstanceId).executionId(delegateTask.getExecutionId()).singleResult();
            //获取自定义 表的部署ID
            ProcessDefinition processDefinition = this.repositoryService.createProcessDefinitionQuery().processDefinitionId(delegateTask.getProcessDefinitionId()).singleResult();
            DeploymentVO deploymentVO = deploymentService.findByProcDefId(processDefinition.getId());
            //通过部署ID和节点名称取得会签设置规则
            Condition condition = new Condition();
            condition.addExpression("actId", actid);
            condition.addExpression("deploymentId", deploymentVO.getId());
            List<NodeInfoVO> nodeInfoList = nodeInfoService.queryNodeInfoListByCondition(condition, null);
            //未从会签规则表中取得会签规则
            if (nodeInfoList.size() <= 0) {
                delegateTask.setVariable("Complete", isComplete);
                return;
            }
            NodeInfoVO nodeInfoVO = nodeInfoList.get(0);
            //获取会签配置规则
            Integer cousignRule = nodeInfoVO.getMultiInsValue();
            String multiInsType = nodeInfoVO.getMultiInsType();
            //1.全票通过,2.半数通过,3.一票通过,4.自定义百分比通过,5.自定义票数通过
            //会签的全票通过
            if (multiInsType.equals("allAgree")) {
                if (complete == instances) {
                    isComplete = 1;
                    delegateTask.setVariable("Complete", isComplete);
                    //一个不同意直接结束流程
                } else if (refuse >= 1) {
                    ActivityImpl endActivity = null;
                    try {
                        endActivity = findActivitiImpl(task.getId(), "end");
                        commitProcess(task.getId(), null, endActivity.getId());
                        saveHistory(task, "2", "会签审批不通过", nodeInfoVO.getFormTypeId());
                    } catch (Exception e) {
                        logger.error(e.getMessage(),e);
                    }
                    return;
                } else {
                    delegateTask.setVariable("Complete", isComplete);
                }
                //会签的过半通过
            } else if (multiInsType.equals("halfAgree")) {
                if (complete*1.0 / instances >= 0.5) {
                    isComplete = 1;
                    delegateTask.setVariable("Complete", isComplete);
                    //全部审批完成但没有审批通过直接结束流程
                    if (completes == instances) {
                        ActivityImpl endActivity = null;
                        try {
                            endActivity = findActivitiImpl(task.getId(), "end");
                            commitProcess(task.getId(), null, endActivity.getId());
                            saveHistory(task, "2", "会签审批不通过", nodeInfoVO.getFormTypeId());
                        } catch (Exception e) {
                        	logger.error(e.getMessage(),e);
                        }
                    }
                    return;
                } else {
                    delegateTask.setVariable("Complete", isComplete);
                }
                //会签一票通过
            } else if (multiInsType.equals("oneAgree")) {
                if (complete >= 1) {
                    isComplete = 1;
                    delegateTask.setVariable("Complete", isComplete);
                } else if (completes == instances) {
                    //全部审批完成但没有审批通过直接结束流程
                    ActivityImpl endActivity = null;
                    try {
                        endActivity = findActivitiImpl(task.getId(), "end");
                        String id = null;
                        if(endActivity != null){
                        	id = endActivity.getId();
                        }
                        commitProcess(task.getId(), null, id);
                        saveHistory(task, "2", "会签审批不通过", nodeInfoVO.getFormTypeId());
                    } catch (Exception e) {
                    	logger.error(e.getMessage(),e);
                    }
                    return;
                } else {
                    delegateTask.setVariable("Complete", isComplete);
                }
                //会签自定义百分比通过
            } else if (multiInsType.equals("percentageAgree")) {
                if (complete * 100.0 / instances >= cousignRule) {
                    isComplete = 1;
                    delegateTask.setVariable("Complete", isComplete);
                } else if (completes == instances) {
                    //全部审批完成但没有审批通过直接结束流程
                    ActivityImpl endActivity = null;
                    try {
                        endActivity = findActivitiImpl(task.getId(), "end");
                        String id = null;
                        if(endActivity != null){
                        	id = endActivity.getId();
                        }
                        commitProcess(task.getId(), null, id);
                        saveHistory(task, "2", "会签审批不通过", nodeInfoVO.getFormTypeId());
                    } catch (Exception e) {
                    	logger.error(e.getMessage(),e);
                    }
                    return;
                } else {
                    delegateTask.setVariable("Complete", isComplete);
                }
            } else if (multiInsType.equals("countAgree")) {
                if (complete == cousignRule) {
                    isComplete = 1;
                    delegateTask.setVariable("Complete", isComplete);
                } else if (completes == instances) {
                    //全部审批完成但没有审批通过直接结束流程
                    ActivityImpl endActivity = null;
                    try {
                        endActivity = findActivitiImpl(task.getId(), "end");
                    } catch (Exception e) {
                    	logger.error(e.getMessage(),e);
                    }
                    try {
                    	String id = null;
                        if(endActivity != null){
                        	id = endActivity.getId();
                        }
                        commitProcess(task.getId(), null, id);
                        saveHistory(task, "2", "会签审批不通过", nodeInfoVO.getFormTypeId());
                    } catch (Exception e) {
                    	logger.error(e.getMessage(),e);
                    }
                    return;
                } else {
                    delegateTask.setVariable("Complete", isComplete);
                }
            } else {
                delegateTask.setVariable("Complete", isComplete);
            }
        }
    }

    public void saveHistory(Task task, String result, String options, String formId) {
        ProcessHistoryVO processHistoryVO = new ProcessHistoryVO();
        processHistoryVO.setActId(task.getTaskDefinitionKey());
        processHistoryVO.setActName(task.getName());
        processHistoryVO.setResult(result);//审批意见
        processHistoryVO.setOptions(options);//审批附加意见
        processHistoryVO.setDeploymentId(getDeploymentId(task.getProcessDefinitionId()));
        processHistoryVO.setProcessInsId(task.getProcessInstanceId());
        //暂时设定为系统管理员
        processHistoryVO.setUserId("1b85da1f28834a7a93965073cc77be3a");
        processHistoryVO.setFormId(formId);
        processHistoryService.saveProcessHistory(processHistoryVO);
    }

    /**
     * 通过流程定义ID获取部署ID
     */
    private String getDeploymentId(String processDefinitionId) {
        DeploymentVO deploy = deploymentService.findByProcDefId(processDefinitionId);
        return deploy.getId();
    }

    /**
     * 根据任务ID获取流程定义
     * @throws Exception 
     */
    private ProcessDefinitionEntity findProcessDefinitionEntityByTaskId(
            String taskId) throws BusinessException{
        // 取得流程定义
        ProcessDefinitionEntity processDefinition = (ProcessDefinitionEntity) ((RepositoryServiceImpl) repositoryService)
                .getDeployedProcessDefinition(findTaskById(taskId)
                        .getProcessDefinitionId());
        if (processDefinition == null) {
            throw new BusinessException("流程定义未找到!");
        }
        return processDefinition;
    }

    /**
     * 根据任务ID和节点ID获取活动节点 <br>
     * @param taskId 任务ID
     * @param activityId 活动节点ID <br> 如果为null或""，则默认查询当前活动节点 <br> 如果为"end"，则查询结束节点 <br>
     */
    private ActivityImpl findActivitiImpl(String taskId, String activityId){
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
     * 根据任务ID获得任务实例
     * @param taskId 任务ID
     */
    private TaskEntity findTaskById(String taskId) throws BusinessException {
        TaskEntity task = (TaskEntity) taskService.createTaskQuery().taskId(taskId).singleResult();
        if (task == null) {
            throw new BusinessException("任务实例未找到!");
        }
        return task;
    }

    /**
     * @param taskId 当前任务ID
     * @param variables 流程变量
     * @param activityId 流程转向执行任务节点ID<br> 此参数为空，默认为提交操作
     */
    private void commitProcess(String taskId, Map<String, Object> variables, String activityId){
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
     * @param taskId 当前任务ID
     * @param activityId 目标节点任务ID
     * @param variables 流程变量
     */
    private void turnTransition(String taskId, String activityId,
            Map<String, Object> variables){
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
     * @param activityImpl 活动节点
     * @param oriPvmTransitionList 原有节点流向集合
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
}
