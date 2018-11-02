package com.gdsp.platform.workflow.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.activiti.bpmn.model.FlowNode;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.gdsp.dev.base.utils.UUIDUtils;
import com.gdsp.dev.core.model.param.Sorter;
import com.gdsp.dev.core.model.query.Condition;
import com.gdsp.platform.workflow.model.ApprAuthorityVO;
import com.gdsp.platform.workflow.model.BussinessFormVO;
import com.gdsp.platform.workflow.model.FormProcinstRltVO;
import com.gdsp.platform.workflow.model.NodeInfoVO;
import com.gdsp.platform.workflow.model.ProcessHistoryVO;
import com.gdsp.platform.workflow.service.IApprAuthorityService;
import com.gdsp.platform.workflow.service.IBussinessFormService;
import com.gdsp.platform.workflow.service.IFormProcinstRltService;
import com.gdsp.platform.workflow.service.INodeInfoService;
import com.gdsp.platform.workflow.service.IProcessHistoryService;
import com.gdsp.platform.workflow.service.IProcessService;
import com.gdsp.platform.workflow.service.IWorkflowQueryPubService;

/**
 * 工作流对外查询接口实现
 * @author wxl
 *
 */
@Service
public class WorkflowQueryPubServiceImpl implements IWorkflowQueryPubService{

	@Autowired
	private IBussinessFormService bussinessFormService;
	@Autowired
	private IProcessHistoryService processHistoryService;
	@Autowired
	private INodeInfoService nodeInfoService;
	@Autowired
	private IApprAuthorityService apprAuthorityService;
	@Autowired
	private IFormProcinstRltService formProcinstRltService;
	@Autowired
	private IProcessService processService;
	
	/**
	 * 根据formId查询审批记录
	 * @param formId
	 * @return
	 */
	@Override
	public List<ProcessHistoryVO> queryApproveInfoByFormId(String formId) {
		BussinessFormVO bussinessFormVO = bussinessFormService.queryFormVariableValueByFormId(formId);
		List<FormProcinstRltVO> formProcinstRltVOs= formProcinstRltService.queryFormProcinstRltByBusiFormId(bussinessFormVO.getId());
		FormProcinstRltVO fprVO=getMaxVersionVO(formProcinstRltVOs);
		String procInstId=fprVO.getProceinstId();
		Sorter sort = new Sorter(Direction.ASC, "createtime");
		Condition cond=new Condition();
		cond.addExpression("PROCESSINSID", procInstId);
		List<ProcessHistoryVO> historyVOs=processHistoryService.queryProcessHistoryListByCondition(cond, sort);
//		List<ProcessHistoryVO> historyVOs=processHistoryService.queryProcessHistoryByInsId(procInstId, null, sort, null).getContent();
		List<String> phActIds=new ArrayList<>();
		//流程历史表里 为已审批的
		//节点信息包含所有节点信息
		//通过actId排查出未审批的节点信息
		//通过nodeinfo 与apprauthority 来查询未审批人
		for (ProcessHistoryVO processHistoryVO : historyVOs) {
			phActIds.add(processHistoryVO.getActId());
		}
		Condition cond2=new Condition();
		cond2.addExpression("DEPLOYMENTID", bussinessFormVO.getDeploymentId());
		Sorter sort2 = new Sorter(Direction.ASC, "ACTID");
		List<NodeInfoVO> nodeInfoVOs = nodeInfoService.queryNodeInfoListByCondition(cond2, sort2);
		for (NodeInfoVO nodeInfoVO : nodeInfoVOs) {
			ProcessHistoryVO phVO=new ProcessHistoryVO(); 
			if(!phActIds.contains(nodeInfoVO.getActId())){
				phVO.setId(UUIDUtils.getRandomUUID());
				Condition cond3=new Condition(); 
				cond3.addExpression("NODEINFOID", nodeInfoVO.getId());
				List<ApprAuthorityVO> apprAuthorityVOs = apprAuthorityService.queryApprAuthorityListByCondition(cond3, null);
				phVO.setUserId(apprAuthorityVOs.get(0).getApprTypeId());
				historyVOs.add(phVO);
			}
		}
		return resultFormat(historyVOs);
	}

	private FormProcinstRltVO getMaxVersionVO(List<FormProcinstRltVO> formProcinstRltVOs) {
		FormProcinstRltVO vo=formProcinstRltVOs.get(0);
		for (int i = 1; i < formProcinstRltVOs.size(); i++) {
			if(formProcinstRltVOs.get(i).getVersion()>vo.getVersion()){
				vo=formProcinstRltVOs.get(i);
			}
		}
		return vo;
	}
	
	private List<ProcessHistoryVO> resultFormat(List<ProcessHistoryVO> phlist) {
        Iterator<ProcessHistoryVO> ite = phlist.iterator();
        while (ite.hasNext()) {
            ProcessHistoryVO vo = (ProcessHistoryVO) ite.next();
            if(StringUtils.isEmpty(vo.getResult())){
            	continue;
            }
            if (Integer.parseInt(vo.getResult()) == 1) {
                vo.setResult("审批通过");
            } else if (Integer.parseInt(vo.getResult()) == 2) {
                vo.setResult("审批不通过");
            } else if (Integer.parseInt(vo.getResult()) == 3) {
                vo.setResult("驳回到发起人");
            } else if (Integer.parseInt(vo.getResult()) == 4) {
                vo.setResult("驳回上一级");
            } else if (Integer.parseInt(vo.getResult()) == 5) {
                vo.setResult("发起流程");
            } else if (Integer.parseInt(vo.getResult()) == 6) {
                vo.setResult("发起人撤回流程");
            }
        }
        return phlist;
    }

	/**
	 * 查询是否还有下一节点
	 */
	@Override
	public Boolean findNextTask(String taskId) {
		Map<String, FlowNode> map = processService.findTask(taskId, "next");
		if(map.size()>0){
			return true;
		}
		return false;
	}

}
