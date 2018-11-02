package com.gdsp.integration.framework.reportentry.web;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gdsp.dev.base.exceptions.BusinessException;
import com.gdsp.dev.base.utils.JsonUtils;
import com.gdsp.dev.core.common.AppConfig;
import com.gdsp.dev.core.common.AppContext;
import com.gdsp.dev.core.model.param.PageSerRequest;
import com.gdsp.dev.core.model.query.Condition;
import com.gdsp.dev.web.mvc.model.AjaxResult;
import com.gdsp.dev.web.mvc.ext.ViewDecorate;
import com.gdsp.dev.web.mvc.ext.ViewWrapper;
import com.gdsp.integration.framework.param.model.TemplateVO;
import com.gdsp.integration.framework.param.service.IParamService;
import com.gdsp.integration.framework.reportentry.model.ReportVO;
import com.gdsp.integration.framework.reportentry.service.IWidgetReportService;
import com.gdsp.integration.framework.reportentry.utils.ReportConst;
import com.gdsp.platform.config.customization.helper.SystemConfigConst;
import com.gdsp.platform.config.global.model.DefDocVO;
import com.gdsp.platform.config.global.service.IDefDocListService;
import com.gdsp.platform.systools.datasource.model.DataSourceVO;
import com.gdsp.platform.systools.datasource.service.IDataSourceQueryPubService;
import com.gdsp.platform.systools.datasource.service.IDataSourceService;
import com.gdsp.ptbase.appcfg.helper.AppConfigConst;
import com.gdsp.ptbase.appcfg.service.IPageWidgetService;

@Controller
@RequestMapping("framework/reportentry")
public class WidgetReportController {

    @Autowired
    private IWidgetReportService reportService;
    @Autowired
    private IDataSourceService   datasourceService;
    @Autowired
    private IDataSourceQueryPubService dataSourceQueryPubService;
    @Autowired
    private IDefDocListService   defListService;
    @Resource
    private IPageWidgetService   pwSeervice;
    @Autowired
    private IParamService        paramService;

    @RequestMapping("/list.d")
    public String list(Model model) {
        return "integration/framework/reportentry/list";
    }

    @RequestMapping("/listData.d")
    @ViewWrapper(wrapped = false)
    public String listdata(Model model, Condition condition, String id,Pageable pageable) {
        String path = null;
        if (!StringUtils.isEmpty(id)) {
            String[] ds_p = getDs_Path(id);
            if (ds_p == null) {
                throw new BusinessException("请设置数据源！");
            } else {
                condition.addExpression("data_source", ds_p[0]);
                path = ds_p[1];
            }
        }
        condition.addExpression("leaf", "Y", "=");
        Page<ReportVO> widgets = reportService.queryReportPage(path, condition, pageable);
        model.addAttribute("widgets", widgets);
        return "integration/framework/reportentry/list-data";
    }

    @RequestMapping("/loadReportfuntree.d")
    @ViewWrapper(wrapped = false)
    public String loadcognosfuntree(Model model, String id) {
        List<ReportVO> lists = null;
        if (StringUtils.isEmpty(id)) {
            lists = dirTansfor(getDataSourceType());
//            Map <String,String> nodeAttr = new HashMap<String,String>();
//            nodeAttr.put("text", "report_name");
//            if(lists.size()!=0){
//                return JsonUtils.formatList2TreeViewJson(lists, nodeAttr);
//            }else{
//                return " ";
//            }
        } else {
            String[] ds_p = getDs_Path(id);
            if (ds_p == null) {
                lists = null;
            } else {
                lists = reportService.queryReportByParentPath(ds_p[1], ds_p[0], false);
            }
        }
//        Map <String,String> nodeAttr = new HashMap<String,String>();
//        nodeAttr.put("text", "report_name");
//        if(lists.size()!=0){
//            return JsonUtils.formatList2TreeViewJson(lists, nodeAttr);
//        }else{
//            return " ";
//        }
        model.addAttribute("nodes", lists);
        return "integration/framework/reportentry/loadreportfuntree";
    }

