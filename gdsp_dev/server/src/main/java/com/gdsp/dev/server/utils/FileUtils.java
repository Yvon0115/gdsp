package com.gdsp.dev.server.utils;

import java.io.Closeable;
import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FileUtils {
    private static Logger       logger         = LoggerFactory.getLogger(FileUtils.class);
    public static void close(Closeable obj) {
        try {
            if (obj != null) {
                obj.close();
            }
        } catch (Exception e) {logger.debug(e.getMessage(), e);}
    }

    public static void delete(String filePath) {
        if (filePath != null && !filePath.trim().equals("")) {
            delete(new File(filePath));
        }
    }

    public static void delete(File file) {
        if (file != null && file.exists()) {
            if (file.isDirectory()) {
                File[] files = file.listFiles();
                for (File subFile : files) {
                    delete(subFile);
                }
            }
            file.delete();
        }
    }
}
