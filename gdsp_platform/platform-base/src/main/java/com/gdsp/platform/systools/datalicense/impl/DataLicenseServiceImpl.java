/*
 * Copyright (c)  Business Intelligence Unified Portal. All rights reserved.
 */
package com.gdsp.platform.systools.datalicense.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gdsp.dev.base.utils.CloneUtils;
import com.gdsp.dev.core.common.AppContext;
import com.gdsp.dev.persist.result.MapListResultHandler;
import com.gdsp.platform.grant.auth.service.IUserRoleQueryPubService;
import com.gdsp.platform.grant.role.model.RoleVO;
import com.gdsp.platform.grant.user.model.UserVO;
import com.gdsp.platform.grant.user.service.IUserQueryPubService;
import com.gdsp.platform.grant.utils.GrantConst;
import com.gdsp.platform.log.service.OpLog;
import com.gdsp.platform.systools.datadic.model.DataDicValueVO;
import com.gdsp.platform.systools.datadic.model.RoleAuthorityVO;
import com.gdsp.platform.systools.datalicense.dao.IDataLicenseDao;
import com.gdsp.platform.systools.datalicense.model.DataLicenseVO;
import com.gdsp.platform.systools.datalicense.service.IDataLicenseService;


/**
 * @author wangliyuan
 *
 */
@Service
@Transactional(readOnly = true)
public class DataLicenseServiceImpl implements IDataLicenseService{
    
    @Autowired
    private IDataLicenseDao dataLicenseDao;
    
    @Autowired
    private IUserQueryPubService userQueryPubService;
    
    @Autowired
    private IUserRoleQueryPubService userRoleService;
    
	@Override
	public List<DataLicenseVO> queryDataDicByRoleId(String roleId) {
		return dataLicenseDao.queryDataDicByRoleId(roleId);
	}

    @Override
    public List<DataLicenseVO> queryDataDicByRoleIds(List<?> roleIds) {
        return dataLicenseDao.queryDataDicByRoleIds(roleIds);
    }

    @Override
    public List<Map<String, Map<?,?>>> powerChecked(List<Map<String, Map<?,?>>> dimvalList, List<DataLicenseVO> dataLicenseRltList) {
        //将有权限的整理成map结构
        if(dataLicenseRltList!=null && dataLicenseRltList.size()>0){
            //以维度为key  维值为list
            Map<String,List<String>> rltMaps = new HashMap<String,List<String>>();
            for(DataLicenseVO dataVO :dataLicenseRltList){
                String key = dataVO.getPk_dic();
                if(rltMaps.containsKey(key)){
                    rltMaps.get(key).add(dataVO.getPk_dicval());
                }else{
                   List<String> list = new ArrayList<String>(); 
                   list.add(dataVO.getPk_dicval());
                   rltMaps.put(key, list);
                }
            }
            //循环所以可以展现的维值树
            if(dimvalList!=null && dimvalList.size()>0){
                for(int i=0;i<dimvalList.size();i++){
                    Iterator<String> iterator = dimvalList.get(i).keySet().iterator();
                    while(iterator.hasNext()){
                        //获取是哪个维度
                        String key = iterator.next();
                        if(rltMaps.containsKey(key)){
                              Iterator iterator2 = dimvalList.get(i).get(key).keySet().iterator();
                             while(iterator2.hasNext()){
                                 List<DataDicValueVO> list = (List)dimvalList.get(i).get(key).get(iterator2.next());
                                 if(list!=null && list.size()>0){
                                     for(DataDicValueVO datavo:list){
                                         if(rltMaps.get(key).contains(datavo.getId())){
                                             datavo.setIsChecked("Y");
                                         }
                                     }
                                 }
                             }
                        }
                    }
                }
            }
        }
        return dimvalList;
    }

    @Override
    public List<DataLicenseVO> queryPowerDataDicVal(List<String> roleId,List<String> dicIdList) {
        return  dataLicenseDao.queryPowerDataDicVal(roleId,dicIdList);
    }
    
