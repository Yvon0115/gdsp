package com.gdsp.platform.systools.datasource.model;

import com.gdsp.dev.core.model.entity.AuditableEntity;

/**
 * 数据源模型VO
 * @author songxiang
 * @date 2015年10月28日 下午2:07:51
 */
public class DataSourceVO extends AuditableEntity {

    /** serialVersionUID  */
    private static final long serialVersionUID = 4958670078132658701L;
    /** 数据源编码 */
    protected String            code;
    /** 名称 */
    protected String            name;
    /** 数据源类型  来自系统码表 */
    protected String            type;
    /** ip */
    protected String            ip;
    /** 端口号 */
    protected String            port;
    /** 链接地址 */
    protected String            url;
    /** 用户名 */
    protected String            username;
    /** 密码 */
    protected String            password;
    /** 描述 */
    protected String            comments;
    
    
	/** 是否为默认数据源 */
    private String            	isDefault;
    
    /** 驱动名 */
    protected String            driver;
    
    
    /** 默认值 */
    protected String            default_flag;
    /** #:dbAuth 是第三方认证登录域名 */
    private String            	span;
    /** BICognos报表默认权限标志  */
    private String            	permissionurl;
    /** 页面前缀URL */
    private String            	px_url;
    /** 同步路径 */	
    private String            	path;
    private String            	dirID;
    /** 产品版本号*/
    private String              product_version;
    
    private java.sql.Connection          Connection;
    
    /** 认证模式。-1:认证模式的缺省状态值;0:简单认证模式;1:LDAP认证模式;2:KERBEROS认证模式*/
    private String       		authentication_model;	
    /** 秘钥文件路径*/
    private String				keytabPath;
    /** kerberos配置文件路径*/
    private String				krbConfPath;
    /** 数据源分类*/
    private String 				classify;
    
    public DataSourceVO() {}
    
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getDefault_flag() {
        return default_flag;
    }

    public void setDefault_flag(String default_flag) {
        this.default_flag = default_flag;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getSpan() {
        return span;
    }

    public void setSpan(String span) {
        this.span = span;
    }

    public String getPermissionurl() {
        return permissionurl;
    }

    public void setPermissionurl(String permissionurl) {
        this.permissionurl = permissionurl;
    }

    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    /**
     * @return Returns the isDefault.
     */
    public String getIsDefault() {
        return isDefault;
    }

    /**
     * @param isDefault The isDefault to set.
     */
    public void setIsDefault(String isDefault) {
        this.isDefault = isDefault;
    }
    
    public String getPx_url() {
        return px_url;
    }

    
    public void setPx_url(String px_url) {
        this.px_url = px_url;
    }

    
    public String getPath() {
        return path;
    }

    
    public void setPath(String path) {
        this.path = path;
    }

    
    public String getDirID() {
        return dirID;
    }

    
    public void setDirID(String dirID) {
        this.dirID = dirID;
    }

    public String getProduct_version() {
        return product_version;
    }

    public void setProduct_version(String product_version) {
        this.product_version = product_version;
    }

    /**
     * @return the connection
     */
    public java.sql.Connection getConnection() {
        return Connection;
    }

    /**
     * @param connection the connection to set
     */
    public void setConnection(java.sql.Connection connection) {
        Connection = connection;
    }

	public String getAuthentication_model() {
		return authentication_model;
	}

	public void setAuthentication_model(String authentication_model) {
		this.authentication_model = authentication_model;
	}

	public String getKeytabPath() {
		return keytabPath;
	}

	public void setKeytabPath(String keytabPath) {
		this.keytabPath = keytabPath;
	}

	public String getKrbConfPath() {
		return krbConfPath;
	}

	public void setKrbConfPath(String krbConfPath) {
		this.krbConfPath = krbConfPath;
	}

	public String getClassify() {
		return classify;
	}

	public void setClassify(String classify) {
		this.classify = classify;
	}

}
