package com.gdsp.ptbase.appcfg.service;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.gdsp.ptbase.appcfg.model.WidgetMetaVO;

/**
 * portlet通用描述信息服务接口
 *
 * @author paul.yang
 * @version 1.0 2015-8-3
 * @since 1.6
 */
public interface IWidgetMetaService {

    /**
     * 新增vo
     * @param vo
     */
    WidgetMetaVO insert(WidgetMetaVO vo);

    /**
     * 
    * @Title: insertBatchVO
    * (批量插入：每次插入限200以内)
    * @param @param metaVOs    设定文件
    * @return void    返回类型
    *      */
    List<WidgetMetaVO> insertBatchVO(List<WidgetMetaVO> metaVOs);

    /**
     * 修改vo
     * @param vo
     */
    WidgetMetaVO update(WidgetMetaVO vo);

    /**
     * 根据id删除
     * @param ids
     * @return
     */
    void delete(String... ids);

    /**
     * 加载portlet描述信息
     * @param id portletid
     * @return WidgetMetaVO对象
     */
    WidgetMetaVO load(String id);

    /**
     * 通用查询方法
     * @param pageId 布局所在页面id
     * @return 以id为键值的widget映射结果集
     */
    Map<String, WidgetMetaVO> findWidgetMetasByPageId(String pageId);

    /**
     * 通用查询方法
     * @param dirId 目录id
     * @return 分类下的部件定义
     */
    List<WidgetMetaVO> findWidgetMetasByDirId(String dirId);

    /**
     * 查询所有目录下的部件描述信息,返回树状maplist
     * @return 所有目录下的部件信息(树状maplist)
     */
    Map<String, List<Map>> findAllWidgetMetaInDirectory();

    /**
     * 通过dirID查询WidgetMeta（带分页）
     * @param id 目录id
     * @return 分类下的部件定义（Page对象）
     */
    Page<WidgetMetaVO> findWidgetMetasByDirIdWithPage(String id,
            Pageable pageable);

    /**
    * @Title: findWidgetMetaPageByKpiDetailId
    * (通过指标ID查询指标所关联的报表集合)
    * @param kpiDetailId	指标ID
    * @return Page<WidgetMetaVO>    返回带分页的报表集合
    *     */
    Page<WidgetMetaVO> findWidgetMetaPageByKpiDetailId(String kpiDetailId, Pageable pageable);

    public boolean synchroCheck(WidgetMetaVO widgetMetaVO);
}
