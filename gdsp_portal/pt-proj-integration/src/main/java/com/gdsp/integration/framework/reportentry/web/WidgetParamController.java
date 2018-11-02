package com.gdsp.integration.framework.reportentry.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
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
import com.gdsp.dev.core.model.query.ChainLogicExpression;
import com.gdsp.dev.core.model.query.Condition;
import com.gdsp.dev.core.model.query.ValueExpression;
import com.gdsp.dev.web.mvc.ext.ViewDecorate;
import com.gdsp.dev.web.mvc.ext.ViewWrapper;
import com.gdsp.dev.web.mvc.model.AjaxResult;
import com.gdsp.integration.framework.param.model.AcParamLibrary;
import com.gdsp.integration.framework.param.model.AcParamRelation;
import com.gdsp.integration.framework.param.service.IParamLibraryService;
import com.gdsp.integration.framework.param.service.IParamReportService;
import com.gdsp.integration.framework.param.service.IParamService;
import com.gdsp.integration.framework.reportentry.service.IWidgetReportService;

@Controller
@RequestMapping("framework/widgetParam")
public class WidgetParamController {

    @Autowired
    private IWidgetReportService reportService;
    @Autowired
    private IParamReportService  paramReport;
    @Autowired
    private IParamLibraryService paramLiService;
    @Autowired
    private IParamService        paramService;

    @RequestMapping("/list.d")
    @ViewWrapper(wrapped = false)
    public String list(Model model, String widget_id) {
        Pageable pageable = new PageSerRequest(0, 10);
        Sorter sort = new Sorter(Direction.ASC, "a.name");
        model.addAttribute("params", paramReport.queryParamPageByReport(widget_id, null, sort, pageable));
        model.addAttribute("widget_id", widget_id);
        return "integration/framework/reportentry/widgetParam/list";
    }

    @RequestMapping("/listData.d")
    @ViewWrapper(wrapped = false)
    public String listData(Model model, Condition condition, String widget_id, Pageable pageable) {
        Sorter sort = new Sorter(Direction.ASC, "a.name");
        model.addAttribute("widget_id", widget_id);
        model.addAttribute("params", paramReport.queryParamPageByReport(widget_id, condition, sort, pageable));
        return "integration/framework/reportentry/widgetParam/list-data";
    }

    @RequestMapping("/delete.d")
    @ResponseBody
    public Object delete(String... id) {
        paramReport.deleteParamRelation(id);
        return AjaxResult.SUCCESS;
    }

    /**
     * 显示参数树
     * @param model
     * @param widgetId
     * @return
     */
    @RequestMapping("/toBatchAddParam.d")
    @ViewWrapper(wrapped = false, onlyAjax = true)
    public String toBatchAddKpi(Model model, String widget_id) {
        List<AcParamLibrary> list = paramLiService.queryAllParamLibrary();
        model.addAttribute("nodes", list);
        model.addAttribute("widget_id", widget_id);
        return "integration/framework/reportentry/widgetParam/param-list";
    }

    @RequestMapping("/queryParam.d")
    public String queryParam(Model model, Condition condition, String id, Pageable pageable, String widget_id) {
        Sorter sort = new Sorter(Direction.ASC, "name");
        if (StringUtils.isNotBlank(id)) {
            condition.addExpression("pid", id);
        } else {
            condition.addExpression("pid", null, "is not null");
        }
        model.addAttribute("widget_id", widget_id);
        model.addAttribute("params", paramService.queryByCondition(condition, pageable, sort));
        return "integration/framework/reportentry/widgetParam/param-list-data";
    }

    @RequestMapping("/loaddirtreenode.d")
    @ResponseBody
    @ViewWrapper(wrapped = false)
    @ViewDecorate(decorate = false)
    public String loaddirtreenode(Model model, String pid){
        Condition condition = new Condition();
//        ChainLogicExpression ch = new ChainLogicExpression();
//        if (!StringUtils.isEmpty(pid)) {
//            ValueExpression category = new ValueExpression("pid", pid);
//            ch.addExpression(category);
//        } else {
//            ch.setOr(true);
//            ValueExpression category = new ValueExpression("pid", "");
//            ValueExpression c2 = new ValueExpression("pid", null, "is null");
//            ch.addExpression(category);
//            ch.addExpression(c2);
//        }
        Sorter sort = null;
        Map dimTree=paramLiService.queryByCondition(condition, sort);
        Map<String,String> nodeAttr = new HashMap<String,String>();
        nodeAttr.put("text", "name");
        if(dimTree.size()!=0)
            return   JsonUtils.formatMap2TreeViewJson(dimTree, nodeAttr);
        else{
            return "";
            }
//        condition.addExpressions(ch);
//        Sorter sort = null;
//        
//        model.addAttribute("nodes", paramLiService.queryByCondition(condition, sort));
//        return "integration/framework/reportentry/widgetParam/treenode";
    }

    @RequestMapping("/batchSaveParam.d")
    @ResponseBody
    public Object batchSaveKPI(String widget_id, String id) {
        if ("".equals(id))
            return AjaxResult.SUCCESS;
        for (String indexId : id.split(",")) {
            AcParamRelation vo = new AcParamRelation();
            vo.setParamId(indexId);
            vo.setReportId(widget_id);
            paramReport.addParamRelation(vo);
        }
        return AjaxResult.SUCCESS;
    }
}
