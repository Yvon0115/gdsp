/**
 * 
 */
package com.gdsp.portal.sso.user.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gdsp.dev.base.exceptions.BusinessException;
import com.gdsp.dev.base.lang.DBoolean;
import com.gdsp.dev.base.lang.DDateTime;
import com.gdsp.dev.core.common.AppConfig;
import com.gdsp.dev.persist.dao.ICrudDao;
import com.gdsp.dev.persist.impl.CrudService;
import com.gdsp.platform.grant.user.service.IUserOptPubService;
import com.gdsp.platform.grant.user.service.IUserQueryPubService;
import com.gdsp.platform.grant.utils.GrantConst;
import com.gdsp.portal.sso.user.dao.IUserSyncTmpDao;
import com.gdsp.portal.sso.user.model.UserSyncDataVO;
import com.gdsp.portal.sso.user.service.IUserSyncPubService;
import com.gdsp.portal.sso.user.service.IUserSyncTmpService;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 用户同步接口实现类
 * @author yucl
 * @version 3.0 2018-2-7
 * @since 1.6
 */
@Service
@Transactional
public class UserSyncPubServiceImpl extends CrudService<UserSyncDataVO> implements IUserSyncPubService {
	
	/**
	 * 日志记录异常写入文件
	 */
	private static final Logger logger = LoggerFactory.getLogger(UserSyncPubServiceImpl.class);
	
	/**
	 * 查询错误状态码
	 */
	public static final int        QUERYTMP_ERROR          = -1;
	/**
     * 插入错误状态码
     */
    public static final int        INSERTING_ERROR          = -2;
    /**
     * 更新错误状态码
     */
    public static final int        UPDATEING_ERROR          = -3;
    /**
     * 删除错误状态码
     */
    public static final int        DELETING_ERROR          = -4;
	
	@Resource
	private IUserSyncTmpDao userSyncTmpDao;
	@Resource
	private IUserQueryPubService userQueryPubService;
	@Resource
	private IUserOptPubService userOptPubService;
	@Resource
	private IUserSyncTmpService userSyncTmpService;
	
	//读取属性文件中属性
	private final String userId =  AppConfig.getProperty("syncUser.userId");
	private final String account =  AppConfig.getProperty("syncUser.account");
	private final String name =  AppConfig.getProperty("syncUser.name");
	private final String isLocked =  AppConfig.getProperty("syncUser.isLocked");
	private final String isDisabeled = AppConfig.getProperty("syncUser.isDisabeled");
	private final String mobile = AppConfig.getProperty("syncUser.mobile");
	private final String tel = AppConfig.getProperty("syncUser.tel");
	private final String email = AppConfig.getProperty("syncUser.email");
	private final String sex = AppConfig.getProperty("syncUser.sex");
	private final String memo = AppConfig.getProperty("syncUser.memo");
	private final String user_createTime = AppConfig.getProperty("syncUser.createTime");
	private final String user_lastModifyTime = AppConfig.getProperty("syncUser.lastModifyTime");
//	private final String usertype = AppConfig.getProperty("syncUser.usertype");
	
	@Override
	public ICrudDao<UserSyncDataVO> getDao() {
		return this.userSyncTmpDao;
	}

