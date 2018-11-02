package com.gdsp.dev.web.mvc.tags;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import freemarker.template.TemplateModelException;
import freemarker.template.TransformControl;

/**
 * 模板回调包装书写器，用于输出器的写出
 * @author yaboocn
 * @version 1.0 2010-2-28
 * @since 1.0
 */
public class CallbackWriter extends Writer implements TransformControl {

    /**
     * 日志变量
     */
    private static Logger logger    = LoggerFactory.getLogger(CallbackWriter.class);
    /**需要写出标签*/
    private TemplateTag   tag;
    /**用于组件写出的书写器*/
    private Writer        writer;
    /**输出器内容串的书写 器*/
    private StringWriter  body;
    /**内容后的部分是否已输出*/
    private boolean       afterBody = false;

    /**
     * 构造方法
     * @param tag 标签
     * @param writer 书写器
     */
    public CallbackWriter(TemplateTag tag, Writer writer) {
        this.tag = tag;
        this.writer = writer;
        if (tag.useBody()) {
            this.body = new StringWriter();
        }
    }

    /* (non-Javadoc)
     * @see java.io.Writer#close()
     */
    public void close() throws IOException {
        if (tag.useBody()) {
            body.close();
        }
        tag.close();
    }

    /* (non-Javadoc)
     * @see java.io.Writer#flush()
     */
    public void flush() throws IOException {
        writer.flush();
        if (tag.useBody()) {
            body.flush();
        }
    }

    /* (non-Javadoc)
     * @see java.io.Writer#write(char[], int, int)
     */
    public void write(char cbuf[], int off, int len) throws IOException {
        if (tag.useBody() && !afterBody) {
            body.write(cbuf, off, len);
        } else {
            writer.write(cbuf, off, len);
        }
    }

    /* (non-Javadoc)
     * @see freemarker.template.TransformControl#onStart()
     */
    public int onStart() throws TemplateModelException, IOException {
        boolean result;
        try {
            result = tag.start(this);
        } catch (Exception e) {
            logger.error("TemplateTag occur error when start!", e);
            throw new TemplateModelException("TemplateTag occur error when start!", e);
        }
        if (result) {
            return EVALUATE_BODY;
        } else {
            return SKIP_BODY;
        }
    }

    /* (non-Javadoc)
     * @see freemarker.template.TransformControl#afterBody()
     */
    public int afterBody() throws TemplateModelException, IOException {
        afterBody = true;
        boolean result;
        try {
            result = tag.end(this, tag.useBody() ? body.toString() : "");
        } catch (Exception e) {
            logger.error("TemplateTag occur error when end!", e);
            throw new TemplateModelException("TemplateTag occur error when end!", e);
        }
        if (result) {
            return REPEAT_EVALUATION;
        } else {
            return END_EVALUATION;
        }
    }

    /* (non-Javadoc)
     * @see freemarker.template.TransformControl#onError(java.lang.Throwable)
     */
    public void onError(Throwable throwable) throws Throwable {
        tag.close();
        throw throwable;
    }

    /**
     * 取得组件标签
     * @return 组件标签
     */
    public TemplateTag getTag() {
        return tag;
    }
}
