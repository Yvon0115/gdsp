package com.gdsp.ptbase.appcfg.model;

import com.gdsp.dev.core.model.entity.AuditableEntity;
import com.gdsp.ptbase.portal.model.IPortletAction;

/**
 * 
* @ClassName: WidgetActionVO
* (资源动作VO类)
* @author songxiang
* @date 2015年10月28日 下午2:08:14
*
 */
public class WidgetActionVO extends AuditableEntity implements IPortletAction {

    /**
     * 序列id
     */
    private static final long  serialVersionUID   = 8887916626478950823L;
    /**
     * 所有的部件类型
     */
    public static final String ALL_WIDGETTYPE     = "All";
    /**
     * 私有的部件类型
     */
    public static final String PRIVATE_WIDGETTYPE = "private";
    /**
     * 操作编码
     */
    private String             code;
    /**
     * 操作显示名称
     */
    private String             name;
    /**
     * 操作模板
     */
    private String             template;
    /**
     * 描述信息
     */
    private String             memo;
    /**
     * 所属widget类型
     */
    private String             widgettype;
    /**
     * 所属widgetid
     */
    private String             widgetid;
    /**
     * 参照显示名称
     */
    private String             refshowvalue;
    /**
     * 排序码
     */
    private Integer            sortnum;
    private String             type;

    public String getRefshowvalue() {
        return refshowvalue;
    }

    public void setRefshowvalue(String refshowvalue) {
        this.refshowvalue = refshowvalue;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getWidgettype() {
        return widgettype;
    }

    public void setWidgettype(String widgettype) {
        this.widgettype = widgettype;
    }

    public String getWidgetid() {
        return widgetid;
    }

    public void setWidgetid(String widgetid) {
        this.widgetid = widgetid;
    }

    public Integer getSortnum() {
        return sortnum;
    }

    public void setSortnum(Integer sortnum) {
        this.sortnum = sortnum;
    }

    @Override
    public String getActionId() {
        return getCode();
    }

    @Override
    public String getActionContent() {
        return getTemplate();
    }
}
