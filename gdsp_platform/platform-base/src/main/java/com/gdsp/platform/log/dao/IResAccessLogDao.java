package com.gdsp.platform.log.dao;

import java.util.List;




import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.gdsp.dev.core.model.param.Sorter;
import com.gdsp.dev.core.model.query.Condition;
import com.gdsp.dev.persist.ext.LogDao;
import com.gdsp.dev.persist.ext.MBDao;
import com.gdsp.platform.log.model.ResAccessLogVO;
import com.gdsp.platform.log.model.ResAccessTopVO;

/**
 * 访问日志
 * @author wqh
 * @since 2017年6月26日 上午9:22:19
 */
//@MBDao
@LogDao
public interface IResAccessLogDao {

    /**
     * 
    * @Title: deleteByCdn
    * (按条件删除)
    * @param cond    参数说明
    *      */
    void deleteByCdn(@Param("cond") Condition cond);

    /**
     * 
    * @Title: findListByCondition
    * (查询)
    * @param cond
    * @param sort
    * @return    参数说明
    *      */
    List<ResAccessLogVO> findListByCondition(@Param("cond") Condition cond, @Param("sort") Sorter sort);

    /**
     * 
    * @Title: findPageByCondition
    * (查询全部)
    * @param condition
    * @param page
    * @param sort
    * @return    参数说明
    *      */
    Page<ResAccessLogVO> findPageByCondition(@Param("condition") Condition condition, Pageable page, @Param("sort") Sorter sort);

    /**
     * 
    * @Title: res_Access_top10
    * (报表活动排行榜)
    * @param @return    设定文件
    * @return List<FavoritesTop10VO>    返回类型
    *      */
    List<ResAccessTopVO> res_Access_top(@Param("org") String pk_org, @Param("showcnt") Integer showcnt, @Param("sort") Sorter sort);

    /**
     * 查询用户最近访问记录
     * @param pk_user 用户id
     * @param count 查询条数
     * @author wqh
     * @since 2017年6月26日
     */
    List<ResAccessTopVO> queryRecentVisitRecords(@Param("pk_user")String pk_user,@Param("count")int count);
    
    /**
     * 
    * @Title: deletes
    * (删除操作)
    * @param ids    参数说明
    *      */
    @Delete("<script>delete from res_accesslog  where id in <foreach collection='array' index='index' item='item' open='(' separator=',' close=')'>#{item}</foreach></script>")
    void deletes(String[] ids);
}
