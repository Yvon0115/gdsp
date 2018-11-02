package com.gdsp.dev.base.utils.data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

import org.apache.commons.collections4.map.LinkedMap;
import org.apache.commons.lang3.ClassUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gdsp.dev.base.exceptions.DevRuntimeException;
import com.gdsp.dev.base.lang.DBoolean;
import com.gdsp.dev.base.lang.DDate;
import com.gdsp.dev.base.lang.DDateTime;
import com.gdsp.dev.base.lang.DTime;

/**
 * 变量对象集合,用于构建属性或参数对象
 * @author paul.yang
 * @version 1.0 2014年9月24日
 * @since 1.6
 */
public class VariantSet implements TypeSet, Serializable, Cloneable {

    private static final Logger              logger           = LoggerFactory.getLogger(VariantSet.class);
    /**
     * 序列id
     */
    private static final long          serialVersionUID = 2303970450143502602L;
    /** 存放变量对象的Map*/
    private LinkedMap<String, Variant> values           = null;

    /**
     * 变量对象集合无参数构造方法
     */
    public VariantSet() {
        values = new LinkedMap<String, Variant>();
    }

    /**
     * 确保指定名称的变量对象存在
     * @param name 指定名称
     * @return 该变量名对应的变量对象
     */
    private Variant ensureVariantExist(String name) {
        Variant v = getVariant(name);
        if (v == null) {
            v = new Variant();
            setVariant(name, v);
        }
        return v;
    }

    /**
     * 检验指定索引是否合法
     * @param index 需要检验的索引
     */
    private void verifyIndex(int index) {
        if (index < 0 || index >= values.size()) {
            throw new ArrayIndexOutOfBoundsException(index);
        } else {
            return;
        }
    }

    /**
     * 检验指定的变量名称是否合法
     * @param name 需要检验的变量名
     */
    private void verifyName(String name) {
        if (!exists(name)) {
            throw new DevRuntimeException("Variant '" + name
                    + "' does not exist!");
        } else {
            return;
        }
    }

    /**
     * 判断变量名是否存在
     * @param name 变量名
     * @return 布尔值
     */
    public boolean exists(String name) {
        return getVariant(name) != null;
    }

    /**
     * 将一个变量集合赋值到当前集合上，即合并到当前集合上
     * @param variants 变量集合
     */
    public void assign(VariantSet variants) {
        int count = variants.size();
        for (int i = 0; i < count; i++) {
            String name = variants.indexToName(i);
            if (!exists(name)) {
                setDataType(name, variants.getDataType(i));
            }
            Object value = variants.getValue(i);
            if (getDataType(i) == DataType.UNKNOWN && "".equals(value)) {
                value = null;
            }
            setValue(name, value);
        }
    }

    /**
     * 清空变量集合
     */
    public void clear() {
        values.clear();
    }

    /**
     * 根据指定索引取得变量对象
     * @param index 指定索引
     * @return 变量对象
     */
    public Variant getVariant(int index) {
        return values.getValue(index);
    }

    /**
     * 根据指定名称取得变量对象
     * @param name 指定名称
     * @return 变量对象
     */
    public Variant getVariant(String name) {
        return (Variant) values.get(name);
    }

    /**
     * 设置指定变量名的变量对象
     * @param name 变量名
     * @param variant 变量对象
     */
    public void setVariant(int index, Variant variant) {
        verifyIndex(index);
        values.put(indexToName(index), variant);
    }

    /**
     * 设置指定变量名的变量对象
     * @param name 变量名
     * @param variant 变量对象
     */
    public void setVariant(String name, Variant variant) {
        values.put(name, variant);
    }

    /**
     * 根据指定索引取得变量所对应的数据类型
     * @param index 索引
     * @return 整型数据类型值
     */
    public DataType getDataType(int index) {
        verifyIndex(index);
        return getVariant(index).getDataType();
    }

