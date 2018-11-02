package com.gdsp.platform.systools.notice.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.gdsp.dev.core.model.param.Sorter;
import com.gdsp.dev.core.model.query.Condition;
import com.gdsp.dev.persist.ext.MBDao;
import com.gdsp.platform.systools.notice.model.SysNoticeVO;

@MBDao
public interface ISysNoticeDao {

    @Select("<script>select * from rms_sys_notice <if test=\"condition != null\">where ${condition._CONDITION_} </if></script>")
    public List<SysNoticeVO> querySysNoticeVosByCondition(Condition condition);

    @Select("<script>select * from rms_sys_notice <if test=\"condition != null\">where ${condition._CONDITION_} </if>"
            + "<if test=\"sort != null\"> order by <foreach collection=\"sort\" item=\"order\" separator=\",\">	${order.property} ${order.direction}</foreach></if></script>")
    Page<SysNoticeVO> queryNoticeVosPageByCondition(@Param("condition") Condition condition, Pageable pageable, @Param("sort") Sorter sort);

    @Insert("insert into rms_sys_notice(id,title,content,valid_flag,publish_date,start_date,end_date,createby,createtime,lastmodifytime,version)"
            + "values(#{id},#{title},#{content},#{valid_flag},#{publish_date,jdbcType=BIGINT},#{start_date,jdbcType=BIGINT},#{end_date,jdbcType=BIGINT},#{createBy},#{createTime,jdbcType=BIGINT},#{lastModifyTime,jdbcType=BIGINT},#{version})")
    public void insert(SysNoticeVO sysNoticeVo);

    @Update("update rms_sys_notice set title=#{title},content=#{content},valid_flag=#{valid_flag:CHAR},"
            + "publish_date=#{publish_date:BIGINT},start_date=#{start_date:BIGINT},end_date=#{end_date:BIGINT},lastModifyTime=#{lastModifyTime:BIGINT},lastModifyBy=#{lastModifyBy}, version=#{version} where id=#{id} ")
    void update(SysNoticeVO sysNoticeVo);

    void updateByPrimaryKeySelective(SysNoticeVO sysNoticeVo);

    @Select("select * from rms_sys_notice where id=#{id}")
    SysNoticeVO load(String id);

    @Delete("<script>delete from rms_sys_notice  WHERE id=#{id} </script>")
    void delete(String id);

    @Delete("<script>delete from rms_sys_notice  where id in <foreach collection='array' index='index' item='item' open='(' separator=',' close=')'>#{item}</foreach></script>")
    public void deleteSysNotices(String[] ids);

    //    @Select("<script>select * from rms_sys_notice where UNIX_TIMESTAMP(now()) > publish_date and valid_flag='Y'"
    //    		+ "<if test=\"condition != null\"> and ${condition._CONDITION_} </if>"
    //    		+ "<if test=\"sort != null\"> order by <foreach collection=\"sort\" item=\"order\" separator=\",\">	${order.property} ${order.direction}</foreach></if></script>")
   /*
    * 权限拆分--------------------------------------2016.12.28
    * 此接口所写sql没有被使用
    @Select("<script>select * from (select main.* from rms_sys_notice main INNER JOIN rms_notice_range relation ON main.id = relation.notice_id where relation.range_id =#{UserId} union SELECT main.* from rms_sys_notice main INNER JOIN rms_notice_range relation ON main.id = relation.notice_id INNER JOIN rms_usergroup ug ON relation.range_id = ug.id INNER JOIN rms_usergroup_rlt rlt on rlt.pk_usergroup = ug.id where rlt.pk_user = #{UserId} union SELECT main.* from rms_sys_notice main INNER JOIN rms_notice_range relation ON main.id = relation.notice_id INNER JOIN rms_user user ON relation.range_id = user.pk_org  where user.id = #{UserId}) t where valid_flag='Y' UNION select main.* from rms_sys_notice main where main.id not in(select relation.notice_id from rms_notice_range relation)  and main.valid_flag='Y'"
            + "<if test=\"condition != null\"> and ${condition._CONDITION_} </if>"
            + "<if test=\"sort != null\"> order by <foreach collection=\"sort\" item=\"order\" separator=\",\">	${order.property} ${order.direction}</foreach></if></script>")
    public Page<SysNoticeVO> querySimpleNoticeVosPage(@Param("condition") Condition condition, Pageable pageable, @Param("sort") Sorter sort, @Param("UserId") String UserId);
    */
    @Select(" select count(*) from rms_sys_notice r where r.id not in (select r.id from rms_sys_notice r INNER JOIN rms_sys_notice_history histoy ON r.id = histoy.notice_id where r.valid_flag='Y' and user_id=#{user_id}) and r.valid_flag='Y' ")
    public int queryPublishInfCount(String user_id);

    @Select("<script>select * from rms_sys_notice where valid_flag='Y' "
            + "<if test=\"condition != null\"> and ${condition._CONDITION_} </if>"
            + "<if test=\"sort != null\"> order by <foreach collection=\"sort\" item=\"order\" separator=\",\">	${order.property} ${order.direction}</foreach></if></script>")
    public Page<SysNoticeVO> querySimpleNoticeVoPage(@Param("condition") Condition condition, Pageable pageable, @Param("sort") Sorter sort);

    @Select("<script>select *  from rms_sys_notice where id in <foreach collection='array' index='index' item='item' open='(' separator=',' close=')'>#{item}</foreach></script>")
    List<SysNoticeVO> loads(String[] id);

    @Select("<script>select * from (select n.* , 1 as readflag from rms_sys_notice n where n.id not in (select h.notice_id from rms_sys_notice_history h where h.user_id =#{user_id}) and n.valid_flag='Y' <if test=\"condition != null\"> and ${condition._CONDITION_} </if> order by publish_date desc) t1 union all select * from ( select n.*, 2 as readflag from rms_sys_notice n where n.id in (select h.notice_id from rms_sys_notice_history h where h.user_id = #{user_id} ) <if test=\"condition != null\"> and ${condition._CONDITION_} </if> order by publish_date desc) t2 "
            + "</script>")
    public Page<SysNoticeVO> querySimpleNoticeVoDlgPage(@Param("condition") Condition condition, Pageable pageable, @Param("user_id") String user_id);
}
