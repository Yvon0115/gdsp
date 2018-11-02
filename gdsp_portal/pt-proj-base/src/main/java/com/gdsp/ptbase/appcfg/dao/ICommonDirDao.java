package com.gdsp.ptbase.appcfg.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.session.ResultHandler;

import com.gdsp.dev.core.model.param.Sorter;
import com.gdsp.dev.core.model.query.Condition;
import com.gdsp.dev.persist.ext.MBDao;
import com.gdsp.dev.persist.result.MapListResultHandler;
import com.gdsp.ptbase.appcfg.model.CommonDirVO;
import com.gdsp.ptbase.appcfg.model.CommonDirsVO;

@MBDao
public interface ICommonDirDao {

    void insert(CommonDirVO dir);

    void update(CommonDirVO dir);

    void updateName(CommonDirVO dir);

    @Delete("delete from ac_common_dir where id=#{id}")
    void deleteById(@Param("id") String id);

    void delete(String[] ids);

    CommonDirVO load(String id);

    List<CommonDirVO> findListByCondition(@Param("cond") Condition cond, @Param("sort") Sorter sort);

    Integer findCountByCondition(@Param("cond") Condition cond);

    Integer findMaxSortNum();

    /**
     * 查询指定类别指定父节点下的第一级子节点
     * @param category 指定分类
     * @param parentId 指定父节点
     * @return 节点列表
     */
    List<CommonDirVO> findFirstLevelDirsByCategory(@Param("") String category, String parentId);

    /**
     * 查询类别下所有的目录
     * @param category 目录类别
     * @param handler 结果处理
     */
    void findDirTreeByCategory(String category, ResultHandler handler);

    /**
     * 查询所有类别目录
     * @param handler
     */
    @Select("select * from ac_common_dir d where (d.parent_id='' or d.parent_id is null) and (d.def1='' or d.def1 is null) and d.category='widgetmeta' order by sortnum")
    @ResultType(CommonDirVO.class)
    void findAllDirTree(ResultHandler handler);

    @Select("select 'Y' from dual where "
            + "NOT EXISTS(select 1 from ac_common_dir d where d.parent_id = #{dirId}) "
            + "and NOT EXISTS(select 1 from ac_widget_meta w where w.dirid = #{dirId})")
    String isExistChildNode(@Param("dirId") String dirId);

    @Select("<script> select * from ac_common_dir  <trim prefix=\"where\" prefixOverrides=\"and |or\"><if test=\"condition!=null and condition._CONDITION_ != null\">${condition._CONDITION_}</if></trim>"
            + " <if test=\"sort != null\"> order by <foreach collection=\"sort\" item=\"order\" separator=\",\">	${order.property} ${order.direction}</foreach></if>  </script> ")
    @ResultType(CommonDirVO.class)
    void queryCommonDirByCondReturnMap(@Param("condition") Condition condition, @Param("sort") Sorter sort, MapListResultHandler handler);

    @Select("<script>select count(1) from ac_common_dir where dir_name = #{dir_name} <if test=\"id != null and id!=''\">and id&lt;&gt;#{id}</if></script>")
    public int existSameDirName(CommonDirVO commonDirVO);
    
    /**
     * 查询出所有的目录
     * @return
     */
    void queryDirs(MapListResultHandler<CommonDirsVO> handler);
}