    /**
     * 设置指定索引对应变量的数据类型
     * @param index 索引
     * @param dataType 整型数据类型值
     */
    public void setDataType(int index, DataType dataType) {
        verifyIndex(index);
        getVariant(index).setDataType(dataType);
    }

    /**
     * 设置指定索引对应变量的数据类型
     * @param index 索引
     * @param dataType 数据类型名
     */
    public void setDataType(int index, String dataType) {
        verifyIndex(index);
        getVariant(index).setDataType(dataType);
    }

    /**
     * 根据名称取得对应变量数据类型
     * @param name 变量名
     * @return 整型数据类型值
     */
    public DataType getDataType(String name) {
        verifyName(name);
        return getVariant(name).getDataType();
    }

    /**
     * 设置指定变量名对应变量的数据类型
     * @param name 变量名
     * @param dataType 整型数据类型值
     */
    public void setDataType(String name, DataType dataType) {
        Variant v = ensureVariantExist(name);
        v.setDataType(dataType);
    }

    /**
     * 设置指定变量名对应变量的数据类型
     * @param name 变量名
     * @param dataType 数据类型名
     */
    public void setDataType(String name, String dataType) {
        Variant v = ensureVariantExist(name);
        v.setDataType(dataType);
    }

    /**
     * 根据索引取得变量值对象
     * @param index 索引
     * @return 变量值对象
     */
    public Object getValue(int index) {
        Variant variant = getVariant(index);
        if (variant != null) {
            return variant.getValue();
        } else {
            return null;
        }
    }

    /**
     * 取得指定索引变量对象的布尔型值
     * @param index 索引
     * @return 布尔型值
     */
    public boolean getBoolean(int index) {
        Variant variant = getVariant(index);
        if (variant != null) {
            return variant.getBoolean();
        } else {
            return false;
        }
    }

    /**
     * 取得指定索引变量对象的自定义布尔型值
     * @param index 索引
     * @return 自定义布尔型值
     */
    public DBoolean getCBoolean(int index) {
        Variant variant = getVariant(index);
        if (variant != null) {
            return variant.getDBoolean();
        } else {
            return DBoolean.FALSE;
        }
    }

    /**
     * 根据索引取得变量的字节型值
     * @param index 索引
     * @return 字节型值
     */
    public byte getByte(int index) {
        Variant variant = getVariant(index);
        if (variant != null) {
            return variant.getByte();
        } else {
            return 0;
        }
    }

    /**
     * 根据索引取得变量的整型值
     * @param index 索引
     * @return 整型值
     */
    public short getShort(int index) {
        Variant variant = getVariant(index);
        if (variant != null) {
            return variant.getShort();
        } else {
            return 0;
        }
    }

    /**
     * 根据索引取得变量的整型值
     * @param index 索引
     * @return 整型值
     */
    public int getInt(int index) {
        Variant variant = getVariant(index);
        if (variant != null) {
            return variant.getInt();
        } else {
            return 0;
        }
    }

    /**
     * 根据索引取得变量的长整型值
     * @param index 索引
     * @return 长整型值
     */
    public long getLong(int index) {
        Variant variant = getVariant(index);
        if (variant != null) {
            return variant.getLong();
        } else {
            return 0L;
        }
    }

    /**
     * 根据索引取得变量的浮点类型值
     * @param index 索引
     * @return 浮点类型值
     */
    public float getFloat(int index) {
        Variant variant = getVariant(index);
        if (variant != null) {
            return variant.getFloat();
        } else {
            return 0.0F;
        }
    }

    /**
     * 根据索引取得变量的双浮点类型值
     * @param index 索引
     * @return 双浮点类型值
     */
    public double getDouble(int index) {
        Variant variant = getVariant(index);
        if (variant != null) {
            return variant.getDouble();
        } else {
            return 0.0D;
        }
    }

    /**
     * 根据索引取得变量的小数类型值
     * @param index 索引
     * @return 小数类型值
     */
    public BigDecimal getBigDecimal(int index) {
        Variant variant = getVariant(index);
        if (variant != null) {
            return variant.getBigDecimal();
        } else {
            return null;
        }
    }

