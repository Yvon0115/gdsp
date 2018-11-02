package com.gdsp.dev.base.lang;

import java.io.Serializable;

/**
 * 自定布尔，用于转化表示布尔值的字符串整数字符等
 * @author paul.yang
 * @version 1.0 2014年9月24日
 * @since 1.6
 */
public class DBoolean implements Serializable, Comparable<Object> {

    /**
     * 序列id
     */
    private static final long    serialVersionUID = -3436081633966148950L;
    /**
     * <code>自定义布尔类</code>对象代表<code>true</code>的对象.
     */
    public static final DBoolean TRUE             = new DBoolean(true);
    /**
     * <code>自定义布尔类</code>对象代表<code>false</code>的对象..
     */
    public static final DBoolean FALSE            = new DBoolean(false);
    /**保存当前对象的布尔值*/
    private boolean              value            = false;

    /**
     * 根据字符构造自定义布尔对象，'Y'和'y'时为true,其它为false。
     * @param ch 字符 
     * @deprecated replaced by {@link #valueOf(char)}
     */
    public DBoolean(char ch) {
        value = (ch == 'Y' || ch == 'y');
    }

    /**
     * 根据整数构造自定义布尔对象，>0时为true,其它为false。
     * @param num 字符 
     * @deprecated replaced by {@link #valueOf(int)}
     */
    public DBoolean(int num) {
        value = (num > 0);
    }

    /**
     * 根据字符串构造自定义布尔对象，忽略大小写后为"Y"或者"TRUE"时为true,其它为false。
     * @param num 字符 
     * @deprecated replaced by {@link #valueOf(String)}
     */
    public DBoolean(String val) {
        if (val != null && ("Y".equalsIgnoreCase(val) || "TRUE".equalsIgnoreCase(val)))
            value = true;
        else
            value = false;
    }

    /**
     * 根据布尔值构造自定义布尔对象
     * @param b 布尔值
     */
    public DBoolean(boolean b) {
        value = b;
    }

    /**
     * 将布尔值生成为自定义布尔对象
     * @param b 传入布尔值
     * @return 自定义布尔对象
     */
    public static DBoolean valueOf(boolean b) {
        return (b ? TRUE : FALSE);
    }

    /**
     * 将字符生成为自定义布尔对象，'Y'和'y'时为true,其它为false。
     * @param ch 字符
     * @return 自定义布尔对象
     */
    public static DBoolean valueOf(char ch) {
        return (ch == 'Y' || ch == 'y') ? TRUE : FALSE;
    }

    /**
     * 将整数生成为自定义布尔对象，>0时为true,其它为false。
     * @param num 整数
     * @return 自定义布尔对象
     */
    public static DBoolean valueOf(int num) {
        return (num > 0 ? TRUE : FALSE);
    }

    /**
     * 将字符串生成为自定义布尔对象，忽略大小写后为"Y"或者"TRUE"时为true,其它为false。
     * @param val 字符串
     * @return 自定义布尔对象
     */
    public static DBoolean valueOf(String val) {
        if (val == null)
            return FALSE;
        if ("Y".equalsIgnoreCase(val) || "TRUE".equalsIgnoreCase(val) || val.equals("1") || val.equalsIgnoreCase("YES"))
            return TRUE;
        return FALSE;
    }

    /**
     * 返回对象boolean型值
     */
    public boolean booleanValue() {
        return value;
    }

    /**
     * 返回对象boolean型值
     */
    public int intValue() {
        return value ? 1 : 0;
    }

    /**
     * 返回表示该对象String型值，true时为"Y",false时为"Y"。
     */
    public String toString() {
        return value ? "Y" : "N";
    }

    /**
     * 比较两对象值是否相等(不是比较对象本身是否为同一对象)。
     */
    public boolean equals(Object obj) {
        if ((obj != null) && (obj instanceof DBoolean)) {
            return value == ((DBoolean) obj).booleanValue();
        }
        return false;
    }

    /**
     * 生成接收方的散列代码---和Boolean相同。
     */
    public int hashCode() {
        return value ? 1231 : 1237;
    }

    /* (non-Javadoc)
     * @see java.lang.Comparable#compareTo(java.lang.Object)
     */
    public int compareTo(Object o) {
        if (o == null)
            return 1;
        return toString().compareTo(o.toString());
    }
}
