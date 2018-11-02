package com.gdsp.platform.workflow.impl;

import com.gdsp.platform.workflow.service.IExtendEventService;

public class ExtendEventImplTwo implements IExtendEventService {

    @Override
    public void beforeEvent() {
        System.out.println("ExtendEventImplTwo.beforeEvent");
    }

    @Override
    public void afterEvent() {
        System.out.println("ExtendEventImplTwo.afterEvent");
    }
}
