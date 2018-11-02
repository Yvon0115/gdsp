package com.gdsp.platform.demo.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gdsp.dev.web.mvc.model.AjaxResult;

/**
 * Web层控件 
 * @author xiangguo
 *
 */
@Controller
@RequestMapping("plugins/date")
public class DatePickerController {

    /**
     * 日期控件
     * @param model
     */
    @RequestMapping("/date.d")
    public String date(Model model) {
        return "demo/date/fre_object";
    }

    @RequestMapping("/ajaxShow.d")
    public String ajaxShow(Model model) {
        return "demo/date/runqianResTree";
    }

    @RequestMapping("/ajaxPost.d")
    @ResponseBody
    public Object ajaxPost(Model model, String reportPaths) {
        return AjaxResult.SUCCESS;
    }
    
}
