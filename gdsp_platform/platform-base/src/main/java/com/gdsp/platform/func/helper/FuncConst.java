package com.gdsp.platform.func.helper;

/**
 * 用菜单常量枚举类来替代此类
 * @see MenuConst
 * @author blank
 * @version 1.0 2018年7月12日 上午10:28:56
 * @since 1.7
 */
@Deprecated
public interface FuncConst {

    //菜单类型 '0:一级分类,1:下级分类,2:管理菜单,3:业务菜单,4:页面菜单'
    public static final int    MENUTYPE_FIRSTLEVEL = 0;
    public static final int    MENUTYPE_MULTILEVEL = 1;
    public static final int    MENUTYPE_ADMIN      = 2;
    public static final int    MENUTYPE_BUSI       = 3;
    public static final int    MENUTYPE_PAGE       = 4;
    public static final String HOME_FUNCODE        = "0001";
}
