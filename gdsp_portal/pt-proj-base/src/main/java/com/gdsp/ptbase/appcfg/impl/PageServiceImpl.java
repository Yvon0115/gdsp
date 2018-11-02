package com.gdsp.ptbase.appcfg.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gdsp.dev.core.common.AppContext;
import com.gdsp.dev.core.model.param.Sorter;
import com.gdsp.dev.core.model.query.Condition;
import com.gdsp.ptbase.appcfg.dao.IPageDao;
import com.gdsp.ptbase.appcfg.model.LayoutColumnVO;
import com.gdsp.ptbase.appcfg.model.PageVO;
import com.gdsp.ptbase.appcfg.model.PageWidgetVO;
import com.gdsp.ptbase.appcfg.service.ILayoutBoxBuilder;
import com.gdsp.ptbase.appcfg.service.IPageService;
import com.gdsp.ptbase.appcfg.service.IPageWidgetService;
import com.gdsp.ptbase.appcfg.service.IWidgetQueryService;
import com.gdsp.ptbase.portal.model.IPortalPage;
import com.gdsp.ptbase.portal.model.IPortlet;
import com.gdsp.ptbase.portal.model.IPortletLayoutBox;
import com.gdsp.ptbase.portal.model.IPortletMeta;
import com.gdsp.ptbase.portal.service.IPortalPageLoadService;

@Service
@Transactional(readOnly = true)
public class PageServiceImpl implements IPageService, IPortalPageLoadService {

    /**
     * dao对象
     */
    @Resource
    private IPageDao            pageDao;
    /**
     * 部件布局服务
     */
    @Resource
    private IPageWidgetService  pwService;
    /**
     * 部件查询服务
     */
    @Resource
    private IWidgetQueryService wqServicce;
    /**
     * 默认布局构建服务
     */
    @Resource(name = "layoutColumnBuilder")
    private ILayoutBoxBuilder   defaultBuilder;

    @Transactional
    @Override
    public PageVO insert(PageVO page) {
        pageDao.insert(page);
        return page;
    }

    @Transactional
    @Override
    public PageVO update(PageVO page) {
        pageDao.update(page);
        return page;
    }

    @Transactional
    @Override
    public void delete(String... ids) {
        //删除页面关联的部件
        pwService.deleteWidgetByPageId(ids);
        //删除页面
        pageDao.delete(ids);
    }

    @Override
    public PageVO load(String id) {
        return pageDao.load(id);
    }

    @Override
    public List<PageVO> findListByCondition(Condition cond, Sorter sort) {
        return pageDao.findListByCondition(cond, sort);
    }

    @Override
    public int findCountByCondition(Condition cond) {
        return pageDao.findCountByCondition(cond);
    }

    @Override
    public int findMaxSortNum(String dirId) {
        Integer i = pageDao.findMaxSortNum(dirId);
        return i == null ? 0 : i.intValue();
    }

    @Transactional
    @Override
    public void sort(String[] pageId) {
        for (int i = 0; i < pageId.length; i++) {
            PageVO p = load(pageId[i]);
            p.setSortnum(i);
            pageDao.update(p);
        }
    }

    @Transactional
    @Override
    public void savePageConfig(PageVO page, String del_comp, PageWidgetVO[] pw1, PageWidgetVO[] pw2, PageWidgetVO[] pw3) {
        // 更新page
        PageVO p = load(page.getId());
        p.setPage_name(page.getPage_name());
        p.setPage_desc(page.getPage_desc());
        p.setLayout_id(page.getLayout_id());
        update(p);
        // 删除页面组件
        String[] delIds = del_comp.split(",");
        pwService.delete(delIds);
        List<PageWidgetVO> newComp = new ArrayList<PageWidgetVO>();
        List<PageWidgetVO> updateComp = new ArrayList<PageWidgetVO>();
        // 给每一列组件赋sortnum、column_id、page_id、widget_style
        List<LayoutColumnVO> lc = wqServicce.getColumnInfoByLayout(page.getLayout_id());
        PageWidgetVO[] tmp = null;
        for (int i = 0; i < lc.size(); i++) {
            if (i == 0) {
                tmp = pw1;
            } else if (i == 1) {
                tmp = pw2;
            } else {
                tmp = pw3;
            }
            for (int j = 0; tmp != null && j < tmp.length; j++) {
                tmp[j].setColumn_id(lc.get(i).getColumn_id());
                tmp[j].setSortnum(j);
                tmp[j].setPage_id(page.getId());
                tmp[j].setWidget_style("color-white");
                if (tmp[j].getId().startsWith("new_widget_")) {
                    tmp[j].setId(null);
                    newComp.add(tmp[j]);
                } else {
                    updateComp.add(tmp[j]);
                }
            }
        }
        for (int i = 0; i < newComp.size(); i++) {
            pwService.insert(newComp.get(i));
        }
        for (int i = 0; i < updateComp.size(); i++) {
            pwService.update(updateComp.get(i));
        }
    }

