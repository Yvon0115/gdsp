package com.gdsp.integration.framework.reportentry.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.gdsp.dev.web.mvc.ext.ViewDecorate;
import com.gdsp.dev.web.mvc.ext.ViewWrapper;
import com.gdsp.integration.framework.kpi.service.IKpiReportService;
import com.gdsp.integration.framework.param.service.IParamReportService;
import com.gdsp.integration.framework.reportentry.model.ReportVO;
import com.gdsp.integration.framework.reportentry.service.IReportOprationService;
import com.gdsp.integration.framework.reportentry.service.IWidgetReportService;
import com.gdsp.integration.framework.reportentry.utils.InstantiationFormulateReport;
import com.gdsp.integration.framework.reportentry.utils.ReportConst;
import com.gdsp.ptbase.appcfg.model.WidgetMetaVO;
import com.gdsp.ptbase.appcfg.service.IWidgetMetaService;
import com.gdsp.ptbase.portal.helper.PortalHelper;
import com.gdsp.ptbase.portal.model.IPortlet;
import com.gdsp.ptbase.portal.model.IPortletMeta;

@Controller
@RequestMapping("portal")
public class IntegrationPorletController {

    @Autowired
    private IWidgetReportService reportService;
    @Autowired
    private IWidgetMetaService   metaService;
    @Autowired
    private IParamReportService  paramReportService;
    @Autowired
    private IKpiReportService    kpiReportService;

    /**
     * 页面加载
     * @return 页面路径
     * @throws Exception 
     * @throws JsonMappingException 
     * @throws JsonParseException 
     */
    @RequestMapping(value = "/portlet/report.d")
    @ViewWrapper(wrapped = false)
    @ViewDecorate(decorate = false)
    public String defaultHandle(Model model){
        IPortlet portlet = PortalHelper.getCurrentPortlet();
        IPortletMeta portletMeta = portlet.getMeta();
        WidgetMetaVO baseEntity = metaService.load(portletMeta.getPortletId());
        InstantiationFormulateReport reportClass = new InstantiationFormulateReport();
        IReportOprationService reportOprationService = reportClass.getReportClass(baseEntity.getType());
        ReportVO vo = reportService.load(portletMeta.getPortletId());
        List<String> param = null;
        if (ReportConst.PARAM_free.equals(vo.getParam_type())) {
            param = paramReportService.queryParamIdListByReport(vo.getId());
        }
        List<String> kpi = kpiReportService.queryKpiByReport(vo.getId());
        return reportOprationService.forwardToPage(model, vo, param, kpi);
    }
}
