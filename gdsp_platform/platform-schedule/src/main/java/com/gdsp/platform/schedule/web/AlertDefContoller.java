package com.gdsp.platform.schedule.web;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.quartz.JobKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gdsp.dev.base.exceptions.BusinessException;
import com.gdsp.dev.web.mvc.ext.ViewWrapper;
import com.gdsp.dev.web.mvc.model.AjaxResult;
import com.gdsp.platform.schedule.model.JobDefVO;
import com.gdsp.platform.schedule.model.JobParameter;
import com.gdsp.platform.schedule.model.JobTriggerVO;
import com.gdsp.platform.schedule.service.IJobService;
import com.gdsp.platform.schedule.utils.ScheduleConst;

/**
 * 预警类型管理
 * Created on 2015/9/21
 */
@Controller
@RequestMapping("schedule/alertdef")
public class AlertDefContoller {
	private static final Logger logger = LoggerFactory.getLogger(AlertDefContoller.class);
    @Autowired
    private IJobService        jobService;
    private List<JobParameter> parameters = new ArrayList<JobParameter>();

    @RequestMapping("/alertDefList.d")
    public String alertDefList(Model model) {
        //
        List<JobDefVO> jobDefVOs = jobService.getJobDefs(ScheduleConst.GROUP_NAME_ALERT);
        model.addAttribute("jobDefs", jobDefVOs);
        return "schedule/alertdef/alertDefList";
    }

    @RequestMapping("/listData.d")
    @ViewWrapper(wrapped = false)
    public String listData(Model model) {
        //
        List<JobDefVO> jobDefVOs = jobService.getJobDefs(ScheduleConst.GROUP_NAME_ALERT);
        model.addAttribute("jobDefs", jobDefVOs);
        return "schedule/alertdef/list-data";
    }

    @RequestMapping("/paraListData.d")
    @ViewWrapper(wrapped = false)
    public String paraListData(Model model) {
        //
        List<JobDefVO> jobDefVOs = jobService.getJobDefs(ScheduleConst.GROUP_NAME_ALERT);
        model.addAttribute("jobDefs", jobDefVOs);
        return "schedule/alertdef/param-list";
    }

    @RequestMapping("/add.d")
    @ViewWrapper(wrapped = false, onlyAjax = true)
    public String add(Model model) {
        model.addAttribute("editType", "add");
        parameters = new ArrayList<JobParameter>();
        JobParameter para = new JobParameter();
        parameters.add(para);
        model.addAttribute("parameters", parameters);
        return "schedule/alertdef/alertDefForm";
    }

    @RequestMapping("/edit.d")
    @ViewWrapper(wrapped = false, onlyAjax = true)
    public String edit(Model model, String name, String group) {
        if (StringUtils.isEmpty(group))
            group = ScheduleConst.GROUP_NAME_ALERT;
        JobDefVO defVO = jobService.getJobDetail(name, group);
        model.addAttribute("jobdef", defVO);
        model.addAttribute("editType", "edit");
        parameters = defVO.getParameters();
        // 参数为空时增加一个空参数
        if (parameters.size() < 1) {
            JobParameter para = new JobParameter();
            parameters.add(para);
        }
        model.addAttribute("parameters", parameters);
        return "schedule/alertdef/alertDefForm";
    }

    @RequestMapping("/save.d")
    @ResponseBody
    public Object save(JobDefVO jobDefVO) {
        jobDefVO.setGroup(ScheduleConst.GROUP_NAME_ALERT);
        try {
            jobService.saveJobDef(jobDefVO);
        } catch (BusinessException e) {
        	logger.error(e.getMessage(),e);
            return new AjaxResult(AjaxResult.STATUS_ERROR, e.getMessage());
        }
        return AjaxResult.SUCCESS;
    }

    @RequestMapping("/delete.d")
    @ResponseBody
    public Object delete(String... name) {
        List<JobKey> jobKeys = new ArrayList<JobKey>();
        if (name != null && name.length > 0) {
            for (String jobName : name) {
                jobKeys.add(JobKey.jobKey(jobName, ScheduleConst.GROUP_NAME_ALERT));
            }
            jobService.delJobDef(jobKeys);
        }
        return AjaxResult.SUCCESS;
    }

    @RequestMapping("/deploy.d")
    @ViewWrapper(wrapped = false, onlyAjax = true)
    public String deploy(Model model, String name, String group) {
        JobDefVO defVO = jobService.getJobDetail(name, ScheduleConst.GROUP_NAME_ALERT);
        JobTriggerVO trigger = new JobTriggerVO();
        model.addAttribute("jobdef", defVO);
        parameters = defVO.getParameters();
        model.addAttribute("parameters", parameters);
        model.addAttribute("trigger", trigger);
        return "schedule/alertdef/deployForm";
    }

    @RequestMapping("/deployJob.d")
    @ResponseBody
    public Object deployJob(JobTriggerVO jobTriggerVO) {
        jobService.deployJobTrigger(jobTriggerVO);
        return AjaxResult.SUCCESS;
    }

    @RequestMapping("/synchroCheck.d")
    @ResponseBody
    public Object synchroCheck(String name) {
        String group = ScheduleConst.GROUP_NAME_ALERT;
        JobDefVO defVO = jobService.getJobDetail(name, group);
        if (defVO.getName() == null) {
            return true;
        } else {
            return false;
        }
    }
}