	@Override
	public Boolean synchronizeUserData(List<?> userList) {
		if(null == userList){
			return false;
		}
		
		List<UserSyncDataVO> allTmpUsers = null;
		try {
			allTmpUsers = userSyncTmpService.queryAllTmpUsers();
		} catch (Exception e1) {
			logger.error(e1.getMessage()+"查询临时表异常，请检查sql正确性！",e1);
			throw new BusinessException(UserSyncPubServiceImpl.QUERYTMP_ERROR, "内部错误，请联系管理员！", e1);
		}	
		
		/* 分析同步数据 */
		List<String> originAccountList = new ArrayList<>();
		List<UserSyncDataVO> userTmpList = new ArrayList<>();
		//将传入得bean对象转换成json对象
		JSONArray fromObject = JSONArray.fromObject(userList);
		ListIterator<?> listIterator = fromObject.listIterator();
		while(listIterator.hasNext()){
			JSONObject currentUser = (JSONObject) listIterator.next();
			//生成对应临时表用户
			UserSyncDataVO needInsert = this.generUserTmp(currentUser);
			userTmpList.add(needInsert);
			//取出帐号集合，方便后面集合操作
			originAccountList.add((String)currentUser.get(account));
		}
		
		if(null == allTmpUsers || allTmpUsers.size() == 0){
			//临时表无数据，只做插入操作
			try {
				userSyncTmpService.insertBatchTmpUsers(userTmpList);
			} catch (Exception e) {
				logger.error(e.getMessage()+"错误状态码："+UserSyncPubServiceImpl.INSERTING_ERROR+",临时表数据入库异常，请检查sql正确性！",e);
				throw new BusinessException(UserSyncPubServiceImpl.INSERTING_ERROR, "内部错误，请联系管理员！", e);
			}
			return true;
		}
		
		/* 集合操作 */
		List<String> allTmpAccountList = new ArrayList<>();
		//取出所有临时表账号集合
		for(UserSyncDataVO VO : allTmpUsers){
			allTmpAccountList.add(VO.getAccount());
		}
		//深度拷贝两个临时表账号集合
		Mapper mapper = new DozerBeanMapper();
		List<String> deleteAccountList = mapper.map(allTmpAccountList, List.class);
		List<String> updateAccountList = mapper.map(originAccountList, List.class);
		
		//删除
		deleteAccountList.removeAll(originAccountList);
		//删除数据，保持临时表数据同步
		if(deleteAccountList.size() > 0){
			try {
				userSyncTmpService.delete(deleteAccountList.toArray(new String[deleteAccountList.size()]));
			} catch (Exception e) {
				logger.error(e.getMessage()+"错误状态码："+UserSyncPubServiceImpl.DELETING_ERROR+",删除临时表数据异常，请检查sql正确性！",e);
				throw new BusinessException(UserSyncPubServiceImpl.DELETING_ERROR, "内部错误，请联系管理员！", e);
			}
		}
		
		//新增
		originAccountList.removeAll(allTmpAccountList);
		List<UserSyncDataVO> insertBatchList = new ArrayList<>();
		for(String insertAccount : originAccountList){
			for(UserSyncDataVO sync : userTmpList){
				if(insertAccount.equals(sync.getAccount())){
					insertBatchList.add(sync);
				}
			}
		}
		if(insertBatchList.size() > 0){
			try {
				userSyncTmpService.insertBatchTmpUsers(insertBatchList);
			} catch (Exception e) {
				logger.error(e.getMessage()+"错误状态码："+UserSyncPubServiceImpl.INSERTING_ERROR+",临时表数据入库异常，请检查sql正确性！",e);
				throw new BusinessException(UserSyncPubServiceImpl.INSERTING_ERROR, "内部错误，请联系管理员！", e);
			}
		}
		
		//更新，交集获取更新帐号集合
		updateAccountList.retainAll(allTmpAccountList);
		if(! (updateAccountList.size() > 0 )){
			return true;
		}
		//帐号集合+传入用户集合筛选更新用户集合
		List<UserSyncDataVO> updateBatchList = new ArrayList<>();
		for(String updateAccount : updateAccountList){
			for(UserSyncDataVO sync : userTmpList){
				if(updateAccount.equals(sync.getAccount())){
					updateBatchList.add(sync);
				}
			}
		}
		if(updateBatchList.size() > 0){
			try {
				userSyncTmpService.updateBatchTmpUsers(updateBatchList);
			} catch (Exception e) {
				logger.error(e.getMessage()+"错误状态码："+UserSyncPubServiceImpl.UPDATEING_ERROR+",更新临时表数据异常，请检查sql正确性！",e);
				throw new BusinessException(UserSyncPubServiceImpl.UPDATEING_ERROR, "内部错误，请联系管理员！", e);
			}
		}
		
		return true;
	}

