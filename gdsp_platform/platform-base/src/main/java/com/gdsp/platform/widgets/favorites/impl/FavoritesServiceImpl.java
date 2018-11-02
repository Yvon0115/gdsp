package com.gdsp.platform.widgets.favorites.impl;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gdsp.dev.core.common.AppContext;
import com.gdsp.dev.core.model.query.Condition;
import com.gdsp.dev.persist.result.MapListResultHandler;
import com.gdsp.platform.widgets.favorites.dao.IFavoritesDao;
import com.gdsp.platform.widgets.favorites.model.FavoritesVO;
import com.gdsp.platform.widgets.favorites.service.IFavoritesService;

@Service
@Transactional
public class FavoritesServiceImpl implements IFavoritesService {
	
	@Autowired
	IFavoritesDao favoritesDao;

	@Override
	public void save(FavoritesVO favoritesVO) {
		favoritesDao.insert(favoritesVO);
	}

	@Override
	public void delete(String id) {
		favoritesDao.delete(id);
	}

	@Override
	public boolean checkUnique(FavoritesVO favoritesVO) {
	    String createBy=AppContext.getContext().getContextUserId();
	    favoritesVO.setCreateBy(createBy);
	    //查询pid字段为null时的数据。用以区别文件夹内外的相同名字的页面。
	    //名字相同，但页面所处的文件夹名字不同也可以通过验证
	    if (StringUtils.isBlank(favoritesVO.getPid())) {
	    	favoritesVO.setPid("0");
	    	return favoritesDao.existBlankSameNameFavorites(favoritesVO) == 0;
		}else {
			
			return favoritesDao.existSameNameFavorites(favoritesVO) == 0;
		}
	}

	@Override
	public Map queryFavoritesVOByCondReturnMap(Condition condition, Sort sort) {
		MapListResultHandler handler = new MapListResultHandler("pid");
		favoritesDao.queryFavoritesVOMapListByCondition(condition, sort, handler);
        return handler.getResult();
	}

}