    @Transactional
    @Override
    @OpLog
    public Object addDicValToRole(Map<String,List<String>> newAuthDataMap,String roleId) {
    	
    	List<DataLicenseVO> grantedPowerList = this.queryDataDicByRoleId(roleId);    // 查询角色当前的数据权限
		List<DataLicenseVO> insertList = new ArrayList<DataLicenseVO>();    // 待新增的数据项
		List<String> deleteList = new ArrayList<String>();    // 待删除的数据项

		
		Map<String,List<String>> inserMap = new HashMap<String, List<String>>();
		Map<String,List<String>> deleteMap = new HashMap<String, List<String>>();
		int opFlag = 0;    // 操作类型标识
		
		if (CollectionUtils.isNotEmpty(grantedPowerList) && MapUtils.isEmpty(newAuthDataMap)) {
			// 原来有赋过权，现在删除了全部权限
			opFlag = 1;
		}else if (CollectionUtils.isEmpty(grantedPowerList) && MapUtils.isNotEmpty(newAuthDataMap)) {
			// 全部是新增的权限
			opFlag = 2;
			DataLicenseVO vo;
			for (Map.Entry<String, List<String>> entry : newAuthDataMap.entrySet()) {
				List<String> valIds = entry.getValue();
				for (String id : valIds) {
					vo = new DataLicenseVO();
					vo.setPk_role(roleId);
					vo.setPk_dic(entry.getKey());
					vo.setPk_dicval(id);
					insertList.add(vo);
				}
			}
		}else if (CollectionUtils.isNotEmpty(grantedPowerList) && MapUtils.isNotEmpty(newAuthDataMap)) {
			// 有增有减，判断情况
			Map<String, List<String>> grantedPowerMap = getAuthDataMap(grantedPowerList);    // 已赋权的数据权限map
			Map<String, List<String>> grantedPowerMapCopy = CloneUtils.clone((HashMap)grantedPowerMap);
//			grantedPowerMapCopy.putAll(grantedPowerMap);    // 复制
			
			Map<String, List<String>> newAuthDataMapCopy = CloneUtils.clone((HashMap)newAuthDataMap);   // 新的权限分配map
//			newAuthDataMapCopy.putAll(newAuthDataMap);    // 复制
			
			
			// 判断新增的数据项,新增项为新list对旧list求差集
			for (Map.Entry<String, List<String>> entry : newAuthDataMapCopy.entrySet()) {
				List<String> newList = entry.getValue();    // 新数据
				List<String> oldList = grantedPowerMapCopy.get(entry.getKey());    // 旧数据
				if (CollectionUtils.isNotEmpty(oldList)) {
					newList.removeAll(oldList);    // 新对旧的差集
				}
				inserMap.put(entry.getKey(), newList);
			}
			
			Map<String, List<String>> grantedPowerMapCopy2 = CloneUtils.clone((HashMap)grantedPowerMap);
//			grantedPowerMapCopy2.putAll(grantedPowerMap);    // 复制
			
			Map<String, List<String>> newAuthDataMapCopy2 = CloneUtils.clone((HashMap)newAuthDataMap);
//			newAuthDataMapCopy2.putAll(newAuthDataMap);    // 复制
			
			// 判断删除的数据项,删除项为旧list对新list求差集
			for (Map.Entry<String, List<String>> entry : grantedPowerMapCopy2.entrySet()) {
				List<String> oldList = entry.getValue();    // 新修改的
				List<String> newList = newAuthDataMapCopy2.get(entry.getKey());    // 已授权的
				if (CollectionUtils.isNotEmpty(newList)) {
					oldList.removeAll(newList);    // 旧对新的差集
				}
				deleteMap.put(entry.getKey(), oldList);
			}
			opFlag = 3;
		}
		
		switch (opFlag) {
		case 1:
			// 删除了所有权限
			Map<String, List<String>> map = getAuthDataMap(grantedPowerList);
			for (Map.Entry<String, List<String>> entry : map.entrySet()) {
				dataLicenseDao.deletePowerConn(roleId, entry.getKey(), entry.getValue());
			}
			break;
		case 2:
			// 第一次授权
			dataLicenseDao.insertPowerConn(insertList);
			break;
		case 3:
			for (Map.Entry<String, List<String>> entry : deleteMap.entrySet()) {
				if (CollectionUtils.isNotEmpty(entry.getValue())) {
					dataLicenseDao.deletePowerConn(roleId, entry.getKey(), entry.getValue());
				}
			}
			List<DataLicenseVO> insertVOs = new ArrayList<DataLicenseVO>();
			DataLicenseVO ivo ;
			for (Map.Entry<String, List<String>> entry : inserMap.entrySet()) {
				List<String> ids = entry.getValue();
				for (String id : ids) {
					ivo = new DataLicenseVO();
					ivo.setPk_role(roleId);
					ivo.setPk_dic(entry.getKey());
					ivo.setPk_dicval(id);
					insertVOs.add(ivo);
				}
			}
			if (CollectionUtils.isNotEmpty(insertVOs)) {
				dataLicenseDao.insertPowerConn(insertVOs);
			}
			
		default:
			break;
		}
		
//		Map<String, List<String>> powerMap = new HashMap<String, List<String>>();    // 有权限的数据字典
		// 如果角色已赋过数据权限
		/*
		if (powerList != null && powerList.size() > 0) {
			for (DataLicenseVO datalicenseVO : powerList) {
				String key = datalicenseVO.getPk_dic();
				if (powerMap.containsKey(key)) {
					powerMap.get(key).add(datalicenseVO.getPk_dicval());
				} else {
					List<String> dicvalueList = new ArrayList<String>();
					dicvalueList.add(datalicenseVO.getPk_dicval());
					powerMap.put(key, dicvalueList);
				}
			}
			// 先循环新的map
			for (String HandlerKey : newAuthmap.keySet()) {
				// 如果没有，则全部是新的授权
				if (powerMap.containsKey(HandlerKey)) {
					for (String powerKey : powerMap.keySet()) {
						if (HandlerKey.equals(powerKey)) {
							List<String> HandlerList = newAuthmap.get(HandlerKey);
							List<String> powersList = powerMap.get(powerKey);
							if (HandlerList != null && HandlerList.size() > 0) {
								if (powersList != null && powersList.size() > 0) {
									if (HandlerList.size() > powersList.size()) {
										HandlerList.removeAll(powersList);
										// 页面新增数据授权
										handlerInsertData(HandlerList,insertList, roleId, HandlerKey);
									} else if (powersList.size() > HandlerList.size()) {
										powersList.removeAll(HandlerList);
										// 删除库里已授权的数据
										for (String deletetString : HandlerList) {
											deleteList.add(deletetString);
										}
									} else if (HandlerList.size() == powersList.size()) {
										HandlerList.removeAll(powersList);
										// 页面新增数据授权
										handlerInsertData(HandlerList,insertList, roleId, HandlerKey);
										powersList.removeAll(HandlerList);
										// 删除库里已授权的数据
										for (String deletetString : HandlerList) {
											deleteList.add(deletetString);
										}
									}
									if(deleteList.size()>0){
										dataLicenseDao.deletePowerConn(roleId,HandlerKey, deleteList);	
									}	
								}
							}
						}
					}
				} else {
					// 新增的授权数据，以前这个维度没有被授权过
					handlerInsertData(newAuthmap.get(HandlerKey), insertList, roleId,
							HandlerKey);
				}
			}
			//删除全不授权的关系
			List<String> deleteRltList = new ArrayList<String>();
			for(String deleteKey:powerMap.keySet()){
				if(!newAuthmap.containsKey(deleteKey)){
					for(String dicvalueId:powerMap.get(deleteKey)){
						deleteRltList.add(dicvalueId);
					}
				}
				if(deleteRltList.size()>0){
					dataLicenseDao.deletePowerConn(roleId,deleteKey, deleteRltList);	
				}
			}
		} else {
			for (String key : newAuthmap.keySet()) {
				handlerInsertData(newAuthmap.get(key), insertList, roleId, key);
			}
		}
		if (insertList.size() > 0) {
			dataLicenseDao.insertPowerConn(insertList);
		}
		*/
		return true;
	}

