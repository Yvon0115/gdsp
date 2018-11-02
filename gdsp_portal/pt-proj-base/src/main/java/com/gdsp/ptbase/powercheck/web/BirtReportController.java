/**
 * 
 */
package com.gdsp.ptbase.powercheck.web;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gdsp.dev.base.utils.UUIDUtils;
import com.gdsp.ptbase.powercheck.service.ITokenService;


/**
 * @author wangliyuan
 *
 */
@Controller
@RequestMapping("birt/report")
public class BirtReportController {
    
    @Resource
    private ITokenService tokenService;
    
    /**
     * intercept show report request add token
     * @param request
     * @return
     */
    @RequestMapping("/handle.d")
    @ResponseBody
    public JSONObject handleReportUrl(HttpServletRequest request){
        String actionUrl= request.getParameter("actionUrl");
        JSONObject json = new JSONObject();
        if(actionUrl!=null){ 
         String randomUUID = UUIDUtils.getRandomUUID();
        String produceReportToken = tokenService.issueToken(randomUUID);
        json.put("token", produceReportToken);
        json.put("realurl", actionUrl);
        }
        return json;
    }
}
