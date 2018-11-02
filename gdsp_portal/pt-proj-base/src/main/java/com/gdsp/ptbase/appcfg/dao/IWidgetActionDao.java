package com.gdsp.ptbase.appcfg.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.gdsp.dev.core.model.query.Condition;
import com.gdsp.dev.persist.ext.MBDao;
import com.gdsp.ptbase.appcfg.model.WidgetActionVO;

/**
 * 
* @ClassName: IWidgetActionDao
* (资源动作管理)
* @author hongwei.xu
* @date 2015年7月22日 下午5:01:48
*
 */
@MBDao
public interface IWidgetActionDao {

    /**
     * 
    * @Title: insert
    * (新增记录)
    * @param vo
    * @return    参数说明
    * @return int    返回值说明
    *      */
    int insert(WidgetActionVO vo);

    /**
     * 
    * @Title: update
    * (更新记录)
    * @param vo
    * @return    参数说明
    * @return int    返回值说明
    *      */
    int update(WidgetActionVO vo);

    /**
     * 
    * @Title: delete
    * (删除记录)
    * @param ids
    * @return    参数说明
    * @return int    返回值说明
    *      */
    int delete(String... ids);

    /**
     * 
    * @Title: load
    * (根据id加载记录)
    * @param id
    * @return    参数说明
    * @return WidgetActionVO    返回值说明
    *      */
    WidgetActionVO load(String id);

    /**
     * 
    * @Title: findActionsByWidget
    * (根据部件id部件类型取得可用动作列表)
    * @param widgetId
    * @param widgetType
    * @param pubType
    * @return    参数说明
    * @return List<WidgetActionVO>    返回值说明
    *      */
    List<WidgetActionVO> findActionsByWidget(@Param("widgetId") String widgetId, @Param("widgetType") String widgetType, @Param("pubType") String pubType);

    /**
     * 
    * @Title: findAllActions
    * (查询所有的操作列表)
    * @return    参数说明
    * @return Map<String,WidgetActionVO>    返回值说明
    *      */
    @MapKey("code")
    Map<String, WidgetActionVO> findAllActions();

    /**
     * 
    * @Title: queryWidgetActionVOByCondition
    * (查询所以资源动作)
    * @param condition
    * @param page
    * @return    参数说明
    * @return Page<WidgetActionVO>    返回值说明
    *      */
    Page<WidgetActionVO> queryWidgetActionVOByCondition(@Param("condition") Condition condition, Pageable page);

    /**
     * 
    * @Title: findSortnum
    * (查询每个类别的最大分类号)
    * @param widgettype
    * @return    参数说明
    * @return int    返回值说明
    *      */
    @Select("select max(sortnum) from ac_widget_action where widgettype=#{widgettype}")
    public int findSortnum(String widgettype);

    /**
     * 
    * @Title: existsType
    * (根据类型查询)
    * @param widgettype
    * @return    参数说明
    * @return List<String>    返回值说明
    *      */
    @Select("select widgettype from ac_widget_action action where action.widgettype=#{widgettype}")
    public List<String> existsType(@Param("widgettype") String widgettype);

    /**
     * 
    * @Title: findWidgetActionVOByWidgetIDs
    * (根据资源id查询)
    * @param widgetid
    * @param page
    * @return    参数说明
    * @return Page<WidgetActionVO>    返回值说明
    *      */
    Page<WidgetActionVO> findWidgetActionVOByWidgetIDs(@Param("widgetid") String widgetid, Pageable page);

    /**
    * @Title: isPreset
    * (查询所有的isPreset字段，此字段如果为“Y”，则不能删除。默认值为"N")
    * @param id	数据字典ID集合
    * @return List<String>  is_preset字段集合
    *     */
    @Select("<script>select is_preset from ac_widget_action sd <if test=\"ids != null\">where sd.id in <foreach collection=\"ids\" item=\"id\" separator=\",\" open=\"(\" close=\")\">'${id}'</foreach></if></script>")
    public List<String> isPreset(@Param("ids") String[] id);

    @Select("<script>select count(1) from ac_widget_action where code = #{code} <if test=\"id != null and id!=''\">and id&lt;&gt;#{id}</if></script>")
    public int existSameCode(WidgetActionVO vo);
}
