package com.gdsp.platform.security.helper;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

import com.alipay.cap.session.common.web.util.SessionUtils;
import com.gdsp.dev.core.common.AppContext;

public class MenuVerifyProcessor implements UrlVerifyProcessor {

    @Override
    public boolean verifyUrl(MenuContext context, String url) {
//        Subject subject = SecurityUtils.getSubject();
//        String loginName = subject != null ? (String) subject.getPrincipal() : null;
        HttpServletRequest request = (HttpServletRequest) AppContext.getContext().getRequest();
        String loginName = SessionUtils.getCurrentUser(request).getUserId();
        if(loginName.startsWith("gyyh$")){
        	loginName = loginName.substring("gyyh$".length());
    	}
        Map<String, String> userMenuMap = context.getUserMenuMap(loginName);
        userMenuMap.put("/grant/user/personalInformation.d", null);
        userMenuMap.put("/tools/message/messageDlg.d",null);
        userMenuMap.put("/func/menu/queryAllAvailableMenu.d",null);  //功能搜索框异步请求时需要放行该路径    lijun  20170512
        //新用户，在首页可以修改密码。
        userMenuMap.put("/grant/user/execResetPersonalInf.d", null);
        for (String key : userMenuMap.keySet()) {
            if (url.contains(key)) {
                return true;
            }
        }
        return false;
    }
}
