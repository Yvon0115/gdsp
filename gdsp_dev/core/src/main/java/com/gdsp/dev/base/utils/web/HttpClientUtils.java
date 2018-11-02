package com.gdsp.dev.base.utils.web;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections4.map.LinkedMap;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpException;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.EntityBuilder;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 通过HttpClient访问，网站的url
 * @author paul.yang
 * @version 1.0 2014年9月24日
 * @since 1.6
 */
public final class HttpClientUtils {

    /**
     * 日志对象
     */
    protected static final Logger logger = LoggerFactory.getLogger(HttpClientUtils.class);

    /**
     * 尝试请求指定链接是否正常
     * @param client 客户端对象
     * @param url 请求的链接
     * @throws HttpException
     * @throws IOException
     */
    public static boolean testUrl(String url) {
        return testUrl(createHttpClient(), url);
    }

    /**
     * 尝试请求指定链接是否正常
     * @param client 客户端对象
     * @param url 请求的链接
     * @throws HttpException
     * @throws IOException
     */
    public static boolean testUrl(HttpClient client, String url) {
        HttpGet httpGet = new HttpGet(url);
        httpGet.addHeader("Content-Type", "text/html;charset=UTF-8");
        int statusCode = 0;
        try {
            HttpResponse response = client.execute(httpGet);
            statusCode = response.getStatusLine().getStatusCode();
        } catch (Exception e) {
            logger.error("HttpClient请求异常", e);
        } finally {
            httpGet.abort();
        }
        if (HttpStatus.SC_OK != statusCode) {
            return false;
        }
        return true;
    }

    /**
     * 通过GET方法请求服务器文本数据
     * @param url 地址
     * @param parameters 参数
     * @return 结果文本
     * @throws HttpException
     * @throws IOException
     */
    public static String httpGetText(String url, LinkedMap<String, Object> parameters) throws HttpException, IOException {
        return httpGetText(createHttpClient(), url, parameters);
    }

    /**
     * 通过GET方法请求服务器文本数据
     * @param client http客户端
     * @param url 地址
     * @param parameters 参数
     * @return 结果文本
     * @throws HttpException
     * @throws IOException
     */
    public static String httpGetText(HttpClient client, String url, LinkedMap<String, Object> parameters) throws HttpException, IOException {
        if (parameters != null && parameters.size() > 0) {
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            for (int i = 0, len = parameters.size(); i < len; i++) {
                Object v = parameters.getValue(i);
                if (v == null)
                    continue;
                String value = v.toString();
                String key = parameters.get(i);
                params.add(new BasicNameValuePair(key, value));
            }
            if (url.indexOf("?") > -1) {
                url += "&" + URLEncodedUtils.format(params, "UTF-8");
            } else {
                url += "?" + URLEncodedUtils.format(params, "UTF-8");
            }
        }
        HttpGet httpGet = new HttpGet(url);
        httpGet.addHeader("Content-Type", "text/html;charset=UTF-8");
        try {
            HttpResponse response = client.execute(httpGet);
            InputStream is = response.getEntity().getContent();
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            IOUtils.copy(is, os);
            return new String(os.toByteArray(), "UTF-8");
        } finally {
            httpGet.abort();
        }
    }

    /**
     * 通过POST方式向服务器发送数据
     * @param client http客户端
     * @param url 地址
     * @param parameters 参数集
     * @param attachs 附件列表
     * @return 返回结果
     * @throws HttpException
     * @throws IOException
     */
    public static String httpPostUpload(HttpClient client, String url, LinkedMap<String, Object> parameters, LinkedMap<String, File> attachs) throws HttpException, IOException {
        HttpPost httpPost = new HttpPost(url);
        HttpEntity entity = null;
        if (attachs != null && attachs.size() > 0) {
            MultipartEntityBuilder multipartBuilder = MultipartEntityBuilder.create();
            multipartBuilder.setCharset(Charset.forName("UTF-8"));
            for (int i = 0, len = attachs.size(); i < len; i++) {
                multipartBuilder.addBinaryBody(attachs.get(i), attachs.getValue(i));
            }
            if (parameters != null && parameters.size() > 0) {
                for (int i = 0, len = parameters.size(); i < len; i++) {
                    Object v = parameters.getValue(i);
                    if (v == null)
                        continue;
                    String value = v.toString();
                    String key = parameters.get(i);
                    multipartBuilder.addTextBody(key, value);
                }
            }
            entity = multipartBuilder.build();
        } else if (parameters != null && parameters.size() > 0) {
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            for (int i = 0, len = parameters.size(); i < len; i++) {
                Object v = parameters.getValue(i);
                if (v == null)
                    continue;
                String value = v.toString();
                String key = parameters.get(i);
                params.add(new BasicNameValuePair(key, value));
            }
            EntityBuilder builder = EntityBuilder.create();
            builder.setContentEncoding("UTF-8");
            builder.setParameters(params);
            entity = builder.build();
        }
        if (entity != null)
            httpPost.setEntity(entity);
        try {
            HttpResponse response = client.execute(httpPost);
            return EntityUtils.toString(response.getEntity(), "GBK");
        } finally {
            httpPost.abort();
        }
    }

    /**
     * 通过POST方式向服务器发送数据
     * @param url 地址
     * @param parameters 参数集
     * @param attachs 附件列表
     * @return 返回结果
     * @throws HttpException
     * @throws IOException
     */
    public static String httpPostUpload(String url, LinkedMap<String, Object> parameters, LinkedMap<String, File> attachs) throws HttpException, IOException {
        return httpPostUpload(createHttpClient(), url, parameters, attachs);
    }

    /**
     * 创建httpClient对象
     * @return httpClient对象
     */
    public static HttpClient createHttpClient() {
        return HttpClientBuilder.create().build();
    }
}
