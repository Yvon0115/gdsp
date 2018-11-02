/**
 * 
 */
package com.gdsp.platform.config.customization.web;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gdsp.dev.core.common.AppContext;
import com.gdsp.dev.web.mvc.ext.ViewWrapper;
import com.gdsp.dev.web.mvc.model.AjaxResult;
import com.gdsp.dev.web.security.login.AuthInfo;
import com.gdsp.dev.web.security.shiro.EncodePasswordService;
import com.gdsp.platform.config.customization.model.PasswordConfVO;
import com.gdsp.platform.config.customization.service.ISystemConfExtService;
import com.gdsp.platform.grant.user.model.UserVO;
import com.gdsp.platform.grant.user.service.IUserOptPubService;
import com.gdsp.platform.grant.user.service.IUserQueryPubService;

/**
 * 
 * @Description:密码安全策略Controller
 * @author guoyang
 * @date 2016年12月6日
 */
@Controller
@RequestMapping("security/pwd")
public class PasswordSecurityPolicyController {
    
    @Autowired
    private IUserQueryPubService     userQueryPubService;
    
    @Resource
    private IUserOptPubService       userOptPubService;
    
    @Autowired
    private ISystemConfExtService    systemConfExtService;
    
    @Autowired
    private EncodePasswordService passwordService = null;
    
    /***
     * 修改密码界面
     * 
     * @param model
     * @param userId
     * @return
     */
    @RequestMapping("/toEditPassword.d")
    @ViewWrapper(wrapped = false)
    public String toEditPassword(Model model) {
        String account = AppContext.getContext().getContextAccount();
        UserVO userVo = userQueryPubService.queryUserByAccount(account);
        PasswordConfVO conf = systemConfExtService.queryPasswordConf();
        model.addAttribute("user", userQueryPubService.load(userVo.getId()));
        model.addAttribute("pwdSecurity", conf);
        return "security/pwdsecurity/editPassword";
    }
    
    /***
     * 修改密码功能
     * 
     * @param user
     * @return
     */
    @RequestMapping("/editPassword.d")
    @ResponseBody
    public Object editPassword(UserVO user,HttpServletRequest request,HttpServletResponse response) {
        userOptPubService.updateUserPasssword(user);
//      response.setHeader("Set-Cookie", "editpwd=N;expires=0; Path=/; HttpOnly");
//      AppContext.getContext().setAttribute(AppContext.SESSION, AppContext.USER_KEY, value);
        AuthInfo uservo =  userQueryPubService.queryUserAuthInfoByAccount(user.getAccount());
        request.getSession().setAttribute(AppContext.USER_KEY, uservo);
        
        return AjaxResult.SUCCESS;
    }
    
    @RequestMapping("/checkPassword.d")
    @ResponseBody
    public Object checkPassword(String user_password){
        String msg = "";
        //密码不允许包含空格
        if(user_password.contains(" ")){
            msg = "密码不允许包含空格！";
            return msg;
        }
        //查询当前用户信息
        UserVO user = userQueryPubService.load(AppContext.getContext().getContextUserId());
        String encode_pwd = passwordService.encodePassword(user.getAccount(), user_password);
        if(encode_pwd.equals(user.getPassword())){
            msg = "新密码不能与原密码相同";
            return msg;
        }
        PasswordConfVO conf = systemConfExtService.queryPasswordConf();
        if(conf.getPwdLength() != null && conf.getPwdLength() != 0 && user_password.length() < conf.getPwdLength()){
            msg = "密码长度不足";
            return msg;
        }
        if(conf.getPwdNumberState() != null && "Y".equals(conf.getPwdNumberState())){
            String regex="[0-9]+?";
            Pattern p=Pattern.compile(regex);
            Matcher m=p.matcher(user_password);
            if(!m.find()){
                msg = "密码未包含数字";
                return msg;
            }
        }
        if(conf.getPwdCharacterState() != null && "Y".equals(conf.getPwdCharacterState())){
            String regex="[@#$]+";
            Pattern p=Pattern.compile(regex);
            Matcher m=p.matcher(user_password);
            if(!m.find()){
                msg = "密码未包含@,#,$中的一个";
                return msg;
            }
        }
        if(conf.getPwdCaseState() != null && "Y".equals(conf.getPwdCaseState())){
            String regex=".*[A-Z]+.*";
            Pattern p=Pattern.compile(regex);
            Matcher m=p.matcher(user_password);
            if(!m.find()){
                msg = "密码未包含大写英文字母";
                return msg;
            }
        }
        if(conf.getPwdEnglishState() != null && "Y".equals(conf.getPwdEnglishState())){
            String regex=".*[a-zA-Z]+.*";
            Pattern p=Pattern.compile(regex);
            Matcher m=p.matcher(user_password);
            if(!m.find()){
                msg = "密码未包含英文字母";
                return msg;
            }
        }
        return true;
    }
}