	/**
	 * 生成同步用户模型
	 * @param userTmp
	 * @param currentUser
	 */
	private UserSyncDataVO generUserTmp(JSONObject currentUser) {
		UserSyncDataVO userTmp = new UserSyncDataVO();
		if(StringUtils.isNotEmpty(userId)){
			String Id = String.valueOf(currentUser.get(userId));
			userTmp.setUserId(Id);
		}
		if(StringUtils.isNotEmpty(account)){
			String userAccount = (String) currentUser.get(account);
			userTmp.setAccount(userAccount);
		}
		if(StringUtils.isNotEmpty(name)){
			String userName = (String) currentUser.get(name);
			userTmp.setName(userName);
		}
		if(StringUtils.isNotEmpty(isLocked)){
			String isLock = DBoolean.valueOf((String)currentUser.get(isLocked)).toString();
			userTmp.setIsLocked(isLock);
		}
		if(StringUtils.isNotEmpty(isDisabeled)){
			String isDisabele = DBoolean.valueOf((String)currentUser.get(isDisabeled)).toString();
			userTmp.setIsDisabled(isDisabele);
		}
		if(StringUtils.isNotEmpty(mobile)){
			String userMobile = (String) currentUser.get(mobile);
			userTmp.setMobile(userMobile);
		}
		if(StringUtils.isNotEmpty(tel)){
			String userTel = (String) currentUser.get(tel);
			userTmp.setTel(userTel);
		}
		if(StringUtils.isNotEmpty(email)){
			String userEmail = (String) currentUser.get(email);
			userTmp.setEmail(userEmail);
		}
		if(StringUtils.isNotEmpty(sex)){
			String userSex = String.valueOf(currentUser.get(sex));
			if(String.valueOf(GrantConst.USER_SEX_FEMALE).equals(userSex) || userSex.contains(String.valueOf(GrantConst.USER_SEX_FEMALE_SHOW)) 
					|| "female".equalsIgnoreCase(userSex) || "f".equalsIgnoreCase(userSex)){
				userSex = String.valueOf(GrantConst.USER_SEX_FEMALE);
			}else{
				userSex = String.valueOf(GrantConst.USER_SEX_MALE);
			}
			userTmp.setSex(Integer.parseInt(userSex));
		}
		if(StringUtils.isNotEmpty(memo)){
			String userMemo = (String) currentUser.get(memo);
			userTmp.setMemo(userMemo);
		}
		if(StringUtils.isNotEmpty(user_createTime)){
			String userCreateTime = currentUser.get(user_createTime).toString();
			if(!"null".equalsIgnoreCase(userCreateTime) && StringUtils.isNotBlank(userCreateTime)){
				userTmp.setUserCreateTime(DDateTime.parseDDateTime(userCreateTime));
			}
		}
		if(StringUtils.isNotEmpty(user_lastModifyTime)){
			String userLastModifyTime = currentUser.get(user_lastModifyTime).toString();
			if(!"null".equalsIgnoreCase(userLastModifyTime) && StringUtils.isNotBlank(userLastModifyTime)){
				userTmp.setUserLastModifyTime(DDateTime.parseDDateTime(userLastModifyTime));
			}
		}
//		if(StringUtils.isNotEmpty(usertype)){
//			String userTypeStr = String.valueOf(currentUser.get(usertype));
//			if("admin".equalsIgnoreCase(userTypeStr) || String.valueOf(GrantConst.USERTYPE_ADMIN).equals(userTypeStr)){
//				userTypeStr = String.valueOf(GrantConst.USERTYPE_ADMIN);
//			}else{
//				userTypeStr = String.valueOf(GrantConst.USERTYPE_USER);
//			}
//			userTmp.setUsertype(Integer.parseInt(userTypeStr));
//		}
		userTmp.setSyncState(DBoolean.FALSE.toString());
		return userTmp;
	}

	@Override
	public Boolean synchronizeUserDelete(List<?> deleteList) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean SynchronizeUserInsert(List<?> insertList) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean SynchronizeUserUpdate(List<?> updateList) {
		// TODO Auto-generated method stub
		return null;
	}

}
