package com.gdsp.platform.grant.product.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gdsp.dev.base.exceptions.BusinessException;
import com.gdsp.dev.base.utils.JsonUtils;
import com.gdsp.dev.core.model.param.Sorter;
import com.gdsp.dev.core.model.query.Condition;
import com.gdsp.dev.core.model.query.Operator;
import com.gdsp.dev.core.model.query.ValueExpression;
import com.gdsp.dev.web.mvc.ext.ViewWrapper;
import com.gdsp.dev.web.mvc.model.AjaxResult;
import com.gdsp.platform.grant.product.model.ProductVO;
import com.gdsp.platform.grant.product.service.IProductOptPubService;
import com.gdsp.platform.grant.supplier.model.SupplierVO;
import com.gdsp.platform.grant.supplier.service.ISupplierQueryPubService;

@Controller
@RequestMapping("grant/product")
public class ProductController {
	@Autowired
	private IProductOptPubService productOptPubService;
	@Autowired
	private ISupplierQueryPubService supQueryPubService;

	@RequestMapping("/list.d")
	public String list(Model model, Condition condition, Pageable pageable) {
		Sorter sort = new Sorter(Direction.ASC, "name");
		model.addAttribute("productPage", productOptPubService.queryProductByCondition(condition, pageable, sort));
		return "grant/product/list";
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
	@ViewWrapper(wrapped = false, onlyAjax = true)
	public String listData(Model model, String pk_sup, Condition condition, Pageable pageable) {
		if (StringUtils.isEmpty(pk_sup))
			throw new BusinessException("请选择角色所在供应商！");
		List<String> supsId = new ArrayList<>();
		List<SupplierVO> supplierVOs = supQueryPubService.findSelfandChildrenById(pk_sup);
		for (SupplierVO supplierVO : supplierVOs) {
			supsId.add(supplierVO.getId());
		}
		ValueExpression valueExpression = new ValueExpression("pk_sup", supsId, Operator.IN);
		condition.addExpressions(valueExpression);
		Sorter sort = new Sorter(Direction.ASC, "name");
		model.addAttribute("selSupId", pk_sup);
		model.addAttribute("productPage", productOptPubService.queryProductByCondition(condition, pageable, sort));
		return "grant/product/list-data";
	}

	@RequestMapping("/add.d")
	public String insert(Model model, String pk_sup) {
		ProductVO productVO = new ProductVO();
		productVO.setPk_sup(pk_sup);
		model.addAttribute("supname", supQueryPubService.load(pk_sup).getSupname());
		model.addAttribute("product", productVO);
		return "grant/product/form";
	}

	@RequestMapping("/save.d")
	@ResponseBody
	public Object save(ProductVO productVO) {
		try {
			if (StringUtils.isNotEmpty(productVO.getId())) {
				productOptPubService.update(productVO);
			} else {
				productOptPubService.insert(productVO);
			}
		} catch (Exception e) {
			return new AjaxResult(AjaxResult.STATUS_ERROR, "操作失败");
		}
		return AjaxResult.SUCCESS;
	}

	@RequestMapping("/edit.d")
	// @ViewWrapper(wrapped = false, onlyAjax = true)
	public String edit(Model model, String id) {
		ProductVO product = productOptPubService.load(id);
		model.addAttribute("product", product);
		model.addAttribute("supname", supQueryPubService.load(product.getPk_sup()).getSupname());
		return "grant/product/form";
	}

	@RequestMapping("/delete.d")
	@ResponseBody
	public Object delete(String[] id) {
		try {
			productOptPubService.delete(id);
		} catch (Exception e) {
			return new AjaxResult(AjaxResult.STATUS_ERROR, "操作失败");
		}
		return AjaxResult.SUCCESS;
	}

	@RequestMapping("/uniqueCheck.d")
	@ResponseBody
	public Object uniqueCheck(ProductVO productVO) {
		return productOptPubService.uniqueCheck(productVO);
	}
}
