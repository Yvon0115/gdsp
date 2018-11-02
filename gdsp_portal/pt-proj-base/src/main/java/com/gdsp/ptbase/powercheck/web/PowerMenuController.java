package com.gdsp.ptbase.powercheck.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/powerMenu")
public class PowerMenuController {


    @RequestMapping("/list.d")
    public String powerMenu(Model model)
    {
        return "/powerMenu/list";
    }
}
