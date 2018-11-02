package com.gdsp.dev.persist.ext.merge.assistant;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.logging.Log;
import org.apache.ibatis.logging.LogFactory;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 * mybatis源码
 * 本地化 dtd 增加额外属性
 * Offline entity resolver for the MyBatis DTDs
 * 
 * @author Clinton Begin,xiangguo
 * @version 1.0 2016-03-31
 * @since 1.8 
 */
public class ResourceMergeXMLMapperEntityResolver implements EntityResolver {
    
    /**
     * 日志对象
     */
    private final static Log                  logger                         = LogFactory.getLog(ResourceMergeXMLMapperEntityResolver.class);
    private static final Map<String, String> doctypeMap            = new HashMap<String, String>();
    private static final String              IBATIS_CONFIG_PUBLIC  = "-//ibatis.apache.org//DTD Config 3.0//EN".toUpperCase(Locale.ENGLISH);
    private static final String              IBATIS_CONFIG_SYSTEM  = "http://ibatis.apache.org/dtd/ibatis-3-config.dtd".toUpperCase(Locale.ENGLISH);
    private static final String              IBATIS_MAPPER_PUBLIC  = "-//ibatis.apache.org//DTD Mapper 3.0//EN".toUpperCase(Locale.ENGLISH);
    private static final String              IBATIS_MAPPER_SYSTEM  = "http://ibatis.apache.org/dtd/ibatis-3-mapper.dtd".toUpperCase(Locale.ENGLISH);
    private static final String              MYBATIS_CONFIG_PUBLIC = "-//mybatis.org//DTD Config 3.0//EN".toUpperCase(Locale.ENGLISH);
    private static final String              MYBATIS_CONFIG_SYSTEM = "http://mybatis.org/dtd/mybatis-3-config.dtd".toUpperCase(Locale.ENGLISH);
    private static final String              MYBATIS_MAPPER_PUBLIC = "-//mybatis.org//DTD Mapper 3.0//EN".toUpperCase(Locale.ENGLISH);
    private static final String              MYBATIS_MAPPER_SYSTEM = "http://mybatis.org/dtd/mybatis-3-mapper.dtd".toUpperCase(Locale.ENGLISH);
    private static final String              MYBATIS_CONFIG_DTD    = "com/gdsp/dev/persist/ext/merge/assistant/mybatis-3-config.dtd";
    private static final String              MYBATIS_MAPPER_DTD    = "com/gdsp/dev/persist/ext/merge/assistant/mybatis-3-mapper.dtd";
    static {
        doctypeMap.put(IBATIS_CONFIG_SYSTEM, MYBATIS_CONFIG_DTD);
        doctypeMap.put(IBATIS_CONFIG_PUBLIC, MYBATIS_CONFIG_DTD);
        doctypeMap.put(IBATIS_MAPPER_SYSTEM, MYBATIS_MAPPER_DTD);
        doctypeMap.put(IBATIS_MAPPER_PUBLIC, MYBATIS_MAPPER_DTD);
        doctypeMap.put(MYBATIS_CONFIG_SYSTEM, MYBATIS_CONFIG_DTD);
        doctypeMap.put(MYBATIS_CONFIG_PUBLIC, MYBATIS_CONFIG_DTD);
        doctypeMap.put(MYBATIS_MAPPER_SYSTEM, MYBATIS_MAPPER_DTD);
        doctypeMap.put(MYBATIS_MAPPER_PUBLIC, MYBATIS_MAPPER_DTD);
    }

    /*
     * Converts a public DTD into a local one
     * 
     * @param publicId The public id that is what comes after "PUBLIC"
     * @param systemId The system id that is what comes after the public id.
     * @return The InputSource for the DTD
     * 
     * @throws org.xml.sax.SAXException If anything goes wrong
     */
    public InputSource resolveEntity(String publicId, String systemId) throws SAXException {
        if (publicId != null)
            publicId = publicId.toUpperCase(Locale.ENGLISH);
        if (systemId != null)
            systemId = systemId.toUpperCase(Locale.ENGLISH);
        InputSource source = null;
        try {
            String path = doctypeMap.get(publicId);
            source = getInputSource(path, source);
            if (source == null) {
                path = doctypeMap.get(systemId);
                source = getInputSource(path, source);
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new SAXException(e.toString());
        }
        return source;
    }

    private InputSource getInputSource(String path, InputSource source) {
        if (path != null) {
            InputStream in;
            try {
                in = Resources.getResourceAsStream(path);
                source = new InputSource(in);
            } catch (IOException e) {
                logger.error(e.getMessage(), e);
            }
        }
        return source;
    }
}
