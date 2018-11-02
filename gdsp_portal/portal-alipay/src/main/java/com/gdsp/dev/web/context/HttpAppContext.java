package com.gdsp.dev.web.context;

import java.lang.ref.WeakReference;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.gdsp.platform.common.helper.DpcTransParamConst;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.SubjectContext;
import org.apache.shiro.web.subject.WebSubject;
import org.apache.shiro.web.subject.support.DefaultWebSubjectContext;

import com.alipay.cap.session.common.web.util.CookieUtils;
import com.alipay.cap.session.common.web.util.SessionUtils;
import com.gdsp.dev.core.common.AppConfig;
import com.gdsp.dev.core.common.AppContext;
import com.gdsp.dev.core.common.IContextUser;
import com.gdsp.dev.core.view.IViewDataLoader;
import com.gdsp.dev.web.security.SecurityHelper;
import com.gdsp.dev.web.security.login.AuthInfo;
import com.gdsp.dev.web.security.login.IUserGrantService;

/**
 * 基于Web的上下文实现
 * @author paul.yang
 * @version 1.0 2014年9月24日
 * @since 1.6
 */
public class HttpAppContext extends AppContext {

    /**
     * 序列id
     */
    private static final long                            serialVersionUID  = -2526666324545080308L;
    /**
     * ajax请求类型
     */
    public static final String                           REQUESTTYPE_AJAX  = "XMLHttpRequest";
    /**
     * 保存request,可以及时释放内存
     */
    private transient WeakReference<HttpServletRequest>  requestReference  = null;
    /**
     * 保存request,可以及时释放内存
     */
    private transient WeakReference<HttpServletResponse> responseReference = null;
    /**
     * 判断当前请求是否为ajax请求
     */
    private boolean                                      isAjaxRequest     = false;
    
    /* (non-Javadoc)
     * @see com.bosssoft.frame.common.FaspContext#getAttribute(int, java.lang.String)
     */
    public Object getAttribute(int scope, String name) {
        HttpServletRequest request = getRequest();
        if (request == null)
            return null;
        switch (scope) {
            case AppContext.REQUEST:
                return request.getAttribute(name);
            case AppContext.VIEW:
                return getViewContext().getAttribute(name);
            case AppContext.SESSION:
                return request.getSession().getAttribute(name);
            case AppContext.APPLICATION:
                return request.getSession().getServletContext().getAttribute(name);
            default:
                return null;
        }
    }

    /* (non-Javadoc)
     * @see com.bosssoft.frame.common.FaspContext#getParameter(java.lang.String)
     */
    public String getParameter(String name) {
        HttpServletRequest request = getRequest();
        if (request == null)
            return null;
        return request.getParameter(name);
    }

    /* (non-Javadoc)
     * @see com.bosssoft.frame.common.FaspContext#getParameters(java.lang.String)
     */
    public String[] getParameters(String name) {
        HttpServletRequest request = getRequest();
        if (request == null)
            return null;
        return request.getParameterValues(name);
    }

    /* (non-Javadoc)
     * @see com.bosssoft.frame.common.FaspContext#getParameterMap()
     */
    @Override
    public Map<String, String[]> getParameterMap() {
        HttpServletRequest request = getRequest();
        if (request == null)
            return null;
        return request.getParameterMap();
    }

    /* (non-Javadoc)
     * @see com.bosssoft.frame.common.FaspContext#removeAttribute(int, java.lang.String)
     */
    public void removeAttribute(int scope, String name) {
        HttpServletRequest request = getRequest();
        if (request == null)
            return;
        switch (scope) {
            case AppContext.REQUEST:
                request.removeAttribute(name);
                break;
            case AppContext.VIEW:
                getViewContext().removeAttribute(name);
                break;
            case AppContext.SESSION:
                request.getSession().removeAttribute(name);
                break;
            case AppContext.APPLICATION:
                request.getSession().getServletContext().removeAttribute(name);
                break;
            default:
                request.removeAttribute(name);
        }
    }

    /* (non-Javadoc)
     * @see com.bosssoft.frame.common.FaspContext#setAttribute(int, java.lang.String, java.lang.Object)
     */
    public void setAttribute(int scope, String name, Object value) {
        HttpServletRequest request = getRequest();
        if (request == null)
            return;
        switch (scope) {
            case AppContext.REQUEST:
                request.setAttribute(name, value);
                break;
            case AppContext.VIEW:
                getViewContext().setAttribute(name, value);
                break;
            case AppContext.SESSION:
                request.getSession().setAttribute(name, value);
                break;
            case AppContext.APPLICATION:
                request.getSession().getServletContext().setAttribute(name, value);
                break;
            default:
                request.setAttribute(name, value);
        }
    }

    /* (non-Javadoc)
     * @see com.bosssoft.frame.common.FaspContext#isEmptyInAttributes(int)
     */
    public boolean isEmptyInAttributes(int scope) {
        HttpServletRequest request = getRequest();
        if (request == null)
            return true;
        switch (scope) {
            case AppContext.REQUEST:
                return request.getAttributeNames().hasMoreElements();
            case AppContext.VIEW:
                return getViewContext().getAttributeNames().isEmpty();
            case AppContext.SESSION:
                return request.getSession().getAttributeNames().hasMoreElements();
            case AppContext.APPLICATION:
                return request.getSession().getServletContext().getAttributeNames().hasMoreElements();
        }
        return true;
    }

