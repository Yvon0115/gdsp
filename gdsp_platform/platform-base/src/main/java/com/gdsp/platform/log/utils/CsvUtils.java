package com.gdsp.platform.log.utils;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletOutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gdsp.dev.core.utils.FileUtils;

public class CsvUtils {
	private static final Logger logger = LoggerFactory.getLogger(CsvUtils.class);
	public static void exportCsv(ServletOutputStream outputStream,List<String> headList, List<String> dataList){
	       try {
	           StringBuilder sb = new StringBuilder();
	           if(headList!=null && !headList.isEmpty()){
	               for(String data : headList){
	                sb.append(data);
	                sb.append("\r");//在这里需要将数据一行一行展示
	               }
	           }
	           if(dataList!=null && !dataList.isEmpty()){
	               for(String data : dataList){
	                sb.append(data);
	                sb.append("\r");
	               }
	           }
	           outputStream.write(sb.toString().getBytes("GBK"));//写入输出流
	           outputStream.flush();//缓存流
	       } catch (Exception e) {
	    	   logger.error(e.getMessage(),e);
	       }finally{
	        if(outputStream!=null){
	        try {
	        outputStream.close();//一定要将流关闭，否则日志中会出错
	                outputStream=null;
	            } catch (IOException e) {
	            	logger.error(e.getMessage(),e);
	            } 
	        }
	       }
	   }
}
