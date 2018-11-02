package com.gdsp.ptbase.appcfg.model;

import com.gdsp.dev.base.lang.DBoolean;
import com.gdsp.dev.core.model.entity.BaseEntity;

public class ACTreeNodeVO extends BaseEntity {

    /**
    * @Fields serialVersionUID 
    */
    private static final long serialVersionUID = -7841364290421657443L;
    /**
     * 树节点名称
     */
    private String            text;
    /**
     * 图标
     */
    private String            icon;
    /**
     * 是否是目录
     */
    private DBoolean          isdir;
    /**
     * 父节点ID
     */
    private String            parent_id;
    /**
     * 操作组
     */
    private String            tags;
    /**
     * 是否可以选择
     */
    private DBoolean          selectable;
    /**
     * 页面类型
     */
    private String            page_type;
    /**
     * 构建树用ID
     */
    private String            btreeid;

    public String getBtreeid() {
        return btreeid;
    }

    public void setBtreeid(String btreeid) {
        this.btreeid = btreeid;
    }

    public String getParent_id() {
        return parent_id;
    }

    public void setParent_id(String parent_id) {
        this.parent_id = parent_id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public DBoolean getIsdir() {
        return isdir;
    }

    public void setIsdir(DBoolean isdir) {
        this.isdir = isdir;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public DBoolean getSelectable() {
        return selectable;
    }

    public void setSelectable(DBoolean selectable) {
        this.selectable = selectable;
    }

    public String getPage_type() {
        return page_type;
    }

    public void setPage_type(String page_type) {
        this.page_type = page_type;
    }
}
