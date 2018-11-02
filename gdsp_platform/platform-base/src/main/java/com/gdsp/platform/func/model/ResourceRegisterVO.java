package com.gdsp.platform.func.model;

/**
* @ClassName: ResourceRegisterVO
* (资源VO)
* @author shiyingjie
* @date 2015年11月17日 上午10:45:33
*
 */
public class ResourceRegisterVO extends BaseTabsRegisterVO {

    private static final long serialVersionUID = 1L;
    private int               parenttype;           //0:菜单，1:页面
    private String            parentid;             //菜单ID/页面ID

    public int getParenttype() {
        return parenttype;
    }

    public void setParenttype(int parenttype) {
        this.parenttype = parenttype;
    }

    public String getParentid() {
        return parentid;
    }

    public void setParentid(String parentid) {
        this.parentid = parentid;
    }

    @Override
    public String getTabEntityType() {
        if (this.tabEntityType != null && !"".equals(this.tabEntityType)) {
            return this.tabEntityType;
        } else {
            return this.getClass().getSimpleName();
        }
    }
}
