package com.gdsp.platform.schedule.web;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.quartz.CronExpression;
import org.quartz.JobKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gdsp.dev.base.exceptions.BusinessException;
import com.gdsp.dev.core.common.AppContext;
import com.gdsp.dev.core.model.query.Condition;
import com.gdsp.dev.core.model.query.IExpression;
import com.gdsp.dev.core.model.query.ValueExpression;
import com.gdsp.dev.web.mvc.ext.ViewWrapper;
import com.gdsp.dev.web.mvc.model.AjaxResult;
import com.gdsp.platform.grant.user.model.UserVO;
import com.gdsp.platform.grant.user.service.IUserQueryPubService;
import com.gdsp.platform.grant.utils.GrantConst;
import com.gdsp.platform.schedule.model.JobDefVO;
import com.gdsp.platform.schedule.model.JobParameter;
import com.gdsp.platform.schedule.model.JobTriggerVO;
import com.gdsp.platform.schedule.service.IJobService;
import com.gdsp.platform.schedule.utils.ScheduleConst;

/**
 * 任务类型管理
 * Created on 2015/8/21
 */
@Controller
@RequestMapping("schedule/jobdef")
public class JobDefContoller {

    private static final Logger  logger     = LoggerFactory.getLogger(JobDefContoller.class);
    @Autowired
    private IJobService          jobService;
    @Autowired
    private IUserQueryPubService userQueryPubService;
    private List<JobParameter>   parameters = new ArrayList<JobParameter>();

    @RequestMapping("/jobDefList.d")
    public String jobDefList(Model model) {
        //
        List<JobDefVO> jobDefVOs = jobService.getJobDefs(ScheduleConst.GROUP_NAME_JOB);
        model.addAttribute("jobDefs", jobDefVOs);
        return "schedule/jobdef/jobDefList";
    }

    @RequestMapping("/listData.d")
    @ViewWrapper(wrapped = false)
    public String listData(Model model) {
        //
        List<JobDefVO> jobDefVOs = jobService.getJobDefs(ScheduleConst.GROUP_NAME_JOB);
        model.addAttribute("jobDefs", jobDefVOs);
        return "schedule/jobdef/list-data";
    }

    @RequestMapping("/add.d")
    @ViewWrapper(wrapped = false, onlyAjax = true)
    public String add(Model model) {
        model.addAttribute("editType", "add");
        //
        parameters = new ArrayList<JobParameter>();
        JobParameter para = new JobParameter();
        parameters.add(para);
        model.addAttribute("parameters", parameters);
        return "schedule/jobdef/jobDefForm";
    }

    @RequestMapping("/edit.d")
    @ViewWrapper(wrapped = false, onlyAjax = true)
    public String edit(Model model, String name, String group) {
        if (StringUtils.isEmpty(group))
            group = ScheduleConst.GROUP_NAME_JOB;
        JobDefVO defVO = jobService.getJobDetail(name, group);
        model.addAttribute("jobdef", defVO);
        model.addAttribute("editType", "edit");
        //
        parameters = defVO.getParameters();
        // 参数为空时增加一个空参数
        if (parameters.size() < 1) {
            JobParameter para = new JobParameter();
            parameters.add(para);
        }
        model.addAttribute("parameters", parameters);
        return "schedule/jobdef/jobDefForm";
    }

