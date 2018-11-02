package com.gdsp.ptbase.appcfg.impl;


import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gdsp.dev.core.common.AppConfig;
import com.gdsp.dev.core.model.query.Condition;
import com.gdsp.dev.persist.result.MapListResultHandler;
import com.gdsp.platform.config.customization.helper.SystemConfigConst;
import com.gdsp.platform.systools.datasource.service.IDataSourceService;
import com.gdsp.ptbase.appcfg.dao.IWidgetMetaDao;
import com.gdsp.ptbase.appcfg.helper.AppConfigConst;
import com.gdsp.ptbase.appcfg.model.WidgetMetaVO;
import com.gdsp.ptbase.appcfg.service.ICommonDirService;
import com.gdsp.ptbase.appcfg.service.IWidgetMetaService;

/**
 * 
* @ClassName: WidgetActionServiceImpl
* (组件动作管理)
* @author hongwei.xu
* @date 2015年7月22日 下午5:07:11
*
 */
@Service
@Transactional(readOnly = true)
public class WidgetMetaServiceImpl implements IWidgetMetaService {

    @Resource
    private IWidgetMetaDao    dao;
    @Resource
    private ICommonDirService dirService;
    @Resource
    private IDataSourceService dataSourceService;
    @Override
    @Transactional
    public WidgetMetaVO insert(WidgetMetaVO vo) {
        dao.insert(vo);
        return vo;
    }

    @Override
    @Transactional
    public WidgetMetaVO update(WidgetMetaVO vo) {
        dao.update(vo);
        return vo;
    }

    @Override
    @Transactional
    public void delete(String... ids) {
        dao.delete(ids);
    }

    @Override
    public WidgetMetaVO load(String id) {
        return dao.load(id);
    }

    @Override
    public Map<String, WidgetMetaVO> findWidgetMetasByPageId(String pageId) {
        return dao.findWidgetsByPageId(pageId);
    }

    @Override
    public List<WidgetMetaVO> findWidgetMetasByDirId(String dirId) {
        return dao.findWidgetMetasByDirId(dirId);
    }

    @Override
    public Map<String, List<Map>> findAllWidgetMetaInDirectory() {
        String reportIntsInfo = AppConfig.getProperty(SystemConfigConst.SYSTEMCONFIG_REPORTINTSINFO);
        String[] reportIntsStrings=null;
        if(StringUtils.isNotEmpty(reportIntsInfo)){
           reportIntsStrings = reportIntsInfo.split(",");
        }else{
           Map<String, List<Map>> result = dirService.findDirTreeByCategory(AppConfigConst.DIR_WIDGETMETA);
           if (result == null)
               return null;
           List<Map> list = result.get("__null_key__");
           for (int i = 0; i < list.size(); i++) {
               Map map = list.get(i);
               String reportType = (String) map.get("reportType");
               if (StringUtils.isNotBlank(reportType)) {
                   list.remove(i);
                   i--;
               }
           }
           MapListResultHandler<Map> handler = new MapListResultHandler<>("parent_id", result);
           dao.findAllWidgetMetaInDirectory(handler);
           return result;
        }
        Map<String, List<Map>> result = dirService.findDirTreeByCategory(AppConfigConst.DIR_WIDGETMETA);
        if (result == null)
            return null;
        List<Map> list = result.get("__null_key__");
        //根据启用的数据源类型查询数据源名称
        Map<String, List<Map>> datasource=dataSourceService.findDirTreeByCategory(reportIntsStrings);
        if(datasource.size()!=0){
            for (int i = 0; i < list.size(); i++) {
                Map map = list.get(i);
                String reportType = (String) map.get("reportType");
                if (StringUtils.isNotBlank(reportType)) {
                    list.remove(i);
                    i--;
                }
            }
            //把查询出的数据源名称在依次添加到list内
            for(int i=0;i<datasource.get("__null_key__").size();i++){
                list.add(datasource.get("__null_key__").get(i));
            }
            MapListResultHandler<Map> handler = new MapListResultHandler<>("parent_id", result);
            dao.findAllWidgetMetaInDirectory(handler);
            return result;
        }else{
            for (int i = 0; i < list.size(); i++) {
                Map map = list.get(i);
                String reportType = (String) map.get("reportType");
                if (StringUtils.isNotBlank(reportType)) {
                    list.remove(i);
                    i--;
                }
            }
            MapListResultHandler<Map> handler = new MapListResultHandler<>("parent_id", result);
            dao.findAllWidgetMetaInDirectory(handler);
            return result;
        }
        
    }

    @Override
    @Transactional
    public List<WidgetMetaVO> insertBatchVO(List<WidgetMetaVO> metaVOs) {
        dao.insertBatchVO(metaVOs);
        return metaVOs;
    }

    @Override
    public Page<WidgetMetaVO> findWidgetMetasByDirIdWithPage(String id,
            Pageable pageable) {
        if (StringUtils.isBlank(id)) {
            return null;
        }
        Condition condition = new Condition();
        condition.addExpression("dirid", id);
        return dao.findWidgetMetasByDirIdWithPage(condition, pageable);
    }

    /* (非 Javadoc)
    * <p>Title: findWidgetMetaPageByKpiDetailId</p>
    * <p>Description: </p>
    * @param kpiDetailId
    * @return
    * @see com.gdsp.ptbase.appcfg.service.IWidgetMetaService#findWidgetMetaPageByKpiDetailId(java.lang.String)
    */
    @Override
    public Page<WidgetMetaVO> findWidgetMetaPageByKpiDetailId(String kpiDetailId, Pageable pageable) {
        Page<WidgetMetaVO> page = dao.findWidgetMetaPageByKpiDetailId(kpiDetailId, pageable);
        return page;
    }

    @Override
    public boolean synchroCheck(WidgetMetaVO widgetMetaVO) {
        return dao.existSameName(widgetMetaVO) == 0;
    }
}
