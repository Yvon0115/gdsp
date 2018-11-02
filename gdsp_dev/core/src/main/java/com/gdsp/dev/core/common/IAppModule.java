package com.gdsp.dev.core.common;

/**
 * 应用模块接口
 * @author paul.yang
 * @version 1.0 2014年9月24日
 * @since 1.6
 */
public interface IAppModule {

    /**
     * 模块名前缀
     */
    public static final String MODULENAME_PROFIX = "module.";

    /**
     * 取得模块依赖的所有模块名称
     * @return
     */
    public String[] getDependModules();

    /**
     * 判断模块是否已经初始化完成
     * @return 布尔值
     */
    public boolean isInit();

    /**
     * 初始化是否完成
     */
    public void init();

    /**
     * 模块销毁
     */
    public void destory();
}
