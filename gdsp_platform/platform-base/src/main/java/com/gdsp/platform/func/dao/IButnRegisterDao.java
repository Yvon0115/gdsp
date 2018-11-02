package com.gdsp.platform.func.dao;

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
import com.gdsp.platform.func.model.ButnRegisterVO;

/**
 * @author zhouyu
 *
 */
@MBDao
public interface IButnRegisterDao {

    @Select("<script> "
            + "	select * from st_butnregister"
            + "	<trim prefix=\"where\" prefixOverrides=\"and |or\">"
            + "	<if test=\"condition!= null\">${condition._CONDITION_}</if></trim>"
            + "<if test=\"sort != null\"> order by	<foreach collection=\"sort\" item=\"order\" separator=\",\">	${order.property} ${order.direction}</foreach></if> "
            + " </script>  ")
    Page<ButnRegisterVO> queryBtnRegister(@Param("condition") Condition condition, @Param("sort") Sorter sort, Pageable page);

    @Select("select * from st_butnregister where id=#{id}")
    ButnRegisterVO loadButnRegisterVOById(String id);

    @Insert("insert into st_butnregister "
            + "(id,funcode,funname,parentid,url,memo,createtime,createby,lastmodifytime,lastmodifyby,version)"
            + " values(#{id},#{funcode},#{funname},#{parentid},#{url},#{memo},#{createTime:BIGINT},#{createBy},#{lastModifyTime:BIGINT},#{lastModifyBy},#{version})")
    void insertButnRegister(ButnRegisterVO butnRegisterPages);

    @Update("update st_butnregister set funname=#{funname},url=#{url},memo=#{memo},lastmodifytime=#{lastModifyTime:BIGINT},lastmodifyby=#{lastModifyBy} ,version=#{version}+1"
            + " where id=#{id}")
    void updateButnRegister(ButnRegisterVO butnRegisterPages);

    @Delete("<script>delete from st_butnregister  where id in <foreach collection='array' index='index' item='item' open='(' separator=',' close=')'>#{item}</foreach></script>")
    void deleteButnRegister(String[] id);

    @Select("select * from st_butnregister where funcode=#{funcode}")
    List<ButnRegisterVO> queryButnByFunCode(String funcode);

    @Select("	select * from st_butnregister where parentid=#{parentid}")
    List<ButnRegisterVO> queryButnByParentID(String parentid);
}
