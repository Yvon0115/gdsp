package com.gdsp.dev.persist.ext;

import org.apache.ibatis.session.SqlSession;

import com.gdsp.dev.core.common.AppContext;

/**
 * 上下文变量助手类
 * @author paul.yang
 * @version 1.0 2014年11月5日
 * @since 1.6
 */
public class SqlTemplateHelper {

    /**
     * sql会话
     */
    private static SqlSession sqlSessionTemplate = null;

    /**
     * 获取单例
     * @return
     */
    public static SqlSession getSqlSession() {
        if (sqlSessionTemplate == null) {
            sqlSessionTemplate = AppContext.getContext().lookup("sqlSessionTemplate", SqlSession.class);
        }
        return sqlSessionTemplate;
    }
}
