package com.gdsp.platform.schedule.web;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.quartz.TriggerKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gdsp.dev.web.mvc.ext.ViewWrapper;
import com.gdsp.dev.web.mvc.model.AjaxResult;
import com.gdsp.platform.grant.user.model.UserVO;
import com.gdsp.platform.grant.user.service.IUserQueryPubService;
import com.gdsp.platform.schedule.model.JobParameter;
import com.gdsp.platform.schedule.model.JobReceiver;
import com.gdsp.platform.schedule.model.JobTriggerVO;
import com.gdsp.platform.schedule.service.IJobService;
import com.gdsp.platform.schedule.utils.ScheduleConst;

/**
 * 任务管理
 * Created on 2015/8/26
 */
@Controller
@RequestMapping("schedule/job")
public class JobContoller {

    @Autowired
    private IJobService  jobService;
    @Autowired
    private IUserQueryPubService userQueryPubService;

    @RequestMapping("/jobList.d")
    public String jobList(Model model) {
        //
        List<JobTriggerVO> jobTriggers = jobService.getTriggersInfo(ScheduleConst.GROUP_NAME_JOB);
        model.addAttribute("jobTriggers", jobTriggers);
        return "schedule/job/jobList";
    }

    @RequestMapping("/listData.d")
    @ViewWrapper(wrapped = false)
    public String listData(Model model) {
        //
        List<JobTriggerVO> jobTriggers = jobService.getTriggersInfo(ScheduleConst.GROUP_NAME_JOB);
        model.addAttribute("jobTriggers", jobTriggers);
        return "schedule/job/list-data";
    }

    @RequestMapping("/showJob.d")
    @ViewWrapper(wrapped = false, onlyAjax = true)
    public String showJob(Model model, String name, String group) {
        JobTriggerVO job = jobService.getJob(name, group);
        model.addAttribute("job", job);
        // 参数
        List<JobParameter> parameters = job.getParameters();
        model.addAttribute("parameters", parameters);
        List<JobReceiver> receivers = job.getReceivers();
        // 消息接受者
        for (JobReceiver receiver : receivers) {
            // 站内消息设置信息
            if (ScheduleConst.SENDTYPE_MESSAGE == receiver.getSendtype())
                model.addAttribute("messagereceivers", receiver);
            // 邮件设置信息
            else if (ScheduleConst.SENDTYPE_MAIL == receiver.getSendtype())
                model.addAttribute("mailreceivers", receiver);
        }
        return "schedule/job/jobForm";
    }

    @RequestMapping("/delete.d")
    @ResponseBody
    public Object delete(String[] status, String... name) {
    	
    	// 操作菜单 - 删除
    	if (status != null && status.length > 0 ) {
			List<String> list = new ArrayList<String>(Arrays.asList(status));
			if (list.contains(ScheduleConst.TRIGGER_STATE_EXECUTING)) {
				AjaxResult ajaxResult = new AjaxResult(AjaxResult.STATUS_ERROR,"无法删除执行中的任务，请暂停后再试。");
				return ajaxResult;
			}
		}else{
			// 批量删除
			if (name !=null) {
				List<TriggerKey> triggerKeys = new ArrayList<TriggerKey>();
				String[] names = name[0].substring(1,name[0].length()-1).replaceAll("\"","").split(",");
				for (int i = 0; i < names.length; i++) {
//					System.out.println(names[i]);
					String triggerName = names[i];
					triggerKeys.add(new TriggerKey(triggerName, ScheduleConst.GROUP_NAME_JOB));
				}
				jobService.delJob(triggerKeys);
				return AjaxResult.SUCCESS;
			}
		}
    	
        if (name != null && name.length > 0) {
            List<TriggerKey> triggerKeys = new ArrayList<TriggerKey>();
            for (String triggerName : name) {
                triggerKeys.add(new TriggerKey(triggerName, ScheduleConst.GROUP_NAME_JOB));
            }
            jobService.delJob(triggerKeys);
        }
        return AjaxResult.SUCCESS;
    }

    @RequestMapping("/stopJob.d")
    @ResponseBody
    public Object stopJob(Model model, String name, String group) {
        jobService.stopJob(name, group);
        return AjaxResult.SUCCESS;
    }

    @RequestMapping("/restartJob.d")
    @ResponseBody
    public Object restartJob(Model model, String name, String group) {
        jobService.restartJob(name, group);
        ;
        return AjaxResult.SUCCESS;
    }

    @RequestMapping("/startNowJob.d")
    @ResponseBody
    public Object startNowJob(Model model, String name, String group) {
        jobService.startNowJob(name, group);
        return AjaxResult.SUCCESS;
    }

    @RequestMapping("/receiverShowList.d")
    @ViewWrapper(wrapped = false)
    public String receiverShowList(Model model, String receivers, String receiveridscode) {
       // Condition condition = new Condition();
        if (StringUtils.isNotEmpty(receivers)) {
           // condition.addExpression("u.id", receivers, "in");
           //Sorter sort = new Sorter(Direction.ASC, "account");
          //  List<UserVO> users = userService.queryUserListByCondition(condition, sort);
            List<UserVO> users = userQueryPubService.queryUserList(receivers, null);
            model.addAttribute("users", users);
        }
        model.addAttribute("receivers", receivers);
        //缓存接受者ID字段
        model.addAttribute("receiveridscode", receiveridscode);
        return "schedule/pub/receiveridsShow-dlg";
    }
}
