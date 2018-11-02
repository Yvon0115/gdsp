package com.gdsp.platform.workflow.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.gdsp.dev.persist.dao.ICrudDao;
import com.gdsp.dev.persist.ext.MBDao;
import com.gdsp.platform.workflow.model.NotifyEventDetailVO;

@MBDao
public interface INotifyEventDetailDao extends ICrudDao<NotifyEventDetailVO> {



	/**
	 * 根据节点信息id查询事件通知信息
	 * @param nfId
	 * @return
	 */
	List<NotifyEventDetailVO> queryNotifyEventDetailByNodeinfoId(@Param("nodeInfoId")String nfId);

	/**
	 * 添加事件通知信息
	 * @param notifyEventDetailVO
	 */
	void insertVO(@Param("notifyEventDetailVO")NotifyEventDetailVO notifyEventDetailVO);

}
