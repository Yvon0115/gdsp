package com.gdsp.platform.comm.mail.model;

import java.io.Serializable;

import org.springframework.web.multipart.MultipartFile;

public class MailInfo implements Serializable {

    private static final long serialVersionUID = 5058192228637712696L;
    private String            subject;                                //主题
    private String            content;                                //文本内容
    private MultipartFile[]    attachment;                             //附件
    private String[]          toAddress;                              //接收地址
    private String[] 		  ccs;									  //多个抄送方
    private String[]		  bccs;									  //多个秘密抄送方
    
    
    public String[] getCcs() {
		return ccs;
	}

	public void setCcs(String[] ccs) {
		this.ccs = ccs;
	}

	public String[] getBccs() {
		return bccs;
	}

	public void setBccs(String[] bccs) {
		this.bccs = bccs;
	}

	public String[] getToAddress() {
        return toAddress;
    }

    public void setToAddress(String[] toAddress) {
        this.toAddress = toAddress;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public MultipartFile[] getAttachment() {
        return attachment;
    }

    public void setAttachment(MultipartFile[] attachment) {
        this.attachment = attachment;
    }
}
