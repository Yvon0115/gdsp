package com.gdsp.ptbase.appcfg.impl;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gdsp.dev.persist.result.MapListResultHandler;
import com.gdsp.ptbase.appcfg.dao.IPageWidgetDao;
import com.gdsp.ptbase.appcfg.model.PageWidgetVO;
import com.gdsp.ptbase.appcfg.service.IPageWidgetService;

/**
 * 页面部件服务实现类
 * @author paul.yang
 * @version 1.0 2015-8-5
 * @since 1.6
 */
@Service
@Transactional(readOnly = true)
public class PageWidgetServiceImpl implements IPageWidgetService {

    /**
     * 数据访问对象
     */
    @Resource
    private IPageWidgetDao dao;

    @Override
    @Transactional
    public PageWidgetVO insert(PageWidgetVO widget) {
        dao.insert(widget);
        return widget;
    }

    @Override
    @Transactional
    public PageWidgetVO update(PageWidgetVO widget) {
        dao.update(widget);
        return widget;
    }

    @Override
    public PageWidgetVO load(String id) {
        return dao.load(id);
    }

    @Override
    @Transactional
    public void delete(String... ids) {
        if (ids != null && ids.length > 0) {
            dao.delete(ids);
        }
    }

    @Override
    public List<PageWidgetVO> findWidgetByWidgetId(String widgetId) {
        return dao.findWidgetByWidgetId(widgetId);
    }

    @Override
    public Map<String, List<PageWidgetVO>> findWidgetByPageId(String pageId) {
        LinkedHashMap<String, List<PageWidgetVO>> map = new LinkedHashMap<>();
        MapListResultHandler<PageWidgetVO> handler = new MapListResultHandler<>("column_id", map);
        dao.findWidgetByPageId(pageId, handler);
        Map<String, List<PageWidgetVO>> result = handler.getResult();
        Collection<List<PageWidgetVO>> values = result.values();
      
        for (List<PageWidgetVO> list : values) {
         pageWidgetToheavy(list);
		}
        return handler.getResult();
    }

	/**
	 * 去除组件容器内的重复数据
	 * @param list 组件容器
	 */ 
	private void pageWidgetToheavy(List<PageWidgetVO> list) {
		for (int i = 0; i < list.size(); i++) {
			for (int j = list.size()-1; j>i;j--) {
				if (list.get(i).getId().equalsIgnoreCase(list.get(j).getId())) {
					list.remove(j);
				}
			}
		}
	}

    @Override
    public Map<String, List<PageWidgetVO>> findFullWidgetByPageId(String pageId) {
        LinkedHashMap<String, List<PageWidgetVO>> map = new LinkedHashMap<>();
        MapListResultHandler<PageWidgetVO> handler = new MapListResultHandler<>("column_id", map);
        dao.findFullWidgetByPageId(pageId, handler);
        return handler.getResult();
    }

    @Override
    public void deleteWidgetByPageId(String[] pageId) {
        dao.deleteWidgetByPageId(pageId);
    }

    @Override
    public String findWidgetIdByPageId(String id) {
        return dao.findWidgetIdByPageId(id);
    }

	@Override
    @Transactional
	public void updateDefColumn(String pageId) {
		String columnId="np_column8";
		dao.updateDefColumn(columnId,pageId);
	}
}
