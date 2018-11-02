package com.gdsp.platform.common.web;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;

import com.gdsp.dev.core.common.AppContext;
import com.gdsp.dev.core.view.IViewDataLoader;
import com.gdsp.platform.grant.auth.service.IPowerMgtQueryPubService;

/**
 * 导航菜单加载
 *
 * @author paul.yang
 * @version 1.0 2015-8-17
 * @since 1.6
 */
@Controller("menuNavigator")
public class MenuNavigatorLoader implements IViewDataLoader {

    @Resource
    private IPowerMgtQueryPubService powerMgtPubService;
    
    // 显示菜单类型：vertical：纵向； horizontal：横向
    private final String             showMenuType_param      = "showMenuType";
    private final String             showMenuType_vertical   = "vertical";
    @SuppressWarnings("unused")
	private final String             showMenuType_horizontal = "horizontal";

    @Override
    public Object getValue() {
        String userId = AppContext.getContext().getContextUserId();
        if (StringUtils.isEmpty(userId))
            return null;
        String menuType = com.gdsp.dev.core.common.AppConfig.getProperty(showMenuType_param);
        if (showMenuType_vertical.equals(menuType)) {
            return powerMgtPubService.queryAllLevelMenuListByUser(userId);
        } else {
            return powerMgtPubService.queryFirstLevelMenuListByUser(userId);
        }
    }

    @Override
    public Object getValue(String... parameter) {
        if (parameter == null || parameter.length == 0)
            return null;
        String userId = AppContext.getContext().getContextUserId();
        return powerMgtPubService.queryLowerLevelMenuMapByUser(userId, parameter[0]);
    }
}