    /**
     * 根据索引取得变量的字符串值
     * @param index 索引
     * @return 字符串值
     */
    public String getString(int index) {
        Variant variant = getVariant(index);
        if (variant != null) {
            return variant.getString();
        } else {
            return null;
        }
    }

    /**
     * 根据索引取得变量的日期型值
     * @param index 索引
     * @return 日期型值
     */
    public Date getDate(int index) {
        Variant variant = getVariant(index);
        if (variant != null) {
            return variant.getDate();
        } else {
            return null;
        }
    }

    /**
     * 根据索引取得变量的自定义日期型值
     * @param index 索引
     * @return 自定义日期型值
     */
    public DDate getDDate(int index) {
        Variant variant = getVariant(index);
        if (variant != null) {
            return variant.getDDate();
        } else {
            return null;
        }
    }

    /**
     * 根据索引取得变量的自定义自定义时间型值
     * @param index 索引
     * @return 自定义时间型值
     */
    public DTime getDTime(int index) {
        Variant variant = getVariant(index);
        if (variant != null) {
            return variant.getDTime();
        } else {
            return null;
        }
    }

    /**
     * 根据索引取得变量的自定义自定义日期时间型值
     * @param index 索引
     * @return 自定义日期时间型值
     */
    public DDateTime getDDateTime(int index) {
        Variant variant = getVariant(index);
        if (variant != null) {
            return variant.getDDateTime();
        } else {
            return null;
        }
    }

    /**
     * 判断指定索引的变量是否为空
     * @param index 索引
     * @return 布尔值
     */
    public boolean isNull(int index) {
        Variant v = getVariant(index);
        if (v != null) {
            return v.isNull();
        } else {
            return true;
        }
    }

    /**
     * 设置对象值到指定索引的变量对象
     * @param index 索引
     * @param value 对象值
     */
    public void setValue(int index, Object value) {
        verifyIndex(index);
        getVariant(index).setValue(value);
    }

    /**
     * 设置布尔值到指定索引的变量对象
     * @param index 索引
     * @param value 布尔值
     */
    public void setBoolean(int index, boolean value) {
        verifyIndex(index);
        getVariant(index).setBoolean(value);
    }

    /**
     * 设置自定义布尔值到指定索引的变量对象
     * @param index 索引
     * @param value 自定义布尔值
     */
    public void setDBoolean(int index, DBoolean value) {
        verifyIndex(index);
        getVariant(index).setDBoolean(value);
    }

    /**
     * 设置字节值到指定索引的变量对象
     * @param index 索引
     * @param value 字节值
     */
    public void setByte(int index, byte value) {
        verifyIndex(index);
        getVariant(index).setByte(value);
    }

    /**
     * 设置短整型值到指定索引的变量对象
     * @param index 索引
     * @param value 短整型值
     */
    public void setShort(int index, short value) {
        verifyIndex(index);
        getVariant(index).setShort(value);
    }

    /**
     * 设置整型值到指定索引的变量对象
     * @param index 索引
     * @param value 整型值
     */
    public void setInt(int index, int value) {
        verifyIndex(index);
        getVariant(index).setInt(value);
    }

    /**
     * 设置长整型值到指定索引的变量对象
     * @param index 索引
     * @param value 长整型值
     */
    public void setLong(int index, long value) {
        verifyIndex(index);
        getVariant(index).setLong(value);
    }

    /**
     * 设置浮点型值到指定索引的变量对象
     * @param index 索引
     * @param value 浮点型值
     */
    public void setFloat(int index, float value) {
        verifyIndex(index);
        getVariant(index).setFloat(value);
    }

    /**
     * 设置双浮点型值到指定索引的变量对象
     * @param index 索引
     * @param value 双浮点型值
     */
    public void setDouble(int index, double value) {
        verifyIndex(index);
        getVariant(index).setDouble(value);
    }

