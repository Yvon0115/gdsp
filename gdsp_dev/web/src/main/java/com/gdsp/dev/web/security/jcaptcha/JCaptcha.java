package com.gdsp.dev.web.security.jcaptcha;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.octo.captcha.service.CaptchaServiceException;
import com.octo.captcha.service.captchastore.FastHashMapCaptchaStore;
import com.octo.captcha.service.image.ImageCaptchaService;

/**
 * 验证码助手类
 * @author paul.yang
 * @version 1.0 15-2-6
 * @since 1.6
 */
public class JCaptcha {
    private static Logger       logger         = LoggerFactory.getLogger(JCaptcha.class);
    /**
     * 验证码服务对象
     */
    private ImageCaptchaService captchaService  = null;
    /**
     * 验证码个数
     */
    private int                 validCodeLength = 4;

    /**
     * 取得验证码个数
     * @return 验证码个数
     */
    public int getValidCodeLength() {
        return validCodeLength;
    }

    /**
     * 设置验证码个数
     * @param validCodeLength 验证码个数
     */
    public void setValidCodeLength(int validCodeLength) {
        this.validCodeLength = validCodeLength;
    }

    /**
     * 取得验证码图片服务
     * @return 验证码图片服务
     */
    public ImageCaptchaService getCaptchaService() {
        if (captchaService == null) {
            captchaService = new DevManageableImageCaptchaService(new FastHashMapCaptchaStore(), new JCaptcaheEngine(validCodeLength), 180,
                    100000, 75000);
        }
        return captchaService;
    }

    /**
     * 设置验证码图片服务
     * @param captchaService 验证码图片服务
     */
    public void setCaptchaService(ImageCaptchaService captchaService) {
        this.captchaService = captchaService;
    }

    /**
     * 验证用户请求
     * @param request 请求对象
     * @param userCaptchaResponse 验证码
     * @return 布尔值
     */
    public boolean validateResponse(
            HttpServletRequest request, String userCaptchaResponse) {
    	HttpServletRequestWrapper requestWrapper = (HttpServletRequestWrapper)request;
        HttpServletRequest httpRequest = (HttpServletRequest)requestWrapper.getRequest();
        if (httpRequest.getSession(false) == null)
            return false;
        boolean validated = false;
        try {
            String id = httpRequest.getRequestedSessionId();
            if(StringUtils.isNotEmpty(id)){
            	validated = getCaptchaService().validateResponseForID(id, userCaptchaResponse)
                        .booleanValue();
            }else{
            	return false;
            }
        } catch (CaptchaServiceException e) {
            logger.debug(e.getMessage(), e);
        }
        return validated;
    }
}