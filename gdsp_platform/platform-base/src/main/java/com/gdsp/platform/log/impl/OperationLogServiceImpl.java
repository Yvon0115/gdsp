package com.gdsp.platform.log.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gdsp.dev.core.model.param.Sorter;
import com.gdsp.dev.core.model.query.Condition;
import com.gdsp.platform.grant.user.model.UserVO;
import com.gdsp.platform.log.dao.IOperationLogDao;
import com.gdsp.platform.log.model.DetailOpLogVO;
import com.gdsp.platform.log.model.OperationLogVO;
import com.gdsp.platform.log.service.IOperationLogService;
import com.gdsp.platform.systools.datasource.model.DataSourceVO;
@Service
@Transactional(readOnly = true)
public class OperationLogServiceImpl implements IOperationLogService {
	
	@Autowired
    private IOperationLogDao operationLogDao;
	
	public List<OperationLogVO> operationLogList(Condition condition){
		return operationLogDao.operationLogList(condition);
	}
	/**
     * 根据单条ID查询数据
     */
    @Override
    public OperationLogVO load(String id) {
        return operationLogDao.load(id);
    }
	@Override
	public List<DetailOpLogVO> queryDetailByCondition(String id){
		return operationLogDao.findDetailInfoById(id);
	}
	
	@Override
	public List<String> getContentList(List<OperationLogVO> opLogList){
		List<String> contentList = new ArrayList<String>();
		for (OperationLogVO operationLogVO : opLogList) {
			String totalData = "";
			StringBuilder build = new StringBuilder();
			//类型
        	if(operationLogVO.getType() != null){
        		build.append(operationLogVO.getType());
        	}
        	if(operationLogVO.getType() == null){
        		build.append("");
        	}
        	//表
        	if(operationLogVO.getTable_name() != null){
        		build.append(","+operationLogVO.getTable_name());
        	}
        	if(operationLogVO.getTable_name() == null){
        		build.append(","+"");
        	}
        	//表名
        	if(operationLogVO.getTable_desc()!= null){
        		build.append(","+operationLogVO.getTable_desc());
        	}
        	if(operationLogVO.getTable_desc() == null){
        		build.append(","+"");
        	}
        	//操作人
        	if(operationLogVO.getUsername() != null){
        		build.append(","+operationLogVO.getUsername());
        	}
        	if(operationLogVO.getUsername() == null){
        		build.append(","+"");
        	}
        	//时间
        	if(operationLogVO.getCreateTime() != null){
        		build.append(","+operationLogVO.getCreateTime());
        	}
        	if(operationLogVO.getCreateTime() == null){
        		build.append(","+"");
        	}
        	String lineData = build.toString();
			String opId = operationLogVO.getId();
        	//根据id获取字段变化详细信息
        	List<DetailOpLogVO> detailInfoList = operationLogDao.findDetailInfoById(opId);
        	if(detailInfoList != null && detailInfoList.size()>0){
        		for (int i=0;i<detailInfoList.size();i++) {
        			StringBuilder colbuilder = new StringBuilder();
            		if(detailInfoList.get(i).getCol_name() != null){
            			colbuilder.append(","+detailInfoList.get(i).getCol_name());
            		}
            		if(detailInfoList.get(i).getCol_name() == null){
            			colbuilder.append(",");
                	}
            		if(detailInfoList.get(i).getCol_desc()!= null){
            			colbuilder.append(","+detailInfoList.get(i).getCol_desc());
            		}
            		if(detailInfoList.get(i).getCol_desc() == null){
            			colbuilder.append(",");
                	}
            		if(detailInfoList.get(i).getOld_data() != null){
            			colbuilder.append(","+detailInfoList.get(i).getOld_data());
            		}
            		if(detailInfoList.get(i).getOld_data() == null){
            			colbuilder.append(",");
                	}
            		if(detailInfoList.get(i).getNew_data() != null){
            			colbuilder.append(","+detailInfoList.get(i).getNew_data());
            		}
            		if(detailInfoList.get(i).getNew_data() == null){
            			colbuilder.append(",");
                	}
            		String colData = colbuilder.toString();
            		if(i == 0){
            			totalData = lineData + colData;
            		}else{
            			totalData = ","+","+","+"," + colData;
            		}
                	contentList.add(totalData);
				}
        	}
		}
		return contentList;
	}
	
}
