package com.gdsp.platform.schedule.model;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TimeZone;
import java.util.TreeSet;

import org.apache.commons.lang3.StringUtils;
import org.quartz.Scheduler;
import org.quartz.impl.triggers.AbstractTrigger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gdsp.platform.schedule.utils.FormatUtils;

/**
 * 任务触发器
 * @author pc
 * @since 2017年9月29日 下午2:18:06
 */
public class JobTriggerVO {
	private static final Logger logger = LoggerFactory.getLogger(JobTriggerVO.class);
    private String               name;                  // 名称
    private String               group                     = Scheduler.DEFAULT_GROUP;    // 业务类型（预警/任务）
    private String               jobName;               // 任务类型（类模板）
    private String               jobGroup                  = Scheduler.DEFAULT_GROUP;
    private int                  priority                  = AbstractTrigger.DEFAULT_PRIORITY;
    private int                  misfireInstruction        = AbstractTrigger.MISFIRE_INSTRUCTION_SMART_POLICY;
    private String               description;           // 描述
    private String               startTime;             // 开始时间
    private String               endTime;               // 结束时间
    private String               nextFireTime;          // 下次执行时间
    private String               previousFireTime;      
    private TimeZone   			 timeZone;              
    private String               triggerType;
    private String               triggerState;
    private String               expression;
    private String               execPolicy;
    private String               period;
    private String               execTime;
    private String               onceTime;
    private String               gapTime;               // 间隔时间
    private String               gaptype;               // 间隔时间单位 
    private String               bTime;
    private String               eTime;
    private String               jobState;              

	// 消息内容
    private String               messageinf;
    private List<JobParameter>   parameters                = new ArrayList<JobParameter>();
    private List<JobReceiver>    receivers                 = new ArrayList<JobReceiver>();
    private static final int     ALL_SPEC_INT              = 99;
    private static final int     NO_SPEC_INT               = 98;
    private static final Integer ALL_SPEC                  = new Integer(ALL_SPEC_INT);
    private static final Integer NO_SPEC                   = new Integer(NO_SPEC_INT);
    private TreeSet              daysOfMonth               = null;
    private TreeSet              daysOfWeek                = null;
    
    /** 周期设置方式：立即执行，且执行一次。 */
    public static final String   EXECPOLICY_WITHOUTDELAY   = "1";
    /** 周期设置方式：定时执行 */
    public static final String   EXECPOLICY_TIMER          = "2";
    /** 周期设置方式：通过CRON表达式定义 */
    public static final String   EXECPOLICY_CORNEXPRESSION = "3";
    
    /** 执行频率：单次 */
    public static final String   EXECTIME_ONCE             = "1";         
    /** 执行频率：多次 */
    public static final String   EXECTIME_MORE             = "2";         
    
    /** 间隔时间单位:时 */
    public static final String   GAPTYPE_HOUR              = "1";         // 小时
    /** 间隔时间单位:分 */
    public static final String   GAPTYPE_MINUTE            = "2";         // 分钟
    /** 间隔时间单位:秒 */
    public static final String   GAPTYPE_SECOND            = "3";         // 秒

    public void reset() {
        daysOfMonth = new TreeSet();
        daysOfWeek = new TreeSet();
        daysOfMonth.add(ALL_SPEC);
        for (int i = 1; i <= 31; i++) {
            daysOfMonth.add(new Integer(i));
        }
        daysOfWeek.add(NO_SPEC);
        setTimeZone(TimeZone.getDefault());
    }

