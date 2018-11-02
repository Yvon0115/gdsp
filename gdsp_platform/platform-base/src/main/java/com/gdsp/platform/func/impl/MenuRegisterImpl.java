package com.gdsp.platform.func.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gdsp.dev.base.exceptions.BusinessException;
import com.gdsp.dev.core.common.AppContext;
import com.gdsp.dev.core.common.IContextUser;
import com.gdsp.dev.core.model.param.Sorter;
import com.gdsp.dev.core.model.query.Condition;
import com.gdsp.dev.core.utils.tree.helper.TreeCodeHelper;
import com.gdsp.dev.persist.result.MapListResultHandler;
import com.gdsp.platform.config.customization.service.ISystemConfExtService;
import com.gdsp.platform.func.dao.IMenuRegisterDao;
import com.gdsp.platform.func.helper.FuncConst;
import com.gdsp.platform.func.helper.MenuConst;
import com.gdsp.platform.func.model.ButnRegisterVO;
import com.gdsp.platform.func.model.MenuRegisterVO;
import com.gdsp.platform.func.model.PageRegisterVO;
import com.gdsp.platform.func.service.IButnRegisterService;
import com.gdsp.platform.func.service.IMenuRegisterService;
import com.gdsp.platform.func.service.IPageRegisterService;
import com.gdsp.platform.grant.auth.model.PowerMgtVO;
import com.gdsp.platform.grant.auth.service.IPowerMgtQueryPubService;
import com.gdsp.platform.grant.auth.service.IUserRoleQueryPubService;
import com.gdsp.platform.grant.role.model.RoleVO;
import com.gdsp.platform.grant.user.model.UserVO;

@Transactional(readOnly = true)
@Service
public class MenuRegisterImpl implements IMenuRegisterService {

    @Autowired
    private IMenuRegisterDao         dao;
    @Autowired
    private IButnRegisterService     butnRegisterService;
    @Autowired
    private IPageRegisterService     pageRegisterService;
    @Autowired
    private IPowerMgtQueryPubService powerMgtQueryPubService;
    @Autowired
    private ISystemConfExtService    confExtService;
    @Autowired
    private IUserRoleQueryPubService userRoleQueryPubService;

    @Override
    @Transactional(readOnly = false)
    @CacheEvict(value = "menuRegister", allEntries = true)
    public void insertMenuRegister(MenuRegisterVO menuRegVo) {
        String parentId = menuRegVo.getParentid();
        if (!StringUtils.isEmpty(parentId)) {
            MenuRegisterVO parentMenuVo = dao.load(parentId);
            menuRegVo = (MenuRegisterVO) TreeCodeHelper.generateTreeCode(menuRegVo, parentMenuVo.getInnercode());
        } else {
            menuRegVo = (MenuRegisterVO) TreeCodeHelper.generateTreeCode(menuRegVo, null);
        }
        // 页面生成菜单默认编码等于内部码
        if (StringUtils.isEmpty(menuRegVo.getFuncode())) {
            menuRegVo.setFuncode(menuRegVo.getInnercode());
        }
        // 显示编码默认等于菜单编码
        if (StringUtils.isEmpty(menuRegVo.getDispcode())) {
            menuRegVo.setDispcode(menuRegVo.getFuncode());
        }
        dao.insert(menuRegVo);
    }

    @Override
    @Transactional(readOnly = false)
    @CacheEvict(value = "menuRegister", allEntries = true)
    public void updateMenuRegister(MenuRegisterVO menuRegVo) {
        dao.update(menuRegVo);
    }