    /**
     * 设置小数值到指定索引的变量对象
     * @param index 索引
     * @param value 小数值
     */
    public void setBigDecimal(int index, BigDecimal value) {
        verifyIndex(index);
        getVariant(index).setBigDecimal(value);
    }

    /**
     * 设置字符串到指定索引的变量对象
     * @param index 索引
     * @param value 字符串
     */
    public void setString(int index, String value) {
        verifyIndex(index);
        getVariant(index).setString(value);
    }

    /**
     * 设置java日期对象到指定索引的变量对象
     * @param index 索引
     * @param value java日期对象
     */
    public void setDate(int index, Date value) {
        verifyIndex(index);
        getVariant(index).setDate(value);
    }

    /**
     * 设置自定义日期对象到指定索引的变量对象
     * @param index 索引
     * @param value 自定义日期对象
     */
    public void setDDate(int index, DDate value) {
        verifyIndex(index);
        getVariant(index).setDDate(value);
    }

    /**
     * 设置自定义时间对象到指定索引的变量对象
     * @param index 索引
     * @param value 自定义时间对象
     */
    public void setDTime(int index, DTime value) {
        verifyIndex(index);
        getVariant(index).setDTime(value);
    }

    /**
     * 设置自定义日期时间对象到指定索引的变量对象
     * @param index 索引
     * @param value 自定义日期时间对象
     */
    public void setDDateTime(int index, DDateTime value) {
        verifyIndex(index);
        getVariant(index).setCDateTime(value);
    }

    /**
     * 设置指定索引的变量值为空
     * @param index 索引
     */
    public void setNull(int index) {
        verifyIndex(index);
        getVariant(index).setNull();
    }

    /**
     * 根据变量名取得变量值对象
     * @param name 变量名
     * @return 值对象
     */
    public Object getValue(String name) {
        Variant variant = getVariant(name);
        if (variant != null) {
            return variant.getValue();
        } else {
            return null;
        }
    }

    /**
     * 根据变量名取得变量的布尔型值
     * @param name 变量名
     * @return 布尔型值
     */
    public boolean getBoolean(String name) {
        Variant variant = getVariant(name);
        if (variant != null) {
            return variant.getBoolean();
        } else {
            return false;
        }
    }

    /**
     * 根据变量名取得变量的自定义布尔型值
     * @param name 变量名
     * @return 自定义布尔型值
     */
    public DBoolean getDBoolean(String name) {
        Variant variant = getVariant(name);
        if (variant != null) {
            return variant.getDBoolean();
        } else {
            return DBoolean.FALSE;
        }
    }

    /**
     * 根据变量名取得变量的字节型值
     * @param name 变量名
     * @return 字节型值
     */
    public byte getByte(String name) {
        Variant variant = getVariant(name);
        if (variant != null) {
            return variant.getByte();
        } else {
            return 0;
        }
    }

    /**
     * 根据变量名取得变量的短整型值
     * @param name 变量名
     * @return 短整型值
     */
    public short getShort(String name) {
        Variant variant = getVariant(name);
        if (variant != null) {
            return variant.getShort();
        } else {
            return 0;
        }
    }

    /**
     * 根据变量名取得变量的整型值
     * @param name 变量名
     * @return 整型值
     */
    public int getInt(String name) {
        Variant variant = getVariant(name);
        if (variant != null) {
            return variant.getInt();
        } else {
            return 0;
        }
    }

    /**
     * 根据变量名取得变量的长整型值
     * @param name 变量名
     * @return 长整型值
     */
    public long getLong(String name) {
        Variant variant = getVariant(name);
        if (variant != null) {
            return variant.getLong();
        } else {
            return 0L;
        }
    }

    /**
     * 根据变量名取得变量的浮点型值
     * @param name 变量名
     * @return 浮点型值
     */
    public float getFloat(String name) {
        Variant variant = getVariant(name);
        if (variant != null) {
            return variant.getFloat();
        } else {
            return 0.0F;
        }
    }