    @RequestMapping("/save.d")
    @ResponseBody
    public Object save(JobDefVO jobDefVO) {
        jobDefVO.setGroup(ScheduleConst.GROUP_NAME_JOB);
        try {
            jobService.saveJobDef(jobDefVO);
        } catch (BusinessException e) {
            logger.error(e.getMessage(), e);
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
                jobKeys.add(JobKey.jobKey(jobName, ScheduleConst.GROUP_NAME_JOB));
            }
            jobService.delJobDef(jobKeys);
        }
        return AjaxResult.SUCCESS;
    }

    @RequestMapping("/deploy.d")
    @ViewWrapper(wrapped = false, onlyAjax = true)
    public String deploy(Model model, String name, String group) {
        JobDefVO defVO = jobService.getJobDetail(name, ScheduleConst.GROUP_NAME_JOB);
        JobTriggerVO trigger = new JobTriggerVO();
        trigger.setGroup(ScheduleConst.GROUP_NAME_JOB);
        parameters = defVO.getParameters();
        model.addAttribute("parameters", parameters);
        model.addAttribute("jobdef", defVO);
        model.addAttribute("trigger", trigger);
        return "schedule/jobdef/deployForm";
    }

    @RequestMapping("/deployJob.d")
    @ResponseBody
    public Object deployJob(JobTriggerVO jobTriggerVO) {
        jobService.deployJobTrigger(jobTriggerVO);
        return AjaxResult.SUCCESS;
    }

    // 接受者列表
    @RequestMapping("/receiverList.d")
    @ViewWrapper(wrapped = false)
    public String receiverList(Model model, String receivers, String receiveridscode) {
        //权限拆分---------------------------------------2016.12.26
        // Condition condition = new Condition();
        if (StringUtils.isNotEmpty(receivers)) {
            // condition.addExpression("u.id", receivers, "in");
            // Sorter sort = new Sorter(Direction.ASC, "account");
            // List<UserVO> users = userService.queryUserListByCondition(condition, sort);
            List<UserVO> users = userQueryPubService.queryUserList(receivers, null);
            model.addAttribute("users", users);
        }
        model.addAttribute("receivers", receivers);
        //缓存接受者ID字段
        model.addAttribute("receiveridscode", receiveridscode);
        return "schedule/pub/receiverids-dlg";
    }

    @RequestMapping("/receiverListData.d")
    @ViewWrapper(wrapped = false)
    public String receiverListData(Model model, String receivers, Condition condition) {
        //权限拆分---------------------------------------2016.12.26
        // Condition condition = new Condition();
        if (StringUtils.isNotEmpty(receivers)) {
            UserVO uservo = new UserVO();
            List<IExpression> expressions = condition.getExpressions();
            //页面条件放入VO，还有一个页面传来的Pk_org
            if (expressions != null && expressions.size() > 0) {
                for (int i = 0; i < expressions.size(); i++) {
                    ValueExpression expression = (ValueExpression) expressions.get(i);
                    if (expression.getPropertyName().equals("username")) {
                        uservo.setUsername((String) expression.getValue());
                    }
                    if (expression.getPropertyName().equals("account")) {
                        uservo.setAccount((String) expression.getValue());
                    }
                }
            }
            //  condition.addExpression("u.id", receivers, "in");
            // Sorter sort = new Sorter(Direction.ASC, "account");
            //  List<UserVO> users = userService.queryUserListByCondition(condition, sort);
            List<UserVO> users = userQueryPubService.queryUserList(receivers, null);
            String loginUserID = AppContext.getContext().getContextUserId();
            UserVO user = userQueryPubService.load(loginUserID);
            Integer userType = user.getUsertype();
            String pk_org = user.getPk_org();
            uservo.setPk_org(pk_org);
            uservo.setUsertype(userType);
            List<UserVO> usersByConds = userQueryPubService.queryUserByCondtion(uservo, loginUserID, true);
            users.retainAll(usersByConds);
            model.addAttribute("users", users);
        }
        model.addAttribute("receivers", receivers);
        return "schedule/pub/userlist-data";
    }

    // 可添加用户列表
    @RequestMapping("/selUserList.d")
    @ViewWrapper(wrapped = false, onlyAjax = true)
    public String selUserList(Model model, String receivers) {
        //权限拆分---------------------------------------2016.12.26
        String userId = AppContext.getContext().getContextUserId();
        // 增加过滤已有用户查询条件
        String addcond = "";
        if (StringUtils.isNotEmpty(receivers)) {
            addcond = "u.id not in ( " + receivers + " )";
        }
        // model.addAttribute("users", userService.queryUserListByUserAndCond(userId, true, addcond));
        model.addAttribute("users", userQueryPubService.queryUserListByUserAndCond(userId, false, addcond));
        model.addAttribute("receivers", receivers);
        return "schedule/pub/userlist-add";
    }

    @RequestMapping("/selUserListData.d")
    @ViewWrapper(wrapped = false, onlyAjax = true)
    public String selUserListData(Model model, Condition condition, String receivers) {
        //权限拆分---------------------------------------2016.12.26
        String userId = AppContext.getContext().getContextUserId();
        // 增加过滤已有用户查询条件
        String condValue = condition.getFreeValue();
        //将单引号替换成两个单引号，避免sql出现'%'%'的情况。     lijun  20170420
        if (condValue != null) {
            condValue = condValue.replaceAll("'", "''");
        }
        StringBuilder addcond = new StringBuilder(""); // 额外条件
        addcond.append(" u.id <>'" + userId + "' ");
        if (StringUtils.isNotEmpty(receivers)) {
            addcond.append(" and  u.id not in ( " + receivers + " )");
            //            if(StringUtils.isNotEmpty(condValue))
        }
        List<String> freeConds = condition.getFreeConditions();
        if (freeConds != null) {
            addcond.append(" and ( ");
            for (int i = 0; i < freeConds.size(); i++) {
                if (i == freeConds.size() - 1) {
                    addcond.append(freeConds.get(i) + "  like '%" + condValue + "%' ");
                } else {
                    addcond.append(freeConds.get(i) + " like  '%" + condValue + "%'  or  ");
                }
            }
            addcond.append(" )");
        }
        String addConds = addcond.toString();
        // model.addAttribute("users", userService.queryUserListByUserAndCond(userId, true, addcond));
        model.addAttribute("users", userQueryPubService.queryUserListByUserAndCond(userId, false, addConds));
        return "schedule/pub/userlist-add-data";
    }

    /**
     * 重名校验
     */
    @RequestMapping("/synchroCheck.d")
    @ResponseBody
    public Object synchroCheck(String name) {
        String group = ScheduleConst.GROUP_NAME_JOB;
        JobDefVO defVO = jobService.getJobDetail(name, group);
        if (defVO.getName() == null) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * CRON表达式校验
     * @author wqh
     * @since 2017年2月7日
     */
    @RequestMapping("/cronExpressionCheck.d")
    @ResponseBody
    public Object cronExpressionCheck(String expression) {
        boolean result = CronExpression.isValidExpression(expression);
        return result;
    }
}
