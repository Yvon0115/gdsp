/**
 * 
 */
package com.gdsp.platform.demo.web;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gdsp.dev.base.utils.JsonUtils;
import com.gdsp.dev.core.model.query.Condition;
import com.gdsp.platform.config.global.model.DefDocListVO;
import com.gdsp.platform.config.global.service.IDefDocListService;
import com.gdsp.platform.log.service.ILoginLogService;

/**
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/plugins/pqGrid")
public class PqGridDemoController {
    
    @Resource
    private IDefDocListService defDocListSercice;
    
    @Autowired
    private ILoginLogService loginLogService;
    
    @RequestMapping("/list.d")
    public String list(Model model,Condition condition, Pageable page){
        Map<String,String> colModelMap = new HashMap<String,String>();
        colModelMap.put("title","类型编码,类型名称 ,类型描述");
        colModelMap.put("dataIndx", "type_code,type_name,type_desc");
        Page<DefDocListVO> defDocListVO = defDocListSercice.queryDefDocListVOByCondition(condition, page, null);
        model.addAttribute("simpleGridPage", defDocListVO);
        model.addAttribute("refreshData", JsonUtils.formatListToPqgridJson(defDocListVO.getContent(),colModelMap));
        //本地数据示例
        //加载一个月的登录日志
//        Map<String,String> colModelMap = new HashMap<String,String>();
//        String type = PortalQueryConst.monthString.toString();
//        List<LoginLogVO> loginLogPage = loginLogService.findListByCondition("", type, "", "");
//        colModelMap.put("title","账号,姓名,登录时间");
//        colModelMap.put("dataIndx", "login_account,username,login_time");
//        model.addAttribute("refreshData", JsonUtils.formatListToPqgridJson(loginLogPage,colModelMap));
        return "demo/pqGridDemo/list";
    }
    
    @RequestMapping("/listdata.d")
    public String getPqGrid(Model model,Condition condition, Pageable page){
        Map<String,String> colModelMap = new HashMap<String,String>();
        colModelMap.put("title","类型编码,类型名称 ,类型描述");
        colModelMap.put("dataIndx", "type_code,type_name,type_desc");
        Page<DefDocListVO> defDocListVO = defDocListSercice.queryDefDocListVOByCondition(condition, page, null);
        model.addAttribute("simpleGridPage", defDocListVO);
        model.addAttribute("refreshData", JsonUtils.formatListToPqgridJson(defDocListVO.getContent(),colModelMap));
        //本地数据示例
        //加载一个月的登录日志
//        Map<String,String> colModelMap = new HashMap<String,String>();
//        String type = PortalQueryConst.monthString.toString();
//        List<LoginLogVO> loginLogPage = loginLogService.findListByCondition("", type, "", "");
//        colModelMap.put("title","账号,姓名,登录时间");
//        colModelMap.put("dataIndx", "login_account,username,login_time");
//        model.addAttribute("refreshData", JsonUtils.formatListToPqgridJson(loginLogPage,colModelMap));
        return "demo/pqGridDemo/list-data";
    }
    
    @RequestMapping("/toedit.d")
    public String toEdit(Model model,String id){
        DefDocListVO defDocListVO = defDocListSercice.load(id);
        model.addAttribute("editType", "edit");
        model.addAttribute("defDocListVO", defDocListVO);
        return "demo/pqGridDemo/form";
    }
    
}
