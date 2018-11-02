package com.gdsp.platform.security.helper;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.UnauthenticatedException;
import org.apache.shiro.web.filter.PathMatchingFilter;
import org.apache.shiro.web.util.WebUtils;

public class UrlVerifyFilter extends PathMatchingFilter {
    
    
    @Override
    protected boolean onPreHandle(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
        //得到访问url
        String requestURI = getPathWithinApplication(request);
        UrlVerifyProcessorManager manager = UrlVerifyProcessorManager.getInstance();
        //访问url是否匹配用户有权限的url
        boolean success = manager.verifyUrl(requestURI);
        if (success) {
            return success;
        } else {
            /*throw new UnauthenticatedException("无该地址授权");*/
        	((HttpServletResponse)response).setHeader("Set-Cookie", "editpwd=Y;expires=0; Path=/; HttpOnly");
			WebUtils.saveRequest(request);  
	        WebUtils.issueRedirect(request, response, "/unauthorized.d");
	        return false;
        }
    }
}
