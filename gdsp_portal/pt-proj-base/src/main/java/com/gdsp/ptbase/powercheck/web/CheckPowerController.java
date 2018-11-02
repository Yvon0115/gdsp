/**
 * 
 */
package com.gdsp.ptbase.powercheck.web;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gdsp.ptbase.powercheck.service.ICheckPowerService;


/**
 * @author wangliyuan
 *
 */
@Controller
@RequestMapping("noauth/check")
@ResponseBody
public class CheckPowerController {
    
    @Autowired
    private ICheckPowerService checkPowerService;
    
    /**
     * check token from report server
     * @param request
     * @return
     */
    @RequestMapping("/proofToken.d")
    @ResponseBody
    public String checkoutToken(HttpServletRequest request) {
        //get token from request
        String token = request.getParameter("token");
        String checkST = checkPowerService.checkST(token);
        return checkST;
        }
    /**
     * check user power 
     * @param request
     * @return
     */
    @RequestMapping("/checkUser.d")
    @ResponseBody
    public String checkoutUserMessage(HttpServletRequest request){
        //get username and password from request
        String userName = request.getParameter("username");
        String password = request.getParameter("password");
        String checkPower = checkPowerService.checkPower(userName, password);
        return checkPower;
        
    }
}
