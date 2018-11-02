package com.gdsp.platform.common.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gdsp.dev.core.common.AppConfig;
import com.gdsp.dev.core.common.AppContext;
import com.gdsp.dev.web.context.HttpAppContext;
import com.gdsp.dev.web.mvc.ext.ViewDecorate;
import com.gdsp.dev.web.mvc.ext.ViewWrapper;
import com.gdsp.platform.grant.user.model.UserVO;
import com.gdsp.platform.grant.user.service.IUserOptPubService;
import com.gdsp.platform.grant.user.service.IUserQueryPubService;

/**
 * 登录设置
 * @author paul.yang
 * @version 1.0 2014年11月11日
 * @since 1.6
 */
@Controller
public class LoginController {
	
	@Autowired
	private IUserOptPubService userOptPubService;
	@Autowired
	private IUserQueryPubService userQueryPubService;

    /**
     * 登录处理
     * @param model 模型对象
     * @param req 请求对象
     * @param username 用户名
     * @return 目标view
     */
    @RequestMapping(value = "/login.d")
    @ViewWrapper(wrapped = false)
    @ViewDecorate(decorate = false)
    public String login(Model model, HttpServletRequest req, HttpServletResponse response, String username) {
       if (SecurityUtils.getSubject().isAuthenticated() && !response.isCommitted())
            return "redirect:/index.d";
        String exceptionClassName = (String) req.getAttribute("shiroLoginFailure");
        String error = null;
        UserVO userVO=null;
        if(username!=null){
          userVO = userQueryPubService.queryUserByAccount(username.trim());
        }
        if (UnknownAccountException.class.getName().equals(exceptionClassName)) {
            error = "用户名/密码错误";
        } else if (IncorrectCredentialsException.class.getName().equals(exceptionClassName)) {
            error = "用户名/密码错误";
        } else if (userVO!=null&&userVO.isDisabled()) {
            error = "当前账号已经停用，请与管理员联系";
        } else if(userVO!=null&&userVO.isLocked()){
            error = "当前账号已经锁定，请与管理员联系";
        } else if ("jCaptcha.error".equals(exceptionClassName)) {
            error = "验证码错误";
        } else if (ExcessiveAttemptsException.class.getName().equals(exceptionClassName)) {
            //锁定用户
//            UserVO userVO = userQueryPubService.queryUserByAccount(username.trim());
            if(userVO != null && StringUtils.isNotBlank(userVO.getId())){
            	int i = AppConfig.getInstance().getInt("retryCount");
            	String[] userId = {userVO.getId()};
                userOptPubService.lockUser(userId);
                error = "登录多于" + i + "次，用户已被锁定，请联系管理员";
            }
        } else if (exceptionClassName != null) {
            error = "用户不存在";
        }
        if (StringUtils.isEmpty(error))
            error = req.getParameter("error");
        model.addAttribute("error", error);
        if (StringUtils.isNotEmpty(username)) {
            model.addAttribute("username", username);
        } else {
            String userParam = req.getParameter("username");
            if (userParam != null) {
                model.addAttribute("username", userParam);
            }
        }
        String password = req.getParameter("password");
        if(StringUtils.isNotEmpty(password)){
        	model.addAttribute("pwd", password);
        }
        // 获取系统名称配置
        String systemName = AppConfig.getInstance().getString("view.systemName");
        model.addAttribute("systemName", systemName);
        if (((HttpAppContext) AppContext.getContext()).isAjaxRequest()) {
            return "common/login/login_ajax";
        }
        return "common/login/login";
    }
}
