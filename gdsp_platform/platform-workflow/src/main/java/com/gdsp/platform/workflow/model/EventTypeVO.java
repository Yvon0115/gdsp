package com.gdsp.platform.workflow.model;

import com.gdsp.dev.core.model.entity.AuditableEntity;

/**
 * 预置事件类型表
 * @author gdsp
 *
 */
public class EventTypeVO extends AuditableEntity {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private String            eventTypeCode;        // 事件类型编码
    private String            eventTypeName;        // 事件类型名称
    private String            eventInterface;       // 事件类型接口

    public String getEventTypeCode() {
        return eventTypeCode;
    }

    public void setEventTypeCode(String eventTypeCode) {
        this.eventTypeCode = eventTypeCode;
    }

    public String getEventTypeName() {
        return eventTypeName;
    }

    public void setEventTypeName(String eventTypeName) {
        this.eventTypeName = eventTypeName;
    }

    public String getEventTypeInterface() {
        return eventInterface;
    }

    public void setEventTypeInterface(String eventTypeInterface) {
        this.eventInterface = eventTypeInterface;
    }
}
