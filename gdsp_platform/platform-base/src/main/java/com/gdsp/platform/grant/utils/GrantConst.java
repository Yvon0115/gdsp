package com.gdsp.platform.grant.utils;

/**
 * GDSP权限系统常量定义
 * @since 2016年12月26日 上午9:19:40
 */
public interface GrantConst {

    //用户类型 0:超级管理员,1:用户
    public static final int    USERTYPE_ADMIN        = 0;
    public static final int    USERTYPE_USER         = 1;
    //性别'0:男,1:女
    public static final int    USER_SEX_MALE         = 0;
    public static final int    USER_SEX_FEMALE       = 1;
    public static final String USER_SEX_MALE_SHOW    = "男";
    public static final String USER_SEX_FEMALE_SHOW  = "女";
    public static final int    OBJ_TYPE_USER         = 1;
    public static final int    OBJ_TYPE_ROLE         = 2;
    // 全局机构主键
    public static final String ORG_GLOBAl            = "global00000000000000000000000001";
    // 是否限制管理员权限
    public static final String PARAM_ISLIMIT         = "RMS001";
    //是否显示安全级别
    public static final String PARAM_ISSHOWSAFELEVEL = "RMS002";
}
