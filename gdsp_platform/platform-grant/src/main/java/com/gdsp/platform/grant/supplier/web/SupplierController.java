package com.gdsp.platform.grant.supplier.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gdsp.dev.base.utils.JsonUtils;
import com.gdsp.dev.core.common.AppContext;
import com.gdsp.dev.web.mvc.ext.ViewWrapper;
import com.gdsp.dev.web.mvc.model.AjaxResult;
import com.gdsp.platform.grant.org.model.OrgVO;
import com.gdsp.platform.grant.supplier.model.SupplierVO;
import com.gdsp.platform.grant.supplier.service.ISupplierOptService;
import com.gdsp.platform.grant.supplier.service.ISupplierQueryPubService;
import com.gdsp.platform.grant.supplier.service.ISupplierService;

@Controller
@RequestMapping("grant/supplier")
public class SupplierController {
	@Autowired
	private ISupplierService supService;
	@Autowired
	private ISupplierOptService supOptService;
	@Autowired
	private ISupplierQueryPubService supQueryPubService;

	@RequestMapping("/list.d")
	public String list(Model model, String pk_sup) {
		List<SupplierVO> supVOs = supQueryPubService.queryAllSupList();
		if (supVOs.size() > 0) {
			SupplierVO supVO = supVOs.get(0);
			pk_sup = supVO.getId();
			model.addAttribute("supVO", supQueryPubService.load(pk_sup));
		}
		return "grant/supplier/list";
	}

	@RequestMapping("/toSupTreePage.d")
	@ViewWrapper(wrapped = false)
	public String toSupTreePage() {
		return "grant/supplier/sup-tree";
	}

	@RequestMapping("/listSup.d")
	@ResponseBody
	public String listSup() {
		List<SupplierVO> supVOs = supQueryPubService.queryAllSupList();
		Map<String, String> nodeAttr = new HashMap<>();
		nodeAttr.put("text", "supname");
		if (supVOs.size() != 0) {
			return JsonUtils.formatList2TreeViewJson(supVOs, nodeAttr);
		} else {
			return "";
		}
	}

	@RequestMapping("/listData.d")
	@ViewWrapper(wrapped = false)
	public String listData(Model model, String pk_sup) {
		model.addAttribute("supVO", supQueryPubService.load(pk_sup));
		return "grant/supplier/form";
	}

	@RequestMapping("/add.d")
	@ViewWrapper(wrapped = false, onlyAjax = true)
	public Object add(Model model, String pk_sup) {
		if (StringUtils.isEmpty(pk_sup)) {
			SupplierVO supvo = new SupplierVO();
			model.addAttribute("supVO", supvo);
		} else {
			SupplierVO sup = supQueryPubService.load(pk_sup);
			SupplierVO supvo = new SupplierVO();
			supvo.setPk_fathersup(pk_sup);
			supvo.setPk_fathername(sup.getSupname());
			model.addAttribute("supVO", supvo);
		}
		model.addAttribute("editType", "add");
		return "grant/supplier/sup-add";
	}

	@RequestMapping("/queryParentSup.d")
	@ViewWrapper(wrapped = false)
	public Object queryParentSup(Model model) {
		return "grant/supplier/queryparentsup";
	}

	@RequestMapping("/edit.d")
	@ViewWrapper(wrapped = false, onlyAjax = true)
	public String edit(Model model, String pk_sup) {
		model.addAttribute("supVO", supQueryPubService.load(pk_sup));
		model.addAttribute("editType", "edit");
		return "grant/supplier/sup-add";
	}

	@RequestMapping("/save.d")
	@ResponseBody
	public Object save(SupplierVO supVO) {
		try {
			if (StringUtils.isEmpty(supVO.getId())) {
				supOptService.insert(supVO);
			} else {
				supOptService.update(supVO);
			}
		} catch (Exception e) {
			return new AjaxResult(AjaxResult.STATUS_ERROR, "同一机构的下级机构名称不能重复，请确认！");
		}
		return AjaxResult.SUCCESS;
	}

	@RequestMapping("/uniqueCheck.d")
	@ResponseBody
	public Object uniqueCheck(SupplierVO supVO) {
		return supService.uniqueCheck(supVO);
	}

	@RequestMapping("/delete.d")
	@ResponseBody
	public Object delete(String id) {
		try {
			supOptService.delete(id);
		} catch (Exception e) {
			return new AjaxResult(AjaxResult.STATUS_ERROR, "操作失败");
		}
		return AjaxResult.SUCCESS;
	}
}
