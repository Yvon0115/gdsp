package com.gdsp.platform.config.global.dao;

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
import com.gdsp.dev.persist.dao.QueryDao;
import com.gdsp.dev.persist.ext.MBDao;
import com.gdsp.platform.config.global.model.DefDocListVO;

@MBDao
public interface IDefDocListDao extends QueryDao<DefDocListVO> {

    /** 根据主键查询 */
    public DefDocListVO findOne(String id);

    Page<DefDocListVO> queryDefDocListVOByCondition(@Param("condition") Condition condition, Pageable page, @Param("sort") Sorter sort);

    //增加
    @Insert("insert into cp_defdoclist (id ,type_code,type_name ,type_desc,createtime,createby,sortnum,lastmodifytime,lastmodifyby,version) "
            + "values (#{id},#{type_code},#{type_name},#{type_desc},#{createTime:BIGINT},#{createBy},#{sortnum},#{lastModifyTime:BIGINT},#{lastModifyBy},#{version})")
    void insert(DefDocListVO defDocListVO);

    //修改
    @Update("update cp_defdoclist set type_code=#{type_code},type_name=#{type_name},type_desc=#{type_desc}, createtime=#{createTime:BIGINT},createby=#{createBy},lastmodifytime=#{lastModifyTime:BIGINT},lastmodifyby=#{lastModifyBy},version=#{version} where id=#{id}")
    void update(DefDocListVO defDocListVO);

    //删除
    @Delete("<script>delete from cp_defdoclist  where id in <foreach collection='array' index='index' item='item' open='(' separator=',' close=')'>#{item}</foreach></script>")
    void delete(String[] ids);

    //单条ID查询
    @Select("select * from cp_defdoclist where id=#{id}")
    DefDocListVO load(String id);

    //查询sortnum的最大值
    @Select("select max(sortnum) from cp_defdoclist")
    public int findSortnum();

    /** 查询所有数据字典项  TODO 移至码表查询dao wqh 2017-8-31 11:29:47 */
//    public List<DefDocListVO> findAllDocListWithDocs();

	/**
	 * 查询所有的isPreset字段，此字段如果为“Y”，则不能删除。默认值为"N")
	 * @param id 数据字典ID集合
	 * @return List<String> is_preset字段集合
	 */
    @Select("<script>select is_preset from cp_defdoclist sd <if test=\"ids != null\">where sd.id in <foreach collection=\"ids\" item=\"id\" separator=\",\" open=\"(\" close=\")\">#{id}</foreach></if></script>")
    public List<String> isPreset(@Param("ids") String[] id);

   // @Select("<script>select count(1) from cp_defdoclist where type_code = #{type_code} <if test=\"id != null and id!=''\">and id&lt;&gt;#{id}</if></script>")
    public int existSameTypeCode(DefDocListVO defDocListVO);

    public List<DefDocListVO> findDocListWithDocsByTypeCode(String typeCode);
    
    /** 通过类型编码查询类型id */
    @Select("select id from cp_defdoclist where type_code=#{typeCode}")
    public String findIdByTypeCode(String typeCode);
}
