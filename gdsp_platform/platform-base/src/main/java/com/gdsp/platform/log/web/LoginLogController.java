package com.gdsp.platform.log.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gdsp.dev.core.model.query.Condition;
import com.gdsp.dev.web.mvc.ext.ViewWrapper;
import com.gdsp.platform.log.helper.PortalQueryConst;
import com.gdsp.platform.log.model.LoginLogVO;
import com.gdsp.platform.log.model.ResAccessLogVO;
import com.gdsp.platform.log.service.ILoginLogService;
import com.gdsp.platform.log.utils.CsvUtils;


//修改人：wangxiaolong
//修改时间：2017-4-7
//修改原因：只有在初始化页面时，默认查询近一个月记录，type生效，其他操作，type失效，均已p_start_date，p_end_date为准

@Controller
@RequestMapping("systools/loginlog")
public class LoginLogController {

    @Autowired
    private ILoginLogService loginLogService;

    /**
     * 加载登陆日志
     * @param model
     * @return 加载页面
     */
    @RequestMapping("/list.d")
    public String list(Model model, Pageable page) {
        //加载一个月的登录日志
//    	String type = PortalQueryConst.monthString.toString();
//        Page<LoginLogVO> loginLogPage = loginLogService.queryPageByCondition(page, "", type, "", "");
//        model.addAttribute("LoginLogPages", loginLogPage);
        return "systools/loginlog/list";
    }

    @RequestMapping("/listData.d")
    @ViewWrapper(wrapped = false)
    public String listData(Model model, Pageable page, String queryParam, String type, String p_start_date, String p_end_date) {
        Page<LoginLogVO> loginLogPage = loginLogService.queryPageByCondition(page, queryParam, type, p_start_date, p_end_date);
        model.addAttribute("LoginLogPages", loginLogPage);
        return "systools/loginlog/list-data";
    }
    
    /**
     * 导出登录日志
     * @param response
     * @param request
     * @throws IOException
     */
    @RequestMapping("/doExportLoginLogModel.d")
    @ViewWrapper(wrapped = false)
    @ResponseBody
    public void doExportLoginLogModel(HttpServletResponse response, HttpServletRequest request,Condition condition,String scopeValue,String startTime,String endTime,String accountOrName) throws IOException{
        //表头信息
    	List<String> headList = new ArrayList<String>();
    	//设置表头信息
    	String loginHead = "账号,用户名,登录时间,ip地址,登录状态";
    	headList.add(loginHead);
    	//获得查询后的数据list
        List<LoginLogVO>  loginList = loginLogService.findListByCondition(accountOrName,null ,startTime,endTime);
        //设置内容信息
    	List<String> downList = new ArrayList<String>();
    	downList = loginLogService.getContentList(loginList);
        response.reset();  
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/octet-stream");
        String fileName = "用户登录日志.csv";
        String downloadFileName =new String(fileName.getBytes("gb2312"),"iso-8859-1");
        response.setHeader("Content-Disposition", "attachment; filename="+ downloadFileName);
        ServletOutputStream sos = response.getOutputStream();
        CsvUtils.exportCsv(sos ,headList,downList);
    }
}
