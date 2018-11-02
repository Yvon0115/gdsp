package com.gdsp.platform.grant.org.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gdsp.dev.base.exceptions.BusinessException;
import com.gdsp.dev.core.model.query.Condition;
import com.gdsp.dev.core.utils.tree.helper.TreeCodeHelper;
import com.gdsp.platform.grant.auth.model.OrgPowerVO;
import com.gdsp.platform.grant.auth.service.IOrgPowerOptPubService;
import com.gdsp.platform.grant.auth.service.IOrgPowerService;
import com.gdsp.platform.grant.org.dao.IOrgDao;
import com.gdsp.platform.grant.org.model.OrgVO;
import com.gdsp.platform.grant.org.service.IOrgOptPubService;
import com.gdsp.platform.grant.org.service.IOrgQueryPubService;
import com.gdsp.platform.grant.org.service.IOrgService;
import com.gdsp.platform.grant.user.model.UserVO;
import com.gdsp.platform.grant.user.service.IUserQueryPubService;
import com.gdsp.platform.log.service.OpLog;

@Service
@Transactional(readOnly = false)
public class OrgOptPubServiceImpl implements IOrgOptPubService {

    @Autowired
    private IOrgDao                orgDao;
    @Autowired
    private IOrgService            orgService;
    @Autowired
    private IOrgQueryPubService    orgPubService;
    @Autowired
    private IOrgPowerService       orgPowerService;
    @Autowired
    private IOrgPowerOptPubService orgPowerOptPubService;
    @Autowired
    private  IUserQueryPubService  userQueryPubService;

    @OpLog
    @Override
    public void insert(OrgVO vo) {
        if (!orgService.uniqueNameCheck(vo)) {
            throw new BusinessException("已经存在的机构名称");
        }
        String parentId = vo.getPk_fatherorg();
        if (!StringUtils.isEmpty(parentId)) {
        	
        	//**lyf 2016.12.27修改 原因：权限拆分，修改了联表查询语句，返回的OrgVO没有了lastModifyByName和createByName**//*
            OrgVO parentVO = orgDao.load(parentId);
        //重构删除无用代码
/*          List<UserVO> uservos = userQueryPubService.findAllUsersList();
            for (UserVO uservo : uservos) {
                if(parentVO.getLastModifyBy() != null&&parentVO.getLastModifyBy().equalsIgnoreCase(uservo.getId())){
                    parentVO.setLastModifyByName(uservo.getUsername());
                }
                if(parentVO.getCreateBy().equalsIgnoreCase(uservo.getId())){
                    parentVO.setCreateByName(uservo.getUsername());
                }
            }
            *//**end**/
            vo = (OrgVO) TreeCodeHelper.generateTreeCode(vo, parentVO.getInnercode());
        } else {
            vo = (OrgVO) TreeCodeHelper.generateTreeCode(vo, null);
        }
        orgDao.insert(vo);
        //增加机构后，查询其上级机构有权限的角色，增加角色机构权限
        if (StringUtils.isNotEmpty(vo.getPk_fatherorg())) {
            Condition cond = new Condition();
            cond.addExpression("resource_id", vo.getPk_fatherorg());
            List<OrgPowerVO> powerVOs = orgPowerService.queryOrgPowerByCondition(cond);
            OrgPowerVO orgPowerVO;
            for (OrgPowerVO powerVO : powerVOs) {
                orgPowerVO = new OrgPowerVO();
                orgPowerVO.setPk_role(powerVO.getPk_role());
                orgPowerVO.setResource_id(vo.getId());
                orgPowerOptPubService.insert(orgPowerVO);
            }
        }
    }

    @OpLog
    @Override
    public void update(OrgVO vo){
        if (!orgService.uniqueNameCheck(vo)) {
            throw new BusinessException("已经存在的机构名称");
        }
        orgDao.update(vo);
    }

    @OpLog
    @Override
    public boolean deleteOrg(String[] ids){
    	//重构接口
        //Condition condition = new Condition();
        
        //List<OrgVO> orgList = orgService.queryChildOrgListById(ids[0], condition, null, false);
        List<OrgVO> orgList = orgPubService.queryChildOrgListById(ids[0]);
        if (orgList != null && orgList.size() > 0) {
            throw new BusinessException("机构存在下级，请先删除下级!");
        }
       // condition = new Condition();
       // List<UserVO> user = userService.queryUserByOrgId(ids[0], condition, new PageSerRequest(), null, false).getContent();
        List<UserVO> user = userQueryPubService.queryUserByOrgId(ids[0], null, null, null,false);
        if (user != null && user.size() > 0) {
            throw new BusinessException("机构存在用户，请先删除用户!");
        }
        orgDao.delete(ids);
        return true;
    }
}
