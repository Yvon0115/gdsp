package com.gdsp.platform.files.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.gdsp.dev.base.exceptions.DevRuntimeException;
import com.gdsp.dev.core.common.AppConfig;
import com.gdsp.dev.core.common.AppContext;
import com.gdsp.platform.files.model.FileStoreConst;
import com.gdsp.platform.files.service.IFileStoreService;

/**
 * 文件存储基于数据库方案
 * @author paul.yang
 * @version 1.0 2014年10月30日
 * @since 1.6
 */
@Service(FileStoreConst.STORE_LOCAL)
public class FileStoreServiceLocalImpl implements IFileStoreService {

    /**
     * 日志对象
     */
    protected Logger logger = LoggerFactory.getLogger(getClass());
    /**
     * 文件根路径在本地硬盘的存储位置
     */
    private String   rootPath;

    @Override
    public InputStream load(String filePath) {
        File file = getFile(filePath);
        if (file.exists() && !file.isDirectory()) {
            try {
                return new FileInputStream(file);
            } catch (FileNotFoundException e) {
                logger.error("file no exist", e);
                return null;
            }
        }
        return null;
    }

    @Override
    public boolean exists(String filePath) {
        return getFile(filePath).exists();
    }

    @Override
    public void save(InputStream content, String filePath) {
        File file = getFile(filePath);
        try {
            if (file.exists() && (file.isDirectory() || !file.canWrite())) {
                throw new DevRuntimeException("Invalid file filePath.");
            }
            FileUtils.copyInputStreamToFile(content, getFile(filePath));
            logger.info("save file filePath:" + filePath);
        } catch (IOException e) {
            logger.error("save file error:" + filePath, e);
            throw new DevRuntimeException(e);
        }
    }

    @Override
    public boolean delete(String filePath) {
        File file = getFile(filePath);
        if (file.exists() && file.canWrite()) {
            try {
                FileUtils.deleteDirectory(file);
                return true;
            } catch (IOException e) {
                logger.error("Delete file fail!", e);
                throw new DevRuntimeException(e);
            }
        }
        return false;
    }

    /**
     * 根据文件路径取得文件路径位置
     * @param path 文件路径
     * @return 文件对象
     */
    protected File getFile(String path) {
        if (StringUtils.isEmpty(path)) {
            throw new IllegalArgumentException("Invalid file filePath.");
        }
        if (path.startsWith("/"))
            path = "/" + path;
        return new File(getRootPath() + path);
    }

    /**
     * 取得文件根路径
     * @return 文件根路径
     */
    private String getRootPath() {
        if (rootPath != null) {
            return rootPath;
        }
        String path = AppConfig.getInstance().getString(FileStoreConst.CK_FILEROOTPATH, FileStoreConst.DV_LOCALSTORE_ROOTPATH);
        Properties prop = System.getProperties();
        String os = prop.getProperty("os.name").toLowerCase();
        if ((os.startsWith("win") && !path.matches("^([A-Za-z]:[/|\\\\])") || (!os.startsWith("win") && !path.startsWith("/")))) {
            path = AppContext.getAppRealPath() + (path.startsWith("/") ? path : "/" + path);
        }
        rootPath = FilenameUtils.normalize(path);
        File r = new File(rootPath);
        if (!r.exists()) {
            if (!r.mkdirs())
                throw new DevRuntimeException("本地文件存储根路径设置不正确！");
        } else if (!r.isDirectory()) {
            throw new DevRuntimeException("本地文件存储根路径设置不正确！");
        }
        return rootPath;
    }
}
