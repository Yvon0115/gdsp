package com.gdsp.platform.grant.user.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gdsp.dev.base.exceptions.BusinessException;
import com.gdsp.dev.base.lang.DDateTime;
import com.gdsp.dev.core.model.query.Condition;
import com.gdsp.dev.web.security.shiro.EncodePasswordService;
import com.gdsp.platform.grant.auth.dao.IOrgPowerDao;
import com.gdsp.platform.grant.auth.dao.IPowerMgtDao;
import com.gdsp.platform.grant.auth.dao.IUserRoleDao;
import com.gdsp.platform.grant.auth.model.OrgPowerVO;
import com.gdsp.platform.grant.auth.model.PowerMgtVO;
import com.gdsp.platform.grant.auth.service.IUserGroupRltService;
import com.gdsp.platform.grant.auth.service.IUserRoleQueryPubService;
import com.gdsp.platform.grant.role.model.RoleVO;
import com.gdsp.platform.grant.user.dao.IUserDao;
import com.gdsp.platform.grant.user.model.UserVO;
import com.gdsp.platform.grant.user.service.IUserOptPubService;
import com.gdsp.platform.log.service.OpLog;

@Service
@Transactional(readOnly = false)
public class UserOptPubServiceImpl implements IUserOptPubService {

    @Autowired
    private IUserDao              userDao;
    @Autowired
    private IUserRoleDao          userRoleDao;
    @Autowired
    private IOrgPowerDao          orgPowerDao;
    @Autowired
    private IPowerMgtDao          powerMgtDao;
    @Autowired
    private EncodePasswordService passwordService = null;
    @Autowired
    private IUserGroupRltService  userGroupRltService;
    @Autowired
    private IUserRoleQueryPubService userRoleQueryPubService;
    
    @OpLog
    @Override
    public void insert(UserVO vo) {
        userDao.insert(vo);
    }

    @OpLog
    @Override
    public void update(UserVO vo) {
        userDao.update(vo);
    }

    @OpLog
    @Override
    public boolean lockUser(String[] id) {
        userDao.lockUser(id);
        return true;
    }

    @OpLog
    @Override
    public boolean unlockUser(String[] id) {
        userDao.unlockUser(id);
        return true;
    }

    @OpLog
    @Override
    public String deleteUser(String id) {
        String check = checkUserForDelete(id);
        if (check.isEmpty()) {
            userDao.delete(id);
            return "";
        } else {
            return check.substring(0, check.length() - 1);
        }
    }

    @OpLog
    @Override
    public void transOrg(String userID, String orgID) {
        if (StringUtils.isEmpty(userID))
            return;
        if (StringUtils.isEmpty(orgID))
        	throw new BusinessException("变更失败，不允许此操作！");
        UserVO user = userDao.load(userID);
        // 校验账号重复性
        if (orgID.equals(user.getPk_org()))
            throw new BusinessException("变更前后机构一致！");
        // 删除用户关联角色
        userRoleDao.removeByUser(new String[] { userID });
        // 删除用户关联机构信息
        orgPowerDao.deleteRoleOrgPower(new String[] { userID });
        // 删除用户关联菜单信息
        powerMgtDao.deleteRoleMenuPower(new String[] { userID });
        // 删除用户关联页面信息
        powerMgtDao.deleteRolePagePower(new String[] { userID });
        // 用户机构变更
        user.setPk_org(orgID);
        userDao.transOrg(user);
        return;
    }

    @OpLog
    @Override
    public void resetUserPasssword(UserVO vo) {
        // 密码加密
        String password = passwordService.encodePassword(vo.getAccount(), vo.getUser_password());
        vo.setUser_password(password);
        //重置状态为Y
        vo.setIsreset("Y");
        userDao.resetUserPasssword(vo);
    }

    @OpLog
    @Override
    public void resetPersonalInf(UserVO user) {
        UserVO vo = userDao.load(user.getId());
        DDateTime current = new DDateTime();    
        if (!vo.getUser_password().equals(user.getUser_password())) {                  
            String password = passwordService.encodePassword(user.getAccount(), user.getUser_password());
            user.setUser_password(password);
            user.setUpdate_pwd_time(current);            
        }
        user.setLastModifyTime(current);//设置最后修改人和最后修改时间信息    lijun   20170428
        user.setLastModifyBy(user.getId());
        userDao.resetPersonalInf(user);
    }

    private String checkUserForDelete(String id) {
        String reslut = "";
        
//        List<RoleVO> userVo = userRoleDao.queryRoleByUserId(id, new PageSerRequest()).getContent();
        List<RoleVO> userVo = userRoleQueryPubService.queryRoleListByUserId(id);
        if (userVo != null && userVo.size() > 0) reslut += "角色、";    //用户存在关联的角色
        Condition cond = new Condition();
        cond.addExpression("m.pk_role", id);
        List<PowerMgtVO> powerMgtVO = powerMgtDao.queryMenuRoleListByRoleId(cond, null);
        if (powerMgtVO != null && powerMgtVO.size() > 0) reslut += "菜单、";   //用户存在关联的菜单
        cond = new Condition();
        cond.addExpression("pk_role", id);
        List<OrgPowerVO> orgPowerVO = orgPowerDao.queryOrgPowerByCondition(cond);
        if (orgPowerVO != null && orgPowerVO.size() > 0) reslut += "机构、";    //用户存在关联的机构
//        cond = new Condition();
//        cond.addExpression("p.pk_role", id);
//        int pagePowerVO = powerMgtDao.getNumPageUserByAddCond(cond);
//        if (pagePowerVO > 0) reslut += "页面、";    //用户存在关联的页面，请清空后再试
        List<UserVO> userVO = userGroupRltService.queryGroupByUser(id);
        if (userVO != null && userVO.size() > 0)
            reslut += "用户组、";
        return reslut;
    }

    @OpLog
	@Override
	public void updateUserPasssword(UserVO user) {
        // 密码加密
        String password = passwordService.encodePassword(user.getAccount(), user.getUser_password());
        user.setUser_password(password);
        //重置状态为N
        user.setIsreset("N");
        //用户修改密码时间
        user.setUpdate_pwd_time(new DDateTime());
        userDao.updateUserPasssword(user);
	}
    
    @Override
    @CacheEvict(value="passwordRetryCache",key="#username")
    public void clearCache(String username){
        
    }
    @OpLog
    @Override
    public boolean disable(String[] id) {
        userDao.disable(id);
        return true;
    }
    @OpLog
    @Override
    public boolean enable(String[] id) {
        userDao.enable(id);
        return true;
    }
}
