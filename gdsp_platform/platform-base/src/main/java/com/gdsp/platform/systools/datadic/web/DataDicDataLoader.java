/**
 * 
 */
package com.gdsp.platform.systools.datadic.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.gdsp.dev.core.view.IViewDataLoader;
import com.gdsp.dev.web.mvc.ext.ViewWrapper;
import com.gdsp.platform.systools.datadic.service.IDataDicService;

/**数据字典数据加载
 * @author wangliyuan
 *
 */
@Controller("loaderDataDic")
@ViewWrapper(wrapped = false)
public class DataDicDataLoader implements IViewDataLoader{
	@Autowired
	private IDataDicService dataDicService;
	@Override
	public Object getValue() {
		
		return null;
	}

	@Override
	public Object getValue(String... parameter) {
		
		if (parameter == null || parameter.length == 0)
            return null;
		return dataDicService.getDatadicValueByTypeCode(parameter[0]);
	}

}
