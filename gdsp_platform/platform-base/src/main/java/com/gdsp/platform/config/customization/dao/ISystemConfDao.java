package com.gdsp.platform.config.customization.dao;

import java.util.List;

import com.gdsp.dev.persist.ext.MBDao;
import com.gdsp.platform.config.customization.model.SystemConfVO;
import com.gdsp.platform.config.global.model.DefDocVO;

/**
 * 系统配置Dao
 * @author lijy
 *@version 1.0 2016/8/16
 */
@MBDao
public interface ISystemConfDao {

    /**
     * 查询报表类型
     * @return
     */
    public List<DefDocVO> queryReportType();

    /**
     * 查询最新系统配置信息
     * @return
     */
    public SystemConfVO querySystemConf();

    /**
     * 添加系统配置信息
     * @param systemConf
     */
    public void insert(SystemConfVO systemConf);
    /**
     * 更新系统配置信息
     * @param systemConf
     */
    public void updateSystemConf(SystemConfVO systemConf);
}
