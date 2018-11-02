package com.gdsp.ptbase.appcfg.model;

import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.gdsp.dev.base.lang.DBoolean;
import com.gdsp.dev.core.data.json.Json2ObjectMapper;
import com.gdsp.dev.core.model.entity.AuditableEntity;
import com.gdsp.ptbase.appcfg.helper.PortletActionHelper;
import com.gdsp.ptbase.portal.model.IPortlet;
import com.gdsp.ptbase.portal.model.IPortletAction;
import com.gdsp.ptbase.portal.model.IPortletMeta;

public class PageWidgetVO extends AuditableEntity implements IPortlet {

    private static final long    serialVersionUID = 6310849886834279848L;
    /**
     * 日志变量
     */
    private static Logger        logger           = LoggerFactory.getLogger(PageWidgetVO.class);
    private String               column_id;                                                     // varchar(32),
    private String               widget_type;                                                   // varchar(32),
    private String               widget_style;                                                  // varchar(32),
    private String               widget_id;                                                     // varchar(32),
    private String               page_id;                                                       // varchar(32),
    private String               title;                                                         // varchar(128),
    private String               width            = "100%";                                     // varchar(50),
    private Integer              height           = 350;                                        // int,
    private DBoolean             auto_height      = DBoolean.FALSE;                             // char(1),
    private DBoolean             title_show      = DBoolean.FALSE;                             // char(1),
    private Integer              sortnum;
    /**
     * 组建选择按钮
     */
    private String               actions;
    /**
     * 对应的portlet定义
     */
    private IPortletMeta         meta;
    /**
     * portlet操作列表
     */
    private List<IPortletAction> portletActions;

    public String getActions() {
        return actions;
    }

    public void setActions(String actions) {
        this.actions = actions;
    }

    @JsonIgnore
    public String[] getAction() {
        String ac = getActions();
        if (StringUtils.isEmpty(ac))
            return null;
        return ac.split(",");
    }

    public void setAction(String[] action) {
        if (action == null || action.length == 0)
            setActions(null);
        setActions(StringUtils.join(action, ","));
    }

    public String getColumn_id() {
        return column_id;
    }

    public void setColumn_id(String column_id) {
        this.column_id = column_id;
    }

    public String getWidget_type() {
        return widget_type;
    }

    public void setWidget_type(String widget_type) {
        this.widget_type = widget_type;
    }

    public String getWidget_style() {
        return widget_style;
    }

    public void setWidget_style(String widget_style) {
        this.widget_style = widget_style;
    }

    public String getWidget_id() {
        return widget_id;
    }

    public void setWidget_id(String widget_id) {
        this.widget_id = widget_id;
    }

    public String getPage_id() {
        return page_id;
    }

    public void setPage_id(String page_id) {
        this.page_id = page_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public DBoolean getAuto_height() {
        return auto_height;
    }

    public void setAuto_height(DBoolean auto_height) {
        this.auto_height = auto_height;
    }

    public DBoolean getTitle_show() {
		return title_show;
	}

	public void setTitle_show(DBoolean title_show) {
		this.title_show = title_show;
	}

	public String toString() {
        try {
            return Json2ObjectMapper.getInstance().writeValueAsString(this);
        } catch (JsonProcessingException e) {
        	logger.error(e.getMessage(),e);
            return "{}";
        }
    }

    public Integer getSortnum() {
        return sortnum;
    }

    public void setSortnum(Integer sortnum) {
        this.sortnum = sortnum;
    }

    @JsonIgnore
    @Override
    public String getPortletTitle() {
        return getTitle();
    }

    @JsonIgnore
    @Override
    public IPortletMeta getMeta() {
        return meta;
    }

    public void setMeta(IPortletMeta meta) {
        this.meta = meta;
    }

    @JsonIgnore
    @Override
    public List<IPortletAction> getPortletActions() {
        if (portletActions == null && StringUtils.isNotEmpty(actions)) {
            portletActions = PortletActionHelper.getPortletActions(actions.split(","));
        }
        return portletActions;
    }

    public void setPortletActions(List<IPortletAction> portletActions) {
        this.portletActions = portletActions;
    }

    @Override
    public String getPreference(String key) {
        try {
            return BeanUtils.getSimpleProperty(this, key);
        } catch (Exception e) {
            logger.error("portlet can not support '" + key + "' preference",e);
            return getMeta().getPreference(key);
        }
    }

    @JsonIgnore
    @Override
    public String getTemplate() {
        return getMeta().getTemplate();
    }
}
