package com.gdsp.platform.workflow.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.activiti.engine.task.Task;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gdsp.dev.core.model.param.Sorter;
import com.gdsp.dev.core.model.query.Condition;
import com.gdsp.platform.grant.user.model.UserVO;
import com.gdsp.platform.grant.user.service.IUserQueryPubService;
import com.gdsp.platform.workflow.dao.IProcessHistoryDao;
import com.gdsp.platform.workflow.model.HistoryVO;
import com.gdsp.platform.workflow.model.ProcessHistoryVO;
import com.gdsp.platform.workflow.service.IDeploymentService;
import com.gdsp.platform.workflow.service.IProcessHistoryService;
import com.gdsp.platform.workflow.service.IProcessService;
import com.gdsp.platform.workflow.utils.WorkflowConst;

/**
 * 流程审批历史扩展 无update()操作
 */
@Service
@Transactional(readOnly = true)
public class ProcessHistoryServiceImpl implements IProcessHistoryService {

    @Autowired
    private IProcessService        processService;
    @Autowired
    private IProcessHistoryDao processHistoryDao;
    @Autowired
    private IUserQueryPubService   userQueryPubService;
    @Autowired
    private IDeploymentService deploymentService;

    @Transactional
    @Override
    public boolean saveProcessHistory(Task task, String userId,String result,String options,String formId) {
    	
    	ProcessHistoryVO processHistoryVO = new ProcessHistoryVO();
    	processHistoryVO.setUserId(userId);//审批人
        processHistoryVO.setResult(result);//审批结果
        processHistoryVO.setOptions(options);//审批附加意见
        processHistoryVO.setFormId(formId);//表单
        processHistoryVO.setActId(task.getTaskDefinitionKey());
        processHistoryVO.setActName(task.getName());
        String deploymentId = deploymentService.findByProcDefId(task.getProcessDefinitionId()).getId();
        processHistoryVO.setDeploymentId(deploymentId);
        processHistoryVO.setProcessInsId(task.getProcessInstanceId());
        processHistoryVO.setTaskId(task.getId());
        return processHistoryDao.saveProcessHistory(processHistoryVO);
    }

    @Transactional
    @Override
    public boolean saveProcessHistory(ProcessHistoryVO processHistoryVO ) {
        return processHistoryDao.saveProcessHistory(processHistoryVO);
    }
    @Transactional
    @Override
    public boolean deleteProcessHistory(String... ids) {
        return processHistoryDao.deleteProcessHistory(ids);
    }
    
    @Transactional
    @Override
    public void withdrawProcessHistoryByInsId(String... procInsId) {
    	/* 权限拆分，通用查询不可直接在SQL中查询用户表
         * 修改人：于成龙
         * 修改时间：2017年11月10日 */
        /*-----===查询流程历史（联合act_ru_task表获取运行中taskId区别于历史表中的节点taskId）===------*/
        List<ProcessHistoryVO> historyByCondition = processHistoryDao.queryProcessHistoryByProcInstID(procInsId[0]);
        String taskId = null;
        String formId = null;
        if(historyByCondition!=null && historyByCondition.size()>0){
            ProcessHistoryVO processHistoryVO = historyByCondition.get(0);
            taskId = processHistoryVO.getTaskId();
            formId = processHistoryVO.getFormId();
        }
        if(StringUtils.isNotEmpty(taskId)){
            processService.complete(taskId, "6", "", formId);
        }
    }

    @Override
    public ProcessHistoryVO findProcessHistoryById(String id) {
        return processHistoryDao.findProcessHistoryById(id);
    }

    @Override
    public Page<ProcessHistoryVO> queryProcessHistoryByCondition(
            Condition condition, Pageable pageable, Sorter sort) {
        Page<ProcessHistoryVO> phlist = processHistoryDao
                .queryProcessHistoryByCondition(pageable, condition, sort);
        return resultFormat(phlist);
    }

    @Override
    public List<ProcessHistoryVO> queryProcessHistoryListByCondition(
            Condition condition, Sorter sort) {
        return processHistoryDao.queryProcessHistoryListByCondition(condition,
                sort);
    }

    /* 权限拆分，通用查询不可直接在SQL中查询用户表修改为掉用公共接口查询用户
     * 修改人：于成龙
     * 修改时间：2017年11月10日 */
    @Override
    public Page<HistoryVO> queryAllHistoryForAdmin(Condition condition, Pageable pageable, Sorter sort) {
        Page<HistoryVO> historylist = processHistoryDao.queryAllHistoryForAdmin(pageable, condition, sort);
        List<String> userIds = new ArrayList<>();
        Iterator<HistoryVO> ite = historylist.iterator();
        while (ite.hasNext()) {
        	HistoryVO vo = (HistoryVO) ite.next();
        	userIds.add(vo.getUserId());
        }
		List<UserVO> usersByUserIds = userQueryPubService.querUsersByUserIds(userIds);
		for(UserVO VO : usersByUserIds){
			Iterator<HistoryVO> item = historylist.iterator();
			while (item.hasNext()) {
	        	HistoryVO hvo = (HistoryVO) item.next();
	        	if(hvo.getUserId().equals(VO.getId())){
	        		hvo.setStartUsers(VO.getUsername());
	        	}
        	}
        }
        return timeFormat(historylist);
    }

