package com.gdsp.dev.core.model.param;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * 重新实现Page接口,方便控制
 * @author paul.yang
 * @version 1.0 2014年9月24日
 * @since 1.6
 */
public class PageSerImpl<T> implements Page<T>, Serializable {

    /**
     * 序列id
     */
    private static final long serialVersionUID = 1463459147799104837L;
    /**
     * 数据内容
     */
    private final List<T>     content          = new ArrayList<T>();
    /**
     * 分页请求
     */
    private final Pageable    pageable;
    /**
     * 数据总数
     */
    private final long        total;

    /**
     * 构造方法
     * @param content 数据内容
     * @param pageable 分页请求
     * @param total 数据总数
     */
    public PageSerImpl(List<T> content, Pageable pageable, long total) {
        this.pageable = pageable;
        if (null == content) {
            this.total = 0;
            this.content.clear();
            return;
        }
        this.content.addAll(content);
        this.total = total;
    }

    public PageSerImpl() {
        this(null);
    }

    /**
     * 数据内容
     * @param content 数据
     */
    public PageSerImpl(List<T> content) {
        this(content, null, null == content ? 0 : content.size());
    }

    /*
     * (non-Javadoc)
     * @see org.springframework.data.domain.Page#getNumber()
     */
    public int getNumber() {
        return pageable == null ? 0 : pageable.getPageNumber();
    }

    /*
     * (non-Javadoc)
     * @see org.springframework.data.domain.Page#getSize()
     */
    public int getSize() {
        return pageable == null ? 0 : pageable.getPageSize();
    }

    /*
     * (non-Javadoc)
     * @see org.springframework.data.domain.Page#getTotalPages()
     */
    public int getTotalPages() {
        return getSize() == 0 ? 1 : (int) Math.ceil((double) total / (double) getSize());
    }

    /*
     * (non-Javadoc)
     * @see org.springframework.data.domain.Page#getNumberOfElements()
     */
    public int getNumberOfElements() {
        return content.size();
    }

    /*
     * (non-Javadoc)
     * @see org.springframework.data.domain.Page#getTotalElements()
     */
    public long getTotalElements() {
        return total;
    }

    /*
     * (non-Javadoc)
     * @see org.springframework.data.domain.Page#hasPreviousPage()
     */
    public boolean hasPreviousPage() {
        return getNumber() > 0;
    }

    /*
     * (non-Javadoc)
     * @see org.springframework.data.domain.Page#isFirstPage()
     */
    public boolean isFirstPage() {
        return !hasPreviousPage();
    }

    /*
     * (non-Javadoc)
     * @see org.springframework.data.domain.Page#hasNextPage()
     */
    public boolean hasNextPage() {
        return getNumber() + 1 < getTotalPages();
    }

    /*
     * (non-Javadoc)
     * @see org.springframework.data.domain.Page#isLastPage()
     */
    public boolean isLastPage() {
        return !hasNextPage();
    }

    /* 
     * (non-Javadoc)
     * @see org.springframework.data.domain.Page#nextPageable()
     */
    public Pageable nextPageable() {
        return hasNextPage() ? pageable.next() : null;
    }

    /* 
     * (non-Javadoc)
     * @see org.springframework.data.domain.Page#previousOrFirstPageable()
     */
    public Pageable previousPageable() {
        if (hasPreviousPage()) {
            return pageable.previousOrFirst();
        }
        return null;
    }

    /*
     * (non-Javadoc)
     * @see org.springframework.data.domain.Page#iterator()
     */
    public Iterator<T> iterator() {
        return content.iterator();
    }

    /*
     * (non-Javadoc)
     * @see org.springframework.data.domain.Page#getContent()
     */
    public List<T> getContent() {
        return content;
    }

    /*
     * (non-Javadoc)
     * @see org.springframework.data.domain.Page#hasContent()
     */
    public boolean hasContent() {
        return !content.isEmpty();
    }

    /*
     * (non-Javadoc)
     * @see org.springframework.data.domain.Page#getSort()
     */
    public org.springframework.data.domain.Sort getSort() {
        return pageable == null ? null : pageable.getSort();
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        String contentType = "UNKNOWN";
        if (content.size() > 0) {
            contentType = content.get(0).getClass().getName();
        }
        return String.format("Page %s of %d containing %s instances", getNumber(), getTotalPages(), contentType);
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof PageSerImpl<?>)) {
            return false;
        }
        PageSerImpl<?> that = (PageSerImpl<?>) obj;
        boolean totalEqual = this.total == that.total;
        boolean contentEqual = this.content.equals(that.content);
        boolean pageableEqual = this.pageable == null ? that.pageable == null : this.pageable.equals(that.pageable);
        return totalEqual && contentEqual && pageableEqual;
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + (int) (total ^ total >>> 32);
        result = 31 * result + (pageable == null ? 0 : pageable.hashCode());
        result = 31 * result + content.hashCode();
        return result;
    }
}
