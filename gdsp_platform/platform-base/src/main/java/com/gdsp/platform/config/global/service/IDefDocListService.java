package com.gdsp.platform.config.global.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.gdsp.dev.base.exceptions.DevException;
import com.gdsp.dev.core.cases.service.IQueryService;
import com.gdsp.dev.core.model.param.Sorter;
import com.gdsp.dev.core.model.query.Condition;
import com.gdsp.platform.config.global.model.DefDocListVO;
import com.gdsp.platform.config.global.model.DefDocVO;

/**
 * 系统码表类型服务
 * @author wqh 2017年8月28日16:27:22
 */
public interface IDefDocListService extends IQueryService<DefDocListVO> {

	/**
	 * 根据主键查询
	 * @param id
	 * @return DefDocListVO
	 * @throws DevException
	 */
    public DefDocListVO findOne(String id) throws DevException;

	/**
	 * 保存 
	 * @param defDocListVO  参数说明
	 * @return void 返回值说明
	 */
    public void saveDefDocList(DefDocListVO defDocListVO);

	/**
	 * 删除操作
	 * @param ids 参数说明
	 * @return void 返回值说明
	 * 删除原因：系统码表不能通过页面操作来删除
	 */
//    public void delete(String[] ids);

	/**
	 * 加载操作对象
	 * @param id
	 * @return 参数说明
	 * @return DefDocListVO 返回值说明
	 */
    public DefDocListVO load(String id);

	/**
	 * 查询所有系统码表类型
	 * @param condition
	 * @param page
	 * @param sort
	 * @return Page<DefDocListVO> 参数说明
	 */
    Page<DefDocListVO> queryDefDocListVOByCondition(@Param("condition") Condition condition, Pageable page, @Param("sort") Sorter sort);

    /**
	 * 查询所有系统码表及其类型
	 * @return Map<String,List<DefDocVO>> 返回类型
	 * TODO 位置挪移至接口IDefDocService    wqh 2017-8-28 10:52:34
	 */
//    Map<String, List<DefDocVO>> findAllDocListWithDocs();

	/**
	 * 根据类型ID查询系统码表类型(包含码表数据项) TODO 错误写法，待更正
	 * @param typeid
	 * @return List<DefDocVO>
	 */
    public List<DefDocVO> getDefDocsByTypeID(String typeid);

	/**
	 * 是否是系统预置数据
	 * @param id 数据字典的ID
	 * @return List<String> 返回所有查询数据项的isPreset字段，此字段如果为“Y”，则不能删除。
	 */
    public List<String> isPreset(String[] id);

    public boolean synchroCheck(DefDocListVO defDocListVO);

	/**
	 * 根据编码code查询系统码表类型
	 * @param typecode
	 * @return List<DefDocListVO> 系统码表分类
	 */
    public List<DefDocListVO> queryByTypeCode(String typecode);
    
    /**
	 * 通过类型编码查询id <br/>
	 * @param typeCode 码表类型编码
	 * @author wqh 2017年8月28日16:18:46
	 */
	public String findIdByTypeCode(String typeCode);
    
}
