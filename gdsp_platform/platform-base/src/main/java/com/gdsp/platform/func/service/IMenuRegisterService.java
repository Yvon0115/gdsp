package com.gdsp.platform.func.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.gdsp.dev.core.model.param.Sorter;
import com.gdsp.dev.core.model.query.Condition;
import com.gdsp.platform.func.model.MenuRegisterVO;

/**
 * @author zhouyu
 *
 */
public interface IMenuRegisterService {

	/**
	 * 根据菜单id加载菜单信息
	 * @param menuID
	 * @return
	 * @author lyf 2017.01.11
	 */
	public MenuRegisterVO load(String menuID);

	public void insertMenuRegister(MenuRegisterVO menuRegVo);

	public void updateMenuRegister(MenuRegisterVO menuRegVo);

	public void deleteMenuRegister(String id);

	public MenuRegisterVO loadMenuRegisterVOById(String id);

	/**
	 * 是否是系统预置菜单
	 * @author: jingf
	 * @CreateDate: 2015年10月14日 上午9:22:26
	 * @param id
	 * @return
	 */
	public String isSystemMemu(String id);

	/**
	 * 根据url加载菜单对象
	 * @param url  url串
	 * @return 菜单对象
	 */
	public MenuRegisterVO loadMenuRegisterVOByUrl(String url);

	/**
	 * 根据菜单id查询菜单路径
	 * @param menuId  菜单id
	 * @return 菜单路径列表
	 */
	public List<MenuRegisterVO> queryMenuPaths(String menuId);

	public List<MenuRegisterVO> queryMenuRegisterVOsByCond(Condition condition, Sorter sort);

	/**
	 * @Title: queryMenuIdsByType234 (类型为（管理菜单，业务菜单，页面菜单）的菜单)
	 * @param menuIds
	 * @return List<String> 返回值说明
	 */
	public List<String> queryMenuIdsByType234(String[] menuIds);

	public List<MenuRegisterVO> queryParentMenuRegisters(Condition condition, Sorter sort);

	public Page<MenuRegisterVO> queryMenuRegisterVOsPages(Condition condition, Pageable page);

	public Map<String, List<MenuRegisterVO>> queryMenuRegisterVOsByCondReturnMap(Condition condition, Sorter sort);

	/**
	 * 修改某菜单的父菜单，并重新生成内码
	 * @author: jingf
	 * @CreateDate: 2015年10月15日 上午10:44:54
	 * @param id 菜单ID
	 * @param parentid 菜单父ID
	 */
	public void editParentMenu(String id, String parentid);

	/**
	 * 根据pageId删除对应的所有菜单
	 * @param pageId 页面id
	 */
	void deleteMenuRegisterByPageId(String pageId);

	/**
	 * 查询父节点ID
	 * @param menuId 菜单ID
	 * @return String 父节点ID
	 */
	public String queryMenuParentId(String menuId);

	/**
	 * 菜单树
	 * @param idSet
	 * @return Map 菜单树
	 */
	public Map<String, List<MenuRegisterVO>> queryMenuMapByIds(Set<?> idSet);

	public boolean synchroCheck(String funname, String parentid);

	public boolean isUniqueName(String funname, String parentid, String id);

	/**
	 * 批量调整内部码
	 * @param parentid
	 */
	public void updateInnercode(String parentid);

	/**
	 * 查询所有注册菜单
	 * @return
	 */
	public List<MenuRegisterVO> queryAllMenuList();

	/** 
	 * 检查是否发布的菜单
	 * @param pageID
	 * @return
	 */
	public int isPublished(String pageID);

	/**
	 * @Title: queryMenuListByCondForPower
	 * @Description: 查询菜单通过条件字符串
	 * @param addCond
	 * @return
	 * @return List<MenuRegisterVO> 返回类型
	 */
	public List<MenuRegisterVO> queryMenuListByCondForPower(String addCond, String userID);

}
