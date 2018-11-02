package com.gdsp.platform.security.helper;

import java.util.Map;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

/**
 * 页面权限校验
 * @author GuoYang
 * 2016年11月18日 下午6:27:03
 */
public class PageVerifyProcessor implements UrlVerifyProcessor {

    @Override
    public boolean verifyUrl(MenuContext context, String url) {
        Subject subject = SecurityUtils.getSubject();
        String loginName = subject != null ? (String) subject.getPrincipal() : null;
        Map<String, String> userPageMap = context.getUserPageMap(loginName);
        //页面定制时允许用户将功能发布成页面，那么context.getUserPageMap查询的数据中当前被访问的url被过滤掉，最终导致页面授权失败。
        //所以将用户关联的菜单再次查询并合并
        Map<String, String> userMenuMap = context.getUserMenuMap(loginName);
        userPageMap.putAll(userMenuMap);
        for (String key : userPageMap.keySet()) {
            if (url.contains(key)) {
                return true;
            }
        }
        return false;
    }
}
