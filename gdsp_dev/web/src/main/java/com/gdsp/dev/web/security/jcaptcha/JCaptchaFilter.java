package com.gdsp.dev.web.security.jcaptcha;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.web.servlet.OncePerRequestFilter;

import com.gdsp.dev.core.common.AppContext;
import com.octo.captcha.service.image.ImageCaptchaService;

/**
 * 验证码生成过滤器
 * @author paul.yang
 * @version 1.0 15-2-6
 * @since 1.6
 */
public class JCaptchaFilter extends OncePerRequestFilter {

    /**
     * 验证码图片服务
     */
    private ImageCaptchaService captchaService = null;

    @Override
    protected void doFilterInternal(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        resp.setDateHeader("Expires", 0L);
        resp.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
        resp.addHeader("Cache-Control", "post-check=0, pre-check=0");
        resp.setHeader("Pragma", "no-cache");
        resp.setContentType("image/jpeg");
        String id = req.getSession().getId();
        BufferedImage bi = getCaptchaService().getImageChallengeForID(id);
        ServletOutputStream out = response.getOutputStream();
        ImageIO.write(bi, "jpg", out);
        try {
            out.flush();
        } finally {
            out.close();
        }
    }

    public ImageCaptchaService getCaptchaService() {
        if (captchaService == null) {
            captchaService = AppContext.lookupBean(JCaptcha.class).getCaptchaService();
        }
        return captchaService;
    }
}