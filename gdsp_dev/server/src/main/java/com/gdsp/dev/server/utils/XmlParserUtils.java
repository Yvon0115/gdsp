/*
 * Copyright(c) FastSpace Software 2014. All Rights Reserved.
 */
package com.gdsp.dev.server.utils;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

/**
 * xml解析器的包装类
 * @author yaboocn
 * @version 1.0 2014年5月5日
 * @since 1.7
 */
public class XmlParserUtils {
    private static Logger       logger         = LoggerFactory.getLogger(XmlParserUtils.class);
    /**
     * 取得新建的xml结构的Document对象
     * @return Document对象
     */
    public static Document getDocument()  {
        DocumentBuilder builder = getDocumentBuilder();
        if (builder == null) {
            return null;
        }
        Document doc = builder.newDocument();
        return doc;
    }

    /**
     * 解析XML文件返回Document对象
     * @param file xml文件
     * @return Document对象
     * @throws IOException 
     * @throws SAXException 
     */
    public static Document getDocument(File file) throws SAXException, IOException  {
        DocumentBuilder builder = getDocumentBuilder();
        if (builder == null) {
            return null;
        }
        Document doc = builder.parse(file);
        return doc;
    }

    /**
     * 解析XML数据流返回Document对象
     * @param in xml输入流
     * @return Document对象
     * @throws IOException 
     * @throws SAXException 
     */
    public static Document getDocument(InputStream in) throws SAXException, IOException  {
        DocumentBuilder builder = getDocumentBuilder();
        if (builder == null) {
            return null;
        }
        Document doc = builder.parse(in);
        return doc;
    }

    /**
     * 解析xml串返回Document对象
     * @param xml xml串
     * @return Document对象
     * @throws IOException 
     * @throws SAXException 
     */
    public static Document getDocument(String xml) throws SAXException, IOException  {
        DocumentBuilder builder = getDocumentBuilder();
        if (builder == null) {
            return null;
        }
        InputStream is = new ByteArrayInputStream(xml.getBytes("utf-8"));
        Document doc = builder.parse(is);
        return doc;
    }

    /**
     * 取得xml解析期的Document构造器对象
     * @return Document构造器对象
     */
    public static DocumentBuilder getDocumentBuilder() {
        DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
        builderFactory.setIgnoringElementContentWhitespace(true);
        builderFactory.setIgnoringComments(true);
        DocumentBuilder builder = null;
        try {
            builder = builderFactory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            
           logger.error(e.getMessage(), e);
        }
        return builder;
    }

    /**
     * 取得SAX方式的解析器对象
     * @return 解析器对象
     */
    public static SAXParser getSAXParser()  {
        SAXParserFactory parserFactory = SAXParserFactory.newInstance();
        SAXParser saxParser = null;
        try {
            saxParser = parserFactory.newSAXParser();
        } catch (ParserConfigurationException | SAXException e) {
            
            logger.error(e.getMessage(), e);
        }
        return saxParser;
    }
}
