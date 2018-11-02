package com.gdsp.platform.log.model;

import com.gdsp.dev.core.model.entity.AuditableEntity;
import com.gdsp.platform.func.helper.FuncConst;
import com.gdsp.platform.func.helper.MenuConst;

/**
 * 
* @ClassName: ResAccessLog
* (资源访问日志)
* @author hongwei.xu
* @date 2015年9月16日 下午3:17:11
 */
public class ResAccessLogVO extends AuditableEntity {

    private static final long  serialVersionUID = 1L;
    public final static String TABLE_NAME       = "res_accesslog";
    /**
     * 被访问资源ID
     */
    protected String             res_id;
    /**
     * 被访问资源名称
     */
    protected String             name;
    /**
     * 资源类型 
     */
    protected String             type;
    /**
     * 资源路径
     */
    protected String             url;
    /**
     * 资源信息
     */
    protected String             msg;
    /**
     * 访问用户
     */
    protected String             pk_user;
    /**
     * 用户名称
     */
    protected String             username;
    
    //修改人：wangxiaolong
    //修改时间：2017-3-20
    //修改原因：需要对查询日志的展示进行修改，添加了新的展示字段
    /**
     * 功能编号
     */
    protected String 				funcode;
    
    /**
     * 功能名称
     */
    protected String 				funname;
    
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        //菜单类型 '0:一级分类,1:下级分类,2:管理菜单,3:业务菜单,4:页面菜单'
//        if ((FuncConst.MENUTYPE_ADMIN + "").equals(type)) {
        if ((MenuConst.MENUTYPE_ADMIN.getValue()).equals(type)) {
            return "管理菜单";
        } else if ((MenuConst.MENUTYPE_BUSI.getValue() + "").equals(type)) {
            return "业务菜单";
        } else if ((MenuConst.MENUTYPE_PAGE.getValue()).equals(type)) {
            return "页面菜单";
        } else if (( MenuConst.MENUTYPE_DIR.getValue()).equals(type)) {
            return "下级分类";
        }
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getRes_id() {
        return res_id;
    }

    public void setRes_id(String res_id) {
        this.res_id = res_id;
    }

    public String getPk_user() {
        return pk_user;
    }

    public void setPk_user(String pk_user) {
        this.pk_user = pk_user;
    }

	public String getFuncode() {
		return funcode;
	}

	public void setFuncode(String funcode) {
		this.funcode = funcode;
	}

	public String getFunname() {
		return funname;
	}

	public void setFunname(String funname) {
		this.funname = funname;
	}
    
    
}
