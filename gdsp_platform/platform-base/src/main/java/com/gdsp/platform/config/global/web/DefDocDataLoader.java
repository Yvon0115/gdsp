package com.gdsp.platform.config.global.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.gdsp.dev.core.view.IViewDataLoader;
import com.gdsp.platform.config.global.service.IDefDocService;

/**
 * 系统码表数据加载器
 * @author wwb
 * @date 2015年10月30日 下午3:15:31
 */
@Controller("loaderDict")
public class DefDocDataLoader implements IViewDataLoader {

    @Autowired
    private IDefDocService defDocService;

    @Override
    public Object getValue() {
        return null;
    }

    @Override
    public Object getValue(String... parameter) {
        if (parameter == null || parameter.length == 0)
            return null;
        else if (parameter.length == 1) {
        	return defDocService.queryDefDocByTypeCode(parameter[0]);
		}else if (parameter.length == 2){
			return defDocService.findSubLevelDocsByCode(parameter[0],parameter[1]);
		}else{
			return null;
		}
    }
}
