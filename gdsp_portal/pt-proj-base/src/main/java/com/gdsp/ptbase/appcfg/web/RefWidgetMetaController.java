package com.gdsp.ptbase.appcfg.web;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gdsp.dev.web.mvc.ext.ViewDecorate;
import com.gdsp.dev.web.mvc.ext.ViewWrapper;
import com.gdsp.ptbase.appcfg.model.WidgetMetaVO;
import com.gdsp.ptbase.appcfg.service.IWidgetMetaService;

/**
 * 部件选择控制类
 *
 * @author paul.yang
 * @version 1.0 2015-8-10
 * @since 1.6
 */
@Controller
@RequestMapping("appcfg/widgetmeta")
public class RefWidgetMetaController {

    /**
     * 部件元数据服务类
     */
    @Autowired
    private IWidgetMetaService widgetMetaService;

    /**
     * 多选部件元数据
     * @return 界面路径
     */
    @RequestMapping("/refmultitree.d")
    @ViewWrapper(wrapped = false)
    @ViewDecorate(decorate = false)
    public String refMultiTree(Model model) {
        Map<String, List<Map>> result = widgetMetaService.findAllWidgetMetaInDirectory();
        model.addAttribute("result", result);
        return "appcfg/widgetmeta/refmultitree";
    }

    @RequestMapping("/list.d")
    @ViewWrapper(wrapped = false, onlyAjax = true)
    public String list(Model model, Pageable pageable, String id) {
        Page<WidgetMetaVO> widgetMetaVO = widgetMetaService.findWidgetMetasByDirIdWithPage(id, pageable);
        model.addAttribute("widgetMetaVO", widgetMetaVO);
        return "";
    }
}
