package com.gdsp.platform.schedule.model;

import java.text.DecimalFormat;

import com.gdsp.dev.base.lang.DDateTime;
import com.gdsp.dev.core.model.entity.BaseEntity;

/**
 * 
 * @ClassName: SchedAccessLogVO
 * (调度执行日志)
 * @author wwb
 * @date 2015年10月20日
 */
public class SchedExecLogVO extends BaseEntity {

    private static final long  serialVersionUID = 1L;
    public final static String TABLE_NAME       = "sched_accesslog";
    private String             job_name;                            //
    private String             job_group;                           //
    private String             trigger_name;                        //
    private String             trigger_group;                       //
    private DDateTime          begintime;
    private DDateTime          endtime;
    private float                elapsedtime;
    private Integer            result;
    private String             memo;

    public String getJob_name() {
        return job_name;
    }

    public void setJob_name(String job_name) {
        this.job_name = job_name;
    }

    public String getJob_group() {
        return job_group;
    }

    public void setJob_group(String job_group) {
        this.job_group = job_group;
    }

    public String getTrigger_name() {
        return trigger_name;
    }

    public void setTrigger_name(String trigger_name) {
        this.trigger_name = trigger_name;
    }

    public String getTrigger_group() {
        return trigger_group;
    }

    public void setTrigger_group(String trigger_group) {
        this.trigger_group = trigger_group;
    }

    public DDateTime getBegintime() {
        return begintime;
    }

    public void setBegintime(DDateTime begintime) {
        this.begintime = begintime;
    }

    public DDateTime getEndtime() {
        return endtime;
    }

    public void setEndtime(DDateTime endtime) {
        this.endtime = endtime;
    }

    public String getElapsedtime() {
    	DecimalFormat decimalFormat = new DecimalFormat("##0.00");
    	float time = elapsedtime / 1000 ;
        return 	decimalFormat.format(time);
    }

    public void setElapsedtime(int elapsedtime) {
        this.elapsedtime = elapsedtime;
    }

    public Integer getResult() {
        return result;
    }

    public void setResult(Integer result) {
        this.result = result;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }
}
