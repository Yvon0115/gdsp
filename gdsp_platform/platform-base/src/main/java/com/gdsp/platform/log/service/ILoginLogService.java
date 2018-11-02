package com.gdsp.platform.log.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.gdsp.platform.log.model.LoginLogVO;

public interface ILoginLogService {

    /**
     * 通过条件查询登陆日志
     * @param condition
     * @param page
     * @param sort
     * @return
     */
    public Page<LoginLogVO> queryPageByCondition(Pageable page, String queryParam,String type, String p_start_date, String p_end_date);
    
    /**
     * 获取用户登录数据list
     * @param queryParam
     * @param type
     * @param p_start_date
     * @param p_end_date
     * @return
     */
    public List<LoginLogVO> findListByCondition(String queryParam,String type, String p_start_date, String p_end_date);
    
    /**
     * 获取csv导出内容list
     */
    public List<String> getContentList(List<LoginLogVO>  loginList);
}
