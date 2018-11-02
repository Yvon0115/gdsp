package com.gdsp.integration.framework.param.web;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gdsp.dev.base.exceptions.DevException;
import com.gdsp.dev.core.model.param.Sorter;
import com.gdsp.dev.core.model.query.Condition;
import com.gdsp.dev.web.mvc.model.AjaxResult;
import com.gdsp.dev.web.mvc.ext.ViewWrapper;
import com.gdsp.integration.framework.param.model.AcParam;
import com.gdsp.integration.framework.param.service.IParamService;
import com.gdsp.platform.config.global.service.IDefDocListService;
import com.gdsp.platform.config.global.service.IDefDocService;
import com.gdsp.ptbase.appcfg.helper.AppConfigConst;

@RequestMapping("param/param")
@Controller("com.gdsp.integration.framework.param.web.ParamController")
public class ParamController {

    @Autowired
    private IParamService      service;
    @Autowired
    private IDefDocService     defService;
    @Autowired
    private IDefDocListService docListService;

    @RequestMapping("/test.d")
    @ViewWrapper(wrapped = false)
    public String test() {
        //service.loadQueryTemplate("gdsp/page/portlet/runqian/condition");
        return null;
    }

    @RequestMapping("/list.d")
    @ViewWrapper(wrapped = false)
    public String list(Model model, String pid, Condition condition, Pageable pageable) {
        Sorter sort = new Sorter(Direction.ASC, "sortnum");
        condition.addExpression("pid", pid);
        model.addAttribute("params", service.queryByCondition(condition, pageable, sort));
        model.addAttribute("widget_id", pid);
        return "integration/framework/param/list";
    }

    @RequestMapping("/listData.d")
    @ViewWrapper(wrapped = false)
    public String listData(Model model, Condition condition, String id, Pageable pageable) {
        Sorter sort = new Sorter(Direction.ASC, "name");
        if (StringUtils.isNotBlank(id)) {
            condition.addExpression("pid", id);
        } else {
            condition.addExpression("pid", null, "is not null");
        }
        model.addAttribute("params", service.queryByCondition(condition, pageable, sort));
        model.addAttribute("pid", id);
        return "integration/framework/param/list-data";
    }

    @RequestMapping("/toAdd.d")
    @ViewWrapper(wrapped = false, onlyAjax = true)
    public String add(Model model, String pid) throws DevException {
        model.addAttribute("editType", "add");
        model.addAttribute("pid", pid);
        model.addAttribute("data_type", docListService.getDefDocsByTypeID(AppConfigConst.WIDGET_PARAM_DATA_TYPE));
        model.addAttribute("data_source_type", docListService.getDefDocsByTypeID(AppConfigConst.WIDGET_DATA_SOURCE_TYPE));
        model.addAttribute("comp_type", docListService.getDefDocsByTypeID(AppConfigConst.WIDGET_AC_COMP_TYPE));
        model.addAttribute("doclists", docListService.findAll());
        return "integration/framework/param/form";
    }

    @RequestMapping("/save.d")
    @ResponseBody
    public Object save(AcParam vo) {
        if (StringUtils.isNotEmpty(vo.getId())) {
            service.updateParam(vo);
        } else {
            service.addParam(vo);
        }
        return AjaxResult.STATUS_SUCCESS;
    }

    @RequestMapping("/edit.d")
    @ViewWrapper(wrapped = false, onlyAjax = true)
    public String edit(Model model, String id) throws DevException {
        AcParam vo = service.load(id);
        model.addAttribute("param", vo);
        model.addAttribute("pid", vo.getPid());
        model.addAttribute("data_type", docListService.getDefDocsByTypeID(AppConfigConst.WIDGET_PARAM_DATA_TYPE));
        model.addAttribute("data_source_type", docListService.getDefDocsByTypeID(AppConfigConst.WIDGET_DATA_SOURCE_TYPE));
        model.addAttribute("comp_type", docListService.getDefDocsByTypeID(AppConfigConst.WIDGET_AC_COMP_TYPE));
        model.addAttribute("doclists", docListService.findAll());
        return "integration/framework/param/form";
    }

    @RequestMapping("/delete.d")
    @ResponseBody
    public Object delete(String... id) {
        service.deleteParamByIds(id);
        return AjaxResult.SUCCESS;
    }
}
