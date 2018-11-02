package com.gdsp.platform.log.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.gdsp.dev.core.model.query.Condition;
import com.gdsp.dev.persist.ext.LogDao;
import com.gdsp.dev.persist.ext.MBDao;
import com.gdsp.platform.log.model.LoginLogVO;

//@MBDao
@LogDao
public interface ILoginLogDao {

    /**
     * 通过条件查询登陆日志
     * @param condition
     * @param page
     * @param sort
     * @return
     */
    public List<LoginLogVO> findListByCondition(@Param("condition") Condition condition);
}
