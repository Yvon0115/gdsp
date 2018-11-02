package com.gdsp.platform.widgets.favorites.web;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gdsp.dev.core.common.AppContext;
import com.gdsp.dev.core.model.entity.AuditableEntity;
import com.gdsp.dev.core.model.param.Sorter;
import com.gdsp.dev.core.model.query.Condition;
import com.gdsp.dev.web.mvc.ext.ViewWrapper;
import com.gdsp.dev.web.mvc.model.AjaxResult;
import com.gdsp.platform.func.model.MenuRegisterVO;
import com.gdsp.platform.func.service.IMenuRegisterService;
import com.gdsp.platform.widgets.favorites.model.FavoritesVO;
import com.gdsp.platform.widgets.favorites.service.IFavoritesService;

/**
 * 收藏管理Controller类
 * @author: gy
 * @date 2016年11月16日
 */
@Controller
@RequestMapping("favorites")
public class FavoritesManagerController {

	@Autowired
	IMenuRegisterService munuRegisterService;

	@Autowired
	IFavoritesService favoritesService;

	/**
	 * 添加收藏
	 * @param model
	 * @param favoritesVO
	 * @return
	 */
	@RequestMapping("/saveFavorites.d")
	@ResponseBody
	public Object saveFavorites(Model model, FavoritesVO favoritesVO) {
		favoritesService.save(favoritesVO);
		return AjaxResult.SUCCESS;
	}

	/**
	 * 删除收藏文件
	 * @param id
	 * @return
	 */
	@RequestMapping("/deleteFavorites.d")
	@ResponseBody
	public Object deleteFavorites(String id) {
		favoritesService.delete(id);
		return AjaxResult.SUCCESS;
	}

	@RequestMapping("/showFavorites.d")
	@ViewWrapper(wrapped = false)
	public String showFavorites(Model model) {
		Sorter sort = new Sorter(Direction.ASC, AuditableEntity.PROP_CREATE_TIME);
		String userid = AppContext.getContext().getContextUserId();
		Condition condition = new Condition();
		condition.addExpression(AuditableEntity.PROP_CREATE_BY, userid);
		Map<?, ?> nodesMap = favoritesService.queryFavoritesVOByCondReturnMap(condition, sort);
		model.addAttribute("nodesMap", nodesMap);
		return "favorites/list";
	}
	
	/*
	@RequestMapping("/showTree.d")
	@ViewWrapper(wrapped = false)
	@ResponseBody
	public String showTree() {
		Sorter sort = new Sorter(Direction.ASC, "createtime");
		String userid = AppContext.getContext().getContextUserId();
		Condition condition = new Condition();
		condition.addExpression("createby", userid);
		Map nodesMap = favoritesService.queryFavoritesVOByCondReturnMap(condition, sort);
		Map<String, String> nodeAttr = new HashMap<String, String>();
		if (nodesMap.size() != 0) {
			return JsonUtils.formatMap2TreeViewJson(nodesMap, nodeAttr);
		} else {
			return "";
		}
	}
*/
	
	@RequestMapping("/showManageFavorites.d")
	@ViewWrapper(wrapped = false)
	public String showManageFavorites(Model model) {
		Sorter sort = new Sorter(Direction.ASC, AuditableEntity.PROP_CREATE_TIME);
		String userid = AppContext.getContext().getContextUserId();
		Condition condition = new Condition();
		condition.addExpression(AuditableEntity.PROP_CREATE_BY, userid);
		Map<?, ?> nodesMap = favoritesService.queryFavoritesVOByCondReturnMap(condition, sort);
		model.addAttribute("nodesMap", nodesMap);
		return "favorites/list2";
	}

	@RequestMapping("/toAddFavorites.d")
	public String toAddFavorites(Model model, String url, String name, String menuId) {
		Sorter sort = new Sorter(Direction.ASC, "createtime");
		String userid = AppContext.getContext().getContextUserId();
		Condition condition = new Condition();
		condition.addExpression("file_type", 1);
		condition.addExpression("createby", userid);
		Map<?, ?> nodesMap = favoritesService.queryFavoritesVOByCondReturnMap(condition, sort);
		model.addAttribute("nodesMap", nodesMap);
		model.addAttribute("url", url);
		if ("".equals(name.trim())) {
			MenuRegisterVO menu = munuRegisterService.loadMenuRegisterVOById(menuId);
			model.addAttribute("name", menu.getFunname());
		} else {
			model.addAttribute("name", name);
		}
		model.addAttribute("menuId", menuId);
		return "favorites/add";
	}

	@RequestMapping("/toAddFavoritesFile.d")
	public String toAddFavoritesFile(Model model, String menuId, String name) {
		Sorter sort = new Sorter(Direction.ASC, "createtime");
		String userid = AppContext.getContext().getContextUserId();
		Condition condition = new Condition();
		condition.addExpression("file_type", 1);
		condition.addExpression("createby", userid);
		Map<?, ?> nodesMap = favoritesService.queryFavoritesVOByCondReturnMap(condition, sort);
		model.addAttribute("nodesMap", nodesMap);
		model.addAttribute("menuId", menuId);
		model.addAttribute("name", name);
		return "favorites/addFile";
	}

	@RequestMapping("/checkUnique.d")
	@ResponseBody
	public boolean checkUnique(FavoritesVO favoritesVO) {
		return favoritesService.checkUnique(favoritesVO);
	}
}
