package com.gdsp.platform.systools.datadic.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gdsp.dev.core.common.AppContext;
import com.gdsp.dev.core.model.param.Sorter;
import com.gdsp.dev.core.model.query.Condition;
import com.gdsp.dev.persist.result.MapListResultHandler;
import com.gdsp.platform.grant.auth.service.IUserRoleQueryPubService;
import com.gdsp.platform.grant.role.model.RoleVO;
import com.gdsp.platform.log.service.OpLog;
import com.gdsp.platform.systools.datadic.dao.IDataDicDao;
import com.gdsp.platform.systools.datadic.dao.IDataDicValueDao;
import com.gdsp.platform.systools.datadic.model.DataDicVO;
import com.gdsp.platform.systools.datadic.model.DataDicValueVO;
import com.gdsp.platform.systools.datadic.model.RoleAuthorityVO;
import com.gdsp.platform.systools.datadic.service.IDataDicService;
import com.gdsp.platform.systools.datalicense.model.DataDicPowerVO;
import com.gdsp.platform.systools.datalicense.model.DataLicenseVO;
import com.gdsp.platform.systools.datalicense.service.IDataDicPowerService;
import com.gdsp.platform.systools.datalicense.service.IDataLicenseService;
/**
* @ClassName: DataDicServiceImpl
* (接口实现类)
* @author qishuo
* @date 2016年12月9日
*/
@Service
@Transactional(readOnly = true)
public class DataDicServiceImpl implements IDataDicService {
    @Autowired
    private IDataDicDao dataDicDao;
    @Autowired
    private IDataDicValueDao dataDicValueDao;
    @Autowired
    private IDataLicenseService dataLicenseService;
    @Autowired
    private  IUserRoleQueryPubService  userRoleQueryPubService;
    @Autowired
    private IDataDicPowerService      dataDicPowerService;
    @Override
    public DataDicVO loadDataDic(String dicId) {
        return dataDicDao.load(dicId);
    }

    @Override
    public Page<DataDicVO> queryDataDicPageByCon(Condition con, Pageable page, Sorter sorter) {
        return dataDicDao.queryDataDicPageByCon(con, page, sorter);
    }

    @Override
    public List<DataDicVO> queryAllDataDicList() {
        return dataDicDao.queryAllDataDicList();
    }

    @Override
    public Page<DataDicVO> queryDataDicExtDicIds(List<DataDicPowerVO> voList, Condition condition, Pageable page) {
        Page<DataDicVO> VO = null;
        List<String> pk_dicId = new ArrayList<String>();
        if (voList.size() > 0) {
            for (DataDicPowerVO vo : voList) {
                pk_dicId.add(vo.getPk_dataDicId());
            }
            VO = dataDicDao.queryDataDicExtDicIds(pk_dicId, condition, page);
        } else {
            VO = dataDicDao.queryDataDicExtDicIds(null, condition, page);
        }
        return VO;
    }

    @OpLog
    @Override
    @Transactional
    public void saveDataDic(DataDicVO dataDicVO) {
        if(StringUtils.isNotEmpty(dataDicVO.getId())){
            dataDicDao.updateDataDic(dataDicVO);
        }else{
            dataDicDao.insert(dataDicVO);
        }
        
    }

    @OpLog
    @Override
    @Transactional
    public List deleteDataDic(String[] ids) {
		List<DataDicValueVO> queryDataDicValue = dataDicDao.queryDataDicValue(ids);// 查询所要删除的维度下的维值
		List<DataDicPowerVO> queryPowerDataDic = dataDicPowerService.queryPowerDataDic(ids);
		List<DataLicenseVO> queryRole = dataLicenseService.queryRole(ids);
		if (queryDataDicValue.size() > 0) {
			return queryDataDicValue;
		} else if (queryPowerDataDic.size() > 0) {
			return queryPowerDataDic;
		} else if (queryRole.size() > 0) {
			return queryRole;
		} else {
			dataDicDao.deleteDataDic(ids);
			return null;
		}
    }

    @Override
    public boolean checkDataDicCode(DataDicVO dataDicVO) {
        if(dataDicDao.existSameTypeCode(dataDicVO)>0){
            return false;
        }else{
            return true;
        }
    }


    @Override
    public Page<DataDicValueVO> findDaDicValPageByDicId(Pageable page,String pk_dicId) {
        Page<DataDicValueVO> dataDicValueVO=dataDicDao.findDaDicValPageByDicId(page, pk_dicId);//查询所有维值对象
        if(dataDicValueVO.getContent().size()>0){
            List<String> pk_fatherId=new ArrayList<String>();
            for(int i=0;i<dataDicValueVO.getContent().size();i++){
                if(dataDicValueVO.getContent().get(i).getPk_fatherId()==null){
                    dataDicValueVO.getContent().get(i).setPk_fatherId("");
                }
                pk_fatherId.add(dataDicValueVO.getContent().get(i).getPk_fatherId());//取出维值对象的pk_fatherId
            }
            List<DataDicValueVO> parentVO=dataDicValueDao.queryParent(pk_fatherId);//根据pk_fatherId查询上级对象信息
            for(int i=0;i<dataDicValueVO.getContent().size();i++){
                for(int j=0;j<parentVO.size();j++){
                    if(dataDicValueVO.getContent().get(i).getPk_fatherId().equals(parentVO.get(j).getId())){//匹配对象的上级对象
                        dataDicValueVO.getContent().get(i).setParentCode(parentVO.get(j).getDimvl_code());
                        dataDicValueVO.getContent().get(i).setParentName(parentVO.get(j).getDimvl_name());
                        break;
                    }
                }
            }
            return dataDicValueVO;
        }else{
            return dataDicValueVO;
        }
    }

