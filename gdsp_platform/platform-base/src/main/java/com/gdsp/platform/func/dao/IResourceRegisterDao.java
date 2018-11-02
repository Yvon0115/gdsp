package com.gdsp.platform.func.dao;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.gdsp.dev.core.model.query.Condition;
import com.gdsp.dev.persist.ext.MBDao;
import com.gdsp.platform.func.model.ResourceRegisterVO;

@MBDao
public interface IResourceRegisterDao {

    @Select("<script>select * from st_resregister <trim prefix=\"where\" prefixOverrides=\"and |or\"> <if test=\"condition!= null\">${condition._CONDITION_}</if></trim></script> ")
    Page<ResourceRegisterVO> getResourceByMumeid(@Param("condition") Condition condition,
            Pageable page);

    @Select("select * from st_resregister where id=#{id}")
    ResourceRegisterVO getResourceByID(String id);

    @Update("update st_resregister set url=#{url} ,memo=#{memo} ,lastmodifytime=#{lastModifyTime:BIGINT},lastmodifyby=#{lastModifyBy} ,version=#{version}+1 where id=#{id}")
    void updateResRegister(ResourceRegisterVO vo);

    @Insert("insert into st_resregister (id,url,memo,parentid,parenttype,createtime,createby,lastmodifytime,lastmodifyby,version) "
            + "values(#{id},#{url},#{memo},#{parentid},#{parenttype},#{createTime:BIGINT},#{createBy},#{lastModifyTime:BIGINT},#{lastModifyBy},#{version})")
    void insertResRegister(ResourceRegisterVO vo);

    @Delete("<script>delete from st_resregister  where id in <foreach collection='array' index='index' item='item' open='(' separator=',' close=')'>#{item}</foreach></script>")
    void delete(String[] id);

    @Select("<script>select count(1) from st_resregister where url = #{url} <if test=\"id != null and id!=''\">and id&lt;&gt;#{id}</if></script>")
    public int existSameURL(ResourceRegisterVO vo);
}
