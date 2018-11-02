package com.gdsp.dev.base.utils.data;

import java.math.BigDecimal;
import java.util.Date;

import com.gdsp.dev.base.lang.DBoolean;
import com.gdsp.dev.base.lang.DDate;
import com.gdsp.dev.base.lang.DDateTime;
import com.gdsp.dev.base.lang.DTime;

/**
 * 可变类型集合接口，通过名称或索引取得指定类型数据
 * @author paul.yang
 * @version 1.0 2014年9月24日
 * @since 1.6
 */
public interface TypeSet {

    /**
     * 根据索引取得变量值对象
     * @param index 索引
     * @return 变量值对象
     */
    public Object getValue(int index);

    /**
     * 取得指定索引变量对象的布尔型值
     * @param index 索引
     * @return 布尔型值
     */
    public boolean getBoolean(int index);

    /**
     * 取得指定索引变量对象的自定义布尔型值
     * @param index 索引
     * @return 自定义布尔型值
     */
    public DBoolean getCBoolean(int index);

    /**
     * 根据索引取得变量的字节型值
     * @param index 索引
     * @return 字节型值
     */
    public byte getByte(int index);

    /**
     * 根据索引取得变量的整型值
     * @param index 索引
     * @return 整型值
     */
    public short getShort(int index);

    /**
     * 根据索引取得变量的整型值
     * @param index 索引
     * @return 整型值
     */
    public int getInt(int index);

    /**
     * 根据索引取得变量的长整型值
     * @param index 索引
     * @return 长整型值
     */
    public long getLong(int index);

    /**
     * 根据索引取得变量的浮点类型值
     * @param index 索引
     * @return 浮点类型值
     */
    public float getFloat(int index);

    /**
     * 根据索引取得变量的双浮点类型值
     * @param index 索引
     * @return 双浮点类型值
     */
    public double getDouble(int index);

    /**
     * 根据索引取得变量的小数类型值
     * @param index 索引
     * @return 小数类型值
     */
    public BigDecimal getBigDecimal(int index);

    /**
     * 根据索引取得变量的字符串值
     * @param index 索引
     * @return 字符串值
     */
    public String getString(int index);

    /**
     * 根据索引取得变量的日期值
     * @param index 索引
     * @return 日期类型值
     */
    public Date getDate(int index);

    /**
     * 根据索引取得变量的自定义日期型值
     * @param index 索引
     * @return 自定义日期型值
     */
    public DDate getDDate(int index);

    /**
     * 根据索引取得变量的自定义自定义时间型值
     * @param index 索引
     * @return 自定义时间型值
     */
    public DTime getDTime(int index);

    /**
     * 根据索引取得变量的自定义自定义日期时间型值
     * @param index 索引
     * @return 自定义日期时间型值
     */
    public DDateTime getDDateTime(int index);

    /**
     * 判断指定索引的变量是否为空
     * @param index 索引
     * @return 布尔值
     */
    public boolean isNull(int index);

    /**
     * 设置对象值到指定索引的变量对象
     * @param index 索引
     * @param value 对象值
     */
    public void setValue(int index, Object value);

    /**
     * 设置布尔值到指定索引的变量对象
     * @param index 索引
     * @param value 布尔值
     */
    public void setBoolean(int index, boolean value);

    /**
     * 设置自定义布尔值到指定索引的变量对象
     * @param index 索引
     * @param value 自定义布尔值
     */
    public void setDBoolean(int index, DBoolean value);

    /**
     * 设置字节值到指定索引的变量对象
     * @param index 索引
     * @param value 字节值
     */
    public void setByte(int index, byte value);

    /**
     * 设置短整型值到指定索引的变量对象
     * @param index 索引
     * @param value 短整型值
     */
    public void setShort(int index, short value);

    /**
     * 设置整型值到指定索引的变量对象
     * @param index 索引
     * @param value 整型值
     */
    public void setInt(int index, int value);

    /**
     * 设置长整型值到指定索引的变量对象
     * @param index 索引
     * @param value 长整型值
     */
    public void setLong(int index, long value);

    /**
     * 设置浮点型值到指定索引的变量对象
     * @param index 索引
     * @param value 浮点型值
     */
    public void setFloat(int index, float value);

    /**
     * 设置双浮点型值到指定索引的变量对象
     * @param index 索引
     * @param value 双浮点型值
     */
    public void setDouble(int index, double value);

    /**
     * 设置小数值到指定索引的变量对象
     * @param index 索引
     * @param value 小数值
     */
    public void setBigDecimal(int index, BigDecimal value);

    /**
     * 设置字符串到指定索引的变量对象
     * @param index 索引
     * @param value 字符串
     */
    public void setString(int index, String value);

    /**
     * 设置java日期对象到指定索引的变量对象
     * @param index 索引
     * @param value java日期对象
     */
    public void setDate(int index, Date value);

    /**
     * 设置自定义日期对象到指定索引的变量对象
     * @param index 索引
     * @param value 自定义日期对象
     */
    public void setDDate(int index, DDate value);

    /**
     * 设置自定义时间对象到指定索引的变量对象
     * @param index 索引
     * @param value 自定义时间对象
     */
    public void setDTime(int index, DTime value);

    /**
     * 设置自定义日期时间对象到指定索引的变量对象
     * @param index 索引
     * @param value 自定义日期时间对象
     */
    public void setDDateTime(int index, DDateTime value);

