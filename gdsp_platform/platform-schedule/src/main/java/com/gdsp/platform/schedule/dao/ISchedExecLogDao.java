package com.gdsp.platform.schedule.dao;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.gdsp.dev.core.model.param.Sorter;
import com.gdsp.dev.core.model.query.Condition;
import com.gdsp.dev.persist.ext.LogDao;
import com.gdsp.platform.schedule.model.SchedExecLogVO;

/**
 * 预警及任务日志
 */
@LogDao
public interface ISchedExecLogDao {

    @Insert("insert into sched_accesslog (id,job_name,job_group,trigger_name,trigger_group,begintime,endtime,elapsedtime,result,memo) "
            + "values (#{id},#{job_name},#{job_group},#{trigger_name},#{trigger_group},#{begintime:BIGINT}),#{endtime:BIGINT}),#{elapsedtime},#{result},#{memo})")
    void insertSchedExecLog(SchedExecLogVO vo);

    @Delete("<script>delete from sched_accesslog  where id in <foreach collection='array' index='index' item='item' open='(' separator=',' close=')'>#{item}</foreach></script>")
    void deleteSchedExecLog(String[] ids);

    @Select("<script>select id,job_name,job_group,trigger_name,trigger_group,begintime,endtime,elapsedtime,result,memo from sched_accesslog <trim prefix=\"where\" prefixOverrides=\"and |or \"><if test=\"condition!= null\">${condition._CONDITION_}</if></trim><if test=\"sort != null\"> order by	<foreach collection=\"sort\" item=\"order\" separator=\",\">	${order.property} ${order.direction}</foreach></if> </script> ")
    Page<SchedExecLogVO> findSchedExecLogByCondition(@Param("condition") Condition condition, @Param("sort") Sorter sort, Pageable page);

    @Select("select * from sched_accesslog where id=#{id}")
    SchedExecLogVO load(String id);
}
