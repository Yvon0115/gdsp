package com.gdsp.platform.grant.user.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.jute.Index;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
//jingfeng@192.9.148.102:29418/gdsp_platform
import org.springframework.transaction.annotation.Transactional;

import com.gdsp.dev.base.exceptions.BusinessException;
import com.gdsp.dev.core.common.AppConfig;
import com.gdsp.dev.core.model.query.Condition;
import com.gdsp.dev.persist.result.MapListResultHandler;
import com.gdsp.dev.web.security.shiro.EncodePasswordService;
//jingfeng@192.9.148.102:29418/gdsp_platform
//jingfeng@192.9.148.102:29418/gdsp_platform
import com.gdsp.platform.grant.org.model.OrgVO;
import com.gdsp.platform.grant.org.service.IOrgQueryPubService;
import com.gdsp.platform.grant.user.dao.IUserDao;
import com.gdsp.platform.grant.user.model.UserDataIOVO;
import com.gdsp.platform.grant.user.model.UserVO;
import com.gdsp.platform.grant.user.service.IUserService;
import com.gdsp.platform.grant.utils.GrantConst;
import com.gdsp.platform.grant.utils.RegExpValidatorUtils;
import com.gdsp.platform.log.service.OpLog;

import net.sf.ehcache.search.aggregator.Sum;

@Service
@Transactional(readOnly = true)
public class UserServiceImpl implements IUserService {

	@Autowired
	private IUserDao userDao;
	@Autowired
	private IOrgQueryPubService orgPubService;
	@Resource
	private EncodePasswordService passwordService = null;

