package com.gdsp.platform.security.helper;

import com.gdsp.dev.core.common.AppContext;
import com.gdsp.platform.security.helper.MenuContext.MenuType;

public class UrlVerifyProcessorManager {

    private static UrlVerifyProcessorManager manager = new UrlVerifyProcessorManager();
    private MenuContext                      context;

    private UrlVerifyProcessorManager() {
        context = AppContext.lookupBean(MenuContext.class);
    }

    public static UrlVerifyProcessorManager getInstance() {
        return manager;
    }

    /**
     * 判断访问url是否匹配用户具有权限的url
     * @param url
     * @return
     */
    public boolean verifyUrl(String url) {
        //判断是否登陆成功跳转的url
        if (url.contains("/index.d")) {
            return true;
        }
        //判断是否为模块的访问url
        if (url.contains("module/func")) {
            return true;
        }
        MenuType menuType = context.getMenuType(url);
        //得到UrlMacthingProcessor的实现
        UrlVerifyProcessor urlVerifyProcessor = menuType.getUrlMacthingProcessor();
        //校验url是否匹配
        boolean success = urlVerifyProcessor.verifyUrl(context, url);
        return success;
    }
}
