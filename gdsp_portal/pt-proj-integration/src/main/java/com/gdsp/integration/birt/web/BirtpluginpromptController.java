
package com.gdsp.integration.birt.web;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("birt/birtplugin")
public class BirtpluginpromptController {
    
 
    @RequestMapping("/prompt.d")
    public String powerMenu(Model model)
    {
        return "/portlet/birt/birtpluginprompt";
    }
}
