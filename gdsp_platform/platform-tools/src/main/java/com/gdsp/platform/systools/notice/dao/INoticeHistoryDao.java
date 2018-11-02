package com.gdsp.platform.systools.notice.dao;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.gdsp.dev.persist.ext.MBDao;
import com.gdsp.platform.systools.notice.model.NoticeHistoryVO;

@MBDao
public interface INoticeHistoryDao {

    @Insert("insert into rms_sys_notice_history(id,notice_id,user_id,access_date,createBy,createTime,lastModifyBy,lastModifyTime,version) values(#{id},#{notice_id},#{user_id},#{access_date},#{createBy},#{createTime,jdbcType=BIGINT},#{lastModifyBy},#{lastModifyTime:BIGINT},#{version})")
    public void insert(NoticeHistoryVO notHistoryVO);

    @Select("select count(*) from rms_sys_notice_history where notice_id=#{notice_id} and user_id=#{user_id}")
    public int queryNoticeHistory(@Param("notice_id") String notice_id, @Param("user_id") String user_id);
    
    @Delete("delete from rms_sys_notice_history where notice_id=#{notice_id}")
    public void delete(String notice_id);
}
