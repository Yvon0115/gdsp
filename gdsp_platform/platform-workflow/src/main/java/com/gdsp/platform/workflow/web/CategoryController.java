package com.gdsp.platform.workflow.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gdsp.dev.base.exceptions.BusinessException;
import com.gdsp.dev.base.utils.JsonUtils;
import com.gdsp.dev.core.model.param.Sorter;
import com.gdsp.dev.core.model.query.Condition;
import com.gdsp.dev.web.mvc.ext.ViewWrapper;
import com.gdsp.dev.web.mvc.model.AjaxResult;
import com.gdsp.platform.workflow.model.CategoryVO;
import com.gdsp.platform.workflow.service.ICategoryService;

/**
 * 流程类别Controller
 * @author sun
 *
 */
@Controller
@RequestMapping("workflow/category")
public class CategoryController {

    private static final Logger logger = LoggerFactory.getLogger(CategoryController.class);
    @Autowired
    private ICategoryService categoryService;

    @RequestMapping("list.d")
    @ViewWrapper(wrapped = false)
    public String list(Model model, Condition condition, Pageable pageable) {
        Sorter sort = new Sorter(Direction.ASC, "categoryname");
        model.addAttribute("categorys", categoryService.queryCategoryByCondition(condition, pageable, sort));
        return "category/list";
    }

    @RequestMapping("listData.d")
    @ViewWrapper(wrapped = false)
    public String listData(Model model, Condition condition, Pageable pageable) {
        Sorter sort = new Sorter(Direction.ASC, "categoryname");
        model.addAttribute("categorys", categoryService.queryCategoryByCondition(condition, pageable, sort));
        return "category/list-data";
    }
    
    /** easytree控件获取构建树的数据*/
    @RequestMapping("/getTreeData.d")
    @ResponseBody
    public Object getTreeData(Condition condition, Sorter sort){
        List<CategoryVO> categoryListByCond = categoryService.queryCategoryListByCondition(condition, sort);
        Map<String, String> nodeAttr = new HashMap<String, String>();
        nodeAttr.put("text", "categoryName");
        if (categoryListByCond.size() != 0) {
            return JsonUtils.formatList2TreeViewJson(categoryListByCond, nodeAttr);
        } else {
            return "";
        }
    }

    @RequestMapping("add.d")
    @ViewWrapper(wrapped = false)
    public String add(Model model, Condition condition, Pageable pageable) {
        model.addAttribute("editType", "add");
        return "category/form";
    }

    @RequestMapping("/save.d")
    @ResponseBody
    public Object save(CategoryVO category) {
        categoryService.saveCategory(category);
        return AjaxResult.SUCCESS;
    }

    @RequestMapping("/edit.d")
    @ViewWrapper(wrapped = false, onlyAjax = true)
    public String edit(Model model, String id) {
        model.addAttribute("editType", "edit");
        model.addAttribute("category", categoryService.findCategoryById(id));
        return "category/form";
    }

    @RequestMapping("/delete.d")
    @ResponseBody
    public Object delete(String... id) {
        if (id != null && id.length > 0) {
            try {
                categoryService.deleteCategory(id);
            } catch (BusinessException e) {
                logger.error(e.getMessage(),e);
                return new AjaxResult(AjaxResult.STATUS_ERROR,e.getMessage(),null,false);
            }
        }
        return AjaxResult.SUCCESS;
    }
    
    @RequestMapping("uniqueCodeCheck")
    @ResponseBody
    public Object uniqueCodeCheck(String id,String categoryCode){
    	boolean flag=categoryService.checkCode(id,categoryCode);
    	return flag;
    }
    @RequestMapping("uniqueNameCheck")
    @ResponseBody
    public Object uniqueNameCheck(String id,String categoryName){
    	boolean flag=categoryService.checkName(id,categoryName);
    	return flag;
    }
}
