package com.gdsp.platform.log.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.gdsp.dev.core.model.param.Sorter;
import com.gdsp.dev.core.model.query.Condition;
import com.gdsp.platform.log.model.ResAccessLogVO;
import com.gdsp.platform.log.model.ResAccessTopVO;

/**
 * 
* @ClassName: IResAccessLogService
* (resAccessLog接口)
* @author songxiang
* @date 2015年10月28日 上午11:28:33
*
 */
public interface IResAccessLogService {

    /**
     * 
    * @Title: deleteByCdn
    * (删除记录)
    * @param cond    参数说明
    *      */
    void deleteByCdn(Condition cond);

    /**
     * 
    * @Title: findListByCondition
    * (无分页查询)
    * @param cond
    * @param sort
    * @return    参数说明
    *      */
    List<ResAccessLogVO> findListByCondition(Condition cond, Sorter sort);

    /**
     * 
    * @Title: findPageByCondition
    * (分页查询)
    * @param condition
    * @param page
    * @param sort
    * @return    参数说明
    *      */
    Page<ResAccessLogVO> findPageByCondition(Condition condition, Pageable page, Sorter sort);

    /**
     * 
    * @Title: res_Access_top10
    * (报表活动排行榜)
    * @param    设定文件
    * @return List<FavoritesTop10VO>    返回类型
    * 
     */
    List<ResAccessTopVO> res_Access_top(String pk_org, Integer showcnt, Sorter sort);

    /**
     * 用户最近访问记录
     * @param pk_user 用户ID
     * @param count 查询记录条数
     * @author wqh
     * @since 2017年6月26日
     */
    List<ResAccessTopVO> findRecentVisitRecords(String pk_user,int count);
    
    
    /**
     * 
    * @Title: deletes
    * (删除多条记录)
    * @param ids    参数说明
    *      */
    void deletes(String... ids);
    
    /**
     * 获取csv导出的内容list
     * @param resAccessList
     * @return
     */
    List<String> getContentList(List<ResAccessLogVO> resAccessList);
}
