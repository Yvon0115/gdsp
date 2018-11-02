package com.gdsp.ptbase.appcfg.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.gdsp.dev.core.model.query.Condition;
import com.gdsp.ptbase.appcfg.model.WidgetCdnTemplateVO;

/**
 * 
* @ClassName: IWidgetCdnTemplateService
* (WidgetCdnTemplate接口)
* @author songxiang
* @date 2015年10月28日 下午12:30:31
*
 */
public interface IWidgetCdnTemplateService {

    /**
     * 
    * @Title: insert
    * (添加模板)
    * @param widgetCdnTemplateVO    参数说明
    * @return void    返回值说明
    *      */
    void insert(WidgetCdnTemplateVO widgetCdnTemplateVO);

    /**
     * 
    * @Title: queryByCondition
    * (查询模板)
    * @param condition
    * @param page
    * @return    参数说明
    * @return Page<WidgetCdnTemplateVO>    返回值说明
    *      */
    Page<WidgetCdnTemplateVO> queryByCondition(Condition condition, Pageable page);

    /**
     * 
    * @Title: deletes
    * (删除模板)
    * @param ids    参数说明
    * @return void    返回值说明
    *      */
    void deletes(String... ids);

    /**
     * 
    * @Title: update
    * (修改模板)
    * @param widgetCdnTemplateVO    参数说明
    * @return void    返回值说明
    *      */
    void update(WidgetCdnTemplateVO widgetCdnTemplateVO);

    /**
     * 
    * @Title: load
    * (加载操作对象)
    * @param id
    * @return    参数说明
    * @return WidgetCdnTemplateVO    返回值说明
    *      */
    WidgetCdnTemplateVO load(String id);

    /**
     * 
    * @Title: queryByConditionNoPage
    * (根据资源类型查询模板)
    * @param @param condition
    * @param @return    设定文件
    * @return List<WidgetCdnTemplateVO>    返回类型
    *      */
    List<WidgetCdnTemplateVO> queryByConditionNoPage(Condition condition);
}
