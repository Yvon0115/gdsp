package com.gdsp.platform.comm.message.web;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gdsp.dev.core.common.AppContext;
import com.gdsp.dev.core.view.IViewDataLoader;
import com.gdsp.platform.comm.message.service.IMessageService;

/**
 * @ClassName: MessageInfDataLoader
 * (消息数据加载)
 * @author wwb
 * @date 2015年10月30日 下午3:15:31
 *
 */
@Controller("messageInfData")
@RequestMapping("tools/messageinfdata")
public class MessageInfDataLoader implements IViewDataLoader {

    @Autowired
    private IMessageService messageService;

    @Override
    @ResponseBody
    @RequestMapping("/getValue.d")
    public Object getValue() {
        String userId = AppContext.getContext().getContextUserId();
        if (StringUtils.isEmpty(userId))
            return null;
        int result = 0;
        result = messageService.queryUnlookInfCount(userId);
		return result;
    }

    @Override
    public Object getValue(String... parameter) {
        if (parameter == null || parameter.length == 0)
            return null;
        return null;
    }
}
