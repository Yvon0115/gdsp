package com.gdsp.dev.base.utils.data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.gdsp.dev.base.lang.DBoolean;
import com.gdsp.dev.base.lang.DDate;
import com.gdsp.dev.base.lang.DDateTime;
import com.gdsp.dev.base.lang.DTime;

/**
 * 数据变量对象，存储类型化值,用于多类型数据
 * @author paul.yang
 * @version 1.0 2014年9月24日
 * @since 1.6
 */
public class Variant implements Serializable, Cloneable {

    /**
     * 序列id
     */
    private static final long serialVersionUID = 1035719937225603367L;
    /**数据类型*/
    private DataType          dataType         = DataType.UNKNOWN;
    /**值对象*/
    private Object            value;

    /**
     * 无参数构造方法
     */
    public Variant() {}

    /**
     * 带类型的构造方法
     * @param dataType 变量数据类型
     */
    public Variant(DataType dataType) {
        this.dataType = dataType;
    }

    /**
     * 取得变量数据类型
     * @return 数据类型
     */
    public DataType getDataType() {
        return dataType;
    }

    /**
     * 设置变量数据类型
     * @param dataType 数据类型
     */
    public void setDataType(DataType dataType) {
        if (this.dataType == dataType)
            return;
        value = TypeConvert.translate(dataType, value);
        this.dataType = dataType;
    }

    /**
     * 通过类型名称设置变量类型
     * @param dataType 变量类型名
     */
    public void setDataType(String dataType) {
        setDataType(DataType.valueOf(dataType.toUpperCase()));
    }

    /**
     * 取得变量值对象
     * @return 变量值对象
     */
    public Object getValue() {
        return value;
    }

    /**
     * 设置变量值对象
     * @param value 变量值对象
     */
    public void setValue(Object value) {
        this.value = TypeConvert.translate(dataType, value);
    }

    /**
     * 取得变量对象的布尔型值
     * @return 布尔型值
     */
    public boolean getBoolean() {
        return TypeConvert.parseBoolean(value);
    }

    /**
     * 取得变量对象的自定义布尔型值
     * @return 自定义布尔型值
     */
    public DBoolean getDBoolean() {
        return TypeConvert.parseDBoolean(value);
    }

    /**
     * 取得变量对象的字节值
     * @return 字节值
     */
    public byte getByte() {
        return TypeConvert.parseByte(value);
    }

    /**
     * 取得变量对象的短整数值
     * @return 短整数值
     */
    public short getShort() {
        return TypeConvert.parseShort(value);
    }

    /**
     * 取得变量对象的整数值
     * @return 整数值
     */
    public int getInt() {
        return TypeConvert.parseInt(value);
    }

    /**
     * 取得变量对象的长整数值
     * @return 长整数值
     */
    public long getLong() {
        return TypeConvert.parseLong(value);
    }

    /**
     * 取得变量对象的浮点型值
     * @return 浮点型值
     */
    public float getFloat() {
        return TypeConvert.parseFloat(value);
    }

    /**
     * 取得变量对象的双浮点型值
     * @return 双浮点型值
     */
    public double getDouble() {
        return TypeConvert.parseDouble(value);
    }

    /**
     * 取得变量对象的小数型值
     * @return 小数型值
     */
    public BigDecimal getBigDecimal() {
        return TypeConvert.parseBigDecimal(value);
    }

    /**
     * 取得变量的字符串值
     * @return 变量的字符串值
     */
    public String getString() {
        return TypeConvert.parseString(value);
    }

    /**
     * 取得变量对象的java日期型值
     * @return java日期型值
     */
    public Date getDate() {
        return TypeConvert.parseDate(value);
    }

    /**
     * 取得变量对象的自定义日期型值
     * @return 自定义日期型值
     */
    public DDate getDDate() {
        return TypeConvert.parseDDate(value);
    }

