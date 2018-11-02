package com.gdsp.dev.web.mvc.view;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import freemarker.cache.TemplateLoader;

/**
 * @author paul.yang
 * @version 1.0 2014年10月23日
 * @since 1.6
 */
public class DevelopTemplateLoader implements TemplateLoader {

    /**
     * 日志对象
     */
    protected Logger             logger         = LoggerFactory.getLogger(getClass());
    /**
     * 多个项目的freemarker目录
     */
    private List<File>           baseDirs       = null;
    /**
     * 多个项目的freemarker目录规范路径
     */
    private List<String>         canonicalPaths = null;
    /**
     * 分割符是否为unix标准
     */
    private static final boolean SEP_IS_SLASH   = File.separatorChar == '/';

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public DevelopTemplateLoader(String templateLoaderPath) throws IOException {
        String pathString = System.getProperty("project.webpaths");
        if (StringUtils.isEmpty(pathString)) {
            throw new IOException("templatepath does not exist.");
        }
        baseDirs = new ArrayList<File>();
        canonicalPaths = new ArrayList<String>();
        String[] dirs = pathString.split(",");
        for (String dir : dirs) {
            final File baseDir = new File(dir, templateLoaderPath);
            File retval = null;
            try {
                retval = (File) AccessController.doPrivileged(new PrivilegedExceptionAction() {

                    @Override
                    public Object run() throws IOException {
                        if (!baseDir.exists()) {
                            logger.error(baseDir + " does not exist.");
                            return null;
                        }
                        if (!baseDir.isDirectory()) {
                            logger.error(baseDir + " is not a directory.");
                            return null;
                        }
                        return baseDir.getCanonicalFile();
                    }
                });
            } catch (PrivilegedActionException e) {
                logger.debug(e.getMessage(), e);
                continue;
            }
            if (retval != null) {
                baseDirs.add(retval);
                String basePath = retval.getPath();
                // Most canonical paths don't end with File.separator,
                // but some does. Like, "C:\" VS "C:\templates".
                if (!basePath.endsWith(File.separator)) {
                    basePath += File.separatorChar;
                }
                canonicalPaths.add(basePath);
            }
        }
        if (baseDirs.size() == 0)
            throw new IOException("templatepath does not exist.");
    }

    @Override
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public Object findTemplateSource(final String name) throws IOException {
        try {
            return AccessController
                    .doPrivileged(new PrivilegedExceptionAction() {

                        @Override
                        public Object run() throws IOException {
                            File source = null;
                            for (int i = 0; i < baseDirs.size(); i++) {
                                File baseDir = baseDirs.get(i);
                                source = new File(baseDir, SEP_IS_SLASH ? name
                                        : name.replace('/', File.separatorChar));
                                if (!source.isFile()) {
                                    continue;
                                }
                                String canonicalPath = canonicalPaths.get(i);
                                String normalized = source.getCanonicalPath();
                                if (!normalized.startsWith(canonicalPath)) {
                                    throw new SecurityException(source
                                            .getAbsolutePath()
                                            + " resolves to "
                                            + normalized
                                            + " which "
                                            + " doesn't start with "
                                            + canonicalPath);
                                }
                                break;
                            }
                            if (source == null || !source.isFile())
                                return null;
                            return source;
                        }
                    });
        } catch (PrivilegedActionException e) {
            logger.debug(e.getMessage(), e);
            throw (IOException) e.getException();
        }
    }

    @Override
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public long getLastModified(final Object templateSource) {
        return ((Long) (AccessController.doPrivileged(new PrivilegedAction() {

            @Override
            public Object run() {
                return new Long(((File) templateSource).lastModified());
            }
        }))).longValue();
    }

    @Override
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public Reader getReader(final Object templateSource, final String encoding)
            throws IOException {
        try {
            return (Reader) AccessController
                    .doPrivileged(new PrivilegedExceptionAction() {

                        @Override
                        public Object run() throws IOException {
                            if (!(templateSource instanceof File)) {
                                throw new IllegalArgumentException(
                                        "templateSource is a: "
                                                + templateSource.getClass()
                                                        .getName());
                            }
                            return new InputStreamReader(new FileInputStream(
                                    (File) templateSource), encoding);
                        }
                    });
        } catch (PrivilegedActionException e) {
            logger.debug(e.getMessage(), e);
            throw (IOException) e.getException();
        }
    }

    @Override
    public void closeTemplateSource(Object templateSource) {
        // Do nothing.
    }
}
