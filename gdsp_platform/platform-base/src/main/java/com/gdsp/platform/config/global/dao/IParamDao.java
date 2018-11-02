package com.gdsp.platform.config.global.dao;

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
import com.gdsp.platform.config.global.model.ParamVO;

@MBDao
public interface IParamDao {

    @Insert("insert into st_param (id,parname,parcode,pargroupid,groupname,groupcode,valuetype,valuerange,defaultvalue,parvalue,memo,createtime,createby,lastmodifytime,lastmodifyby) "
            + "values (#{id},#{parname},#{parcode},#{pargroupid},#{groupname},#{groupcode},#{valuetype},#{valuerange},#{defaultvalue},#{parvalue},#{memo},#{createTime:BIGINT},#{createBy},#{lastModifyTime:BIGINT},#{lastModifyBy})")
    public void insertParamDef(ParamVO param);

    @Delete("delete from st_param where id = #{id}")
    public void deleteParamDef(String id);

    @Update("update st_param set parname=#{parname},parcode=#{parcode},pargroupid=#{pargroupid},groupname=#{groupname},groupcode=#{groupcode},valuetype=#{valuetype},"
            + "valuerange=#{valuerange},defaultvalue=#{defaultvalue},parvalue=#{parvalue},memo=#{memo},createtime=#{createTime:BIGINT},createby=#{createBy},"
            + "lastmodifytime=#{lastModifyTime:BIGINT},lastmodifyby=#{lastModifyBy} where id=#{id}")
    public void updateParamDef(ParamVO param);

    @Select("select * from st_param where parcode = #{paramcode}")
    public ParamVO queryParamVOByCode(String paramcode);

    @Update("update st_param set parvalue=#{paramvalue} where parcode = #{paramcode}")
    public void setParamValue(@Param("paramcode") String paramCode, @Param("paramvalue") String paramValue);

    @Select("<script>select * from st_param <trim prefix=\"where\" prefixOverrides=\"and |or\"><if test=\"cond!=null and cond._CONDITION_ != null\">${cond._CONDITION_}</if></trim>"
            + "<if test=\"sort!=null\"> order by <foreach collection=\"sort\" item=\"order\" separator=\",\">${order.property} ${order.direction}</foreach></if></script>")
    public Page<ParamVO> queryParamDefList(@Param("cond") Condition condition, Pageable page, @Param("sort") Sorter sort);

    @Update("update st_param set parvalue=defaultvalue where id = #{id}")
    public void restoreDefault(String id);

    @Select("select * from st_param where id=#{id}")
    ParamVO load(String id);
}
