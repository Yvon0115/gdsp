package com.gdsp.platform.workflow.service;

/**
 * 扩展事件服务
 * @author xl
 */
public interface IExtendEventService {

    void beforeEvent();

    void afterEvent();
}