    @RequestMapping("/toSysReportFolder.d")
    @ResponseBody
    public Object toSysCognosFolder(String data_source_id) {
        DataSourceVO dataSourceVO = dataSourceQueryPubService.load(data_source_id);
        if (StringUtils.isEmpty(data_source_id) && dataSourceVO != null) {
            return new AjaxResult(AjaxResult.STATUS_ERROR, "页面有问题，请刷新后重试");
        } else if (dataSourceVO==null || checkDatasource(getDataSourceType(), dataSourceVO.getType()) == false) {
            return new AjaxResult(AjaxResult.STATUS_ERROR, "请选择根路径同步");
        } else {
            //
//            List<DataSourceVO> lists = datasourceService.queryDataSourceByType(data_source_id);
//            if (lists.size() < 1) {
//                return new AjaxResult(AjaxResult.STATUS_ERROR, "没有找到相应的数据源");
//            }
            reportService.synLocalReportfromRemote(dataSourceVO);
        }
        // 设置默认值
        return new AjaxResult(AjaxResult.STATUS_SUCCESS, "同步成功");
    }

    @RequestMapping("/edit.d")
    @ViewWrapper(wrapped = false, onlyAjax = true)
    public String edit(Model model, String id) {
        ReportVO vo = reportService.load(id);
        model.addAttribute("widget", vo);
        if (ReportConst.Cognos.equals(vo.getData_source_type())) {
            model.addAttribute("param_type", defListService.getDefDocsByTypeID(AppConfigConst.WIDGET_AC_PARAM_TYPE));
            model.addAttribute("report_type", defListService.getDefDocsByTypeID(AppConfigConst.WIDGET_AC_RPT_TYPE));
            return "integration/cognos/form";
        } else {
            return "integration/framework/reportentry/form";
        }
    }

    @RequestMapping("/save.d")
    @ResponseBody
    public Object save(ReportVO pubWedgetVO) {
        if (StringUtils.isEmpty(pubWedgetVO.getId())) {
            return new AjaxResult(AjaxResult.STATUS_ERROR, "页面错误，刷新后重试");
        } else {
            reportService.updateReport(pubWedgetVO);
        }
        // 设置默认值
        return AjaxResult.SUCCESS;
    }

    @RequestMapping("/delete.d")
    @ResponseBody
    public Object delete(String... id) {
        //删除前判断，如果删除的是目录，则需要下面没有子内容才可以删除
        if (id != null && id.length > 0) {
            Pageable pageable = new PageSerRequest(0, 10);
            DataSourceVO dataSourceVO = dataSourceQueryPubService.load(id[0]);
            for (String pid : id) {
                if (dataSourceVO!=null) {
                    return new AjaxResult(AjaxResult.STATUS_ERROR, "根路径不允许删除");
                }
                ReportVO vo = reportService.load(pid);
                if ("N".equals(vo.getLeaf())) {
                    Page<ReportVO> widgets = reportService.queryReportPage(vo.getReport_path(), null, pageable);
                    List<ReportVO> subNodes = widgets.getContent();
                    if (subNodes != null && subNodes.size() > 0) {
                        return new AjaxResult(AjaxResult.STATUS_ERROR, "目录[" + vo.getReport_name() + "]包含下级，不允许删除！");
                    }
                } else {
                    if (pwSeervice.findWidgetByWidgetId(pid) != null && pwSeervice.findWidgetByWidgetId(pid).size() > 0) {
                        return new AjaxResult(AjaxResult.STATUS_ERROR, "资源[" + vo.getReport_name() + "]已被功能页面引用，不允许删除！");
                    }
                }
            }
            reportService.deleteReport(id);
            return AjaxResult.SUCCESS;
        }
        return new AjaxResult(AjaxResult.STATUS_ERROR, "没有选择要删除的对象");
    }

    @RequestMapping("/editComments.d")
    @ViewWrapper(wrapped = false, onlyAjax = true)
    public String editComments(Model model, String widget_id) {
        ReportVO nodeVO = reportService.load(widget_id);
        model.addAttribute("widget_id", widget_id);
        model.addAttribute("comments", nodeVO.getComments());
        return "integration/framework/reportentry/editComments";
    }

    @RequestMapping("/saveComments.d")
    @ResponseBody
    public Object saveComments(String widget_id, String comments) {
        ReportVO nodeVO = reportService.load(widget_id);
        nodeVO.setComments(comments);
        reportService.updateReport(nodeVO);
        return AjaxResult.SUCCESS;
    }

    /**
     * 外部接口，添加组件时使用
     * @param model
     * @param parent_id
     * @return
     */
    @RequestMapping("/refwidgetmetaext.d")
    @ViewWrapper(wrapped = false)
    @ViewDecorate(decorate = false)
    public String widgetMetaExt(Model model, String type,String id) {
        model.addAttribute("id", id);
        model.addAttribute("type", type);
        return "integration/framework/reportentry/ref-widgetmeta-ext";
    }

