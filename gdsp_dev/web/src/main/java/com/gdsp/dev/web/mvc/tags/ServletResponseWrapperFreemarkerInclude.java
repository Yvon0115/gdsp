package com.gdsp.dev.web.mvc.tags;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

/**
 * 用于
 *
 * @author paul.yang
 * @version 1.0 2015-11-2
 * @since 1.6
 */
public class ServletResponseWrapperFreemarkerInclude extends HttpServletResponseWrapper {

    /**
     * 输出器
     */
    private PrintWriter printWriter = null;

    /**
     * Constructs a response adaptor wrapping the given response.
     *
     * @param response
     * @param writer wrapper writer
     * @throws IllegalArgumentException if the response is null
     */
    public ServletResponseWrapperFreemarkerInclude(HttpServletResponse response, Writer writer) {
        super(response);
        if (writer instanceof PrintWriter) {
            this.printWriter = (PrintWriter) writer;
        } else {
            this.printWriter = new PrintWriter(writer);
        }
    }

    @Override
    public PrintWriter getWriter() throws IOException {
        return printWriter;
    }

    @Override
    public ServletOutputStream getOutputStream() throws IOException {
        throw new IllegalStateException();
    }

    public void flushBuffer() throws IOException {
        this.printWriter.flush();
    }
}
