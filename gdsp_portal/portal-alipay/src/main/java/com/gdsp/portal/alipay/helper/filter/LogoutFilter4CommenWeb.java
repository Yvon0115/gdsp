package com.gdsp.portal.alipay.helper.filter;

import com.alipay.cap.session.common.web.service.CapRemoteSessionService;
import com.alipay.cap.session.common.web.service.impl.CapRemoteSessionServiceImpl;
import com.gdsp.dev.base.exceptions.BusinessException;
import com.gdsp.dev.base.lang.DBoolean;
import com.gdsp.dev.base.utils.web.HttpClientUtils;
import com.gdsp.dev.core.common.AppConfig;
import com.gdsp.dev.core.common.AppContext;
import com.gdsp.dev.core.utils.FileUtils;
import com.gdsp.platform.common.helper.DpcTransParamConst;
import org.apache.commons.collections4.map.LinkedMap;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author yucl
 * @version 0.0.1 2018/5/22 11:45
 * @since 1.6
 */
public class LogoutFilter4CommenWeb implements Filter {

    public static final String DEFAULT_REDIRECT_URL = "/";

    private  String  redirectUrl = DEFAULT_REDIRECT_URL;

    private CapRemoteSessionService capRemoteSessionService;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        if (capRemoteSessionService == null) {
            throw new ServletException("capRemoteSessionService can't be null");
        }
    }

    @Override
    public void doFilter(ServletRequest rq, ServletResponse rp, FilterChain chain) throws IOException {
        HttpServletRequest request = (HttpServletRequest)rq;
        HttpServletResponse response = (HttpServletResponse)rp;
        String redirectUrl = getRedirectUrl(request, response);
        String sessionId = (String) AppContext.getContext().getAttribute(AppContext.SESSION, DpcTransParamConst.LOGIN_TOKEN_KEY);
        LinkedMap<String, Object> params = new LinkedMap<>();
        params.put(DpcTransParamConst.LOGIN_TOKEN_KEY, sessionId);
        String uasServerUrl = FileUtils.getFileIO("uas.sessionInvalidate", DBoolean.TRUE.booleanValue());
        try {
            capRemoteSessionService.invalidate(sessionId, request);
            HttpClientUtils.httpGetText(uasServerUrl, params);
        } catch (Exception e){
            throw new BusinessException("capRemoteSessionService remote call filed!", e);
        }
        issueRedirect(response, redirectUrl);
    }

    @Override
    public void destroy() {

    }

    public void issueRedirect(HttpServletResponse response, String redirectUrl) throws IOException {
        response.sendRedirect(redirectUrl);
    }

    protected String getRedirectUrl(HttpServletRequest request, HttpServletResponse response){
        String scheme = request.getScheme();
        String serverName = request.getServerName();
        int serverPort = request.getServerPort();
        String contextPath = request.getContextPath();
        String redirectUrl = getRedirectUrl();
        boolean includePort = true;
        if ("http".equals(scheme.toLowerCase()) && serverPort == 80) {
            includePort = false;
        }
        if ("https".equals(scheme.toLowerCase()) && serverPort == 443) {
            includePort = false;
        }
        if (!redirectUrl.startsWith("/")){
            redirectUrl = "/" + redirectUrl;
        }
        return scheme + "://" + serverName + ((includePort) ? (":" + serverPort) : "") + contextPath + redirectUrl
                + "?" + DpcTransParamConst.DPC_CUSTOMER_KEY  + "=" + AppConfig.getProperty("alidpc.customerId");
    }

    public String getRedirectUrl(){
        return redirectUrl;
    }

    public void setRedirectUrl(String redirectUrl) {
        this.redirectUrl = redirectUrl;
    }

    public void setCapRemoteSessionService(CapRemoteSessionServiceImpl capRemoteSessionService) {
        this.capRemoteSessionService = capRemoteSessionService;
    }
}
