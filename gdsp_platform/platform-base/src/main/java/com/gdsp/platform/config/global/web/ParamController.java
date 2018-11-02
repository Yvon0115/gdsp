package com.gdsp.platform.config.global.web;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gdsp.dev.core.model.param.Sorter;
import com.gdsp.dev.core.model.query.Condition;
import com.gdsp.dev.web.mvc.ext.ViewWrapper;
import com.gdsp.dev.web.mvc.model.AjaxResult;
import com.gdsp.platform.config.global.model.ParamVO;
import com.gdsp.platform.config.global.service.IParamService;

/**
 * 客户化 - 参数设置
 * 2016年11月29日 上午9:55:30
 */
@Controller
@RequestMapping("conf/param")
public class ParamController {

    @Resource
    private IParamService paramservice;

    @RequestMapping("/list.d")
    public String listParam(HttpServletRequest request, Model model, Pageable pageable) {
        Condition cond = new Condition();
        Sorter sort = new Sorter(Direction.DESC, "createTime");
        Page<ParamVO> paramvos = paramservice.queryParamDefList(cond, pageable, sort);
        model.addAttribute("page", paramvos);
        return "/conf/param/list";
    }

    @RequestMapping("/listdata.d")
    @ViewWrapper(wrapped = false)
    public String listParamData(HttpServletRequest request, Model model, Pageable pageable, Condition cond) {
        Sorter sort = new Sorter(Direction.DESC, "createTime");
        Page<ParamVO> paramvos = paramservice.queryParamDefList(cond, pageable, sort);
        model.addAttribute("page", paramvos);
        return "/conf/param/paramtable";
    }

    /** 跳转到修改参数界面 */
    @RequestMapping("/editParamValue.d")
    @ViewWrapper(wrapped = false)
    public String editParamValue(Model model, String id) {
        model.addAttribute("param", paramservice.loadParam(id));
        return "/conf/param/editParaValue";
    }

    /** 修改参数 */
    @RequestMapping("/setParamValue.d")
    @ResponseBody
    public Object setParamValue(HttpServletRequest request, Model model, ParamVO paramVO) {
        paramservice.setParamValue(paramVO.getParcode(), paramVO.getParvalue());
        return AjaxResult.SUCCESS;
    }

    /** 恢复默认 */
    @RequestMapping("/restoreDefault.d")
    @ResponseBody
    public Object restoreDefault(HttpServletRequest request, Model model, String id) {
        paramservice.restoreDefault(id);
        return AjaxResult.SUCCESS;
    }
}