    @Override
    @Transactional(readOnly = false)
    @CacheEvict(value = "menuRegister", allEntries = true)
    public void deleteMenuRegister(String id) {
        Condition condition = new Condition();
        condition.addExpression("parentid", id);
        List<MenuRegisterVO> list = queryMenuRegisterVOsByCond(condition, null);
        String isSystemMemu = isSystemMemu(id);
        List<ButnRegisterVO> butnVOs = butnRegisterService.queryButnByParentID(id);// 存在button
        /** ---- 查询更改  wqh 2016/12/22  原因：权限拆分 -------------------------*/
        // 更改前
        /**        List<PowerMgtVO> connectToRole = powerMgtService.isConnectToRole(id); */
        // 更改后
        List<PowerMgtVO> connectToRole = powerMgtQueryPubService.isConnectToRole(id);
        /**---------------------- 更改至此结束 ----------------------------------*/
        if (CollectionUtils.isNotEmpty(list)) {
            throw new BusinessException("存在子节点，请删除对应的子节点后再重新操作！");
        } else if ("Y".equalsIgnoreCase(isSystemMemu)) {
            throw new BusinessException("删除失败，为内置菜单，不可以删除！");
        } else if (CollectionUtils.isNotEmpty(butnVOs)) {	
            throw new BusinessException("存在对应的按钮，请删除对应的按钮后再重新操作！");
        }
        if (CollectionUtils.isNotEmpty(connectToRole)) {
            throw new BusinessException("菜单已被授权，请在角色管理中删除对应的授权后再重新操作！");
        }
        MenuRegisterVO menuVO = dao.load(id);
        // 页面菜单校验是否已有发布页
        if (MenuConst.MENUTYPE_PAGE.intValue() == menuVO.getFuntype()) {
            condition = new Condition();
            condition.addExpression("menuid", id);
            List<PageRegisterVO> pageVOs = pageRegisterService.queryPageRegisterListByCondition(condition, null);
            if (CollectionUtils.isNotEmpty(pageVOs)) {
                throw new BusinessException("删除失败，页面菜单已发布对应页面！");
            }
        }
        dao.delete(id);
    }

    @Override
    public MenuRegisterVO loadMenuRegisterVOById(String id) {
        return dao.load(id);
    }

    @Override
    public MenuRegisterVO loadMenuRegisterVOByUrl(String url) {
        MenuRegisterVO menu = dao.loadMenuByMenuUrl(url);
        if (menu == null)
            menu = dao.loadMenuByPageUrl(url);
        return menu;
    }

    @Override
    public List<MenuRegisterVO> queryMenuPaths(String menuId) {
        return dao.queryMenuPathByMenuId(menuId);
    }

    @Override
    public List<MenuRegisterVO> queryMenuRegisterVOsByCond(Condition condition,
            Sorter sort) {
        return dao.queryMenuRegisterVOListByCondition(condition, sort);
    }

    @Override
    public Map<String,List<MenuRegisterVO>> queryMenuRegisterVOsByCondReturnMap(Condition condition, Sorter sort) {
        MapListResultHandler<MenuRegisterVO> handler = new MapListResultHandler<>("parentid");
        dao.queryMenuRegisterVOMapListByCondition(condition, sort, handler);
        return handler.getResult();
    }

    @Override
    public Page<MenuRegisterVO> queryMenuRegisterVOsPages(Condition condition,
            Pageable page) {
        return dao.queryMenuRegisterVOsPages(condition, page);
    }

    @Override
    public List<MenuRegisterVO> queryParentMenuRegisters(Condition condition,
            Sorter sort) {
        return dao.queryMenuRegisterVOListByCondition(condition, sort);
    }

    @Override
    public List<String> queryMenuIdsByType234(String[] menuIds) {
        return dao.queryMenuIdsByType234(menuIds);
    }

    @Override
    public String isSystemMemu(String id) {
        return dao.isSystemMemu(id);
    }

