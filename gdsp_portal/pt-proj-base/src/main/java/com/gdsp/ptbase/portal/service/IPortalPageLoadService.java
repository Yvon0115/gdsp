package com.gdsp.ptbase.portal.service;

import com.gdsp.ptbase.portal.model.IPortalPage;

/**
 * 页面加载服务
 *
 * @author paul.yang
 * @version 1.0 2015-7-30
 * @since 1.6
 */
public interface IPortalPageLoadService {

    /**
     * 加载界面配置
     * @param key 界面标识
     * @return portal配置对象
     */
    IPortalPage loadPortalPage(String key);
}
