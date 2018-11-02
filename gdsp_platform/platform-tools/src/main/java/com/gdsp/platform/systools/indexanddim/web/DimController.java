/*
 * 类名: DimController
 * 创建人: wly    
 * 创建时间: 2016年2月22日
 */
package com.gdsp.platform.systools.indexanddim.web;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gdsp.dev.core.model.query.Condition;
import com.gdsp.dev.web.mvc.ext.ViewWrapper;
import com.gdsp.dev.web.mvc.model.AjaxResult;
import com.gdsp.platform.config.global.service.IDefDocService;
import com.gdsp.platform.systools.indexanddim.model.DimVO;
import com.gdsp.platform.systools.indexanddim.model.DimValueVO;
import com.gdsp.platform.systools.indexanddim.service.IDimService;
import com.gdsp.platform.systools.indexanddim.service.IIdxDimRelService;

/**
 * 维度管理控制类 <br/>
 * @author wly
 */
@Controller
@RequestMapping("indexanddim/dim")
public class DimController {

    @Autowired
    private IDimService       dimService;
    @Resource
    private IDefDocService    defDocService;
    @Autowired
    private IIdxDimRelService idxDimRelService;

    /**
     * 查询维度信息 <br/>
     * @param model
     * @param condition
     * @return
     */
    @RequestMapping("/list.d")
    public String list(Model model, Condition condition, Pageable pageable) {
        model.addAttribute("dims", dimService.queryDimByCondition(condition, pageable));
        return "indexanddim/dim/list";
    }

    /**
     * 查询维度列表<br/>
     * @param model
     * @param condition
     * @param pageable
     * @return
     */
    @RequestMapping("/listData.d")
    @ViewWrapper(wrapped = false)
    public String listData(Model model, Condition condition, Pageable pageable) {
        model.addAttribute("dims", dimService.queryDimByCondition(condition, pageable));
        return "indexanddim/dim/list-data";
    }

    /**
     * 添加维度 <br/>
     * @param model
     * @param dimVO
     * @return
     */
    @RequestMapping("/addDim.d")
    @ViewWrapper(wrapped = false)
    public String addDim(Model model, DimVO dimVO) {
        model.addAttribute("dimVO", dimVO);
        model.addAttribute("editType", "add");
        return "indexanddim/dim/dim-add";
    }

    /**
     * 保存维度信息 <br/>
     * @param dimVO
     * @return
     */
    @RequestMapping("/saveDim.d")
    @ResponseBody
    public Object saveDim(DimVO dimVO) {
        dimService.saveDim(dimVO);
        return AjaxResult.SUCCESS;
    }

    /**
     * 唯一性校验 维度名称 <br/>
     * @param vo
     * @return
     */
    @RequestMapping("/uniqueCheckName.d")
    @ResponseBody
    public Object uniqueCheckName(DimVO vo) {
        return dimService.uniqueCheckName(vo);
    }

    /**
     * 
     * 唯一性校验 维度字段名称 <br/>
     * 
     * @param vo
     * @return
     */
    @RequestMapping("/uniqueCheckField.d")
    @ResponseBody
    public Object uniqueCheckField(DimVO vo) {
        return dimService.uniqueCheckField(vo);
    }

    /**
     * 
     * 修改维度组 <br/>
     * 
     * @param model
     * @param id
     * @return
     */
    @RequestMapping("/editDim.d")
    @ViewWrapper(wrapped = false)
    public String editDim(Model model, String id) {
        model.addAttribute("dimVO", dimService.load(id));
        model.addAttribute("editType", "edit");
        return "indexanddim/dim/dim-add";
    }

    /**
     * 
     * 删除维度 <br/>
     * 
     * @param id 维度id
     * @return
     */
    @RequestMapping("/deleteDim.d")
    @ResponseBody
    public Object deleteDim(String... id) {
        if (id != null && id.length > 0) {
            int dimCite = idxDimRelService.isDimCite(id);
            if (dimCite != 0 && dimCite > 0) {
                return new AjaxResult(AjaxResult.STATUS_ERROR, "当前维度被指标引用，先请删除指标维度关联关系!", null, false);
            } else {
                dimService.deleteDim(id);
            }
        }
        return AjaxResult.SUCCESS;
    }