	/*
	 * @Transactional
	 * 
	 * @Override public boolean saveUser(UserVO vo) { // 根据是否有ID判定新增还是编辑维护 if
	 * (StringUtils.isNotEmpty(vo.getId())) { userDao.update(vo); } else { //
	 * 密码加密 String password = passwordService.encodePassword(vo.getAccount(),
	 * vo.getUser_password()); vo.setUser_password(password);
	 * userDao.insert(vo); } return true; }
	 * 
	 * @Override public Page<UserVO> queryUserByOrgId(String pk_org, Condition
	 * condition, Pageable page, Sorter sort, boolean containLower) { if
	 * (StringUtils.isEmpty(pk_org)) return null; // 增加机构查询条件 if (containLower)
	 * { OrgVO orgVO = orgPubService.load(pk_org); String innercode =
	 * orgVO.getInnercode(); condition.addExpression("u.pk_org",
	 * "(select id from rms_orgs where innercode like '" + innercode + "%' )",
	 * "in"); } else { condition.addExpression("u.pk_org", pk_org); } return
	 * userDao.queryUserByCondition(condition, page, sort); }
	 * 
	 * @Override public Page<UserVO> queryUserByCondition(Condition condition,
	 * Pageable page, Sorter sort) { return
	 * userDao.queryUserByCondition(condition, page, sort); }
	 * 
	 * @Override public List<UserVO> queryUserListByCondition(Condition
	 * condition, Sorter sort) { return
	 * userDao.queryUserListByCondition(condition, sort); }
	 */
	@Transactional
	@OpLog
	@Override
	public int importUsers(List<UserDataIOVO> vos) {
		UserVO userVO;
		// 机构
		Map<String, OrgVO> orgsMap = new HashMap<String, OrgVO>();
		StringBuilder accountMsg = new StringBuilder();
		StringBuilder orgMsg = new StringBuilder();
		StringBuilder userMsg = new StringBuilder();
		StringBuilder errMsg = new StringBuilder();
		int index = vos.size();
		for (int i = 0; i < vos.size(); i++) {
			userVO = new UserVO();
			// 用户账号
			String account = vos.get(i).getAccount();
			// 用户姓名
			String username = vos.get(i).getUsername();
			// 密码
			String userpassword = vos.get(i).getUser_password();
			// 手机
			String mobilephone = vos.get(i).getMobile();
			// 固定电话
			String telephone = vos.get(i).getTel();
			// 邮箱
			String email = vos.get(i).getEmail();
			// 性别
			String sex = vos.get(i).getSex();
			// 所属机构
			String pk_org = vos.get(i).getPk_org();
			// 机构编码
			String orgCode = vos.get(i).getOrgCode();
			// 描述
			String memo = vos.get(i).getMemo();
			// 判断是空行
			if (StringUtils.isEmpty(account) && StringUtils.isEmpty(username) && StringUtils.isEmpty(userpassword)
					&& StringUtils.isEmpty(mobilephone) && StringUtils.isEmpty(telephone) && StringUtils.isEmpty(email)
					&& StringUtils.isEmpty(sex) && StringUtils.isEmpty(pk_org) 
					&& StringUtils.isEmpty(orgCode) && StringUtils.isEmpty(memo)) {
				index --;
				continue;
			}
			if (account != null) {
				account = account.replace(" ", "");
			}
			if (StringUtils.isBlank(account)) {
				if (userMsg.length() > 0)
					userMsg.append(",用户账号为空:").append(username);
				else
					userMsg.append("用户账号为空:").append(username);
				continue;
			}
			// 校验账号重复性
			if (!isUniqueUser(vos.get(i).getAccount(), null)) {
				if (accountMsg.length() > 0)
					accountMsg.append(",账号已经存在:").append(account);
				else
					accountMsg.append("账号已经存在:").append(account);
				continue;
			}
			int accountLenght = vos.get(i).getAccount().length();
			if (accountLenght > 20) {
				if (accountMsg.length() > 0)
					accountMsg.append(",账号长度超过了20位:").append(account);
				else
					accountMsg.append("账号长度超过了20位:").append(account);
				continue;
			}
			userVO.setAccount(vos.get(i).getAccount());
			userVO.setEmail(vos.get(i).getEmail());
			userVO.setMobile(vos.get(i).getMobile());
			// 之前在导入用户时未加入固话，补加
			userVO.setTel(vos.get(i).getTel());
			userVO.setMemo(vos.get(i).getMemo());
			userVO.setAccount(vos.get(i).getAccount());
			userVO.setUsername(vos.get(i).getUsername());
			// 性别
			// String sex = vos.get(i).getSex();
			if (!StringUtils.isEmpty(sex) && GrantConst.USER_SEX_FEMALE_SHOW.equals(sex)) {
				userVO.setSex(GrantConst.USER_SEX_FEMALE);
			} else {
				userVO.setSex(GrantConst.USER_SEX_MALE);
			}
			// 设置默认值
			userVO.setUsertype(GrantConst.USERTYPE_USER);
			userVO.setOnlyadmin("N");
			userVO.setIslocked("N");
			userVO.setVersion(0);
			userVO.setOrigin("0");
			// 用户邮箱
			// String email = vos.get(i).getEmail();
			if (StringUtils.isNotBlank(email) && RegExpValidatorUtils.isNotEmail(email)) {
				if (userMsg.length() > 0)
					userMsg.append(",用户邮箱输入非法:").append(username);
				else
					userMsg.append("用户邮箱输入非法:").append(username);
			}
			// 用户座机号码
			// String telephone = vos.get(i).getTel();
			if (StringUtils.isNotBlank(telephone) && RegExpValidatorUtils.isNotTelephone(telephone)) {
				if (userMsg.length() > 0)
					userMsg.append(",用户座机号码输入非法:").append(username);
				else
					userMsg.append("用户座机号码输入非法:").append(username);
			}
			// 用户手机号码
			// String mobilephone = vos.get(i).getMobile();
			if (StringUtils.isNotBlank(mobilephone) && RegExpValidatorUtils.isNotMobilephone(mobilephone)) {
				if (userMsg.length() > 0)
					userMsg.append(",用户手机号码输入非法:").append(username);
				else
					userMsg.append("用户手机号码输入非法:").append(username);
			}

			// 用户姓名
			// String username = vos.get(i).getUsername();
			if (username != null) {
				username = username.replace(" ", "");
			}
			if (StringUtils.isBlank(username)) {
				if (userMsg.length() > 0)
					userMsg.append(",用户名称为空，账号为:").append(account);
				else
					userMsg.append("用户名称为空，账号为:").append(account);
				continue;
			} else if (vos.get(i).getUsername().length() > 60) {
				if (userMsg.length() > 0)
					userMsg.append(",用户名称长度超过60位,账号为:").append(account);
				else
					userMsg.append("用户名称长度超过60位,账号为:").append(account);
				continue;
			} else if (RegExpValidatorUtils.isValidName(vos.get(i).getUsername())) {
				if (userMsg.length() > 0)
					userMsg.append(",用户名称填写非法:").append(username);
				else
					userMsg.append("用户名称填写非法:").append(username);
				continue;
			}
			// 用户密码
			// String userpassword = vos.get(i).getUser_password();
			if (userpassword != null) {
				userpassword = userpassword.replace(" ", "");
			}
			if (StringUtils.isBlank(userpassword)) {
				if (userMsg.length() > 0)
					userMsg.append(",用户密码为空:").append(username);
				else
					userMsg.append("用户密码为空:").append(username);
				continue;
			}

			/////////////////////////////////////////////////////////////////////////////////////
			/// 总效验逻辑：按excel模板中列的顺序来效验，每一行中，只要有一个列下的值（单元格）不符合效验就退出该行的效验，开始下一个行的效验/////
			/// 优先效验：非空，非法，长度 /////
			/// 第二效验：机构名称效验在前，机构编码效验在后，如果机构名称确实存在，那么需要比较已经效验通过的机构名称是否与机构编码匹配
			///////////////////////////////////////////////////////////////////////////////////// /////
			/////////////////////////////////////////////////////////////////////////////////////

			// A、机构名称效验
			OrgVO org = orgsMap.get(vos.get(i).getPk_org());
			if (org == null) {
				// 1.用户所在机构名称是否正确
				if (StringUtils.isBlank(vos.get(i).getPk_org())) {
					if (orgMsg.length() > 0)
						orgMsg.append(",用户所属机构名称为空:").append(username);
					else
						orgMsg.append("用户所属机构名称为空:").append(username);
					continue;
				}

				if (vos.get(i).getPk_org().length() > 60) {
					if (orgMsg.length() > 0)
						orgMsg.append(",用户所属机构名称长度超过60位:").append(username);
					else
						orgMsg.append("用户所属机构名称长度超过60位:").append(username);
					continue;
				}

				if (RegExpValidatorUtils.isValidName(vos.get(i).getPk_org())) {
					if (orgMsg.length() > 0)
						orgMsg.append(",用户所属机构名称非法:").append(username);
					else
						orgMsg.append("用户所属机构名称非法:").append(username);
					continue;
				}

				// 2.查询用户所属机构名称
				List<OrgVO> orgvo = orgPubService.queryAllOrgList();
				List<OrgVO> orgVOs = new ArrayList<OrgVO>();
				for (OrgVO ovo : orgvo) {
					if (ovo.getOrgname().equalsIgnoreCase(vos.get(i).getPk_org())) {
						orgVOs.add(ovo);
					}
				}
				if (orgVOs != null && orgVOs.size() > 0) {
					org = orgVOs.get(0);
					orgsMap.put(vos.get(i).getOrgCode(), org);
				} else {
					if (orgMsg.length() > 0)
						orgMsg.append(",未找到相应用户所属机构名称:").append(username);
					else
						orgMsg.append("未找到相应用户所属机构名称:").append(username);
					continue;
				}

			}

			// B、机构编码效验
			OrgVO orgVO = orgsMap.get(vos.get(i).getOrgCode());
			if (orgVO == null) {
				if (StringUtils.isBlank(vos.get(i).getOrgCode())) {
					if (orgMsg.length() > 0)
						orgMsg.append(",用户所属机构编码为空:").append(username);
					else
						orgMsg.append("用户所属机构编码为空:").append(username);
					continue;
				}

				// 1.用户所在机构编码名称是否正确
				if (RegExpValidatorUtils.isValidName(vos.get(i).getOrgCode())) {
					if (orgMsg.length() > 0)
						orgMsg.append(",用户所属机构编码非法:").append(username);
					else
						orgMsg.append("用户所属机构编码非法:").append(username);
					continue;
				}

				if (vos.get(i).getOrgCode().length() > 20) {
					if (orgMsg.length() > 0)
						orgMsg.append(",用户所属机构编码长度超过20位:").append(username);
					else
						orgMsg.append("用户所属机构编码长度超过20位:").append(username);
					continue;
				}
				// 2.查询用户所属机构编码
				/** lyf 2016.12.23修改 权限拆分start **/
				List<OrgVO> orgvo = orgPubService.queryAllOrgList();
				List<OrgVO> orgVOs = new ArrayList<OrgVO>();
				for (OrgVO ovo : orgvo) {
					if (ovo.getOrgcode().equalsIgnoreCase(vos.get(i).getOrgCode())) {
						if (ovo.getOrgname().equalsIgnoreCase(vos.get(i).getPk_org())) {
							orgVOs.add(ovo);
						}
					}
				}
				/** end **/

				if (orgVOs != null && orgVOs.size() > 0) {
					orgVO = orgVOs.get(0);
					orgsMap.put(vos.get(i).getOrgCode(), orgVO);
				} else {
					if (orgMsg.length() > 0)
						orgMsg.append(",未找到相应用户所属机构编码:").append(username);
					else
						orgMsg.append("未找到相应用户所属机构编码:").append(username);
					continue;
				}
			} else {
				// 第二效验
				// 1.用户所在机构编码名称是否正确
				if (StringUtils.isBlank(vos.get(i).getOrgCode())) {
					if (orgMsg.length() > 0)
						orgMsg.append(",用户所属机构名称为空:").append(username);
					else
						orgMsg.append("用户所属机构名称为空:").append(username);
					continue;
				}

				if (RegExpValidatorUtils.isValidName(vos.get(i).getOrgCode())) {
					if (orgMsg.length() > 0)
						orgMsg.append(",用户所属机构编码非法:").append(username);
					else
						orgMsg.append("用户所属机构编码非法:").append(username);
					continue;
				}

				if (vos.get(i).getOrgCode().length() > 20) {
					if (orgMsg.length() > 0)
						orgMsg.append(",用户所属机构编码长度超过20位:").append(username);
					else
						orgMsg.append("用户所属机构编码长度超过20位:").append(username);
					continue;
				}
				// 2.查询用户所属机构编码 编码和名称都存在
				/** lyf 2016.12.23修改 权限拆分start **/
				List<OrgVO> orgvo = orgPubService.queryAllOrgList();
				List<OrgVO> orgVOs = new ArrayList<OrgVO>();
				for (OrgVO ovo : orgvo) {
					if (ovo.getOrgcode().equalsIgnoreCase(vos.get(i).getOrgCode())) {
						if (ovo.getOrgname().equalsIgnoreCase(vos.get(i).getPk_org())) {
							orgVOs.add(ovo);
						}
					}
				}
				/** end **/

				// 此逻辑之前，名称已经通过优先效验，编码也通过优先效验，存在的名称和编码是否一致通过是否为null来判断
				if (orgVOs != null && orgVOs.size() > 0) {
					orgVO = orgVOs.get(0);
					orgsMap.put(vos.get(i).getOrgCode(), orgVO);
				} else {
					if (orgMsg.length() > 0)
						orgMsg.append(",用户所属机构名称与用户所属机构编码不一致:").append(username);
					else
						orgMsg.append("用户所属机构名称与用户所属机构编码不一致:").append(username);
					continue;
				}

			}

			// C、机构名称和机构编码是否匹配效验
			if (org != null && (!org.getOrgcode().equals(vos.get(i).getOrgCode()))) {
				if (orgMsg.length() > 0)
					orgMsg.append(",用户所属机构名称与用户所属机构编码不一致:").append(username);
				else
					orgMsg.append("用户所属机构名称与用户所属机构编码不一致:").append(username);
				continue;
			}

			userVO.setPk_org(orgVO.getId());
			if (StringUtils.isBlank(vos.get(i).getUser_password())) {
				String strPsw = AppConfig.getInstance().getString("portal.default.password");
				strPsw = userVO.getAccount() + strPsw;
				vos.get(i).setUser_password(strPsw);
			}
			// 密码加密
			String password = passwordService.encodePassword(userVO.getAccount(), vos.get(i).getUser_password());
			userVO.setUser_password(password);
			userVO.setIsdisabled("N");
			userVO.setIslocked("N");
			userVO.setIsreset("N");
			userDao.insert(userVO);
			vos.get(i).setUserID(userVO.getId());
		}

		if (accountMsg.length() > 0) {
			errMsg.append(accountMsg.toString());
		}
		if (orgMsg.length() > 0) {
			if (errMsg.length() > 0) {
				errMsg.append("\r\n");
			}
			errMsg.append(orgMsg.toString());
		}
		if (userMsg.length() > 0) {
			if (errMsg.length() > 0) {
				errMsg.append("\r\n");
			}
			errMsg.append(userMsg.toString());
		}
		if (errMsg.length() > 0) {
			throw new BusinessException("导入用户失败，原因如下：\r\n" + errMsg);
		}
		return index;
	}