    /**
     * 根据ID获取角色的数据权限
     * @param roleId
     */
	private Map<String, List<String>> getAuthDataMap(List<DataLicenseVO> grantedPowerList) {
		// 当前角色有权限的数据字典
//		List<DataLicenseVO> powerList = this.queryDataDicByRoleId(roleId);
		
		// 以树id为key，数据项集合为value的map结构  --Map<树ID，List<数据项ID>>
		Map<String, List<String>> grantedPowerMap = new HashMap<String, List<String>>();
		for (DataLicenseVO datalicenseVO : grantedPowerList) {
			String key = datalicenseVO.getPk_dic();
			if (grantedPowerMap.containsKey(key)) {
				grantedPowerMap.get(key).add(datalicenseVO.getPk_dicval());
			} else {
				List<String> dicvalueList = new ArrayList<String>();
				dicvalueList.add(datalicenseVO.getPk_dicval());
				grantedPowerMap.put(key, dicvalueList);
			}
		}
		return grantedPowerMap;
	}

    @Override
    public List<DataLicenseVO> queryRole(String[] ids) {
        return dataLicenseDao.queryRole(ids);
    }

    @Override
    public List<DataLicenseVO> queryRoleDicval(String[] ids) {
        return dataLicenseDao.queryRoleDicval(ids);
    }
    
    @Override
    public List<DataLicenseVO> queryAllRoleDataList() {
        return dataLicenseDao.queryAllRoleDataList();
    }
    
    @Override
    public List<RoleAuthorityVO> queryAllDimList() {
        return dataLicenseDao.queryAllDimList();
    }
    
