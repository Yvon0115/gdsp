package com.gdsp.dev.server.utils;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ZipUtils {
    private static Logger       logger         = LoggerFactory.getLogger(ZipUtils.class);
    public interface INameFilter {

        /**
         * 根据名称进行过滤，返回true说明需要过滤，false为不过滤
         * @param name
         * @return
         */
        public boolean filter(String name);
    }

    public static void unzip(String zipFilePath) {
        unzip(zipFilePath, null, true, null);
    }

    public static void unzip(File zipFile) {
        if (!zipFile.exists()) {
            return;
        }
        unzip(zipFile, null, true, null);
    }

    public static void unzip(File zipFile, String destPath) {
        unzip(zipFile, destPath, true, null);
    }

    public static void unzip(String zipFilePath, String destPath) {
        unzip(zipFilePath, destPath, true, null);
    }

    public static void unzip(String zipFilePath, INameFilter filter) {
        unzip(zipFilePath, null, true, filter);
    }

    public static void unzip(File zipFile, INameFilter filter) {
        unzip(zipFile, null, true, filter);
    }

    public static void unzip(String zipFilePath, boolean cover) {
        unzip(zipFilePath, null, cover, null);
    }

    public static void unzip(File zipFile, boolean cover) {
        if (!zipFile.exists()) {
            return;
        }
        unzip(zipFile, null, cover, null);
    }

    public static void unzip(String zipFilePath, String destPath, INameFilter filter) {
        unzip(new File(zipFilePath), destPath, true, filter);
    }

    public static void unzip(File zipFile, String destPath, INameFilter filter) {
        unzip(zipFile, destPath, true, filter);
    }

    public static void unzip(File zipFile, String destPath, boolean cover) {
        unzip(zipFile, destPath, cover, null);
    }

    public static void unzip(String zipFilePath, String destPath, boolean cover) {
        unzip(zipFilePath, destPath, cover, null);
    }

    public static void unzip(String zipFilePath, boolean cover, INameFilter filter) {
        unzip(zipFilePath, null, cover, filter);
    }

    public static void unzip(File zipFile, boolean cover, INameFilter filter) {
        unzip(zipFile, null, cover, filter);
    }

    public static void unzip(String zipFilePath, String destPath, boolean cover, INameFilter filter) {
        unzip(new File(zipFilePath), destPath, cover, filter);
    }

    public static void unzip(File zipFile, String destPath, boolean cover, INameFilter filter) {
        if (!zipFile.exists()) {
            return;
        }
        if (destPath == null || destPath.trim().equals("")) {
            String filename = zipFile.getName();
            int lastIndex = filename.lastIndexOf(".");
            destPath = zipFile.getParent() + File.separator + filename.substring(0, lastIndex);
        }
        destPath += File.separator;
        File outDir = new File(destPath);
        if (!outDir.exists()) {
            outDir.mkdir();
        }
        ZipInputStream zipIn = null;
        FileInputStream files=null;
        try {
            files=new FileInputStream(zipFile);
            zipIn = new ZipInputStream(new BufferedInputStream(files));
            ZipEntry zipEntry = null;
            byte[] buf = new byte[1024];
            boolean needFilter = filter != null;
            while ((zipEntry = zipIn.getNextEntry()) != null) {
                if (needFilter) {
                    if (filter.filter(zipEntry.getName())) {
                        //该文件需要过滤，跳过后续解压步骤
                        zipIn.closeEntry();
                        continue;
                    }
                }
                //解压
                unzipHander(zipIn, destPath, zipEntry, buf, cover);
                zipIn.closeEntry();
            }
        } catch (Exception ioe) {
            System.out.println(ioe.getMessage());
            logger.debug(ioe.getMessage(),ioe);
        } finally {
            FileUtils.close(zipIn);
            try {
                if(files!=null)
                {
                files.close();
                }
            } catch (IOException e) {
               logger.debug(e.getMessage(), e);
            }
        }
    }

    public static void unzipWar(File zipFile, String destPath, Map<String, Object> resFileLocationMap, boolean cover, INameFilter filter) {
        if (!zipFile.exists()) {
            return;
        }
        if (destPath == null || destPath.trim().equals("")) {
            String filename = zipFile.getName();
            int lastIndex = filename.lastIndexOf(".");
            destPath = zipFile.getParent() + File.separator + filename.substring(0, lastIndex);
        }
        destPath += File.separator;
        File outDir = new File(destPath);
        if (!outDir.exists()) {
            outDir.mkdir();
        }
        ZipInputStream zipIn = null;
        //拿到目标解压位置
        String targetUnzipLocaltionDir = null;
        //解压时，判断所有子项目的target/classes/下是否有对应的配置文件，如果有，就不解压。
//        List<String> resourceFiles = null;
        if (resFileLocationMap != null) {
            targetUnzipLocaltionDir = (String) resFileLocationMap.get("targetDir");
//            resourceFiles = (ArrayList) resFileLocationMap.get("resourceFiles");
        }
        FileInputStream files=null;
        try {
            files=new FileInputStream(zipFile);
            zipIn = new ZipInputStream(new BufferedInputStream(files));
            ZipEntry zipEntry = null;
            byte[] buf = new byte[1024];
            boolean needFilter = filter != null;
            while ((zipEntry = zipIn.getNextEntry()) != null) {
                if (needFilter) {
                    if (filter.filter(zipEntry.getName())) {
                        //该文件需要过滤，跳过后续解压步骤
                        zipIn.closeEntry();
                        continue;
                    }
                }
                //解压之前，进行判断
                String warFileName = zipEntry.getName();
                boolean isExists = exists(warFileName, resFileLocationMap);
                if (isExists) {
                    //该文件在子项目存在，不解压
                    zipIn.closeEntry();
                    continue;
                } else if (warFileName.startsWith("WEB-INF/classes/")) {//    WEB-INF/classes/
                    //如果是配置文件，解压到子项目的target\classes
                    unzipWarHander(zipIn, targetUnzipLocaltionDir, zipEntry, buf, cover);
                } else {
                    //普通资源文件解压到临时目录
                    unzipHander(zipIn, destPath, zipEntry, buf, cover);
                }
                zipIn.closeEntry();
            }
        } catch (Exception ioe) {
            System.out.println(ioe.getMessage());
            logger.debug(ioe.getMessage(),ioe);
        } finally {
            FileUtils.close(zipIn);
            try {
                files.close();
            } catch (IOException e) {
                
                logger.debug(e.getMessage(),e);
            }
        }
    }

    @SuppressWarnings("unchecked")
	private static boolean exists(String warFileName,
            Map<String, Object> resFileLocationMap) {
        if (!warFileName.startsWith("WEB-INF/classes/")) {
            return false;
        }
        //得到配置文件的相对路径
        String relativePath = warFileName.substring(warFileName.indexOf("WEB-INF/classes") + "WEB-INF/classes".length());
        //判断所有子项目的target/classes/下是否有对应的配置文件，如果有，就不解压。
        List<String> resourceFiles = null;
        if (resFileLocationMap != null) {
            resourceFiles =(List<String>) resFileLocationMap.get("resourceFiles");
        }
        for (String path : resourceFiles) {
            File file = new File(path + relativePath);
            if (file.exists()) {
                return true;
            }
        }
        return false;
    }

    private static File unzipHander(ZipInputStream zipIn, String destPath, ZipEntry zipEntry, byte[] buf, boolean cover) {
        int readedBytes;
        FileOutputStream fileOut;
        File file;
        file = new File(destPath + zipEntry.getName());
        if (zipEntry.isDirectory()) {
            file.mkdirs();
        } else {
            // 如果指定文件的目录不存在,则创建之.
            File parent = file.getParentFile();
            if (!parent.exists()) {
                parent.mkdirs();
            }
            if (!cover && file.exists() && file.length() > 0) {
                //不覆盖且文件存在时，才不解压
                return file;
            }
            fileOut = null;
            try {
                fileOut = new FileOutputStream(file.getAbsolutePath());
                while ((readedBytes = zipIn.read(buf)) > 0) {
                    fileOut.write(buf, 0, readedBytes);
                }
            } catch (Exception e) {logger.debug(e.getMessage(),e);} finally {
                FileUtils.close(fileOut);
            }
        }
        return file;
    }

    private static File unzipWarHander(ZipInputStream zipIn, String destPath, ZipEntry zipEntry, byte[] buf, boolean cover) {
        int readedBytes;
        FileOutputStream fileOut;
        File file;
        if (destPath != null) {
            if (!destPath.endsWith("/") && !destPath.endsWith("\\")) {
                destPath += File.separator;
            }
        }
        String warFileName = zipEntry.getName();
        if (warFileName.startsWith("WEB-INF/classes")) {
            warFileName = warFileName.substring(warFileName.indexOf("WEB-INF/classes") + "WEB-INF/classes".length());
        }
        file = new File(destPath + warFileName);
        if (zipEntry.isDirectory()) {
            file.mkdirs();
        } else {
            // 如果指定文件的目录不存在,则创建之.
            File parent = file.getParentFile();
            if (!parent.exists()) {
                parent.mkdirs();
            }
            if (!cover && file.exists() && file.length() > 0) {
                //不覆盖且文件存在时，才不解压
                return file;
            }
            fileOut = null;
            try {
                fileOut = new FileOutputStream(file.getAbsolutePath());
                while ((readedBytes = zipIn.read(buf)) > 0) {
                    fileOut.write(buf, 0, readedBytes);
                }
            } catch (Exception e) {logger.debug(e.getMessage(),e);} finally {
                FileUtils.close(fileOut);
            }
        }
        return file;
    }

    public static void main(String[] args) {
        String warFileName = "WEB-INF/classes/aa";
        String newStr = warFileName.substring(warFileName.indexOf("WEB-INF/classes") + "WEB-INF/classes".length());
        System.out.println(newStr);
    }
}
