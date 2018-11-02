package com.gdsp.platform.schedule.utils;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.quartz.CronTrigger;
import org.quartz.JobDataMap;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleTrigger;
import org.quartz.Trigger;
import org.quartz.TriggerKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gdsp.platform.schedule.model.JobParameter;
import com.gdsp.platform.schedule.model.JobReceiver;
import com.gdsp.platform.schedule.model.JobTriggerVO;

/**
 * @ClassName: TriggerHelper
 * (任务Helper类)
 * @author wwb
 * @date 2015年11月3日 下午4:52:58
 *
 */
public class TriggerUtils {
	private static final Logger logger = LoggerFactory.getLogger(TriggerUtils.class);
    /**
     * 
     * @param trigger
     * @return
     */
    public static String getTriggerType(Trigger trigger) {
        String type = null;
        if (trigger == null) {
            return null;
        }
        if (trigger.getClass() == SimpleTrigger.class) {
            type = "simple";
        } else if (trigger.getClass() == CronTrigger.class) {
            type = "cron";
        } else {
            type = trigger.getClass().getName();
        }
        return type;
    }

    /**
     * @Title: getTriggersFromJob
     * (通过任务类型获取任务)
     * @param scheduler
     *            调度
     * @param jobName
     *            任务类型
     * @param jobGroup
     *            任务类型组
     * @return Trigger[] 任务数组
     *      */
    public static Trigger[] getTriggersFromJob(Scheduler scheduler, String jobName, String jobGroup) {
        List<? extends Trigger> triggerList;
        try {
            triggerList = scheduler.getTriggersOfJob(new JobKey(jobName, jobGroup));
            Trigger[] retArr = new Trigger[triggerList.size()];
            triggerList.toArray(retArr);
            return retArr;
        } catch (SchedulerException e) {
        	logger.error(e.getMessage(),e);
        }
        return null;
    }

    /**
     * @Title: getJobTriggerFromTrigger
     * (通过任务获取前台展示任务设置信息)
     * @param scheduler
     *            调度
     * @param key
     *            任务key
     * @return JobTriggerVO 前台展示任务设置信息
     *      */
    public static JobTriggerVO getJobTriggerFromTrigger(Scheduler scheduler, TriggerKey key) {
        Trigger trigger;
        try {
            trigger = scheduler.getTrigger(key);
            JobTriggerVO triggerVO = new JobTriggerVO();
            triggerVO.setTriggerState(FormatUtils.getTriggerState(triggerVO,scheduler.getTriggerState(key)));
            triggerVO.setName(trigger.getKey().getName());
            triggerVO.setGroup(trigger.getKey().getGroup());
            triggerVO.setJobName(trigger.getJobKey().getName());
            triggerVO.setJobGroup(trigger.getJobKey().getGroup());
            triggerVO.setStartTime(FormatUtils.getDateAsString(trigger.getStartTime()));
            triggerVO.setEndTime(FormatUtils.getDateAsString(trigger.getEndTime()));
            triggerVO.setNextFireTime(FormatUtils.getDateAsString(trigger.getNextFireTime()));
            if (trigger instanceof SimpleTrigger) {
                SimpleTrigger simple = (SimpleTrigger) trigger;
                triggerVO.setExpression("重复次数:" + (simple.getRepeatCount() == -1 ? "无限" : simple.getRepeatCount()) + ",重复间隔:"
                        + (simple.getRepeatInterval() / 1000L));
                triggerVO.setDescription(simple.getDescription());
            }
            if (trigger instanceof CronTrigger) {
                CronTrigger cron = (CronTrigger) trigger;
                triggerVO.setExpression(cron.getCronExpression());
                triggerVO.setDescription(cron.getDescription());
            }
            JobDataMap jobDataMap = trigger.getJobDataMap();
            // 处理附加设置信息
            if (jobDataMap != null) {
                List<JobParameter> parameters = (List<JobParameter>) jobDataMap.get(ScheduleConst.DATAMAP_KEY_PARA);
                if (parameters != null && parameters.size() > 0) {
                    triggerVO.setParameter(parameters);
                }
                List<JobReceiver> receivers = (List<JobReceiver>) jobDataMap.get(ScheduleConst.DATAMAP_KEY_RECEIVER);
                if (receivers != null && receivers.size() > 0) {
                    triggerVO.setReceivers(receivers);
                }
                String messageinf = (String) jobDataMap.get(ScheduleConst.DATAMAP_KEY_MESSAGE);
                if (StringUtils.isNotEmpty(messageinf)) {
                    triggerVO.setMessageinf(messageinf);
                }
            }
            return triggerVO;
        } catch (SchedulerException e) {
        	logger.error(e.getMessage(),e);
        }
        return null;
    }

    public static JobDataMap getJobDataMapFromJobTrigger(JobTriggerVO jobTriggerVO) {
        JobDataMap jobDataMap = new JobDataMap();
        // 处理参数信息
        List<JobParameter> parameters = jobTriggerVO.getParameters();
        if (parameters.size() > 0) {
            jobDataMap.put(ScheduleConst.DATAMAP_KEY_PARA, parameters);
        }
        // 
        List<JobReceiver> receivers = jobTriggerVO.getReceivers();
        List<JobReceiver> validReceivers = new ArrayList<JobReceiver>();
        for (JobReceiver receiver : receivers) {
            // 获取有效的接受者设置
            if ("Y".equalsIgnoreCase(receiver.getIsset()))
                validReceivers.add(receiver);
        }
        if (validReceivers.size() > 0) {
            jobDataMap.put(ScheduleConst.DATAMAP_KEY_RECEIVER, validReceivers);
        }
        //
        String messageinf = jobTriggerVO.getMessageinf();
        if (StringUtils.isNotEmpty(messageinf)) {
            jobDataMap.put(ScheduleConst.DATAMAP_KEY_MESSAGE, messageinf);
        }
        return jobDataMap;
    }
}