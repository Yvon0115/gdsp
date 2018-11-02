package com.gdsp.platform.grant.usergroup.dao;

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
import com.gdsp.platform.grant.usergroup.model.UserGroupVO;

/**
 * @version 2015/7/1
 */
@MBDao
public interface IUserGroupDao {

    @Insert("insert into rms_usergroup (id,groupname,parentid,innercode,pk_org,memo,createtime,createby,lastmodifytime,lastmodifyby,version) "
            + "values (#{id},#{groupname},#{parentid},#{innercode},#{pk_org},#{memo},#{createTime:BIGINT},#{createBy},#{lastModifyTime:BIGINT},#{lastModifyBy},#{version})")
    void insert(UserGroupVO userGroup);

    @Update("update rms_usergroup set groupname=#{groupname},parentid=#{parentid},innercode=#{innercode},pk_org=#{pk_org},memo=#{memo},"
            + "lastmodifytime=#{lastModifyTime:BIGINT},lastmodifyby=#{lastModifyBy},version=#{version} where id=#{id}")
    void update(UserGroupVO userGroup);

    @Delete("<script>delete from rms_usergroup  where id in <foreach collection='array' index='index' item='item' open='(' separator=',' close=')'>#{item}</foreach></script>")
    void delete(String[] ids);

    @Select("select * from rms_usergroup where id=#{id}")
    UserGroupVO load(String id);

    Page<UserGroupVO> queryUserGroupByCondition(@Param("condition") Condition condition, Pageable page, @Param("sort") Sorter sort);

    List<UserGroupVO> queryUserGroupListByCondition(@Param("condition") Condition condition, @Param("sort") Sorter sort);

    @Select("<script>select count(1) from rms_usergroup where groupname = #{groupname} <if test=\"id != null and id!=''\">and id&lt;&gt;#{id}</if></script>")
    public int existSameUserGroup(UserGroupVO userGroup);
}