    @Override
    public IPortalPage loadPortalPage(String key) {
        PageVO pageVO = pageDao.load(key);
        Map<String, List<PageWidgetVO>> widgets = pwService.findFullWidgetByPageId(key);
        pageVO.setWidgets(widgets);
        List<IPortletLayoutBox> layouts = getLayoutBuilder(pageVO.getLayoutType()).build(pageVO);
        
//*****原有的设计中，使用了一个自定义的linkedhashmap数据结构，结构过于复杂，以至于default-external-layout.ftl页面中较难取到配置信息，                 ******//
//*****解决方法：因原有设计涉及面较广，在不改变数据结构原有设计上，把查询出来的 widgets中的配置信息提取出来，通过setPreference方法再存入IPortletMeta                           ******//
       
        //<#list page.layouts as layout>       layout  对应    iPortletLayoutBox                                                  
        for (IPortletLayoutBox iPortletLayoutBox : layouts) {
        	if (iPortletLayoutBox.getPortlets()!=null) {
        	IPortlet[] portlets = iPortletLayoutBox.getPortlets();
        	for (IPortlet iPortlet : portlets) {
        		externalResource(widgets, iPortlet);
        	}
        	}
		}
        pageVO.setLayouts(layouts);
        return pageVO;
    }

	/**
	 * 默认添加外部资源配置属性参数，用于配置外部资源的展示
	 * @param widgets
	 * @param iPortlet
	 */ 
	private void externalResource(Map<String, List<PageWidgetVO>> widgets, IPortlet iPortlet) {
		if (iPortlet!=null) {
      //<#list layout.portlets as portlet>    portlet 对应    iPortlet  
		IPortletMeta meta = iPortlet.getMeta();
//设置外部资源以后，如果用户没有设置外部资源配置属性参数，默认添加外部资源配置属性参数。当拓展了外部资源配置属性后需要更改判断逻辑。		
		String regex="^([hH][tT]{2}[pP]://|[hH][tT]{2}[pP][sS]://)(([A-Za-z0-9-~]+).)+([A-Za-z0-9-~\\/])+$";
			if (meta.getPortletURL().matches(regex)&&(meta.getPreference(IPortletMeta.EXTERNALRESOURCE)==null||StringUtils.isNoneBlank(meta.getPreference(IPortletMeta.EXTERNALRESOURCE)))) {
				meta.setPreference(IPortletMeta.EXTERNALRESOURCE,"yes");
		}
//高度配置信息的添加,是否显示标题
			  for (Entry<String, List<PageWidgetVO>> m : widgets.entrySet()) { 
					List<PageWidgetVO> list = m.getValue();
					for (PageWidgetVO pageWidgetVO : list) {
						Integer height = pageWidgetVO.getHeight();
						meta.setPreference("height", height.toString());
						meta.setPreference("title_show", pageWidgetVO.getTitle_show().toString());
					}
			  }
	}
}


    protected ILayoutBoxBuilder getLayoutBuilder(String layoutType) {
        if (layoutType == null)
            return defaultBuilder;
        ILayoutBoxBuilder builder = AppContext.getContext().lookup("layout" + StringUtils.capitalize(layoutType.toLowerCase()) + "Builder", ILayoutBoxBuilder.class);
        if (builder != null)
            return builder;
        return defaultBuilder;
    }

    @Override
    public List<PageVO> queryPageVOListByDirid(String dirId,Condition condition,Sorter sort) {
        return pageDao.queryPageVOListByDirid(dirId,condition,sort);
    }

    @Override
    @Transactional
    public void batchUpdate(List<PageVO> page) {
        if (page != null && page.size() > 0) {
            Iterator<PageVO> it = page.iterator();
            while (it.hasNext()) {
                PageVO vo = it.next();
                pageDao.batchUpdate(vo);
            }
        }
    }

	@Override
    @Transactional
	public void updateDefLayout(String pageId) {
		String layoutId="np_layout4";
		pageDao.updateDefLayout(layoutId,pageId);
		
	}
}