    public JobTriggerVO() {
        super();
        reset();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public String getJobGroup() {
        return jobGroup;
    }

    public void setJobGroup(String jobGroup) {
        this.jobGroup = jobGroup;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public int getMisfireInstruction() {
        return misfireInstruction;
    }

    public void setMisfireInstruction(int misfireInstruction) {
        this.misfireInstruction = misfireInstruction;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getNextFireTime() {
        return nextFireTime;
    }

    public void setNextFireTime(String nextFireTime) {
        this.nextFireTime = nextFireTime;
    }

    public String getPreviousFireTime() {
        return previousFireTime;
    }

    public void setPreviousFireTime(String previousFireTime) {
        this.previousFireTime = previousFireTime;
    }

    public TimeZone getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(TimeZone timeZone) {
        this.timeZone = timeZone;
    }

    public String getTriggerType() {
        return triggerType;
    }

    public void setTriggerType(String triggerType) {
        this.triggerType = triggerType;
    }

    public String getTriggerState() {
        return triggerState;
    }

    public void setTriggerState(String triggerState) {
        this.triggerState = triggerState;
    }

    public String getExpression() {
        return expression;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }

    public String getExecPolicy() {
        return execPolicy;
    }

    public void setExecPolicy(String execPolicy) {
        this.execPolicy = execPolicy;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }
    
    public String getJobState() {
		return jobState;
	}

	public void setJobState(String jobState) {
		this.jobState = jobState;
	}

    public Integer[] getDaysOfMonthValues() {
        Integer[] list = new Integer[31];
        for (int i = 0; i < 31; i++) {
            list[i] = new Integer(i + 1);
        }
        return list;
    }

    public Integer[] getDaysOfMonthLabels() {
        return getDaysOfMonthValues();
    }

    public Integer[] getDaysOfMonth() {
    	Integer[] list = null;
    	if (daysOfMonth != null) {
    		list = new Integer[daysOfMonth.size()];
            int i = 0;
            for (Iterator it = daysOfMonth.iterator(); it.hasNext(); i++) {
                list[i] = (Integer) it.next();
            }
        }
        return list;
    }

    public void setDaysOfMonth(Integer[] val) {
        if (daysOfMonth != null) {
            daysOfMonth.clear();
        } else {
            daysOfMonth = new TreeSet();
        }
        for (Integer element : val) {
            daysOfMonth.add(element);
        }
        daysOfWeek.clear();
        daysOfWeek.add(NO_SPEC);
    }

    public String[] getDaysOfWeekLabels() {
        String[] list = { "周日", "周一", "周二", "周三", "周四", "周五", "周六" };
        return list;
    }

    public Integer[] getDaysOfWeekValues() {
        Integer[] list = new Integer[7];
        for (int i = 0; i < 7; i++) {
            list[i] = new Integer(i + 1);
        }
        return list;
    }

    public Integer[] getDaysOfWeek() {
    	Integer[] list = null;
    	if (daysOfWeek != null) {
    		list = new Integer[daysOfWeek.size()];
            int i = 0;
            for (Iterator it = daysOfWeek.iterator(); it.hasNext(); i++) {
                list[i] = (Integer) it.next();
            }
        }
        return list;
    }

    public void setDaysOfWeek(Integer[] val) {
        if (daysOfWeek != null) {
            daysOfWeek.clear();
        } else {
            daysOfWeek = new TreeSet();
        }
        for (Integer element : val) {
            daysOfWeek.add(element);
        }
        daysOfMonth.clear();
        daysOfMonth.add(NO_SPEC);
    }

    public String getCronExpression() {
        StringBuilder buf = new StringBuilder();
        // 执行频率：单次
        if (EXECTIME_ONCE.equals(getExecTime())) {    
            try {
                Date onceTime = FormatUtils.parseStringToTime(getOnceTime());    // 获取单次执行的时间点
                buf.append(onceTime.getSeconds());
                buf.append(" ");
                buf.append(onceTime.getMinutes());
                buf.append(" ");
                buf.append(onceTime.getHours());
            } catch (ParseException e) {
            	logger.error(e.getMessage(),e);
            }
        } 
        // 执行频率：多次 
        else if (EXECTIME_MORE.equals(getExecTime())) {    
            buf.append("0");
            /** 时分秒三选一，互斥关系 */
            // 间隔为“秒”
            if (GAPTYPE_SECOND.equals(getGaptype())) {     
                buf.append("/" + getGapTime());
                buf.append(" ");
                buf.append("*");  // 以秒为间隔的任务，每分钟都执行
            }
            // 间隔为“分”
            else if (GAPTYPE_MINUTE.equals(getGaptype())) {
            	buf.append(" ");
            	buf.append("0");
            	buf.append("/" + getGapTime());
			}else{
	        	buf.append(" ");
				buf.append("0");
			}
            buf.append(" ");
            
            /* buf.append(" ");
            // 分
            buf.append("0");
            // CRON表达式生成策略更改 wqh 2017/2/6
            if (GAPTYPE_MINUTE.equals(getGaptype())) {
            	buf.append("0");    //
                buf.append("/" + getGapTime());
            }else{
            	buf.append("0");    //CRON表达式生成策略更改 lijun 2017/4/21  *表示代表整个时间段，这里需要明确为0
            }
            buf.append(" ");
            */
            
            // 任务的开始时间和结束时间
            if (StringUtils.isNoneEmpty(getbTime())) {
                buf.append(getbTime());
                if (StringUtils.isNoneEmpty(geteTime())) {
                    buf.append("-" + geteTime());
                }
                // 如果没有结束时间，则默认结束时间为23时
                else if (!GAPTYPE_HOUR.equals(getGaptype())) {
                    buf.append("-23");
                }
            }
            // “没有开始时间”本身逻辑上是有问题的，默认从0点开始按照间隔时间来执行
            else {
                buf.append("*");
            }
            // 间隔为“时”
            if (GAPTYPE_HOUR.equals(getGaptype())) {
                buf.append("/" + getGapTime());
            }
            buf.append(" ");
        } 
        // 不会存在这种“每时每分每秒都在执行”的任务的
        else {
            buf.append("*");
            buf.append(" ");
            buf.append("*");
            buf.append(" ");
            buf.append("*");
        }
        buf.append(" ");
        buf.append(getExpressionSetSummary(daysOfMonth));
        buf.append(" ");
        buf.append("*");
        buf.append(" ");
        buf.append(getExpressionSetSummary(daysOfWeek));
        buf.append(" ");
        buf.append("*");
        buf.append("\n");
        return buf.toString();
    }

    private String getExpressionSetSummary(Set set) {
        if (set.contains(NO_SPEC)) {
            return "?";
        }
        if (set.contains(ALL_SPEC)) {
            return "*";
        }
        StringBuilder buf = new StringBuilder();
        Iterator itr = set.iterator();
        boolean first = true;
        while (itr.hasNext()) {
            Integer iVal = (Integer) itr.next();
            String val = iVal.toString();
            if (!first) {
                buf.append(",");
            }
            buf.append(val);
            first = false;
        }
        return buf.toString();
    }

    public String getExecTime() {
        return execTime;
    }

    public void setExecTime(String execTime) {
        this.execTime = execTime;
    }

    public String getOnceTime() {
        return onceTime;
    }

    public void setOnceTime(String onceTime) {
        this.onceTime = onceTime;
    }

    public String getGapTime() {
        return gapTime;
    }

    public void setGapTime(String gapTime) {
        this.gapTime = gapTime;
    }

    public String getGaptype() {
        return gaptype;
    }

    public void setGaptype(String gaptype) {
        this.gaptype = gaptype;
    }

    public String getbTime() {
        return bTime;
    }

    public void setbTime(String bTime) {
        this.bTime = bTime;
    }

    public String geteTime() {
        return eTime;
    }

    public void seteTime(String eTime) {
        this.eTime = eTime;
    }

    /**
     * @return
     */
    public List<JobParameter> getParameters() {
        return parameters;
    }

    public void addParameter(JobParameter param) {
        this.parameters.add(param);
    }

    public void setParameter(List<JobParameter> parameters) {
        this.parameters = parameters;
    }

    public String getMessageinf() {
        return messageinf;
    }

    public void setMessageinf(String messageinf) {
        this.messageinf = messageinf;
    }

    public List<JobReceiver> getReceivers() {
        return receivers;
    }

    public void setReceivers(List<JobReceiver> receivers) {
        this.receivers = receivers;
    }
}