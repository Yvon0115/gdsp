package com.gdsp.platform.config.global.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.gdsp.dev.persist.dao.QueryDao;
import com.gdsp.dev.persist.ext.MBDao;
import com.gdsp.dev.persist.result.MapListResultHandler;
import com.gdsp.platform.config.global.model.DefDocListVO;
import com.gdsp.platform.config.global.model.DefDocVO;

@MBDao
public interface IDefDocDao extends QueryDao<DefDocVO> {

	/** 根据主键查询 */
    @Select("select * from cp_defdoc where id=#{id}")
    @ResultType(DefDocVO.class)
    DefDocVO load(String id);
    
    // TODO 重复代码 待删除
//    public DefDocVO findOne(String id);

    /** 查询分类下的档案  */
    public List<DefDocVO> findListByType(String type_id);

    /** 查询分类下的档案 */
    Page<DefDocVO> findPageByType(@Param("type_id") String type_id, Pageable pageable);

    //添加
    @Insert("insert into cp_defdoc (id,type_id, doc_code,doc_name ,doc_desc,pk_fatherid,createtime,createby,sortnum,lastmodifytime,lastmodifyby,version) "
            + "values (#{id},#{type_id},#{doc_code},#{doc_name},#{doc_desc},#{pk_fatherId},#{createTime:BIGINT},#{createBy},#{sortnum},#{lastModifyTime:BIGINT},#{lastModifyBy},#{version})")
    void insert(DefDocVO defDocVO);

    //修改
    @Update("update cp_defdoc set doc_code=#{doc_code},doc_name=#{doc_name},doc_desc=#{doc_desc},pk_fatherid=#{pk_fatherId} where id=#{id}")
    void update(DefDocVO defDocVO);

    //删除
    @Delete("<script>delete from cp_defdoc  where id in <foreach collection='array' index='index' item='item' open='(' separator=',' close=')'>#{item}</foreach></script>")
    void delete(String[] ids);

    

    //查询到sortnum的最大值
    @Select("select max(sortnum) from cp_defdoc")
    public int findSortnum();

//    @Select("select * from cp_defdoc order by type_id ")
//    List<DefDocVO> findAlldefDocs();

	/**
	 * 查询所有的isPreset字段，此字段如果为“Y”，则不能删除。默认值为"N"
	 * @param id 数据字典ID集合
	 */
    @Select("<script>select is_preset from cp_defdoc sd <if test=\"ids != null\">where sd.id in <foreach collection=\"ids\" item=\"id\" separator=\",\" open=\"(\" close=\")\">#{id}</foreach></if></script>")
    public List<String> isPreset(@Param("ids") String[] id);

//    @Select("select * from cp_defdoc where doc_name=#{doc_name} and type_id=#{type_id}")
//    public DefDocVO findByName(@Param("doc_name") String doc_name, @Param("type_id") String type_id);

//    @Select("select * from cp_defdoc where doc_desc=#{doc_desc} and type_id=#{type_id}")
//    public DefDocVO findByDocDesc(@Param("doc_desc") String doc_desc, @Param("type_id") String type_id);

    //@Select("<script>select count(1) from cp_defdoc where doc_code = #{doc_code} and type_id=#{type_id} <if test=\"id != null and id!=''\">and id&lt;&gt;#{id}</if></script>")
    public int existSameDocCode(DefDocVO defDocVO);

//    @Select("select * from cp_defdoc where TYPE_ID=#{dataSource}")
//    public List<DefDocVO> getDefDocsByTypeIDFromDataBase(String dataSource);

    /**
     * 通过类型编码查询类型id <br/>
     * TODO 此接口已移至IDefDocListDao
     */
//    @Select("select id from cp_defdoclist where type_code=#{code}")
//    public String findIdByCode(String typeCode);

    /**
     * 获取时间频度
     * @param dateFreq
     * @return
     */
    public List<DefDocVO> findDateFreq(String dateFreq);

    /**
     * 获取时间频度用于前台页面展示
     * @param tempStrs
     * @return
     */
    public List<DefDocVO> findFreqByStateFreq(@Param("tempStrs") String[] tempStrs, @Param("frequency") String frequency);

    /**
     * 根据频度名称获取频度code
     * @param tempStrs
     * @return
     */
    public List<DefDocVO> findFreqByCodeName(String[] tempStrs);
    /**
     * 选择上级
     * @param handler
     * @param type_id 类型ID
     */
    public void selectParent(MapListResultHandler handler,@Param("type_id")String type_id);
    /**
     * 查询所有的对象的上级
     * @param id
     * @return
     */
    List<DefDocVO> queryParent(List<String> id);
//    /**
//     * 查询该对象的上级
//     * @param id
//     * @return
//     */
//    DefDocVO queryParentVO(@Param("id")String id);
    
    /** 查询所有数据字典项 */
    public List<DefDocListVO> findAllDocListWithDocs();
    
    /**  根据档案类型和数据项编码查询单条数据项    --wqh 2017年8月28日16:40:28 */
    @Select("select * from cp_defdoc doc left join cp_defdoclist type on type.ID = doc.TYPE_ID where doc.doc_code=#{docCode} and type.type_code=#{typeCode}")
    public DefDocVO queryDocByTypeAndCode(@Param("typeCode")String typeCode,@Param("docCode")String docCode);
    
    /** 根据code查询子级档案 */
    public List<DefDocVO> querySubLevelDocsByCode(@Param("typeCode")String typeCode,@Param("docCode")List<String> docCodes);
    
    /**
     * 根据类型Id查询数据项
     * @param typeCode
     * @param handler
     */
    void queryDefDocMapByTypeId(@Param("typeId") String typeId, MapListResultHandler handler);
}
