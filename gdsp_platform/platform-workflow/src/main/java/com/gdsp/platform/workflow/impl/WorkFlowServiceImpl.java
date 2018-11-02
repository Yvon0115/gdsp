package com.gdsp.platform.workflow.impl;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gdsp.platform.workflow.model.DeploymentVO;
import com.gdsp.platform.workflow.service.IDeploymentService;
import com.gdsp.platform.workflow.service.IProcessService;
import com.gdsp.platform.workflow.service.IWorkFlowService;

/**
* @ClassName: WorkFlowServiceImpl
* @Description: 工作流服务实现类
* @author zhaomingyang
* @date 2015.12.11
*
*/
@Service
public class WorkFlowServiceImpl implements IWorkFlowService {
	private static final Logger logger = LoggerFactory.getLogger(WorkFlowServiceImpl.class);
    @Autowired
    protected IDeploymentService deploymentService;
    @Autowired
    protected IProcessService    processService;

    //传入封装的变量map，在processService.start方法中将变量持久化到activiti引擎中 ，
    //在办理的时候complete方法中通过act_ru_variable表进行查询变量信息
    @Override
    public boolean StartProcessInstance(String deploymentCode, String formId, Map<String, Object> variableMap) {
        try {
            DeploymentVO deploymentVO = deploymentService.findByDeploymentCode(deploymentCode);
            String deploymentId = deploymentVO.getId();
            return processService.start(deploymentId, formId, variableMap);
        } catch (Exception e) {
        	logger.error(e.getMessage(),e);
            return false;
        }
    }

    //0 代表正在审批中，1 代表审批通过，2 代表审批未通过
    @Override
    public int processInstanceStatus(String formId) {
        return processService.processInstanceStatus(formId);
    }
}
