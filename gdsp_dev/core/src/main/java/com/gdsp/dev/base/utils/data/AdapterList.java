package com.gdsp.dev.base.utils.data;

import java.util.AbstractList;
import java.util.List;
import java.util.RandomAccess;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gdsp.dev.base.exceptions.DevRuntimeException;

/**
 * 列表适配器，为了直接将整个列表的对象适配成新对象的列表
 * 检少循环创建新对象的次数
 * @author paul.yang
 * @version 1.0 2014年9月24日
 * @since 1.6
 */
public class AdapterList<E, T> extends AbstractList<E> implements List<E>, RandomAccess, Cloneable, java.io.Serializable {

    /**
     * 日志对象
     */
    private static final Logger      logger           = LoggerFactory.getLogger(AdapterList.class);
    /**
     * 序列id
     */
    private static final long  serialVersionUID = -664686039444125456L;
    /**
     * 原始列表
     */
    private List<T>            original;
    /**
     * 适配类
     */
    private Class<? extends E> adapterClass     = null;

    /**
     * 构造方法
     * @param adapterClass 适配器类
     * @param original 原始列表
     */
    public AdapterList(Class<? extends E> adapterClass, List<T> original) {
        this.adapterClass = adapterClass;
        this.original = original;
    }

    /* (non-Javadoc)
     * @see java.util.AbstractList#get(int)
     */
    @Override
    public E get(int index) {
        if (this.adapterClass == null || size() == 0)
            return null;
        T t = original.get(index);
        try {
            return adapterClass.getConstructor(t.getClass()).newInstance(t);
        } catch (Exception e) {
            logger.error("适配对象构造出错！", e);
            throw new DevRuntimeException(e);
        }
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    /* (non-Javadoc)
         * @see java.util.AbstractCollection#size()
         */
    @Override
    public int size() {
        if (original == null)
            return 0;
        return original.size();
    }
}
