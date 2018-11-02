package com.gdsp.platform.widgets.favorites.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Sort;

import com.gdsp.dev.core.model.query.Condition;
import com.gdsp.dev.persist.ext.MBDao;
import com.gdsp.dev.persist.result.MapListResultHandler;
import com.gdsp.platform.widgets.favorites.model.FavoritesVO;

@MBDao
public interface IFavoritesDao {
	
	void insert(FavoritesVO favoritesVO);
	
	void delete(@Param("id")String id);

	void queryFavoritesVOMapListByCondition(@Param("condition")Condition condition, @Param("sort")Sort sort,MapListResultHandler<?> handler);
	/**
	 * 重名校验
	 * @param favoritesVO
	 * @return
	 */
	int existSameNameFavorites(FavoritesVO favoritesVO);
	
	/**
	 * 空值校验
	 * @param favoritesVO
	 * @return
	 */
	int existBlankSameNameFavorites(FavoritesVO favoritesVO);
}
