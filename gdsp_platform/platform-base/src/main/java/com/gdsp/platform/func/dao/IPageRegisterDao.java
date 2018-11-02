package com.gdsp.platform.func.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.gdsp.dev.core.model.param.Sorter;
import com.gdsp.dev.core.model.query.Condition;
import com.gdsp.dev.persist.ext.MBDao;
import com.gdsp.platform.func.model.PageRegisterVO;

/**
 * 页面注册DAO
 * @author
 *
 */
@MBDao
public interface IPageRegisterDao {

    @Insert("insert into st_pageregister (id,funname,dispcode,menuid,pageid,url,memo,createtime,createby,lastmodifytime,lastmodifyby,version) "
            + "values (#{id},#{funname},#{dispcode},#{menuid},#{pageid},#{url},#{memo},#{createTime:BIGINT},#{createBy},#{lastModifyTime:BIGINT},#{lastModifyBy},#{version})")
    void insert(PageRegisterVO vo);

    @Update("update st_pageregister set funname=#{funname},dispcode=#{dispcode},menuid=#{menuid},pageid=#{pageid},url=#{url},memo=#{memo},"
            + "lastmodifytime=#{lastModifyTime:BIGINT},lastmodifyby=#{lastModifyBy} ,version=#{version}+1 where id=#{id}")
    void update(PageRegisterVO vo);

    @Delete("<script>delete from st_pageregister where id=#{id} </script>")
    void delete(String ids);

    @Select("select * from st_pageregister where id=#{id}")
    PageRegisterVO load(String id);

    @Select("<script> select * from st_pageregister  <trim prefix=\"where\" prefixOverrides=\"and |or\"><if test=\"condition!=null and condition._CONDITION_ != null\">${condition._CONDITION_}</if></trim>"
            + " <if test=\"sort != null\"> order by <foreach collection=\"sort\" item=\"order\" separator=\",\">	${order.property} ${order.direction}</foreach></if>  </script> ")
    List<PageRegisterVO> queryPageRegisterListByCondition(@Param("condition") Condition condition, @Param("sort") Sorter sort);

    @Select("select count(*) from st_pageregister where pageid=#{pageid}")
    int loadPageID(String pageId);

    @Select("<script>select count(1) from st_pageregister where funname = #{funname} and menuid=#{menuid} <if test='id'>and id&lt;&gt;#{id}</if></script>")
    public int existSameName(@Param("funname") String funname, @Param("menuid") String menuid, @Param("id") String id);

    @Select("select id,funname,menuid,pageid,url from st_pageregister")
    List<PageRegisterVO> queryAllPageList();
}
