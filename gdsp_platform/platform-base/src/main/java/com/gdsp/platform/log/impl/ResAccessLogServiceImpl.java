package com.gdsp.platform.log.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gdsp.dev.core.common.AppContext;
import com.gdsp.dev.core.model.param.Sorter;
import com.gdsp.dev.core.model.query.Condition;
import com.gdsp.platform.grant.user.model.UserVO;
import com.gdsp.platform.grant.user.service.IUserQueryPubService;
import com.gdsp.platform.log.dao.IResAccessLogDao;
import com.gdsp.platform.log.model.ResAccessLogVO;
import com.gdsp.platform.log.model.ResAccessTopVO;
import com.gdsp.platform.log.service.IResAccessLogService;

/**
 * 
* 接口实现类)
* @author songxiang
* @date 2015年10月28日 上午11:35:56
*
 */
@Service
@Transactional
public class ResAccessLogServiceImpl implements IResAccessLogService {

    @Autowired
    private IResAccessLogDao resAccessLogDao;
    @Autowired
    private IUserQueryPubService userQueryPubService;

    @Override
    public void deleteByCdn(Condition cond) {
        resAccessLogDao.deleteByCdn(cond);
    }

    @Override
    public List<ResAccessLogVO> findListByCondition(Condition cond, Sorter sort) {
    	List<ResAccessLogVO> list = resAccessLogDao.findListByCondition(cond, sort);
    	List<String> userIdList = new ArrayList<String>();
        Set<String> userHash = new HashSet<String>();
        for(ResAccessLogVO resVO:list){
       	 userHash.add(resVO.getPk_user());
        }
        Iterator<String> it = userHash.iterator();
        while(it.hasNext()){
       	 	userIdList.add(it.next());
        }
        List<UserVO> querUsersByUserIds = userQueryPubService.querUsersByUserIds(userIdList);
        for(ResAccessLogVO resAccVO:list){
       	 for(UserVO userVO:querUsersByUserIds){
       		 if(resAccVO.getPk_user().equals(userVO.getId())){
       			 resAccVO.setUsername(userVO.getUsername());
       		 }
       	 }
        }
        return list;
    }

    @Override
    public Page<ResAccessLogVO> findPageByCondition(Condition condition, Pageable page, Sorter sort) {
         Page<ResAccessLogVO> findPageByCondition = resAccessLogDao.findPageByCondition(condition, page, sort);
         List<ResAccessLogVO> content = findPageByCondition.getContent();
         List<String> userIdList = new ArrayList<String>();
         Set<String> userHash = new HashSet<String>();
         for(ResAccessLogVO resVO:content){
        	 userHash.add(resVO.getPk_user());
         }
         Iterator<String> it = userHash.iterator();
         while(it.hasNext()){
        	 userIdList.add(it.next());
         }
         List<UserVO> querUsersByUserIds = userQueryPubService.querUsersByUserIds(userIdList);
         for(ResAccessLogVO resAccVO:content){
        	 for(UserVO userVO:querUsersByUserIds){
        		 if(resAccVO.getPk_user().equals(userVO.getId())){
        			 resAccVO.setUsername(userVO.getUsername());
        		 }
        	 }
         }
        return findPageByCondition;
    }

    @Override
    public List<ResAccessTopVO> res_Access_top(String pk_org, Integer showcnt, Sorter sort) {
        return resAccessLogDao.res_Access_top(pk_org, showcnt, sort);
    }

    @Override
    public void deletes(String... ids) {
        resAccessLogDao.deletes(ids);
    }
    
    @Override
    public List<String> getContentList(List<ResAccessLogVO> resAccessList){
    	List<String> contentList = new ArrayList<String>();
    	//每一行数据是一行逗号分隔的字符串
        //修改人：wangxiaolong
    	//修改时间：2017-3-20
    	//修改原因：由于需要对访问日志的展示进行修改，因此对导出的字段也进行了修改；
        for (ResAccessLogVO resAccessLogVO : resAccessList) {
        	StringBuilder builder = new StringBuilder();
        	if(resAccessLogVO.getFuncode() != null){
        		builder.append(resAccessLogVO.getFuncode());
        	}
        	if(resAccessLogVO.getFuncode() == null){
        		builder.append("");
        	}
        	if(resAccessLogVO.getFunname() != null){
        		builder.append(","+resAccessLogVO.getFunname() );
        	}
        	if(resAccessLogVO.getFunname()  == null){
        		builder.append(","+"");
        	}
        	if(resAccessLogVO.getType() != null){
        		builder.append(","+resAccessLogVO.getType());
        	}
        	if(resAccessLogVO.getType() == null){
        		builder.append(","+"");
        	}
        	if(resAccessLogVO.getUsername() != null){
        		builder.append(","+resAccessLogVO.getUsername());
        	}
        	if(resAccessLogVO.getUsername() == null){
        		builder.append(","+"");
        	}
        	if(resAccessLogVO.getCreateTime() != null){
        		builder.append(","+resAccessLogVO.getCreateTime());
        	}
        	if(resAccessLogVO.getCreateTime() == null){
        		builder.append(","+"");
        	}
        	String lineData = builder.toString();
        	contentList.add(lineData);        	
        }
        return contentList;
    }

	@Override
	public List<ResAccessTopVO> findRecentVisitRecords(String pk_user, int count) {
		return resAccessLogDao.queryRecentVisitRecords(pk_user, count);
	}

	

}
