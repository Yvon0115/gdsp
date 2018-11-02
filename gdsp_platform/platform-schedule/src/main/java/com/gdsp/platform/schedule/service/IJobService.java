package com.gdsp.platform.schedule.service;

import java.util.List;

import org.quartz.JobKey;
import org.quartz.TriggerKey;

import com.gdsp.platform.schedule.model.JobDefVO;
import com.gdsp.platform.schedule.model.JobTriggerVO;

/**
* @ClassName: IJobService
* (任务、预警服务接口)
* @author wwb
* @date 2015年10月28日 下午3:16:53
*
*/
public interface IJobService {

    /**
     * 查询所有任务类型
     * @param group 任务组
     * @return List
     */
    public List<JobDefVO> getJobDefs(String group);

    /**
     * 查询任务类型
     * @param name 任务类型名称
     * @param group 任务类型组
     * @return JobDefVO
     */
    public JobDefVO getJobDetail(String name, String group);

    /**
     * 查询任务
     * @param name 任务名称
     * @param group 任务组
     * @return JobTriggerVO
     */
    public JobTriggerVO getJob(String name, String group);

    /**
     * 保存任务类型
     * @param jobDefVO 任务类型
     * @return
     */
    public void saveJobDef(JobDefVO jobDefVO);

    /**
     * 删除任务类型
     * @param jobKeys List
     * @return
     */
    public void delJobDef(List<JobKey> jobKeys);

    /**
     * 部署任务
     * @param jobTriggerVO 任务
     * @return
     */
    public void deployJobTrigger(JobTriggerVO jobTriggerVO);

    /**
     * 获取所有的任务
     * @param group 任务组
     * @return List
     */
    public List<JobTriggerVO> getTriggersInfo(String group);

    /**
     * 删除任务
     * @param triggerKeys List
     * @return
     */
    public void delJob(List<TriggerKey> triggerKeys);

    /**
     * 暂停任务
     * @param name 任务类型名称
     * @param group 任务类型组
     * @return
     */
    public void stopJob(String name, String group);

    /**
     * 恢复任务
     * @param name 任务类型名称
     * @param group 任务类型组
     * @return
     */
    public void restartJob(String name, String group);

    /**
     * 立马执行一次任务
     * @param name 任务类型名称
     * @param group 任务类型组
     * @return
     */
    public void startNowJob(String name, String group);

    /**
     * 修改触发器时间
     * @param name 任务类型名称
     * @param group 任务类型组
     * @param cron 触发器时间 
     * @return
     */
    public void modifyTrigger(String name, String group, String cron);

    /**
     * 暂停调度器
     * @return
     */
    public void stopScheduler();
}
