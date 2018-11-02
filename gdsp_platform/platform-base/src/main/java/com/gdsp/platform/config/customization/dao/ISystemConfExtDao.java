/**
 * 
 */
package com.gdsp.platform.config.customization.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.gdsp.dev.persist.ext.MBDao;
import com.gdsp.platform.config.customization.model.GrantAgingVO;
import com.gdsp.platform.config.customization.model.SystemConfExtVO;

/**
 * 
 * @Description:系统配置扩展dao
 * @author guoyang
 * @date 2016年12月2日
 */
@MBDao
public interface ISystemConfExtDao {

	void updateBatch(List<SystemConfExtVO> systemConfExtVOs);

	List<SystemConfExtVO> querySystemConfExtVoListByCatgCode(@Param("catgCode")String catgCode);

}
