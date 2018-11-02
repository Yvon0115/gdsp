package com.gdsp.platform.common.utils;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
* @ClassName: ParamFunctionMaster
* (参数函数处理类)
* @author hongwei.xu
* @date 2015年8月13日 下午4:19:58
*
 */
public class ParamFunctionMaster {
	private static final Logger logger = LoggerFactory.getLogger(ParamFunctionMaster.class);

    /**
     * 
    * @Title: getFunValueByInvoke
    * (函数处理方法)
    * @param @param classPath
    * @param @param strmethod
    * @param @return
    * @return String    返回类型
    *      */
    public static Object getFunValueByInvoke(String classPath, String strmethodAndParams) {
        Object resultVal = null;
        try {
            Class<?> __class = Class.forName(classPath);
            //默认规则所有参数传递用@分隔
            String strs[] = strmethodAndParams.split("@");
            if (strs.length >= 2) {
                int cnt = 1;
                List<Object> paraList = new ArrayList<Object>();
                List<Class> classList = new ArrayList<Class>();
                while (cnt < strs.length) {
                    paraList.add(strs[cnt]);
                    classList.add(strs[cnt].getClass());
                    cnt++;
                }
                Method method = __class.getDeclaredMethod(strs[0], classList.toArray(new Class[0]));
                resultVal = method.invoke(__class.newInstance(), paraList.toArray(new Object[0]));
            } else {
                Method method = __class.getDeclaredMethod(strs[0]);
                resultVal = method.invoke(__class.newInstance());
            }
        } catch (Exception e) {
        	logger.error(e.getMessage(),e);
        }
        return resultVal;
    }
}
