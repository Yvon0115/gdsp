package com.gdsp.platform.workflow.web;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gdsp.dev.core.model.param.Sorter;
import com.gdsp.dev.core.model.query.Condition;
import com.gdsp.dev.web.mvc.ext.ViewWrapper;
import com.gdsp.dev.web.mvc.model.AjaxResult;
import com.gdsp.platform.workflow.model.DeploymentVO;
import com.gdsp.platform.workflow.model.FormTypeVO;
import com.gdsp.platform.workflow.model.FormVariableVO;
import com.gdsp.platform.workflow.service.IDeploymentService;
import com.gdsp.platform.workflow.service.IFormTypeService;
import com.gdsp.platform.workflow.service.IFormVariableService;

/**
 * 单据注册Controller
 * @author MYZhao
 *
 */
@Controller
@RequestMapping("workflow/formdef")
public class FormDefController {

    @Autowired
    private IFormTypeService     formTypeService;
    @Autowired
    private IFormVariableService formVariableService;
    @Autowired
    private IDeploymentService deploymentService;

    @RequestMapping("/list.d")
    public String alertDefList(Model model, Condition condition, Pageable pageable) {
//        Sorter sort = new Sorter(Direction.ASC, "createTime");
//        Page<FormTypeVO> formTypeVOList = formTypeService.queryFormTypePageByCondition(condition, sort, pageable);
//        model.addAttribute("formDefs", formTypeVOList);
        return "formDef/list";
    }

    @RequestMapping("/listData.d")
    @ViewWrapper(wrapped = false)
    public String listData(Model model, Condition condition, Pageable pageable, String categoryCode) {
        Sorter sort = new Sorter(Direction.ASC, "createTime");
        if (StringUtils.isNotEmpty(categoryCode)) {
            condition.addExpression("c.id", categoryCode);
        }
        Page<FormTypeVO> formTypeVOList = formTypeService.queryFormTypePageByCondition(condition, sort, pageable);
        model.addAttribute("formDefs", formTypeVOList);
        return "formDef/list-data";
    }

    @RequestMapping("/add.d")
    @ViewWrapper(wrapped = false, onlyAjax = true)
    public String add(Model model, String pk_categoryid) {
        model.addAttribute("editType", "add");
        List<FormVariableVO> parameters = new ArrayList<FormVariableVO>();
        FormVariableVO para = new FormVariableVO();
        parameters.add(para);
        model.addAttribute("pk_categoryid", pk_categoryid);
        model.addAttribute("parameters", parameters);
        return "formDef/form";
    }

    @RequestMapping("/save.d")
    @ResponseBody
    public Object save(FormTypeVO formTypeVO) {
        formTypeService.saveFormType(formTypeVO);
        return AjaxResult.SUCCESS;
    }
    
    @RequestMapping("delete.d")
    @ResponseBody
    public Object delete(String... id) {
        if (id != null && id.length > 0) {
        	Condition cd=new Condition();
        	cd.addExpression("formtypeid", id, "in");
        	List<DeploymentVO> deploymentVOs = deploymentService.queryDeploymentListByCondition(cd, null);
        	if(!deploymentVOs.isEmpty()){
    			return  new AjaxResult("该单据存在流程定义中,请先删除对应流程定义!"); 
    		}
        	formTypeService.deleteFormType(id);
        }
        return AjaxResult.SUCCESS;
    }
    
    
    @RequestMapping("/edit.d")
    @ViewWrapper(wrapped = false, onlyAjax = true)
    public String edit(Model model, String id) {
        model.addAttribute("editType", "edit");
        FormTypeVO formTypeVO = formTypeService.findTypeAndVariableById(id);
        model.addAttribute("formDefs", formTypeVO);
        List<FormVariableVO> parameters = formTypeVO.getParameters();
        // 参数为空时增加一个空参数
        if (parameters.size() < 1) {
            FormVariableVO para = new FormVariableVO();
            parameters.add(para);
        }
        model.addAttribute("parameters", parameters);
        return "formDef/form";
    }
    
    
    @RequestMapping("uniqueCodeCheck")
    @ResponseBody
    public Object uniqueCodeCheck(String formCode){
    	boolean flag=formTypeService.checkCode(formCode);
    	return flag;
    }
    @RequestMapping("uniqueNameCheck")
    @ResponseBody
    public Object uniqueNameCheck(String id,String formName){
    	boolean flag=formTypeService.checkName(id,formName);
    	return flag;
    }
}
