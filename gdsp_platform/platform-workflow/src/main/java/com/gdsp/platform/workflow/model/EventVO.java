package com.gdsp.platform.workflow.model;

import com.gdsp.dev.core.model.entity.AuditableEntity;

/**
 * 预置事件表
 * @author gdsp
 *
 */
public class EventVO extends AuditableEntity {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private String            eventCode;            // 事件编码
    private String            eventName;            // 事件名称
    private String            eventType;            // 事件类型
    private String            formTypeId;           // 单据类型ID
    private String            implement;            // 实现类
    private String            note;                 // 备注

    public String getEventCode() {
        return eventCode;
    }

    public void setEventCode(String eventCode) {
        this.eventCode = eventCode;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public String getFormTypeId() {
        return formTypeId;
    }

    public void setFormTypeId(String formTypeId) {
        this.formTypeId = formTypeId;
    }

    public String getImplement() {
        return implement;
    }

    public void setImplement(String implement) {
        this.implement = implement;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
