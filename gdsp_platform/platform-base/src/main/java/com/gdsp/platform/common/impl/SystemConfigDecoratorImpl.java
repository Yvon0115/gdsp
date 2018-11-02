package com.gdsp.platform.common.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.ModelAndView;

import com.gdsp.dev.core.common.AppConfig;
import com.gdsp.dev.web.mvc.interceptor.IDecorator;
import com.gdsp.platform.config.customization.helper.SystemConfigConst;

/**
 * 系统配置数据包装实现类<br>
 * @author lijy
 *
 */
@Service
@Transactional
public class SystemConfigDecoratorImpl implements IDecorator {

    @Override
    public void handleModelAndView(ModelAndView mv) {
        // 获取首页启用状态
        String sysHomePageState = AppConfig.getProperty(SystemConfigConst.SYSTEMCONFIG_HOMEPAGE_STATE);
        mv.addObject("sysHomePageState", sysHomePageState);
        // 获取系统名称配置
        String systemName = AppConfig.getInstance().getString("view.systemName");
        mv.addObject("systemName", systemName);
    }
}