    /**
     * 加载资源树
     * 添加组件时使用
     * @param model
     * @param parent_id
     * @return
     */
    @RequestMapping("/loadrunqiantreenode.d")
    @ViewWrapper(wrapped = false)
    @ViewDecorate(decorate = false)
    public String loadRunQianTreeNode(Model model, String parent_id, String type,String id) {
        if (StringUtils.isEmpty(parent_id)) {
            DataSourceVO dataSourceVO = dataSourceQueryPubService.load(id);
            String path = dataSourceVO.getPath();
//            String path = AppConfig.getInstance().getString(type + ".path");
            model.addAttribute("nodes", reportService.queryReportByParentPath(path, id, true));
        } else {
            ReportVO node = reportService.load(parent_id);
            String path = node.getReport_path();
            model.addAttribute("nodes", reportService.queryReportByParentPath(path, node.getData_source(), true));
        }
        model.addAttribute("id",id);
        model.addAttribute("type", type);
        return "integration/framework/reportentry/ref-widgetmeta-ext-node";
    }

    //加工根路径
    private List<ReportVO> dirTansfor(String[] dirs) {
        if(dirs.length > 0){
//        List<ReportVO> list = new ArrayList<ReportVO>();
            List<ReportVO> lists = new ArrayList<ReportVO>();
            List<DefDocVO> defDocVOs = defListService.getDefDocsByTypeID(ReportConst.AC_DATASOURCE_TYPE);//reportService.queryDocName(dis).getDoc_name();
            List<DataSourceVO> dataSourceVOs = datasourceService.queryEnableDataSource(dirs);
            for(DataSourceVO VO : dataSourceVOs){
                ReportVO vo = new ReportVO();
                for(DefDocVO defDocVO : defDocVOs){
                    String code = defDocVO.getDoc_code();
                    String type = VO.getType();
                    if(code.equals(type)){
                        vo.setReport_name(defDocVO.getDoc_name());
                        break;
                    }
                }
                vo.setReport_path(VO.getPath());
                vo.setData_source_name(VO.getName());
                vo.setId(VO.getId());
                vo.setParent_path("");
                vo.setLeaf("N");
                lists.add(vo);
            }
            return lists;
            } else {
            return null;
        }
    }

    //获取数据源ID与路径
    private String[] getDs_Path(String id) {
        String[] ds_path = new String[2];
//        List<DataSourceVO> dataSourceVOs = datasourceService.queryEnableDataSource(getDataSourceType());
        DataSourceVO dataSourceVO = dataSourceQueryPubService.load(id);
        if (dataSourceVO != null && checkDatasource(getDataSourceType(), dataSourceVO.getType())) {
//            String path = AppConfig.getInstance().getString(id + ".path");
//            List<DataSourceVO> dats = datasourceService.queryDataSourceByType(id);
            String path = dataSourceVO.getPath();
            ds_path[0] = id;
            ds_path[1] = path;
        } else {
            ReportVO vo = reportService.load(id);
            ds_path[0] = vo.getData_source();
            ds_path[1] = vo.getReport_path();
        }
        return ds_path;
    }

    /**
     * 调用允许展示的资源列表
     * @return
     */
    private String[] getDataSourceType() {
        String reportIntsInfo = AppConfig.getProperty(SystemConfigConst.SYSTEMCONFIG_REPORTINTSINFO);
        //        String repor="birt122,";
        if (!StringUtils.isEmpty(reportIntsInfo)) {
            reportIntsInfo = reportIntsInfo.substring(0, reportIntsInfo.lastIndexOf(","));
            String[] reportIntsStrings = reportIntsInfo.split(",");
            return reportIntsStrings;
        }
        return new String[] {};
    }

    private boolean checkDatasource(String[] dirs, String dataSource_type) {
        for (String dir : dirs) {
            if (dir.equals(dataSource_type)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 加载查询模板
     * @param model
     * @return
     */
    @RequestMapping("/loadQueryTemplate.d")
    @ViewWrapper(wrapped = false)
    @ViewDecorate(decorate = false)
    public String loadQueryTemplate(Model model) {
        String rootPath = AppContext.getAppRealPath() + ReportConst.TEMPLATE_PATH;
        Map<String, List<TemplateVO>> map = new HashMap<String, List<TemplateVO>>();
        paramService.loadQueryTemplate(rootPath, rootPath, map);
        model.addAttribute("rootPath", rootPath);
        model.addAttribute("nodesMap", map);
        return "integration/framework/reportentry/queryTemplate-tree";
    }
}
