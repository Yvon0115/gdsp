package com.gdsp.ptbase.appcfg.service;

import java.util.List;
import java.util.Map;

import com.gdsp.ptbase.appcfg.model.PageWidgetVO;

/**
 * 页面portlet服务接口
 * @author paul.yang
 * @version 1.0 2015-8-3
 * @since 1.6
 */
public interface IPageWidgetService {

    /**
     * 新增页面部件
     * @param widget 页面部件对象
     * @return 新增后的页面部件
     */
    PageWidgetVO insert(PageWidgetVO widget);

    /**
     * 更新页面部件对象
     * @param widget 页面部件
     * @return 更新后页面部件
     */
    PageWidgetVO update(PageWidgetVO widget);

    /**
     * 删除指定的页面部件
     * @param id 部件id
     */
    void delete(String... id);

    /**
     * 加载指定的页面部件对象
     * @param id 页面部件
     * @return
     */
    PageWidgetVO load(String id);

    /**
     * 根据页面主键加载所有的页面部件
     * @param pageId 页面主键
     * @return 页面部件列表
     */
    Map<String, List<PageWidgetVO>> findWidgetByPageId(String pageId);

    /**
     * 根据页面主键加载页面中的部件(带有meta信息)列表
     * @param pageId 页面主键
     * @return 部件(带有meta信息)列表
     */
    Map<String, List<PageWidgetVO>> findFullWidgetByPageId(String pageId);

    List<PageWidgetVO> findWidgetByWidgetId(String widgetId);

    /**
     * 
     * @Title: deleteWidgetByPageId 
     * @Description: 根据页面ID删除页面关联的部件
     * @param page_id	页面ID
     * @return void    返回类型	
     */
    void deleteWidgetByPageId(String[] pageId);
    /**
     * 给页面定制发布成功能的页面     查询报表说明使用
     * 通过页面id查询组件id也就是报表id 
     * @param page_id
     * @return
     */
    String findWidgetIdByPageId(String id);

	/**
	 * 将删除的自定义布局的页面恢复至默认单列
	 * @param page_id 当前页面id
	 */
	void updateDefColumn(String page_id);
}
