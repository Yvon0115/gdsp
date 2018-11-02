package com.gdsp.dev.web.security.jcaptcha;

import com.octo.captcha.engine.CaptchaEngine;
import com.octo.captcha.service.captchastore.CaptchaStore;
import com.octo.captcha.service.image.DefaultManageableImageCaptchaService;

/**
 * 验证码图片管理类
 *
 * @author paul.yang
 * @version 1.0 15-2-6
 * @since 1.6
 */
public class DevManageableImageCaptchaService extends
        DefaultManageableImageCaptchaService {

    /**
     * 构造方法
     * @param captchaStore 验证码存储
     * @param captchaEngine 验证码引擎
     * @param minGuarantedStorageDelayInSeconds 验证码过期时间
     * @param maxCaptchaStoreSize 验证码最大个数
     * @param captchaStoreLoadBeforeGarbageCollection
     */
    public DevManageableImageCaptchaService(CaptchaStore captchaStore, CaptchaEngine captchaEngine, int minGuarantedStorageDelayInSeconds, int maxCaptchaStoreSize,
            int captchaStoreLoadBeforeGarbageCollection) {
        super(captchaStore, captchaEngine, minGuarantedStorageDelayInSeconds, maxCaptchaStoreSize, captchaStoreLoadBeforeGarbageCollection);
    }

    /**
     * 验证码校验
     * @param id 会话id
     * @param userCaptchaResponse 输入验证码
     * @return 布尔值
     */
    public boolean hasCapcha(String id, String userCaptchaResponse) {
        return store.getCaptcha(id).validateResponse(userCaptchaResponse);
    }
}
