/*
 * Copyright(c) FastSpace Software 2014. All Rights Reserved.
 */
package com.gdsp.dev.core.cases.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gdsp.dev.core.common.AppContext;

/**
 * 基础服务对象
 * @author yaboocn
 * @version 1.0 2011-8-26
 * @since 1.6
 */
public class BaseService {

    /**
     * 日志对象
     */
    protected Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 取得上下文对象
     * @return 上下文对象
     */
    protected AppContext getContext() {
        return AppContext.getContext();
    }
}
