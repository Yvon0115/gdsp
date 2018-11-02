package com.gdsp.platform.func.model;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.gdsp.dev.core.utils.excel.ExcelVOAttribute;
import com.gdsp.dev.core.utils.tree.model.ITreeEntity;

/**
 * 菜单注册表
 * @author wwb
 * @version 1.0 2015/6/15
 */
public class MenuRegisterVO extends BaseTabsRegisterVO implements ITreeEntity {

    /**
     * 
     */
    private static final long  serialVersionUID = 1L;
    /**
     * 菜单参数名
     */
    public static final String MENUIDPARAM      = "__mn";
    private String             funcode;                  // 菜单编码
    private String             innercode;                // 内部码
    private String             dispcode;                 // 显示编码
    private String             parentid;                 // 上级节点id
    private int                funtype;                  //菜单类型 '0:一级分类,1:下级分类,2:管理菜单,3:业务菜单,4:页面菜单' null default '0',
    private String             ispower;                  // 权限控制
    private String             helpname;                 // 帮助文件
    private String             hintinf;                  // 提示信息
    private String             isenable;                 // 是否启用	
    private String             parentInnerCode;          //父节点的内部码
    private String             isSystemMenu;             //是否是内置菜单Y为是N为否
    private String             safeLevel;                //安全级别
    private String             pageid;                   // 页面ID，页面同步时记录
    private String             fileurl;
    //view field
    private String             isChecked;                //是否选中菜单
    private String             funname_safelevel;        //菜单加安全级别，用于授权菜单显示使用
    private String             isreport;                 //是否报表目录
    private String             isrootmenu;               //是否系统管理员菜单
    @ExcelVOAttribute(name = "系统名称", column = "A", isExport = true)
    private String             sysName;                  //系统名称，导出excel使用
    @ExcelVOAttribute(name = "菜单全路径", column = "B", isExport = true)
    private String             path;                     //菜单全路径
    
    /**----- 新增如下属性，支持页面菜单多页面  wqh 2016/11/18 -----*/
    private List<MenuRegisterVO> childrenPage;			 // 子页面
    private String pageName;							 // 页面名称
    private boolean defaultDisplay;						 // 默认显示页面标识
    private String iconField;                            // 自定义图标
    
    
    
    public String getIconField() {
        return iconField;
    }

    
    public void setIconField(String iconField) {
        this.iconField = iconField;
    }

    public String getIsreport() {
        return isreport;
    }

    public void setIsreport(String isreport) {
        this.isreport = isreport;
    }

    public String getFileurl() {
        return fileurl;
    }

    public void setFileurl(String fileurl) {
        this.fileurl = fileurl;
    }

    public String getIsSystemMenu() {
        return isSystemMenu;
    }

    public void setIsSystemMenu(String isSystemMenu) {
        this.isSystemMenu = isSystemMenu;
    }

    public String getIsChecked() {
        return isChecked;
    }

    public void setIsChecked(String isChecked) {
        this.isChecked = isChecked;
    }

    public String getInnercode() {
        return innercode;
    }

    public void setInnercode(String innercode) {
        this.innercode = innercode;
    }

    public String getFuncode() {
        return funcode;
    }

    public void setFuncode(String funcode) {
        this.funcode = funcode;
    }

    public String getDispcode() {
        return dispcode;
    }

    public void setDispcode(String dispcode) {
        this.dispcode = dispcode;
    }

    public String getParentid() {
        return parentid;
    }

    public void setParentid(String parentid) {
        this.parentid = parentid;
    }

    public int getFuntype() {
        return funtype;
    }

    public void setFuntype(int funtype) {
        this.funtype = funtype;
    }

    public String getIspower() {
        return ispower;
    }

    public void setIspower(String ispower) {
        this.ispower = ispower;
    }

    public String getHelpname() {
        return helpname;
    }

    public void setHelpname(String helpname) {
        this.helpname = helpname;
    }

    public String getHintinf() {
        return hintinf;
    }

    public void setHintinf(String hintinf) {
        this.hintinf = hintinf;
    }

    public String getIsenable() {
        return isenable;
    }

    public void setIsenable(String isenable) {
        this.isenable = isenable;
    }

    public String getParentInnerCode() {
        return parentInnerCode;
    }

    public void setParentInnerCode(String parentInnerCode) {
        this.parentInnerCode = parentInnerCode;
    }

    @Override
    public String getTableName() {
        return "st_menuregister";
    }

    public String getFuncUrl() {
        String url = getUrl();
        if (StringUtils.isEmpty(url))
            return url;
        if (url.indexOf("?") > -1) {
            url += "&";
        } else {
            url += "?";
        }
        return url + MENUIDPARAM + "=" + getId();
    }

    public String getPageid() {
        return pageid;
    }

    public void setPageid(String pageid) {
        this.pageid = pageid;
    }

    public String getSafeLevel() {
        return safeLevel;
    }

    public void setSafeLevel(String safeLevel) {
        this.safeLevel = safeLevel;
    }

    public String getFunname_safelevel() {
        if (this.funname_safelevel == null) {
            return funname;
        } else {
            return funname + "(" + funname_safelevel + ")";
        }
    }

    public void setFunname_safelevel(String funname_safelevel) {
        this.funname_safelevel = funname_safelevel;
    }

    /**
     * @return Returns the sysName.
     */
    public String getSysName() {
        return sysName;
    }

    /**
     * @param sysName The sysName to set.
     */
    public void setSysName(String sysName) {
        this.sysName = sysName;
    }

    /**
     * @return Returns the path.
     */
    public String getPath() {
        return path;
    }

    /**
     * @param path The path to set.
     */
    public void setPath(String path) {
        this.path = path;
    }

    public String getIsrootmenu() {
        return isrootmenu;
    }

    public void setIsrootmenu(String isrootmenu) {
        this.isrootmenu = isrootmenu;
    }

    @Override
    public String getTabEntityType() {
        if (this.tabEntityType != null && !"".equals(this.tabEntityType)) {
            return this.tabEntityType;
        } else {
            return this.getClass().getSimpleName();
        }
    }

    @Override
    public String getTabUrl() {
        if (this.tabUrl != null && !"".equals(this.tabUrl)) {
            return this.tabUrl;
        } else {
            return this.getFuncUrl();
        }
    }

	public List<MenuRegisterVO> getChildrenPage() {
		return childrenPage;
	}

	public void setChildrenPage(List<MenuRegisterVO> childrenPage) {
		this.childrenPage = childrenPage;
	}

	public String getPageName() {
		return pageName;
	}

	public void setPageName(String pageName) {
		this.pageName = pageName;
	}

	public boolean isDefaultDisplay() {
		return defaultDisplay;
	}

	public void setDefaultDisplay(boolean defaultDisplay) {
		this.defaultDisplay = defaultDisplay;
	}

	
}
