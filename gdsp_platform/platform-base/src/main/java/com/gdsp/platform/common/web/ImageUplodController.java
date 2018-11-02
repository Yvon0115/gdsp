package com.gdsp.platform.common.web;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.gdsp.dev.core.common.AppConfig;

@Controller
@RequestMapping("/systools/ckeditor")
public class ImageUplodController {

	private static final Logger logger = LoggerFactory.getLogger(ImageUplodController.class);
	
    @RequestMapping("/upload.d")
    public void uploadFile(@RequestParam("upload") MultipartFile multipartFile, HttpServletRequest request, HttpServletResponse response){
        response.setContentType("text/html;charset=UTF-8");
        response.setHeader("X-Frame-Options", "SAMEORIGIN");
        String CKEditorFuncNum = request.getParameter("CKEditorFuncNum");
        PrintWriter out = null;
        String path = request.getContextPath() + AppConfig.getInstance().getString("imageUploadUrl");
        String imageName = "";
        if (!multipartFile.isEmpty()) {
            String fileName = multipartFile.getOriginalFilename();
            String prefix = fileName.substring(fileName.lastIndexOf(".")+1);
            try {
                // 文件保存路径  
                DateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
                Calendar calendar = Calendar.getInstance();
                imageName = df.format(calendar.getTime())+"."+prefix;
                String filePath = request.getSession().getServletContext().getRealPath("/") + AppConfig.getInstance().getString("imageUploadUrl")
                        + imageName;
                // 转存文件  
                multipartFile.transferTo(new File("/" + filePath));
            } catch (Exception e) {
            	logger.error(e.getMessage(),e);
            }
        }
        String s = "<script type=\"text/javascript\">window.parent.CKEDITOR.tools.callFunction(" + CKEditorFuncNum + ", '" + path + imageName + "','上传成功！');</script>";
        try {
            out = response.getWriter();
            out.print(s);
            out.flush();
        } catch (IOException e) {
        	logger.error(e.getMessage(),e);
        }
    }
}