	@Override
	public boolean isUniqueUser(String account, String id) {
		return userDao.existSameUser(account, id) == 0;
	}

	@Override
	public Page<UserVO> queryUserPreByIds(String[] userIds, String[] userGroupIds, String[] roleIds, String[] orgIds,
			Pageable pageable) {
		if (userIds.length == 0) {
			userIds = new String[1];
			userIds[0] = "''";
		}
		if (userGroupIds.length == 0) {
			userGroupIds = new String[1];
			userGroupIds[0] = "''";
		}
		if (roleIds.length == 0) {
			roleIds = new String[1];
			roleIds[0] = "''";
		}
		if (orgIds.length == 0) {
			orgIds = new String[1];
			orgIds[0] = "''";
		}
		return userDao.queryUserPreByIds(userIds, userGroupIds, roleIds, orgIds, pageable);
	}
	/*
	 * @Override public Page<UserVO> queryUserPageByUser(String userID, Pageable
	 * pageable, boolean containSelf) { return
	 * queryUserPageByUserAndCond(userID, pageable, containSelf, null); }
	 * 
	 * @Override public Page<UserVO> queryUserPageByUserAndCond(String userId,
	 * Pageable pageable, boolean containSelf, String addCond) { if
	 * (StringUtils.isBlank(userId)) { return null; } UserVO userVO =
	 * userDao.load(userId); if (StringUtils.isBlank(addCond)) { addCond =
	 * "  u.usertype = " + GrantConst.USERTYPE_USER; } else addCond +=
	 * " and u.usertype = " + GrantConst.USERTYPE_USER; if (!containSelf) {
	 * addCond += " and u.id <>'" + userId + "' "; } // 根据用户类型设置条件查询 if
	 * (userVO.getUsertype() != GrantConst.USERTYPE_ADMIN) { // 用户有管理权限的机构用户
	 * addCond += " and " + GrantUtils.getOrgPowerCondByUser(userId,
	 * "u.pk_org"); } return userDao.queryUserPageByAddCond(null, addCond,
	 * pageable); }
	 * 
	 * @Override public List<UserVO> queryUserListByUser(String userId, boolean
	 * containSelf) { return queryUserListByUserAndCond(userId, containSelf,
	 * null); }
	 * 
	 * @Override public List<UserVO> queryUserListByUserAndCond(String userId,
	 * boolean containSelf, String addCond) { if (StringUtils.isBlank(userId)) {
	 * return null; } UserVO userVO = userDao.load(userId); if
	 * (StringUtils.isBlank(addCond)) { addCond = "  u.usertype = " +
	 * GrantConst.USERTYPE_USER; } else addCond += " and u.usertype = " +
	 * GrantConst.USERTYPE_USER; if (!containSelf) { addCond += " and u.id <>'"
	 * + userId + "' "; } // 根据用户类型设置条件查询 if (userVO.getUsertype() !=
	 * GrantConst.USERTYPE_ADMIN) { // 用户有管理权限的机构用户 addCond += " and " +
	 * GrantUtils.getOrgPowerCondByUser(userId, "u.pk_org"); } return
	 * userDao.queryUserListByAddCond(addCond); }
	 */

	@Override
	public boolean synchroCheck(String account) {
		return isUniqueUser(account, null);
	}
	/*
	 * @Override public Page<UserVO> queryUserPageByAddCond(Condition condition,
	 * String addCond, Pageable page) { return
	 * userDao.queryUserPageByAddCond(condition, addCond, page); }
	 * 
	 * @Override public Page<UserVO> queryUserByIds(String[] userIds, Pageable
	 * page) { return userDao.queryUserByIds(userIds, page); }
	 * 
	 * @Override public Map queryUserByUserIds(String[] userIds) {
	 * 
	 * @SuppressWarnings("rawtypes") MapListResultHandler handler = new
	 * MapListResultHandler("id"); userDao.queryUserByUserIds(handler, userIds);
	 * return handler.getResult(); }
	 */
	
	//用户同步接口及任务调用
	@Override
	public List<UserVO> queryUserByAccountList(List<String> accountList) {
		return userDao.findUserByAccountList(accountList);
	}
}
