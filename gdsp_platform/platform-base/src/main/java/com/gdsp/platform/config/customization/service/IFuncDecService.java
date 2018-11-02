package com.gdsp.platform.config.customization.service;

import java.util.List;

import com.gdsp.dev.core.model.query.Condition;
import com.gdsp.platform.config.customization.model.FuncDecDataIOVO;
import com.gdsp.platform.config.customization.model.FuncDecVO;

/**
 * 
* @ClassName: IFuncDecService
* (funcDec接口)
* @author songxiang
* @date 2015年10月28日 上午11:01:39
*
 */

public interface IFuncDecService {
	/**
	 * 
	* @Title: loadFuncDecVOById
	* (加载操作对象)
	* @param id
	* @return    参数说明
	* @return FuncDecVO    返回值说明
	* 	 */
	public FuncDecVO loadFuncDecVOById(String id);
	/**
	 * 
	* @Title: delete
	* (删除某个功能)
	* @param id    参数说明
	* @return void    返回值说明
	* 	 */
	public void delete(String id);
	/**
	 * 
	* @Title: save
	* (新增某个功能)
	* @param funcDecVO    参数说明
	* @return void    返回值说明
	* 	 */
	public void save(FuncDecVO funcDecVO);
	/**
	 * 
	* @Title: queryFuncDecListByCondition
	* (根据menuid查询)
	* @param menuid
	* @return    参数说明
	* @return List<FuncDecVO>    返回值说明
	* 	 */
	public List<FuncDecVO> queryFuncDecListByCondition(String menuid);
	/**
	 * 
	* @Title: findListByMenuId
	* (根据menuid查询)
	* @param menuid
	* @param condition
	* @return    参数说明
	* @return List<FuncDecVO>    返回值说明
	* 	 */
	List<FuncDecVO> findListByMenuId(String menuid,Condition condition);
	/**
	 * 
	* @Title: batchUpdate
	* (批量修改功能)
	* @param funcDecVO    参数说明
	* @return void    返回值说明
	* 	 */
	void batchUpdate(List<FuncDecVO> funcDecVO);
	/**
	 * 导入excel解析出来的数据，（导入功能说明）
	 * @param vos excel解析出来的数据
	 */
	public void importFuncDec(List<FuncDecDataIOVO> vos);
	
	
	/**
	 * @return 如果为true,说明超过数据库字段长度
	 * @return 获取数据库编码集
	 */
	public boolean findDataSourceNlslang(String content);
}