    @OpLog
    @Override
    @Transactional
    public void saveDataDicValue(DataDicValueVO dataDicValueVO) {
        if(StringUtils.isNotEmpty(dataDicValueVO.getId())){
            dataDicValueDao.updateDataDicValue(dataDicValueVO);
        }else{
            dataDicValueDao.addDataDicValue(dataDicValueVO); 
        }
    }

    @Override
    public DataDicValueVO loadDataDicValue(String dicValId) {
        DataDicValueVO dataDicValueVO=dataDicValueDao.load(dicValId);
        if(StringUtils.isNotEmpty(dataDicValueVO.getPk_fatherId())){
            List<String> pk_fatherId=new ArrayList<String>();
            pk_fatherId.add(dataDicValueVO.getPk_fatherId());
            List<DataDicValueVO> parent=dataDicValueDao.queryParent(pk_fatherId);
            for(DataDicValueVO i:parent){
                dataDicValueVO.setParentCode(i.getDimvl_code());
                dataDicValueVO.setParentName(i.getDimvl_name()); 
            }
        }
        return dataDicValueVO;
    }

    @OpLog
    @Override
    @Transactional
    public List deleteDataDicVal(String[] ids) {
        //查询所要删除的维值的下级
        List<DataDicValueVO> ChildDataDicValue=dataDicValueDao.queryChildDataDicValue(ids);
        List<DataLicenseVO> queryRoleDicval=dataLicenseService.queryRoleDicval(ids);
        if(ChildDataDicValue.size()>0){
            return ChildDataDicValue;
        }else if(queryRoleDicval.size()>0){
            return queryRoleDicval;
        }else{
            dataDicValueDao.deleteDataDicVal(ids);
            return null;
        }
    }

    @Override
    public boolean checkDataDicValCode(DataDicValueVO dataDicValCode) {
        if(dataDicValueDao.checkDataDicValCode(dataDicValCode)>0){
            return false;
        }else{
            return true;
        }
    }

    @Override
    public Map queryDataDicValTree(String pk_dicId) {
        MapListResultHandler handler = new MapListResultHandler("pk_fatherId");
        dataDicValueDao.queryDataDicValTree(handler,pk_dicId);
        return handler.getResult();
    }

    @Override
    public List<Map<String, Map<?, ?>>> queryDataDicVal(List<String> pk_dicIds) {
        List<Map<String, Map<?, ?>>> list=new ArrayList<Map<String, Map<?,?>>>();
        for(String pk_dicId:pk_dicIds){
            Map<String, Map<?, ?>> map=new HashMap<String, Map<?,?>>();
            MapListResultHandler handler = new MapListResultHandler("pk_fatherId");
            dataDicValueDao.queryDataDicValTree(handler,pk_dicId); 
            map.put(pk_dicId, handler.getResult());
            list.add(map);
        } 
        return list;
    }

    @Override
    public List<Map<String, String>> queryDataDicDetail(List<String> dicId) {
        return  dataDicDao.queryDataDicDetail(dicId);
    }
    @Override
    public List<RoleAuthorityVO> queryDataDicInfo(List<String> dicValIds) {
        
        return  dataDicValueDao.findDataDicInfo(dicValIds);

    }

	@Override
	public List<DataDicValueVO> getDatadicValueByTypeCode(String typeCode) {
		
		
		String userId = AppContext.getContext().getContextUserId();
		List<RoleVO> queryRoleList = userRoleQueryPubService.queryRoleListByUserId(userId);
		List<String> roleIdList = new ArrayList<String>();
		for (RoleVO roleVO : queryRoleList) {
			roleIdList.add(roleVO.getId());
		}
		List<DataDicVO> list = dataDicDao.queryDataDicValByTypeCode(typeCode);
		if (list != null && list.size() > 0) {
			String dicId = list.get(0).getId();
			List<String> dicIdList = new ArrayList<String>();
			dicIdList.add(dicId);
			List<DataLicenseVO> queryPowerDataDicVal = dataLicenseService.queryPowerDataDicVal(roleIdList, dicIdList);
			//被授权，查询当前登录用户有权限的数据
			if(queryPowerDataDicVal!=null && queryPowerDataDicVal.size()>0){
				// 去重有权限的数据字典值
				List<String> dicValList = new ArrayList<String>();
				Set<String> set = new HashSet<String>();
				for (DataLicenseVO dataVO : queryPowerDataDicVal) {
					set.add(dataVO.getPk_dicval());
				}
				Iterator it = set.iterator();
				while (it.hasNext()) {
					dicValList.add((String) it.next());
				}
				List<DataDicValueVO> dataDicValueVOList = list.get(0).getDataDicValueVO();
				List<DataDicValueVO> returnList = new ArrayList<DataDicValueVO>();
				for (String dicValIds : dicValList) {
					for (DataDicValueVO dataDicValueVO : dataDicValueVOList) {
						if (dicValIds.contains(dataDicValueVO.getId())) {
							returnList.add(dataDicValueVO);
						}
					}
				}
				return returnList;
			}else{
			//没有被授权，返回全部
			return list.get(0).getDataDicValueVO();
			
			}
		}
		return null;

	}

}

