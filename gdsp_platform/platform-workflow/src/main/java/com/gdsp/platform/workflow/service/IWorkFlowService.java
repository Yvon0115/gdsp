package com.gdsp.platform.workflow.service;

import java.util.Map;

/**
* @ClassName: IWorkFlowService
* @Description: 工作流服务
* @author zhaomingyang
* @date 2015.12.11
*
*/
public interface IWorkFlowService {

    /**
     * 启动已部署的流程
     * @param deploymentCode 流程编码
     * @param formId 单据唯一标示id
     * @param variableMap 变量名和值封装成一个map类型的参数
     * @return 是否启动
     * @author MYZhao
     */
    public boolean StartProcessInstance(String deploymentCode, String formId, Map<String, Object> variableMap);

    /**
     * 根据formid查询当前流程实例的状态
     * @param formId 单据唯一表示id
     * @return 0 代表正在审批中，1 代表审批通过，2 代表审批未通过
     * @author MYZhao
     */
    public int processInstanceStatus(String formId);
}
