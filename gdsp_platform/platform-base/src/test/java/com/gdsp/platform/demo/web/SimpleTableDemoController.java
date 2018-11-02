/*
 * Copyright (c)  Business Intelligence Unified Portal. All rights reserved.
 * 
 */ 
package com.gdsp.platform.demo.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * SimpleTableDemoController
 * @author lt
 * @date 2017年12月5日 下午3:51:30
 */
@Controller
@RequestMapping("page/simpleTable")
public class SimpleTableDemoController {

	@RequestMapping("/list.d")
	public String list() {
		return "demo/simpleTable/stDemo";
	}
}