    /**
     * 根据变量名取得变量的双浮点型值
     * @param name 变量名
     * @return 双浮点型值
     */
    public double getDouble(String name) {
        Variant variant = getVariant(name);
        if (variant != null) {
            return variant.getDouble();
        } else {
            return 0.0D;
        }
    }

    /**
     * 根据变量名取得变量的小数型值
     * @param name 变量名
     * @return 小数型值
     */
    public BigDecimal getBigDecimal(String name) {
        Variant variant = getVariant(name);
        if (variant != null) {
            return variant.getBigDecimal();
        } else {
            return null;
        }
    }

    /**
     * 根据变量名取得变量的字符型值
     * @param name 变量名
     * @return 字符型值
     */
    public String getString(String name) {
        Variant variant = getVariant(name);
        if (variant != null) {
            return variant.getString();
        } else {
            return null;
        }
    }

    /**
     * 根据变量名取得变量的java日期对象值
     * @param name 变量名
     * @return java日期对象值
     */
    public Date getDate(String name) {
        Variant variant = getVariant(name);
        if (variant != null) {
            return variant.getDate();
        } else {
            return null;
        }
    }

    /**
     * 根据变量名取得变量的自定义日期对象值
     * @param name 变量名
     * @return 自定义日期对象值
     */
    public DDate getDDate(String name) {
        Variant variant = getVariant(name);
        if (variant != null) {
            return variant.getDDate();
        } else {
            return null;
        }
    }

    /**
     * 根据变量名取得变量的自定义时间对象值
     * @param name 变量名
     * @return 自定义时间对象值
     */
    public DTime getDTime(String name) {
        Variant variant = getVariant(name);
        if (variant != null) {
            return variant.getDTime();
        } else {
            return null;
        }
    }

    /**
     * 根据变量名取得变量的自定义日期时间对象值
     * @param name 变量名
     * @return 自定义日期时间对象值
     */
    public DDateTime getDDateTime(String name) {
        Variant variant = getVariant(name);
        if (variant != null) {
            return variant.getDDateTime();
        } else {
            return null;
        }
    }

    /**
     * 判断指定变量名的变量是否为空
     * @param name 变量名
     * @return 布尔值
     */
    public boolean isNull(String name) {
        Variant v = getVariant(name);
        if (v != null) {
            return v.isNull();
        } else {
            return true;
        }
    }

    /**
     * 设置值对象到指定变量名的变量对象
     * @param name 变量名
     * @param value 值对象
     */
    public void setValue(String name, Object value) {
        Variant v = ensureVariantExist(name);
        v.setValue(value);
    }

    /**
     * 设置布尔值到指定变量名的变量对象
     * @param name 变量名
     * @param value 布尔值
     */
    public void setBoolean(String name, boolean value) {
        Variant v = ensureVariantExist(name);
        v.setBoolean(value);
    }

    /**
     * 设置自定义布尔值到指定变量名的变量对象
     * @param name 变量名
     * @param value 自定义布尔值
     */
    public void setDBoolean(String name, DBoolean value) {
        Variant v = ensureVariantExist(name);
        v.setDBoolean(value);
    }

    /**
     * 设置字节型值到指定变量名的变量对象
     * @param name 变量名
     * @param value 字节型值
     */
    public void setByte(String name, byte value) {
        Variant v = ensureVariantExist(name);
        v.setByte(value);
    }

    /**
     * 设置短整型值到指定变量名的变量对象
     * @param name 变量名
     * @param value 短整型值
     */
    public void setShort(String name, short value) {
        Variant v = ensureVariantExist(name);
        v.setShort(value);
    }

    /**
     * 设置整型值到指定变量名的变量对象
     * @param name 变量名
     * @param value 整型值
     */
    public void setInt(String name, int value) {
        Variant v = ensureVariantExist(name);
        v.setInt(value);
    }

    /**
     * 设置长整型值到指定变量名的变量对象
     * @param name 变量名
     * @param value 长整型值
     */
    public void setLong(String name, long value) {
        Variant v = ensureVariantExist(name);
        v.setLong(value);
    }

