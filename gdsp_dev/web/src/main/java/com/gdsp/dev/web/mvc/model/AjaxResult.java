package com.gdsp.dev.web.mvc.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang3.StringUtils;

/**
 * ajax访问的结果
 * @author paul.yang
 * @version 1.0 2014年9月24日
 * @since 1.6
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.PUBLIC_MEMBER)
public class AjaxResult {

    /*回调类型常量*/
    /**
     * 回调类型，关闭当前页签
     */
    public static final String     CALLBACK_CLOSESELF    = "closeSelf";
    /**
     * 回调类型,刷新表格数据
     */
    public static final String     CALLBACK_REFRESHTABLE = "refreshTable";
    /**
     * 回调类型,刷新当前页面
     */
    public static final String     CALLBACK_REFRESHPAGE  = "refreshPage";
    /**
     * 回调类型,跳转到指定页面
     */
    public static final String     CALLBACK_FORWARDURL   = "forwardUrl";
    /**
     * 回调类型,调用method属性指定的方法
     */
    public static final String     CALLBACK_CALLMETHOD   = "callMethod";
    /*ajax结果常量*/
    /**
     * 操作成功
     */
    public static final AjaxResult SUCCESS               = new AjaxResult("操作成功。");
    /**
     * 操作失败
     */
    public static final AjaxResult ERROR                 = new AjaxResult("操作失败。");
    /**
     * 操作超时
     */
    public static final AjaxResult TIMEOUT               = new AjaxResult("操作超时。");
    /**
     * 不需要提示
     */
    public static final AjaxResult NOTHING               = new AjaxResult();
    /**
     * 成功状态码
     */
    public static final int        STATUS_SUCCESS        = 200;
    /**
     * 错误状态码
     */
    public static final int        STATUS_ERROR          = 300;
    /**
     * 超时状态码
     */
    public static final int        STATUS_TIMEOUT        = 301;
    /**
     * 状态码
     */
    private int                    statusCode            = STATUS_SUCCESS;
    /** 消息*/
    private String                 message               = null;
    /**
     * 回调类型
     */
    private List<String>           callBackTypes         = null;
    /**
     * 回调方法
     */
    private String                 method                = null;
    /**
     * 参数
     */
    private Map<String, Object>    params                = null;

    /**
     * 构造方法
     */
    public AjaxResult() {}

    public AjaxResult(String message) {
        this(STATUS_SUCCESS, message);
    }

    public AjaxResult(int statusCode, String message) {
        this(statusCode, message, null);
    }

    public AjaxResult(int statusCode, String message, Map<String, Object> params) {
        this(statusCode, message, params, false);
    }

    public AjaxResult(int statusCode, String message, Map<String, Object> params, boolean needTranslate) {
        this.statusCode = statusCode;
        this.message = message;
        this.params = params;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * 设置回调类型列表
     * @param callBackTypes 回调类型列表
     */
    public void setCallBackTypes(List<String> callBackTypes) {
        this.callBackTypes = callBackTypes;
    }

    /**
     * 添加回调类型
     * @param callBackType 回调类型
     */
    public void addCallBackType(String callBackType) {
        if (callBackTypes == null)
            callBackTypes = new ArrayList<String>();
        callBackTypes.add(callBackType);
    }

    /**
     * 清空回调类型
     */
    public void clearCalllBackTypes() {
        if (callBackTypes != null)
            callBackTypes.clear();
    }

    public String getCallbackType() {
        if (callBackTypes == null || callBackTypes.size() == 0)
            return null;
        return StringUtils.join(callBackTypes, ',');
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public Map<String, Object> getParams() {
        return params;
    }

    public void setParams(Map<String, Object> params) {
        this.params = params;
    }
}
