package com.gdsp.platform.workflow.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.gdsp.dev.persist.ext.MBDao;
import com.gdsp.platform.workflow.model.LeaveVO;

@MBDao
public interface ILeaveDao {

    @Insert("insert into form_leave (id,startTime,endTime,phone,leaveType,leaveDay,reason,createby,createtime,lastmodifyby,lastmodifytime,version) "
            + "values(#{id},#{startTime:BIGINT},#{endTime:BIGINT},#{phone},#{leaveType},#{leaveDay},#{reason},#{createBy},#{createTime:BIGINT},#{lastModifyBy},#{lastModifyTime:BIGINT},#{version})")
    void insert(LeaveVO user);
    
    @Update("update form_leave set startTime=#{startTime:BIGINT},endTime=#{endTime:BIGINT},phone=#{phone},leaveType=#{leaveType},leaveDay=#{leaveDay},reason=#{reason},createby=#{createBy},createtime=#{createTime:BIGINT},"
            + "lastmodifyby=#{lastModifyBy},lastmodifytime=#{lastModifyTime:BIGINT},version=#{version} where id=#{id}")
    void update(LeaveVO vo);
    
    @Select("select * from form_leave where id=#{id}")
    LeaveVO load(String id);

}
