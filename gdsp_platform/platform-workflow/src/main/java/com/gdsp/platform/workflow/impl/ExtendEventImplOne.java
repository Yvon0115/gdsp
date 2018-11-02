package com.gdsp.platform.workflow.impl;

import com.gdsp.platform.workflow.service.IExtendEventService;

public class ExtendEventImplOne implements IExtendEventService {

    @Override
    public void beforeEvent() {
        System.out.println("ExtendEventImplOne.beforeEvent");
    }

    @Override
    public void afterEvent() {
        System.out.println("ExtendEventImplOne.afterEvent");
    }
}
