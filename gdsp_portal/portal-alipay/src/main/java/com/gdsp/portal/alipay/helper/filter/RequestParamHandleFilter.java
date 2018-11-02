package com.gdsp.portal.alipay.helper.filter;

import com.alipay.cap.common.model.UserTenantModel;
import com.alipay.cap.session.common.web.util.SessionUtils;
import com.gdsp.dev.base.lang.DBoolean;
import com.gdsp.dev.core.common.AppConfig;
import com.gdsp.dev.core.common.AppContext;
import com.gdsp.dev.core.utils.FileUtils;
import com.gdsp.dev.web.context.HttpContextFactory;
import com.gdsp.platform.common.helper.DpcTransParamConst;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * cap单点登录请求追加参数过滤处理
 * @author yucl
 * @version 3.0.1 2018/5/17 9:49
 * @since 1.6
 */
public class RequestParamHandleFilter implements Filter {

    private final Logger logger = LoggerFactory.getLogger(RequestParamHandleFilter.class);

    /**
     *  session中用户过期时间
     **/
    private String userInvalidTime;

    @Override
    public void init(FilterConfig filterConfig) {}

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest rq = (HttpServletRequest) request;
        HttpServletResponse rp = (HttpServletResponse) response;
        HttpContextFactory.getContext(rq, rp);
        String sessionToken = rq.getParameter(DpcTransParamConst.LOGIN_TOKEN_KEY);
        String requestURI = WebUtils.getPathWithinApplication(rq);
        StringBuffer requestUrl = rq.getRequestURL();
        UserTenantModel localUser = SessionUtils.getCurrentUser(rq);
        //用户不为空，且不过期，放行
        if (null != localUser) {
            if( !SessionUtils.isUserValid(rq)){
              //cap用户过期时间刷新，单位：min（可以配置与session超时同样时间）
              SessionUtils.refreshUserValidTime(rq, Integer.valueOf(userInvalidTime));
              chain.doFilter(rq, rp);
              return;
            } else {
              //判断用户是否过期
              if (StringUtils.isBlank(rq.getParameter(DpcTransParamConst.DPC_CUSTOMER_KEY))){
                  String uri = FileUtils.getFileIO("cap.redirectIndexUrl", DBoolean.TRUE.booleanValue());
                  if (uri.startsWith("/")){
                      uri = uri.substring(1);
                  }
                  requestUrl = new StringBuffer(requestUrl.substring(0, (requestUrl.length()- requestURI.length() + 1)));
                  requestUrl.append(uri).append("?" + DpcTransParamConst.DPC_CUSTOMER_KEY + "=").append(AppConfig.getProperty("alidpc.customerId")).append("&").append(DpcTransParamConst.LOGIN_TOKEN_KEY).append("=").append(AppContext.getContext().getAttribute(AppContext.SESSION, DpcTransParamConst.LOGIN_TOKEN_KEY));
                  rp.sendRedirect(String.valueOf(requestUrl));
                  return;
              }
              chain.doFilter(rq, rp);
              return;
            }
        }
        //登录请求，追加参数
        if (null == localUser) {
            if (StringUtils.isBlank(rq.getParameter(DpcTransParamConst.DPC_CUSTOMER_KEY))){
                String uri = FileUtils.getFileIO("cap.redirectIndexUrl", DBoolean.TRUE.booleanValue());
                if (uri.startsWith("/")){
                    uri = uri.substring(1);
                }
                if (!StringUtils.equals("/", requestURI)){
                    requestUrl = new StringBuffer(requestUrl.substring(0, (requestUrl.length()- requestURI.length() + 1)));
                }
                requestUrl.append(uri).append("?" + DpcTransParamConst.DPC_CUSTOMER_KEY + "=").append(AppConfig.getProperty("alidpc.customerId"));
                rp.sendRedirect(String.valueOf(requestUrl));
                return;
            }
        }

        //判断token，不为空则存放到上下文
        if(StringUtils.isNotBlank(sessionToken)){
            AppContext.getContext().setAttribute(AppContext.SESSION, DpcTransParamConst.LOGIN_TOKEN_KEY, sessionToken);
        }
        chain.doFilter(rq, rp);
    }

    @Override
    public void destroy() {}

    public void setUserInvalidTime(String userInvalidTime) {
        this.userInvalidTime = userInvalidTime;
    }
}
