package com.gdsp.platform.grant.org.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gdsp.dev.core.model.param.Sorter;
import com.gdsp.dev.core.model.query.Condition;
import com.gdsp.platform.grant.org.dao.IOrgDao;
import com.gdsp.platform.grant.org.model.OrgVO;
import com.gdsp.platform.grant.org.service.IOrgQueryPubService;
import com.gdsp.platform.grant.org.service.IOrgService;
import com.gdsp.platform.grant.user.dao.IUserDao;
import com.gdsp.platform.grant.user.model.UserVO;
import com.gdsp.platform.grant.user.service.IUserQueryPubService;

@Service
@Transactional(readOnly = true)
public class OrgQueryPubServiceImpl implements IOrgQueryPubService {

    @Autowired
    private IOrgDao orgDao;
    @Autowired
    private IOrgService orgService;
    @Autowired
    private IUserQueryPubService userQueryPubService;
    @Autowired
    private IUserDao userDao;
    
    
    @Override
    public OrgVO load(String id) {
        /**lyf 2016.12.27修改 原因：权限拆分，修改了联表查询语句，返回的OrgVO没有了lastModifyByName和createByName**/
        OrgVO orgvo = orgDao.load(id);
        String lastModifyUserID = orgvo.getLastModifyBy();
//        UserVO modifyuser = userQueryPubService.load(lastModifyBy);  // 接口间互相调用造成冲突
        UserVO modifyuser = userDao.load(lastModifyUserID);
        if(modifyuser != null){
        	orgvo.setLastModifyByName(modifyuser.getUsername());
        }
        String createUserID = orgvo.getCreateBy();
        UserVO createuser = userDao.load(createUserID);
        if(createuser != null){
        	orgvo.setCreateByName(createuser.getUsername());
        }
        return orgvo;
        /**end**/
//        return orgDao.load(id);
    }

    @Override
    public List<OrgVO> queryAllOrgList() {
        return orgDao.queryAllOrgList();
    }

    @Override
    public List<OrgVO> queryOrgListByIDs(List list) {
    	if(list.size()==0){
    		list.add("");
    	}
        return orgDao.queryOrgListByIDs(list);
    }

    //重构如下代码
/*    *//**lyf 2016.12.23修改 权限拆分 start**//*
    @Override
    public List<OrgVO> queryChildOrgListById(String orgID,boolean containItself) {
        String innercode = null;
        //查询机构，查出innercode参数，以便查询其子机构
        //OrgVO orgVO = orgDao.load(orgID);
        *//**lyf 2016.12.27修改 原因：权限拆分，修改了联表查询语句，返回的OrgVO没有了lastModifyByName和createByName**//*
        OrgVO orgVO = orgDao.load(orgID);
        List<UserVO> uservos = userQueryPubService.findAllUsersList();
        for (UserVO uservo : uservos) {
            if(orgVO.getLastModifyBy() != null&&orgVO.getLastModifyBy().equalsIgnoreCase(uservo.getId())){
                orgVO.setLastModifyByName(uservo.getUsername());
            }
            if(orgVO.getCreateBy().equalsIgnoreCase(uservo.getId())){
                orgVO.setCreateByName(uservo.getUsername());
            }
        }
        *//**end**//*
        if (orgVO != null) {
            innercode = orgVO.getInnercode();
        }
        List<OrgVO> orgvo = orgDao.queryOrgListByOrgIdAndInnercode(innercode);
        if (!containItself) {
            orgvo.remove(orgVO);
        }
        return orgvo;
    }
*/
    @Override
    public List<OrgVO> queryOrgListByCondition(String orgname) {
        Condition condition = new Condition();
        condition.addExpression("ORGNAME", orgname, "like");
        Sorter sort = new Sorter("innercode");;
        return orgService.queryOrgListByCondition(condition, sort);
    }
    /**end**/

    @Override
    public List<OrgVO> findAllOrgList() {
    	return orgDao.findAllOrgList();
    }

	@Override
	public List<OrgVO> queryChildOrgListById(String orgID) {
        List<OrgVO> orgvo = null;
        List<String> userIds = new ArrayList<String>();
        
        //查询机构，查出innercode参数，以便查询其子机构 
        OrgVO orgVO = orgDao.load(orgID);
        if (orgVO != null) {
            orgvo = orgDao.queryChildOrgListByOrgInnercode(orgVO.getInnercode());
        }
        
        return this.updateUserInfo(orgvo);
	}

	@Override
	public List<OrgVO> querySelftAndChildOrgListById(String orgID) {
        List<OrgVO> orgvo = null;
        
        
        //查询机构，查出innercode参数，以便查询其子机构 
        OrgVO orgVO = orgDao.load(orgID);
        if (orgVO != null) {
            orgvo = orgDao.querySelfAndChildOrgListByInnercode(orgVO.getInnercode());
        }
        
        return this.updateUserInfo(orgvo);
	}
	
	/**
	 * 更新用户信息，由于机构用户表分离，不能关联查询
	 * @param orgvo
	 * @return
	 */
	private List<OrgVO> updateUserInfo(List<OrgVO> orgvo){
		List<String> userIds = new ArrayList<String>();
		
		if(orgvo != null){
        	for(OrgVO vo : orgvo){
        		if(StringUtils.isNotEmpty(vo.getCreateBy())){
        			userIds.add(vo.getCreateBy());
        		}
        		if(StringUtils.isNotEmpty(vo.getLastModifyBy())){
        			userIds.add(vo.getLastModifyBy());
        		}
        	}
        }

        //查询机构需要关联的用户
        Map<String, List<UserVO>> userMap = userQueryPubService.queryUserByUserIds((userIds.toArray(new String [0])));
        
        if(orgvo != null && userMap != null){
        	for(OrgVO vo : orgvo){
        		if(userMap.get(vo.getCreateBy()) != null){
        			vo.setCreateByName(userMap.get(vo.getCreateBy()).get(0).getUsername());
        		}
        		if(userMap.get(vo.getLastModifyBy()) != null){
        			vo.setLastModifyByName(userMap.get(vo.getLastModifyBy()).get(0).getUsername());
        		}
        	}
        }        

        return orgvo;
	}
}
