package com.gdsp.platform.files.service;

import java.io.InputStream;

/**
 * 文件存储服务接口
 *
 * @author paul.yang
 * @version 1.0 2014年10月29日
 * @since 1.6
 */
public interface IFileInfoService {

    /**
     * 根据文件物理存储路径加载文件内容
     *
     * @param filePath 文件物理存储路径
     * @return 文件字节数组
     */
    InputStream load(String filePath);

    /**
     * 根据文件物理存储路径判断文件是否存在
     *
     * @param filePath 文件物理存储路径
     * @return 布尔值
     */
    boolean exists(String filePath);

    /**
     * 保存文件到指定物理存储路径
     *
     * @param content 文件字节数组
     * @param filePath    文件物理存储路径
     */
    void save(InputStream content, String filePath);

    /**
     * 根据文件物理存储路径删除对应文件
     *
     * @param filePath 文件物理存储路径
     * @return 是否删除成功
     */
    boolean delete(String filePath);
}
