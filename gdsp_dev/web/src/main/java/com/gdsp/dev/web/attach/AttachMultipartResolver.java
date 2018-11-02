package com.gdsp.dev.web.attach;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUpload;
import org.apache.commons.fileupload.FileUploadBase;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.ProgressListener;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.gdsp.dev.core.common.AppContext;

/**
 * 重写CommonsMultipartResolver以监听文件上传进度
 * @author yaboocn
 * @version 1.0 2014年8月9日
 * @since 1.6
 */
public class AttachMultipartResolver extends CommonsMultipartResolver {

    /* (non-Javadoc)
     * @see org.springframework.web.multipart.commons.CommonsMultipartResolver#newFileUpload(org.apache.commons.fileupload.FileItemFactory)
     */
    @Override
    protected FileUpload newFileUpload(FileItemFactory fileItemFactory) {
        ServletFileUpload upload = new ServletFileUpload(fileItemFactory);
        upload.setSizeMax(-1);
        UploadProgressListener uploadProgressListener = new UploadProgressListener();
        upload.setProgressListener(uploadProgressListener);
        return upload;
    }

    /* (non-Javadoc)
     * @see org.springframework.web.multipart.commons.CommonsMultipartResolver#parseRequest(javax.servlet.http.HttpServletRequest)
     */
    @Override
    public MultipartParsingResult parseRequest(HttpServletRequest request)
            throws MultipartException {
        String encoding = getDefaultEncoding();
        FileUpload fileUpload = prepareFileUpload(encoding);
        try {
            List<FileItem> fileItems = ((ServletFileUpload) fileUpload).parseRequest(request);
            return parseFileItems(fileItems, encoding);
        } catch (FileUploadBase.SizeLimitExceededException ex) {
            long contentLength = Long.parseLong(request.getHeader("Content-Length"));
            FileExceedException e = new FileExceedException(contentLength, fileUpload.getSizeMax(), ex);
            throw e;
        } catch (FileUploadException ex) {
            throw new MultipartException("Could not parse multipart servlet request", ex);
        }
    }
}

/**
 * 上传进度监听器
 * @author paul.yang
 * @version 1.0 2014年9月24日
 * @since 1.6
 */
class UploadProgressListener implements ProgressListener {

    /* (non-Javadoc)
     * @see org.apache.commons.fileupload.ProgressListener#update(long, long, int)
     */
    public void update(long readBytes, long contentLength, int order) {
        AppContext context = AppContext.getContext();
        Progress ps = (Progress) context.getAttribute(AppContext.SESSION, "__uploadProgress");
        if (ps == null)
            ps = new Progress();
        ps.setReadBytes(readBytes);
        ps.setContentBytes(contentLength);
        ps.setOrder(order);
        // 更新
        context.setAttribute(AppContext.SESSION, "__uploadProgress", ps);
    }
}
