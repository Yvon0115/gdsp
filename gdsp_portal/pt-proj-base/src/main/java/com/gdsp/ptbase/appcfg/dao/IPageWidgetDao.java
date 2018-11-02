package com.gdsp.ptbase.appcfg.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.gdsp.dev.persist.ext.MBDao;
import com.gdsp.dev.persist.result.MapListResultHandler;
import com.gdsp.ptbase.appcfg.model.PageWidgetVO;

@MBDao
public interface IPageWidgetDao {

    /**
     * 新增记录
     * @param vo vo对象
     * @return 成功记录数
     */
    int insert(PageWidgetVO vo);

    /**
     * 更新记录
     * @param vo vo对象
     * @return 成功记录数
     */
    int update(PageWidgetVO vo);

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
    PageWidgetVO load(String id);

    /**
     * 根据页面主键加载页面中的部件列表
     * @param pageId 页面主键
     * @param handler 结果处理
     * @return 部件列表
     */
    void findWidgetByPageId(String pageId, MapListResultHandler<PageWidgetVO> handler);

    /**
     * 根据页面主键加载页面中的部件(带有meta信息)列表
     * @param pageId 页面主键
     * @param handler 结果处理
     * @return 部件(带有meta信息)列表
     */
    void findFullWidgetByPageId(String pageId, MapListResultHandler<PageWidgetVO> handler);

    List<PageWidgetVO> findWidgetByWidgetId(String widgetId);

    int deleteWidgetByPageId(String[] pageId);
    
    String findWidgetIdByPageId(String id);
 
    /**
     * 删除自定义布局时，将已保存的自定义布局页面恢复为默认单列布局
     * @param column_id 
     * @param page_id
     */
    void updateDefColumn(@Param("column_id")String column_id,@Param("page_id")String page_id);
}
