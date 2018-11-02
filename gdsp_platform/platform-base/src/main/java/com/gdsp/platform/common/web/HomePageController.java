/*
 * 类名: homePageController
 * 创建人: wly   
 * 创建时间: 2016年5月10日
 */
package com.gdsp.platform.common.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gdsp.dev.web.mvc.ext.ViewDecorate;

/**
 * 系统主页控制器
 * @author Administrator
 */
@Controller
@RequestMapping("home")
public class HomePageController {

    @RequestMapping("/homePage.d")
    @ViewDecorate("decorate/castle-main")
    public String homePage(Model model) {
        return "common/login/homepage";
    }
}
