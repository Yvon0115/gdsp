package com.gdsp.platform.files.model;

/**
 * 文件存储常量
 * @author paul.yang
 * @version 1.0 2014年11月4日
 * @since 1.6
 */
public final class FileStoreConst {

    /**
     * 本地存储方式
     */
    public final static String STORE_LOCAL            = "localFileStore";
    /**
     * FastFS存储方式
     */
    public final static String STORE_FASTDFS          = "fastDFSFileStore";
    /**
     * FastFS存储方式
     */
    public final static String STORE_MONGODB          = "mongoDBFileStore";
    /**
     * 文件相关配置，文件存储方式
     */
    public final static String CK_STORESERVICE        = "filestore.service";
    /**
     * 文件相关配置，文件存储路径
     */
    public final static String CK_FILEROOTPATH        = "filestore.local.root";
    /**
     * 文件相关配置，文件存储方式
     */
    public final static String DV_STORESERVICE        = STORE_LOCAL;
    /**
     * 文件相关配置，文件存储路径
     */
    public final static String DV_LOCALSTORE_ROOTPATH = "attachs";
}
