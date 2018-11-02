/*
 * Copyright (c)  Business Intelligence Unified Portal. All rights reserved.
 * 
 */ 
package com.gdsp.platform.demo.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 表单样式 viewOnly属性demo
 * @author lt
 * @date 2017年11月14日 下午4:51:30
 */
@Controller
@RequestMapping("page/view")
public class ViewOnlyDemoController {

	@RequestMapping("/list.d")
	public String list() {
		return "demo/form/viewOnlyDemo";
	}
}
