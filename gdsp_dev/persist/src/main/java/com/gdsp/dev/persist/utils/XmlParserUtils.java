/*
 * Copyright(c) FastSpace Software 2014. All Rights Reserved.
 */
package com.gdsp.dev.persist.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.ibatis.logging.Log;
import org.apache.ibatis.logging.LogFactory;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

/**
 * xml解析器的包装类
 * @author yaboocn
 * @version 1.0 2014年5月5日
 * @since 1.7
 */
public class XmlParserUtils {
    /**
     * 日志对象
     */
    private final static Log                  logger                         = LogFactory.getLog(XmlParserUtils.class);

    /**
     * 取得新建的xml结构的Document对象
     * @return Document对象
     */
    public static Document getDocument() {
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
    public static Document getDocument(File file) throws SAXException, IOException {
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
    public static Document getDocument(String xml, boolean ignoreComments) throws SAXException, IOException {
        DocumentBuilder builder = getDocumentBuilder(ignoreComments);
        if (builder == null) {
            return null;
        }
        InputStream is = new ByteArrayInputStream(xml.getBytes("utf-8"));
        Document doc = builder.parse(is);
        return doc;
    }

    public static Document getDocument(String xml) throws SAXException, IOException {
        return getDocument(xml, true);
    }

    public static DocumentBuilder getDocumentBuilder()  {
        return getDocumentBuilder(true);
    }

    /**
     * 取得xml解析期的Document构造器对象
     * @return Document构造器对象
     */
    public static DocumentBuilder getDocumentBuilder(boolean ignoreComments) {
        DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
        builderFactory.setIgnoringElementContentWhitespace(true);
        builderFactory.setIgnoringComments(ignoreComments);
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
    public static SAXParser getSAXParser() {
        SAXParserFactory parserFactory = SAXParserFactory.newInstance();
        SAXParser saxParser = null;
        try {
            saxParser = parserFactory.newSAXParser();
        } catch (ParserConfigurationException | SAXException e) {
            
            logger.error(e.getMessage(), e);
        }
        return saxParser;
    }

    /**
     * 将文档对象转化为 string
     * @param doc
     * @return
     * @throws TransformerException
     * @throws UnsupportedEncodingException
     */
    public static String getDocumentFullContent(Document doc) throws TransformerException, UnsupportedEncodingException {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        TransformerFactory factory = TransformerFactory.newInstance();
        Transformer trans = factory.newTransformer();
        DOMSource source = new DOMSource(doc);
        trans.setOutputProperty(OutputKeys.ENCODING, "utf-8");
        trans.setOutputProperty(OutputKeys.INDENT, "yes");
        StreamResult result = new StreamResult(os);
        trans.transform(source, result);
        return os.toString("utf-8");
    }
}
