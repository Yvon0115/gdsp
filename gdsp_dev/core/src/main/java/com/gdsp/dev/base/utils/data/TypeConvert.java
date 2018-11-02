package com.gdsp.dev.base.utils.data;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gdsp.dev.base.lang.DBoolean;
import com.gdsp.dev.base.lang.DDate;
import com.gdsp.dev.base.lang.DDateTime;
import com.gdsp.dev.base.lang.DTime;

/**
 * 数据类型转换器
 * @author paul.yang
 * @version 1.0 2014年9月24日
 * @since 1.6
 */
public final class TypeConvert {

    /**日志对象不能依赖common包*/
    private static final Logger logger = LoggerFactory.getLogger(TypeConvert.class);
    /**日期格式化对象*/
    private final static String sdf1   = "yyyy-MM-dd";
    /**时间格式化对象*/
    private final static String sdf2   = "HH:mm:ss";
    /**日期时间格式化对象*/
    private final static String sdf3   = "yyyy-MM-dd HH:mm:ss";

    /**
     * 私有构造方法，防止实例化
     */
    private TypeConvert() {}

    /**
     * 将Object对象解析成字符串类型
     * @param obj 需要转换的对象
     * @return 字符串
     */
    public static final String parseString(Object obj) {
        if (obj == null) {
            return null;
        }
        return obj.toString();
    }

    /**
     * 将对象解析成字节类型
     * @param obj 需要转换的对象
     * @return 字节值
     */
    public static final byte parseByte(Object obj) {
        if (obj == null) {
            return 0;
        }
        if (obj instanceof Number) {
            return ((Number) obj).byteValue();
        }
        if (obj instanceof Boolean) {
            return (byte) (((Boolean) obj).booleanValue() ? 1 : 0);
        }
        String s;
        if ((s = obj.toString()).equals("")) {
            return 0;
        } else {
            return Byte.parseByte(s);
        }
    }

    /**
     * 将对象解析成字节类型
     * @param obj 需要转换的对象
     * @return 字节值
     */
    public static final short parseShort(Object obj) {
        if (obj == null) {
            return 0;
        }
        if (obj instanceof Number) {
            return ((Number) obj).shortValue();
        }
        if (obj instanceof Boolean) {
            return (short) (((Boolean) obj).booleanValue() ? 1 : 0);
        }
        String s;
        if ((s = obj.toString()).equals("")) {
            return 0;
        } else {
            return Short.parseShort(s);
        }
    }

    /**
     * 将对象解析成整数类型
     * @param obj 需转换的对象
     * @return 整数值
     */
    public static final int parseInt(Object obj) {
        if (obj == null) {
            return 0;
        }
        if (obj instanceof Number) {
            return ((Number) obj).intValue();
        }
        if (obj instanceof Boolean) {
            return !((Boolean) obj).booleanValue() ? 0 : 1;
        }
        String s;
        if ((s = obj.toString()).equals("")) {
            return 0;
        } else {
            return Integer.parseInt(s);
        }
    }

    /**
     * 将对象解析成长整类型
     * @param obj 需转换的对象
     * @return 长整数值
     */
    public static final long parseLong(Object obj) {
        if (obj == null) {
            return 0;
        }
        if (obj instanceof Number) {
            return ((Number) obj).longValue();
        }
        if (obj instanceof Boolean) {
            return !((Boolean) obj).booleanValue() ? 0L : 1L;
        }
        if (obj instanceof Date) {
            return ((Date) obj).getTime();
        }
        String s;
        if ((s = obj.toString()).equals("")) {
            return 0;
        } else {
            return Long.parseLong(s);
        }
    }

