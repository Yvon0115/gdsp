package com.gdsp.platform.grant.demo.web;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gdsp.dev.base.exceptions.DevException;
import com.gdsp.dev.base.exceptions.MessageException;
import com.gdsp.dev.core.cases.service.ICrudService;
import com.gdsp.dev.core.cases.service.IQueryService;
import com.gdsp.dev.core.model.query.Condition;
import com.gdsp.dev.web.mvc.ext.ViewWrapper;
import com.gdsp.dev.web.mvc.model.AjaxResult;
import com.gdsp.platform.grant.demo.model.DemoVO;
import com.gdsp.platform.grant.demo.service.IDemoService;

@Controller
@RequestMapping("grant/mydemo")
public class DemoController {
	@Autowired
	private IDemoService demoService;
	@Autowired
	private ICrudService<DemoVO> demoOptPubService;
	@Autowired
	private IQueryService<DemoVO> demoQueryPubService;

	@RequestMapping("/list.d")
	public String list(Model model, Condition condition, Pageable pageable) {
		try {
			model.addAttribute("demoPage", demoQueryPubService.findPageByCondition(condition, pageable));
		} catch (DevException e) {
			e.printStackTrace();
		}
		return "grant/demo/list";
	}

	@RequestMapping("/listData.d")
	@ViewWrapper(wrapped = false, onlyAjax = true)
	public String listData(Model model, Condition condition, Pageable pageable) {
		try {
			model.addAttribute("demoPage", demoQueryPubService.findPageByCondition(condition, pageable));
		} catch (DevException e) {
			e.printStackTrace();
		}
		return "grant/demo/list-data";
	}

	@RequestMapping("/add.d")
	@ViewWrapper(wrapped = false, onlyAjax = false)
	public String insert(Model model) {
		return "grant/demo/form";
	}

	@RequestMapping("/save.d")
	@ResponseBody
	public Object save(DemoVO demoVO) {
		try {
			if (StringUtils.isNotEmpty(demoVO.getId())) {
				demoOptPubService.update(demoVO);
			} else {
				demoOptPubService.insert(demoVO);
			}
		} catch (Exception e) {
			return new AjaxResult(AjaxResult.STATUS_ERROR,e.getMessage());
		}
		return AjaxResult.SUCCESS;
	}

	@RequestMapping("/edit.d")
	@ViewWrapper(wrapped = false, onlyAjax = true)
	public String edit(Model model, String id) {
		model.addAttribute("demo", demoOptPubService.load(id));
		return "grant/demo/form";
	}

	@RequestMapping("/delete.d")
	@ResponseBody
	public Object delete(String[] id) {
		try {
			demoOptPubService.delete(id);
		} catch (Exception e) {
			return new AjaxResult(AjaxResult.STATUS_ERROR, "操作失败");
		}
		return AjaxResult.SUCCESS;
	}

	@RequestMapping("/uniqueCheck.d")
	@ResponseBody
	public Object uniqueCheck(DemoVO demoVO) {
		return demoService.uniqueCheck(demoVO);
	}
}
