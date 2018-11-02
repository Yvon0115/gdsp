package com.gdsp.platform.func.web;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
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
import com.gdsp.platform.func.model.ButnRegisterVO;
import com.gdsp.platform.func.service.IButnRegisterService;

/***
 * 
 * 菜单按钮维护
 * @author shiyingjie
 *
 */
@Controller
@RequestMapping("func/butn")
public class ButnRegisterController {

    @Autowired
    IButnRegisterService butnRegisterService;

    @RequestMapping("/list.d")
    @ViewWrapper(wrapped = false)
    public String list(Model model, Pageable pageable, String menuID, Condition condition) {
        Sorter sort = new Sorter(Direction.ASC, "funcode");
        condition.addExpression("parentid", menuID);
        Page<ButnRegisterVO> page = butnRegisterService.queryBtnRegister(condition, sort, pageable);
        model.addAttribute("butnRegisterPages", page);
        model.addAttribute("menuID", menuID);
        return "func/menuregister/butnlist";
    }

    @RequestMapping("/butnlistData.d")
    @ViewWrapper(wrapped = false)
    public String listData(Model model, Condition condition, String menuID, Pageable pageable) {
        Sorter sort = new Sorter(Direction.ASC, "funcode");
        condition.addExpression("parentid", menuID);
        Page<ButnRegisterVO> page = butnRegisterService.queryBtnRegister(condition, sort, pageable);
        model.addAttribute("butnRegisterPages", page);
        model.addAttribute("menuID", menuID);
        return "func/menuregister/butnlist-data";
    }

    @RequestMapping("/add.d")
    @ViewWrapper(wrapped = false, onlyAjax = true)
    public String add(Model model, String menuID) {
        model.addAttribute("menuID", menuID);
        return "func/menuregister/butnform";
    }

    @RequestMapping("/edit.d")
    @ViewWrapper(wrapped = false, onlyAjax = true)
    public String edit(Model model, String id) {
        model.addAttribute("butnRegisterPages", butnRegisterService.loadButnRegisterVOById(id));
        return "func/menuregister/butnform";
    }

    @RequestMapping("/save.d")
    @ResponseBody
    public Object save(ButnRegisterVO butnRegisterPages) {
        if (StringUtils.isNotEmpty(butnRegisterPages.getId())) {
            butnRegisterService.updateButnRegister(butnRegisterPages);
        } else {
            List<ButnRegisterVO> vo = butnRegisterService.queryButnByFunCode(butnRegisterPages.getFuncode());
            if (vo != null && vo.size() > 0)
                return new AjaxResult(AjaxResult.STATUS_ERROR, "按钮编码已存在,请重新输入!", null, false);
            butnRegisterService.insertButnRegister(butnRegisterPages);
        }
        return AjaxResult.SUCCESS;
    }

    @RequestMapping("/delete.d")
    @ResponseBody
    public Object delete(String... id) {
        butnRegisterService.deleteButnRegister(id);
        return AjaxResult.SUCCESS;
    }
}
