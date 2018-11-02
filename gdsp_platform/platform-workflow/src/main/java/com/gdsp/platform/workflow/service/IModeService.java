package com.gdsp.platform.workflow.service;


/**
 * 流程定义服务
 * @author zhaojianjun
 *
 */
public interface IModeService {
    /**
     * 保存流程定义
     * @param key 流程编码
     * @param name 流程名称
     * @param descriptor 流程描述
     * @param deploymentVO 流程部署对象
     */
    public void save(String key, String name, String descriptor);
}
