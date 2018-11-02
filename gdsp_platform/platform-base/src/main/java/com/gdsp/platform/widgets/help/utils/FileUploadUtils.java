package com.gdsp.platform.widgets.help.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

import com.gdsp.dev.core.common.AppConfig;

public class FileUploadUtils {

    /**完成文件上传的同时，返回路径path（相对路径）*/
    /*
     * 要求：
     * 将上传的文件，上传到upload文件夹下
     * 将上传的文件，同时使用日期格式文件夹管理维护（格式：\2014\02\24\）
     * 上传后的文件名要惟一（UUID或者日期）
     * 上传文件的方法，让大家做一个封装， 支持多种文件格式的上传
     * 上传的路径path，存放相对路径（编译文件的移植）
     */
    public static String uploadFileReturnPath(File upload, String uploadFileName) {
        //查找到文件上传的路径upload
        String basePath = AppConfig.getInstance().getString("portal.help.upload.addr") + "/upload";
        //使用当前时间作为日期文件夹
        String datePath = new SimpleDateFormat("/yyyy/MM/dd/").format(new Date());
        //获取上传文件的后缀名称
        String prifix = uploadFileName.substring(uploadFileName.lastIndexOf("."));
        //文件名使用UUID，自动生成32位的值
        String filename = UUID.randomUUID().toString() + prifix;
        //如果日期文件夹要是不存在，创建日期文件夹
        File dateFile = new File(basePath + datePath);
        if (!dateFile.exists()) {
            dateFile.mkdirs();
        }
        //目标文件
        File destFile = new File(basePath + datePath + filename);
        //文件上传
        upload.renameTo(destFile);
        //相对路径
        return "/upload" + datePath + filename;
    }

    public static String uploadFileReturnPath(InputStream in, String uploadFileName) throws IOException {
        //查找到文件上传的路径upload
        String basePath = AppConfig.getInstance().getString("portal.help.upload.addr") + "/upload";
        //使用当前时间作为日期文件夹
        String datePath = new SimpleDateFormat("/yyyy/MM/dd/").format(new Date());
        //获取上传文件的后缀名称
        String prifix = uploadFileName.substring(uploadFileName.lastIndexOf("."));
        //文件名使用UUID，自动生成32位的值
        String filename = UUID.randomUUID().toString() + prifix;
        //如果日期文件夹要是不存在，创建日期文件夹
        File dateFile = new File(basePath + datePath);
        if (!dateFile.exists()) {
            dateFile.mkdirs();
        }
        //目标文件
        File destFile = new File(basePath + datePath + filename);
        //文件上传
        //upload.renameTo(destFile);
        FileOutputStream fos = FileUtils.openOutputStream(destFile);
        IOUtils.copy(in, fos);
        fos.close();
        in.close();
        //相对路径
        return "/upload" + datePath + filename;
    }

    public static void main(String[] args) throws Exception {
        //源文件
        File srcFile = new File("F:\\dir\\a.txt");
        //目标文件
        File destFile = new File("F:\\dir\\dir2\\a.txt");
        //文件上传（复制）
        //FileUtils.copyFile(srcFile, destFile);
        //文件上传（剪切）
        boolean flag = srcFile.renameTo(destFile);
        System.out.println(flag);
    }
}