    /* (non-Javadoc)
     * @see com.bosssoft.frame.common.FaspContext#isEmpty()
     */
    public boolean isEmpty() {
        if (!isEmptyInAttributes(AppContext.REQUEST))
            return false;
        if (!isEmptyInAttributes(AppContext.VIEW))
            return false;
        if (!isEmptyInAttributes(AppContext.SESSION))
            return false;
        if (!isEmptyInAttributes(AppContext.APPLICATION))
            return false;
        return true;
    }

    /**
     * 取得Servlet上下文对象，应用级上下文
     * @return Servlet上下文对象
     */
    public ServletContext getApplication() {
        HttpServletRequest request = getRequest();
        if (request == null)
            return null;
        return request.getSession().getServletContext();
    }

    /**
     * 取得Session对象
     * @return Session对象
     */
    public HttpSession getSession() {
        HttpServletRequest request = getRequest();
        if (request == null)
            return null;
        return request.getSession();
    }

    /**
     * 取得View上下文对象
     * @return View上下文对象
     */
    public HttpViewContext getViewContext() {
        return HttpViewContext.getViewContext(this);
    }

    /**
     * 判断当前请求是否为ajax请求
     * @return 布尔值
     */
    public boolean isAjaxRequest() {
        return isAjaxRequest;
    }

    /**
     * 取得Http请求对象
     * @return Http请求对象
     */
    public HttpServletRequest getRequest() {
        if (requestReference == null)
            return null;
        return requestReference.get();
    }

    /**
     * 设置Http请求对象
     * @param request Http请求对象
     */
    public void setRequest(HttpServletRequest request) {
        String requestType = request.getHeader("X-Requested-With");
        isAjaxRequest = REQUESTTYPE_AJAX.equalsIgnoreCase(requestType);
        requestReference = new WeakReference<HttpServletRequest>(request);
    }

    /**
     * 取得Http响应对象
     * @return Http响应对象
     */
    public HttpServletResponse getResponse() {
        if (responseReference == null)
            return null;
        return responseReference.get();
    }

    /**
     * 设置Http响应对象
     * @param response Http响应对象
     */
    public void setResponse(HttpServletResponse response) {
        responseReference = new WeakReference<>(response);
    }

    /**
     * 通过加载器获取上下文值
     * @param loaderKey 加载器键值
     * @return 加载结果
     */
    public Object getLoaderValue(String loaderKey) {
        IViewDataLoader loader = lookup(loaderKey, IViewDataLoader.class);
        if (loader == null)
            return null;
        return loader.getValue();
    }

    /**
     * 通过加载器获加载指定参数的上下文值
     * @param loaderKey 加载器键值
     * @param parameter 参数
     * @return 加载结果
     */
    public Object getLoaderValue(String loaderKey, String... parameter) {
        IViewDataLoader loader = lookup(loaderKey, IViewDataLoader.class);
        if (loader == null)
            return null;
        return loader.getValue(parameter);
    }
    
    /** 
     * 根据阿里cap单点登录工具获取用户信息注册到门户上下文中
     */
    @Override
    public String getContextAccount() {
    	String DPC_CUSTOMER_ID_PREFIX = AppConfig.getProperty("alidpc.customerKeyPrefix");
    	HttpServletRequest request = (HttpServletRequest) AppContext.getContext().getRequest();
    	String account = SessionUtils.getCurrentUser(request).getUserId();
    	if(account.startsWith(DPC_CUSTOMER_ID_PREFIX)){
    		account = account.substring(DPC_CUSTOMER_ID_PREFIX.length());
    	}
    	return account;
    }

    public String getDpcCustomerKey() {
        return DpcTransParamConst.DPC_CUSTOMER_KEY;
    }

    public String getDpcLoginTokenKey() {
        return DpcTransParamConst.LOGIN_TOKEN_KEY;
    }

    public String getDpcCustomerId() {
        return AppConfig.getProperty("alidpc.customerId");
    }

    public String getDpcLoginToken() {
        String loginToken = (String) getAttribute(SESSION, DpcTransParamConst.LOGIN_TOKEN_KEY);
        if (null != loginToken && loginToken.length() > 0) {
            return loginToken;
        } else {
            return "";
        }
    }
    
    @Override
    public IContextUser getContextUser() {
        String account = getContextAccount();
        if (StringUtils.isEmpty(account))
            return null;
        IContextUser user = (IContextUser) getAttribute(SESSION, USER_KEY);
        if (user != null)
            return user;
        IUserGrantService service = SecurityHelper.getGrantService();
        if (service == null)
            return null;
        // 放入session中的用户信息，不应包含用户的私人及敏感信息
        user = service.findAuthInfoByAccount(account);
        setAttribute(SESSION, USER_KEY, user);
        return user;
    }

    public void login(IContextUser user) {
        SubjectContext context = new DefaultWebSubjectContext();
        context.setAuthenticated(true);
        AuthInfo authInfo = (AuthInfo) user;
        context.setAuthenticationToken(new UsernamePasswordToken(authInfo.getAccount(), authInfo.getPassword()));
        context.setSubject(new WebSubject.Builder(SecurityUtils.getSecurityManager(), getRequest(), getResponse()).buildWebSubject());
        context.setAuthenticationInfo(new SimpleAuthenticationInfo(authInfo.getAccount(), authInfo.getPassword(), "userRealm"));
        SecurityUtils.getSecurityManager().createSubject(context);
    }
}
