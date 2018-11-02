package com.gdsp.platform.grant.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 正则验证工具类
 * @author lyf  2016.12.29
 */
public class RegExpValidatorUtils {
    
    /**
     * 验证邮箱
     * @param str 待验证的字符串
     * @return 如果是符合的字符串，返回<b>true</b>，否则为<b>false</b>
     */
    public static boolean isEmail(String str){
        String regex = "^([\\w-\\.]+)@((\\[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([\\w-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(str);
        return m.matches();
    }
    
    /**
     * 验证邮箱
     * @param str 待验证的字符串
     * @return 如果不是符合的字符串，返回<b>true</b>，否则为<b>false</b>
     */
    public static boolean isNotEmail(String str){
        String regex = "^([\\w-\\.]+)@((\\[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([\\w-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(str);
        boolean t = m.matches();
        boolean f = !t;
        return f;
    }
    
    /**
     * 验证座机号
     * @param str 待验证的字符串
     * @return 如果是符合的字符串，返回<b>true</b>，否则为<b>false</b>
     */
    public static boolean isTelephone(String str){
        String regex = "^(((\\d{3,4})|\\d{3,4}-)?\\d{7,8}(-\\d{3}))|(((\\d{3,4})|\\d{3,4}-)?\\d{7,8})$";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(str);
        return m.matches();
    }
    
    /**
     * 验证座机号
     * @param str 待验证的字符串
     * @return 如果不是符合的字符串，返回<b>true</b>，否则为<b>false</b>
     */
    public static boolean isNotTelephone(String str){
//        String regex = "^(((\\d{3,4})|\\d{3,4}-)?\\d{7,8}(-\\d{3}))|(((\\d{3,4})|\\d{3,4}-)?\\d{7,8})$";
//		将前端添加用户与用模板导入用户的固定电话验证调整一致
    	String regex = "^((\\+\\d{1,3}\\-\\d{3,4}\\-\\d{7,8})|(\\(\\d{1,3}\\-\\d{3,4}\\)\\d{7,8})|(\\d{3,4}\\-\\d{7,8})|(\\d{7,8}))$";
    	Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(str);
        boolean t = m.matches();
        boolean f = !t;
        return f;
    }
    
    /**
     * 验证手机号
     * @param str 待验证的字符串
     * @return 如果是符合的字符串，返回<b>true</b>，否则为<b>false</b>
     */
    public static boolean isMobilephone(String str){
        String regex = "^[1]+[34578]+\\d{9}$";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(str);
        return m.matches();
    }
    
    /**
     * 验证手机号
     * @param str 待验证的字符串
     * @return 如果不是符合的字符串，返回<b>true</b>，否则为<b>false</b>
     */
    public static boolean isNotMobilephone(String str){
        String regex = "^[1]+[34578]+\\d{9}$";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(str);
        boolean t = m.matches();
        boolean f = !t;
        return f;
    }
    
    /**
     * 验证名称（屏蔽特殊字符）
     * @param str 待验证的字符串
     * @return 如果是符合的字符串，返回<b>true</b>，否则为<b>false</b>
     */
    public static boolean isRightName(String str){
    	String regex = "^[a-zA-Z0-9\u4E00-\u9FA5]+$";
    	Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(str);
        return m.matches();
    }
    
    /**
     * 验证名称（屏蔽特殊字符）
     * @param str 待验证的字符串
     * @return 如果不是符合的字符串，返回<b>true</b>，否则为<b>false</b>
     */
    public static boolean isNotRightName(String str){
    	String regex = "^[a-zA-Z0-9\u4E00-\u9FA5]+$";
    	Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(str);
        boolean t = m.matches();
        boolean f = !t;
        return f;
    }
    
    /**
     * 验证名称（不屏蔽特殊字符“.:_()[]【】”）
     * @param str 待验证的字符串
     * @return 如果是符合的字符串，返回<b>true</b>，否则为<b>false</b>
     */
    public static boolean isValidName(String str){
    	String regex = "^[a-zA-Z0-9._\u4E00-\u9FA5._()\\[\\]【】（）\\-\\:\\：]+$";
    	Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(str);        
        boolean t = m.matches();
        boolean f = !t;
        return f;
    }
}
