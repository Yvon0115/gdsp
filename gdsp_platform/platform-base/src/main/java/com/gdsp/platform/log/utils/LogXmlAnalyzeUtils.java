package com.gdsp.platform.log.utils;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.gdsp.dev.persist.utils.XmlParserUtils;

/**
 * 日志xml文件解析工具类
 * @author guoyang wangchunlong
 * @since 2017年5月31日 下午5:33:04
 */
public class LogXmlAnalyzeUtils {
	private static final Logger logger = LoggerFactory.getLogger(LogXmlAnalyzeUtils.class);
	private static String                            classpath       = null;
	public final static String                CLASS_PROJECT   = "java.class.path";
	public static Element getDocumentRoot(){
		Element root = null;
		String classpaths = getClasspath();
        if (StringUtils.isEmpty(classpaths)) {
            return null;
        }
		Properties properties = System.getProperties();
        String split = properties.getProperty("path.separator", ";");
        String[] paths = classpaths.split(split);
        String realPath = null;
        //环境标记，初始设为false，代表部署环境
        boolean envirFlag = false;
        for (String path : paths) {
			if(path.indexOf("platform-base/target/classes")>-1 ){
				//开发环境，改变标识
				envirFlag = true;
				//说明是开发的环境，获得的路径应该为
				realPath = path+"/gdsp/conf/gdsp-oplog.xml";
			}
        }
        //部署环境
        if(!envirFlag){
        	String path = LogXmlAnalyzeUtils.class.getClassLoader().getResource("").getPath();
        	realPath = path + "gdsp/conf/gdsp-oplog.xml";
        }
		//File file = new File("G:gdsp-oplog.xml"); 
		File file = null;
		try {
			// 解码类路径，因为获取到的路径是URL，里面可能包含特殊字符转码后的内容，这些内容不能直接用于创建file。
			file = new File(URLDecoder.decode(realPath,"utf-8"));  
		} catch (UnsupportedEncodingException e) {
			logger.info(e.getMessage(),e);  
		}   
		InputStream in = null;
		try {
			in = new FileInputStream(file);
		} catch (FileNotFoundException e) {
			logger.info(e.getMessage(),e);  
		}
		Document doc = null;
		try {
			doc = XmlParserUtils.getDocument(in);
		} catch (SAXException ex) {
			logger.info(ex.getMessage(),ex);  
		} catch (IOException exp) {
			logger.info(exp.getMessage(),exp);  
		}
		root = doc != null ? doc.getDocumentElement() : null;
		return root;
	}
	
	/**
	 * 获取xml文 件中所有需要记录变化的表名
	 * @return
	 */
	public static List<String> getTableNameList(){
		Element root  = getDocumentRoot();
		// 获得根元素下的子节点
		NodeList childNodes = root.getChildNodes();
		List<String> tableNameList = new ArrayList<String>();
		for (int i = 0; i < childNodes.getLength(); i++) {
			Node node1 = childNodes.item(i);
			if ("table".equals(node1.getNodeName())) {
				if(node1.hasAttributes()){  
		             NamedNodeMap nodemap=node1.getAttributes();  
		             for(int m = 0;m < nodemap.getLength();m++){  
		                 Node nd = nodemap.item(m);  
		                 String strname = nd.getNodeName();
		                 if("name".equals(strname)){
		                	 tableNameList.add(nd.getNodeValue());
		                 }
		             }             
		         }
			}
		}
		return tableNameList;
	}
	
	/**
	 * 根据表名获取该表所有需要记录的字段和字段描述
	 * @param tableName
	 * @return
	 */
	public static Map<String,String> getTableColumnMap(String tableName){
		Element root  = getDocumentRoot();
		NodeList childNodes = root.getChildNodes();
		Map<String,String> tableColumnList = new HashMap<String,String>();
		for (int i = 0; i < childNodes.getLength(); i++) {
			Node node1 = childNodes.item(i);
			if(node1.getAttributes()!=null){
				if(tableName.equals(node1.getAttributes().getNamedItem("name").getNodeValue())){
					NodeList nodeDetail = node1.getChildNodes();
					for (int j = 0; j < nodeDetail.getLength(); j++) {
						Node detail = nodeDetail.item(j);
						if(detail.getAttributes()!=null){
							tableColumnList.put(detail.getTextContent().trim(),detail.getAttributes().getNamedItem("memo").getNodeValue());
						}
					}
				}
			}
		}
		return tableColumnList;
	}
	
	/**
	 * 根据表名获取表描述信息
	 * @param tableName
	 * @return
	 */
	public static String getTableDesc(String tableName){
		Element root  = getDocumentRoot();
		// 获得根元素下的子节点
		NodeList childNodes = root.getChildNodes();
		String memo = "";
		for (int i = 0; i < childNodes.getLength(); i++) {
			Node node1 = childNodes.item(i);
			if ("table".equals(node1.getNodeName())) {
				if(tableName.equals(node1.getAttributes().getNamedItem("name").getNodeValue())){  
		               memo = node1.getAttributes().getNamedItem("memo").getNodeValue();
		        }
		    }
		}
		return memo;
	}
	
	public static String getClasspath() {
		if (StringUtils.isEmpty(classpath)) {
	        Properties properties = System.getProperties();
	        classpath = properties.getProperty(CLASS_PROJECT);
	        classpath = classpath.replaceAll("\\\\", "/");
		}
		return classpath;
	}
	
}
