package com.gdsp.dev.core.common;

import java.io.Serializable;
import java.util.List;

import com.gdsp.dev.base.utils.data.ValuePair;

/**
 * 访问的界面相关信息
 * @author yangbo
 * @version 1.0 2014-7-10
 * @since 1.6
 */
public class RequestInfo implements Serializable{

    /**
     * 
     */
    private static final long serialVersionUID = -8070911854556159980L;
    /**
     * 菜单id
     */
    private String          menuId     = null;
    /**
     * 菜单编码
     */
    private String          menuCode   = null;
    /**
     * 菜单类型
     */
    private int             menuType   = -1;
    /**
     * 菜单名称
     */
    private String          menuName   = null;
    /**
     * 模块id,顶层id
     */
    private String          moduleId   = null;
    /**
     * 界面导航路径标识数组
     */
    private List<String>    paths      = null;
    /**
     * 界面面包屑
     */
    private List<ValuePair> breadCrumb = null;

    public String getMenuId() {
        return menuId;
    }

    public void setMenuId(String menuId) {
        this.menuId = menuId;
    }

    public String getMenuCode() {
        return menuCode;
    }

    public void setMenuCode(String menuCode) {
        this.menuCode = menuCode;
    }

    public int getMenuType() {
        return menuType;
    }

    public void setMenuType(int menuType) {
        this.menuType = menuType;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public String getModuleId() {
        if (moduleId == null)
            return getMenuId();
        return moduleId;
    }

    public void setModuleId(String moduleId) {
        this.moduleId = moduleId;
    }

    public List<String> getPaths() {
        return paths;
    }

    public void setPaths(List<String> paths) {
        this.paths = paths;
    }

    public List<ValuePair> getBreadCrumb() {
        return breadCrumb;
    }

    public void setBreadCrumb(List<ValuePair> breadCrumb) {
        this.breadCrumb = breadCrumb;
    }
}