    @Override
    @Transactional(readOnly = false)
    public void editParentMenu(String id, String currentParentId) {
        MenuRegisterVO menuRegVo = dao.load(id);
        String oldTreeCode = menuRegVo.getInnercode();
        if (!StringUtils.isEmpty(currentParentId)) {//选择了父菜单
            //更改内码
            MenuRegisterVO parentMenuVo = dao.load(currentParentId);
            //生成新的内码
            String innercode = TreeCodeHelper.generateTreeCode(menuRegVo.getTableName(), parentMenuVo.getInnercode());
            menuRegVo.setInnercode(innercode);
            //同步子菜单的内码
            TreeCodeHelper.synchronizeSubTree(menuRegVo, oldTreeCode);
            TreeCodeHelper.afterMoveNodeToParent(menuRegVo, parentMenuVo.getInnercode());
        } else {//没选父菜单
                //生成新的内码
            String innercode = TreeCodeHelper.generateTreeCode(menuRegVo.getTableName(), null);
            menuRegVo.setInnercode(innercode);
            //同步子菜单的内码
            TreeCodeHelper.synchronizeSubTree(menuRegVo, oldTreeCode);
        }
        menuRegVo.setParentid(currentParentId);
        dao.updateParentMenuId(menuRegVo);
    }

    @Override
    @Transactional
    @CacheEvict(value = "menuRegister", allEntries = true)
    public void deleteMenuRegisterByPageId(String pageId) {
        dao.deleteByPageId(pageId);
    }

    @Override
    public String queryMenuParentId(String menuId) {
        return dao.queryMenuParentId(menuId);
    }

    @Override
    public Map<String, List<MenuRegisterVO>> queryMenuMapByIds(Set<?> idSet) {
        MapListResultHandler<MenuRegisterVO> handler = new MapListResultHandler<>("parentid");
        dao.queryMenuMapByIds(idSet, handler);
        return handler.getResult();
    }

    @Override
    public boolean synchroCheck(String funname, String parentid) {
        
        return isUniqueName(funname, parentid, null);
    }

    @Override
    public boolean isUniqueName(String funname, String parentid, String id) {
        
        return dao.existSameName(funname, parentid, id) == 0;
    }

    @Transactional
    public void updateInnercode(String parentid) {
        Condition cd = new Condition();
        cd.addExpression("parentid", parentid);
        List<MenuRegisterVO> list = queryMenuRegisterVOsByCond(cd, null);
        if (CollectionUtils.isNotEmpty(list)) {
            MenuRegisterVO parentMenuVo = dao.load(parentid);
            Iterator<MenuRegisterVO> it = list.iterator();
            while (it.hasNext()) {
                MenuRegisterVO vo = it.next();
                vo = (MenuRegisterVO) TreeCodeHelper.generateTreeCode(vo, parentMenuVo.getInnercode());
                dao.updateInnercode(vo);
                updateInnercode(vo.getId());
            }
        }
    }

    @Override
    @Cacheable(value = "menuRegister", key = "#root.methodName")
    public List<MenuRegisterVO> queryAllMenuList() {
        return dao.queryAllMenuList();
    }

    @Override
    public int isPublished(String pageID) {
        return dao.loadPageID(pageID);
    }

    // TODO 须优化
    @Override
    public List<MenuRegisterVO> queryMenuListByCondForPower(String addCond, String userID) {
        // 权限时效过滤
        String agingStatus = confExtService.queryGrantAgingConfigs().getStatus();
        if ("Y".equalsIgnoreCase(agingStatus)) {
            List<RoleVO> rolevos = userRoleQueryPubService.queryFilteredRoleListByUserId(userID);
            List<String> roleids = new ArrayList<>();
            for (int i = 0; i < rolevos.size(); i++) {
                roleids.add(rolevos.get(i).getId());
            }
            List<String> menuIDs = powerMgtQueryPubService.queryPowerMgtByRoleIdList(roleids);
            Condition condition = new Condition();
            if (menuIDs == null) {      //若menuIDs为空，则需要实例化后才能添加元素  lijun 20170412
                menuIDs = new ArrayList<>();
                menuIDs.add("''");
            } else if (menuIDs.size() == 0) {
                menuIDs.add("''");
            }
            condition.addExpression("ma.id", menuIDs, "in");
            return dao.queryAgingFilteredMenuList(condition, addCond);
        } else {
            return dao.queryMenuListByCondForPower(addCond);
        }
    }

    @Override
    public MenuRegisterVO load(String menuID) {
        return dao.load(menuID);
    }
}
