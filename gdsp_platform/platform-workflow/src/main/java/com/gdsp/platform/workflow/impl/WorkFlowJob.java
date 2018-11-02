package com.gdsp.platform.workflow.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.gdsp.platform.schedule.service.AbstractJobImpl;
import com.gdsp.platform.workflow.model.TimerTaskVO;
import com.gdsp.platform.workflow.service.IProcessService;
import com.gdsp.platform.workflow.service.ITimerTaskService;
import com.gdsp.platform.workflow.utils.WorkflowConst.FlowResult;

/**
 * 流程节点自动处理任务
 * @author lt
 * @date 2018年1月24日 上午10:01:05
 */
public class WorkFlowJob extends AbstractJobImpl {
	private static final Logger logger = LoggerFactory.getLogger(WorkFlowJob.class);

    @Autowired
    private ITimerTaskService timerTaskService;
    @Autowired
    private IProcessService   processService;

    protected void executeJob() {
        List<TimerTaskVO> timerTaskList = timerTaskService.queryAllTimerTaskList();
        for (int i = 0; i < timerTaskList.size(); i++) {
            TimerTaskVO timerTaskVO = timerTaskList.get(i);
            //任务创建时间
            long millis = timerTaskVO.getTaskCreateTime().getMillis();
            //当前时间
            Calendar now = Calendar.getInstance();
            now.setTime(new Date());
            String unit = timerTaskVO.getUnit();
            int minLength = 0;
            //获取设置的超时分钟数
            if ("min".equals(unit)) {
                minLength = timerTaskVO.getLength();
            } else if ("hour".equals(unit)) {
                minLength = timerTaskVO.getLength() * 60;
            } else if ("day".equals(unit)) {
                minLength = timerTaskVO.getLength() * 60 * 24;
            }
            //判断是否超时
            if (minLength != 0 && (now.getTimeInMillis() - millis) / (1000 * 60) > minLength) {
                int execType = timerTaskVO.getExecType();
                if (1 == execType) {//通知待办人
                    processService.dealInform(3, timerTaskVO.getTaskId());
                } else if (2 == execType) {//自动通过
                    try {
                        processService.complete(timerTaskVO.getTaskId(), String.valueOf(FlowResult.SYSTEMAGREE), "任务超时自动通过", "");
                    } catch (Exception e) {
                        logger.error(e.getMessage(),e);
                    }
                } else if (3 == execType) {//自动不通过
                    try {
                        processService.complete(timerTaskVO.getTaskId(), String.valueOf(FlowResult.SYSTEMREJECT), "任务超时自动不通过", "");
                    } catch (Exception e) {
                    	logger.error(e.getMessage(),e);
                    }
                } else if (5 == execType) {//自动驳回到发起人
                    try {
                        processService.complete(timerTaskVO.getTaskId(), String.valueOf(FlowResult.SYSTEMSENDBACK_SPONSOR), "任务超时自动驳回到发起人", "");
                    } catch (Exception e) {
                    	logger.error(e.getMessage(),e);
                    }
                } else if (4 == execType) {//自动驳回上一级
                    try {
                        processService.complete(timerTaskVO.getTaskId(), String.valueOf(FlowResult.SYSTEMSENDBACK_PREVIOUS), "任务超时自动驳回上一级", "");
                    } catch (Exception e) {
                    	logger.error(e.getMessage(),e);
                    }
                }
            }
        }
    }
}
