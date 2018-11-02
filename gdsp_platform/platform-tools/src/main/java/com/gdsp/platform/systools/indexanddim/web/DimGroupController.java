/*
 * 类名: DimGroupController
 * 创建人: wly    
 * 创建时间: 2016年2月2日
 */
package com.gdsp.platform.systools.indexanddim.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gdsp.dev.base.exceptions.BusinessException;
import com.gdsp.dev.core.model.param.PageSerRequest;
import com.gdsp.dev.core.model.query.Condition;
import com.gdsp.dev.web.mvc.ext.ViewWrapper;
import com.gdsp.dev.web.mvc.model.AjaxResult;
import com.gdsp.platform.systools.indexanddim.model.DimGroupRltVO;
import com.gdsp.platform.systools.indexanddim.model.DimGroupVO;
import com.gdsp.platform.systools.indexanddim.service.IDimGroupService;

/**
 * 维度分组管理控制类 <br/>
 * 
 * @author wly
 */
@Controller
@RequestMapping("indexanddim/dimgroup")
public class DimGroupController {

    @Autowired
    private IDimGroupService dimgroupService;

    /**
     * 
     * 查询维度分组信息 <br/>
     * 
     * @param model
     * @return
     */
    @RequestMapping("/list.d")
    public String list(Model model, Condition condition) {
        List<DimGroupVO> dimgroups = dimgroupService.queryDimGroupByCondition(condition);
        model.addAttribute("nodes", dimgroups);
        return "indexanddim/dimgroup/list";
    }

    /**
     * 
     *  查询维度树信息<br/>
     * 
     * @param model
     * @param condition
     * @return
     */
    @RequestMapping("/listDim.d")
    @ViewWrapper(wrapped = false)
    public String listDim(Model model, Condition condition) {
        List<DimGroupVO> dimgroups = dimgroupService.queryDimGroupByCondition(condition);
        model.addAttribute("nodes", dimgroups);
        return "indexanddim/dimgroup/dimgroup-tree";
    }

    /**
     * 
     *  添加维度分组页面<br/>
     * 
     * @return
     */
    @RequestMapping("/addDimGroup.d")
    @ViewWrapper(wrapped = false, onlyAjax = true)
    public String addDimGroup(Model model, DimGroupVO dimgroup) {
        model.addAttribute("dimgroupVO", dimgroup);
        model.addAttribute("editType", "add");
        return "indexanddim/dimgroup/dimgroup-add";
    }

    /**
     * 
     * 保存维度组信息 <br/>
     * 
     * @param dimgroup
     * @return
     */
    @RequestMapping("/saveDimGroup.d")
    @ResponseBody
    public Object saveDimGroup(DimGroupVO dimgroup) {
        if (!dimgroupService.uniqueNameCheck(dimgroup)) {
            return new AjaxResult(AjaxResult.STATUS_ERROR, "同一维度组的下级维度组名称不能重复，请确认！", null, false);
        }
        dimgroupService.saveDimGroup(dimgroup);
        return AjaxResult.SUCCESS;
    }

    /**
     * 查询上级维度组<br/>
     * @param model
     * @return
     */
    @RequestMapping("/queryParentGroup.d")
    @ViewWrapper(wrapped = false)
    public Object queryParentGroup(Model model, Condition condition) {
        List<DimGroupVO> dimgroups = dimgroupService.queryDimGroupByCondition(condition);
        model.addAttribute("nodes", dimgroups);
        return "indexanddim/dimgroup/parentgroup";
    }

    /**
     * 唯一性校验<br/>
     * @param dim
     * @return
     */
    @RequestMapping("/uniqueCheck.d")
    @ResponseBody
    public Object uniqueCheck(DimGroupVO dim) {
        return dimgroupService.uniqueCheck(dim);
    }

