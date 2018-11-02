package com.gdsp.platform.config.customization.service;

import java.util.List;

import com.gdsp.platform.config.customization.model.SystemConfVO;
import com.gdsp.platform.config.global.model.DefDocVO;

public interface ISystemConfService {

    /**
     * 从字典表中查询报表类型，报表集成配置多选按钮组初始化数据源
     * @return
     */
    public List<DefDocVO> queryReportType();

    /**
     * 查询最新系统配置信息
     * @return
     */
    public SystemConfVO querySystemConf();

    /**
     * 保存系统配置信息
     * @param systemConf
     */
    public void saveSystemConf(SystemConfVO systemConf);
}
