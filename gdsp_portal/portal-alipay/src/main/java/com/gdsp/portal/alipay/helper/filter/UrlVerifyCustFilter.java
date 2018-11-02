/**
 * 
 */
package com.gdsp.portal.alipay.helper.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gdsp.dev.core.common.AppContext;
import com.gdsp.dev.web.context.HttpAppContext;
import com.gdsp.platform.security.helper.UrlVerifyProcessorManager;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.web.util.WebUtils;

import com.alipay.cap.session.common.web.util.SessionUtils;
import com.gdsp.dev.web.context.HttpContextFactory;

/**
 * 阿里cap单点登录集成url拦截器
 * @author yucl
 * @version 3.0.1 2018-4-27
 * @since 1.6
 */
public class UrlVerifyCustFilter implements Filter {

	@Override
	public void init(FilterConfig filterConfig) {
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		//得到访问url
        String requestURI = WebUtils.getPathWithinApplication(WebUtils.toHttp(request));
        UrlVerifyProcessorManager manager = UrlVerifyProcessorManager.getInstance();
        //访问url是否匹配用户有权限的url
        boolean success = manager.verifyUrl(requestURI);
        if (success) {
			chain.doFilter(request, response);
//            return success;
        } else {
            /*throw new UnauthenticatedException("无该地址授权");*/
        	((HttpServletResponse)response).setHeader("Set-Cookie", "editpwd=Y;expires=0; Path=/; HttpOnly");
			WebUtils.saveRequest(request);  
	        WebUtils.issueRedirect(request, response, "/unauthorized.d");
//	        return false;
        }
	}

	@Override
	public void destroy() {
		
	}

}
