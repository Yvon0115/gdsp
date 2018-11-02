/**
 * 
 */
package com.gdsp.integration.framework.reportentry.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.gdsp.dev.base.lang.DDateTime;
import com.gdsp.dev.core.common.AppContext;
import com.gdsp.dev.core.model.param.Sorter;
import com.gdsp.dev.core.model.query.Condition;
import com.gdsp.integration.framework.reportentry.dao.HistoryChangeDao;
import com.gdsp.integration.framework.reportentry.model.HistoryChangeVO;
import com.gdsp.integration.framework.reportentry.service.IHistoryChangeService;
import com.gdsp.platform.grant.user.model.UserVO;
import com.gdsp.platform.grant.user.service.IUserQueryPubService;


/**
 * @author wangliyuan
 *
 */
@Service
public class HistoryChangeServiceImpl implements IHistoryChangeService {
    
    @Autowired
    private HistoryChangeDao historyChangeDao;
    @Autowired
    private IUserQueryPubService userQueryPubService;
    
    @Override
    public List<HistoryChangeVO> queryByCondition(Condition condition, Sorter sort) {
        
    	/*权限拆分-------------------------------------2016.12.28
        return historyChangeDao.queryByCondition(condition, page, sort);
        */
    	List<HistoryChangeVO> queryByCondition = historyChangeDao.queryByCondition(condition, sort);
    	setUserName(queryByCondition);
        return queryByCondition;
    }

    @Override
    public boolean saveHistoryChange(HistoryChangeVO historyChangeVO) {
        if (StringUtils.isEmpty(historyChangeVO.getId())){
            DDateTime current = new DDateTime();
            historyChangeVO.setOperationTime(current);
        }
        Condition condition=new Condition();
        condition.addExpression("link_id",historyChangeVO.getLink_id());
        Sorter sort=null;
        //查询数据库中存在的变更时间
        List<HistoryChangeVO> changeTime=historyChangeDao.queryByConditionReturnList(condition, sort);
        String beginTime= new String(historyChangeVO.getChangeTime());
        //判断数据库是否存在数据,若不存在则肯定为新增
        if(changeTime.size()==0){
            historyChangeDao.save(historyChangeVO);
            return true; 
        }else{
            //找出数据库中最靠后的时间
            String maxChangeTime = null;
            for(int i=0;i<changeTime.size();i++){
                String beginChangeTime=changeTime.get(i).getChangeTime();
                for(int j=i+1;j<changeTime.size();j++){
                    String endChangeTime=changeTime.get(j).getChangeTime();
                    if(beginChangeTime.compareTo(endChangeTime)<0){
                        beginChangeTime=endChangeTime;
                    }
                 }
                maxChangeTime=beginChangeTime;
            }
            //判断是新增还是修改
            if (StringUtils.isEmpty(historyChangeVO.getId())) {
                //判断新增的数据的变更时间是否在之前的变更时间之前
                if(beginTime.compareTo(maxChangeTime)<0){
                    return false;
                }else{
                    historyChangeDao.save(historyChangeVO);
                    return true; 
                }
             }
            else
            { 
                String loginUserID = AppContext.getContext().getContextUserId();
                historyChangeVO.setCreateBy(loginUserID);
                //判断修改的数据的变更时间是否在之前的变更时间之前
                if(beginTime.compareTo(maxChangeTime)<0){
                    return false;
                }else{
                    historyChangeDao.update(historyChangeVO); 
                    return true;
                }
            }
        }
            
    }

    @Override
    public HistoryChangeVO load(String id) {
        
    /*权限拆分-------------------------------------2016.12.28
        return historyChangeDao.load(id);
     */
    	HistoryChangeVO historyChangeVO  = historyChangeDao.load(id);
    	UserVO userVO = userQueryPubService.load(historyChangeVO.getCreateBy());
    	historyChangeVO.setUsername(userVO.getUsername());
    	return historyChangeVO;
    }

    @Override
    public List<HistoryChangeVO> findHistoryChangeVOBylinkId(String link_id) {
        
    /*权限拆分-------------------------------------2016.12.28
        return historyChangeDao.findHistoryChangeVOBylinkId(link_id, page);
     */
    	List<HistoryChangeVO> findHistoryChangeVOBylinkId = historyChangeDao.findHistoryChangeVOBylinkId(link_id);
    	setUserName(findHistoryChangeVOBylinkId);
    	return findHistoryChangeVOBylinkId;
    }

    @Override
    public void delete(String id) {
        
        historyChangeDao.delete(id);
        
    }

    @Override
    public List<HistoryChangeVO> queryByConditionReturnList(Condition condition, Sorter sort) {
        
        return null;
    }

    @Override
    public HistoryChangeVO findHistoryChangeVOBylinkId(String link_id, String opType) {
        
        return null;
    }

    @Override
    public int findHistoryChangeVOByCon(Condition condition,HistoryChangeVO historyChangeVO) {
        
        condition.addExpression("link_id", historyChangeVO.getLink_id());
        if("创建".equals(historyChangeVO.getOpType())){
            condition.addExpression("opType", "创建");
            if(StringUtils.isNotEmpty(historyChangeVO.getId())){
                //修改历史变更，选择创建
                condition.addExpression("id", historyChangeVO.getId(),"<>");
                return   historyChangeDao.findHistoryChangeVOByCon(condition);
            } else{
                //添加历史变更，选择创建
                return historyChangeDao.findHistoryChangeVOByCon(condition);
            }
        }else{ 
            //添加历史变更，选择变更
            return 0;
        }
       
    }
    /**
     * 用于给HistoryChangeVO添加用户名
     * @param list
     * @return
     * 2016年12月28日
     * win
     */
    public List<HistoryChangeVO> setUserName(List<HistoryChangeVO> list){
    	List<String> userIdList = new ArrayList<String>();
        Set<String> userHash = new HashSet<String>();
        for(HistoryChangeVO hisVO:list){
        	userHash.add(hisVO.getCreateBy());
        }
        Iterator<String> it = userHash.iterator();
        while(it.hasNext()){
       	 userIdList.add(it.next());
        }
        List<UserVO> querUsersByUserIds = userQueryPubService.querUsersByUserIds(userIdList);
        for(HistoryChangeVO historyVO : list){
        	for(UserVO userVO :querUsersByUserIds){
        		if(historyVO.getCreateBy().equals(userVO.getId())){
        			historyVO.setUsername(userVO.getUsername());
        		}
        	}
        }
		return list;
    	
    }
}
