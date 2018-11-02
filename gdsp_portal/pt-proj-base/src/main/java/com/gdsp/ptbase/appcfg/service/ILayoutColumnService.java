package com.gdsp.ptbase.appcfg.service;

import java.util.List;

import com.gdsp.platform.config.global.model.DefDocVO;
import com.gdsp.ptbase.appcfg.model.LayoutColumnVO;

/**
 * 列布局接口
 *
 * @author paul.yang
 * @version 1.0 2015-8-5
 * @since 1.6
 */
public interface ILayoutColumnService {

    /**
     * 根据列布局id加载所有布局列
     * @param layoutId 布局主键
     * @return 布局列
     */
    List<LayoutColumnVO> findLayoutColumns(String layoutId);

    /**
	 * 保存自定义布局
	 * @param columnIdArray 列宽，对应AC_LAYOUT_COLUMN的COLUMN_ID
	 * @param layout_id 布局，对应AC_LAYOUT_COLUMN的LAYOUT_ID
	 * @param defDocVO 系统码表CP_DEFDOC
	 * 
	 */
	void save(List<String> columnIdArray,DefDocVO defDocVO);
	
	/**
	 * 删除自定义布局
	 * @param layout_id
	 * @param page_ids
	 */
	void delete(String layout_id, List<String> page_ids);
	
	/**
	 * 检查自定义布局名称是否唯一
	 * @param typeCode  类型编码
	 * @param docName   详细类型名称
	 * @return
	 * @author lt
	 * @date 2017年9月26日 下午5:48:24
	 */
	public boolean docNameCheck(String typeCode, String docName);
	
}
