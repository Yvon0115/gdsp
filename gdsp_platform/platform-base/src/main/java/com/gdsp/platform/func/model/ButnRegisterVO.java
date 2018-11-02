package com.gdsp.platform.func.model;

/**
 * 按钮注册表
 * @author wwb
 * @version 1.0 2015/6/15
 */
public class ButnRegisterVO extends BaseTabsRegisterVO {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private String            funcode;              // 按钮编码
    private String            parentid;             // 所属菜单

    public String getFuncode() {
        return funcode;
    }

    public String getParentid() {
        return parentid;
    }

    public void setParentid(String parentid) {
        this.parentid = parentid;
    }

    public void setFuncode(String funcode) {
        this.funcode = funcode;
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
