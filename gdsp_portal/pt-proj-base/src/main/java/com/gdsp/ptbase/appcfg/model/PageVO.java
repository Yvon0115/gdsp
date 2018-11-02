package com.gdsp.ptbase.appcfg.model;

import java.util.List;
import java.util.Map;

import com.gdsp.dev.core.model.entity.AuditableEntity;
import com.gdsp.ptbase.portal.model.IPortalPage;
import com.gdsp.ptbase.portal.model.IPortletLayoutBox;

public class PageVO extends AuditableEntity implements IPortalPage {

    private static final long               serialVersionUID = -3791039430875059227L;
    private String                          page_name;                               // varchar(128) NULL,
    private String                          page_desc;                               // varchar(256) NULL,
    private String                          layout_id;                               // varchar(32) NULL,
    private String                          dir_id;                                  // varchar(32) NULL,
    private int                             sortnum;                                 // int(11) NULL,
    private String                          page_type;
    private String                          type;
    /**
     * 按布局分组的widget列表
     */
    private Map<String, List<PageWidgetVO>> widgets;
    /**
     * 页面对应的url地址,可为空,为空使用页面默认处理url
     */
    private String                          pageURL;
    /**
     * 布局类型默认为column
     */
    private String                          layoutType;
    /**
     * 页面数据准备接口对应的bean id
     */
    private String                          handlerBean;
    /**
     * 页面布局列表
     */
    private List<IPortletLayoutBox>         layouts          = null;

    public String getPage_name() {
        return page_name;
    }

    public void setPage_name(String page_name) {
        this.page_name = page_name;
    }

    public String getPage_desc() {
        return page_desc;
    }

    public void setPage_desc(String page_desc) {
        this.page_desc = page_desc;
    }

    public String getLayout_id() {
        return layout_id;
    }

    public void setLayout_id(String layout_id) {
        this.layout_id = layout_id;
    }

    public String getDir_id() {
        return dir_id;
    }

    public void setDir_id(String dir_id) {
        this.dir_id = dir_id;
    }

    public int getSortnum() {
        return sortnum;
    }

    public void setSortnum(int sortnum) {
        this.sortnum = sortnum;
    }

    public String getPage_type() {
        return page_type;
    }

    public void setPage_type(String page_type) {
        this.page_type = page_type;
    }

    @Override
    public String getPageURL() {
        return pageURL;
    }

    public void setPageURL(String pageURL) {
        this.pageURL = pageURL;
    }

    @Override
    public String getHandlerBean() {
        return handlerBean;
    }

    public void setHandlerBean(String handlerBean) {
        this.handlerBean = handlerBean;
    }

    @Override
    public String getLayoutType() {
        if (layoutType == null && "np_layout_tabs".equalsIgnoreCase(layout_id)) {
            return "tabs";
        }
        return layoutType;
    }

    public void setLayoutType(String layoutType) {
        this.layoutType = layoutType;
    }

    @Override
    public List<IPortletLayoutBox> getLayouts() {
        return layouts;
    }

    public void setLayouts(List<IPortletLayoutBox> layouts) {
        this.layouts = layouts;
    }

    @Override
    public String getTemplate() {
        return getPage_type();
    }

    public Map<String, List<PageWidgetVO>> getWidgets() {
        return widgets;
    }

    public void setWidgets(Map<String, List<PageWidgetVO>> widgets) {
        this.widgets = widgets;
    }
}
