package com.gdsp.platform.comm.message.dao;

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
import com.gdsp.platform.comm.message.model.MessageVO;

@MBDao
public interface IMessageDao {

    @Insert("insert into tools_message (id,touserid,fromuserid,touseraccout,fromuseraccount,subject,content,iflook ,transtime,msgtype,reply_by,attachments) "
            + "values (#{id},#{touserid},#{fromuserid},#{touseraccout},#{fromuseraccount},#{subject},#{content},#{iflook:BOOLEAN},#{transtime:BIGINT},#{msgtype},#{reply_by},#{attachments})")
    void insertMessage(MessageVO vo);

    @Delete("<script>delete from tools_message  where id in <foreach collection='array' index='index' item='item' open='(' separator=',' close=')'>#{item}</foreach></script>")
    void deleteMessage(String[] ids);

    @Select("<script>select id,touserid,fromuserid,touseraccout,fromuseraccount,subject,content,iflook ,transtime ,msgtype,reply_by,attachments from tools_message <trim prefix=\"where\" prefixOverrides=\"and |or \"><if test=\"condition!= null\">${condition._CONDITION_}</if></trim><if test=\"sort != null\"> order by	<foreach collection=\"sort\" item=\"order\" separator=\",\">	${order.property} ${order.direction}</foreach></if> </script> ")
    Page<MessageVO> findMessageByCondition(@Param("condition") Condition condition, @Param("sort") Sorter sort, Pageable page);

    @Update("update tools_message set iflook = 'Y' where id = #{id}")
    void signlook(String id);

    @Select("select * from tools_message where id=#{id}")
    MessageVO load(String id);

    @Select("select count(id) from tools_message where touserid = #{userId} and iflook = 'N'")
    public int queryUnlookInfCount(String userId);
}
