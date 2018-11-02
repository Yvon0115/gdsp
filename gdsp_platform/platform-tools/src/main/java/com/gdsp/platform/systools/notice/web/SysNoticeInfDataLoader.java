package com.gdsp.platform.systools.notice.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gdsp.dev.core.common.AppContext;
import com.gdsp.dev.core.view.IViewDataLoader;
import com.gdsp.platform.systools.notice.service.ISysNoticeService;

/**
 * 消息数据加载器
 * @author wqh
 * @since 2017年6月22日 下午1:55:25
 */
@Controller("noticeInfData")
@RequestMapping("tools/noticeInfdata")
public class SysNoticeInfDataLoader implements IViewDataLoader {

    @Autowired
    private ISysNoticeService noticeService;

    @Override
    @ResponseBody
    @RequestMapping("/getValue.d")
    public Object getValue() {
        
        String user_id = AppContext.getContext().getContextUserId();
        int result = 0;
        result = noticeService.queryPublishInfCount(user_id);
        return result;
    }

    @Override
    public Object getValue(String... parameter) {
        
        return null;
    }
}
