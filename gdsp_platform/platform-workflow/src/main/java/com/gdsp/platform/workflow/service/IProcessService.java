package com.gdsp.platform.workflow.service;

import java.util.List;
import java.util.Map;

import org.activiti.bpmn.model.FlowNode;
import org.activiti.engine.task.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.gdsp.dev.base.exceptions.BusinessException;
import com.gdsp.platform.grant.user.model.UserVO;

public interface IProcessService {

    //启动流程
    public boolean start(String deploymentId, String formid, Map<String, Object> variableMap) throws BusinessException;

    //获取会签变量信息
    public Map<String, Object> procNextMultiIns(String deploymentId, Task task, Map<String, Object> map) throws BusinessException;

    //保存流程历史
    //public boolean saveHistory(Task task, String result, String options, String formId);

    //办理任务
    public boolean complete(String taskId, String result, String options, String formId) throws BusinessException;

    //发送通知
    public void dealInform(int notice, String taskId);

    /**
     * @param formid单据唯一标示id
     * @return 0 代表正在审批中，1 代表审批通过，2 代表审批未通过
     * @author MYZhao
     */
    public int processInstanceStatus(String formid);

    /**
     * 用户预览查询，查询用户及所属用户组名、角色名、机构名
     * @return Page<UserVO>
     */
    public Page<UserVO> queryCandidateById(String[] userIds, String[] userGroupIds, String[] roleIds, String[] orgIds, Pageable pageable);

    public List<UserVO> queryCandidateById(String[] userIds, String[] userGroupIds, String[] roleIds, String[] orgIds);

	boolean startProcess(String deploymentId, String formid, Map<String, Object> variableMap) throws BusinessException;
	
	Map<String, FlowNode> findTask(String taskId, String type) throws BusinessException;
	
}
