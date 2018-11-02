package com.gdsp.platform.workflow.model;

import com.gdsp.dev.base.lang.DDateTime;
import com.gdsp.dev.core.model.entity.AuditableEntity;

/**
 * 请假模型
 *
 */
public class LeaveVO extends AuditableEntity {

    private static final long serialVersionUID = 1L;
    private DDateTime         startTime;            // 开始时间
    private DDateTime         endTime;              // 结束时间 
    private String            phone;                // 联系电话
    private String            leaveType;            // 请假类型
    private String            leaveDay;             //请假天数
    private String            reason;               // 请假事由

    public String getLeaveDay() {
        return leaveDay;
    }

    public void setLeaveDay(String leaveDay) {
        this.leaveDay = leaveDay;
    }

    public DDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(DDateTime startTime) {
        this.startTime = startTime;
    }

    public DDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(DDateTime endTime) {
        this.endTime = endTime;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getLeaveType() {
        return leaveType;
    }

    public void setLeaveType(String leaveType) {
        this.leaveType = leaveType;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