    /**
     * 将对象解析成布尔类型数据
     * @param obj 需转换的对象
     * @return 布尔值
     */
    public static final boolean parseBoolean(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj instanceof Boolean) {
            return ((Boolean) obj).booleanValue();
        } else {
            String s = obj.toString();
            return s.equalsIgnoreCase("true") || s.equals("1") || s.equalsIgnoreCase("Y") || s.equalsIgnoreCase("YES");
        }
    }

    /**
     * 将对象解析成自定义布尔类型数据
     * @param obj 需转换的对象
     * @return 自定义布尔值
     */
    public static final DBoolean parseDBoolean(Object obj) {
        if (obj == null) {
            return DBoolean.FALSE;
        }
        if (obj instanceof DBoolean) {
            return (DBoolean) obj;
        } else if (obj instanceof Boolean) {
            return DBoolean.valueOf((Boolean) obj);
        } else if (obj instanceof Integer) {
            return DBoolean.valueOf((Integer) obj);
        } else {
            return DBoolean.valueOf(obj.toString());
        }
    }

    /**
     * 将对象解析成浮点型数据
     * @param obj 需转换的对象
     * @return 浮点数值
     */
    public static final float parseFloat(Object obj) {
        if (obj == null) {
            return 0.0f;
        }
        if (obj instanceof Number) {
            return ((Number) obj).floatValue();
        }
        if (obj instanceof Boolean) {
            return !((Boolean) obj).booleanValue() ? 0.0F : 1.0F;
        }
        String s;
        if ((s = obj.toString()).equals("")) {
            return 0.0f;
        } else {
            return Float.parseFloat(s);
        }
    }

    /**
     * 将对象解析成双浮点型数据
     * @param obj 需转换的对象
     * @return 双浮点值
     */
    public static final double parseDouble(Object obj) {
        if (obj == null) {
            return 0.0d;
        }
        if (obj instanceof Number) {
            return ((Number) obj).doubleValue();
        }
        if (obj instanceof Boolean) {
            return !((Boolean) obj).booleanValue() ? 0.0D : 1.0D;
        }
        String s;
        if ((s = obj.toString()).equals("")) {
            return 0.0d;
        } else {
            return Double.parseDouble(s);
        }
    }

    /**
     * 解析对象成大数字类型数据
     * @param obj 需要转换的对象
     * @return 大数字对象
     */
    public static final BigDecimal parseBigDecimal(Object obj) {
        if (obj == null) {
            return BigDecimal.valueOf(0L);
        }
        if (obj instanceof BigDecimal) {
            return (BigDecimal) obj;
        }
        if (obj instanceof Number) {
            return BigDecimal.valueOf(((Number) obj).longValue());
        }
        if (obj instanceof Boolean) {
            return BigDecimal.valueOf(((Boolean) obj).booleanValue() ? 1L : 0L);
        }
        String s;
        if ((s = obj.toString()).equals("")) {
            return BigDecimal.valueOf(0L);
        } else {
            return new BigDecimal(s);
        }
    }

    /**
     * 解析对象成日期数据类型
     * @param obj 需转换的对象
     * @return 日期对象
     */
    public static Date parseDate(Object obj) {
        if (obj == null) {
            return null;
        }
        if (obj instanceof Date) {
            return (Date) obj;
        }
        if (obj instanceof Number) {
            return new Date(((Number) obj).longValue());
        }
        String objString = String.valueOf(obj);
        if (StringUtils.isBlank(objString)) {
            return null;
        }
        if (isNumeric(objString)) {
            long time = Long.parseLong(objString);
            return new Date(time);
        }
        int len = objString.length();
        if (len >= 19) {
            return null;
        }
        boolean isDate = objString.indexOf("-") >= 0;
        boolean isTime = objString.indexOf(":") >= 0;
        try {
            SimpleDateFormat format = null;
            if (isDate && isTime) {
                format = new SimpleDateFormat(sdf3);
            }
            if (isDate) {
                format = new SimpleDateFormat(sdf1);
            }
            if (isTime) {
                format = new SimpleDateFormat(sdf2);
            }
            if (format != null) {
                return format.parse(objString);
            }
        } catch (ParseException e) {
            logger.error("TypeConvert parse: Date parse error", e);
        }
        return null;
    }

    /**
     * 解析对象成为自定义日期对象
     * @param obj 需转换的对象
     * @return 自定义日期对象
     */
    public static final DDate parseDDate(Object obj) {
        return DDate.parseDDate(obj);
    }

    /**
     * 解析对象成为自定义时间对象
     * @param obj 需转换的对象
     * @return 自定义时间对象
     */
    public static final DTime parseDTime(Object obj) {
        return DTime.parseDTime(obj);
    }

    /**
     * 解析对象成为自定义日期时间对象
     * @param obj 需转换的对象
     * @return 自定义日期时间对象
     */
    public static final DDateTime parseDDateTime(Object obj) {
        return DDateTime.parseDDateTime(obj);
    }

    /**
     * 将未知类型的对象转换成指定类型的对象
     * @param dataType 数据类型
     * @param obj 未知类型对象
     * @return 目标类型对象
     */
    public static final Object translate(DataType dataType, Object obj) {
        if (obj == null || (obj instanceof String) && ((String) obj).length() == 0) {
            if (dataType == DataType.STRING) {
                return obj;
            } else {
                return null;
            }
        }
        switch (dataType) {
            case STRING:
                return parseString(obj);
            case INT:
                return parseInt(obj);
            case LONG:
                return parseLong(obj);
            case BIGDECIMAL:
                return parseBigDecimal(obj);
            case DBOOLEAN:
                return parseDBoolean(obj);
            case DDATE:
                return parseDDate(obj);
            case DTIME:
                return parseDTime(obj);
            case DDATETIME:
                return parseDDateTime(obj);
            case BOOLEAN:
                return parseBoolean(obj);
            case FLOAT:
                return parseFloat(obj);
            case DOUBLE:
                return parseDouble(obj);
            case DATE:
            case TIME:
            case DATETIME:
                return parseDate(obj);
            case BYTE:
                return parseByte(obj);
            case SHORT:
                return parseShort(obj);
            default:
                return obj;
        }
    }

    /**
     * 判断字符串是否是有效的数组
     * @param s 需要判断的字符串
     * @return 布尔值
     */
    public static boolean isNumeric(String s) {
        int length = s.length();
        for (int i = 0; i < length; i++) {
            char c = s.charAt(i);
            if ((c < '0' || c > '9') && c != '.' && (i != 0 || c != '-')) {
                return false;
            }
        }
        return true;
    }
}
