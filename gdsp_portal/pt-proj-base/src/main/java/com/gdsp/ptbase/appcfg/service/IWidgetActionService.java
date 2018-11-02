package com.gdsp.ptbase.appcfg.service;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.gdsp.dev.core.model.query.Condition;
import com.gdsp.ptbase.appcfg.model.WidgetActionVO;

/**
 * 
* @ClassName: IWidgetActionService
* (资源动作管理接口)
* @author hongwei.xu
* @date 2015年7月22日 下午5:01:48
*
 */
public interface IWidgetActionService {

    /**
     * 
    * @Title: insert
    * (插入操作vo)
    * @param vo
    * @return    参数说明
    * @return WidgetActionVO    返回值说明
    *      */
    WidgetActionVO insert(WidgetActionVO vo);

    /**
     * 
    * @Title: update
    * (更新操作vo)
    * @param vo
    * @return    参数说明
    * @return WidgetActionVO    返回值说明
    *      */
    WidgetActionVO update(WidgetActionVO vo);

    /**
     * 
    * @Title: delete
    * ( 删除操作)
    * @param ids    参数说明
    * @return void    返回值说明
    *      */
    void delete(String... ids);

    /**
     * 
    * @Title: load
    * (加载操作对象)
    * @param id
    * @return    参数说明
    * @return WidgetActionVO    返回值说明
    *      */
    WidgetActionVO load(String id);

    /**
     * 
    * @Title: findActionsByWidgetId
    * (根据部件Id取得所有可用的操作列表)
    * @param widgetId
    * @param widgetType
    * @return    参数说明
    * @return List<WidgetActionVO>    返回值说明
    *      */
    List<WidgetActionVO> findActionsByWidgetId(String widgetId, String widgetType);

    /**
     * 
    * @Title: findAllActions
    * (取得所有操作的映射,以code作为键值)
    * @return    参数说明
    * @return Map<String,WidgetActionVO>    返回值说明
    *      */
    Map<String, WidgetActionVO> findAllActions();

    /**
     * 
    * @Title: queryWidgetActionVOByCondition
    * (查询资源动作)
    * @param condition
    * @param page
    * @return    参数说明
    * @return Page<WidgetActionVO>    返回值说明
    *      */
    Page<WidgetActionVO> queryWidgetActionVOByCondition(Condition condition, Pageable page);

    /**
     * 
    * @Title: findWidgetActionVOByWidgetIDs
    * (根据部件Id取得所有可用的操作列表)
    * @param widgetid
    * @param page
    * @return    参数说明
    * @return Page<WidgetActionVO>    返回值说明
    *      */
    Page<WidgetActionVO> findWidgetActionVOByWidgetIDs(String widgetid, Pageable page);

    /**
    * @Title: isPreset
    * (是否是系统预置数据)
    * @param id	数据字典的ID
    * @return List<String>  返回所有查询数据项的isPreset字段，此字段如果为“Y”，则不能删除。
    *     */
    public List<String> isPreset(String[] id);

    public boolean synchroCheck(WidgetActionVO vo);
}
