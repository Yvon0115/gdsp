package com.gdsp.platform.widgets.favorites.service;

import java.util.Map;

import org.springframework.data.domain.Sort;

import com.gdsp.dev.core.model.query.Condition;
import com.gdsp.platform.widgets.favorites.model.FavoritesVO;

/**
 * 收藏夹接口
 * @author gy
 * @version 1.0 2018年7月10日 上午11:15:34
 * @since 1.7
 */
public interface IFavoritesService {

	/**
	 * 保存收藏
	 * @param favoritesVO 收藏对象
	 */
	void save(FavoritesVO favoritesVO);
	
	/**
	 * 删除收藏
	 * @param id 收藏对象id
	 */
	void delete(String id);	

	/**
	 * 唯一性校验
	 * @param favoritesVO 收藏对象
	 * @return
	 */
	boolean checkUnique(FavoritesVO favoritesVO);
	
	/**
	 * 查询收藏夹
	 * @param condition 条件
	 * @param sort 排序
	 * @return
	 */
	Map<?, ?> queryFavoritesVOByCondReturnMap(Condition condition,Sort sort);
}
