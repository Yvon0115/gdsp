package com.gdsp.platform.common.impl;

import java.util.Properties;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gdsp.dev.core.common.AppConfig;
import com.gdsp.dev.core.common.AppContext;
import com.gdsp.dev.core.common.IAppModule;
import com.gdsp.platform.config.customization.helper.SystemConfigConst;
import com.gdsp.platform.config.customization.impl.SystemConfImpl;
import com.gdsp.platform.config.customization.model.SystemConfVO;
import com.gdsp.platform.config.customization.service.ISystemConfService;

@Service
@Transactional
public class AppModuleImpl implements IAppModule {

    @Override
    public String[] getDependModules() {
        return null;
    }

    @Override
    public boolean isInit() {
        return false;
    }

    @Override
    public void init() {
        ISystemConfService service = AppContext.lookupBean(SystemConfImpl.class);
        // 查询系统配置信息
        SystemConfVO systemConf = service.querySystemConf();
        Properties properties = AppConfig.getInstance().getConfig();
        // 系统配置首页信息、报表信息放在AppConfig中
        if (null != systemConf) {
            properties.setProperty(SystemConfigConst.SYSTEMCONFIG_HOMEPAGE_STATE, systemConf.getSysHomeState());
            if (!StringUtils.isEmpty(systemConf.getSysHomeUrl())) {
                properties.setProperty(SystemConfigConst.SYSTEMCONFIG_HOMEPAGE_URL, systemConf.getSysHomeUrl());
            } else {
                properties.setProperty(SystemConfigConst.SYSTEMCONFIG_HOMEPAGE_URL, "");
            }
            if (!StringUtils.isEmpty(systemConf.getReportInts())) {
                properties.setProperty(SystemConfigConst.SYSTEMCONFIG_REPORTINTSINFO, systemConf.getReportInts());
            } else {
                properties.setProperty(SystemConfigConst.SYSTEMCONFIG_REPORTINTSINFO, "");
            }
        }
    }

    @Override
    public void destory() {}
}