    /* 权限拆分，通用查询不可直接在SQL中查询用户表修改为掉用公共接口查询用户
     * 修改人：于成龙
     * 修改时间：2017年11月10日 */
    @Override
    public Page<ProcessHistoryVO> queryProcessHistoryByInsId(String procInsId, Sorter sort, Pageable pageable) {
        Page<ProcessHistoryVO> phlist = processHistoryDao.queryProcessHistoryByInsId(procInsId, sort, pageable);
        List<String> userIds = new ArrayList<>();
        Iterator<ProcessHistoryVO> ite = phlist.iterator();
        while (ite.hasNext()) {
        	ProcessHistoryVO vo = (ProcessHistoryVO) ite.next();
        	userIds.add(vo.getUserId());
        }
        /* 查询对应用户集合 */
        List<UserVO> usersByUserIds = userQueryPubService.querUsersByUserIds(userIds);
        /*   对应用户名      */
        for(UserVO VO : usersByUserIds){
        	Iterator<ProcessHistoryVO> item = phlist.iterator();
        	while (item.hasNext()) {
            	ProcessHistoryVO phvo = (ProcessHistoryVO) item.next();
            	if(phvo.getUserId().equals(VO.getId())){
        			phvo.setUserId(VO.getUsername());
        		}
            }
    	}
        return resultFormat(phlist);
    }

    @Override
    public Page<HistoryVO> queryAllHistory() {
        return processHistoryDao.queryAllHistory();
    }

    /**
     * 审批结果： 1 审批通过;2 审批不通过;3 驳回到发起人;4 驳回上一级.
     */
    public Page<ProcessHistoryVO> resultFormat(Page<ProcessHistoryVO> phlist) {
        Iterator<ProcessHistoryVO> ite = phlist.iterator();
        while (ite.hasNext()) {
            ProcessHistoryVO vo = (ProcessHistoryVO) ite.next();
            if (Integer.parseInt(vo.getResult()) == WorkflowConst.FlowResult.AGREE) {
                vo.setResult("审批通过");
            } else if (Integer.parseInt(vo.getResult()) == WorkflowConst.FlowResult.REJECT) {
                vo.setResult("审批不通过");
            } else if (Integer.parseInt(vo.getResult()) == WorkflowConst.FlowResult.SENDBACK_SPONSOR) {
                vo.setResult("驳回到发起人");
            } else if (Integer.parseInt(vo.getResult()) == WorkflowConst.FlowResult.SENDBACK_PREVIOUS) {
                vo.setResult("驳回上一级");
            } else if (Integer.parseInt(vo.getResult()) == WorkflowConst.FlowResult.STARTPROCESS) {
                vo.setResult("发起流程");
            } else if (Integer.parseInt(vo.getResult()) == WorkflowConst.FlowResult.RETRACT) {
                vo.setResult("发起人撤回流程");
            } else if (Integer.parseInt(vo.getResult()) == WorkflowConst.FlowResult.ADMINAGREE) {
                vo.setResult("管理员审批通过");
            } else if (Integer.parseInt(vo.getResult()) == WorkflowConst.FlowResult.ADMINREJECT) {
                vo.setResult("管理员审批不通过");
            } else if (Integer.parseInt(vo.getResult()) == WorkflowConst.FlowResult.ADMINSENDBACK_SPONSOR) {
                vo.setResult("管理员驳回发起人");
            } else if (Integer.parseInt(vo.getResult()) == WorkflowConst.FlowResult.RESTARTPROCESSINST) {
                vo.setResult("重新发起流程");
            }else if (Integer.parseInt(vo.getResult()) == WorkflowConst.FlowResult.SYSTEMAGREE) {
                vo.setResult("系统自动通过");
            } else if (Integer.parseInt(vo.getResult()) == WorkflowConst.FlowResult.SYSTEMREJECT) {
                vo.setResult("系统自动不通过");
            } else if (Integer.parseInt(vo.getResult()) == WorkflowConst.FlowResult.SYSTEMSENDBACK_SPONSOR) {
                vo.setResult("系统驳回发起人");
            } else if (Integer.parseInt(vo.getResult()) == WorkflowConst.FlowResult.SYSTEMSENDBACK_PREVIOUS) {
                vo.setResult("系统驳回上一节点");
            }
        }
        return phlist;
    }

    /**
     * 时间格式化
     * @param historylist
     * @return Page<HistoryVO>
     */
    public Page<HistoryVO> timeFormat(Page<HistoryVO> historylist) {
        Iterator<HistoryVO> iterator = historylist.iterator();
        while (iterator.hasNext()) {
            HistoryVO vo = (HistoryVO) iterator.next();
            long time = Long.parseLong(vo.getDuration()); // 转换long类型
            long d = time / 86400000; // 天数
            long hms = time % 86400000; // 除去天数的毫秒数
            long h = hms / 3600000; // 时
            long hh = hms % 3600000;
            long m = hh / 60000; // 分
            long mm = hh % 60000;
            long s = mm / 1000; // 秒
            // Date date = new Date(hms);
            // DateFormat dateFormat = new SimpleDateFormat("HH时mm分ss秒");
            // String hour = dateFormat.format(date);
            String day = Long.toString(d);
            String hour = Long.toString(h);
            String min = Long.toString(m);
            String sec = Long.toString(s);
            String dueTime = day + "天" + hour + "时" + min + "分" + sec + "秒";
            vo.setDuration(dueTime);
        }
        return historylist;
    }

    @Override
    public int queryApproveResultByInstId(String proInstId) {
        return processHistoryDao.queryApproveResultByInstId(proInstId);
    }

    @Transactional
	@Override
	public void deleteProcessHisByTaskIdAndProinstId(String taskId, String processInstanceId) {
		processHistoryDao.deleteProcessHisByTaskIdAndProinstId(taskId,processInstanceId);
		
	}
}