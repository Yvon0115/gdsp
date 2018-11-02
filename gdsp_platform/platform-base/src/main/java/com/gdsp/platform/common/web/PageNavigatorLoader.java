package com.gdsp.platform.common.web;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;

import com.gdsp.dev.core.common.AppContext;
import com.gdsp.dev.core.view.IViewDataLoader;
import com.gdsp.platform.grant.auth.service.IPowerMgtQueryPubService;

/**
 * 导航栏  页面菜单加载
 *
 * @author paul.yang
 * @version 1.0 2015-8-17
 * @since 1.6
 */
@Controller("pageNavigator")
public class PageNavigatorLoader implements IViewDataLoader {

    @Resource
    private IPowerMgtQueryPubService powerMgtPubService;

    @Override
    public Object getValue() {
        String userId = AppContext.getContext().getContextUserId();
        if (StringUtils.isEmpty(userId))
            return userId;
        return powerMgtPubService.queryPageMenuListByUser(userId);
    }

    @Override
    public Object getValue(String... parameter) {
        return getValue();
    }
}
