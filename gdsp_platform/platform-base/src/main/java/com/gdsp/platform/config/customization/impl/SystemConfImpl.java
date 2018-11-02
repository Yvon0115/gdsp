package com.gdsp.platform.config.customization.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gdsp.platform.config.customization.dao.ISystemConfDao;
import com.gdsp.platform.config.customization.model.SystemConfVO;
import com.gdsp.platform.config.customization.service.ISystemConfService;
import com.gdsp.platform.config.global.model.DefDocVO;

@Service
@Transactional
public class SystemConfImpl implements ISystemConfService {

    @Autowired
    private ISystemConfDao systemConfDao;

    /**
     * 从字典表中查询报表类型，报表集成配置多选按钮组初始化数据源
     * @return
     */
    @Override
    public List<DefDocVO> queryReportType() {
        List<DefDocVO> reportTypeList = systemConfDao.queryReportType();
        return reportTypeList;
    }

    /**
     * 查询最新系统配置信息
     * @return
     */
    @Override
    public SystemConfVO querySystemConf() {
        SystemConfVO systemConf = systemConfDao.querySystemConf();
        return systemConf;
    }

    /**
     * 保存系统配置信息
     * @param systemConf
     */
    @Override
    public void saveSystemConf(SystemConfVO systemConf) {
        if(StringUtils.isEmpty(systemConf.getId()))
        {
        systemConfDao.insert(systemConf);
        }
        else{
            systemConfDao.updateSystemConf(systemConf);
        }
    }
}