    /**
     * 修改维度分组 <br/>
     * @param model
     * @param id
     * @return
     */
    @RequestMapping("/editDimGroup.d")
    @ViewWrapper(wrapped = false, onlyAjax = true)
    public String editDimGroup(Model model, String id) {
        DimGroupVO dimGroupVO = dimgroupService.load(id);
        model.addAttribute("dimgroupVO", dimGroupVO);
        model.addAttribute("editType", "edit");
        return "indexanddim/dimgroup/dimgroup-add";
    }

    /**
     * 删除维度分组 <br/>
     * @param id
     * @return
     */
    @RequestMapping("/deleteDimGroup.d")
    @ResponseBody
    public Object deleteGroup(String id) {
        if (StringUtils.isEmpty(id)) {
            return new AjaxResult(AjaxResult.STATUS_ERROR, "删除失败，ID为空！", null, false);
        }
        Condition cond = new Condition();
        List<DimGroupVO> dimGroupList = dimgroupService.queryChildDimGroupListById(id, cond, null, false);
        if (dimGroupList != null && dimGroupList.size() > 0) {
            return new AjaxResult(AjaxResult.STATUS_ERROR, "当前分组存在下级，请先删除下级!", null, false);
        }
        cond = new Condition();
        List<DimGroupRltVO> dimList = dimgroupService.queryDimByGroupId(id, cond, new PageSerRequest()).getContent();
        if (dimList != null && dimList.size() > 0) {
            return new AjaxResult(AjaxResult.STATUS_ERROR, "当前分组存在维度，请先删除维度!", null, false);
        }
        dimgroupService.deleteDimGroup(id);
        return AjaxResult.SUCCESS;
    }

    /**
     * 查询维度组可关联的维度列表<br/>
     * @param model
     * @param areaId
     * @param pageable
     * @return
     */
    @RequestMapping("/addDimList.d")
    @ViewWrapper(wrapped = false)
    public String addDimList(Model model, String dimGroupId, Pageable pageable) {
        model.addAttribute("dimGroupId", dimGroupId);
        model.addAttribute("dims", dimgroupService.queryDimForDimGroupPower(dimGroupId, pageable));
        return "indexanddim/pub/dimlist-data";
    }

    /**
     * 添加维度页面<br/>
     * @param model
     * @param dimGroupId
     * @param pageable
     * @return
     */
    @RequestMapping("/addDim.d")
    @ViewWrapper(wrapped = false)
    public String addDim(Model model, String dimGroupId, Pageable pageable) {
        model.addAttribute("dimGroupId", dimGroupId);
        model.addAttribute("dims", dimgroupService.queryDimForDimGroupPower(dimGroupId, pageable));
        return "indexanddim/dimgroup/dim-add";
    }

    /**
     * 查询分组对应的维度 <br/>
     * @param model
     * @param dimGroupId
     * @param condition
     * @param pageable
     * @return
     */
    @RequestMapping("/reloadDim.d")
    @ViewWrapper(wrapped = false)
    public String reloadDim(Model model, String dimGroupId, String innercode, Condition condition, Pageable pageable) {
        if (StringUtils.isEmpty(dimGroupId))
            throw new BusinessException("请选择待分配维度组！");
        model.addAttribute("dimGroupId", dimGroupId);
        model.addAttribute("groupDims", dimgroupService.queryDimByDimGroupId(innercode, condition, pageable));
        return "indexanddim/dimgroup/dimlist-data";
    }

    /**
     * 保存维度到维度组<br/>
     * @param dimGroupId  维度组id
     * @param id 维度id
     * @return
     */
    @RequestMapping("/saveDimOnGroup.d")
    @ResponseBody
    public Object saveDimOnGroup(String dimGroupId, String... id) {
        dimgroupService.addDimOnDimgroup(dimGroupId, id);
        return AjaxResult.SUCCESS;
    }

    /**
     * 删除维度组关联的维度 <br/>
     * @param id (row id)
     * @return
     */
    @RequestMapping("/deleteDimInDimGroup.d")
    @ResponseBody
    public Object deleteDimInDimGroup(String... id) {
        if (id != null && id.length > 0) {
            dimgroupService.deleteDim(id);
        }
        return AjaxResult.SUCCESS;
    }
}
