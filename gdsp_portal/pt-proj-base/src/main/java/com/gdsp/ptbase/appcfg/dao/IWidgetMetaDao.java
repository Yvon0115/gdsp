package com.gdsp.ptbase.appcfg.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.session.ResultHandler;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.gdsp.dev.core.model.query.Condition;
import com.gdsp.dev.persist.ext.MBDao;
import com.gdsp.ptbase.appcfg.model.WidgetMetaVO;

/**
 * 
* @ClassName: IWidgetMetaDao
* (组件动作管理)
* @author hongwei.xu
* @date 2015年7月22日 下午5:01:48
*
 */
@MBDao
public interface IWidgetMetaDao {

    /**
     * 新增记录
     * @param vo vo对象
     * @return 成功记录数
     */
    int insert(WidgetMetaVO vo);

    /**
     * 
    * @Title: insertBatchVO
    * (批量插入：每次插入限200以内)
    * @param @param metaVOs    设定文件
    * @return void    返回类型
    *      */
    void insertBatchVO(List<WidgetMetaVO> metaVOs);

    /**
     * 更新记录
     * @param vo vo对象
     * @return 成功记录数
     */
    int update(WidgetMetaVO vo);

    /**
     * 新增记录
     * @param ids 主键数组
     * @return 成功记录数
     */
    int delete(String... ids);

    /**
     * 根据id加载记录
     * @param id 主键
     * @return vo对象
     */
    WidgetMetaVO load(String id);

    /**
     * 根据页面id加载页面内的部件描述信息
     * @param pageId 页面id
     * @return 页面部件映射
     */
    @MapKey("id")
    Map<String, WidgetMetaVO> findWidgetsByPageId(String pageId);

    /**
     * 查询指定分类下的部件描述信息
     * @param categoryId 分类id
     * @return 部件列表
     */
    List<WidgetMetaVO> findWidgetMetasByDirId(String categoryId);

    /**
     * 查询所有在目录中的部件元数据,并以map返回结果
     * @param handler 结果处理
     */
    void findAllWidgetMetaInDirectory(ResultHandler handler);

    /**
     * 通过条件查询WidgetMeta（带分页）
     * @param condition
     * @param pageable
     * @return
     */
    @Select("<script> select * from ac_widget_meta <trim prefix=\"where\" prefixOverrides=\"and |or\"><if test=\"condition!=null and condition._CONDITION_ != null\">${condition._CONDITION_}</if></trim> order by createTime desc </script>  ")
    Page<WidgetMetaVO> findWidgetMetasByDirIdWithPage(@Param("condition") Condition condition,
            @Param("pageable") Pageable pageable);

    /**
    * @Title: findWidgetMetaPageByKpiDetailId
    * (通过指标ID查询指标所关联的报表集合)
    * @param kpiDetailId	指标ID
    * @return Page<WidgetMetaVO>    返回带分页的报表集合
    *     */
    @Select("<script>select * from ac_widget_meta awm where awm.id in (select widget_id from pt_kpi pk where pk.id in (SELECT kk.kpi_id FROM pt_kpi_kpidetail kk WHERE kk.kpidetail_id=#{kpiDetailId}))</script>")
    Page<WidgetMetaVO> findWidgetMetaPageByKpiDetailId(@Param("kpiDetailId") String kpiDetailId, @Param("pageable") Pageable pageable);

    @Select("<script>select count(1) from ac_widget_meta where name = #{name} and dirId=#{dirId} <if test=\"id != null and id!=''\">and id&lt;&gt;#{id}</if></script>")
    public int existSameName(WidgetMetaVO widgetMetaVO);
}