    /**
     * 设置指定索引的变量值为空
     * @param index 索引
     */
    public void setNull(int index);

    /**
     * 根据变量名取得变量值对象
     * @param name 变量名
     * @return 值对象
     */
    public Object getValue(String name);

    /**
     * 根据变量名取得变量的布尔型值
     * @param name 变量名
     * @return 布尔型值
     */
    public boolean getBoolean(String name);

    /**
     * 根据变量名取得变量的自定义布尔型值
     * @param name 变量名
     * @return 自定义布尔型值
     */
    public DBoolean getDBoolean(String name);

    /**
     * 根据变量名取得变量的字节型值
     * @param name 变量名
     * @return 字节型值
     */
    public byte getByte(String name);

    /**
     * 根据变量名取得变量的短整型值
     * @param name 变量名
     * @return 短整型值
     */
    public short getShort(String name);

    /**
     * 根据变量名取得变量的整型值
     * @param name 变量名
     * @return 整型值
     */
    public int getInt(String name);

    /**
     * 根据变量名取得变量的长整型值
     * @param name 变量名
     * @return 长整型值
     */
    public long getLong(String name);

    /**
     * 根据变量名取得变量的浮点型值
     * @param name 变量名
     * @return 浮点型值
     */
    public float getFloat(String name);

    /**
     * 根据变量名取得变量的双浮点型值
     * @param name 变量名
     * @return 双浮点型值
     */
    public double getDouble(String name);

    /**
     * 根据变量名取得变量的小数型值
     * @param name 变量名
     * @return 小数型值
     */
    public BigDecimal getBigDecimal(String name);

    /**
     * 根据变量名取得变量的字符型值
     * @param name 变量名
     * @return 字符型值
     */
    public String getString(String name);

    /**
     * 根据变量名取得变量的java日期对象值
     * @param name 变量名
     * @return java日期对象值
     */
    public Date getDate(String name);

    /**
     * 根据变量名取得变量的自定义日期对象值
     * @param name 变量名
     * @return 自定义日期对象值
     */
    public DDate getDDate(String name);

    /**
     * 根据变量名取得变量的自定义时间对象值
     * @param name 变量名
     * @return 自定义时间对象值
     */
    public DTime getDTime(String name);

    /**
     * 根据变量名取得变量的自定义日期时间对象值
     * @param name 变量名
     * @return 自定义日期时间对象值
     */
    public DDateTime getDDateTime(String name);

    /**
     * 判断指定变量名的变量是否为空
     * @param name 变量名
     * @return 布尔值
     */
    public boolean isNull(String name);

    /**
     * 设置值对象到指定变量名的变量对象
     * @param name 变量名
     * @param value 值对象
     */
    public void setValue(String name, Object value);

    /**
     * 设置布尔值到指定变量名的变量对象
     * @param name 变量名
     * @param value 布尔值
     */
    public void setBoolean(String name, boolean value);

    /**
     * 设置自定义布尔值到指定变量名的变量对象
     * @param name 变量名
     * @param value 自定义布尔值
     */
    public void setDBoolean(String name, DBoolean value);

    /**
     * 设置字节型值到指定变量名的变量对象
     * @param name 变量名
     * @param value 字节型值
     */
    public void setByte(String name, byte value);

    /**
     * 设置短整型值到指定变量名的变量对象
     * @param name 变量名
     * @param value 短整型值
     */
    public void setShort(String name, short value);

    /**
     * 设置整型值到指定变量名的变量对象
     * @param name 变量名
     * @param value 整型值
     */
    public void setInt(String name, int value);

    /**
     * 设置长整型值到指定变量名的变量对象
     * @param name 变量名
     * @param value 长整型值
     */
    public void setLong(String name, long value);

    /**
     * 设置浮点型值到指定变量名的变量对象
     * @param name 变量名
     * @param value 浮点型值
     */
    public void setFloat(String name, float value);

    /**
     * 设置双浮点型值到指定变量名的变量对象
     * @param name 变量名
     * @param value 双浮点型值
     */
    public void setDouble(String name, double value);

    /**
     * 设置小数对象到指定变量名的变量对象
     * @param name 变量名
     * @param value 小数对象
     */
    public void setBigDecimal(String name, BigDecimal value);

    /**
     * 设置字符串到指定变量名的变量对象
     * @param name 变量名
     * @param value 字符串
     */
    public void setString(String name, String value);

    /**
     * 设置java日期对象到指定变量名的变量对象
     * @param name 变量名
     * @param value java日期对象
     */
    public void setDate(String name, Date value);

    /**
     * 设置自定义日期对象到指定变量名的变量对象
     * @param name 变量名
     * @param value 自定义日期对象
     */
    public void setDDate(String name, DDate value);

    /**
     * 设置自定义时间对象到指定变量名的变量对象
     * @param name 变量名
     * @param value 自定义时间对象
     */
    public void setDTime(String name, DTime value);

    /**
     * 设置自定义日期时间对象到指定变量名的变量对象
     * @param name 变量名
     * @param value 自定义日期时间对象
     */
    public void setDDateTime(String name, DDateTime value);

    /**
     * 设置指定变量名的变量对象值为空
     * @param name 变量名
     */
    public void setNull(String name);
}
