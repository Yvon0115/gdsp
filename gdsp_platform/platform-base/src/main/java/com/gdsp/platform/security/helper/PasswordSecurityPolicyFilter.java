package com.gdsp.platform.security.helper;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.PathMatchingFilter;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.gdsp.dev.core.common.AppContext;
import com.gdsp.dev.web.security.login.IUserGrantService;
import com.gdsp.platform.grant.user.model.UserVO;

/**
 * 权限时效过滤
 * @author lijun
 * @author wqh
 * @since 2017年4月6日 上午11:44:26
 */
public class PasswordSecurityPolicyFilter extends PathMatchingFilter {
    @Autowired
    private IUserGrantService iUserGrantService;

    @Override
    protected boolean onPreHandle(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
        /* 放弃原本通过cookie判断用户密码时效状态的做法，因为cookie可能会被禁用；
         * 即使启用cookie，在同一个浏览器中登录多个用户也会出问题。（浏览器不被关闭，
         * 或者cookie不被清除，会导致后续在此浏览器登录的用户获取到错误的密码时效状态）
         * 已不再建议使用cookie，因为运用不当的话可能会产生诸多问题。
         */
        /*
        boolean editpwd = false;
        Cookie[] cookies= ((HttpServletRequest)request).getCookies();
        for(Cookie cookie : cookies){
        	if("editpwd".equals(cookie.getName()) && "N".equals(cookie.getValue())){
        		editpwd = false;
        	}
        }*/
        boolean editpwd = false;
        HttpServletRequest httpRequest = WebUtils.toHttp(request);
        // 从session中获取用户，避免多次数据库查询
        UserVO user = (UserVO) httpRequest.getSession().getAttribute(AppContext.USER_KEY);
        //当从session获取不到用户时，再从SecurityUtil获取用户
        if(user == null){
          Subject subject = SecurityUtils.getSubject();
          String account =  (String)subject.getPrincipal();
          user = (UserVO) iUserGrantService.findAuthInfoByAccount(account);
        }
        // 判断：若用户为新增、密码重置状态或者密码过期，则需要重新修改密码  lijun 20170427
        if (user != null) {
            PasswordSecurityPolicyValidator validator = AppContext.lookupBean(PasswordSecurityPolicyValidator.class);
            editpwd = validator.validatePasswordByAccount(user);
        }
        StringBuffer url = httpRequest.getRequestURL();
        // 如果请求是密码校验方法且为异步方法,放行
        if (url.toString().contains("/security/pwd/checkPassword.d") && httpRequest.getHeader("x-requested-with") != null) {
            editpwd = false;
        } else if (url.toString().contains("/security/pwd/toEditPassword.d")) {
            editpwd = false;
        } else if (url.toString().contains("/security/pwd/editPassword.d") && httpRequest.getHeader("x-requested-with") != null) {
            editpwd = false;
        }
        if (editpwd) {
            /*((HttpServletResponse)response).setHeader("Set-Cookie", "editpwd=N;expires=0; Path=/; HttpOnly");
            Subject subject = SecurityUtils.getSubject();
            String account =  (String)subject.getPrincipal();
            if (isNeedsReset) {
            ((HttpServletResponse)response).setHeader("Set-Cookie", "editpwd=Y;expires=0; Path=/; HttpOnly");
            }*/
            WebUtils.saveRequest(request);
            WebUtils.issueRedirect(request, response, "/security/pwd/toEditPassword.d");
            return false;
        }
        return true;
    }
}
