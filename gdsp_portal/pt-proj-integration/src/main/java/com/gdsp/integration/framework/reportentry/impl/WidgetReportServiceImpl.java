package com.gdsp.integration.framework.reportentry.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gdsp.dev.core.common.AppConfig;
import com.gdsp.dev.core.model.query.Condition;
import com.gdsp.integration.framework.reportentry.dao.WidgetReportDao;
import com.gdsp.integration.framework.reportentry.model.ReportVO;
import com.gdsp.integration.framework.reportentry.service.IReportOprationService;
import com.gdsp.integration.framework.reportentry.service.IWidgetReportService;
import com.gdsp.integration.framework.reportentry.utils.InstantiationFormulateReport;
import com.gdsp.integration.framework.reportentry.utils.ReportConst;
import com.gdsp.platform.systools.datasource.model.DataSourceVO;
import com.gdsp.platform.systools.datasource.service.IDataSourceService;
import com.gdsp.ptbase.appcfg.model.WidgetMetaVO;
import com.gdsp.ptbase.appcfg.service.IWidgetMetaService;

@Service
@Transactional(readOnly = true)
public class WidgetReportServiceImpl implements IWidgetReportService {

    @Autowired
    private WidgetReportDao    widgetReportDao;
    @Autowired
    private IWidgetMetaService metaService;
    @Autowired
    private IDataSourceService datasourceService;

    @Override
    public ReportVO load(String reportId) {
        
        return widgetReportDao.load(reportId);
    }

    @Transactional
    @Override
    public void deleteReport(String[] ids) {
        
        metaService.delete(ids);
        widgetReportDao.deleteReport(ids);
    }

    @Transactional
    @Override
    public void updateReport(ReportVO vo) {
        
        widgetReportDao.updateReport(vo);
    }

    @Override
    public Page<ReportVO> queryReportPage(String path, Condition condition,
            Pageable page) {
        
        return widgetReportDao.queryReportPage(path, condition, page);
    }

    @Override
    public List<ReportVO> queryReportByParentPath(String path, String datasourceID, boolean leaf) {
        
        Condition condition = new Condition();
        condition.addExpression("data_source", datasourceID);
        condition.addExpression("parent_path", path);
        if (!leaf) {
            condition.addExpression("leaf", "N");
        }
        return widgetReportDao.queryReportByDataSource(condition);
    }

    @Transactional
    @Override
    public void synLocalReportfromRemote(DataSourceVO datasource) {
        if (datasource == null) {
            return;
        }
        
        //1、根据数据源获取服务上路径所有报表
        InstantiationFormulateReport reportClass = new InstantiationFormulateReport();
        IReportOprationService reportOprationService = reportClass.getReportClass(datasource.getType());
        if (reportOprationService == null) {
            return;
        }
        reportOprationService.login(datasource);
//        String path = datasource.getPath();
//        String path = AppConfig.getInstance().getString(datasource.getType() + ReportConst.REPORT_SYN_PATH);
        List<ReportVO> listReportNodes = reportOprationService.getReportFromRemote(datasource);
        if (listReportNodes == null || listReportNodes.size() == 0) {
            return;
        }
        //2、数据库中查询已经同步过的报表
        Condition condition = new Condition();
        condition.addExpression("data_source", datasource.getId());
        List<ReportVO> lisLocalNodes = widgetReportDao.queryReportByDataSource(condition);
        List<String> listKeys = new ArrayList<String>();
        if (lisLocalNodes != null && lisLocalNodes.size() > 0) {
            Iterator<ReportVO> it = lisLocalNodes.iterator();
            while (it.hasNext()) {
                ReportVO vo = it.next();
                listKeys.add(vo.getReport_path());
            }
        }
        //3、找出数据库中没有的报表
        Iterator<ReportVO> it = listReportNodes.iterator();
        List<ReportVO> insertNodes = new ArrayList<ReportVO>();
        while (it.hasNext()) {
            ReportVO vo = it.next();
            //如果已经存在则刨除掉
            if (listKeys.size() <= 0 || !listKeys.contains(vo.getReport_path())) {
                vo.setData_source(datasource.getId());
                insertNodes.add(vo);
            }
            if (insertNodes.size() == 50) {
                //分批次插入数据库，批量插入数据库中
                widgetReportDao.insert(insertNodes);
                List<WidgetMetaVO> metaVOs = conversionWidgetMetaVOs(insertNodes, datasource.getType());
                if (metaVOs.size() > 0) {
                    metaService.insertBatchVO(metaVOs);
                }
                insertNodes.clear();
            }
        }
        if (insertNodes.size() > 0) {
            //处理最后部分
            widgetReportDao.insert(insertNodes);
            List<WidgetMetaVO> metaVOs = conversionWidgetMetaVOs(insertNodes, datasource.getType());
            if (metaVOs.size() > 0) {
                metaService.insertBatchVO(metaVOs);
            }
        }
    }

    /**
     * 
    * @Title: conversionWidgetMetaVOs
    * (转化为Porta组件元数据)
    * @param nodes
    * @return List<WidgetMetaVO>  
    *      */
    private List<WidgetMetaVO> conversionWidgetMetaVOs(List<ReportVO> nodes, String type) {
        List<WidgetMetaVO> listMeta = new ArrayList<WidgetMetaVO>();
        Iterator<ReportVO> it = nodes.iterator();
        while (it.hasNext()) {
            ReportVO node = it.next();
            if (!"Y".equals(node.getLeaf())) {
                continue;
            }
            WidgetMetaVO meta = new WidgetMetaVO();
            meta.setName(node.getReport_name());
            meta.setDirId(node.getParent_path());
            meta.setType(type);
            meta.setLoadUrl(ReportConst.LOAD_URL);
            meta.setVersion(0);
            //meta.setPreference("report_path",node.getReport_path());
            meta.setId(node.getId());
            listMeta.add(meta);
        }
        return listMeta;
    }
}
