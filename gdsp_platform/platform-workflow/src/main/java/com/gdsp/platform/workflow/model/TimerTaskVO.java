package com.gdsp.platform.workflow.model;

import com.gdsp.dev.base.lang.DDateTime;
import com.gdsp.dev.core.model.entity.AuditableEntity;

/**
 * 定时任务表
 * 
 * @author gdsp
 *
 */
public class TimerTaskVO extends AuditableEntity {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private String            deploymentId;         // 流程部署ID
    private String            taskId;               // 流程任务ID
    private int               execType;             // 处理方式
    private String            formTypeId;           // 单据类型ID
    private String            actId;                // 活动节点ID
    private int               length;               // 定时长度
    private String            unit;                 // 长度单位
    private int               isWorkdays;           // 是否工作日
    private DDateTime         taskCreateTime;       //任务创建时间

    public String getDeploymentId() {
        return deploymentId;
    }

    public void setDeploymentId(String deploymentId) {
        this.deploymentId = deploymentId;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public int getExecType() {
        return execType;
    }

    public void setExecType(int execType) {
        this.execType = execType;
    }

    public String getFormTypeId() {
        return formTypeId;
    }

    public void setFormTypeId(String formTypeId) {
        this.formTypeId = formTypeId;
    }

    public String getActId() {
        return actId;
    }

    public void setActId(String actId) {
        this.actId = actId;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public int getIsWorkdays() {
        return isWorkdays;
    }

    public void setIsWorkdays(int isWorkdays) {
        this.isWorkdays = isWorkdays;
    }

    public DDateTime getTaskCreateTime() {
        return taskCreateTime;
    }

    public void setTaskCreateTime(DDateTime taskCreateTime) {
        this.taskCreateTime = taskCreateTime;
    }
}
