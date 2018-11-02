package com.gdsp.platform.systools.datalicense.web;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gdsp.dev.core.common.AppConfig;
import com.gdsp.dev.core.model.query.Condition;
import com.gdsp.dev.web.mvc.ext.ViewWrapper;
import com.gdsp.dev.web.mvc.model.AjaxResult;
import com.gdsp.platform.config.customization.helper.SystemConfigConst;
import com.gdsp.platform.systools.datadic.service.IDataDicService;
import com.gdsp.platform.systools.datalicense.model.DataDicPowerVO;
import com.gdsp.platform.systools.datalicense.service.IDataDicPowerService;
import com.gdsp.platform.systools.datasource.model.DataSourceVO;
import com.gdsp.platform.systools.datasource.service.IDataSourceService;

@Controller
@RequestMapping("datalicense/powerdic")
public class DateDicPowerController {

    @Autowired
    private IDataDicPowerService dataDicPowerService;
    @Autowired
    private IDataSourceService   dataSourceService;
    @Autowired
    private IDataDicService      dataDicService;

    /**
     * 默认加载页面
     * @param model
     * @return
     */
    @RequestMapping("/list.d")
    public String list(Model model) {
        String[] reportType = this.getDataSourceType();
        List<DataSourceVO> dataSourceList = null;
        if(reportType.length > 0){
            dataSourceList = dataSourceService.queryEnableDataSource(reportType);
        } else {
            dataSourceList = null;
        }
        model.addAttribute("dataSourceList", dataSourceList);
        return "datalicense/powerdic/list";
    }

    /**
     * 刷新页面
     * @param model
     * @param dataSourceID
     * @return
     */
    @RequestMapping("/listData.d")
    @ViewWrapper(wrapped = false)
    public String listData(Model model, String dataSourceId, Condition condition, Pageable page) {
    	Page<DataDicPowerVO> nullData = null;
    	if(dataSourceId == null){
    		model.addAttribute("dataSource", nullData);
    	}else{
    		Page<DataDicPowerVO> dataSourceDicVO = dataDicPowerService.queryDataDicByDataSourceId(dataSourceId, condition, page);
            model.addAttribute("dataSource", dataSourceDicVO);
    	}
    	 return "datalicense/powerdic/list-data";
    }

    /**
     * 添加数据源数据字典关联关系
     * @param model
     * @param dataSourceId
     * @param page
     * @return 
     */
    @RequestMapping("/addData.d")
    @ViewWrapper(wrapped = false)
    public String addData(Model model, String dataSourceId, Condition condition, Pageable page) {
        List<DataDicPowerVO> voList = dataDicPowerService.queryDataDicIds(dataSourceId);
        model.addAttribute("dataSourceId", dataSourceId);
        model.addAttribute("dataDicVO", dataDicService.queryDataDicExtDicIds(voList, condition, page));
        return "datalicense/powerdic/dataDicPower-add";
    }
    
    /**
     * 重新加载添加维度列表
     * @param model
     * @param dataSourceId
     * @param condition
     * @param page
     * @return
     */
    @RequestMapping("/reloadData.d")
    @ViewWrapper(wrapped = false, onlyAjax = true)
    public String reloadData(Model model, String dataSourceId, Condition condition, Pageable page) {
        List<DataDicPowerVO> voList = dataDicPowerService.queryDataDicIds(dataSourceId);
        model.addAttribute("dataDicVO", dataDicService.queryDataDicExtDicIds(voList, condition, page));
        return "datalicense/powerdic/reload-data";
    }

    /**
     * 保存数据源数据字典关联关系
     * @param dataSourceId
     * @param id
     * @return
     */
    @RequestMapping("/saveData.d")
    @ResponseBody
    private Object saveData(String dataSourceId, String[] id) {
        dataDicPowerService.saveDataSourceDataDicRel(dataSourceId, id);
        return AjaxResult.SUCCESS;
    }

    /**
     * 删除数据源数据字典关联关系
     * @param id
     * @return
     */
    @RequestMapping("/deleteData.d")
    @ResponseBody
    private Object deleteData(String[] id) {
        if (dataDicPowerService.deleteDataSourceDicRel(id)) {
            return new AjaxResult(AjaxResult.STATUS_SUCCESS, "删除成功！");
        } else {
            return new AjaxResult(AjaxResult.STATUS_ERROR, "删除失败，请检查选中数据是否已被授权！");
        }
    }

    /**
     * 调用启用的的数据源列表
     * @return 启用的报表类型
     */
    private String[] getDataSourceType() {
        String reportIntsInfo = AppConfig.getProperty(SystemConfigConst.SYSTEMCONFIG_REPORTINTSINFO);
        if (!StringUtils.isEmpty(reportIntsInfo)) {
            reportIntsInfo = reportIntsInfo.substring(0, reportIntsInfo.lastIndexOf(","));
            String[] reportIntsStrings = reportIntsInfo.split(",");
            return reportIntsStrings;
        }
        return new String[] {};
    }
}