    /**
     * 设置浮点型值到指定变量名的变量对象
     * @param name 变量名
     * @param value 浮点型值
     */
    public void setFloat(String name, float value) {
        Variant v = ensureVariantExist(name);
        v.setFloat(value);
    }

    /**
     * 设置双浮点型值到指定变量名的变量对象
     * @param name 变量名
     * @param value 双浮点型值
     */
    public void setDouble(String name, double value) {
        Variant v = ensureVariantExist(name);
        v.setDouble(value);
    }

    /**
     * 设置小数对象到指定变量名的变量对象
     * @param name 变量名
     * @param value 小数对象
     */
    public void setBigDecimal(String name, BigDecimal value) {
        Variant v = ensureVariantExist(name);
        v.setBigDecimal(value);
    }

    /**
     * 设置字符串到指定变量名的变量对象
     * @param name 变量名
     * @param value 字符串
     */
    public void setString(String name, String value) {
        Variant v = ensureVariantExist(name);
        v.setString(value);
    }

    /**
     * 设置java日期对象到指定变量名的变量对象
     * @param name 变量名
     * @param value java日期对象
     */
    public void setDate(String name, Date value) {
        Variant v = ensureVariantExist(name);
        v.setDate(value);
    }

    /**
     * 设置自定义日期对象到指定变量名的变量对象
     * @param name 变量名
     * @param value 自定义日期对象
     */
    public void setDDate(String name, DDate value) {
        Variant v = ensureVariantExist(name);
        v.setDDate(value);
    }

    /**
     * 设置自定义时间对象到指定变量名的变量对象
     * @param name 变量名
     * @param value 自定义时间对象
     */
    public void setDTime(String name, DTime value) {
        Variant v = ensureVariantExist(name);
        v.setDTime(value);
    }

    /**
     * 设置自定义日期时间对象到指定变量名的变量对象
     * @param name 变量名
     * @param value 自定义日期时间对象
     */
    public void setDDateTime(String name, DDateTime value) {
        Variant v = ensureVariantExist(name);
        v.setCDateTime(value);
    }

    /**
     * 设置指定变量名的变量对象值为空
     * @param name 变量名
     */
    public void setNull(String name) {
        Variant v = ensureVariantExist(name);
        v.setNull();
    }

    /**
     * 移除指定索引的变量对象
     * @param index 索引
     */
    public Variant remove(int index) {
        return values.remove(index);
    }

    /**
     * 移除指定变量名的变量对象
     * @param name 变量名
     */
    public Variant remove(String name) {
        return values.remove(name);
    }

    /**
     * 取得变量名集合
     * @return 变量名集合
     */
    public Set<String> getVariantNames() {
        return values.keySet();
    }

    /**
     * 取得变量集合的大小
     * @return 集合的大小
     */
    public int size() {
        return values.size();
    }

    /**
     * 将指定索引转换为变量名
     * @param index 索引
     * @return 变量名
     */
    public String indexToName(int index) {
        return values.get(index);
    }

    /* (non-Javadoc)
     * @see java.lang.Object#clone()
     */
    public Object clone() {
        try {
            VariantSet o = (VariantSet) super.clone();
            LinkedMap<String, Variant> vs = new LinkedMap<String, Variant>();
            int vcount = values.size();
            for (int i = 0; i < vcount; i++) {
                vs.put(values.get(i), values.getValue(i).clone());
            }
            o.values = vs;
            return o;
        } catch (CloneNotSupportedException e) {
            logger.debug(e.getMessage(),e);
            throw new InternalError(e.getMessage());
        }
    }

    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj instanceof VariantSet) {
            return values.equals(((VariantSet) obj).values);
        } else {
            return false;
        }
    }

    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    public int hashCode() {
        return values.hashCode();
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    public String toString() {
        StringBuilder sb = new StringBuilder(ClassUtils.getShortClassName(getClass()));
        sb.append(":").append(values.toString());
        return sb.toString();
    }
}