    /**
     * 取得变量对象的自定义时间型值
     * @return 自定义时间型值
     */
    public DTime getDTime() {
        return TypeConvert.parseDTime(value);
    }

    /**
     * 取得变量对象的自定义日期时间型值
     * @return 自定义日期时间型值
     */
    public DDateTime getDDateTime() {
        return TypeConvert.parseDDateTime(value);
    }

    /**
     * 用布尔型值设置当前变量对象的值，如果当前变量对象为未知类型，
     * 则变量数据类型为布尔型
     * @param value 布尔型值
     */
    public void setBoolean(boolean value) {
        if (dataType == DataType.UNKNOWN) {
            dataType = DataType.BOOLEAN;
        }
        if (dataType == DataType.BOOLEAN) {
            this.value = value;
        } else {
            this.value = TypeConvert.translate(dataType, value);
        }
    }

    /**
     * 用自定义布尔型值设置当前变量对象的值，如果当前变量对象为未知类型，
     * 则变量数据类型为自定义布尔型
     * @param value 自定义布尔型值
     */
    public void setDBoolean(DBoolean value) {
        if (dataType == DataType.UNKNOWN) {
            dataType = DataType.DBOOLEAN;
        }
        if (dataType == DataType.DBOOLEAN) {
            this.value = value;
        } else {
            this.value = TypeConvert.translate(dataType, value);
        }
    }

    /**
     * 用字节值设置当前变量对象的值，如果当前变量对象为未知类型，
     * 则变量数据类型为字节型
     * @param value 字节值
     */
    public void setByte(byte value) {
        if (dataType == DataType.UNKNOWN) {
            dataType = DataType.BYTE;
        }
        if (dataType == DataType.BYTE) {
            this.value = value;
        } else {
            this.value = TypeConvert.translate(dataType, value);
        }
    }

    /**
     * 用短整数值设置当前变量对象的值，如果当前变量对象为未知类型，
     * 则变量数据类型为短整数类型
     * @param value 短整数
     */
    public void setShort(short value) {
        if (dataType == DataType.UNKNOWN) {
            dataType = DataType.SHORT;
        }
        if (dataType == DataType.SHORT) {
            this.value = value;
        } else {
            this.value = TypeConvert.translate(dataType, value);
        }
    }

    /**
     * 用整型值设置当前变量对象的值，如果当前变量对象为未知类型，
     * 则变量数据类型为整型
     * @param value 整型值
     */
    public void setInt(int value) {
        if (dataType == DataType.UNKNOWN) {
            dataType = DataType.INT;
        }
        if (dataType == DataType.INT) {
            this.value = value;
        } else {
            this.value = TypeConvert.translate(dataType, value);
        }
    }

    /**
     * 用长整型值设置当前变量对象的值，如果当前变量对象为未知类型，
     * 则变量数据类型为长整型
     * @param value 长整型值
     */
    public void setLong(long value) {
        if (dataType == DataType.UNKNOWN) {
            dataType = DataType.LONG;
        }
        if (dataType == DataType.LONG) {
            this.value = value;
        } else {
            this.value = TypeConvert.translate(dataType, value);
        }
    }

    /**
     * 用浮点型值设置当前变量对象的值，如果当前变量对象为未知类型，
     * 则变量数据类型为浮点型
     * @param value 浮点型值
     */
    public void setFloat(float value) {
        if (dataType == DataType.UNKNOWN) {
            dataType = DataType.FLOAT;
        }
        if (dataType == DataType.FLOAT) {
            this.value = value;
        } else {
            this.value = TypeConvert.translate(dataType, value);
        }
    }

    /**
     * 用双浮点型值设置当前变量对象的值，如果当前变量对象为未知类型，
     * 则变量数据类型为双浮点型
     * @param value 双浮点型值
     */
    public void setDouble(double value) {
        if (dataType == DataType.UNKNOWN) {
            dataType = DataType.DOUBLE;
        }
        if (dataType == DataType.DOUBLE) {
            this.value = value;
        } else {
            this.value = TypeConvert.translate(dataType, value);
        }
    }

