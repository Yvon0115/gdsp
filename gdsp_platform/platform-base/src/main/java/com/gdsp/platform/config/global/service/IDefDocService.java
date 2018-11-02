package com.gdsp.platform.config.global.service;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.gdsp.dev.core.cases.service.IQueryService;
import com.gdsp.platform.config.global.model.DefDocVO;

/**
 * 系统码表服务
 * @author songxiang
 * @date 2015年10月28日 下午1:58:20
 */
public interface IDefDocService extends IQueryService<DefDocVO> {

	/**
	 * 加载操作对象 
	 * @param id
	 * @return 参数说明
	 * @return DefDocVO  
	 * */
	public DefDocVO load(String id);
	
	/**
	 * 根据主键查询单条数据项(不包括父级编码和名称)
	 * @param id
	 * @return 参数说明
	 * @return DefDocVO 返回值说明
	 */
	public DefDocVO findOne(String id);

	
	/**
	 * 通过名称查询VO,邮储用
	 * @param doc_name
	 * @return
	 * 删除原因：无用方法
	 * @author wqh 2017-9-18 14:04:50
	 */
//	public DefDocVO findByName(String doc_name, String type_id);
	/**
	 * 通过描述查询VO,邮储专用
	 * @param doc_name
	 * @return
	 * 删除原因：无用方法
	 * @author wqh 2017-9-18 14:04:50
	 */
//	public DefDocVO findByDocDesc(String doc_desc, String type_id);
	
	/**
	 * 通过类型和编码查询VO <br/>
	 * @param typeCode 码表类型编码
	 * @param docCode 码表项编码
	 * @return
	 */
	public DefDocVO findDocByTypeAndCode(String typeCode, String docCode);
	
	/**
	 * 查询某个档案分类下的数据项 
	 * @param type_id  档案类型id
	 * @return 参数说明
	 * @return List<DefDocVO> 返回值说明
	 */
	public List<DefDocVO> findListByType(String type_id);

	/**
	 * 根据类型ID查询分类下的档案
	 * @param type_id
	 * @param pageable
	 * @return 参数说明
	 * @return Page<DefDocVO> 返回值说明
	 * */
	public Page<DefDocVO> findPageByType(String type_id, Pageable pageable);

	/**
	 * 保存操作 
	 * @param defDocVO 参数说明
	 * @return void 返回值说明
	 * */
	public void saveDefDoc(DefDocVO defDocVO);

	

	/**
	 * 删除 
	 * @param ids 参数说明
	 * @return void 返回值说明
	 * 删除原因：不允许在页面上删除
	 * @author wqh 2017-9-18 14:04:50
	 */
//	public void delete(String[] ids);

	/**
	 * 查询到sortnum的最大值 
	 * @return 参数说明
	 * @return int 返回值说明
	 * */
	public int findSortnum();

	/**
	 * 是否是系统预置数据，如果是Y则是系统预置，不可以删除。 
	 * @param id
	 * @return List<String> 返回所有查询数据项的isPreset字段，此字段如果为“Y”，则不能删除。
	 * */
	public List<String> isPreset(String[] id);

	
	/**
	 * 重复验证
	 * @param defDocVO
	 * @return
	 */
	public boolean synchroCheck(DefDocVO defDocVO);

	/**
	 * 直接从数据库获取 
	 * @param dataSource
	 * 删除原因：重复方法
	 * @see findListByType
	 * @author wqh 2017-9-18 14:09:10
	 */
//	public List<DefDocVO> getDefDocsByTypeIDFromDataBase(String dataSource);

	/**
     * 通过编码查询id <br/>
     * TODO 逻辑错误，应移至码表类型查询接口(IDefDocListService)中
     * @param code
     */
//    public String findIdByCode(String code);

	/**
	 * 获取时间频度
	 * @param dateFreq
	 * @return
	 */
	public List<DefDocVO> findDateFreq(String dateFreq);

	/**
	 * 获取时间频度用于前台页面展示
	 * @param tempStrs
	 * @return
	 */
	public List<DefDocVO> findFreqByStateFreq(String[] tempStrs, String frequency);

	/**
	 * 根据频度名称获取频度code
	 * @param tempStrs
	 * @return
	 */
	public List<DefDocVO> findFreqByCodeName(String[] tempStrs);

	/**
	 * 根据基础档案编码（类型编码）查询数据项集合 <br/>
	 * @param typeCode
	 * @return 码表数据项
	 */
	public List<DefDocVO> queryDefDocByTypeCode(String typeCode);
	
	/**
	 * 查询以DocCode为父级的数据项 <br/> 
	 * 用来查询具有层级结构的码表中某一级的某一数据项的子级档案
	 * @param typeCode 基础档案编码（类型编码）
	 * @param docCode 要查询的数据项的父级编码
	 * @return 以docCode为父code的数据项
	 * @author wqh 2017年8月28日
	 */
	public List<DefDocVO> findSubLevelDocsByCode(String typeCode, String docCode);
	
	/**
	 * 查询以DocCode为父级的数据项 <br/> 
	 * 用来查询具有层级结构的码表中某一级的某一数据项的子级档案
	 * @param typeCode 基础档案编码（类型编码）
	 * @param docCode 要查询的数据项的父级编码
	 * @return 以docCode为父code的数据项
	 * @author wqh 2017年10月26日
	 */
	public List<DefDocVO> findSubLevelDocsByCode(String typeCode, List<String> docCode);
	
	/**
	 * 根据类型编码查询数据项（树形结构数据）
	 * @param typeCode
	 * @return
	 */
	public Map findDefDocMapByTypeCode(String typeCode);
	/**
	 * 选择对象的上级
	 * @param type_id 对象的类型ID
	 * @return 返回Map树
	 */
	public Map selectParent(String type_id);
	
	
	/**
	 * 查询所有系统码表及其类型
	 * 位置挪移：来自接口IDefDocListService
	 * @see IDefDocListService
	 * @return Map<String,List<DefDocVO>> 返回类型
	 * @author wqh 2017年8月28日10:54:07
	 */
    Map<String, List<DefDocVO>> findAllDocListWithDocs();
}
