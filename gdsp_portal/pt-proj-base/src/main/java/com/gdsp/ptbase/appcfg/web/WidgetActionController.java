package com.gdsp.ptbase.appcfg.web;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gdsp.dev.core.model.query.Condition;
import com.gdsp.dev.web.mvc.model.AjaxResult;
import com.gdsp.dev.web.mvc.ext.ViewDecorate;
import com.gdsp.dev.web.mvc.ext.ViewWrapper;
import com.gdsp.platform.config.global.service.IDefDocService;
import com.gdsp.ptbase.appcfg.model.WidgetActionVO;
import com.gdsp.ptbase.appcfg.service.IWidgetActionService;
import com.gdsp.ptbase.appcfg.service.IWidgetMetaService;

/**
 * 
* @ClassName: WidgetActionController
* (组件Action控制类)
* @author hongwei.xu
* @date 2015年7月22日 下午4:55:44
*
 */
@Controller
@RequestMapping("/widgetmgr/action")
public class WidgetActionController {

    @Autowired
    private IWidgetMetaService   widgetMetaService;
    @Autowired
    private IWidgetActionService widgetActionService;
    @Autowired
    private IDefDocService       defDocService;
    private String               isLocal;

    /**
     * 
    * @Title: list
    * (页面加载)
    * @param model
    * @param pageable
    * @param type
    * @return    参数说明
    * @return String    返回值说明
    *      */
    @RequestMapping("/list.d")
    @ViewWrapper(wrapped = false, onlyAjax = true)
    public String list(Model model, Pageable pageable, String type) {
        Condition condition = new Condition();
        Page<WidgetActionVO> actions = widgetActionService.queryWidgetActionVOByCondition(condition, pageable);
        model.addAttribute("actions", actions);
        model.addAttribute("type", type);
        return "appcfg/widgetmgr/action/list";
    }

    /**
     * 
    * @Title: listData
    * (局部加载)
    * @param model
    * @param condition
    * @param widgetid
    * @param pageable
    * @return    参数说明
    * @return String    返回值说明
    *      */
    @RequestMapping("/listData.d")
    @ViewWrapper(wrapped = false)
    public String listData(Model model, Condition condition, String widgetid, Pageable pageable) {
        if (StringUtils.isNotEmpty(widgetid)) {
            condition.addExpression("widgetid", widgetid);
        }
        Page<WidgetActionVO> actions = widgetActionService.queryWidgetActionVOByCondition(condition, pageable);
        model.addAttribute("widgetid", widgetid);
        model.addAttribute("actions", actions);
        return "appcfg/widgetmgr/action/list-data";
    }

    /**
     * 
    * @Title: add
    * (去添加页面)
    * @param model
    * @param widgetid
    * @return    参数说明
    * @return String    返回值说明
    *      */
    @RequestMapping("/add.d")
    @ViewWrapper(wrapped = false, onlyAjax = true)
    public String add(Model model, String widgetid) {
        model.addAttribute("type", isLocal);
        model.addAttribute("widgetid", widgetid);
        model.addAttribute("types", defDocService.findListByType("ac_res_type"));
        return "appcfg/widgetmgr/action/form";
    }

    /**
     * 
    * @Title: edit
    * (去编辑页面)
    * @param model
    * @param id
    * @return    参数说明
    * @return String    返回值说明
    *      */
    @RequestMapping("/edit.d")
    @ViewWrapper(wrapped = false, onlyAjax = true)
    public String edit(Model model, String id) {
        WidgetActionVO vo = widgetActionService.load(id);
        model.addAttribute("widgetid", vo.getWidgetid());
        model.addAttribute("types", defDocService.findListByType("ac_res_type"));
        model.addAttribute("action", widgetActionService.load(id));
        model.addAttribute("type", isLocal);
        model.addAttribute("editType", "edit");
        return "appcfg/widgetmgr/action/form";
    }

    /**
     * 
    * @Title: save
    * (更新操作)
    * @param model
    * @param vo
    * @return    参数说明
    * @return Object    返回值说明
    *      */
    @RequestMapping("/save.d")
    @ResponseBody
    public Object save(Model model, WidgetActionVO vo) {
        if (StringUtils.isEmpty(vo.getId())) {
            widgetActionService.insert(vo);
        } else {
            //			vo.setVersion(vo.getVersion()+1);
            widgetActionService.update(vo);
        }
        model.addAttribute("type", isLocal);
        //设置默认值
        return AjaxResult.SUCCESS;
    }

    /**
     * 
    * @Title: delete
    * (删除操作)
    * @param id
    * @return    参数说明
    * @return Object    返回值说明
    *      */
    @RequestMapping("/delete.d")
    @ResponseBody
    public Object delete(String... id) {
        List<String> list = widgetActionService.isPreset(id);
        if (list.contains("Y")) {
            return new AjaxResult(AjaxResult.STATUS_ERROR, "删除的选项包含系统预置数据，不能删除。");
        }
        if (id != null && id.length > 0) {
            widgetActionService.delete(id);
        }
        return AjaxResult.SUCCESS;
    }

    /**
     * 
    * @Title: refMultiTree
    * (选择 资源名称)
    * @param model
    * @return    参数说明
    * @return String    返回值说明
    *      */
    @RequestMapping("/refmultitree.d")
    @ViewWrapper(wrapped = false)
    @ViewDecorate(decorate = false)
    public String refMultiTree(Model model) {
        Map<String, List<Map>> result = widgetMetaService.findAllWidgetMetaInDirectory();
        model.addAttribute("result", result);
        return "/appcfg/widgetmgr/action/action_Reference";
    }

    /**
     * 
    * @Title: find
    * (根据widgetId查询)
    * @param model
    * @param widgetId
    * @param type
    * @param pageable
    * @return    参数说明
    * @return String    返回值说明
    *      */
    @RequestMapping("/find.d")
    @ViewWrapper(wrapped = false)
    public String find(Model model, String widgetId, String type, Pageable pageable) {
        Page<WidgetActionVO> actions = widgetActionService.findWidgetActionVOByWidgetIDs(widgetId, pageable);
        model.addAttribute("actions", actions);
        model.addAttribute("widgetid", widgetId);
        isLocal = type;
        model.addAttribute("type", isLocal);
        return "appcfg/widgetmgr/action/list";
    }

    @RequestMapping("/synchroCheck.d")
    @ResponseBody
    public Object synchroCheck(WidgetActionVO vo) {
        return widgetActionService.synchroCheck(vo);
    }
}
