package com.gdsp.platform.func.web;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gdsp.dev.core.model.query.Condition;
import com.gdsp.dev.web.mvc.ext.ViewWrapper;
import com.gdsp.dev.web.mvc.model.AjaxResult;
import com.gdsp.platform.func.model.MenuRegisterVO;
import com.gdsp.platform.func.model.ResourceRegisterVO;
import com.gdsp.platform.func.service.IMenuRegisterService;
import com.gdsp.platform.func.service.IResourceRegisterService;

/**
* @ClassName: ResourceRegisterController
* (菜单资源维护)
* @author shiyingjie
* @date 2015年11月17日 上午10:32:34
*
 */
@Controller
@RequestMapping("func/resource")
public class ResourceRegisterController {

    @Autowired
    IResourceRegisterService resourceService;
    @Autowired
    IMenuRegisterService     menuService;

    @RequestMapping("/list.d")
    @ViewWrapper(wrapped = false)
    public String list(Model model, Pageable pageable, String menuID, Condition condition) {
        condition.addExpression("parentid", menuID);
        model.addAttribute("resource", resourceService.getResourceByMumeid(condition, pageable));
        model.addAttribute("muneID", menuID);
        return "func/resregister/list";
    }

    @RequestMapping("/listData.d")
    @ViewWrapper(wrapped = false)
    public String listData(Model model, Condition condition, String muneID, Pageable pageable) {
        condition.addExpression("parentid", muneID);
        model.addAttribute("resource", resourceService.getResourceByMumeid(condition, pageable));
        model.addAttribute("muneID", muneID);
        return "func/resregister/list-data";
    }

    @RequestMapping("/add.d")
    @ViewWrapper(wrapped = false)
    public String add(Model model, String muneID) {
        MenuRegisterVO vo = menuService.loadMenuRegisterVOById(muneID);
        int type;
        if (vo.getFuntype() == 4) {
            type = 1;
        } else {
            type = 0;
        }
        model.addAttribute("parenttype", type);
        model.addAttribute("muneID", muneID);
        model.addAttribute("edittype", "add");
        return "func/resregister/form";
    }

    @RequestMapping("/edit.d")
    @ViewWrapper(wrapped = false)
    public String edit(Model model, String id) {
        model.addAttribute("edittype", "edit");
        model.addAttribute("resource", resourceService.getResourceByID(id));
        return "func/resregister/form";
    }

    @RequestMapping("/save.d")
    @ResponseBody
    public Object save(ResourceRegisterVO vo) {
        if (StringUtils.isNotEmpty(vo.getId())) {
            resourceService.updateResRegister(vo);
        } else {
            resourceService.insertResRegister(vo);
        }
        return AjaxResult.SUCCESS;
    }

    @RequestMapping("/delete.d")
    @ResponseBody
    public Object delete(String... id) {
        if (id != null && id.length > 0) {
            resourceService.delete(id);
        }
        return AjaxResult.SUCCESS;
    }

    @RequestMapping("/synchroCheck.d")
    @ResponseBody
    public Object synchroCheck(ResourceRegisterVO vo) {
        return resourceService.synchroCheck(vo);
    }
}
