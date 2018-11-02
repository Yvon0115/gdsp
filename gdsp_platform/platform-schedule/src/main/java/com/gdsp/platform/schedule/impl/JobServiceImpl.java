package com.gdsp.platform.schedule.impl;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleTrigger;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.quartz.impl.JobDetailImpl;
import org.quartz.impl.matchers.GroupMatcher;
import org.quartz.impl.triggers.CronTriggerImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gdsp.dev.base.exceptions.BusinessException;
import com.gdsp.platform.schedule.model.JobDefVO;
import com.gdsp.platform.schedule.model.JobParameter;
import com.gdsp.platform.schedule.model.JobTriggerVO;
import com.gdsp.platform.schedule.service.IJobService;
import com.gdsp.platform.schedule.utils.FormatUtils;
import com.gdsp.platform.schedule.utils.ScheduleConst;
import com.gdsp.platform.schedule.utils.TriggerUtils;

@Service
@Transactional(readOnly = true)
public class JobServiceImpl implements IJobService {

    private static final Logger logger = LoggerFactory.getLogger(JobServiceImpl.class);
    @Autowired
    private Scheduler           scheduler;

    @Override
    public List<JobDefVO> getJobDefs(String group) {
        try {
            GroupMatcher<JobKey> matcher = GroupMatcher.groupStartsWith(group);
            Set<JobKey> jobKeys = scheduler.getJobKeys(matcher);
            List<JobDefVO> defVOs = new ArrayList<JobDefVO>();
            // 查询已经定义任务类型，转换
            for (JobKey key : jobKeys) {
                JobDefVO defVO = new JobDefVO();
                JobDetail jobDetail = scheduler.getJobDetail(key);
                defVO.setName(jobDetail.getKey().getName());
                defVO.setDescription(jobDetail.getDescription());
                defVO.setClassName(jobDetail.getJobClass().getName());
                defVOs.add(defVO);
            }
            return defVOs;
        } catch (SchedulerException e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    @Override
    public JobDefVO getJobDetail(String name, String group) {
        if (StringUtils.isEmpty(group))
            group = Scheduler.DEFAULT_GROUP;
        try {
            // 查询任务
            JobDetail jobDetail = scheduler.getJobDetail(new JobKey(name, group));
            // VO转换
            JobDefVO defVO = new JobDefVO();
            if (jobDetail != null) {
                defVO.setName(jobDetail.getKey().getName());
                defVO.setGroup(jobDetail.getKey().getGroup());
                defVO.setDescription(jobDetail.getDescription());
                defVO.setClassName(jobDetail.getJobClass().getName());
                JobDataMap jobDataMap = jobDetail.getJobDataMap();
                if (jobDataMap != null) {
                    List<JobParameter> parameters = (List<JobParameter>) jobDataMap.get(ScheduleConst.DATAMAP_KEY_PARA);
                    if (parameters != null && parameters.size() > 0) {
                        for (JobParameter param : parameters) {
                            defVO.addParameter(param);
                        }
                    }
                }
            }
            return defVO;
        } catch (SchedulerException e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    @Override
    public JobTriggerVO getJob(String name, String group) {
        if (StringUtils.isEmpty(group))
            group = Scheduler.DEFAULT_GROUP;
        return TriggerUtils.getJobTriggerFromTrigger(scheduler, new TriggerKey(name, group));
    }

    @SuppressWarnings("unchecked")
    @Transactional
    @Override
    public void saveJobDef(JobDefVO jobDefVO) {
        JobDetailImpl jobDetail = new JobDetailImpl();
        jobDetail.setName(jobDefVO.getName());
        jobDetail.setGroup(jobDefVO.getGroup());
        jobDetail.setDescription(jobDefVO.getDescription());
        // 设置可持久化
        jobDetail.setDurability(true);
        // 处理参数信息
        List<JobParameter> parameters = jobDefVO.getParameters();
        List<JobParameter> validParas = new ArrayList<JobParameter>();
        for (JobParameter parameter : parameters) {
            // 去掉无效的参数
            if (StringUtils.isEmpty(parameter.getParaname()))
                break;
            validParas.add(parameter);
        }
        if (validParas.size() > 0) {
            JobDataMap jobDataMap = new JobDataMap();
            jobDataMap.put(ScheduleConst.DATAMAP_KEY_PARA, validParas);
            jobDetail.setJobDataMap(jobDataMap);
        }
        try {
            jobDetail.setJobClass((Class<? extends Job>) Class.forName((jobDefVO.getClassName())));
            scheduler.addJob(jobDetail, true);
        } catch (ClassNotFoundException e) {
            logger.error(e.getMessage(), e);
            throw new BusinessException("实现类不存在：" + jobDefVO.getClassName());
        } catch (SchedulerException e) {
            logger.error(e.getMessage(), e);
        }
    }

    // 删除任务类型
    @Override
    @Transactional
    public void delJobDef(List<JobKey> jobKeys) {
        try {
            scheduler.deleteJobs(jobKeys);
        } catch (SchedulerException e) {
            logger.error(e.getMessage(), e);
        }
    }

    // 删除任务
    @Override
    @Transactional
    public void delJob(List<TriggerKey> triggerKeys) {
        try {
            scheduler.unscheduleJobs(triggerKeys);
        } catch (SchedulerException e) {
            logger.error(e.getMessage(), e);
        }
    }

    // 获取所有的触发器
    @Override
    public List<JobTriggerVO> getTriggersInfo(String group) {
        try {
            GroupMatcher<TriggerKey> matcher = GroupMatcher.groupStartsWith(group);
            Set<TriggerKey> Keys = scheduler.getTriggerKeys(matcher);
            List<JobTriggerVO> triggers = new ArrayList<JobTriggerVO>();
            for (TriggerKey key : Keys) {
                Trigger trigger = scheduler.getTrigger(key);
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
                    triggerVO.setExpression("重复次数:" + (simple.getRepeatCount() == -1 ? "无限" : simple.getRepeatCount())
                            + ",重复间隔:" + (simple.getRepeatInterval() / 1000L));
                    triggerVO.setDescription(simple.getDescription());
                }
                if (trigger instanceof CronTrigger) {
                    CronTrigger cron = (CronTrigger) trigger;
                    triggerVO.setExpression(cron.getCronExpression());
                    triggerVO.setDescription(cron.getDescription());
                }
                triggers.add(triggerVO);
            }
            return triggers;
        } catch (SchedulerException e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    @Override
    @Transactional
    public void deployJobTrigger(JobTriggerVO jobTriggerVO) {
        String execPolicy = jobTriggerVO.getExecPolicy();
        // 立即执行
        if (execPolicy.equals(JobTriggerVO.EXECPOLICY_WITHOUTDELAY)) {
            // 处理附加配置信息
            JobDataMap jobDataMap = TriggerUtils.getJobDataMapFromJobTrigger(jobTriggerVO);
            execJob(jobTriggerVO.getName(), jobTriggerVO.getJobName(), jobTriggerVO.getJobGroup(), jobDataMap);
            return;
        }
        CronTriggerImpl trigger = new CronTriggerImpl();
        try {
            trigger.setName(jobTriggerVO.getName());
            trigger.setGroup(jobTriggerVO.getGroup());
            trigger.setJobName(jobTriggerVO.getJobName());
            trigger.setJobGroup(jobTriggerVO.getJobGroup());
            try {
                // Corn表达式直接输入
                if (JobTriggerVO.EXECPOLICY_CORNEXPRESSION.equals(execPolicy)) {
                    if (null != jobTriggerVO.getExpression() && !("".equals(jobTriggerVO.getExpression()))) {
                        trigger.setCronExpression(jobTriggerVO.getExpression());
                    }
                } else {
                    // 根据页面参数生成的CRON表达式
                    trigger.setCronExpression(jobTriggerVO.getCronExpression());
                }
            } catch (ParseException e) {
                logger.error(e.getMessage(), e);
            }
            trigger.setDescription(jobTriggerVO.getDescription());
            try {
                trigger.setStartTime(FormatUtils.parseStringToDate(jobTriggerVO.getStartTime()));
                if (StringUtils.isNoneEmpty(jobTriggerVO.getEndTime()))
                    trigger.setEndTime(FormatUtils.parseStringToDate(jobTriggerVO.getEndTime()));
            } catch (ParseException e) {
                logger.error(e.getMessage(), e);
            }
            // 处理附加配置信息
            JobDataMap jobDataMap = TriggerUtils.getJobDataMapFromJobTrigger(jobTriggerVO);
            trigger.setJobDataMap(jobDataMap);
            scheduler.scheduleJob(trigger);
            // 休眠
            if (ScheduleConst.TRIGGER_STATE_PAUSED.equals(jobTriggerVO.getTriggerState()))
                scheduler.pauseTrigger(new TriggerKey(jobTriggerVO.getName(), jobTriggerVO.getGroup()));
        } catch (SchedulerException e) {
            logger.error(e.getMessage(), e);
        }
    }

    // 暂停任务
    @Override
    @Transactional
    public void stopJob(String name, String group) {
        TriggerKey triggerKey = new TriggerKey(name, group);
        try {
            scheduler.pauseTrigger(triggerKey);
        } catch (SchedulerException e) {
            logger.error(e.getMessage(), e);
        }
    }

    // 恢复任务
    @Override
    @Transactional
    public void restartJob(String name, String group) {
        TriggerKey triggerKey = new TriggerKey(name, group);
        try {
            scheduler.resumeTrigger(triggerKey);
        } catch (SchedulerException e) {
            logger.error(e.getMessage(), e);
        }
    }

    // 立即执行一次任务
    @Override
    @Transactional
    public void startNowJob(String name, String group) {
        /*
         * 将触发器的名称通过JobDataMap缓存； 
         * 如果不缓存，则后续触发器的名称由随机数生成，无法干预修改。
         * 缓存时命名为triggerName，取值时也根据triggerName取
         */
        try {
            TriggerKey triggerKey = new TriggerKey(name, group);
            Trigger trigger = scheduler.getTrigger(triggerKey);
            // JobDetail job =6
            // JobBuilder.newJob(scheduler.getJobDetail(key).getJobClass()).storeDurably().build();
            trigger.getJobDataMap().put("triggerName", name);
            scheduler.triggerJob(trigger.getJobKey(), trigger.getJobDataMap());
        } catch (SchedulerException e) {
            logger.error(e.getMessage(), e);
        }
    }

    // 立即执行一次任务类型
    @Transactional
    public void execJob(String name, String jobName, String group, JobDataMap data) {
        try {
            JobKey key = new JobKey(jobName, group);
            data.put("triggerName", name);
            scheduler.triggerJob(key, data);
        } catch (SchedulerException e) {
            logger.error(e.getMessage(), e);
        }
    }

    // 修改触发器时间
    @Override
    @Transactional
    public void modifyTrigger(String name, String group, String cron) {
        try {
            TriggerKey key = TriggerKey.triggerKey(name, group);
            CronTrigger newTrigger = (CronTrigger) TriggerBuilder.newTrigger().withIdentity(key)
                    .withSchedule(CronScheduleBuilder.cronSchedule(cron)).build();
            scheduler.rescheduleJob(key, newTrigger);
        } catch (SchedulerException e) {
            logger.error(e.getMessage(), e);
        }
    }

    // 暂停调度器
    @Override
    @Transactional
    public void stopScheduler() {
        try {
            if (!scheduler.isInStandbyMode()) {
                scheduler.standby();
            }
        } catch (SchedulerException e) {
            logger.error(e.getMessage(), e);
        }
    }
}
