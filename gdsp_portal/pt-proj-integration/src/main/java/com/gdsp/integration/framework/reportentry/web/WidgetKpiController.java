package com.gdsp.integration.framework.reportentry.web;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gdsp.dev.base.utils.JsonUtils;
import com.gdsp.dev.core.model.param.PageSerRequest;
import com.gdsp.dev.core.model.param.Sorter;
import com.gdsp.dev.core.model.query.Condition;
import com.gdsp.dev.web.mvc.ext.ViewDecorate;
import com.gdsp.dev.web.mvc.ext.ViewWrapper;
import com.gdsp.dev.web.mvc.model.AjaxResult;
import com.gdsp.integration.framework.kpi.service.IKpiExtendService;
import com.gdsp.integration.framework.kpi.service.IKpiReportService;

@Controller
@RequestMapping("framework/widgetKpi")
public class WidgetKpiController {
    @Autowired
    private IKpiExtendService iKpiExtendService;
    @Autowired
    private IKpiReportService    kpiReport;

    @RequestMapping("/list.d")
    @ViewWrapper(wrapped = false)
    public String list(Model model, String widgetId) {
        Pageable pageable = new PageSerRequest(0, 10);
        Condition condition = new Condition();
        Sorter sort = new Sorter(Direction.ASC, "name");
        model.addAttribute("widgetId", widgetId);
        model.addAttribute("kpis", kpiReport.queryKpiPageByReport(widgetId, condition, sort, pageable));
        return "integration/framework/reportentry/widgetKpi/list";
    }

    @RequestMapping("/listData.d")
    @ViewWrapper(wrapped = false)
    public String listData(Model model, String widgetId, Condition condition, Pageable pageable) {
        Sorter sort = new Sorter(Direction.ASC, "name");
        model.addAttribute("widgetId", widgetId);
        model.addAttribute("kpis", kpiReport.queryKpiPageByReport(widgetId, condition, sort, pageable));
        return "integration/framework/reportentry/widgetKpi/list-data";
    }

    @RequestMapping("/delete.d")
    @ResponseBody
    public Object delete(String widgetId,String... id) {
        kpiReport.deleteKpiReport(widgetId,id);
        return AjaxResult.SUCCESS;
    }

    /**
     * 显示指标树
     * @param model
     * @param widgetId
     * @return
     */
    @RequestMapping("/toBatchAddKpi.d")
    @ViewWrapper(wrapped = false, onlyAjax = true)
    public String toBatchAddKpi(Model model, String widgetId) {
        Map listVO = iKpiExtendService.queryKpiLibraryMap();
        model.addAttribute("kpiMap", listVO);
        model.addAttribute("widgetid", widgetId);
        return "integration/framework/reportentry/widgetKpi/kpi-list";
    }
    @RequestMapping("/loadtreenode.d")
    @ResponseBody
    @ViewWrapper(wrapped = false)
    @ViewDecorate(decorate = false)
    public String loaddirtreenode(Model model, String pid){
        Map dimTree=iKpiExtendService.queryKpiLibraryMap();
        Map<String,String> nodeAttr = new HashMap<String,String>();
        nodeAttr.put("text", "groupName");
        if(dimTree.size()!=0)
            return   JsonUtils.formatMap2TreeViewJson(dimTree, nodeAttr);
        else{
            return "";
            }
    }

    @RequestMapping("/queryKpi.d")
    @ViewWrapper(wrapped = false)
    public String queryKpi(Model model, Condition condition, String id, Pageable pageable) {
        Sorter sort = new Sorter(Direction.ASC, "name");
        model.addAttribute("kpis", iKpiExtendService.queryKpiPage(id, condition, sort, pageable));
        model.addAttribute("pid", id);
        return "integration/framework/reportentry/widgetKpi/kpi-list-data";
    }

    @RequestMapping("/batchSaveKpi.d")
    @ResponseBody
    public Object batchSaveKPI(String id, String widgetid) {
        if ("".equals(id))
            return AjaxResult.SUCCESS;
        String[] indexId = id.split(",");
        kpiReport.addKpiReport(widgetid, indexId);
        return AjaxResult.SUCCESS;
    }
}
