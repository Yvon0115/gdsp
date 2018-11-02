/**  
* @Title: ZtreeDao.java
* @Package com.gdsp.platform.demo.dao
* (用一句话描述该文件做什么)
* @author 连长
* @date 2017年6月13日 下午4:56:17
* @version V1.0  
*/ 
package com.gdsp.platform.demo.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.gdsp.dev.persist.ext.MBDao;
import com.gdsp.platform.demo.model.ZtreeOrgVO;

/**
* @ClassName: ZtreeDao
* (这里用一句话描述这个类的作用)
* @author shiyingjie
* @date 2017年6月13日 下午4:56:17
*
*/
@MBDao
public interface ZtreeDemoDao {

	List<ZtreeOrgVO> queryRootOrgOnZtree();
	
	List<ZtreeOrgVO> queryOrgsByIdOnZtree(@Param("id")String id);
	
	List<ZtreeOrgVO> queryAllOrgListOnZtree();
	
	List<String> queryOrgListByUserOnZtree(@Param("userID")String id);
	
	List<ZtreeOrgVO> queryOrgListByIDsOnZtree(List list);
}
