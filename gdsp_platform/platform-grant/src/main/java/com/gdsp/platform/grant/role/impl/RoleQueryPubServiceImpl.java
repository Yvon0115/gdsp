package com.gdsp.platform.grant.role.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gdsp.dev.core.common.AppContext;
import com.gdsp.dev.core.model.param.Sorter;
import com.gdsp.dev.core.model.query.Condition;
import com.gdsp.platform.grant.auth.service.IOrgPowerQueryPubService;
import com.gdsp.platform.grant.org.model.OrgVO;
import com.gdsp.platform.grant.org.service.IOrgQueryPubService;
import com.gdsp.platform.grant.role.dao.IRoleDao;
import com.gdsp.platform.grant.role.model.RoleVO;
import com.gdsp.platform.grant.role.service.IRoleQueryPubService;
import com.gdsp.platform.grant.role.service.IRoleService;

@Service
@Transactional(readOnly = true)
public class RoleQueryPubServiceImpl implements IRoleQueryPubService {

	@Autowired
	private IRoleDao roleDao;
	@Autowired
	private IOrgQueryPubService orgPubService;
	@Autowired
	private IRoleService roleService;
	@Autowired
	private IOrgPowerQueryPubService orgPowerQueryPubService;

	@Override
	public RoleVO load(String id) {
		RoleVO vo = roleDao.load(id);
		// 以下代码是查询机构名称
		if (vo != null && !StringUtils.isEmpty(vo.getPk_org())) {
			OrgVO orgVo = orgPubService.load(vo.getPk_org());
			vo.setOrgname(orgVo.getOrgname());
		}
		return vo;
	}

	@Override
	public List<RoleVO> queryRoleListByOrgIds(List list) {
		return roleDao.queryRoleListByOrgIds(list);
	}

	@Override
	public List<RoleVO> queryRoleListByOrgId(RoleVO cond, boolean isContainChild) {
		if (StringUtils.isEmpty(cond.getPk_org()))
			return null;
		// 增加条件：是否包含下级机构角色
		Condition condition = new Condition();
		List<String> orgsId = new ArrayList<>();
		if (isContainChild) {
			/** lyf 2016.12.23修改 权限拆分start **/
			List<OrgVO> orgVOs = orgPubService.querySelftAndChildOrgListById(cond.getPk_org());
			/** end **/
			String userId = AppContext.getContext().getContextUserId();
			List<OrgVO> orgListByUser = orgPowerQueryPubService.queryOrgListByUser(userId);
			orgVOs.retainAll(orgListByUser);
			for(OrgVO orgVO :orgVOs){
				orgsId.add(orgVO.getId());
			}
		} else {
			orgsId.add(cond.getId());
		}
		condition.addExpression("pk_org", orgsId, "in");
		if (StringUtils.isNotBlank(cond.getRolename())) {
			condition.addExpression("rolename", cond.getRolename() + "%", "like");
		}
		if (StringUtils.isNotBlank(cond.getMemo())) {
			condition.addExpression("memo", cond.getMemo() + "%", "like");
		}
		Sorter sort = new Sorter(Direction.ASC, "rolename");
		List<RoleVO> roleList = roleDao.queryRoleListByCondition(condition, sort);
		// set orgname
		if (roleList != null && roleList.size() > 0) {
			List<String> orgids = new ArrayList<String>();
			for (RoleVO roleVO : roleList) {
				orgids.add(roleVO.getPk_org());
			}
			List<OrgVO> orgVOs = orgPubService.queryOrgListByIDs(orgids);
			if (orgVOs != null && orgVOs.size() > 0) {
				for (RoleVO roleVO : roleList) {
					for (OrgVO orgVO : orgVOs) {
						if (roleVO.getPk_org().equals(orgVO.getId())) {
							roleVO.setOrgname(orgVO.getOrgname());
							break;
						}
					}
				}
			}
		}
		return roleList;
	}

	@Override
	public List<Map<String, String>> queryRoleList(String OrgId) {
		List<Map<String, String>> queryRoleList = roleDao.queryRoleList(OrgId);
		return queryRoleList;
	}

	@Override
	public List<RoleVO> findAllRoleList() {
		return roleDao.findAllRoleList();
	}

	/** 模糊查询 - 根据条件查询角色列表 */
	@Override
	public List<RoleVO> fuzzySearchRoleListByCondition(String roleName) {
		Condition condition = new Condition();
		if (roleName != null) {
			condition.addExpression("ROLENAME", roleName, "like");
		}
		Sorter sort = new Sorter(Direction.ASC, "ROLENAME");
		return roleService.queryRoleListByCondition(condition, sort);
	}

	/** 根据id查询角色 */
	@Override
	public List<RoleVO> queryRoleListByRoleIds(List roleIds) {
		Condition condition = new Condition();
		if (roleIds != null&&roleIds.size()>0) {
			condition.addExpression("id", roleIds, "in");
		}
		Sorter sort = new Sorter(Direction.ASC, "ROLENAME");
		return roleService.queryRoleListByCondition(condition, sort);
	}
}
