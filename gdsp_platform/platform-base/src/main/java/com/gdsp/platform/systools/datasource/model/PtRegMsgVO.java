/**  
* @Title: PtRegMsgVO.java
* @Package com.gdsp.platform.systools.datasource.model
* (用一句话描述该文件做什么)
* @author yuchenglong
* @date 2017年6月30日 上午10:51:31
* @version V1.0  
*/
package com.gdsp.platform.systools.datasource.model;

/**
* @ClassName: PtRegMsgVO
* (用于返回数据源注册服务是否成功的状态及信息)
* @author yuchenglong
* @date 2017年6月30日 上午10:51:31
*
*/
public class PtRegMsgVO {

    /**
     * 默认无参构造方法
    * <p>Title: </p>
    * <p>Description: </p>
     */
    public PtRegMsgVO() {}

    /**
     * 带参构造方法 
    * <p>Title: </p>
    * <p>Description: </p>
    * @param state：状态
    * @param statusCode：状态码
    * @param content：详情
     */
    public PtRegMsgVO(Boolean regFlag,String state, String statusCode, String content) {
        this.regFlag = regFlag;
        this.state = state;
        this.statusCode = statusCode;
        this.content = content;
    }

    /**
     * 注册是否成功，布尔值
     */
    private Boolean regFlag;
    /**
     * 注册状态：成功/失败
     */
    private String  state;
    /**
     * 状态码
     */
    private String  statusCode;
    /**
     * 注册详细信息
     */
    private String  content;

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Boolean getRegFlag() {
        return regFlag;
    }

    public void setRegFlag(Boolean regFlag) {
        this.regFlag = regFlag;
    }
}
