package com.gdsp.platform.func.model;

/**
 * 页面注册表
 * @author wwb
 * @version 1.0 2015/7/28
 */
public class PageRegisterVO extends BaseTabsRegisterVO {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private String            dispcode;             // 显示编码
    private String            menuid;               // 菜单ID
    private String            pageid;               // 页面ID
    
    private boolean defaultPage;
//    public static final boolean isDefault = false;
    
    

    public String getDispcode() {
        return dispcode;
    }

    public void setDispcode(String dispcode) {
        this.dispcode = dispcode;
    }

    public String getMenuid() {
        return menuid;
    }

    public void setMenuid(String menuid) {
        this.menuid = menuid;
    }

    public String getPageid() {
        return pageid;
    }

    public void setPageid(String pageid) {
        this.pageid = pageid;
    }

    @Override
    public String getTabEntityType() {
        if (this.tabEntityType != null && !"".equals(this.tabEntityType)) {
            return this.tabEntityType;
        } else {
            return this.getClass().getSimpleName();
        }
    }

	public boolean isDefaultPage() {
		return defaultPage;
	}

	public void setDefaultPage(boolean defaultPage) {
		this.defaultPage = defaultPage;
	}
}
