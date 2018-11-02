package com.gdsp.platform.schedule.web;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gdsp.dev.base.lang.DDate;
import com.gdsp.platform.common.utils.DateUtils;
import com.gdsp.dev.core.model.param.Sorter;
import com.gdsp.dev.core.model.query.Condition;
import com.gdsp.dev.web.mvc.ext.ViewWrapper;
import com.gdsp.dev.web.mvc.model.AjaxResult;
import com.gdsp.platform.schedule.model.SchedExecLogVO;
import com.gdsp.platform.schedule.service.ISchedExecLogService;

//修改人：lijun
//修改时间：2017-4-10
//修改原因： 默认加载开始时间从当天起往前近一个月的日志，type生效;
//其他操作: type失效，查询条件按照begintime、endtime
@Controller
@RequestMapping("schedule/joblog")
public class JobLogController {

    @Autowired
    private ISchedExecLogService logService;

    @RequestMapping("/list.d")
    public String list(Model model, Condition condition, Pageable pageable, String schedType, String p_start_date, String p_end_date) {
        Sorter sort = new Sorter(Direction.DESC, "begintime");
        condition.addExpression("job_group", schedType);
        // 默认加载开始时间从当天起往前近一个月的日志  lijun 20170411
        condition.addExpression("begintime", new DDate(DateUtils.addMonth(new Date(), -1)).getMillis() / 1000, ">=");
        condition.addExpression("endtime", new DDate(DateUtils.addMonth(new Date(), 0)).getDateAfter(1).getMillis() / 1000, "<");
        Page<SchedExecLogVO> logPages = logService.findSchedExecLogByCondition(condition, pageable, sort);
        model.addAttribute("schedType", schedType);
        model.addAttribute("logPages", logPages);
        return "schedule/joblog/list";
    }

    @RequestMapping("/listData.d")
    @ViewWrapper(wrapped = false)
    public String listData(Model model, Condition condition, Pageable pageable, String type, String p_start_date, String p_end_date, String schedType) {
        Sorter sort = new Sorter(Direction.DESC, "begintime");
        condition.addExpression("job_group", schedType);
        // 自由
        /*
        if (ScheduleConst.freeString.equals(type) || StringUtils.isEmpty(type)) {
            if (StringUtils.isNotBlank(p_start_date)) {
                p_start_date = p_start_date + " 00:00:00";
                condition.addExpression("begintime", new DDate(p_start_date).getMillis() / 1000, ">=");
            }
            if (StringUtils.isNotBlank(p_end_date)) {
                p_end_date = p_end_date + " 23:59:59";
                condition.addExpression("endtime", new DDate(p_end_date).getDateAfter(1).getMillis() / 1000, "<=");
            }
        }
        //今天
        else if (ScheduleConst.todayString.equals(type)) {
            condition.addExpression("endtime", new DDate(FormatUtils.getDayAsString(new Date())).getMillis() / 1000, ">=");
        }
        //昨天
        else if (ScheduleConst.yestodayString.equals(type)) {
            condition.addExpression("endtime", new DDate(FormatUtils.addDay(FormatUtils.getDayAsString(new Date()), -1)).getMillis() / 1000, ">=");
            condition.addExpression("endtime", new DDate(FormatUtils.getDayAsString(new Date())).getMillis() / 1000, "<");
        }
        //近7天
        else if (ScheduleConst.weekString.equals(type)) {
            condition.addExpression("endtime", new DDate(FormatUtils.addDay(FormatUtils.getDayAsString(new Date()), -6)).getMillis() / 1000, ">=");
        }
        //近30天
        else if (ScheduleConst.monthString.equals(type)) {
            condition.addExpression("endtime", new DDate(FormatUtils.addMonth(FormatUtils.getDayAsString(new Date()), -1)).getMillis() / 1000, ">=");
        }
        
        */
        if (StringUtils.isNotEmpty(p_start_date)) {
            condition.addExpression("begintime", new DDate(p_start_date).getMillis() / 1000, ">=");
        }
        if (StringUtils.isNotEmpty(p_end_date)) {
            condition.addExpression("endtime", new DDate(p_end_date).getDateAfter(1).getMillis() / 1000, "<");
        }
        Page<SchedExecLogVO> logPages = logService.findSchedExecLogByCondition(condition, pageable, sort);
        model.addAttribute("logPages", logPages);
        if ("alert".equals(schedType))
            return "schedule/joblog/alert-list-data";
        else
            return "schedule/joblog/job-list-data";
    }

    @RequestMapping("/delete.d")
    @ResponseBody
    public Object delete(String... id) {
        if (id != null && id.length > 0) {
            logService.deletes(id);
        }
        return AjaxResult.SUCCESS;
    }

    @RequestMapping("/logInf.d")
    @ViewWrapper(wrapped = false)
    public String logInf(Model model, String id) {
        SchedExecLogVO logVO = logService.logInf(id);
        model.addAttribute("logVO", logVO);
        return "schedule/joblog/logInfo";
    }
}