    /**
     * 
     *  授权（维度关联维值）<br/>
     * 
     * @param model
     * @param dimId
     * @param pageable
     * @param condition
     * @return
     */
    @RequestMapping("/dimValueList.d")
    @ViewWrapper(wrapped = false)
    public String dimValueList(Model model, String dimId, Pageable pageable, Condition condition) {
        model.addAttribute("dimId", dimId);
        model.addAttribute("dims", dimService.queryDimValueByDimId(dimId, condition, pageable));
        return "indexanddim/dim/dimvalue-list";
    }

    /**
     * 
     * 查询维值树页面 <br/>
     * 
     * @param model
     * @param dimId
     * @param pageable
     * @param condition
     * @return
     */
    @RequestMapping("/dimValueTree.d")
    @ViewWrapper(wrapped = false)
    public String dimValueTree(Model model, String dimId) {
        Map queryDimValue = dimService.queryDimValue(dimId);
        model.addAttribute("dimId", dimId);
        model.addAttribute("dimvalueMap", queryDimValue);
        return "indexanddim/dim/dimvalue";
    }

    /**
     * 
     * 查询维值data <br/>
     * 
     * @param model
     * @param dimId
     * @return
     */
    @RequestMapping("/dimValueTreeData.d")
    public String dimValueTreeData(Model model, String dimId) {
        Map queryDimValue = dimService.queryDimValue(dimId);
        model.addAttribute("dimvalueMap", queryDimValue);
        return "indexanddim/dim/dimvalue-tree";
    }

    /**
     * 
     * 维度关联的维值列表   目前系统无该功能<br/>
     * 
     * @param model
     * @param dimId
     * @param pageable
     * @param condition
     * @return
     */
    @RequestMapping("/dimValueListData.d")
    @ViewWrapper(wrapped = false)
    public String dimValueListData(Model model, String dimId, Pageable pageable, Condition condition) {
        model.addAttribute("dimId", dimId);
        model.addAttribute("dims", dimService.queryDimValueByDimId(dimId, condition, pageable));
        return "indexanddim/dim/dimvaluelist-data";
    }

    /**
     * 
     * 添加维值    目前系统无该功能 <br/>
     * 
     * @param model
     * @param dimValueVO
     * @return
     */
    @RequestMapping("/addDimValue.d")
    @ViewWrapper(wrapped = false)
    public String addDimValue(Model model, String dimId, DimValueVO dimValueVO) {
        model.addAttribute("dimId", dimId);
        model.addAttribute("dimValueVO", dimValueVO);
        model.addAttribute("editType", "add");
        return "indexanddim/dim/dimvalue-add";
    }

    /**
     * 
     * 保存维值信息    目前系统无该功能<br/>
     * 
     * @param dimValueVO
     * @return
     */
    @RequestMapping("/saveDimValue.d")
    @ResponseBody
    public Object saveDimValue(DimValueVO dimValueVO, String dimId) {
        dimService.saveDimValue(dimValueVO, dimId);
        return AjaxResult.SUCCESS;
    }

    /**
     * 
     * 修改维值    目前系统无该功能<br/>
     * 
     * @param model
     * @param id
     * @return
     */
    @RequestMapping("/editDimValue.d")
    @ViewWrapper(wrapped = false)
    public String editDimValue(Model model, String id, String dimId) {
        DimValueVO dimValueVO = dimService.loadDimValue(id);
        model.addAttribute("dimId", dimId);
        model.addAttribute("dimValueVO", dimValueVO);
        model.addAttribute("editType", "edit");
        return "indexanddim/dim/dimvalue-add";
    }

    /**
     * 
     * 删除维值信息    目前系统无该功能<br/>
     * 
     * @param id 维度和维值关联的id
     * @return
     */
    @RequestMapping("/deleteDimValue.d")
    @ResponseBody
    @ViewWrapper(wrapped = false)
    public Object deleteDimValue(String... id) {
        if (id != null && id.length > 0) {
            dimService.deleteDimValue(id);
        }
        return AjaxResult.SUCCESS;
    }
}