    /**
     * 用小数型值设置当前变量对象的值，如果当前变量对象为未知类型，
     * 则变量数据类型为小数型
     * @param value 小数型值
     */
    public void setBigDecimal(BigDecimal value) {
        if (dataType == DataType.UNKNOWN) {
            dataType = DataType.BIGDECIMAL;
        }
        if (dataType == DataType.BIGDECIMAL) {
            this.value = value;
        } else {
            this.value = TypeConvert.translate(dataType, value);
        }
    }

    /**
     * 用字符串设置当前变量对象的值，如果当前变量对象为未知类型，
     * 则变量数据类型为字符型
     * @param value 字符串值
     */
    public void setString(String value) {
        if (dataType == DataType.UNKNOWN) {
            dataType = DataType.STRING;
        }
        if (dataType == DataType.STRING) {
            this.value = value;
        } else {
            this.value = TypeConvert.translate(dataType, value);
        }
    }

    /**
     * 用java日期型值设置当前变量对象的值，如果当前变量对象为未知类型，
     * 则变量数据类型为java日期型
     * @param value java日期型值
     */
    public void setDate(Date value) {
        if (dataType == DataType.UNKNOWN) {
            dataType = DataType.DATE;
        }
        if (dataType == DataType.DATE) {
            this.value = value;
        } else {
            this.value = TypeConvert.translate(dataType, value);
        }
    }

    /**
     * 用自定义日期型值设置当前变量对象的值，如果当前变量对象为未知类型，
     * 则变量数据类型为自定义日期型
     * @param value 自定义日期型值
     */
    public void setDDate(DDate value) {
        if (dataType == DataType.UNKNOWN) {
            dataType = DataType.DDATE;
        }
        if (dataType == DataType.DDATE) {
            this.value = value;
        } else {
            this.value = TypeConvert.translate(dataType, value);
        }
    }

    /**
     * 用自定义时间型值设置当前变量对象的值，如果当前变量对象为未知类型，
     * 则变量数据类型为自定义时间型
     * @param value 自定义时间型值
     */
    public void setDTime(DTime value) {
        if (dataType == DataType.UNKNOWN) {
            dataType = DataType.DTIME;
        }
        if (dataType == DataType.DTIME) {
            this.value = value;
        } else {
            this.value = TypeConvert.translate(dataType, value);
        }
    }

    /**
     * 用自定义日期时间型值设置当前变量对象的值，如果当前变量对象为未知类型，
     * 则变量数据类型为自定义日期时间型
     * @param value 自定义日期时间型值
     */
    public void setCDateTime(DDateTime value) {
        if (dataType == DataType.UNKNOWN) {
            dataType = DataType.DDATETIME;
        }
        if (dataType == DataType.DDATETIME) {
            this.value = value;
        } else {
            this.value = TypeConvert.translate(dataType, value);
        }
    }

    /**
     * 判断当前值是否为空
     * @return 布尔值
     */
    public boolean isNull() {
        return value == null;
    }

    /**
     * 设置当前变量对象值为空
     */
    public void setNull() {
        value = null;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    public boolean equals(Object obj) {
        if (obj != null && obj instanceof Variant) {
            Object other = ((Variant) obj).getValue();
            return (value == other) || (value != null && value.equals(other));
        } else {
            return false;
        }
    }

    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    public int hashCode() {
        if (value != null) {
            return value.hashCode();
        } else {
            return 0;
        }
    }

    /* (non-Javadoc)
     * @see java.lang.Object#clone()
     */
    protected Variant clone() throws CloneNotSupportedException {
        Variant o = (Variant) super.clone();
        o.value = value;
        return o;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    public String toString() {
        return value != null ? value.toString() : "Variant {null}";
    }
}
