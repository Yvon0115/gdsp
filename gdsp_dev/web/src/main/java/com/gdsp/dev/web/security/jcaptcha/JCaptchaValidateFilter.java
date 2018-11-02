package com.gdsp.dev.web.security.jcaptcha;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.web.filter.AccessControlFilter;
import org.apache.shiro.web.util.WebUtils;

/**
 * 验证码验证过滤器
 * @author paul.yang
 * @version 1.0 15-2-6
 * @since 1.6
 */
public class JCaptchaValidateFilter extends AccessControlFilter {

    /**
     * 是否开启验证码支持
     */
    private boolean  jcaptchaEnabled     = true;
    /**
     * 前台提交的验证码参数名
     */
    private String   jcaptchaParam       = "jcaptchaCode";
    /**
     * 验证失败后存储到的属性名
     */
    private String   failureKeyAttribute = "shiroLoginFailure";
    /**
     * 验证码生成服务
     */
    private JCaptcha jcaptcha            = null;

    public void setJcaptchaEnabled(boolean jcaptchaEnabled) {
        this.jcaptchaEnabled = jcaptchaEnabled;
    }

    public void setJcaptchaParam(String jcaptchaParam) {
        this.jcaptchaParam = jcaptchaParam;
    }

    public void setFailureKeyAttribute(String failureKeyAttribute) {
        this.failureKeyAttribute = failureKeyAttribute;
    }

    public void setJcaptcha(JCaptcha jcaptcha) {
        this.jcaptcha = jcaptcha;
    }

    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue)  {
        //1、设置验证码是否开启属性，页面可以根据该属性来决定是否显示验证码  
        request.setAttribute("jcaptchaEbabled", jcaptchaEnabled);
        HttpServletRequest httpServletRequest = WebUtils.toHttp(request);
        //2、判断验证码是否禁用 或不是表单提交（允许访问）  
        if (jcaptchaEnabled == false || !"post".equalsIgnoreCase(httpServletRequest.getMethod())) {
            return true;
        }
        //3、此时是表单提交，验证验证码是否正确  
        return jcaptcha.validateResponse(httpServletRequest, httpServletRequest.getParameter(jcaptchaParam));
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response)  {
        //如果验证码失败了，存储失败key属性  
        request.setAttribute(failureKeyAttribute, "jCaptcha.error");
        return true;
    }
}