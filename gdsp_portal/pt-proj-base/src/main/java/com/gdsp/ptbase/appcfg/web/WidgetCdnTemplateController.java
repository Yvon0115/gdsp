package com.gdsp.ptbase.appcfg.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gdsp.dev.base.exceptions.DevException;
import com.gdsp.dev.core.model.query.Condition;
import com.gdsp.dev.web.mvc.ext.ViewWrapper;
import com.gdsp.dev.web.mvc.model.AjaxResult;
import com.gdsp.platform.config.global.service.IDefDocService;
import com.gdsp.ptbase.appcfg.helper.AppConfigConst;
import com.gdsp.ptbase.appcfg.model.WidgetCdnTemplateVO;
import com.gdsp.ptbase.appcfg.service.IWidgetCdnTemplateService;

/**
 * 
* @ClassName: WidgetCdnTemplateController
* (资源动作模板控制类)
* @author songxiang
* @date 2015年10月28日 下午1:40:51
*
 */
@Controller
@RequestMapping("/widget/cdnTemplate")
public class WidgetCdnTemplateController {

    @Autowired
    private IWidgetCdnTemplateService WidgetCdnTemplateService;
    @Autowired
    private IDefDocService            defDocService;

    /**
     * 
    * @Title: queryFiles
    * (页面加载)
    * @param model
    * @param pageable
    * @return    参数说明
    * @return String    返回值说明
    *      */
    @RequestMapping("/list.d")
    public String queryFiles(Model model, Pageable pageable) {
        Condition condition = new Condition();
        Page<WidgetCdnTemplateVO> paramsPage = WidgetCdnTemplateService.queryByCondition(condition, pageable);
        model.addAttribute("paramsPage", paramsPage);
        return "appcfg/cdnTemplate/list";
    }

    /**
     * 
    * @Title: listData
    * (局部加载)
    * @param model
    * @param pageable
    * @return    参数说明
    * @return String    返回值说明
    *      */
    @RequestMapping("/listData.d")
    @ViewWrapper(wrapped = false)
    public String listData(Model model, Pageable pageable) {
        Condition condition = new Condition();
        Page<WidgetCdnTemplateVO> paramsPage = WidgetCdnTemplateService.queryByCondition(condition, pageable);
        model.addAttribute("paramsPage", paramsPage);
        return "appcfg/cdnTemplate/list-data";
    }

    /**
     * 
    * @Title: edit
    * (去编辑页面)
    * @param model
    * @param id
    * @return
    * @throws DevException    参数说明
    * @return String    返回值说明
    *      */
    @RequestMapping("/edit.d")
    @ViewWrapper(wrapped = false, onlyAjax = true)
    public String edit(Model model, String id) throws DevException {
        model.addAttribute("type", defDocService.findListByType(AppConfigConst.WIDGET_AC_RES_TYPE));
        model.addAttribute("widgetCdnTemplateVO", WidgetCdnTemplateService.load(id));
        return "appcfg/cdnTemplate/form";
    }

    /**
     * 
    * @Title: add
    * (去添加页面)
    * @param model
    * @return    参数说明
    * @return String    返回值说明
    *      */
    @RequestMapping("/add.d")
    @ViewWrapper(wrapped = false, onlyAjax = true)
    public String add(Model model) {
        model.addAttribute("type", defDocService.findListByType(AppConfigConst.WIDGET_AC_RES_TYPE));
        return "appcfg/cdnTemplate/form";
    }

    /**
     * 
    * @Title: save
    * (更新操作)
    * @param widgetCdnTemplateVO
    * @return    参数说明
    * @return Object    返回值说明
    *      */
    @RequestMapping("/save.d")
    @ViewWrapper(wrapped = false, onlyAjax = true)
    @ResponseBody
    public Object save(WidgetCdnTemplateVO widgetCdnTemplateVO) {
        WidgetCdnTemplateService.insert(widgetCdnTemplateVO);
        return AjaxResult.SUCCESS;
    }

    /**
     * 
    * @Title: delete
    * (删除)
    * @param id
    * @return    参数说明
    * @return Object    返回值说明
    *      */
    @RequestMapping("/delete.d")
    @ResponseBody
    public Object delete(String... id) {
        if (id != null && id.length > 0) {
            WidgetCdnTemplateService.deletes(id);
        }
        return AjaxResult.SUCCESS;
    }
}
