package com.gdsp.ptbase.appcfg.helper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.gdsp.dev.core.common.AppContext;
import com.gdsp.ptbase.appcfg.model.WidgetActionVO;
import com.gdsp.ptbase.appcfg.service.IWidgetActionService;
import com.gdsp.ptbase.portal.model.IPortletAction;

/**
 * portlet操作助手类
 *
 * @author paul.yang
 * @version 1.0 2015-8-5
 * @since 1.6
 */
public final class PortletActionHelper {

    /**
     * 根据操作键值取得操作列表
     * @param actions 操作键值数组
     * @return 操作列表
     */
    public static List<IPortletAction> getPortletActions(String[] actions) {
        IWidgetActionService service = AppContext.getContext().lookup(IWidgetActionService.class);
        Map<String, WidgetActionVO> actionMap = service.findAllActions();
        List<IPortletAction> portletActions = new ArrayList<>();
        for (String a : actions) {
            WidgetActionVO vo = actionMap.get(a);
            if (vo != null)
                portletActions.add(vo);
        }
        return portletActions;
    }
}
