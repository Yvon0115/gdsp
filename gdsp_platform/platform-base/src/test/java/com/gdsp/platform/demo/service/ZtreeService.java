/**  
* @Title: ZtreeService.java
* @Package com.gdsp.platform.demo.service
* (用一句话描述该文件做什么)
* @author 连长
* @date 2017年6月13日 下午4:48:23
* @version V1.0  
*/ 
package com.gdsp.platform.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.gdsp.platform.demo.model.ZtreeOrgVO;

/**
* @ClassName: ZtreeService
* (这里用一句话描述这个类的作用)
* @author shiyingjie
* @date 2017年6月13日 下午4:48:23
*
*/
public interface ZtreeService {
	
	
	public List<ZtreeOrgVO> queryRootOrgOnZtree();
	
	public List<ZtreeOrgVO> queryOrgsByIdOnZtree(String id);
	
	public List<ZtreeOrgVO> queryOrgListByUserOnZtree(String id);

}