    @Override
    public List<RoleAuthorityVO> queryAllDimValueList() {
        return dataLicenseDao.queryAllDimValueList();
    }
    
    public List handlerInsertData(List<String> needHandlerList,List<DataLicenseVO> resultList,String roleId,String DicId){
    	for(String inserString:needHandlerList){
			DataLicenseVO datalicenseVO = new DataLicenseVO();
 			datalicenseVO.setPk_role(roleId);
 			datalicenseVO.setPk_dic(DicId);
 			 datalicenseVO.setPk_dicval(inserString);
 			resultList.add(datalicenseVO);
		}
    	return resultList;
    }

	/* 修改人：caianqi
	 * 修改原因：
	 * 原有的逻辑：
	 * 只要有数据授权这个页面的操作权利，就可以显示自己所管理的任意机构下的任意角色，包括自己的角色，
	 * 当上级管理员授权某一个数据字典中的一个维度的权限，拥有此角色的用户却可以看到该字典下的全部维度 
	 * 修改后的逻辑：
	 * 只显示被赋予的权限维度值，并可以将自己具有的权限维度值赋给下一级
	 */
    /** 一句话概括： 这个方法列出了当前登录用户有权限的数据字典树并所选角色权限勾选了对应节点  */
	@Override
	public Map getDimValueTree(String dicId, String roleId) {
		MapListResultHandler handler = new MapListResultHandler("pk_fatherId");
		String userId = AppContext.getContext().getContextUserId();
		UserVO userVO = userQueryPubService.load(userId);
	
		// 下面的逻辑是：
		// 超级管理员直接返回查询结果
		// 当不是超级管理员时，从<超级管理员>或<上级管理角色>对<当前登陆的角色>所设置的授权维度结果中 ，
		// 查找左侧列表中 <已点击的角色> 被 <当前登陆用户角色> 所  <授权的维度> 并  <显示>被选中状态。
		if (userVO.getUsertype() == GrantConst.USERTYPE_ADMIN) {
			dataLicenseDao.getDimValueTree(handler, dicId, roleId);
			Map map = handler.getResult();
			return map;
		} else {
			// 当前登录用户拥有的角色
			List<RoleVO> roleList = userRoleService.queryRoleListByUserId(userId);
			List<String> roleIdList = new ArrayList<String>();
			if (roleList != null && roleList.size() > 0) {
				for (RoleVO roleVO : roleList) {
					roleIdList.add(roleVO.getId());
				}
			}
			
			//1.查询当前登录用户有权限的数据字典维度值VO
			List<DataLicenseVO> loginUserDataDicValList = dataLicenseDao.queryDimValueByRoleId(dicId,roleIdList);
			List<DataLicenseVO> uniqueVal = new ArrayList<DataLicenseVO>();
			//不同的角色可能会有相同的授权维度，去重
			if (loginUserDataDicValList != null && loginUserDataDicValList.size() > 0) {
			    uniqueVal = new ArrayList<DataLicenseVO>(new LinkedHashSet<DataLicenseVO>(loginUserDataDicValList)); 
				//重置当前角色的数据授权维度的checked状态
				for (DataLicenseVO dLicenseVO : uniqueVal) {
					dLicenseVO.setIsChecked("N");
				}
			}
			
			//2.查询点击角色所具有的维度值
			dataLicenseDao.getRoleDimValueTree(handler, dicId, roleId);
	        Map<String, List<DataLicenseVO>> map = handler.getResult();
	        List<DataLicenseVO> selectedRolePower = map.get("__null_key__");
	        List<String> cosDicValIdList= new ArrayList<>();
	        
	        //如果点击的角色有权限维度，那么将点击角色的权限维度添加到当前用户角色的权限维度中，并让其显示为checked状态。
	        //如果点击的角色没有任何维度，那么直接使用重置checked的当前用户数据授权结果
	        if (CollectionUtils.isNotEmpty(selectedRolePower)) {
	        	for (DataLicenseVO dataLicenseVO : selectedRolePower) {
	        		cosDicValIdList.add(dataLicenseVO.getId());
				} 
	        	for (DataLicenseVO crtdataLicenseVO : uniqueVal) {
					for (String cosDicValId : cosDicValIdList) {
						if (crtdataLicenseVO.getId().equalsIgnoreCase(cosDicValId)) {
							crtdataLicenseVO.setIsChecked("Y");
						}
					}
				}
	        	map.put("__null_key__", uniqueVal);
	        	return map;
			}else {
				map.put("__null_key__", uniqueVal);
				return map;
			}
		}
	}


}
