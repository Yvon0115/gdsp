package com.gdsp.ptbase.portal.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gdsp.platform.grant.auth.model.UserDefaultPageVO;
import com.gdsp.platform.grant.auth.service.IPowerMgtQueryPubService;
import com.gdsp.ptbase.portal.dao.IPortalPageDao;
import com.gdsp.ptbase.portal.service.IPortalPageHandler;

@Service
@Transactional(readOnly = false)
public class PortalPageHandlerImpl implements IPortalPageHandler{

	@Autowired
	private IPortalPageDao portalPageDao;
	
	@Autowired
	private IPowerMgtQueryPubService powerMgtQueryPubService;
	
	
	@Override
	public Map<String, Object> prepareData(String user) {
		return null;
	}

	@Override
	public boolean setDefaultHomePage(String userID, String pageID,
			String menuID) {
		List<UserDefaultPageVO> udps = powerMgtQueryPubService.findDefaultPageByUser(userID);
		
		UserDefaultPageVO vo = new UserDefaultPageVO();
		vo.setPk_user(userID);
		vo.setPage_id(pageID);
		vo.setMenu_id(menuID);
		// 如果前台传过来的菜单ID与表中的菜单ID相同，说明这个菜单之前设置过默认首页
		boolean hasDefault = false;
		for (int i = 0; i < udps.size(); i++) {
			UserDefaultPageVO row = udps.get(i);
			if (menuID.equals(row.getMenu_id())) {
				hasDefault = true;
				break;
			}
		}
		if (hasDefault) {
			portalPageDao.updateUserHomePage(vo);
		}else {
			portalPageDao.insert(vo);
		}
		
		return false;
	}

	@Override
	public boolean revokeDefaultHomePage(String userID, String pageID,
			String menuID) {
		
		
		
		return false;
	}

}
